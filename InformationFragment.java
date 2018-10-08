package com.wony.kotech.androidyes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InformationFragment extends Fragment {

    private List<String> mTotalData = new ArrayList<String>();
    private List<String> mCurrentData = new ArrayList<String>();

    private int mCurrentPage = 1;
    private int mItemPerRow = 1;

    private boolean isLoadMore = false;

    private Handler handler = new Handler();
    private ProgressDialog mLoading;

    private ListView listView;
    LoginActivity loginActivity = new LoginActivity();

    public EditText editText, editText1;
    Calendar myCalendar = Calendar.getInstance();

    public InformationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((MainActivity)getActivity()).toolbar.setTitle("거래내역");

        View v = inflater.inflate(R.layout.fragment_information, container, false);

        editText = (EditText) v.findViewById(R.id.date_edit);
        editText1 = (EditText) v.findViewById(R.id.date_edit1);
        editText.setInputType(0); //키보드 x
        editText1.setInputType(0);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener dat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dat, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        int aa = loginActivity.a;

        mLoading = new ProgressDialog(getActivity());
        mLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoading.setMessage("Loading....");

        for (int i = 1; i <= aa; i++) {
            mTotalData.add("  "+i);
        }

        getData();

        listView = (ListView) v.findViewById(R.id.In_listview);
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mCurrentData
        );

        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.information_header, listView,false);
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int mLastFirstVisibleItem;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                isLoadMore = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);

                if(mLastFirstVisibleItem < firstVisibleItem) {
                    if(isLoadMore) {
                        mLoading.show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLoading.dismiss();
                            }
                        }, 500);
                    }
                }

                mLastFirstVisibleItem = firstVisibleItem;
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isLoadMore) {
                    //TODO 화면이 바닥에 닿을때 처리
                    isLoadMore = false;
                    getData();
                    listViewAdapter.notifyDataSetChanged();
                }
            }
        });
        return v;
    }

    private void getData() {

        int tt = 0;

        for (int i = 0; i < 20; i++) {

            tt = i + (mCurrentPage - 1 ) * 20;

            if(tt <= mTotalData.size() - 1) {
                mCurrentData.add(mTotalData.get(i + ((mCurrentPage - 1) * 20)));
            }
        }
        mCurrentPage += 1;
    }

    private void updateLabel() {
        String myFormat = "yy - MM - dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {
        String myFormat = "yy - MM - dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText1.setText(sdf.format(myCalendar.getTime()));
    }
}
