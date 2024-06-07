package com.example.dcu_image_viewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.ViewHolder> {

    private final List<String> imagePaths;
    private final Context context;
    private final OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public DetailImageAdapter(Context context, List<String> imagePaths, OnImageClickListener listener) {
        this.context = context;
        this.imagePaths = imagePaths;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imagePath = imagePaths.get(position);
        Glide.with(context).load(imagePath).into(holder.detailImageView);

        holder.itemView.setOnClickListener(v -> listener.onImageClick(position));
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView detailImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailImageView = itemView.findViewById(R.id.detailImageView);
        }
    }
}
