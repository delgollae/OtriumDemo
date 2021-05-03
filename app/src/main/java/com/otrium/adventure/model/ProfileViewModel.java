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


public class ProfileViewModel {
    private String loginId;
    private String repoUserName;
    private String description;
    private String avatarUrl;
    private Integer starredCount;
    private String language;
    private String languageColor;

    public ProfileViewModel() {
    }

    /**
     * @param loginId
     * @param repoUserName
     * @param description
     * @param avatarUrl
     * @param starredCount
     * @param language
     * @param languageColor
     */
    public ProfileViewModel(String loginId, String repoUserName, String description, String avatarUrl, Integer starredCount, String language, String languageColor) {
        this.loginId = loginId;
        this.repoUserName = repoUserName;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.starredCount = starredCount;
        this.language = language;
        this.languageColor = languageColor;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getRepoUserNameName() {
        return repoUserName;
    }

    public void setRepoName(String name) {
        this.repoUserName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getStarredCount() {
        return starredCount;
    }

    public void setStarredCount(Integer starredCount) {
        this.starredCount = starredCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    @Override
    public String toString() {
        return "LoginId : '" + loginId + '\'' + " name : '" + repoUserName + '\'';
    }
}
