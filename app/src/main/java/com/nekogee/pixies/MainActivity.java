package com.nekogee.pixies;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Photo> photoList = new ArrayList<>();
    private DrawerLayout mDrawerLayout;

    public static final int UPLOAD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.myDrawer);
        NavigationView navView = findViewById(R.id.nav_view);

        navView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navView.setItemIconTintList(null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Title");
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
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
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
        //final LinearLayoutManager lLayoutManager = new LinearLayoutManager(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        PhotoAdapter adapter = new PhotoAdapter(photoList);
        recyclerView.setAdapter(adapter);
    }

       /* public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.toolbar, menu);
            return true;
        }*/

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);
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
            switch(requestCode) {
                case 1:
                    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openAlbum();
                    } else {
                        Toast.makeText(this, "You denied the permission",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
    }

    private void initPhotos() {
        for (int i = 0; i < 2; i++) {
            Photo pic1 = new Photo("ooodghdghdfhdhfdghdfgdfgdfgdfgdfgfdgdfgdfgdfgfddfgdfgdffhdfhdfhdfhd", R.drawable.pic6);
            photoList.add(pic1);
            Photo pic2 = new Photo("ooodghdgdfghdfhdfhd", R.drawable.pic3);
            photoList.add(pic2);
            Photo pic3 = new Photo("ooodghdghdfhdfhdfh", R.drawable.pic1);
            photoList.add(pic3);
            Photo pic4 = new Photo("ooodghdghdfhdfhdfsdfdsfdgfdhdfhdfhd", R.drawable.pic2);
            photoList.add(pic4);
            Photo pic5 = new Photo("ooodghdghddsfsdffsdfdfhfdsgrehserrefsgfhtrdsgfdsfdsfdsfsdfsdfsdfsdfsdhtrhghghgdfgsfdgsdfgfdgdfgfdgfgfgshshshshsfhdfhdf", R.drawable.pic6);
            photoList.add(pic5);
            Photo pic6 = new Photo("ooodghdgh", R.drawable.pic4);
            photoList.add(pic6);
            Photo pic7 = new Photo("ooodghdghdfhd", R.drawable.pic5);
            photoList.add(pic7);
        }

    }
}
