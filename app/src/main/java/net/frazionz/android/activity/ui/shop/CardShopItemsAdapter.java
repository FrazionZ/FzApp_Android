package net.frazionz.android.activity.ui.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.frazionz.android.R;
import net.frazionz.android.activity.ui.profile.CardProfileData;
import net.frazionz.android.utils.FZUtils;

import java.util.ArrayList;

public class CardShopItemsAdapter extends ArrayAdapter<CardShopItemData> {

    public CardShopItemsAdapter(@NonNull Context context, ArrayList<CardShopItemData> cardShopItemDataArrayList) {
        super(context, 0, cardShopItemDataArrayList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_shopitem_data, parent, false);
        }
        CardShopItemData courseModel = getItem(position);
        courseModel.getShopItems().updateName();
        new FZUtils.DownloadImageTask((ImageView) listitemView.findViewById(R.id.dataIcon))
                .execute("https://api.frazionz.net/public/minecraft/textures/getItems.php?f="+courseModel.getShopItems().getTextureName());
        ((TextView) listitemView.findViewById(R.id.dataKey)).setText(courseModel.getShopItems().getMinecraftItemName());
        ((TextView) listitemView.findViewById(R.id.dataValue)).setText(Html.fromHtml(
                courseModel.getShopItems().getActualBuyPrice() + "" + "<font color=\"#43fe3b\">$</font>" + " / "+
                        courseModel.getShopItems().getActualSellPrice()+"<font color=\"#ff3f43\">$</font>"));
        return listitemView;
    }

}
