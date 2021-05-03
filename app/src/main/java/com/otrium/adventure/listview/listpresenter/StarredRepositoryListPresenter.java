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
import com.otrium.adventure.listview.component.StarredRepositoryListContract;
import com.otrium.adventure.model.ProfileViewModel;
import com.otrium.adventure.model.StarredRepositoryViewRepository;

import java.util.List;

public final class StarredRepositoryListPresenter implements StarredRepositoryListContract.Presenter {

    private StarredRepositoryListContract.View view;
    private final StarredRepositoryListContract.OnResponseCallback callback = new StarredRepositoryListContract.OnResponseCallback() {
        @Override
        public void onResponse(List<ProfileViewModel> profileViewModels) {
            view.showStarredRepositoryList(profileViewModels);
            view.hideProgress();
        }

        @Override
        public void onError(String errMsg) {
            view.hideProgress();
            view.showLoadingError(errMsg);
        }
    };
    private StarredRepositoryViewRepository mclient;

    public StarredRepositoryListPresenter(StarredRepositoryListContract.View view, StarredRepositoryViewRepository client) {
        this.view = view;
        mclient = client;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void loadStarredRepositoryList(Response<UsersQuery.Data> response) {
        view.showProgress();
        mclient.loadStarredRepositoryList(callback, response);
    }
}
