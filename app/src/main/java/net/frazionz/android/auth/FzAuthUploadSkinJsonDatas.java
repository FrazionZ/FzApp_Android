package net.frazionz.android.auth;

import java.util.Collection;

public class FzAuthUploadSkinJsonDatas {

    private String isSlim;
    private String username;
    private String token;

    public FzAuthUploadSkinJsonDatas(String username, String token, String isSlim)
    {
        super();
        this.username = username;
        this.token = token;
        this.isSlim = isSlim;
    }

    public String getUsername()
    {
        return username;
    }


    public String getToken() {
        return token;
    }

    public String getSlimType() {
        return isSlim;
    }
}
