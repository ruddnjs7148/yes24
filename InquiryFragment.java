package com.wony.kotech.androidyes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class InquiryFragment extends Fragment {

    public InquiryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((MainActivity)getActivity()).toolbar.setTitle("고객지원");

        View v = inflater.inflate(R.layout.inquiry_fragment, container, false);
        BottomNavigationView navigation = (BottomNavigationView) v.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
}
