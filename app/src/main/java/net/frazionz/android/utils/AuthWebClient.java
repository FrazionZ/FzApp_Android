package net.frazionz.android.utils;

import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.frazionz.android.activity.HomeActivity;
import net.frazionz.android.auth.FzAuthExist;
import net.frazionz.android.auth.JAuthTrixCMS;
import net.frazionz.android.auth.JTrixUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AuthWebClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Toast.makeText(view.getContext(), "test", Toast.LENGTH_LONG).show();
        if(!url.startsWith("https://auth.frazionz.net")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(browserIntent);
            return super.shouldOverrideUrlLoading(view, url);
        }else{
            if(url.contains("login/finish?datas=")){
                JTrixUtil.finishLogged(view.getContext(), url);
            }
        }
        return false;
    }

}
