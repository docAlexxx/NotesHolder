package com.example.notesholder;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();

        Utils.setSubtitleName(actionBar,"Settings");

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}