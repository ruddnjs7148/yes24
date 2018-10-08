package com.wony.kotech.androidyes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((MainActivity)getActivity()).toolbar.setTitle("YES24");

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Button button = (Button) v.findViewById(R.id.btn_wallet);
        button.setOnClickListener(this);
        Button button1 = (Button) v.findViewById(R.id.btn_transfer);
        button1.setOnClickListener(this);
        Button button2 = (Button) v.findViewById(R.id.btn_information);
        button2.setOnClickListener(this);
        Button button3 = (Button) v.findViewById(R.id.btn_customer);
        button3.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wallet:
                replaceFragment(0);
                break;
            case R.id.btn_transfer:
                replaceFragment(1);
                break;
            case R.id.btn_information:
                replaceFragment(2);
                break;
            case R.id.btn_customer:
                replaceFragment(3);
                break;
        }
    }

    public void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new ProfileFragment();
                break;
            case 1:
                fragment = new TransferFragment();
                break;
            case 2:
                fragment = new InformationFragment();
                break;
            case 3:
                fragment = new NoticeFragment();
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
