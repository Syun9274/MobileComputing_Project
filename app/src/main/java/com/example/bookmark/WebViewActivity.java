package com.example.bookmark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WebViewActivity extends AppCompatActivity {

    EditText bookMarkCurrentAddName;
    WebView webView;
    String url;
    ArrayList<BookmarkList> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_main);

        webView = findViewById(R.id.webView);

        // default webpage: Google
        webView.loadUrl("https://www.google.co.kr/");
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();

        if (intent != null) {
            url = intent.getStringExtra("url");
            myDataset = intent.getParcelableArrayListExtra("bookmarkList");
            Log.d("WebViewActivity", "Received bookmarkList: " + myDataset);

            // 초기 정보 등록
            if (myDataset == null) {
                myDataset = new ArrayList<>(); // bookmarkList 초기화
                myDataset.add(new BookmarkList("Naver", "https://www.naver.com/"));
                myDataset.add(new BookmarkList("Google", "https://www.google.co.kr/"));
                myDataset.add(new BookmarkList("Daum", "https://www.daum.net/"));
            }

            webView.loadUrl(url);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(WebViewActivity.this, MainActivity.class);

        switch (item.getItemId()) {
            // 북마크 페이지로 이동
            case R.id.bookmarkGo:
                intent.putParcelableArrayListExtra("bookmarkList", myDataset);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;

            // 현재 페이지 북마크 추가
            case R.id.bookmarkCurrentAdd:
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                View dialogView = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.dialogview_webview, null);
                builder.setView(dialogView)
                        .setTitle("북마크");

                bookMarkCurrentAddName = dialogView.findViewById(R.id.BookMarkCurrentAddName);

                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String currentUrl = webView.getUrl();
                                String name = bookMarkCurrentAddName.getText().toString();

                                myDataset.add(new BookmarkList(name, currentUrl));

                                Toast.makeText(WebViewActivity.this, "Bookmark " + name + " Addition Successful", Toast.LENGTH_SHORT).show();
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

            // 종료
            case R.id.exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
