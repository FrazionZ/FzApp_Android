package net.frazionz.android.activity.ui.shop;

public class ShopTypes {

    private int id;
    private String typeName;
    private boolean isActive;
    private String minecraftItemName;
    private int minecraftItemData;

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "ShopType : " + this.typeName + " - isActive : " + this.isActive;
    }

    public int getMinecraftItemData() {
        return minecraftItemData;
    }

    public String getMinecraftItemName() {
        return minecraftItemName;
    }

}

