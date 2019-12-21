package com.jqk.jetpacklibrary.navigation

import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.jqk.jetpacklibrary.R
import com.jqk.jetpacklibrary.databinding.ActivityNavigationBinding

/**
 * Created by jiqingke
 * on 2019/2/1
 */
class NavigationActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener{
    lateinit var binding: ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)
        // 用代码的方式创建 NavHostFragment
//        val finalHost = NavHostFragment.create(R.navigation.my_navi)
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment, finalHost)
//                .setPrimaryNavigationFragment(finalHost) // this is the equivalent to app:defaultNavHost="true"
//                .commit()
    }

    // 拦截系统后退按钮，效果跟app:defaultNavHost="true"一致
//    override fun onSupportNavigateUp() = findNavController(BlankFragment()).navigateUp()

    override fun onSupportNavigateUp(): Boolean {
        Log.d("jiqingke", "返回")
        return findNavController(BlankFragment()).navigateUp()
    }
    override fun onFragmentInteraction(uri: Uri) {

    }
}