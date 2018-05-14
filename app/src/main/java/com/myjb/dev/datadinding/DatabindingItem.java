package com.myjb.dev.datadinding;

        import android.databinding.BaseObservable;
        import android.databinding.ObservableField;
        import android.graphics.drawable.Drawable;

public class DatabindingItem extends BaseObservable {
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableField<Drawable> img = new ObservableField<>();
}
