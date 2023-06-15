package com.example.bookmark;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<BookmarkList> mDataset;
    private Context context;

    public MyAdapter(Context context) { this.context = context; }

    public MyAdapter(ArrayList<BookmarkList> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int pos = position;
        holder.bookmarkBtn.setText(mDataset.get(pos).bookmarkName);
        final String url = mDataset.get(pos).bookmarkUrl;
        final Context context = holder.itemView.getContext();

        // 북마크 클릭하여 해당 페이지로 이동
        holder.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);

                intent.putExtra("url", url);
                intent.putParcelableArrayListExtra("bookmarkList", mDataset);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        // 북마크 길게 클릭
        holder.bookmarkBtn.setOnLongClickListener(new View.OnLongClickListener() {
            ArrayList<BookmarkList> bookmarkDelete = new ArrayList<>();

            @Override
            public boolean onLongClick(View v) {
                // 북마크 삭제
                mDataset.remove(mDataset.get(pos));
                notifyDataSetChanged();

                Toast.makeText(context, "Bookmark Deletion Successful", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

    public int getItemCount() {
        return mDataset.size();
    }

}