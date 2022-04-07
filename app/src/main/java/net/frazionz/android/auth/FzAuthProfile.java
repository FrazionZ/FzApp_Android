package net.frazionz.android.auth;

public class FzAuthProfile {

    int id, order;

    String type, userName, userMail, userRank, token, uuid, created_at, updated_at, lastlog_at;

    long timeGame;

    double money;

    boolean doubleAuth, whiteListMaintenance, accountBanned, accountConfirmed, isSlimSkin, TfaEnable;

    public FzAuthProfile(int id, String userName, String token, String uuid, boolean doubleAuth, boolean TfaEnable, String lastlog_at, boolean isSlimSkin, int order)
    {
        this.id = id;
        this.userName = userName;
        this.token = token;
        this.uuid = uuid;
        this.doubleAuth = doubleAuth;
        this.TfaEnable = TfaEnable;
        this.lastlog_at = lastlog_at;
        this.isSlimSkin = isSlimSkin;
        this.order = order;
    }

    public FzAuthProfile(int id, String type, String userName, String userMail, String userRank, String token, String uuid, boolean doubleAuth, boolean TfaEnable, double money, boolean accountBanned, boolean accountConfirmed, long timeGame, String created_at, String updated_at, boolean isSlimSkin, boolean whiteListMaintenance)
    {
        this.id = id;
        this.type = type;
        this.userName = userName;
        this.userMail = userMail;
        this.userRank = userRank;
        this.token = token;
        this.uuid = uuid;
        this.doubleAuth = doubleAuth;
        this.TfaEnable = TfaEnable;
        this.money = money;
        this.accountBanned = accountBanned;
        this.accountConfirmed = accountConfirmed;
        this.timeGame = timeGame;
        this.isSlimSkin = isSlimSkin;
        this.whiteListMaintenance = whiteListMaintenance;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "FzAuthProfile{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", token='" + token + '\'' +
                ", doubleAuth='" + doubleAuth + '\'' +
                ", uuid='" + uuid + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", lastlog_at='" + lastlog_at + '\'' +
                ", timeGame=" + timeGame +
                ", whiteListMaintenance=" + whiteListMaintenance +
                ", money=" + money +
                ", accountBanned=" + accountBanned +
                ", accountConfirmed=" + accountConfirmed +
                ", isSlimSkin=" + isSlimSkin +
                '}';
    }

    public int getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    public String getLastlog_at() {
        return lastlog_at;
    }

    public boolean isSlimSkin() {
        return isSlimSkin;
    }

    public boolean isDoubleAuth() {
        return doubleAuth;
    }

    public String getType() {
        return type;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getUserMail()
    {
        return userMail;
    }

    public String getUserRank()
    {
        return userRank;
    }

    public String getToken()
    {
        return token;
    }

    public boolean isTfaEnable() {
        return TfaEnable;
    }

    public String getUuid()
    {
        return uuid;
    }

    public double getMoney()
    {
        return money;
    }

    public boolean isAccountBanned()
    {
        return accountBanned;
    }

    public boolean isAccountConfirmed()
    {
        return accountConfirmed;
    }

    public long getTimeGame() {
        return timeGame;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public boolean getWLM() { return whiteListMaintenance; }

    public String getUpdated_at()
    {
        return updated_at;
    }
}
