package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myjb.dev.R;

import butterknife.BindView;

public class CustomView extends GeneralView {

    @BindView(R.id.text)
    TextView textView;

    public CustomView(Context context) {
        super(context);
    }

    public void onBindData(int position, Object data) {
        textView.setText((String) data);
        textView.setTag(position);
    }

    public View createView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list, parent, false);
    }
}
