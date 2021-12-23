package com.example.notesholder

import com.example.notesholder.Utils.setSubtitleName
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notesholder.R

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        setSubtitleName(actionBar, "Settings")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}