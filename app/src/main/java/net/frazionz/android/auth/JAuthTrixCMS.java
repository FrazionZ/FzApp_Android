package net.frazionz.android.auth;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.antoineok.jauth.AuthStatus;
import fr.antoineok.jauth.exception.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;

public class JAuthTrixCMS {

    private final Context ctx;
    private String serverName, urlF;

    private AuthStatus authStatus;

    private boolean userConnected;

    private boolean rejectedBanned;

    protected final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

    private FzAuthProfile profile;

    public FzAuthProfile getProfile()
    {
        return profile;
    }

    public AuthStatus getAuthStatus()
    {
        return authStatus;
    }

    private boolean neededConfirmed;

    private int userMaxChar;
    private int passwordMaxChar;

    private String uid, userPassword, type;

    public JAuthTrixCMS(Context ctx, String serverName, String url, String uuid, String password, String type)
    {
        this(ctx, serverName, url, uuid, password, type, 25, 25);
    }

    public JAuthTrixCMS(Context ctx, String serverName, String url, FzAuthProfile authProfile)
    {
        if(url.endsWith("/"))
        {
            this.urlF = url.substring(0, url.length() - 1);
        }
        else
        {
            this.urlF = url;
        }
        this.ctx = ctx;
        this.authStatus = AuthStatus.CONNECTED;
        this.userConnected = true;
        this.uid = String.valueOf(authProfile.getId());
        this.userPassword = authProfile.getToken();
        this.serverName = serverName;
        this.userMaxChar = 25;
        this.passwordMaxChar = 25;
        this.neededConfirmed = authProfile.accountConfirmed;
        this.rejectedBanned = authProfile.accountBanned;
        this.type = "logJam";
        this.profile = authProfile;
    }

    public JAuthTrixCMS(Context ctx, String serverName, String url, String uuid, String password, String type, int userMaxchar, int passMaxchar, boolean confirm, boolean ban)
    {
        if(url.endsWith("/"))
        {
            this.urlF = url.substring(0, url.length() - 1);
        }
        else
        {
            this.urlF = url;
        }
        this.ctx = ctx;
        this.authStatus = AuthStatus.DISCONNECTED;
        this.userConnected = false;
        this.uid = uuid;
        this.userPassword = password;
        this.serverName = serverName;
        this.userMaxChar = userMaxchar;
        this.passwordMaxChar = passMaxchar;
        this.neededConfirmed = confirm;
        this.rejectedBanned = ban;
        this.type = type;
    }

    public JAuthTrixCMS(Context ctx, String serverName, String url, String uuid, String password, String type, int userMaxchar, int passMaxchar)
    {
        this(ctx, serverName, url, uuid, password, type, userMaxchar, passMaxchar, false, false);
    }

    public void disconnect()
    {
        if(!this.userConnected || this.authStatus != AuthStatus.DISCONNECTED)
        {
            return;
        }
        long time = System.currentTimeMillis();
        this.authStatus = AuthStatus.DISCONNECTED;
        this.userConnected = false;
        time = System.currentTimeMillis() - time;
        DecimalFormat form = new DecimalFormat("#.##");
    }

    public void connect() throws ServerNotFoundException, UserEmptyException, UserNotConfirmedException, UserBannedException, UserWrongException, UserComputerNotFoundException, IOException
    {
        long time = System.currentTimeMillis();
        try {
            this.connect(this.urlF);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {

            e.printStackTrace();
        }
        if(this.authStatus != AuthStatus.CONNECTED)
        {
            return;
        }
        time = System.currentTimeMillis() - time;
        DecimalFormat form = new DecimalFormat("#.##");
    }

    JAuthTCMSProfileManager profileManager;

    private void connect(String url) throws ServerNotFoundException, UserEmptyException, UserNotConfirmedException, UserBannedException, UserWrongException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UserComputerNotFoundException, IOException
    {

        this.authStatus = AuthStatus.CONNECTION;

        if(url == null)
        {

            this.userConnected = false;
            this.authStatus = AuthStatus.DISCONNECTED;

            throw new ServerNotFoundException(JTrixUtil.log("langDef.getIncoURL()"));
        }

        int responseCode = JTrixUtil.pingUrl(url);

        if(!(200 <= responseCode && responseCode <= 399))
        {
            this.userConnected = false;
            this.authStatus = AuthStatus.DISCONNECTED;
            throw new HttpException(JTrixUtil.log("ServerName"));
        }

        profileManager = new JAuthTCMSProfileManager(this.urlF, this.uid, this.userPassword);

        if(!profileManager.isProfileExist())
        {
            this.userConnected = false;
            this.authStatus = AuthStatus.DISCONNECTED;
            throw new UserWrongException(JTrixUtil.log("langDef.getCompteInex()"));
        }

        long time = System.currentTimeMillis();

        profileManager.loadProfile(this.ctx, this.type);
        this.profile = profileManager.getProfile();

        time = System.currentTimeMillis() - time;
        DecimalFormat form = new DecimalFormat("#.##");
        if(this.rejectedBanned && this.profile.isAccountBanned())
        {
            this.userConnected = false;
            this.authStatus = AuthStatus.DISCONNECTED;
            throw new UserBannedException(JTrixUtil.log("langDef.getBannedAcc().replace((<ServerName>), this.serverName)"));
        }
        if(this.neededConfirmed && !this.profile.isAccountConfirmed())
        {
            this.userConnected = false;
            this.authStatus = AuthStatus.DISCONNECTED;
            throw new UserNotConfirmedException(JTrixUtil.log("langDef.getAccNotVerif()"));
        }
        this.userConnected = true;

        this.authStatus = AuthStatus.CONNECTED;
    }

    public String getType() {
        return type;
    }

    public JAuthTCMSProfileManager getProfileManager() {
        return profileManager;
    }
}
