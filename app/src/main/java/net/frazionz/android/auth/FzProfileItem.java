package net.frazionz.android.auth;

import org.json.JSONException;
import org.json.JSONObject;

public class FzProfileItem {

    int id, order;

    String userName, password, email, token, uuid, lastlog_at;

    boolean doubleAuth, isSlimSkin, favorite;

    public FzProfileItem(String userName, String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public FzProfileItem(int id, String userName, String token, String email, String uuid, String lastlog_at, boolean doubleAuth, boolean isSlimSkin, int order, boolean favorite)
    {
        this.id = id;
        this.userName = userName;
        this.token = token;
        this.email = email;
        this.uuid = uuid;
        this.lastlog_at = lastlog_at;
        this.isSlimSkin = isSlimSkin;
        this.order = order;
        this.favorite = favorite;
        this.doubleAuth = doubleAuth;
    }

    @Override
    public String toString() {
        return "FzProfileItem{" +
                "id=" + id +
                ", order=" + order +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", uuid='" + uuid + '\'' +
                ", doubleAuth='" + doubleAuth + '\'' +
                ", lastlog_at='" + lastlog_at + '\'' +
                ", isSlimSkin=" + isSlimSkin +
                '}';
    }

    public JSONObject toJsonProfile() throws JSONException {
        return new JSONObject().put("id", id).put("username", userName).put("token", token).put("isSlim", isSlimSkin).put("order", order);
    }

    public String getEmail() {
        return email;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getLastlog_at() {
        return lastlog_at;
    }

    public boolean isSlimSkin() {
        return isSlimSkin;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getToken()
    {
        return token;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSlimSkin(boolean slimSkin) {
        isSlimSkin = slimSkin;
    }

    public void setLastlog_at(String lastlog_at) {
        this.lastlog_at = lastlog_at;
    }

    public boolean isDoubleAuth() {
        return doubleAuth;
    }

    public void setDoubleAuth(boolean doubleAuth) {
        this.doubleAuth = doubleAuth;
    }
}
