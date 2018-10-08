package com.wony.kotech.androidyes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {

    private List<String> mTotalData = new ArrayList<String>();
    private List<String> mCurrentData = new ArrayList<String>();

    private int mCurrentPage = 1;
    private int mItemPerRow = 1;

    private boolean isLoadMore = false;

    private Handler handler = new Handler();
    private ProgressDialog mLoading;

    private ListView listView;
    LoginActivity loginActivity = new LoginActivity();

    public NoticeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((MainActivity)getActivity()).toolbar.setTitle("고객지원");

        View v = inflater.inflate(R.layout.notice_fragment, container, false);
        BottomNavigationView navigation = (BottomNavigationView) v.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        int aa = loginActivity.a;

        mLoading = new ProgressDialog(getActivity());
        mLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoading.setMessage("Loading....");

        if(aa < 5) {
            aa = 4;
        }

        for (int i = 1; i <= aa; i++) {
            mTotalData.add(""+i);
        }

        getData();

        listView = (ListView) v.findViewById(R.id.nt_listview);
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mCurrentData
        );

        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.notice_header, listView,false);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_notice:
                    replaceFragment(0);
                    return true;
                case R.id.nav_faq:
                    replaceFragment(1);
                    return true;
                case R.id.nav_inquiry:
                    replaceFragment(2);
                    return true;
            }
            return false;
        }
    };

    public void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new NoticeFragment();
                break;
            case 1:
                fragment = new FaqFragment();
                break;
            case 2:
                fragment = new InquiryFragment();
                break;
        }

        if (null != fragment) {
            //FragmentTransaction의 인스턴스는 FragmentManager 클래스로부터 가져올 수 있음.
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //FragmentTransaction의 replace() 메소드를 통해 동적으로 Fragment 교체.
            transaction.replace(R.id.main_content, fragment);
            //뒤로가기버튼 누르면 이전 Fragment를 복원 해줌.
            transaction.addToBackStack(null);
            transaction.commit();
        }
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
}
