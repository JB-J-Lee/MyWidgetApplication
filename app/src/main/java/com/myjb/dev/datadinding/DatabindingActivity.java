/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myjb.dev.datadinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.myjb.dev.R;
import com.myjb.dev.databinding.ABindingBinding;
import com.myjb.dev.databinding.ItemDatabindingListBinding;

import java.util.Arrays;

/**
 * https://developer.android.com/topic/libraries/data-binding/
 */
public class DatabindingActivity extends AppCompatActivity {

    ABindingBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.a_binding);
        binding.databindingList.setAdapter(new testAdapter(this, R.layout.item_databinding_list, R.id.text, getResources().getStringArray(R.array.cheese)));
    }

    class testAdapter extends ArrayAdapter<String> {

        LayoutInflater inflater;

        public testAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        public testAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
            super(context, resource, 0, Arrays.asList(objects));
            inflater = LayoutInflater.from(getApplicationContext());

        }

        public testAdapter(@NonNull Context context, @LayoutRes int resource,
                           @IdRes int textViewResourceId, @NonNull String[] objects) {
            super(context, resource, textViewResourceId, Arrays.asList(objects));
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ItemDatabindingListBinding binding;

            if (convertView == null) {
                binding = DataBindingUtil.inflate(inflater, R.layout.item_databinding_list, parent, false);
                convertView = binding.getRoot();
            } else {
                binding = (ItemDatabindingListBinding) convertView.getTag();
            }

            convertView.setTag(binding);

            DatabindingItem items = new DatabindingItem();
            items.text.set(getItem(position));
            items.img.set(getApplicationContext().getResources().getDrawable(R.drawable.ic_appwidget));
            binding.setItem(items);

            return convertView;
        }
    }
}