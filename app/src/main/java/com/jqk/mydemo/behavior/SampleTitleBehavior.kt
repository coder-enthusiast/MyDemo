package com.jqk.mydemo.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.util.L

class SampleTitleBehavior : CoordinatorLayout.Behavior<TextView> {
    var deltay: Float = 0f

    constructor() : super()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        if (deltay == 0f) {
            deltay = dependency.y - child.height
        }

        L.d("deltay = " + deltay)

        var dy = dependency.y - child.height
        dy = if (dy < 0f) 0f else dy
        val y = -(dy / deltay) * child.height

        L.d("y = " + y)

        child.translationY = y

        val alpha = 1 - (dy / deltay)
        child.alpha = alpha

        return true
    }


}