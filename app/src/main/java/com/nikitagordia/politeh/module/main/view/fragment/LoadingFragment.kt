package com.nikitagordia.politeh.module.main.view.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikitagordia.cosin.Cosin
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.main.presenter.MainPresenter
import com.nikitagordia.politeh.module.main.presenter.MainPresenterInterface
import com.nikitagordia.politeh.module.main.view.MainActivity

/**
 * Created by nikitagordia on 5/25/18.
 */

class LoadingFragment : Fragment() {

    private lateinit var cosin: Cosin
    private lateinit var textView: TextView

    private lateinit var presenter: MainPresenterInterface

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater?.inflate(R.layout.fragment_loading, container, false)!!
        cosin = v.findViewById<Cosin>(R.id.cosin)
        textView = v.findViewById<TextView>(R.id.textView)

        return v
    }

    override fun onStart() {
        super.onStart()
    }

    fun loadingEnded() {
        cosin.setEnd()
        val fadeout = ObjectAnimator.ofFloat(textView, "alpha", 1F, 0F)
        fadeout.duration = 900
        val fadein = ObjectAnimator.ofFloat(textView, "alpha", 0F, 1F)
        fadein.duration = 900
        fadeout.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                if (activity == null) return
                textView.text = resources.getString(R.string.welcome_student)
                fadein.start()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })
        fadein.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                if (activity == null) return
                (activity as MainActivity).updateFragment(true)
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })
        fadeout.start()
    }

    companion object {
        fun getInstance() : LoadingFragment {
            return LoadingFragment()
        }
    }
}