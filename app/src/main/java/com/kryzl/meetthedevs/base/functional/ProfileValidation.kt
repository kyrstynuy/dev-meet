package com.kryzl.meetthedevs.base.functional

import android.util.Patterns
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import timber.log.Timber

val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

const val NAME_PATTERN = "^[A-Za-z\\u00d1\\u00f1,. \\-']+[ ]+[A-Za-z\\u00d1\\u00f1,. \\-']+\$"

fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidMobile(): Boolean {
    return try {
        val numberProto = phoneUtil.parse(this, "PH")
        phoneUtil.isValidNumber(numberProto) && phoneUtil.getNumberType(numberProto) == PhoneNumberUtil.PhoneNumberType.MOBILE
    } catch (exception: NumberParseException) {
        Timber.d("NumberParseException was thrown: $exception")
        false
    }
}

fun String.isValidName(): Boolean {
    return this.matches(Regex(NAME_PATTERN))
}
