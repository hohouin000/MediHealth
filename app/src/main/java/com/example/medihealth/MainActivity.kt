package com.example.medihealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.medihealth.fragments.ClassFragment
import com.example.medihealth.fragments.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
   private val classFragment = ClassFragment()
   private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(classFragment)

        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_class -> replaceFragment(classFragment)
                R.id.ic_setting ->replaceFragment(settingFragment)
            }
            true
        }
    }

    private fun replaceFragment (fragment: Fragment){
        if (fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}

