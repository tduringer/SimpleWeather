package com.trintduringer.simpleweather.core.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource


sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class StringResourceId(
        val id: StringResource,
        val args: Array<Any> = arrayOf()
    ) : UiText

    class StringResourceInt(
        val int: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceInt -> androidx.compose.ui.res.stringResource(
                id = int,
                formatArgs = args
            )

            is StringResourceId -> org.jetbrains.compose.resources.stringResource(
                resource = id,
                formatArgs = args
            )
        }
    }
}