package com.ar.simplecommerce.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.simplecommerce.R;
import com.ar.simplecommerce.helper.AppUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aderifaldi on 24/08/2016.
 */
@SuppressLint("ValidFragment")
public class EmptyFragment extends Fragment {

    static String message = "Ini Empty Fragment";

    @BindView(R.id.txt_info)
    TextView txt_info;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);
        txt_info.setText(message);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.txt_info)
    public void onViewClicked() {
        AppUtility.showToast(getActivity(),message);
    }
}
