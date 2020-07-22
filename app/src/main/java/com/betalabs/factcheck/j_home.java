package com.betalabs.factcheck;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class j_home extends AppCompatActivity {
    AnimatedBottomBar mMainNav;

    private FrameLayout mMainFrame;

    private j_trend j_trend;
    private j_profile j_profile;
    private j_post j_post;
    private noti noti;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_home);
        
        getSupportActionBar().hide();


        mMainNav = findViewById(R.id.bottomNavigationView);
        mMainFrame = findViewById(R.id.main_frame);

        noti  = new noti();
        j_trend = new j_trend();
        j_post = new j_post();
        j_profile = new j_profile();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        if (currentUser == null)
        {
            Intent login = new Intent(this, login.class);
            startActivity(login);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fast_out_extra_slow_in);
        fragmentManager.beginTransaction().replace(R.id.main_frame, new j_trend()).commit();





        mMainNav.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId())
                {
                    case R.id.j_nav_trend:
                    fragment = new j_trend();
                    break;


                    case R.id.j_nav_post:
                        fragment = new j_post();
                        break;


                    case R.id.j_nav_noti:
                        fragment = new noti();
                        break;

                    case R.id.j_nav_profile:
                        fragment = new j_profile();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + newTab.getId());
                }

                if (fragment!=null)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fast_out_extra_slow_in);
                    fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
                }
                else
                {
                    Toast.makeText(j_home.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

    }


}
