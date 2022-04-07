package net.frazionz.android.auth;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.CloseableHttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.CloseableHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClients;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.frazionz.android.activity.HomeActivity;
import net.frazionz.android.utils.FZUtils;
import net.frazionz.android.utils.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JTrixUtil {

    public static Integer pingUrl(String url)
    {
        HttpGet post = new HttpGet(url);

        try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post))
        {
            int responseCode = response.getStatusLine().getStatusCode();
            //System.out.println(responseCode);
            return(responseCode);
        }
        catch(IOException e)
        {

            e.printStackTrace();
        }
        return 500;

    }

    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    public static String log(String message)
    {
        return "JAuth > " + message;
    }

    public static byte[] encrypt(byte[] data, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] keyBytes = Base64.decode(publicKey.substring(26, publicKey.length() -25).replace("-", "").replace(" ", "").replace("\n", ""), Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // Encrypt data
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // encrypt the data segmentation
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encode(encryptedData, Base64.DEFAULT);
    }

    public static String getPublicKey(String url)
    {
        HttpPost post3 = new HttpPost(url +"/api/auth/v1/public/key");
        try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post3))
        {
            String resp = EntityUtils.toString(response.getEntity());
            System.out.println(resp);
            return resp;
        }
        catch(IOException e)
        {
            return null;
        }
    }

    public static void finishLogged(Context ctx, String url) {
        try {
            URL u = new URL(url);
            Set<Map.Entry<String, String>> sqi = FZUtils.decodeQueryString(u.getQuery()).entrySet();
            String datasJson = "";
            while(sqi.iterator().hasNext()){
                if(sqi.iterator().next().getKey().equalsIgnoreCase("datas")) {
                    datasJson = new String(Base64.decode(sqi.iterator().next().getValue().getBytes(), Base64.DEFAULT));
                    break;
                }
            }
            proccessFinishLogged(ctx, new JSONObject(datasJson));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void proccessFinishLogged(Context ctx, JSONObject jsonObject){
        try {

            Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
            FzAuthExist ext = gson.fromJson(jsonObject.toString(), FzAuthExist.class);

            FZUtils.setAuth(new JAuthTrixCMS(ctx, "FrazionZ", "https://auth.frazionz.net/", ext.getProfile()));

            JSONArray currentProfiles = FZUtils.getJAListProfiles();
            boolean canRegisterProfil = true;
            for (int i = 0; i <  currentProfiles.length(); i++)
                if (((JSONObject)  currentProfiles.get(i)).getString("uuid").equalsIgnoreCase(ext.getProfile().getUuid()))
                    canRegisterProfil = false;



            if (canRegisterProfil) {
                JSONObject newProfile = new JSONObject();
                newProfile.put("id", FZUtils.getAuth().getProfile().getId());
                newProfile.put("lastlog", new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(new Date()));
                newProfile.put("username", FZUtils.getAuth().getProfile().getUserName());
                newProfile.put("email", FZUtils.getAuth().getProfile().getUserMail());
                newProfile.put("token", FZUtils.getAuth().getProfile().getToken());
                newProfile.put("uuid", FZUtils.getAuth().getProfile().getUuid());
                newProfile.put("isSlim", FZUtils.getAuth().getProfile().isSlimSkin());
                currentProfiles.put(newProfile);
                JsonReader.writeJson(FZUtils.fileProfiles,  currentProfiles.toString());
            }else
                FZUtils.updateProfilRegister(Objects.requireNonNull(FZUtils.getProfileItem(FZUtils.getAuth().getProfile().getUuid())));
            FZUtils.refreshListProfiles(ctx);

            if (!FZUtils.getAuth().getProfile().isAccountConfirmed()) {
                //new FzDialogAccountConfirmed(panelManager, FzUtils.getProfileItem(FZUtils.getAuth().getProfile().getUuid())).setOverlayClose(false);
                FZUtils.getAuth().disconnect();
            }else{
                Intent intent = new Intent(ctx, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                ctx.startActivity(intent);
                ((AppCompatActivity) ctx).finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
