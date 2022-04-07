package net.frazionz.android.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import net.frazionz.android.activity.HomeActivity;
import net.frazionz.android.activity.ui.shop.ShopItems;
import net.frazionz.android.activity.ui.shop.ShopTypes;
import net.frazionz.android.auth.FzAuthProfile;
import net.frazionz.android.auth.FzLoginAuth;
import net.frazionz.android.auth.FzProfileItem;
import net.frazionz.android.auth.JAuthTrixCMS;
import net.frazionz.android.auth.UserComputerNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

import fr.antoineok.jauth.AuthStatus;
import fr.antoineok.jauth.exception.ServerNotFoundException;
import fr.antoineok.jauth.exception.UserBannedException;
import fr.antoineok.jauth.exception.UserEmptyException;
import fr.antoineok.jauth.exception.UserNotConfirmedException;
import fr.antoineok.jauth.exception.UserWrongException;

public class FZUtils {

    public static File dirApp = new File(Environment.getDataDirectory(), "/data/net.frazionz.android/");
    public static File fileProfiles = new File(dirApp, "profiles.json");
    public static ArrayList<FzProfileItem> fzAuthProfiles = new ArrayList<>();
    public static JAuthTrixCMS jAuth;
    public static JSONObject apiFactionProfile;
    public static JSONArray apiItemsMC;

    public static SharedPreferences getSharedPreferences(Context ctx, String nameRef) {
        return ctx.getSharedPreferences(nameRef, Context.MODE_PRIVATE);
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;
        Post post;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        public DownloadImageTask(ImageView bmImage, Post post) {
            this.bmImage = bmImage;
            this.post = post;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            if(post != null)
                post.setImgPreviewView(result);
            bmImage.setVisibility(View.VISIBLE);
        }

        public ImageView getBmImage() {
            return bmImage;
        }
    }

