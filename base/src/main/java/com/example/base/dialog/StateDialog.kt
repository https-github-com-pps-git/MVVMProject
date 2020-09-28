package com.example.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.base.R
import com.example.base.databinding.StateDialogBinding
import com.example.base.viewmodel.StateBean

class StateDialog {

    private var mDialog: Dialog
    private var mDataBinding: StateDialogBinding
    constructor(context: Context){
        mDialog = Dialog(context, R.style.DialogStyle)
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.state_dialog,null,false)
        var view = mDataBinding?.root

        mDialog.setContentView(view)

        //获取屏幕的宽度和高度
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height

        val window = mDialog.window
        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.width = (width * 0.6f).toInt()
        layoutParams?.height = (height * 0.3f).toInt()

        mDialog.window?.attributes = layoutParams;
    }

    fun showDialog(msg: StateBean){
        mDataBinding.stateBean = msg
        mDataBinding.executePendingBindings()
        if (!mDialog.isShowing){
            mDialog.show()
        }
    }

    fun dismiss() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

}