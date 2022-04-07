package net.frazionz.android.auth;

public class FzAuthExist {

    public boolean exist;
    public boolean isURIP;
    public String uuid;
    public String reason;

    private FzAuthProfile profile;

    public FzAuthExist(boolean isURIP, boolean exist, String uuid, FzAuthProfile profile, String reason)
    {
        this.isURIP = isURIP;
        this.exist = exist;
        this.uuid = uuid;
        this.profile = profile;
        this.reason = reason;
    }

    public FzAuthExist(FzAuthProfile user)
    {
        this.profile = user;
        this.uuid = user.getUuid();
        this.exist = true;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isURIP() {
        return isURIP;
    }

    public boolean exist() {
        return exist;
    }

    public FzAuthProfile getProfile()
    {
        return profile;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "FzAuthExist{" +
                "exist=" + exist +
                ", isURIP=" + isURIP +
                ", uuid='" + uuid + '\'' +
                ", reason='" + reason + '\'' +
                ", profile=" + profile +
                '}';
    }
}
