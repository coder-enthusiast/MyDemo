package com.jqk.mydemo.file.dir

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.jqk.mydemo.R

class DirFragmentActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var fragmentTransaction : FragmentTransaction
    lateinit var fragment: DirFragment
    lateinit var fragment2: DirFragment2
    lateinit var button: Button
    lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dirfragment)

        button = findViewById(R.id.fragment)
        button2 = findViewById(R.id.fragment2)

        button.setOnClickListener(this)
        button2.setOnClickListener(this)

        fragment = DirFragment()
        fragment2 = DirFragment2()

        chooseFragment(1)
    }

    fun chooseFragment(type: Int) {
        when (type) {
            1 -> {
                fragmentTransaction = supportFragmentManager.beginTransaction()

                if (!fragment.isAdded()) {
                    fragmentTransaction.add(R.id.contentView, fragment, "dialFragment")
                }

                fragmentTransaction.show(fragment)
                fragmentTransaction.hide(fragment2)
                fragmentTransaction.commit()

            }
            2 -> {
                fragmentTransaction = supportFragmentManager.beginTransaction()

                if (!fragment2.isAdded()) {
                    fragmentTransaction.add(R.id.contentView, fragment2, "dialFragment")
                }

                fragmentTransaction.show(fragment2)
                fragmentTransaction.hide(fragment)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fragment -> {
                chooseFragment(1)
            }

            R.id.fragment2 -> {
                chooseFragment(2)
            }
        }
    }
}
