package net.frazionz.android.activity.ui.profile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.frazionz.android.R;

import org.w3c.dom.Text;

public class CardProfileData extends LinearLayout {

    private final TextView dataKey;
    private final TextView dataValue;
    private final TextView dataIcon;
    private View view;
    private final int drawableIcon;
    private final String keyDataStr;
    private final String valueDataStr;

    public CardProfileData(Context context, int drawableIcon, String keyDataStr, String valueDataStr) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        this.drawableIcon = drawableIcon;
        this.keyDataStr = keyDataStr;
        this.valueDataStr = valueDataStr;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.card_profile_data, this, true);

        dataIcon = view.findViewById(R.id.dataIcon);
        dataKey = view.findViewById(R.id.dataKey);
        dataValue = view.findViewById(R.id.dataValue);

    }

    public TextView getDataIcon() {
        return dataIcon;
    }

    public int getDrawableIcon() {
        return drawableIcon;
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
