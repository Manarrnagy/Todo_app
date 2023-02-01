package com.route.todoapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.route.todoapp.R
import com.route.todoapp.fragments.AddBottomSheetFragment
import com.route.todoapp.fragments.ListFragment
import com.route.todoapp.fragments.SettingsFragment

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    lateinit var floatingButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNav = findViewById(R.id.bottomNavbar)
        floatingButton = findViewById(R.id.floatingButton)
        pushFragment(ListFragment())
        bottomNav.setOnItemSelectedListener(object :NavigationBarView.OnItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if (item.itemId == R.id.listItem) {
                    pushFragment(ListFragment())
                } else {
                    pushFragment(SettingsFragment())
                }
                return true
            }
        })
        floatingButton.setOnClickListener{
            val addBottomSheet = AddBottomSheetFragment()
            addBottomSheet.onAddClicked = object : AddBottomSheetFragment.OnAddClicked{
                override fun onClick() {

                }

            }
            addBottomSheet.show(supportFragmentManager,"")
        }
    }
    fun pushFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}