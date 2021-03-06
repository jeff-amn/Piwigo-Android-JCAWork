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

package org.piwigo.ui.activity;

import android.accounts.Account;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
//jca imports
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.DataOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
// end jca imports

//import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.NameValuePair;
import org.apache.http.params.HttpParams;
import org.piwigo.R;
import org.piwigo.databinding.ActivityMainBinding;
import org.piwigo.databinding.DrawerHeaderBinding;
import org.piwigo.ui.Navigator;
import org.piwigo.ui.fragment.AlbumsFragment;
import org.piwigo.ui.fragment.ImagesFragment;
import org.piwigo.ui.model.User;
import org.piwigo.ui.view.MainView;
import org.piwigo.ui.viewmodel.MainViewModel;

import org.piwigo.ui.viewmodel.AlbumsViewModel;




import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

    private static int RESULT_LOAD_IMG = 1;
    private int serverResponseCode = 0;

    @Inject MainViewModel viewModel;

    private Account account;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        DrawerHeaderBinding headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.drawer_header, binding.navigationView, false);

        viewModel.setView(this);
        bindLifecycleEvents(viewModel);
        binding.setViewModel(viewModel);
        headerBinding.setViewModel(viewModel);
        binding.navigationView.addHeaderView(headerBinding.getRoot());
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            viewModel.setTitle(getString(R.string.nav_albums));
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, new AlbumsFragment())
                    .commit();
        }
    }

    @Override protected void onResume() {
        super.onResume();
        checkAccount();
        if (account == null) {
            loadAccount();
        }
    }

    public void onTouchSelected (View view){
        //jaskdlasd
    }


    @Override public void onItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_albums:

              //  FragmentManager fragmentManager = getFragmentManager();
             //   FragmentTransaction fragmentTransaction = fragmentManager
              //          .beginTransaction();

              //  frag(R.id.content, new ImagesFragment());

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content, new ImagesFragment())
                        .commit();




                break;
            case R.id.nav_upload:
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                break;

            case R.id.nav_settings:
                Toast.makeText(this, "Settings menu selected",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void checkAccount() {
        if (account != null) {
            account = accountHelper.getAccount(account.name, false);
        }
    }

    private void loadAccount() {
     //   preferencesRepository.setAccountName("abc@www.sitename.com");  // JCA - to select another account
        String name = preferencesRepository.getAccountName();
        account = accountHelper.getAccount(name, true);
        if (account == null) {
            finish();
            return;
        }
        if (!account.name.equals(name)) {
            preferencesRepository.setAccountName(account.name);
        }
        User user = accountHelper.createUser(account);
        viewModel.setUser(user);
    }


// jca added - prompts user to select image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                Toast.makeText(this, "You selected an image",
                        Toast.LENGTH_LONG).show();
                new UploadImage("file1").execute();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }



    }

    private class  UploadImage  extends AsyncTask<Void, Void,  Void> {
        Bitmap image;
        String name;
        String piwigo_url = "http://www.piwigo.org/demo";

        public UploadImage(String name) {
            // this.image = image;
            this.name = name;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // image.compress

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            //      File sourceFile = new File(name);
            try {
                // open a URL connection to the Servlet
                //  FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(piwigo_url + "/ws.php?format=json");

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                ;

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());


                //  JSONObject jsonParam = new JSONObject();
                //  jsonParam.put("method","pwg.session.login");
                //   jsonParam.put("username", "test");
                //   jsonParam.put("password", "test");

                String data = URLEncoder.encode("method", "UTF-8")
                        + "=" + URLEncoder.encode("pwg.session.login&username=test&password=test", "UTF-8");

                wr.writeBytes("method=pwg.session.login&username=test&password=test");
                //  wr.writeBytes(data);
                //  wr.writeBytes(jsonParam.toString());

                wr.flush();
                wr.close();

                // conn.connect();

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.d("response", serverResponseMessage);

                //    conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");


                DataOutputStream wr2 = new DataOutputStream(conn.getOutputStream());
                wr2.writeBytes("method=pwg.session.getstatus");
                wr2.flush();
                wr2.close();

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage2 = conn.getResponseMessage();

                Log.d("response", serverResponseMessage2);

            } catch (MalformedURLException ex) {
                Log.d("response", ex.toString());
            } catch (Exception e) {
                Log.d("response", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}
