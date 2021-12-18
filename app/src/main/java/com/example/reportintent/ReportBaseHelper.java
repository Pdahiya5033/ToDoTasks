package com.example.reportintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.reportintent.ReportDBSchema.ReportTable;

public class ReportBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="reportBase.db";

    public ReportBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        // insert string create table
        db.execSQL("create table "+ReportTable.NAME+"("+"_id integer primary key autoincrement, "+ReportTable.Cols.UUID+", "+
                ReportTable.Cols.TITLE+", "+
                ReportTable.Cols.DATE+", "+
                ReportTable.Cols.RESOLVED+")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
