package com.learningapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.learningapp.Modal.PostGrid;
import com.learningapp.R;

import java.util.ArrayList;

public class PostGridAdapter extends ArrayAdapter<PostGrid> {
    public PostGridAdapter(@NonNull Context context, ArrayList<PostGrid> postGrids) {
        super(context, 0, postGrids);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.post_grid_item, parent, false);
        }
        PostGrid postGrid = getItem(position);
        ImageView postImage = listItemView.findViewById(R.id.userPost);
        Glide.with(getContext()).load(postGrid.getImageURI()).into(postImage);
        return listItemView;
    }
}