package net.frazionz.android.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Post implements Serializable {

    public static ArrayList<Post> posts = new ArrayList<>();
    public static int maxPage;
    private final int id;
    private final String name;
    private final String imgPreview;
    private transient Bitmap imgPreviewView;
    private final String createdAt;

    public Post(int id, String name, String imgPreview, String createdAt){
        this.id = id;
        this.name = name;
        this.imgPreview = imgPreview;
        this.createdAt = createdAt;
    }

    public static void setMaxPage(int last_page) {
        maxPage = last_page;
    }

    public static int getMaxPage() {
        return maxPage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return name;
    }

    public static ArrayList<Post> getPosts(){
        return posts;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getImgPreview() {
        return imgPreview;
    }

    public Bitmap getImgPreviewBM() {
        return imgPreviewView;
    }

    public void setImgPreviewView(Bitmap imgPreviewView) {
        this.imgPreviewView = imgPreviewView;
    }
}
