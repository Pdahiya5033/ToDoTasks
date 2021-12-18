package com.example.reportintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class ReportPagerActivity extends AppCompatActivity {
    private static final String EXTRA_REPORT_ID="com.android.pdahiya.reportIntent.report_id";
    private ViewPager mViewPager;
    private List<Report> mReports;
    public static Intent newIntent(Context packageContext, UUID reportId){
        Intent intent=new Intent(packageContext,ReportPagerActivity.class);
        intent.putExtra(EXTRA_REPORT_ID,reportId);
        return intent;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pager);

        UUID reportId=(UUID) getIntent().getSerializableExtra(EXTRA_REPORT_ID);


        mViewPager=(ViewPager) findViewById(R.id.activity_report_pager_view_pager);
        mReports=ReportStore.get(this).getmReports();
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Report report = mReports.get(position);
                return ReportFragment.newInstance(report.getmId());
            }

            @Override
            public int getCount() {
                return mReports.size();
            }
        });
        for(int i=0;i<mReports.size();i++){
            if(mReports.get(i).getmId().equals(reportId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }




    }
}
