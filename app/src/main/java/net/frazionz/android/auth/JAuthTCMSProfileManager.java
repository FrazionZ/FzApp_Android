package net.frazionz.android.auth;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.antoineok.jauth.exception.UserWrongException;
import fr.antoineok.jauth.jsons.JsonExist;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class JAuthTCMSProfileManager {

    private String url, uuid, userPassword, eUuid, ePassword;

    private FzAuthProfile profile;
    public FzAuthProfile getProfile() {
        return profile;
    }
    public String getUrl()
    {
        return url;
    }
    public static String key;
    public String getUuid()
    {
        return uuid;
    }



    public String getUserPassword()
    {
        return userPassword;
    }



    public JAuthTCMSProfileManager(String url, String uuid, String userPassword) {
        JAuthTCMSProfileManager.key = JTrixUtil.getPublicKey(url);
        this.url = url;
        try {
            this.eUuid = new String(JTrixUtil.encrypt(uuid.getBytes(), key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.userPassword = userPassword;
        this.uuid = uuid;
    }



    Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();



    public boolean isProfileExist()
    {
        String url = this.url + "/api/auth/v1/acheck";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("uuid", uuid));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post))
        {
            String jsonE = EntityUtils.toString(response.getEntity());
            JsonExist exist = gson.fromJson(jsonE, JsonExist.class);
            System.out.println(jsonE);
            return exist.exist();
        }
        catch(IOException e)
        {
            return false;
        }

    }


    public static String toBase64(String input)
    {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public void loadProfile(Context ctx, String type) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, UserWrongException, UserComputerNotFoundException
    {
        if(!this.isProfileExist())
            return;

        String url2 = this.url + "/api/auth/v1/android";
        HttpPost post2 = new HttpPost(url2);
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        JAuthTCMSProfileManager.key = JTrixUtil.getPublicKey(url);
        urlParameters.add(new BasicNameValuePair("uuid", uuid));
        urlParameters.add(new BasicNameValuePair("password", userPassword));
        urlParameters.add(new BasicNameValuePair("type", toBase64(type)));
        urlParameters.add(new BasicNameValuePair("macAddress", "ip"));
        post2.setEntity(new UrlEncodedFormEntity(urlParameters));
        try(CloseableHttpClient httpClient2 = HttpClients.createDefault(); CloseableHttpResponse response2 = httpClient2.execute(post2))
        {
            String jsonE2 = EntityUtils.toString(response2.getEntity());
            FzAuthExist ext = gson.fromJson(jsonE2, FzAuthExist.class);
            if(!ext.exist() || !ext.isURIP() && type.equalsIgnoreCase("logToken")) {
                Toast.makeText(ctx, ext.getReason(), Toast.LENGTH_LONG).show();
                throw new UserComputerNotFoundException("Les informations étant incorrectes, \nNous avons supprimés le profil de votre liste");
            }
            profile = ext.getProfile();
        }
        catch(IOException e)
        {
            System.err.println("Impossible d'envoyer la requete: ");
            e.printStackTrace();
        }
    }

    public void updateProfile(FzAuthProfile fzAuthProfile, String keydata, String valuedata) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, UserWrongException
    {
        String json = gson.toJson(new FzAuthUpdateJsonDatas(toBase64(fzAuthProfile.userName), toBase64(fzAuthProfile.token), toBase64(keydata), toBase64(valuedata)));
        String eJson = new String(JTrixUtil.encrypt(json.getBytes(), key));
        String url2 = this.url + "/api/auth/v1/update";
        HttpPost post2 = new HttpPost(url2);
        List<NameValuePair> urlParameters2 = new ArrayList<>();
        urlParameters2.add(new BasicNameValuePair("data", eJson));
        post2.setEntity(new UrlEncodedFormEntity(urlParameters2));
        try(CloseableHttpClient httpClient2 = HttpClients.createDefault(); CloseableHttpResponse response2 = httpClient2.execute(post2))
        {
            String jsonE2 = EntityUtils.toString(response2.getEntity());
            FzAuthUpdated ext = gson.fromJson(jsonE2, FzAuthUpdated.class);
            if(!ext.updated()) {
                throw new UserWrongException("Impossible de faire la mise à jour");
            }
            profile = ext.getProfile();
        }
        catch(IOException e)
        {
            System.err.println("Impossible d'envoyer la requete: ");
            e.printStackTrace();
        }
    }

    public void uploadSkinProfile(String userName, String token, String skinType, String skinFile) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, UserWrongException
    {
        String json = gson.toJson(new FzAuthUploadSkinJsonDatas(toBase64(userName), toBase64(userPassword), toBase64(skinType)));
        String eJson = new String(JTrixUtil.encrypt(json.getBytes(), key));
        String url2 = this.url + "/api/auth/v1/skupload";
        HttpPost post2 = new HttpPost(url2);
        List<NameValuePair> urlParameters2 = new ArrayList<>();
        urlParameters2.add(new BasicNameValuePair("data", eJson));
        post2.setEntity(MultipartEntityBuilder.create().addTextBody("data", eJson).addTextBody("skin", skinFile).build());
        try(CloseableHttpClient httpClient2 = HttpClients.createDefault(); CloseableHttpResponse response2 = httpClient2.execute(post2))
        {
            String jsonE2 = EntityUtils.toString(response2.getEntity());
            FzAuthUpdated ext = gson.fromJson(jsonE2, FzAuthUpdated.class);
            if(!ext.updated()) {
                throw new UserWrongException("Impossible de faire la mise à jour\n"+jsonE2);
            }
            System.out.println("Debug Result Upload Skin: "+ext.result());
        }
        catch(IOException e)
        {
            System.err.println("Impossible d'envoyer la requete: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void uploadCapeProfile(String base64Cape) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IOException, InvalidKeyException {
        String json = gson.toJson(new FzAuthUploadCapeJsonDatas(toBase64(uuid), toBase64(userPassword)));
        String eJson = new String(JTrixUtil.encrypt(json.getBytes(), key));
        String url2 = this.url + "/api/auth/v1/cpupload";
        HttpPost post2 = new HttpPost(url2);
        List<NameValuePair> urlParameters2 = new ArrayList<>();
        urlParameters2.add(new BasicNameValuePair("data", eJson));

        post2.setEntity(MultipartEntityBuilder.create().addTextBody("data", eJson).addTextBody("cape", base64Cape).build());
        try(CloseableHttpClient httpClient2 = HttpClients.createDefault(); CloseableHttpResponse response2 = httpClient2.execute(post2))
        {
            String jsonE2 = EntityUtils.toString(response2.getEntity());
            FzAuthUpdated ext = gson.fromJson(jsonE2, FzAuthUpdated.class);
            if(!ext.updated()) {
                throw new UserWrongException("Impossible de faire la mise à jour\n"+jsonE2);
            }
            System.out.println("Debug Result Cape Skin: "+ext.result());
        }
        catch(IOException | UserWrongException e)
        {
            System.err.println("Impossible d'envoyer la requete: "+e.getMessage());
            e.printStackTrace();
        }
    }



    public String getKey() {
        return key;
    }
}
