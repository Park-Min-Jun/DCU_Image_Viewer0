package com.example.dcu_image_viewer;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private List<String> imagePaths;
    private int initialPosition;
    private ImageView largeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imagePaths = getIntent().getStringArrayListExtra("imagePaths");
        initialPosition = getIntent().getIntExtra("position", 0);

        largeImageView = findViewById(R.id.largeImageView);

        RecyclerView recyclerView = findViewById(R.id.detailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        DetailImageAdapter adapter = new DetailImageAdapter(this, imagePaths, position -> updateLargeImage(position));
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(initialPosition);
        updateLargeImage(initialPosition);
    }

    private void updateLargeImage(int position) {
        String imagePath = imagePaths.get(position);
        Glide.with(this).load(imagePath).into(largeImageView);
    }
}
