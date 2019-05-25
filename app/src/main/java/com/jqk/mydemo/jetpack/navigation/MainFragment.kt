package com.jqk.mydemo.jetpack.navigation

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.FragmentMainBinding

/**
 * Created by jiqingke
 * on 2019/2/2
 */
class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.blankFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == R.id.mainFragment) {
                Toast.makeText(context, "我被点了", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}