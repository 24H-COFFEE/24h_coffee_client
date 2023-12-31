package com.example.a24h_coffee_client.view.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a24h_coffee_client.R;
import com.example.a24h_coffee_client.adapter.AdapterBanner;
import com.example.a24h_coffee_client.adapter.AdapterCategory;
import com.example.a24h_coffee_client.adapter.AdapterProduct;
import com.example.a24h_coffee_client.constant.AppConstants;
import com.example.a24h_coffee_client.databinding.FragmentHomeBinding;
import com.example.a24h_coffee_client.model.Banner;
import com.example.a24h_coffee_client.model.Category;
import com.example.a24h_coffee_client.model.Product;
import com.example.a24h_coffee_client.model.User;
import com.example.a24h_coffee_client.utils.DepthPageTransformer;
import com.example.a24h_coffee_client.utils.UIUtils;
import com.example.a24h_coffee_client.view.activity.SplashActivity;
import com.example.a24h_coffee_client.view.activity.account.LoginActivity;
import com.example.a24h_coffee_client.view.activity.search.SearchActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View {
    private FragmentHomeBinding mBinding;
    private HomeContract.Presenter mPresenter;
    private List<Product> mProductList;
    private AdapterProduct adapterProduct;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mPresenter = new HomePresenter(this);
        onClick();
        return mBinding.getRoot();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void onClick() {
        mBinding.etSearch.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
            return false;
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        UIUtils.openLayout(mBinding.ivLoadingHomeFragment, mBinding.layoutHomeFragment, false, getContext());
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getListBanner();
        mPresenter.getListCategories();
        mPresenter.getListProduct();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString(AppConstants.KEY_USER, "");
        User user = new Gson().fromJson(userJson, User.class);
        String image = user.getImage();
        Glide.with(view.getContext())
                .load(image)
                .into(mBinding.civUserHome);
    }

    @Override
    public void onListBanner(List<Banner> list) {
        AdapterBanner adapterBanner = new AdapterBanner(list);
        mBinding.vpSlideImage.setAdapter(adapterBanner);
        mPresenter.autoNextBanner(mBinding.vpSlideImage, list);
        mBinding.circleIndicator3.setViewPager(mBinding.vpSlideImage);
        mBinding.vpSlideImage.setPageTransformer(new DepthPageTransformer());
    }

    @Override
    public void onListCategory(List<Category> categories) {
        AdapterCategory adapterCategory = new AdapterCategory(categories, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rcvProductCategory.setLayoutManager(layoutManager);
        mBinding.rcvProductCategory.setAdapter(adapterCategory);
    }

    @Override
    public void onListProduct(List<Product> products) {
        adapterProduct = new AdapterProduct(products);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mBinding.rcvProduct.setLayoutManager(layoutManager);
        mBinding.rcvProduct.setAdapter(adapterProduct);
        mBinding.rcvProduct.setNestedScrollingEnabled(false);
        mProductList = products;
        new Handler().postDelayed(() -> {
            UIUtils.openLayout(mBinding.ivLoadingHomeFragment, mBinding.layoutHomeFragment, true, getContext());
        }, 500);

    }

    @Override
    public void onItemClickListener(int id) {
        List<Product> productList = new ArrayList<>();
        if (id == 0){
            adapterProduct.setList(mProductList);
        }else {
            for (Product product: mProductList) {
                if (product.getCategoryID() == id){
                    productList.add(product);
                }
            }

            adapterProduct.setList(productList);
        }
    }
}