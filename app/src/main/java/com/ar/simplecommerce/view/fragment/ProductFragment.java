package com.ar.simplecommerce.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ar.simplecommerce.R;
import com.ar.simplecommerce.data.cache.CacheDB;
import com.ar.simplecommerce.data.cache.CacheManager;
import com.ar.simplecommerce.helper.AppUtility;
import com.ar.simplecommerce.helper.Constant;
import com.ar.simplecommerce.helper.LinearLayoutManagerWithSmoothScroller;
import com.ar.simplecommerce.model.api.ApiResponse;
import com.ar.simplecommerce.model.api.ModelProductList;
import com.ar.simplecommerce.view.activity.MainActivity;
import com.ar.simplecommerce.view.adapter.ProductListAdapter;
import com.ar.simplecommerce.viewmodel.MainVM;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aderifaldi on 24/08/2016.
 */
@SuppressLint("ValidFragment")
public class ProductFragment extends Fragment {
    //Todo: View binding use ButterKnife
    @BindView(R.id.scrollUpButon)
    FloatingActionButton scrollUpButon;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    //Todo: Declaration variable
    private RecyclerView listProduct;

    private ProductListAdapter adapter;
    private LinearLayoutManagerWithSmoothScroller linearLayoutManager;

    private MainVM viewModel;
    private CacheDB cacheDB;
    private Activity activity;

    static String message = "Ini Product Fragment";

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        //Todo: Initialize variable
        activity = getActivity();

        viewModel = ViewModelProviders.of(this).get(MainVM.class);
        cacheDB = CacheDB.getDatabase(getActivity());

        listProduct = view.findViewById(R.id.listProduct);

        linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(activity);
        adapter = new ProductListAdapter(activity);

        //Todo: Set layout manager of RecyclerView
        listProduct.setLayoutManager(linearLayoutManager);

        //Todo: Set adapter of RecyclerView
        listProduct.setAdapter(adapter);

        //Todo: Execute method loadApiResponse
        loadApiResponse();

        //Todo: Set on item click listener of list
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModelProductList.Products itemData = adapter.getData().get(i);
                AppUtility.showToast(activity, itemData.getName());
            }
        });

        //Todo: Add scroll listener of RecyclerView
        listProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && scrollUpButon.getVisibility() == View.VISIBLE) {
                    //Hide floating button when scroll up
                    scrollUpButon.hide();
                } else if (dy < 0 && scrollUpButon.getVisibility() != View.VISIBLE) {
                    //Show floating button when scroll down
                    scrollUpButon.show();
                }
            }
        });



        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CacheDB.destroyInstance();
    }

    public void loadApiResponse() {

        viewModel.getProductList(activity, cacheDB, Constant.Cache.PRODUCT);
        viewModel.getData().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressBar.setVisibility(View.GONE);

                ModelProductList data = (ModelProductList) apiResponse.getData();

                if (data != null) {
                    if (data.getStatus().equals(Constant.Api.SUCCESS)) {

                        //Todo: Store data to list
                        storeDataToList(data);
                        //Todo: Hide progress bar
                        scrollUpButon.setVisibility(View.VISIBLE);

                    } else {
                        if (data.getMessage() != null) {
                            AppUtility.showToast(activity, data.getMessage());
                        } else {
                            AppUtility.showToastFailedGetingData(activity);
                        }
                    }
                } else {
                    AppUtility.showToastFailedGetingData(activity);
                }

            }
        });

    }

    private void storeDataToList(ModelProductList data) {
        //Todo: Clear list
        adapter.getData().clear();

        //Todo: Add data to adapter
        for (int i = 0; i < data.getProducts().size(); i++) {
            adapter.getData().add(data.getProducts().get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }

        //Todo: Notify adapter when data updated
        adapter.notifyDataSetChanged();

        //Todo: Store data to cache
        CacheManager.storeCache(cacheDB, Constant.Cache.PRODUCT, new Gson().toJson(data));
    }

    @OnClick(R.id.scrollUpButon)
    public void onViewClicked() {
        //Todo: Scroll RecyclerView to top
        listProduct.smoothScrollToPosition(0);
    }
}
