package com.nekogee.pixies;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<Photo> photoList = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private String returnedData = "00";
    private Photo pic_new;
    private String imagePath;
    private File file;
    private PicBean picBean;
    private String url;

    //PhotoAdapter adapter = new PhotoAdapter(photoList);
    PhotoAdapter adapter;
    public static final int UPLOAD = 1;
    public static final int EDIT_TEXT = 2;
    public static final int UPDATE_PIC = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        // new SwipeRefreshLayout
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh(){
                //TODO
                swipeRefreshPhotos();
            }
        };
        swipeRefresh.setOnRefreshListener(listener);

        /*
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                //可删除
                swipeRefresh.setRefreshing(true);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                swipeRefresh.setRefreshing(false);
            }
        });*/
        //首页自动刷新
        swipeRefresh.post(new Runnable(){
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
        listener.onRefresh();

        mDrawerLayout = findViewById(R.id.myDrawer);
        NavigationView navView = findViewById(R.id.nav_view);

        navView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navView.setItemIconTintList(null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setTitle("Title");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        // navView.setCheckedItem(R.id.upload);//?
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload: {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            System.out.println("open");
                            openAlbum();
                        }
                    }
                    break;
                    case R.id.history: {
                        mDrawerLayout.closeDrawers();
                    }
                    break;
                    default:
                }
                return true;

            }
        });

        initPhotos();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        adapter = new PhotoAdapter(photoList, new PhotoAdapter.onRecyclerViewItemClick() {
            @Override
            public void onItemClick(View v, int position) {
                String url = photoList.get(position).getUrl();
                if(url==null) {
                    Toast.makeText(v.getContext(),"图片链接失效啦(/ﾟДﾟ)/ ",Toast.LENGTH_SHORT).show();
                } else {
                    ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("url",photoList.get(position).getUrl());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(v.getContext(),"图片链接复制成功(●’◡’●)ﾉ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //final LinearLayoutManager lLayoutManager = new LinearLayoutManager(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

       /* public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.toolbar, menu);
            return true;
        }*/

    private void swipeRefreshPhotos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //initPhotos();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

            break;
            default:
        }
        return true;
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, UPLOAD);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case UPLOAD:
                if (resultCode == RESULT_OK) {
                    // 4.4及以上系统使用这个方法处理图片
                    handleImageOnKitKat(data);
                }
                break;
            case EDIT_TEXT:
                if(resultCode == RESULT_OK) {
                    String editText = data.getStringExtra("EditText");
                    //url可能会未及时更新
                    pic_new = new Photo(editText,imagePath,url);
                    photoList.add(0,pic_new);
                    adapter.notifyDataSetChanged();
                }
            default:
        }
    }

    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);// 根据图片路径显示图片
        Log.d("neww", "b4 upload");
        uploadImage(imagePath);
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void getEditText() {
        Intent intent = new Intent(MainActivity.this,EditTextActivity.class);
        startActivityForResult(intent,EDIT_TEXT);
    }
    private void displayImage(String imagePath) {
        if (imagePath != null) {
           getEditText();
            mDrawerLayout.closeDrawers();
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadImage(final String imagePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("neww", "run: in");
                    OkHttpClient client = new OkHttpClient();

                    File file = new File(imagePath);

                    MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                    String filename = file.getName();

                    requestBody.addFormDataPart("smfile", filename, body);

                    Request request = new Request.Builder()
                            .url("https://sm.ms/api/upload")
                            .removeHeader("User-Agent")
                            .addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                            .post(requestBody.build())
                            .build();
                    client.newBuilder().build().newCall(request).execute();

                    Log.d("neww", "run: after post");
                    String msg = request.toString();
                    Log.d("neww", msg);
                    Response response = client.newCall(request).execute();
                    Log.d("neww", "run: aa");
                    String responseData = response.body().string();
                    Log.d("neww", responseData);
                    parseJSON(responseData);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

            //Toast.makeText(this, "failed to upload image", Toast.LENGTH_SHORT).show();
    }

    private void parseJSON(String responseData) {
        try {
            Log.d("neww", responseData);
            picBean = new PicBean();
            JSONObject jsonObject = new JSONObject(responseData);
            String code = jsonObject.getString("code");
            Log.d("neww", code);
            if(code.equals("success")) {
                JSONObject data = jsonObject.getJSONObject("data");
                int size = data.getInt("size");
                String storeName = data.getString("storename");
                url = data.getString("url");
                String delete = data.getString("delete");
                picBean.setSize(size);
                picBean.setStoreName(storeName);
                picBean.setUrl(url);
                picBean.setDelete(delete);
                Log.d("neww", url);
            } else {
                String msg = jsonObject.getString("msg");
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void initPhotos() {
        for (int i = 0; i < 2; i++) {
            Photo pic1 = new Photo("海底游鱼乐，天边过雁愁。浮天沧海远，去世法舟轻。", R.drawable.pic6);
            photoList.add(pic1);
            Photo pic2 = new Photo("夏天到了，又可以吃西瓜了！",R.drawable.pic5);
            photoList.add(pic2);
            Photo pic3 = new Photo("So cute",R.drawable.pic4);
            photoList.add(pic3);
            Photo pic4 = new Photo("hunter-Bjork", R.drawable.pic3);
            photoList.add(pic4);
            Photo pic5 = new Photo("经过大海时，眼望着海水与天相接的地方，蓝蓝的海水就荡漾在心里，心跳缓缓的，悠悠的，有一种说不出的舒畅，心中就像没有云彩的天空一片，宽了、静了，渐渐的觉得人活着是多么好。", R.drawable.pic6);
            photoList.add(pic5);
            Photo pic6 = new Photo("Life is Strange!",R.drawable.pic2);
            photoList.add(pic6);
            Photo pic7 = new Photo("大海呀大海",R.drawable.pic1);
            photoList.add(pic7);
        }
    }
}
