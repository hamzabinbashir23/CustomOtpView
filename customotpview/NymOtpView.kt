package com.hamza.customotp.otpfield

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.hamza.customotp.R
import com.hamza.customotp.databinding.ItemOtpDashBinding
import com.hamza.customotp.databinding.ItemOtpSymbolBinding
import com.hamza.customotp.databinding.NymOtpViewBinding

class NymOtpView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: NymOtpViewBinding
    private val dashView: ItemOtpDashBinding

    private var fieldRadius: Float = 0.0f
    private var fieldBGColor: Int = 0
    private var strokeColor: Int = 0
    private var textColor: Int = 0
    private var otpTextListeners: OtpTextListeners? = null
    private var isStrokeActive=false

    init {
        with(LayoutInflater.from(context)) {
            binding = NymOtpViewBinding.inflate(this, this@NymOtpView, true)
            dashView = ItemOtpDashBinding.inflate(this, this@NymOtpView, false)
        }
        initAttributes(attrs, defStyleAttr)
    }

    private fun initAttributes(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val attributes = context.obtainStyledAttributes(
            attrs, R.styleable.NymOtpView, defStyleAttr, defStyleAttr
        )

        with(attributes) {
            val codeLength = getInteger(R.styleable.NymOtpView_fieldLength, 4).coerceIn(1, 6)
            fieldRadius =
                getDimension(
                    R.styleable.NymOtpView_fieldRadius,
                    resources.getDimension(com.intuit.sdp.R.dimen._12sdp)
                )
            fieldBGColor = getColor(
                R.styleable.NymOtpView_textBackgroundColor,
                ContextCompat.getColor(context, R.color.light_gray)
            )
            strokeColor = getColor(
                R.styleable.NymOtpView_fieldStrokeColor,
                ContextCompat.getColor(context, R.color.cerulean_blue)
            )
            textColor = getColor(
                R.styleable.NymOtpView_textColor,
                ContextCompat.getColor(context, R.color.ds_on_secondary)
            )
            val dashPosition = getInteger(R.styleable.NymOtpView_dashPosition, 0)

            setOtpFields(codeLength)
            setDashAtPosition(dashPosition)

            recycle()
        }
    }

    fun setOtpFields(amount: Int) {
        if (amount <= 0) return

        binding.root.removeAllViews()
        val otpFields = List(amount) {
            ItemOtpSymbolBinding.inflate(LayoutInflater.from(context), this, false).root
        }

        otpTextListeners = OtpTextListeners(otpFields).apply { attachAll() }
        for (i in otpFields.indices) {
            binding.root.addView(otpFields[i])
        }
        setTextColor()
        setFieldBackground(isStrokeActive)
    }

    fun setDashAtPosition(index: Int) {
        if (index >= binding.root.childCount - 1) return
        if (index <= 0) {
            binding.root.removeView(dashView.root)
            return
        }

        binding.root.addView(dashView.root, index)
    }

    fun clearOtp() {
        binding.root.children.forEach { if (it is EditText) it.text.clear() }
    }

    fun onChange(doOnChange: ((value: String) -> Unit)?) {
        otpTextListeners?.onChange = doOnChange
    }

    private fun setTextColor() {
        binding.root.children.forEach { (it as EditText).setTextColor(textColor) }
    }

    private fun setFieldBackground(hasStroke: Boolean) {
        val drawable = getDrawable(strokeColor, fieldBGColor, fieldRadius, hasStroke)
        binding.root.children.forEach { it.background = drawable }
    }

     fun setActiveFieldBackground() {
        val drawable =
            otpTextListeners?.let { getDrawable(strokeColor, fieldBGColor, fieldRadius, it.activeStrokeColor) }
         otpTextListeners?.otpFields?.get(otpTextListeners?.index!!)!!.background = drawable
//        binding.root.children.forEach { it.background = drawable }
    }

    private fun getDrawable(
        strokeColor: Int,
        fieldColor: Int,
        fieldRadius: Float,
        hasStroke: Boolean
    ): GradientDrawable {

        return GradientDrawable().apply {
            setColor(fieldColor)
            cornerRadius = fieldRadius
            if (hasStroke) setColor(strokeColor)
        }
    }
}