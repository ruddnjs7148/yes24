package com.wony.kotech.androidyes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransferFragment extends Fragment {

    public TransferFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ((MainActivity)getActivity()).toolbar.setTitle("송금하기");

        View v = inflater.inflate(R.layout.fragment_transfer, container, false);

        return v;
    }
}
