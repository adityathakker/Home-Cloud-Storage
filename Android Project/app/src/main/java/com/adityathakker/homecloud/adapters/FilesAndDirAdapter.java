package com.adityathakker.homecloud.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adityathakker.homecloud.R;
import com.adityathakker.homecloud.model.FileOrDirPojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 21/10/16.
 */

public class FilesAndDirAdapter extends RecyclerView.Adapter<FilesAndDirAdapter.FilesAndDirViewHolder> {
    public static final String TAG = FilesAndDirAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    ArrayList<FileOrDirPojo> arrayList;
    public FilesAndDirAdapter(Context context, ArrayList<FileOrDirPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public FilesAndDirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_file_or_dir_row, parent, false);
        FilesAndDirViewHolder viewHolder = new FilesAndDirViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilesAndDirViewHolder holder, int position) {
        FileOrDirPojo fileOrDirPojo = arrayList.get(position);
        holder.name.setText(fileOrDirPojo.getName());
        if(fileOrDirPojo.getType().equals("file")){
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_insert_drive_file_black_24dp));
        }else{
            holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_folder_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }

    class FilesAndDirViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.file_icon)
        ImageView icon;
        @BindView(R.id.file_name)
        TextView name;
        public FilesAndDirViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition(), arrayList.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onItemLongClick(view, getAdapterPosition(), arrayList.get(getAdapterPosition()));
            return true;
        }
    }

    //Click Listener Interface
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, FileOrDirPojo clickedFileOrDirPojo);
    }

    //Click Listener Interface
    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position, FileOrDirPojo clickedFileOrDirPojo);
    }

    //Setting up the click listener
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    //Setting up the click listener
    public void setOnItemLongClickListener(final OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
