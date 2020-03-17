package com.jqk.mydemo.fragment

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.fragment.app.Fragment
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityFragmentBinding

class FragmentActivity : AppCompatActivity() {
    val FIRST: Int = 0
    val SECOND: Int = 1
    val THIRD: Int = 2

    lateinit var b: ActivityFragmentBinding
    lateinit var firstFragment: FirstFragment
    lateinit var secondFragment: SecondFragment
    lateinit var thirdFragment: ThirdFragment

    var tabFragments: MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_fragment)
        b.view = this

        if (savedInstanceState != null) {

            if (supportFragmentManager.findFragmentByTag(FirstFragment().javaClass.name) == null) {
                firstFragment = FirstFragment()
            } else {
                firstFragment = supportFragmentManager.findFragmentByTag(FirstFragment().javaClass.name) as FirstFragment
            }

            if (supportFragmentManager.findFragmentByTag(SecondFragment().javaClass.name) == null) {
                secondFragment = SecondFragment()
            } else {
                secondFragment = supportFragmentManager.findFragmentByTag(SecondFragment().javaClass.name) as SecondFragment
            }

            if (supportFragmentManager.findFragmentByTag(ThirdFragment().javaClass.name) == null) {
                thirdFragment = ThirdFragment()
            } else {
                thirdFragment = supportFragmentManager.findFragmentByTag(ThirdFragment().javaClass.name) as ThirdFragment
            }
        } else {
            firstFragment = FirstFragment()
            secondFragment = SecondFragment()
            thirdFragment = ThirdFragment()
        }

        tabFragments.add(firstFragment)
        tabFragments.add(secondFragment)
        tabFragments.add(thirdFragment)

        showFragment(FIRST)
    }

    fun showFragment(type: Int) {
        val ft = supportFragmentManager.beginTransaction()
        for (i in tabFragments) {
            ft.hide(i)
        }
        if (!tabFragments[type].isAdded) {
            ft.add(R.id.fragmentView, tabFragments[type], tabFragments[type].javaClass.name)
            ft.show(tabFragments[type])
        } else {
            ft.show(tabFragments[type])
        }
        ft.commit()
    }

    fun first(view: View) {
        showFragment(FIRST)
    }

    fun second(view: View) {
        showFragment(SECOND)
    }

    fun third(view: View) {
        showFragment(THIRD)
    }
}