package com.example.pokemonapp.feature.auth.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokemonapp.R
import com.example.pokemonapp.component.LoadingCard
import com.example.pokemonapp.component.MySnackbarHost
import com.example.pokemonapp.component.resultWithAction
import com.example.pokemonapp.feature.auth.presentation.component.PasswordTextField
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.ui.theme.defaultButtonModifier
import com.example.pokemonapp.util.RequestState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navigateToList: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onLoginState by viewModel.onLoginState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val lblOk = stringResource(R.string.lbl_OK)

    LaunchedEffect(onLoginState) {
        when (val state = onLoginState) {
            is RequestState.Success -> {
                val result = snackbarHostState.showSnackbar(
                    message = state.data,
                    actionLabel = lblOk,
                    duration = SnackbarDuration.Short
                )
                resultWithAction(result, navigateToList)
            }

            is RequestState.Error -> {
                val result = snackbarHostState.showSnackbar(
                    message = state.message,
                    actionLabel = lblOk,
                    duration = SnackbarDuration.Short
                )
                resultWithAction(result, viewModel::resetOnLoginState)
            }

            else -> {}
        }
    }

    Scaffold(
        snackbarHost = {
            MySnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(onLoginState) {
                is RequestState.Loading -> {
                    LoadingCard()
                }
                else -> {
                    LoginForm(
                        username = uiState.username,
                        updateUsername = viewModel::updateUsername,
                        password = uiState.password,
                        updatePassword = viewModel::updatePassword,
                        login = viewModel::login,
                        isIdle = onLoginState is RequestState.Idle,
                        navigateToRegister = navigateToRegister
                    )
                }
            }
        }
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    username: String,
    updateUsername: (String) -> Unit,
    password: String,
    updatePassword: (String) -> Unit,
    login: () -> Unit,
    isIdle: Boolean,
    navigateToRegister: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displaySmall,
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = updateUsername,
            label = { Text(stringResource(R.string.lbl_username)) },
        )
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.lbl_password),
            value = password,
            onValueChange = updatePassword,
            contentDescription = stringResource(R.string.content_desc_toggle_password),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = defaultButtonModifier
                .fillMaxWidth(),
            enabled = username.isNotEmpty() && password.isNotEmpty() && isIdle,
            onClick = login
        ) {
            Text(text = stringResource(R.string.btn_login))
        }
        OutlinedButton(
            modifier = defaultButtonModifier
                .fillMaxWidth(),
            onClick = {
                updateUsername("")
                updatePassword("")
                navigateToRegister()
            }
        ) {
            Text(text = stringResource(R.string.btn_register))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginFormPreview() {
    PokemonAppTheme {
        LoginForm(
            username = "Username",
            updateUsername = { },
            password = "Password",
            updatePassword = { },
            login = { },
            isIdle = true,
            navigateToRegister = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PokemonAppTheme {
        LoginScreen(
            navigateToList = {},
            navigateToRegister = {}
        )
    }
}