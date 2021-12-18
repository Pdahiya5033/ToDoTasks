package com.example.reportintent;

import java.util.Date;
import java.util.UUID;

public class Report {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mResolved;

    public Date getmDate(){
        return mDate;
    }
    public void setmDate(Date mDate){
        this.mDate=mDate;
    }
    public boolean getmResolved(){
        return mResolved;
    }
    public void setmResolved(boolean mResolved){
        this.mResolved=mResolved;
    }
    public Report(){
//        mId=UUID.randomUUID();
//        mDate=new Date();
        this(UUID.randomUUID());
    }
    public Report(UUID id){
        mId=id;
        mDate=new Date();
    }
    public UUID getmId() {
        return mId;
    }
    public String getmTitle(){
        return mTitle;
    }
    public void setmTitle(String title){
        mTitle=title;
    }
}
