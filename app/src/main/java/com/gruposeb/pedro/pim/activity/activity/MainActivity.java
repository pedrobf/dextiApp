package com.gruposeb.pedro.pim.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gruposeb.pedro.pim.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_green_light)
                .fragment(R.layout.slide1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_red_light)
                .fragment(R.layout.slide2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.slide3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.cadastro)
                .canGoForward(false)
                .build());
    }

    public void btnEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnCadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

}
