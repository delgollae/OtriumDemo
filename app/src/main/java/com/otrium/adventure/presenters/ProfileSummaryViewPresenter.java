package com.otrium.adventure.presenters;

import com.apollographql.apollo.api.Response;
import com.otrium.adventure.UsersQuery;

public interface ProfileSummaryViewPresenter {
    void profileSummaryData(Response<UsersQuery.Data> response);
}
