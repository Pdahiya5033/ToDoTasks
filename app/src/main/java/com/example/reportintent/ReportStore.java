package com.example.reportintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportStore {
    private static ReportStore sReportStore;
//    private List<Report> mReports;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static ReportStore get(Context context){
        if(sReportStore==null){
            sReportStore=new ReportStore(context);
        }
        return sReportStore;
    }
    private ReportStore(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new ReportBaseHelper(mContext).getWritableDatabase();

//        mReports=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            Report report = new Report();
//            report.setmTitle("Report # "+i);
//            report.setmResolved(i%2==0);
//            mReports.add(report);
//        }

    }
    public void addReport(Report r){
//        mReports.add(r);
        ContentValues values=getContentValues(r);
        mDatabase.insert(ReportDBSchema.ReportTable.NAME,null,values);
    }
    public void updateReport(Report report){
        String uuidString=report.getmId().toString();
        ContentValues values=getContentValues(report);
        mDatabase.update(ReportDBSchema.ReportTable.NAME,values,
                ReportDBSchema.ReportTable.Cols.UUID+" = ?",
                new String[] {uuidString});

    }
    public List<Report> getmReports(){
//        return mReports;
//        return new ArrayList<>();
        List<Report> reports=new ArrayList<>();;
        ReportCursorWrapper cursor=queryReports(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                reports.add(cursor.getReport());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return reports;
    }
    public Report getReport(UUID id){
//        for(Report report:mReports){
//            if(report.getmId().equals(id)){
//                return report;
//            }
//        }
//        return null;
        ReportCursorWrapper cursor=queryReports(
                ReportDBSchema.ReportTable.Cols.UUID+" = ?",
                new String[] {id.toString()}
        );
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getReport();
        }finally{
            cursor.close();
        }
    }
    private static ContentValues getContentValues(Report report){
        ContentValues values=new ContentValues();
        values.put(ReportDBSchema.ReportTable.Cols.UUID,report.getmId().toString());
        values.put(ReportDBSchema.ReportTable.Cols.TITLE,report.getmTitle());
        values.put(ReportDBSchema.ReportTable.Cols.DATE,report.getmDate().getTime());
        values.put(ReportDBSchema.ReportTable.Cols.RESOLVED,report.getmResolved()?1:0);
        return values;
    }
    private ReportCursorWrapper queryReports(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(ReportDBSchema.ReportTable.NAME,
                null,whereClause,whereArgs,null,null,null);
        return new ReportCursorWrapper(cursor);
    }
}
