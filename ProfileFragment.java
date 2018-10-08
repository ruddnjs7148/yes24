package com.wony.kotech.androidyes;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private List<String> mTotalData = new ArrayList<String>();
    private List<String> mCurrentData = new ArrayList<String>();

    private int mCurrentPage = 1;
    private int mItemPerRow = 1;

    private boolean isLoadMore = false;

    private Handler handler = new Handler();
    private ProgressDialog mLoading;

    private ListView listView;
    LoginActivity loginActivity = new LoginActivity();

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((MainActivity)getActivity()).toolbar.setTitle("나의 지갑");
//        ((LoginActivity)getActivity()).getLayoutInflater().inflate(R.layout.listview_header, listView);

        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        int aa = loginActivity.a;

        if(aa<5) {
            aa=4;
        }

        mLoading = new ProgressDialog(getActivity());
        mLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLoading.setMessage("Loading....");

        for (int i = 1; i <= aa; i++) {
            mTotalData.add(" "+i);
        }

        getData();

        listView = (ListView) v.findViewById(R.id.Pr_listview);
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mCurrentData
        );

//        ArrayAdapter listViewAdapter = new ArrayAdapter(get Activity(), android.R.layout.simple_list_item_1, mTotalData);

//        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.listview_header, listView,false);
//        final View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header, null, false);
//        listView.addHeaderView(header);
//        .FixedViewInfo(header);

//        ViewGroup header = inflater.inflate(R.layout.listview_header, null);
//        ListView = (ListView)findViewById(R.id.memo_list);
//        ListView.addFooterView(memo_AddView );




//        LayoutInflater inflater1 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View header = (View) inflater1.inflate(R.layout.listview_header, null);
////        ViewGroup header = inflater1.inflate(R.layout.listview_header, null);
//        listView.addHeaderView(header);
//        listView.addHeaderView(header);
//        listView.addHeaderView(header, null, false);

//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        memo_AddView = inflater.inflate(R.layout.listview_header, null);
//        ListView = (ListView)findViewById(R.id.memo_list);
//        ListView.addFooterView(memo_AddView );

        listView.setAdapter(listViewAdapter);
//
//        LayoutInflater inflater1 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View header = (View) inflater1.inflate(R.layout.listview_header, null);
//        listView.addHeaderView(header);





        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        LinearLayout listHeaderView = (LinearLayout)inflater2.inflate(com.wony.kotech.androidyes.R.layout.listview_header, null);

//        ListView lv = getListView()   ;
        listView.setTextFilterEnabled(false);

        listView.addHeaderView(listHeaderView);








        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int mLastFirstVisibleItem;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
 //                getData();
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                isLoadMore = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);

                Log.d("inIF", "firstVisible" + firstVisibleItem + "," + totalItemCount + "," + isLoadMore + "," + mLastFirstVisibleItem);

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
                    Log.d("SCROLLING DOWN","TRUE");
                }

                mLastFirstVisibleItem = firstVisibleItem; //mLastFirstVisibleItem = 0, firstVisibleItem = 1
            } //mLastFirstVisibleItem = 1

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isLoadMore) {
                    //TODO 화면이 바닥에 닿을때 처리
                    isLoadMore = false;
                    getData();
                    listViewAdapter.notifyDataSetChanged();
//                    isLoadMore = true;
                }
            }
        });

/*        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {




                getData();
                listViewAdapter.notifyDataSetChanged();

//                getAdapter();
//                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                if((totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount)) {
//                    getData();
//                    listViewAdapter.notifyDataSetChanged();
//                }

//                getData();
//                listViewAdapter.notifyDataSetChanged();

                int last = totalItemCount - visibleItemCount;

                if (((firstVisibleItem >= last)) && (totalItemCount != 0) && isLoadMore) {

                    isLoadMore = true;
                    mLoading.show();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            isLoadMore = false;
                            mLoading.dismiss();
                        }
                    }, 1000);

//                    getData();
//                    listViewAdapter.notifyDataSetChanged();
                }

//                int lastInScreen = firstVisibleItem + visibleItemCount;
//
//                if ((lastInScreen == totalItemCount) && !isLoadMore && (firstVisibleItem != 0)) {
//                    isLoadMore = true;
//                    mLoading.show();
//                    getData();
//                    listViewAdapter.notifyDataSetChanged();
//
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            isLoadMore = false;
//                            mLoading.dismiss();
//                        }
//                    }, 500);
//
//                }
            }

        });*/

        return v;
    }

    //getData -> 스크롤 할 때 load more 시킬 때 함수.
    //mTotalData -> 리스트 안의 총 데이터 갯수
    private void getData() {

//        if (mItemPerRow * mCurrentPage > mTotalData.size()) {
//            return;
//        }

        int tt = 0;
        //mCurrentPage = 1, mTotalData = size = 300, mCurrentData = size = 0
        for (int i = 0; i < 20; i++) {
            //mCurrentData = size => 1씩 증가
            tt = i + (mCurrentPage - 1 ) * 20;
            Log.d("tt" , String.valueOf(tt));
            if(tt <= mTotalData.size() - 1) {
                mCurrentData.add(mTotalData.get(i + ((mCurrentPage - 1) * 20)));
            }
        }
        mCurrentPage += 1;
    }
}