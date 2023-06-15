package com.example.bookmark;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText bookMarkAddName, bookMarkAddUrl;
    ArrayList<BookmarkList> myDataset = new ArrayList<BookmarkList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // webViewActivity 정보 교환 (intent)
        Intent intent = getIntent();

        // 받아올 정보가 있을 경우: 정보 갱신
        if (intent != null && intent.hasExtra("bookmarkList")) {
            myDataset = intent.getParcelableArrayListExtra("bookmarkList");
            Log.d("MainActivity", "Received bookmarkList: " + myDataset);

            // 받아온 Dataset을 토대로 RecyclerView 실행
            mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);
        }

        mAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            // 북마크 이름과 주소를 직접 입력하여 추가
            case R.id.bookmarkAdd:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogview_main, null);
                builder.setView(dialogView).setTitle("북마크");

                bookMarkAddName = dialogView.findViewById(R.id.BookMarkAddName);
                bookMarkAddUrl = dialogView.findViewById(R.id.BookMarkAddUrl);

                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 북마크 이름과 URL 입력
                                String name = bookMarkAddName.getText().toString();
                                String url = bookMarkAddUrl.getText().toString();

                                // 북마크 정보를 담은 리스트에 추가
                                myDataset.add(new BookmarkList(name, url));
                                mAdapter.notifyDataSetChanged();

                                Toast.makeText(MainActivity.this, "Bookmark " + name + " Addition Successful", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 취소 버튼 클릭 시 처리할 작업
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            // 북마크를 선택하지 않고 WebView로 이동
            case R.id.webViewGo:
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putParcelableArrayListExtra("bookmarkList", myDataset);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}