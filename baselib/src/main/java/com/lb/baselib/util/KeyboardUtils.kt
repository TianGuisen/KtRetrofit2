package com.lb.baselib.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.lb.baselib.ToolApplication.appContext

/**
 * @author 田桂森 2019/7/5
 */
class KeyboardUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    interface OnSoftInputChangedListener {
        fun onSoftInputChanged(height: Int)
    }

    companion object {

        private var sContentViewInvisibleHeightPre: Int = 0
        private var onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
        private var onSoftInputChangedListener: OnSoftInputChangedListener? = null

        /**
         * Show the soft input.
         *
         * @param activity The activity.
         */
        fun showSoftInput(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
                view.isFocusable = true
                view.isFocusableInTouchMode = true
                view.requestFocus()
            }
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        /**
         * Show the soft input.
         *
         * @param view The view.
         */
        fun showSoftInput(view: View) {
            val imm =appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        /**
         * Hide the soft input.
         *
         * @param activity The activity.
         */
        fun hideSoftInput(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
            var view = activity.currentFocus
            if (view == null) view = View(activity)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Hide the soft input.
         *
         * @param view The view.
         */
        fun hideSoftInput(view: View) {
            val imm = appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Toggle the soft input display or not.
         */
        fun toggleSoftInput() {
            val imm =appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        /**
         * Return whether soft input is visible.
         *
         * @param activity             The activity.
         * @param minHeightOfSoftInput The minimum height of soft input.
         * @return `true`: yes<br></br>`false`: no
         */
        @JvmOverloads
        fun isSoftInputVisible(
            activity: Activity,
            minHeightOfSoftInput: Int = 200
        ): Boolean {
            return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput
        }

        private fun getContentViewInvisibleHeight(activity: Activity): Int {
            val contentView = activity.findViewById<View>(android.R.id.content)
            val outRect = Rect()
            contentView.getWindowVisibleDisplayFrame(outRect)
            return contentView.bottom - outRect.bottom
        }

        /**
         * Register soft input changed listener.
         *
         * @param activity The activity.
         * @param listener The soft input changed listener.
         */
        fun registerSoftInputChangedListener(
            activity: Activity,
            listener: OnSoftInputChangedListener
        ) {
            val flags = activity.window.attributes.flags
            if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
            val contentView = activity.findViewById<View>(android.R.id.content)
            sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity)
            onSoftInputChangedListener = listener
            onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                if (onSoftInputChangedListener != null) {
                    val height = getContentViewInvisibleHeight(activity)
                    if (sContentViewInvisibleHeightPre != height) {
                        onSoftInputChangedListener!!.onSoftInputChanged(height)
                        sContentViewInvisibleHeightPre = height
                    }
                }
            }
            contentView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        }

        /**
         * Register soft input changed listener.
         *
         * @param activity The activity.
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        fun unregisterSoftInputChangedListener(activity: Activity) {
            val contentView = activity.findViewById<View>(android.R.id.content)
            contentView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
            onSoftInputChangedListener = null
            onGlobalLayoutListener = null
        }

        /**
         * Fix the leaks of soft input.
         *
         * Call the function in [Activity.onDestroy].
         *
         * @param context The context.
         */
        fun fixSoftInputLeaks(context: Context?) {
            if (context == null) return
            val imm = appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
            val strArr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
            for (i in 0..2) {
                try {
                    val declaredField = imm.javaClass.getDeclaredField(strArr[i]) ?: continue
                    if (!declaredField.isAccessible) {
                        declaredField.isAccessible = true
                    }
                    val obj = declaredField.get(imm)
                    if (obj == null || obj !is View) continue
                    if (obj.context === context) {
                        declaredField.set(imm, null)
                    } else {
                        return
                    }
                } catch (th: Throwable) {
                    th.printStackTrace()
                }

            }
        }

        /**
         * Click blankj area to hide soft input.
         *
         * Copy the following code in ur activity.
         */
        fun clickBlankArea2HideSoftInput() {
            Log.i("KeyboardUtils", "Please refer to the following code.")
            /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // Return whether touch the view.
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
        }
    }
}
/**
 * Return whether soft input is visible.
 *
 * The minimum height is 200
 *
 * @param activity The activity.
 * @return `true`: yes<br></br>`false`: no
 */
