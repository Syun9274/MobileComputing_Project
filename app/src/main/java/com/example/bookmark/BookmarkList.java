package com.example.bookmark;

import android.os.Parcel;
import android.os.Parcelable;

public class BookmarkList implements Parcelable {

    // 북마크 이름과 URL
    public String bookmarkName;
    public String bookmarkUrl;

    public BookmarkList(String name, String url) {
        bookmarkName = name;
        bookmarkUrl = url;
    }

    protected BookmarkList(Parcel in) {
        // Parcel에서 문자열 읽기
        bookmarkName = in.readString();
        bookmarkUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Parcel에서 문자열 쓰기
        dest.writeString(bookmarkName);
        dest.writeString(bookmarkUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<BookmarkList> CREATOR
            = new Parcelable.Creator<BookmarkList>() {

        @Override
        public BookmarkList createFromParcel(Parcel in) {
            return new BookmarkList(in);
        }

        @Override
        public BookmarkList[] newArray(int size) {
            return new BookmarkList[size];
        }
    };
}

