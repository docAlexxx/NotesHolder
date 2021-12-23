package com.example.notesholder

import androidx.appcompat.app.AppCompatActivity
import com.example.notesholder.ChangeResult
import android.content.SharedPreferences
import android.os.Bundle
import com.example.notesholder.R
import android.widget.Toast
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.example.notesholder.NotesFragment
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.example.notesholder.InfoFragment
import com.example.notesholder.SettingsFragment
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), ChangeResult {
    private var sharedPref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = getPreferences(MODE_PRIVATE)
        Notes.notes = ArrayList()
        val savedNotes = sharedPref!!.getString(Notes.KEY, null)
        if (savedNotes == null || savedNotes.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val type = object : TypeToken<ArrayList<Notes?>?>() {}.type
                Notes.notes = GsonBuilder().create().fromJson(savedNotes, type)
            } catch (e: JsonSyntaxException) {
                Toast.makeText(this, "Ошибка трансформации", Toast.LENGTH_SHORT).show()
            }
        }
        if (savedInstanceState == null) {
            addFragment(NotesFragment())
        }
        setupToolbar()
    }

    private fun addFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupNavigationDrawer(toolbar)
    }

    private fun setupNavigationDrawer(toolbar: Toolbar) {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            val id = item.itemId
            when (id) {
                R.id.about_point -> {
                    openAboutFragment()
                    drawer.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.settings_point -> {
                    openSettingsFragment()
                    drawer.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.exit_point -> {
                    dialogBeforeExit()
                    return@setNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.about_point -> {
                openAboutFragment()
                return true
            }
            R.id.settings_point -> {
                openSettingsFragment()
                return true
            }
            R.id.exit_point -> {
                dialogBeforeExit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAboutFragment() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("")
            .replace(R.id.fragment_container, InfoFragment())
            .commit()
    }

    private fun openSettingsFragment() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("")
            .replace(R.id.fragment_container, SettingsFragment())
            .commit()
    }

    override fun onDestroy() {
        Toast.makeText(this, "NotesHolder has closed", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }

    private fun dialogBeforeExit() {
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setIcon(R.drawable.exit)
            .setPositiveButton("Yes") { dialogInterface, i -> finish() }
            .setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int ->
                Snackbar.make(
                    findViewById(R.id.container),
                    "You still stay in the App",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .show()
    }

    override fun onChangeResult(name: String?, date: Date?) {
        Notes.notes!![Notes.currentIndex].name = name
        Notes.notes!![Notes.currentIndex].date = date!!
    }
}