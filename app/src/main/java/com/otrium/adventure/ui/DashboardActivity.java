/*
 * Copyright (c) 2021. Otrium (D.J.Eranda). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package com.otrium.adventure.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.otrium.adventure.R;
import com.otrium.adventure.UsersQuery;
import com.otrium.adventure.listview.ProfileListAdapter;
import com.otrium.adventure.listview.component.ProfileListContract;
import com.otrium.adventure.listview.listpresenter.ProfileListPresenter;
import com.otrium.adventure.listview.StarredRepositoryListAdapter;
import com.otrium.adventure.listview.component.StarredRepositoryListContract;
import com.otrium.adventure.listview.listpresenter.StarredRepositoryListPresenter;
import com.otrium.adventure.listview.TopRepositoryListAdapter;
import com.otrium.adventure.listview.component.TopRepositoryListContract;
import com.otrium.adventure.listview.listpresenter.TopRepositoryListPresenter;
import com.otrium.adventure.model.ProfileSummaryView;
import com.otrium.adventure.model.ProfileViewClient;
import com.otrium.adventure.model.ProfileViewModel;
import com.otrium.adventure.model.StarredRepositoryViewClient;
import com.otrium.adventure.model.TopRepositoryViewClient;
import com.otrium.adventure.presenters.ProfileSummaryViewPresenter;
import com.otrium.adventure.presenters.ProfileSummaryViewPresenterImpl;
import com.otrium.adventure.util.ApiClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements ProfileSummaryView, ProfileListContract.View, TopRepositoryListContract.View, StarredRepositoryListContract.View {

    private ProfileSummaryViewPresenter summaryViewPresenter;

    private TextView textProfileName, textViewUserName, textViewEmail, textViewFollowers, textViewFollowings;

    private ImageView imageView;

    private RecyclerView topPinnedRecyclerView;

    private RecyclerView topRepositoriesRecyclerView;

    private RecyclerView starredRepositoriesRecyclerView;

    private ProfileListAdapter profileListAdapter;

    private TopRepositoryListAdapter topRepositoryListAdapter;

    private StarredRepositoryListAdapter starredRepositoryListAdapter;

    //private SwipeRefreshLayout swipeLayout;

    private ProfileListContract.Presenter topPinnedPresenter;

    private TopRepositoryListContract.Presenter topRepositoryPresenter;

    private StarredRepositoryListContract.Presenter starredRepositoryPresenter;
    private final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getUsers();
        }
    };

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);
        initializeView();
        summaryViewPresenter = new ProfileSummaryViewPresenterImpl(this);
        getUsers();
    }

    private void initializeView() {
        textProfileName = findViewById(R.id.textProfileName);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewFollowers = findViewById(R.id.textViewFollowers);
        textViewFollowings = findViewById(R.id.textViewFollowings);


        /**Pinned Repository List**/
        topPinnedPresenter = new ProfileListPresenter(DashboardActivity.this, new ProfileViewClient());

        topRepositoryPresenter = new TopRepositoryListPresenter(DashboardActivity.this, new TopRepositoryViewClient());

        starredRepositoryPresenter = new StarredRepositoryListPresenter(DashboardActivity.this, new StarredRepositoryViewClient());

        topPinnedRecyclerView = (RecyclerView) findViewById(R.id.topPinnedRecyclerView); // list
        topPinnedRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // for vertical liner list


        topRepositoriesRecyclerView = (RecyclerView) findViewById(R.id.topRepositoriesRecyclerView);
        topRepositoriesRecyclerView.setHasFixedSize(true);
        topRepositoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        starredRepositoriesRecyclerView = (RecyclerView) findViewById(R.id.starredRepositoriesRecyclerView);
        starredRepositoriesRecyclerView.setHasFixedSize(true);
        starredRepositoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        profileListAdapter = new ProfileListAdapter(this);
        topPinnedRecyclerView.setAdapter(profileListAdapter);

        topRepositoryListAdapter = new TopRepositoryListAdapter(this);
        topRepositoriesRecyclerView.setAdapter(topRepositoryListAdapter);

        starredRepositoryListAdapter = new StarredRepositoryListAdapter(this);
        starredRepositoriesRecyclerView.setAdapter(starredRepositoryListAdapter);
    }

    @Override
    public void showProfileSummaryView(Response<UsersQuery.Data> response) {
        textProfileName.setText(response.getData().user().name());
        textViewUserName.setText(response.getData().user().login());
        textViewEmail.setText(response.getData().user().email());
        textViewFollowers.setText(response.getData().user().followers().totalCount() + " followers");
        textViewFollowings.setText(response.getData().user().following().totalCount() + " following");

        imageView = findViewById(R.id.imageView);
        String url = response.getData().user().avatarUrl().toString();
        loadImage(url);
    }

    private void getUsers() {
        ApiClient.getmApolloClient()
                .query(UsersQuery.builder().build())
                .enqueue(new ApolloCall.Callback<UsersQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<UsersQuery.Data> response) {
                        DashboardActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                summaryViewPresenter.profileSummaryData(response);

                                topPinnedPresenter.loadPinnedRepositoryList(response);

                                topRepositoryPresenter.loadTopRepositoryList(response);

                                starredRepositoryPresenter.loadStarredRepositoryList(response);
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadImage(String url) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    // for future, to show progress
    @Override
    public void showProgress() {
        //swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        //swipeLayout.setRefreshing(false);
    }

    /**toggle the visibility of empty textview or list
    display list only when response it not empty**/
    private void showORHideListView(boolean flag) {
        if (flag) {
            topPinnedRecyclerView.setVisibility(View.VISIBLE);
            topRepositoriesRecyclerView.setVisibility(View.VISIBLE);
            starredRepositoriesRecyclerView.setVisibility(View.VISIBLE);
        } else {
            topPinnedRecyclerView.setVisibility(View.GONE);
            topRepositoriesRecyclerView.setVisibility(View.GONE);
            starredRepositoriesRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTopPinnedList(List<ProfileViewModel> profileViewModels) {
        if (!profileViewModels.isEmpty()) {
            profileListAdapter.setList(profileViewModels);
            showORHideListView(true);
        }
    }

    @Override
    public void showTopRepositoryList(List<ProfileViewModel> profileViewModels) {
        if (!profileViewModels.isEmpty()) {
            topRepositoryListAdapter.setList(profileViewModels);
            showORHideListView(true);
        }
    }


    @Override
    public void showStarredRepositoryList(List<ProfileViewModel> profileViewModels) {
        if (!profileViewModels.isEmpty()) {
            starredRepositoryListAdapter.setList(profileViewModels);
            showORHideListView(true);
        }
    }

    @Override
    public void showLoadingError(String errMsg) {
        hideProgressAndShowErr(errMsg);
        showORHideListView(false);
    }

    private void hideProgressAndShowErr(String msg) {
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        showORHideListView(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topPinnedPresenter.dropView();
        topRepositoryPresenter.dropView();
        starredRepositoryPresenter.dropView();
    }
}
