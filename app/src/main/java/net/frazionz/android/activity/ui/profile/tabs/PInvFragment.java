package net.frazionz.android.activity.ui.profile.tabs;

import net.frazionz.android.R;
import net.frazionz.android.utils.FZUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class PInvFragment extends IPFragment {


    private View root;
    Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile_inv, container, false);
        reloadDataProfile();
        return root;
    }

    @Override
    public void reloadDataProfile() {
        GridView invGrid = root.findViewById(R.id.invGrid);
        ArrayList<CardInventoryItemData> cardInventoryItemDataList = new ArrayList<CardInventoryItemData>();
        ArrayList<CardInventoryItemData.InvItemStack> invItemStacks = new Gson().fromJson(new Gson().fromJson(FZUtils.apiFactionProfile.toString(), JsonObject.class).getAsJsonArray("inventory").toString(), new TypeToken<List<CardInventoryItemData.InvItemStack>>(){}.getType());
        for(int slot=0; slot<35; slot++){
            CardInventoryItemData.InvItemStack returnItemStack = null;
            for(CardInventoryItemData.InvItemStack invItemStack : invItemStacks)
                if(invItemStack.getSlot() == slot)
                    returnItemStack = invItemStack;
            cardInventoryItemDataList.add(new CardInventoryItemData(this.getContext(), returnItemStack, slot));
        }
        invGrid.setAdapter(new CardInventoryItemAdapter(this.getContext(), cardInventoryItemDataList));
    }
}
