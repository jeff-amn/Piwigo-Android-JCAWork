/*
 * Copyright 2015 Phil Bayfield https://philio.me
 * Copyright 2015 Piwigo Team http://piwigo.org
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

package org.piwigo.io;

import org.piwigo.io.model.AddCategoryResponse;
import org.piwigo.io.model.CategoryListResponse;
import org.piwigo.io.model.GetImageInfoResponse;
import org.piwigo.io.model.StatusResponse;
import org.piwigo.io.model.SuccessResponse;
import org.piwigo.io.model.ImageUploadResponse;
import org.piwigo.io.model.ImageListResponse;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface RestService {

    @GET("/ws.php?method=pwg.session.getStatus")
    Observable<StatusResponse> getStatus();

    @POST("/ws.php?method=pwg.session.login")
    @FormUrlEncoded
    Observable<Response> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("/ws.php?method=pwg.session.logout")
    Observable<SuccessResponse> logout();

    @POST("/ws.php?method=pwg.categories.add")
    @FormUrlEncoded
    Observable<AddCategoryResponse> addCategory(
            @Field("name") String name,
            @Field("parent") Integer parent,
            @Field("comment") String comment,
            @Field("visible") Boolean visible,
            @Field("status") String status,
            @Field("commentable") Boolean commentable
    );

    @GET("/ws.php?method=pwg.categories.getList")
    Observable<CategoryListResponse> getCategories(@Query("cat_id") Integer categoryId);

    @GET("/ws.php?method=pwg.images.getInfo")
    Observable<GetImageInfoResponse> getImageInfo(@Query("image_id") int imageId);


    //JCA added
    @GET("/ws.php?method=pwg.categories.getImages")
    // List<ImageListResponse> getImages(@Query("cat_id") Integer categoryId);
    Observable<ImageListResponse> getImages(@Query("cat_id") int categoryId);


    @Multipart
    @POST("/ws.php?method=pwg.images.upload")
    @FormUrlEncoded
    Observable<ImageUploadResponse> uploadImage(
            @Part("image") String image,
            @Part("category") String category,
            @Part("name") String name,
            @Part("file") TypedFile file
    );

}