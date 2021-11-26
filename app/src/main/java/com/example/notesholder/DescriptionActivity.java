package com.example.notesholder;

import static com.example.notesholder.descriptionFragment.ARG_INDEX;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        if (Utils.isLandscape(getResources())) {
            finish();
            return;
        }

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.description_container, descriptionFragment.newInstance(getIntent().getExtras().getInt(ARG_INDEX)))
                    .commit();
    }
}