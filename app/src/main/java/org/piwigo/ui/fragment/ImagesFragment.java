/*
 * Copyright 2017 Phil Bayfield https://philio.me
 * Copyright 2017 Piwigo Team http://piwigo.org
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

package org.piwigo.ui.fragment;

/**
 * Created by Jeff on 9/26/2017.
 */


import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.piwigo.R;
import org.piwigo.databinding.FragmentImagesBinding;
import org.piwigo.ui.viewmodel.ImagesViewModel;

import org.piwigo.helper.CommonVars;

import javax.inject.Inject;

public class ImagesFragment extends BaseFragment {

    private static final int PHONE_MIN_WIDTH = 320;
    private static final int TABLET_MIN_WIDTH = 360;

    CommonVars comvars = CommonVars.getInstance();

    @Inject ImagesViewModel viewModel;

    FragmentImagesBinding binding;



    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false);
        binding.recycler2.setLayoutManager(new GridLayoutManager(getContext(), calculateColumnCount()));
        return binding.getRoot();

    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivityComponent().inject(this);
        bindLifecycleEvents(viewModel);
        binding.setViewModel(viewModel);
        viewModel.loadImages(comvars.getValue());
    }

    private int calculateColumnCount() {
        Configuration configuration = getResources().getConfiguration();
        int screenSize = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        boolean largeScreen = screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE;
        return (int) Math.floor(configuration.screenWidthDp / (largeScreen ? TABLET_MIN_WIDTH : PHONE_MIN_WIDTH));
    }

    //jca added
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //jca added
        return true;
    }





}
