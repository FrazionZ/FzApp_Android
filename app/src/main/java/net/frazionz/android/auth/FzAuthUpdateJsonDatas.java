package net.frazionz.android.auth;

public class FzAuthUpdateJsonDatas {

    private final String kdata;
    private final String vdata;
    private String username;

    private String token;

    public FzAuthUpdateJsonDatas(String username, String token, String kdata, String vdata)
    {
        super();
        this.username = username;
        this.token = token;
        this.kdata = kdata;
        this.vdata = vdata;
    }

    public String getUsername()
    {
        return username;
    }

    public String getKdata()
    {
        return kdata;
    }

    public String getVdata() {
        return vdata;
    }

    public String getToken() {
        return token;
    }
}
