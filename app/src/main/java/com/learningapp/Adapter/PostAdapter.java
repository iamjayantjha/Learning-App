package com.learningapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Comments.CommentFragment;
import com.learningapp.Modal.Posts;
import com.learningapp.Modal.Users;
import com.learningapp.Profile.ProfileFragment;
import com.learningapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    public Context mContext;
    public List<Posts> posts;
    public FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Posts> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((mContext)).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Posts post = posts.get(position);
        Glide.with(mContext).load(post.getPostImage()).into(holder.userPost);
        holder.date.setText(post.getDate());
        if (post.getCaption().equals("")) {
            holder.caption.setVisibility(View.GONE);
        } else {
            holder.caption.setVisibility(View.VISIBLE);
            holder.caption.setText(post.getCaption());
        }
        if (post.getLocation().equals("")) {
            holder.location.setVisibility(View.GONE);
        } else {
            holder.location.setVisibility(View.VISIBLE);
            holder.location.setText(post.getLocation());
        }

        holder.userPost.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostId()).child(firebaseUser.getUid()).setValue(true);
                    holder.like.setImageResource(R.drawable.heart_filled);
                    return super.onDoubleTap(e);
                }
            });
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        publisherInfo(holder.userProfilePicture, holder.userName, post.getPublisher());
        isLiked(post.getPostId(), holder.like);
        isSaved(post.getPostId(), holder.save);
        holder.userName.setOnClickListener(v -> {
            SharedPreferences.Editor preferences = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            preferences.putString("profileid", post.getPublisher());
            preferences.apply();
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
        });
        holder.userProfilePicture.setOnClickListener(v -> {
            SharedPreferences.Editor preferences = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            preferences.putString("profileid", post.getPublisher());
            preferences.apply();
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
        });

        holder.like.setOnClickListener(v -> {
            if(holder.like.getTag().equals("like")){
                FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostId()).child(firebaseUser.getUid()).setValue(true);
                holder.like.setImageResource(R.drawable.heart_filled);
            } else {
                FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostId()).child(firebaseUser.getUid()).removeValue();
                holder.like.setImageResource(R.drawable.heart);
            }
        });
        holder.comment.setOnClickListener(v -> {
            SharedPreferences.Editor preferences = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            preferences.putString("postId", post.getPostId());
            preferences.putString("publisherId", post.getPublisher());
            preferences.apply();
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CommentFragment()).commit();
        });
        holder.comments.setOnClickListener(v -> {
            SharedPreferences.Editor preferences = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            preferences.putString("postId", post.getPostId());
            preferences.putString("publisherId", post.getPublisher());
            preferences.apply();
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CommentFragment()).commit();
        });
        holder.save.setOnClickListener(v -> {
            if (holder.save.getTag().equals("save")) {
                FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(post.getPostId()).setValue(true);
                holder.save.setImageResource(R.drawable.save_filled);
            }else {
                FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(post.getPostId()).removeValue();
                holder.save.setImageResource(R.drawable.save);
            }
        });
        numLikes(holder.likesCount, post.getPostId());
        getComments(post.getPostId(), holder.comments);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView userProfilePicture;
        TextView userName, location, likesCount, caption, comments, date;
        ImageView userPost, like, comment, save;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfilePicture = itemView.findViewById(R.id.userProfilePicture);
            userName = itemView.findViewById(R.id.userName);
            location = itemView.findViewById(R.id.location);
            likesCount = itemView.findViewById(R.id.likesCount);
            caption = itemView.findViewById(R.id.caption);
            comments = itemView.findViewById(R.id.comments);
            date = itemView.findViewById(R.id.date);
            userPost = itemView.findViewById(R.id.userPost);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
        }
    }

    private void isLiked(String postid, final ImageView imageView){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assert firebaseUser != null;
                if(snapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.heart_filled);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.heart);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void numLikes(final TextView likes, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.getChildrenCount()>1){
                        likes.setText(snapshot.getChildrenCount() + " likes");
                    } else {
                        likes.setText(snapshot.getChildrenCount() + " like");
                    }
                }else {
                    likes.setText("0 likes");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getComments(String postid, final TextView commentsCount) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == 1){
                    commentsCount.setText("View " + snapshot.getChildrenCount() + " comment");
                } else if (snapshot.getChildrenCount() > 1){
                    commentsCount.setText("View all " + snapshot.getChildrenCount() + " comments");
                } else {
                    commentsCount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isSaved(String postid, final ImageView imageView){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postid).exists()){
                    imageView.setImageResource(R.drawable.save_filled);
                    imageView.setTag("saved");
                } else {
                    imageView.setImageResource(R.drawable.save);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void publisherInfo(final CircleImageView userProfilePicture, final TextView userName, final String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                assert users != null;
                Glide.with(mContext).load(users.getImage_url()).into(userProfilePicture);
                userName.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
