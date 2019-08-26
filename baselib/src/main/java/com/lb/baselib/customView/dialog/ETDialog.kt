package com.lb.baselib.customView.dialog

import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.lb.baselib.R
import com.lb.baselib.util.AppUtil

/**
 * @author 田桂森 2019/5/14
 * edittext dialog
 */
class ETDialog : BaseLDialog<ETDialog>() {

    override fun layoutRes(): Int = R.layout.layout_edit_dialog
    private var isShowPosBtn = false
    private var isShowNegBtn = false

    private var hideText: CharSequence = ""
    private var editText: CharSequence = ""
    lateinit var et_content: EditText

    private var positiveButtonText: CharSequence = "確定"
    private var positiveButtonClickListener: OnETListener? = null
    private var positiveButtonColor: Int = AppUtil.getColor(R.color.sg_common_red)
    private var inputType=0
    private var negativeButtonText: CharSequence = "取消"
    private var negativeButtonClickListener: OnETListener? = null
    private var negativeButtonColor: Int = AppUtil.getColor(R.color.sg_black)

    init {
        setWidthDp(56f * 5.5f)
        setBackgroundDrawableRes(R.drawable.def_dialog_bg)
    }


    /**
     * View Handler
     * The management of the relevant state of the view is written here
     */
    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {

                holder.getView<TextInputLayout>(R.id.text_input_layout)
                        .setHint(hideText)
                val et_content = holder.getView<EditText>(R.id.et_content)
                et_content.setText(editText)
                et_content.setSelection(editText.length)
                if(inputType==NUMBER){
                    et_content.inputType= InputType.TYPE_CLASS_NUMBER
                }
                
                if (!isShowNegBtn && !isShowPosBtn) {
                    holder.getView<LinearLayout>(R.id.bottomBtnLayout).visibility = View.GONE
                } else {
                    holder.getView<Button>(R.id.neg_btn).apply {
                        visibility = if (isShowNegBtn) View.VISIBLE else View.GONE
                        text = negativeButtonText
                        if (negativeButtonColor != 0) {
                            setTextColor(negativeButtonColor)
                        }
                        setOnClickListener {
                            negativeButtonClickListener?.onClick(et_content.text.toString(), it)
                            dialog.dismiss()
                        }
                    }

                    holder.getView<Button>(R.id.pos_btn).apply {
                        visibility = if (isShowPosBtn) View.VISIBLE else View.GONE
                        text = positiveButtonText
                        if (positiveButtonColor != 0) {
                            setTextColor(positiveButtonColor)
                        }
                        setOnClickListener {
                            positiveButtonClickListener?.onClick(et_content.text.toString(), it)
                            dialog.dismiss()
                        }
                    }
                }

            }
        }
    }


    override fun layoutView(): View? = null


    fun setHide(hide: CharSequence): ETDialog {
        hideText = hide
        return this
    }

    fun setText(text: CharSequence): ETDialog {
        editText = text
        return this
    }

    fun getText(): String {
        return et_content.text.toString()
    }

    /**
     * Left Button
     */
    @JvmOverloads
    fun setNegativeButton(text: CharSequence, listener: OnETListener? = null, @ColorInt color: Int = negativeButtonColor): ETDialog {
        isShowNegBtn = true
        negativeButtonText = text
        negativeButtonClickListener = listener
        negativeButtonColor = color
        return this
    }

    /**
     * Right Button
     */
    @JvmOverloads
    fun setPositiveButton(text: CharSequence,
                          listener: OnETListener? = null,
                          @ColorInt color: Int = positiveButtonColor): ETDialog {
        isShowPosBtn = true
        positiveButtonText = text
        positiveButtonClickListener = listener
        positiveButtonColor = color
        return this
    }

    private var mListener: OnETListener? = null

    fun setOnDialogClickListener(listener: OnETListener) {
        this.mListener = listener
    }

    fun setInputType(type: Int): ETDialog {
         inputType = type
        return this
    }

    companion object {
        const val NUMBER = 1
        fun init(fragmentManager: FragmentManager): ETDialog {
            return ETDialog().apply { setFragmentManager(fragmentManager) }
        }

    }

}

interface OnETListener {
    fun onClick(text: String, view: View)
}

//SimpleMsgDialog.init(supportFragmentManager)
//.setTitle("Material Style")
//.setMessage("This is Material Design dialog!")
//.setLeftButton("Decline", View.OnClickListener {
//    Toast.makeText(this@MainActivity, "Decline", Toast.LENGTH_SHORT).show()
//})
//.setRightButton("Accept", View.OnClickListener {
//    Toast.makeText(this@MainActivity, "Accept", Toast.LENGTH_SHORT).show()
//})
//.show()