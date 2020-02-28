package com.jqk.mydemo.fragment

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityFragmentBinding

class FragmentActivity : AppCompatActivity() {
    val FIRST: Int = 1
    val SECOND: Int = 2
    val THIRD: Int = 3

    lateinit var b: ActivityFragmentBinding
    lateinit var firstFragment: FirstFragment
    lateinit var secondFragment: SecondFragment
    lateinit var thirdFragment: ThirdFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_fragment)
        b.view = this

        if (savedInstanceState != null) {

            if (supportFragmentManager.findFragmentByTag("firstFragment") == null) {
                firstFragment = FirstFragment()
            } else {
                firstFragment = supportFragmentManager.findFragmentByTag("firstFragment") as FirstFragment
            }

            if (supportFragmentManager.findFragmentByTag("secondFragment") == null) {
                secondFragment = SecondFragment()
            } else {
                secondFragment = supportFragmentManager.findFragmentByTag("secondFragment") as SecondFragment
            }

            if (supportFragmentManager.findFragmentByTag("thirdFragment") == null) {
                thirdFragment = ThirdFragment()
            } else {
                thirdFragment = supportFragmentManager.findFragmentByTag("thirdFragment") as ThirdFragment
            }
        } else {
            firstFragment = FirstFragment()
            secondFragment = SecondFragment()
            thirdFragment = ThirdFragment()
        }

        showFragment(FIRST)
    }

    fun showFragment(type: Int) {
        when (type) {
            FIRST -> {
                if (!firstFragment.isAdded) {
                    supportFragmentManager.beginTransaction().remove(firstFragment).commit()
                    supportFragmentManager.beginTransaction().add(R.id.fragmentView, firstFragment, "firstFragment").commit()
                } else {
                    supportFragmentManager.beginTransaction().show(firstFragment).commit()
                }

                if (secondFragment.isAdded) {
                    supportFragmentManager.beginTransaction().hide(secondFragment).commit()
                }

                if (thirdFragment.isAdded) {
                    supportFragmentManager.beginTransaction().hide(thirdFragment).commit()
                }
            }
            SECOND -> {
                if (!secondFragment.isAdded) {
                    supportFragmentManager.beginTransaction().remove(secondFragment).commit()
                    supportFragmentManager.beginTransaction().add(R.id.fragmentView, secondFragment, "secondFragment").commit()
                } else {
                    supportFragmentManager.beginTransaction().show(secondFragment).commit()
                }

                if (firstFragment.isAdded) {
                    supportFragmentManager.beginTransaction().hide(firstFragment).commit()
                }

                if (thirdFragment.isAdded) {
                    supportFragmentManager.beginTransaction().hide(thirdFragment).commit()
                }
            }
            THIRD -> {
                if (!thirdFragment.isAdded) {
                    supportFragmentManager.beginTransaction().remove(thirdFragment).commit()
                    supportFragmentManager.beginTransaction().add(R.id.fragmentView, thirdFragment, "thirdFragment").commit()
                } else {
                    supportFragmentManager.beginTransaction().show(thirdFragment).commit()
                }

                if (firstFragment.isAdded) {
                    supportFragmentManager.beginTransaction().hide(firstFragment).commit()
                }

                if (secondFragment.isAdded) {
                    supportFragmentManager.beginTransaction().hide(secondFragment).commit()
                }
            }
        }
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