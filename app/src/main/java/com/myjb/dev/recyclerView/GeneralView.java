package com.myjb.dev.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.ButterKnife;

public abstract class GeneralView<T> extends FrameLayout {
    public GeneralView(Context context) {
        super(context);

        View childView = createView(this);
        setLayoutParams(childView.getLayoutParams());
        addView(childView);

        ButterKnife.bind(this);
    }

    public abstract void onBindData(int position, T data);

    public abstract View createView(ViewGroup parent);
}
