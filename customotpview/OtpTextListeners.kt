package com.hamza.customotp.otpfield

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText

class OtpTextListeners(val otpFields: List<EditText>) {

    var onChange: ((String) -> Unit)? = null

    var activeStrokeColor = false

    var index = 0
    private var lastIndex = ""

    fun attachAll() {
        otpFields.forEachIndexed { i, et ->
            val nextField = if (i < otpFields.lastIndex) otpFields[i + 1] else null
            val prevField = if (i != 0) otpFields[i - 1] else null

            et.addTextChangedListener(OtpTextWatcher(prevField, nextField))
            et.onFocusChangeListener = OtpFocusChangeListener()
            et.setOnKeyListener(OtpKeyListener())
        }
    }

    inner class OtpTextWatcher(private val prevField: EditText?, private val nextField: EditText?) :
        TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (s?.length) {
                1 -> nextField?.requestFocus()
                0 -> prevField?.requestFocus()
            }

            for(i in otpFields.indices) {
                if (otpFields[i].text.isNotEmpty()) {
                    activeStrokeColor = true

                    if (lastIndex.isEmpty()){
                        lastIndex +=i
                        break
                    }
                    var check :String = i.toString()
                    if (!lastIndex.contains(check)) {
                        index = i
                        lastIndex +=index
                        break
                    }
                }else {
                    activeStrokeColor = false
                    index = i
                   lastIndex = lastIndex.dropLast(1)
                    break
                }
            }

            onChange?.invoke(otpFields.joinToString("") { it.text })
        }
    }

    inner class OtpFocusChangeListener() : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (v == null) return

            if (hasFocus) {
                var lastEmpty = v

                for (i in otpFields.indices.reversed()) {
                    if (otpFields[i].text.isEmpty()) {
                        lastEmpty = otpFields[i]

                    } else {
                        break
                    }
                }
                lastEmpty!!.requestFocus()
            }
        }
    }

    inner class OtpKeyListener() : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (keyCode == KeyEvent.KEYCODE_DEL && event?.action == KeyEvent.ACTION_DOWN) {
                for (i in otpFields.indices.reversed()) {
                    if (otpFields[i].text.isNotEmpty()) {
                        otpFields[i].text.clear()
                        otpFields[i].requestFocus()

                        break
                    }
                }
                return true
            }
            return false
        }
    }
}