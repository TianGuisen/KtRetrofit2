package com.lb.baselib.customView.dialog

import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentManager
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.lb.baselib.R
import com.lb.baselib.util.AppUtil

/**
 * @author 田桂森 2019/5/14
 * 简单提示带确定取消的dialog
 */
class SimpleMsgDialog : BaseLDialog<SimpleMsgDialog>() {

    private var isShowTitle = false
    private var isShowPosBtn = false
    private var isShowNegBtn = false

    private var titleText: CharSequence = ""

    private var messageText: CharSequence = ""

    private var positiveButtonText: CharSequence = "確定"
    private var positiveButtonClickListener: View.OnClickListener? = null
    private var positiveButtonColor: Int = AppUtil.getColor(R.color.sg_common_red)

    private var negativeButtonText: CharSequence = "取消"
    private var negativeButtonClickListener: View.OnClickListener? = null
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
                holder.getView<TextView>(R.id.title_tv).apply {
                    visibility = if (isShowTitle) View.VISIBLE else View.GONE
                    text = titleText
                }

                holder.getView<TextView>(R.id.msg_tv).apply {
                    text = messageText
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
                            negativeButtonClickListener?.onClick(it)
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
                            positiveButtonClickListener?.onClick(it)
                            dialog.dismiss()
                        }
                    }
                }

            }
        }
    }

    override fun layoutRes(): Int = R.layout.layout_msg_dialog

    override fun layoutView(): View? = null

    /**
     * Title Text(Support Rich text)
     */
    fun setTitle(title: CharSequence): SimpleMsgDialog {
        isShowTitle = true
        titleText = title
        return this
    }

    /**
     * Message Text(Support Rich text)
     */
    fun setMessage(msg: CharSequence): SimpleMsgDialog {
        messageText = msg
        return this
    }

    /**
     * Left Button
     */
    @JvmOverloads
    fun setLeftButton(text: CharSequence,
                      listener: ((v: View) -> Unit)? = null,
                      @ColorInt color: Int = negativeButtonColor): SimpleMsgDialog {
        isShowNegBtn = true
        negativeButtonText = text
        negativeButtonClickListener = object : View.OnClickListener {
            override fun onClick(v: View) {
                if (listener == null) {
                    dismiss()
                } else {
                    listener(v)
                }
            }
        }

        negativeButtonColor = color
        return this
    }

    /**
     * Right Button
     */
    @JvmOverloads
    fun setRightButton(text: CharSequence,
                       listener: ((v: View) -> Unit)? = null,
                       @ColorInt color: Int = positiveButtonColor): SimpleMsgDialog {
        isShowPosBtn = true
        positiveButtonText = text
        listener?.let {
            positiveButtonClickListener = object : View.OnClickListener {
                override fun onClick(v: View) {
                    it(v)
                }
            }
        }
        positiveButtonColor = color
        return this
    }

    companion object {
        fun init(fragmentManager: FragmentManager): SimpleMsgDialog {
            return SimpleMsgDialog().apply { setFragmentManager(fragmentManager) }
        }
    }

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