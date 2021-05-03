package com.kryzl.meetthedevs.base.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.kryzl.meetthedevs.R

/**
 * Attributes:
 * <attr name="actionButtonStyle" format="reference"/>
 * <attr name="actionButtonColor" format="color"/>
 */
class ActionButton
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = R.attr.actionButtonStyle
) :
    AppCompatButton(context, attrs, defStyleAttr) {

    init {
        attrs?.let { setAttributes(it, defStyleAttr) }
    }

    private fun setAttributes(attributeSet: AttributeSet, defStyleAttr: Int) {
        val array = context.obtainStyledAttributes(
            attributeSet, R.styleable.ActionButton,
            defStyleAttr, R.style.button_action
        )
        val buttonColor: Int = array.getResourceId(
            R.styleable.ActionButton_actionButtonColor,
            R.color.primary
        )
        this.backgroundTintList = ContextCompat.getColorStateList(context, buttonColor)
        array.recycle()
    }
}
