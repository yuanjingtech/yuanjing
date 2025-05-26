package com.yuanjingtech

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

var customAppLocale by mutableStateOf<String?>(null)

expect object LocalAppLocale {
    val current: String @Composable get

    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}

var customAppThemeIsDark by mutableStateOf<Boolean?>(null)

expect object LocalAppTheme {
    val current: Boolean @Composable get

    @Composable
    infix fun provides(value: Boolean?): ProvidedValue<*>
}

var customAppDensity by mutableStateOf<Density?>(null)

object LocalAppDensity {
    val current: Density
        @Composable get() = LocalDensity.current

    @Composable
    infix fun provides(value: Density?): ProvidedValue<*> {
        val new = value ?: LocalDensity.current
        return LocalDensity.provides(new)
    }
}

@Composable
fun AppEnvironment(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppLocale provides customAppLocale,
    ) {
        key(customAppLocale) {
            CompositionLocalProvider(
                LocalAppDensity provides customAppDensity,
            ) {
                key(customAppDensity) {
                    CompositionLocalProvider(
                        LocalAppTheme provides customAppThemeIsDark,
                    ) {
                        key(customAppThemeIsDark) {

                            content()
                        }
                    }
                }
            }
        }
    }
}