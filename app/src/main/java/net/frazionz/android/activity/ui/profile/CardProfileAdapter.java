package net.frazionz.android.activity.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.frazionz.android.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CardProfileAdapter extends ArrayAdapter<CardProfileData> {

    public CardProfileAdapter(@NonNull Context context, ArrayList<CardProfileData> cardProfileDataArrayList) {
        super(context, 0, cardProfileDataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_profile_data, parent, false);
        }
        CardProfileData courseModel = getItem(position);
        if(position == 0)
            ((LinearLayout) listitemView.findViewById(R.id.layoutCardProfileData)).setPadding(30, 30, 30, 0);
        else if(position == getCount()-1)
            ((LinearLayout) listitemView.findViewById(R.id.layoutCardProfileData)).setPadding(30, 0, 30, 30);
        ((TextView) listitemView.findViewById(R.id.dataIcon)).setCompoundDrawablesWithIntrinsicBounds(courseModel.getDrawableIcon(), 0, 0, 0);
        ((TextView) listitemView.findViewById(R.id.dataKey)).setText(courseModel.getKeyDataStr());
        ((TextView) listitemView.findViewById(R.id.dataValue)).setText(courseModel.getValueDataStr());
        return listitemView;
    }

}
