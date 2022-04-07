package net.frazionz.android.activity.ui.profile.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.frazionz.android.R;
import net.frazionz.android.activity.ui.profile.CardProfileData;

import java.util.ArrayList;

public class CardInventoryItemAdapter extends ArrayAdapter<CardInventoryItemData> {

    public CardInventoryItemAdapter(@NonNull Context context, ArrayList<CardInventoryItemData> cardProfileDataArrayList) {
        super(context, 0, cardProfileDataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_inventory_item, parent, false);
        }
        CardInventoryItemData cardInventoryItemData = getItem(position);
        cardInventoryItemData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Ceci est un test", Toast.LENGTH_LONG).show();
            }
        });
        return listitemView;
    }

}
