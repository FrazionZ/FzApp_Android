package net.frazionz.android.auth;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import net.frazionz.android.utils.AuthWeb;
import net.frazionz.android.utils.AuthWebClient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class FzLoginAuth
{
    private CompletableFuture<String> future;

    public FzLoginAuth(Context ctx, String url, boolean launchInThread)
    {
        start(ctx, url, launchInThread);
    }

    public CompletableFuture<String> start(Context ctx, String urlStart, boolean launchInThread)
    {
        if(launchInThread)
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    init(ctx, urlStart);
                }
            }, 100);
        else
            init(ctx, urlStart);

        return this.future;
    }


    protected void init(Context ctx, String url)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        AuthWeb wv = new AuthWeb(ctx);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.loadUrl(url);
        wv.setFocusable(true);
        wv.setFocusableInTouchMode(true);
        wv.requestFocusFromTouch();
        wv.setWebViewClient(new AuthWebClient());
        wv.setWebChromeClient(new WebChromeClient());
        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
