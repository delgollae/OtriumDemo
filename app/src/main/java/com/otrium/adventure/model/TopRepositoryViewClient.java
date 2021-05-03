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

package com.otrium.adventure.model;

import android.os.Handler;

import com.apollographql.apollo.api.Response;
import com.otrium.adventure.UsersQuery;
import com.otrium.adventure.listview.component.TopRepositoryListContract;

import java.util.ArrayList;


/**
 * Precess GRAPHQL data
 * */
public final class TopRepositoryViewClient implements TopRepositoryViewRepository {
    public void loadTopRepositoryList(final TopRepositoryListContract.OnResponseCallback callback, Response<UsersQuery.Data> response) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<ProfileViewModel> profileViewModels = new ArrayList<>();
                try {
                    Integer dataCount = response.getData().user().topRepositories().nodes().size();
                    System.out.println("TOP Repository : " + dataCount);
                    for (Integer i = 0; i < dataCount; i++) {
                        String loginId = response.getData().user().topRepositories().nodes().get(i).owner().login();
                        String repoUserName = response.getData().user().topRepositories().nodes().get(i).name();
                        String description = response.getData().user().topRepositories().nodes().get(i).description();
                        String avatarUrl = response.getData().user().topRepositories().nodes().get(i).owner().avatarUrl().toString();
                        Integer starredCount = response.getData().user().topRepositories().nodes().get(i).stargazerCount();
                        String language = "";
                        String languageColor = "";
                        if(response.getData().user().topRepositories().nodes().get(i).primaryLanguage() != null) {
                            language = response.getData().user().topRepositories().nodes().get(i).primaryLanguage().name();
                            languageColor = response.getData().user().topRepositories().nodes().get(i).primaryLanguage().color();
                        }

                        profileViewModels.add(new ProfileViewModel(loginId, repoUserName, description, avatarUrl, starredCount, language, languageColor));
                    }
                    callback.onResponse(profileViewModels);
                } catch (Exception e) {
                    System.out.println("TOP Repository Exception : " + e);
                    e.printStackTrace();
                    callback.onError("Error from network");
                }
            }
        }, 0);
    }

}
