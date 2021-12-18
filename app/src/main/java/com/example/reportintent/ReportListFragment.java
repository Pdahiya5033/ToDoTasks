package com.example.reportintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportListFragment extends Fragment {
    private RecyclerView mReportRecyclerView;
    private ReportAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE="subtitle";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_report_list,container,false);
        mReportRecyclerView=(RecyclerView) view.findViewById(R.id.report_recycler_view);
        mReportRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState!=null){
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    @Override
    public void onSaveInstanceState(Bundle b){
        super.onSaveInstanceState(b);
        b.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_report_list,menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_new_report:
                Report report=new Report();
                ReportStore.get(getActivity()).addReport(report);
                Intent intent=ReportPagerActivity
                        .newIntent(getActivity(),report.getmId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateSubtitle(){
        ReportStore reportStore=ReportStore.get(getActivity());
        int reportCount=reportStore.getmReports().size();
        String subtitle=getString(R.string.subtitle_format,reportCount+" ");

        if(!mSubtitleVisible){
            subtitle=null;
        }

        AppCompatActivity activity=(AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    private void updateUI(){
        ReportStore reportStore=ReportStore.get(getActivity());
        List<Report> reports=reportStore.getmReports();
        if(mAdapter==null) {
            mAdapter = new ReportAdapter(reports);
            mReportRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setReports(reports);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }
    private class ReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Report mReport;
        public TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mResolvedCheckBox;

        public ReportHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView=(TextView) itemView.findViewById(R.id.list_item_report_title_text_view);
            mDateTextView=(TextView) itemView.findViewById(R.id.list_item_report_date_text_view);
            mResolvedCheckBox=(CheckBox) itemView.findViewById(R.id.list_report_resolved_checkBox);
        }
        @Override
        public void onClick(View v){
            Intent intent=ReportPagerActivity.newIntent(getActivity(),mReport.getmId());
            startActivity(intent);
        }
        private void bindReport(Report report){
            mReport=report;
            mTitleTextView.setText(mReport.getmTitle());
            mDateTextView.setText(mReport.getmDate().toString());
            mResolvedCheckBox.setChecked(mReport.getmResolved());
        }

    }

    private class ReportAdapter extends RecyclerView.Adapter<ReportHolder>{
        private List<Report> mReports;
        public ReportAdapter(List<Report> reports){
            mReports=reports;
        }
        @Override
        public ReportHolder onCreateViewHolder(ViewGroup parent,int viewType){
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.list_item_report,parent,false);
            return new ReportHolder(view);
        }
        @Override
        public void onBindViewHolder(ReportHolder holder,int position){
            Report report=mReports.get(position);
            holder.bindReport(report);
        }
        @Override
        public int getItemCount(){
            return mReports.size();
        }
        public void setReports(List<Report> reports){
            mReports=reports;

        }
    }
}














