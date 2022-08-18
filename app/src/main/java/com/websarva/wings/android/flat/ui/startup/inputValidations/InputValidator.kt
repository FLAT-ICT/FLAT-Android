package com.websarva.wings.android.flat.ui.startup.inputValidations

import com.websarva.wings.android.flat.R

object InputValidator {

    fun getNameErrorIdOrNull(input: String): Int? {
        return when {
            // input.length < 2 -> R.string.name_too_short
            //etc..
            input.isEmpty() -> R.string.name_empty
            input.length >= 10 -> R.string.name_too_long
            else -> null
        }
    }

    fun getCardNumberErrorIdOrNull(input: String): Int? {
        return when {
            // input.length < 16 -> R.string.card_number_too_short
            //etc..
            else -> null
        }
    }

    fun getPasswordErrorIdOrNull(input: String): Int? {
        val alphaNumeric = Regex("[a-zA-Z0-9]+")
        return when {
            !input.matches(alphaNumeric) -> R.string.invalid_character
            input.length < 8 -> R.string.password_too_short
            input.length > 255 -> R.string.password_too_long
            //etc..
            else -> null
        }
    }

    fun getPasswordConfirmErrorIdOrNull(input: String, other: String): Int? {
        val alphaNumeric = Regex("[a-zA-Z0-9]+")
        return when {
            input != other -> R.string.password_retype_mismatch
            !input.matches(alphaNumeric) -> R.string.invalid_character
            input.length < 8 -> R.string.password_too_short
            input.length > 255 -> R.string.password_too_long
            else -> null
        }
    }
}