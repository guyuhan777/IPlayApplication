package com.iplay.iplayapplication.customComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/5/22.
 */

public class EditTextWithLabel extends LinearLayout {

    TextView zone;

    EditText editText;

    public EditTextWithLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.phone_with_area_code,this,true);
        zone = (TextView) findViewById(R.id.zone_text);
        editText = (EditText) findViewById(R.id.phone_edit);
    }

    public boolean setSubViewFocus(){
        return editText.requestFocus();
    }

}
