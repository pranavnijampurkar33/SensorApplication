package com.example.pratham.testintegration03;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Hotels",
                "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#678FB4"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_round_icon);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Banks",
                "We carefully verify all banks before add them into the app",
                Color.parseColor("#65B0B4"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_round_icon);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Stores",
                "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_round_icon);

        PaperOnboardingPage lastPage = new PaperOnboardingPage("Last",
                "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_round_icon);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(lastPage);

        final PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int i, int i1) {
                if(onBoardingFragment.getElements().size()-1 == i1){
                    setResult(Activity.RESULT_OK,new Intent());
                    finish();
                }
            }
        });
    }
}
