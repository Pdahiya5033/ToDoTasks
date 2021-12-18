package com.example.reportintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class ReportFragment extends Fragment {
    private static final String ARG_REPORT_ID="report_id";
    private static final String DIALOG_DATE="dialog_date";
    private static final int REQUEST_DATE=0;
    private Report mReport;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mResolvedCheckBox;
    public static ReportFragment newInstance(UUID reportId){
        Bundle args=new Bundle();
        args.putSerializable(ARG_REPORT_ID,reportId);
        ReportFragment fragment=new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        UUID reportId=(UUID) getActivity().getIntent().getSerializableExtra(ReportActivity.EXTRA_REPORT_ID);
        // for removing fragment dependency on activity used below code
         UUID reportId=(UUID) getArguments().getSerializable(ARG_REPORT_ID);
       mReport=ReportStore.get(getActivity()).getReport(reportId);

    }
    @Override
    public void onPause(){
        super.onPause();
        ReportStore.get(getActivity()).updateReport(mReport);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_report,container,false);
        mTitleField=(EditText) v.findViewById(R.id.report_title);
        mTitleField.setText(mReport.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mReport.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton=(Button) v.findViewById(R.id.report_date);
        mDateButton.setText(mReport.getmDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager=getFragmentManager();
//                DatePickerFragment dialog=new DatePickerFragment();
                DatePickerFragment dialog=DatePickerFragment.newInstance(mReport.getmDate());
                dialog.setTargetFragment(ReportFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mResolvedCheckBox=(CheckBox) v.findViewById(R.id.report_solved);
        mResolvedCheckBox.setChecked(mReport.getmResolved());
        mResolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mReport.setmResolved(isChecked);
            }
        });
        return v;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_DATE){
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mReport.setmDate(date);
            mDateButton.setText(mReport.getmDate().toString());
        }
    }






}
