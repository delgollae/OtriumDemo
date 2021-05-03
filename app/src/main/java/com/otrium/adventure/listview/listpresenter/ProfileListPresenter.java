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
package com.otrium.adventure.listview.listpresenter;

import com.apollographql.apollo.api.Response;
import com.otrium.adventure.UsersQuery;
import com.otrium.adventure.listview.component.ProfileListContract;
import com.otrium.adventure.model.ProfileViewModel;
import com.otrium.adventure.model.ProfileViewRepository;

import java.util.List;

public final class ProfileListPresenter implements ProfileListContract.Presenter {

    private ProfileListContract.View view;
    private final ProfileListContract.OnResponseCallback callback = new ProfileListContract.OnResponseCallback() {
        @Override
        public void onResponse(List<ProfileViewModel> profileViewModels) {
            view.showTopPinnedList(profileViewModels);
            view.hideProgress();
        }

        @Override
        public void onError(String errMsg) {
            view.hideProgress();
            view.showLoadingError(errMsg);
        }
    };
    private ProfileViewRepository mclient;

    public ProfileListPresenter(ProfileListContract.View view, ProfileViewRepository client) {
        this.view = view;
        mclient = client;
    }

    @Override
    public void dropView() {
        view = null;
    }


    @Override
    public void loadPinnedRepositoryList(Response<UsersQuery.Data> response) {
        view.showProgress();
        mclient.showTopPinnedList(callback, response);
        // required for espresso UI testing
    }
}
