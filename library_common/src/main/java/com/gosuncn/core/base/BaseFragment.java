package com.gosuncn.core.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gosuncn.core.utils.L;


public abstract class BaseFragment extends Fragment {

    public void showLoadingDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoadingDialog();
        }

    }

    public void cancelLoadingDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).cancelLoadingDialog();
        }
    }

    public void showLongToast(String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLongToast(msg);
        }
    }

    public void showLongToast(int resId) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLongToast(resId);
        }
    }

    public void showShortToast(String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showShortToast(msg);
        }
    }

    public void showShortToast(int resId) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showShortToast(resId);
        }
    }

    @Override
    public void onAttach(Context context) {
        L.e("fragment_lifecycle", getClass() + "   onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.e("fragment_lifecycle", getClass() + "   onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.e("fragment_lifecycle", getClass() + "   onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        L.e("fragment_lifecycle", getClass() + "   onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        L.e("fragment_lifecycle", getClass() + "   onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        L.e("fragment_lifecycle", getClass() + "   onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        L.e("fragment_lifecycle", getClass() + "   onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        L.e("fragment_lifecycle", getClass() + "   onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        L.e("fragment_lifecycle", getClass() + "   onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        L.e("fragment_lifecycle", getClass() + "   onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        L.e("fragment_lifecycle", getClass() + "   onDetach");
        super.onDetach();
    }
}
