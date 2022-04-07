package net.frazionz.android.activity.ui.shop;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.frazionz.android.R;

public class CardShopItemData extends LinearLayout {

    private final TextView dataKey;
    private final TextView dataValue;
    private final ImageView dataItem;
    private final ShopItems shopItems;
    private View view;
    private final String keyDataStr;
    private final String valueDataStr;


    public CardShopItemData(Context context, ShopItems shopItems) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        this.shopItems = shopItems;
        this.keyDataStr = shopItems.getTextureName();
        this.valueDataStr = shopItems.getTextureName();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.card_shopitem_data, this, true);

        dataItem = view.findViewById(R.id.dataIcon);
        dataKey = view.findViewById(R.id.dataKey);
        dataValue = view.findViewById(R.id.dataValue);

    }


    public ShopItems getShopItems() {
        return shopItems;
    }

    public ImageView getDataIcon() {
        return dataItem;
    }

    public TextView getDataKey() {
        return dataKey;
    }

    public TextView getDataValue() {
        return dataValue;
    }

    public String getKeyDataStr() {
        return keyDataStr;
    }

    public String getValueDataStr() {
        return valueDataStr;
    }
}
