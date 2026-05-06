package com.example.pokemonapp.feature.auth.presentation.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<RegisterViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onRegisterState by viewModel.onRegisterState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val lblOK = stringResource(R.string.lbl_OK)

    LaunchedEffect(onRegisterState) {
        when(val state = onRegisterState) {
            is OnRegisterState.Failure -> {
                val result = snackbarHostState.showSnackbar(
                    message = state.message,
                    actionLabel = lblOK,
                    duration = SnackbarDuration.Short
                )
                resultWithAction(
                    result,
                    viewModel::resetOnRegisterState
                )
            }
            is OnRegisterState.Success -> {
                val result = snackbarHostState.showSnackbar(
                    message = state.message,
                    actionLabel = lblOK,
                    duration = SnackbarDuration.Short
                )
                resultWithAction(
                    result,
                    navigateBack
                )
            }
            else -> { }
        }
    }

    Scaffold(
        snackbarHost = {
            MySnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.btn_register))
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when(onRegisterState) {
            is OnRegisterState.Loading -> {
                LoadingCard(modifier = Modifier.padding(innerPadding))
            }
            else -> {
                RegisterForm(
                    modifier = Modifier.padding(innerPadding),
                    username = uiState.username,
                    updateUsername = viewModel::updateUsername,
                    isUsernameValid = uiState.isUsernameValid,
                    password = uiState.password,
                    updatePassword = viewModel::updatePassword,
                    isPasswordValid = uiState.isPasswordValid,
                    retypePassword = uiState.retypePassword,
                    updateRetypePassword = viewModel::updateRetypePassword,
                    isPasswordMatch = uiState.isPasswordMatch,
                    register = viewModel::register,
                    isIdle = onRegisterState is OnRegisterState.Idle
                )
            }
        }
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    username: String,
    updateUsername: (String) -> Unit,
    isUsernameValid: Boolean,
    password: String,
    updatePassword: (String) -> Unit,
    isPasswordValid: Boolean,
    retypePassword: String,
    updateRetypePassword: (String) -> Unit,
    isPasswordMatch: Boolean,
    register: () -> Unit,
    isIdle: Boolean,
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = updateUsername,
            label = { Text(stringResource(R.string.lbl_username)) },
            isError = username.isNotEmpty() && !isUsernameValid,
            supportingText = {
                if (username.isNotEmpty() && !isUsernameValid) {
                    Text(stringResource(R.string.msg_validation_username))
                }
            },
            singleLine = true
        )
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.lbl_password),
            value = password,
            onValueChange = updatePassword,
            contentDescription = stringResource(R.string.content_desc_toggle_password),
            isError = password.isNotEmpty() && !isPasswordValid,
            supportingText = {
                if (password.isNotEmpty() && !isPasswordValid) {
                    Text(stringResource(R.string.msg_validation_password))
                }
            }
        )
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.lbl_retype_password),
            value = retypePassword,
            onValueChange = updateRetypePassword,
            contentDescription = stringResource(R.string.content_desc_toggle_retype_password),
            isError = retypePassword.isNotEmpty() && !isPasswordMatch,
            supportingText = {
                if (retypePassword.isNotEmpty() && !isPasswordMatch) {
                    Text(stringResource(R.string.msg_validation_retype_password))
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = defaultButtonModifier
                .fillMaxWidth(),
            enabled = isUsernameValid && isPasswordValid && isPasswordMatch && isIdle,
            onClick = register,
        ) {
            Text(text = stringResource(R.string.btn_register))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterFormPreview() {
    PokemonAppTheme {
        RegisterForm(
            username = "Username",
            updateUsername = { },
            isUsernameValid = true,
            password = "Password",
            updatePassword = { },
            isPasswordValid = true,
            retypePassword = "Password",
            updateRetypePassword = { },
            isPasswordMatch = true,
            register = { },
            isIdle = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    PokemonAppTheme {
        RegisterScreen(
            navigateBack = { }
        )
    }
}