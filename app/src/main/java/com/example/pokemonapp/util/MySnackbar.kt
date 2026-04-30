package com.example.pokemonapp.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MySnackbarHost(
    snackbarHostState: SnackbarHostState
) {
    return SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.windowInsetsPadding(WindowInsets.ime)
    )
}

fun resultWithAction(
    result: SnackbarResult,
    action: () -> Unit
) {
    when(result) {
        SnackbarResult.ActionPerformed -> action()
        SnackbarResult.Dismissed -> action()
    }
}