    public static Map<String, String> decodeQueryString(String query) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                if (!key.isEmpty()) {
                    params.put(key, value);
                }
            }
            return params;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e); // Cannot happen with UTF-8 encoding.
        }
    }

    public static Animation fadeAnimation(int fadeType) {
        Animation fadeIn = null;
        if (fadeType == 0)
            fadeIn = new AlphaAnimation(0.0f, 1.0f);
        else if (fadeType == 1)
            fadeIn = new AlphaAnimation(1.0f, 0.0f);
        fadeIn.setDuration(800);
        fadeIn.setStartOffset(800);
        return fadeIn;
    }

    public static String getMacAddress(Context ctx) {
        WifiManager wimanager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("MissingPermission") String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = "Device don't have mac address or wi-fi is disabled";
        }
        return macAddress;
    }

    public static JAuthTrixCMS getAuth() {
        return jAuth;
    }

    public static JSONObject getApiFactionProfile() {
        return apiFactionProfile;
    }

    public static void setAuth(JAuthTrixCMS jAuthSet) {
        jAuth = jAuthSet;
    }

    public static void updateProfilRegister(FzProfileItem authProfile){
        try {
            FZUtils.updateDataProfile(FZUtils.getAuth().getProfile().getUuid(), "id", FZUtils.getAuth().getProfile().getId());
            authProfile.setId(FZUtils.getAuth().getProfile().getId());
            FZUtils.updateDataProfile(FZUtils.getAuth().getProfile().getUuid(), "token", FZUtils.getAuth().getProfile().getToken());
            authProfile.setToken(FZUtils.getAuth().getProfile().getToken());
            FZUtils.updateDataProfile(FZUtils.getAuth().getProfile().getUuid(), "username", FZUtils.getAuth().getProfile().getUserName());
            authProfile.setUserName(FZUtils.getAuth().getProfile().getUserName());
            FZUtils.updateDataProfile(FZUtils.getAuth().getProfile().getUuid(), "email", FZUtils.getAuth().getProfile().getUserMail());
            authProfile.setEmail(FZUtils.getAuth().getProfile().getUserMail());
            FZUtils.updateDataProfile(FZUtils.getAuth().getProfile().getUuid(), "isSlim", FZUtils.getAuth().getProfile().isSlimSkin());
            authProfile.setSlimSkin(FZUtils.getAuth().getProfile().isSlimSkin());
            FZUtils.updateDataProfile(FZUtils.getAuth().getProfile().getUuid(), "lastlog", new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(new Date()));
            authProfile.setLastlog_at(new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(new Date()));
        } catch (IOException | JSONException e) {
            FZUtils.getAuth().disconnect();
        }

    }

    public static void updateDataProfile(String uidProfil, String key, Object val) throws IOException, JSONException {
        JSONArray authLoadProfiles = JsonReader.readJsonArrayFromFile(FZUtils.fileProfiles);
        for (int i=0; i < authLoadProfiles.length(); i++) {
            if(authLoadProfiles.getJSONObject(i).getString("uuid").equalsIgnoreCase(uidProfil)) {
                JSONObject authProfilObject = authLoadProfiles.getJSONObject(i);
                authLoadProfiles.remove(i);
                authProfilObject.put(key, val);
                authLoadProfiles.put(authProfilObject);
            }
        }
        JsonReader.writeJson(FZUtils.fileProfiles, authLoadProfiles.toString());
    }

    public static JSONArray getJAListProfiles(){
        try {
            return JsonReader.readJsonArrayFromFile(FZUtils.fileProfiles);
        } catch (IOException | JSONException e) {return null;}
    }

    public static ArrayList<FzProfileItem> getFzAuthProfiles() {
        return fzAuthProfiles;
    }

    public static FzProfileItem getProfileItem(String uuid){
        for(FzProfileItem profileItem : getFzAuthProfiles()){
            if(profileItem.getUuid().equalsIgnoreCase(uuid))
                return profileItem;
        }
        return null;
    }

    public static void refreshListProfiles(Context ctx){
        getFzAuthProfiles().clear();
        loadListProfiles(ctx);
    }

    public static boolean doListProfiles(Context ctx){
        try {
            File fileProfiles = FZUtils.fileProfiles;
            JSONArray authLoadProfiles = JsonReader.readJsonArrayFromFile(fileProfiles);
            return authLoadProfiles.length() > 0;
        } catch (IOException | JSONException e) {
            Toast.makeText(ctx, "Une erreur s'est produite lors de la lecture des profils..", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static void loadListProfiles(Context ctx){
        if(doListProfiles(ctx)){
            try {
                JSONArray authLoadProfiles = JsonReader.readJsonArrayFromFile(fileProfiles);
                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < authLoadProfiles.length(); i++) {
                    if(!authLoadProfiles.getJSONObject(i).has("order"))
                        FZUtils.updateDataProfile(authLoadProfiles.getJSONObject(i).getString("uuid"), "order", String.valueOf(i));
                    jsonValues.add(authLoadProfiles.getJSONObject(i));
                }
                Collections.sort( jsonValues, new Comparator<JSONObject>() {
                    private static final String KEY_NAME = "order";
                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();
                        try {
                            valA = (String) a.get(KEY_NAME);
                            valB = (String) b.get(KEY_NAME);
                        }
                        catch (JSONException e) {
                        }

                        return valA.compareTo(valB);
                    }
                });
                for (int i=0; i < authLoadProfiles.length(); i++) {
                    JSONObject profilObj = jsonValues.get(i);

                    boolean checkProfileAdd = false;
                    for (FzProfileItem profileItem : FZUtils.getFzAuthProfiles()){
                        if (profileItem.getUuid().equalsIgnoreCase(profilObj.getString("uuid")) || profileItem.getId() == profilObj.getInt("id")) {
                            checkProfileAdd = true;
                            break;
                        }
                    }

                    if(!checkProfileAdd)
                        FZUtils.getFzAuthProfiles().add(
                                new FzProfileItem(
                                        ((profilObj.has("id") ? profilObj.getInt("id") : -1)),
                                        profilObj.getString("username"), profilObj.getString("token"), ((profilObj.has("email") ? profilObj.getString("email") : "")),
                                        profilObj.getString("uuid"), ((profilObj.has("lastlog") ? profilObj.getString("lastlog") : "-")),
                                        true,
                                        ((profilObj.has("isSlim") ? profilObj.getBoolean("isSlim") : false)),
                                        ((profilObj.has("order") ? profilObj.getInt("order") : -1)),
                                        ((profilObj.has("favorite") ? profilObj.getBoolean("favorite") : false))
                                ));
                }
            } catch (IOException | JSONException e) {
                Toast.makeText(ctx, "Une erreur s'est produite lors de la lecture des profils..", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void authToken(Context ctx, String type, FzProfileItem authProfile, boolean alreadyLogOtherProfile, boolean DFAToLogin){
        if(alreadyLogOtherProfile) {
            System.out.println("Déconnexion du compte (" + getAuth().getProfile().getUuid() + ")");
            getAuth().disconnect();
        }
        setAuth(new JAuthTrixCMS(ctx, "FrazionZ", "https://auth.frazionz.net/", authProfile.getUuid(), authProfile.getToken(), type));
        try {
            getAuth().connect();
            if (getAuth().getAuthStatus() == AuthStatus.CONNECTED) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getAuth().getProfile().isAccountBanned()) {
                            Toast.makeText(ctx, "banned", Toast.LENGTH_LONG).show();
                            getAuth().disconnect();
                            //if(profilItem != null)
                            //profilItem.resetAAS();
                        } else {
                            try {
                                FZUtils.updateDataProfile(getAuth().getProfile().getUuid(), "token", getAuth().getProfile().getToken());
                                authProfile.setToken(getAuth().getProfile().getToken());
                            } catch (IOException | JSONException e) {
                            }
                            if (!getAuth().getProfile().isAccountConfirmed()) {
                                // new FzDialogAccountConfirmed(panelManager, authProfile).setOverlayClose(false);
                                getAuth().disconnect();
                            } else if (getAuth().getProfile().isDoubleAuth() && getAuth().getType().equalsIgnoreCase("logToken")) {
                                //new FzDialogAccount2FA(panelManager, authProfile, profilItem, DFAToLogin).setOverlayClose(false);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx, "UserComputerNotFoundException!", Toast.LENGTH_LONG).show();
                                        String url = "https://auth.frazionz.net/login?accessToken=" + getAuth().getProfile().getToken() + "&macAddress=ip&client=android";
                                        new FzLoginAuth(ctx, url, false);
                                    }
                                }, 100);
                            } else {
                                authContinueToken(ctx, authProfile, alreadyLogOtherProfile);
                            }
                        }
                    }
                }, 100);
            }
        } catch (UserComputerNotFoundException e){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, "UserComputerNotFoundException!", Toast.LENGTH_LONG).show();
                    String url = "https://auth.frazionz.net/login?accessToken=unknow&macAddress=ip&client=android&id="+authProfile.getId();
                    new FzLoginAuth(ctx, url, false);
                }
            }, 100);
        } catch (UserNotConfirmedException e){
            //new FzDialogAccountConfirmed(panelManager, authProfile);
        } catch (ServerNotFoundException | UserBannedException | UserEmptyException | UserWrongException | IOException e) {
            e.printStackTrace();
            Toast.makeText(ctx, e.getMessage().replaceAll("JAuth > ", ""), Toast.LENGTH_LONG).show();
        }
    }

    public static void authContinueToken(Context ctx, FzProfileItem authProfile, boolean alreadyLogOtherProfile){
        loadProfile(ctx, authProfile);
        if (alreadyLogOtherProfile) {
            Intent intent = new Intent(ctx, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            ctx.startActivity(intent);
            ((AppCompatActivity) ctx).finish();
        }else {
            updateProfilRegister(authProfile);
            authProfile.setDoubleAuth(getAuth().getProfile().isDoubleAuth());
            Intent intent = new Intent(ctx, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            ctx.startActivity(intent);
            ((AppCompatActivity) ctx).finish();
        }
    }

    public static void loadProfile(Context ctx, FzProfileItem authProfile) {
        try {
            apiFactionProfile = JsonReader.readJsonFromUrl(VarFz.getParamGetProfile(authProfile));
        } catch (IOException e) {
            Toast.makeText(ctx, "Impossible de charger le profile", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx, "Impossible de charger le profile", Toast.LENGTH_LONG).show();
        }
    }

    public static ShopTypes[] loadShopType(Context ctx) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(JsonReader.readJsonArrayFromUrl(VarFz.getParamGetShopTypes()).toString(), ShopTypes[].class);
        } catch (IOException e) {
            Toast.makeText(ctx, "Impossible de charger le profile", Toast.LENGTH_LONG).show();
            return null;
        } catch (JSONException e) {
            Toast.makeText(ctx, "Impossible de charger le profile", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static ShopItems[] loadShopItems(Context ctx, ShopTypes shopTypes) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(JsonReader.readJsonArrayFromUrl(VarFz.getParamGetShopItems(shopTypes)).toString(), ShopItems[].class);
        } catch (IOException e) {
            Toast.makeText(ctx, "Impossible de charger le profile", Toast.LENGTH_LONG).show();
            return null;
        } catch (JSONException e) {
            Toast.makeText(ctx, "Impossible de charger le profile", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static void loadProfile(Context ctx, FzAuthProfile authProfile) {
        try {
            apiFactionProfile = JsonReader.readJsonFromUrl(VarFz.getParamGetProfile(authProfile));
        } catch (IOException e) {
            Toast.makeText(ctx, "Impossible de charger les infos du profile", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx, "Impossible de charger les infos du profile", Toast.LENGTH_LONG).show();
        }
    }

    public static void loadItemsMC(Context ctx) {
        try {
            apiItemsMC = JsonReader.readJsonArrayFromUrl("https://minecraft-ids.grahamedgecombe.com/items.json");
        } catch (IOException e) {
            Toast.makeText(ctx, "Impossible de charger les items", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx, "Impossible de charger les items", Toast.LENGTH_LONG).show();
        }
    }

    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public static String conversMoney(String s){
        Locale usa = new Locale("en", "US");
        Currency dollars = Currency.getInstance(usa);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(Double.parseDouble(s)).replace("$", "");
    }

    public static String conversDate(String s){
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(s);
            simpleDateFormat.applyPattern("dd-MM-yyyy à HH:mm");
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    public static void loadMorePosts(Context ctx, String currentPage, boolean isMainActivity){
        try {
            JSONObject jsonLoadPostPre = JsonReader.readJsonFromUrl(VarFz.getParamGetPostsByPage(currentPage));
            if(jsonLoadPostPre.has("last_page"))
                Post.setMaxPage(jsonLoadPostPre.getInt("last_page"));
            JSONArray jsonLoadPost = jsonLoadPostPre.getJSONArray("data");
            for(int i=0;i<jsonLoadPost.length();i++){
                JSONObject objPost = jsonLoadPost.getJSONObject(i);
                if(objPost.has("id") && objPost.has("title") && objPost.has("img") && objPost.has("published_at")){

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse(objPost.getString("published_at"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    format = new SimpleDateFormat("dd MMM yyyy");
                    Post.getPosts().add(new Post(objPost.getInt("id"), objPost.getString("title"), objPost.getString("img"), format.format(date)));
                }
                if(isMainActivity){
                    if(i+1 == jsonLoadPost.length()){
                        ctx.startActivity(new Intent(ctx, HomeActivity.class));
                        ((AppCompatActivity) ctx).finish();
                    }
                }
            }

        } catch (IOException e) {
            Toast.makeText(ctx, "Impossible de charger les postes (" + VarFz.getParamGetPostsByPage("1") + ")", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx, "Impossible de charger les postes", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
