package net.frazionz.android.auth;

public class FzAuthUploadCapeJsonDatas {

    private String isSlim;
    private String username;
    private String token;

    public FzAuthUploadCapeJsonDatas(String username, String token)
    {
        super();
        this.username = username;
        this.token = token;
    }

    public String getUsername()
    {
        return username;
    }


    public String getToken() {
        return token;
    }

}
