package net.frazionz.android.activity;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;


import net.frazionz.android.R;
import net.frazionz.android.utils.FZUtils;
import net.frazionz.android.utils.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;


public class PostListView extends ArrayAdapter {

    Context c;
    private Post post;

    public PostListView(@NonNull Context context, int resource) {
        super(context, resource);
        this.c = context;
    }

    @Override
    public int getCount() {
        return Post.getPosts().size();
    }

    @Override
    public Object getItem(int i) {
        return Post.getPosts().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            view= LayoutInflater.from(c).inflate(R.layout.item_post,viewGroup,false);
        }
        //reference widgets
        CardView cardPost=view.findViewById(R.id.postCard);
        ImageView previewPost=view.findViewById(R.id.preview);
        TextView titlePost=view.findViewById(R.id.titlePost);
        TextView descrPost=view.findViewById(R.id.perexMinimPost);

        //bind data to widgets
        post = (Post) getItem(i);

        if(post.getImgPreviewBM() != null)
            previewPost.setImageBitmap(post.getImgPreviewBM());
        else
            new FZUtils.DownloadImageTask(previewPost, post)
                .execute(post.getImgPreview());

        titlePost.setText(post.getTitle());

        descrPost.setText("Publiée le "+post.getCreatedAt());

        cardPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(), PostViewActivity.class);
                intent.putExtra("post", post);
                v.getContext().startActivity(intent);*/
            }
        });

        return view;
    }



}
