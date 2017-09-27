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

package org.piwigo.ui.viewmodel;

/**
 * Created by Jeff on 9/26/2017.
 */

import android.content.res.Resources;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import org.piwigo.BR;
import org.piwigo.R;
import org.piwigo.io.model.Category;
import org.piwigo.io.model.ImageInfo;
import org.piwigo.io.repository.CategoriesRepository;
import org.piwigo.ui.adapter.BindingRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;


import rx.Subscriber;
import rx.Subscription;

public class PhotosViewModel extends BaseViewModel {
}
