package com.example.reportintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class ReportCursorWrapper extends CursorWrapper {

    public ReportCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Report getReport(){
        String uuidString=getString(getColumnIndex(ReportDBSchema.ReportTable.Cols.UUID));
        String title=getString(getColumnIndex(ReportDBSchema.ReportTable.Cols.TITLE));
        long date=getLong(getColumnIndex(ReportDBSchema.ReportTable.Cols.DATE));
        int isResolved=getInt(getColumnIndex(ReportDBSchema.ReportTable.Cols.RESOLVED));

        Report report=new Report(UUID.fromString(uuidString));
        report.setmTitle(title);
        report.setmDate(new Date(date));
        report.setmResolved(isResolved!=0);
        return report;

    }
}
