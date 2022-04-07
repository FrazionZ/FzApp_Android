package net.frazionz.android.activity.ui.profile.tabs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.frazionz.android.R;

public class CardInventoryItemData extends LinearLayout {

    private InvItemStack invItemStack;
    private int slot;
    private View view;

    public CardInventoryItemData(Context context, InvItemStack invItemStack, int slot) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.card_inventory_item, this, true);

        ImageView itemTexture = view.findViewById(R.id.itemTexture);
        if(invItemStack != null)
            this.invItemStack = invItemStack;
        else {
            Toast.makeText(view.getContext(), "Slot "+slot+" is empty", Toast.LENGTH_SHORT).show();
        }

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

    }

    public InvItemStack getInvItemStack() {
        return invItemStack;
    }

    public static class InvItemStack {

        private String minecraftItemRealName;
        private int slot;

        public String getMinecraftItemRealName() {
            return minecraftItemRealName;
        }

        public int getSlot() {
            return slot;
        }

        @Override
        public String toString() {
            return "InvItemStack{" +
                    "minecraftItemRealName='" + minecraftItemRealName + '\'' +
                    ", slot=" + slot +
                    '}';
        }
    }
}

