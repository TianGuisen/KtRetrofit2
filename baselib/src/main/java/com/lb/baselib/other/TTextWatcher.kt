package com.lb.baselib.other

import android.text.Editable
import android.text.TextWatcher

/**
 * @author 田桂森 2019/6/11
 */
abstract class TTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}

