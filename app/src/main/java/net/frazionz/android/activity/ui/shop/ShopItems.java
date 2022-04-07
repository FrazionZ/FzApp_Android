package net.frazionz.android.activity.ui.shop;

import android.widget.Toast;

import net.frazionz.android.utils.FZUtils;
import net.frazionz.android.utils.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ShopItems {

    private int id;
    private String minecraftItemName;
    private String textureName;
    private int minecraftItemData;
    private int maxStock;
    private int actualStock;
    private int shopType;
    private boolean isActive;
    private double actualBuyPrice;
    private double actualSellPrice;

    public ShopItems(int id, String minecraftItemName, String textureName, int minecraftItemData, int maxStock, int actualStock, int shopType, boolean isActive, double actualBuyPrice, double actualSellPrice) {
        this.id = id;
        this.minecraftItemName = minecraftItemName;
        this.textureName = textureName;
        this.minecraftItemData = minecraftItemData;
        this.maxStock = maxStock;
        this.actualStock = actualStock;
        this.shopType = shopType;
        this.isActive = isActive;
        this.actualBuyPrice = actualBuyPrice;
        this.actualSellPrice = actualSellPrice;
    }

    public void updateName(){
        try {
            for(int i=0;i<FZUtils.apiItemsMC.length();i++){
                JSONObject itemMC = FZUtils.apiItemsMC.getJSONObject(i);
                if(itemMC.getString("text_type").equalsIgnoreCase(minecraftItemName)){
                    if(itemMC.getString("meta").equalsIgnoreCase(String.valueOf(minecraftItemData))){
                        this.minecraftItemName = itemMC.getString("name");
                    }else
                        this.minecraftItemName = FZUtils.upperCaseFirst(minecraftItemName);
                }else
                    this.minecraftItemName = FZUtils.upperCaseFirst(minecraftItemName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getActualStock() {
        return actualStock;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public int getMinecraftItemData() {
        return minecraftItemData;
    }

    public String getMinecraftItemName() {
        return minecraftItemName;
    }

    public String getTextureName() {
        return textureName;
    }

    public int getShopType() {
        return shopType;
    }

    public double getActualBuyPrice() {
        return actualBuyPrice;
    }

    public double getActualSellPrice() {
        return actualSellPrice;
    }

    public boolean isActive() {
        return isActive;
    }

}

