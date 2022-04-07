package net.frazionz.android.auth;

public class FzRankObj {

    private String displayName;
    private String displayColor;

    public FzRankObj(String displayName, String displayColor){
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "FzRankObj{" +
                "displayName='" + displayName + '\'' +
                ", displayColor='" + displayColor + '\'' +
                '}';
    }
}
