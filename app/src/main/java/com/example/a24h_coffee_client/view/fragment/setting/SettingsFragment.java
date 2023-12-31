package com.example.a24h_coffee_client.view.fragment.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a24h_coffee_client.constant.AppConstants;
import com.example.a24h_coffee_client.databinding.FragmentSettingsBinding;
import com.example.a24h_coffee_client.model.User;
import com.example.a24h_coffee_client.utils.FormatUtils;
import com.example.a24h_coffee_client.utils.UIUtils;
import com.example.a24h_coffee_client.view.activity.account.LoginActivity;
import com.example.a24h_coffee_client.view.activity.changepass.ChangePassActivity;
import com.example.a24h_coffee_client.view.activity.contact.ContactActivity;
import com.example.a24h_coffee_client.view.activity.updateinfor.UpdateAccountActivity;
import com.google.gson.Gson;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding mBinding;
    private SettingContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(AppConstants.KEY_USERNAME, "");
        String userJson = sharedPreferences.getString(AppConstants.KEY_USER, "");
        User user = new Gson().fromJson(userJson, User.class);
        String fullName = user.getName();
        String image = user.getImage();
        mBinding.tvNameUser.setText(fullName);
        Glide.with(view.getContext())
                        .load(image)
                                .into(mBinding.civUser);
        setListener();
    }


    private void setListener() {
        mBinding.layoutLogout.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finishAffinity();
        });
        mBinding.layoutContact.setOnClickListener(view1 -> nextContact());
        mBinding.layoutUpdateInfor.setOnClickListener(view1 -> nextUpdateInfor());
        mBinding.layoutChangePass.setOnClickListener(view1 -> nextChangePass());
    }

    public void nextContact(){
        Intent intent = new Intent(getContext(), ContactActivity.class);
        startActivity(intent);
    }
    public void nextUpdateInfor(){
        Intent intent = new Intent(getContext(), UpdateAccountActivity.class);
        startActivity(intent);
    }
    public void nextChangePass(){
        Intent intent = new Intent(getContext(), ChangePassActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        UIUtils.openLayout(mBinding.ivLoadingSettingFragment, mBinding.layoutSettingFragment, false, getContext());
        SharedPreferences sharedPreferences  = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
        User user = new Gson().fromJson(sharedPreferences.getString(AppConstants.KEY_USER,""), User.class) ;
        mBinding.tvNameUser.setText(user.getName());
        Glide.with(this).load(user.getImage()).centerCrop().into(mBinding.civUser);
        new Handler().postDelayed(() -> {
            UIUtils.openLayout(mBinding.ivLoadingSettingFragment, mBinding.layoutSettingFragment, true, getContext());
        }, 500);

    }
}