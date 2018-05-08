package com.myjb.dev.recyclerView;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.myjb.dev.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_list)
    RecyclerView recyclerview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_recyclerview);

        ButterKnife.bind(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));

        List<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cheese)));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, list);

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(recyclerAdapter);
        alphaAdapter.setDuration(100);

        recyclerview.setAdapter(alphaAdapter);

        recyclerview.setItemAnimator(new FadeInLeftAnimator());
        recyclerview.getItemAnimator().setAddDuration(1000);
        recyclerview.getItemAnimator().setRemoveDuration(1000);
        recyclerview.getItemAnimator().setMoveDuration(1000);
        recyclerview.getItemAnimator().setChangeDuration(1000);

    }
}
