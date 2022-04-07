package net.frazionz.android.auth;

public class FzAuthJsonDatas {

    private String uuid, password;

    public FzAuthJsonDatas(String uuid, String password)
    {
        super();
        this.uuid = uuid;
        this.password = password;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getPassword()
    {
        return password;
    }
}
