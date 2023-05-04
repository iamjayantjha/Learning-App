package com.learningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "learning_app.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PHONE_NUMBER";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID TEXT PRIMARY KEY, NAME TEXT, PHONE_NUMBER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String id,String name,String phone_number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone_number);
        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }

    public Cursor readData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor readData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {COL_1,COL_2,COL_3};
        String selection = COL_1+ " LIKE ?";
        String[] selection_args = {id};
        Cursor res = db.query(TABLE_NAME,projection,selection,selection_args,null,null,null);
        return res;
    }
    public boolean updateData(String id,String name,String phone_number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone_number);
        /*long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;*/
        //String[] selection_args = {id};
        long result = db.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        return result != -1;
    }

    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"ID =?",new String[]{id});
        return result !=1;
    }

}
