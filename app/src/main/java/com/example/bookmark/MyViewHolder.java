package com.example.bookmark;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyViewHolder extends RecyclerView.ViewHolder {

    Button bookmarkBtn;

    public MyViewHolder(View itemView) {
        super(itemView);

        bookmarkBtn = itemView.findViewById(R.id.bookmarkListBtn);
    }
}
