package net.frazionz.android.auth;

public class FzAuthUpdated {

    private final boolean updated;

    private String result;

    private FzAuthProfile profile;

    public FzAuthUpdated(boolean updated, String result)
    {
        this.updated = updated;
        this.result = result;
    }

    public FzAuthUpdated(boolean updated, FzAuthProfile result)
    {
        this.updated = updated;
        this.profile = result;
    }

    public boolean updated() {
        return updated;
    }

    public String result()
    {
        return result;
    }

    public FzAuthProfile getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "FzAuthUpdated{" +
                "updated=" + updated +
                ", result='" + result + '\'' +
                ", profile=" + profile +
                '}';
    }
}
