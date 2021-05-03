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

package com.otrium.adventure.listview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.otrium.adventure.R;
import com.otrium.adventure.model.ProfileViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopRepositoryListAdapter extends RecyclerView.Adapter<TopRepositoryListAdapter.TopPinnedHolder> {

    private final Context context;
    private final List<ProfileViewModel> profileViewModel = new ArrayList<>();

    public TopRepositoryListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TopPinnedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_pinned_list_raw, parent, false);
        return new TopPinnedHolder(view);
    }

    @Override
    public void onBindViewHolder(TopPinnedHolder holder, final int position) {
        holder.textProfileName.setText(profileViewModel.get(position).getLoginId());
        holder.textRepositoryOwner.setText(profileViewModel.get(position).getRepoUserNameName());
        holder.textRepositoryDescription.setText(profileViewModel.get(position).getDescription());
        holder.textLanguage.setText(profileViewModel.get(position).getLanguage());
        holder.textLanguageColor.setCardBackgroundColor(Color.parseColor(profileViewModel.get(position).getLanguageColor()));

        String url = profileViewModel.get(position).getAvatarUrl();
        Picasso.get()
                .load(url)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, profileViewModel.get(position).getRepoUserNameName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setList(List<ProfileViewModel> list) {
        profileViewModel.clear();
        profileViewModel.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return profileViewModel.size();
    }

    public static class TopPinnedHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView textLanguageColor;
        TextView textProfileName, textRepositoryOwner, textRepositoryDescription, textViewStars, textLanguage;

        public TopPinnedHolder(View v) {
            super(v);
            textProfileName = (TextView) v.findViewById(R.id.textProfileName);
            textRepositoryOwner = (TextView) v.findViewById(R.id.textRepositoryOwner);
            textRepositoryDescription = (TextView) v.findViewById(R.id.textRepositoryDescription);
            textViewStars = (TextView) v.findViewById(R.id.textViewStars);
            textLanguage = (TextView) v.findViewById(R.id.textLanguage);

            imageView = (ImageView) v.findViewById(R.id.imageView);
            textProfileName = (TextView) v.findViewById(R.id.textProfileName);
            textLanguageColor= (CardView) v.findViewById(R.id.textLanguageColor);
        }
    }
}
