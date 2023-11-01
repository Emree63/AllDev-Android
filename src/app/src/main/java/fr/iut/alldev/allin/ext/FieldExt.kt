package fr.iut.alldev.allin.ext

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.iut.alldev.allin.R

const val ALLOWED_SYMBOLS = "~`!@#\$%^&*()_-+={[}]|\\:;\"'<,>.?/"

sealed class FieldErrorState(
    private val messageId: Int? = null,
    private val messageArgs:Array<out Any> = emptyArray()
){
    object NoError: FieldErrorState()

    data class TooShort(val fieldName: String, val minChar: Int)
        : FieldErrorState(R.string.FieldError_TooShort, arrayOf(fieldName, minChar))

    data class BadFormat(val fieldName: String, val format: String)
        : FieldErrorState(R.string.FieldError_BadFormat, arrayOf(fieldName, format))

    object NotIdentical: FieldErrorState(R.string.FieldError_NotIdentical)

    data class NoSpecialCharacter(val fieldName: String, val characters: String = ALLOWED_SYMBOLS) :
        FieldErrorState(R.string.FieldError_NoSpecialCharacter, arrayOf(fieldName, characters))

    data class AlreadyUsed(val value: String) :
        FieldErrorState(R.string.FieldError_AlreadyUsed, arrayOf(value))

    data class PastDate(val fieldName: String)
        : FieldErrorState(R.string.FieldError_PastDate, arrayOf(fieldName))
    data class DateOrder(val fieldName1: String, val fieldName2: String)
        : FieldErrorState(R.string.FieldError_DateOrder, arrayOf(fieldName1, fieldName2))



    @Composable
    fun errorResource() = stringResourceOrNull(id = messageId, messageArgs)
}

fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.containsCharacter(characters: CharSequence): Boolean {
    var contains = false
    characters.forEach {
        if(this.contains(it)){
            contains = true
            return@forEach
        }
    }
    return contains
}


@Composable
fun stringResourceOrNull(@StringRes id: Int?, args: Array<out Any>) = id?.let {
    stringResource(id = id, *args)
}

@Composable
fun stringResourceOrNull(@StringRes id: Int?) = id?.let {
    stringResource(id = id)
}
