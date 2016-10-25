package lic.swifter.demo.lazy.fragment;
/*
 * Copyright (C) 2015, Lee-swifter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by cheng on 2016/10/25.
 */

import android.util.Log;
import android.widget.TextView;

import lic.swifter.demo.lazy.R;

public class ThirdFragment extends LazyFragment {

    @Override
    void initialize() {
        TextView title = $(content, R.id.fragment_main_title);
        TextView desc = $(content, R.id.fragment_main_desc);

        title.setText(R.string.first_fragment);
        desc.setText(R.string.initialized);

        Log.i("lazy_load", "ThirdFragment initialized.");
    }

    @Override
    int setContentView() {
        return R.layout.fragment_main;
    }
}
