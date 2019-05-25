package com.jqk.mydemo.jetpack.navigation

import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.jqk.mydemo.BlankFragment
import com.jqk.mydemo.BlankFragment2
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityNavigationuiBinding

/**
 * Created by jiqingke
 * on 2019/2/12
 */
class NavigationUIActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener {

    lateinit var binding: ActivityNavigationuiBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigationui)
        // toolbar
//        val navController = findNavController(R.id.fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        // CollapsingToolbarLayout
//        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        val navController = findNavController(R.id.fragment)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
        // Action bar
//        navController = findNavController(R.id.fragment)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setSupportActionBar(binding.toolbar)
//        setupActionBarWithNavController(navController, appBarConfiguration)
        // drawerLayout
        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navController)
        // ActionBar
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        // BottomNavigationView
        binding.bottomNav.setupWithNavController(navController)

    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("jiqingke", "onSupportNavigateUp()")
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}