package com.personal.buyapp.utils

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.personal.buyapp.R
import kotlinx.android.synthetic.main.animation_dialog.*

class CompleteActionDialog : AppCompatDialogFragment() {

    var onDone: (() -> Unit)? = null

    companion object {
        fun show(manager: FragmentManager, onDone: (() -> Unit)? = null) {
            val dialog = CompleteActionDialog()
            dialog.onDone = onDone
            dialog.show(manager)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.animation_dialog,container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        tick_animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                log("animation repeated")
            }

            override fun onAnimationEnd(animation: Animator?) {
                dismiss()
                onDone?.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
                log("animation canceled")
            }

            override fun onAnimationStart(animation: Animator?) {
                log("animation started")
            }
        })

        view.setOnClickListener {
            dismiss()
        }
    }
    fun show(manager: FragmentManager) {
        show(manager, "dialog")
    }

}