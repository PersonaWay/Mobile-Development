package com.capstone.personaway.custom

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.capstone.personaway.R

class custom_password @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        // Jangan atur ulang inputType secara manual, biarkan dari XML/TextInputLayout
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val parentLayout = getParentTextInputLayout()
                if (s != null && s.length < 8) {
                    parentLayout?.error = context.getString(R.string.error_password)
                } else {
                    parentLayout?.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getParentTextInputLayout(): TextInputLayout? {
        var parentView = parent
        while (parentView is ViewGroup) {
            if (parentView is TextInputLayout) {
                return parentView
            }
            parentView = parentView.parent
        }
        return null
    }
}
