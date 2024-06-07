package com.example.dcu_image_viewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.provider.MediaStore;

public class MainActivity extends AppCompatActivity implements ImageAdapter.OnImageClickListener {

    private static final int REQUEST_PERMISSION = 100;
    private List<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            loadImages();
        }
    }

    private void loadImages() {
        imagePaths = getImagePathsFromMediaStore();

        Log.d("MainActivity", "Loaded image paths: " + imagePaths.toString());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ImageAdapter adapter = new ImageAdapter(imagePaths, this);
        recyclerView.setAdapter(adapter);
    }

    private List<String> getImagePathsFromMediaStore() {
        List<String> paths = new ArrayList<>();

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.DATA + " like ? ",
                new String[]{"%DCIM/Camera%"},
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                paths.add(path);
            }
            cursor.close();
        }

        return paths;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImages();
            } else {
                Log.e("MainActivity", "Permission denied");
            }
        }
    }

    @Override
    public void onImageClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("imagePaths", new ArrayList<>(imagePaths));
        intent.putExtra("position", position);
        startActivity(intent);
    }
}