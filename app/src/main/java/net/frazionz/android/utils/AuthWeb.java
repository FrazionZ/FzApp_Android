package net.frazionz.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AuthWeb extends WebView {

    public AuthWeb(Context context) {
        super(context);
        init();
    }

    public AuthWeb(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public AuthWeb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    protected void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        BaseInputConnection baseInputConnection = new BaseInputConnection(this, false);
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;
        outAttrs.inputType = EditorInfo.TYPE_NULL;
        return baseInputConnection;
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

}
