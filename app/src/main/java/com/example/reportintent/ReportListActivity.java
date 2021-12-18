package com.example.reportintent;

import androidx.fragment.app.Fragment;

public class ReportListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new ReportListFragment();
    }
}
