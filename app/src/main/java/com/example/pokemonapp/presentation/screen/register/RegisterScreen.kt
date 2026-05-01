package com.example.pokemonapp.presentation.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.R
import com.example.pokemonapp.presentation.component.PasswordTextField
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.ui.theme.defaultButtonModifier
import com.example.pokemonapp.util.MySnackbarHost
import com.example.pokemonapp.util.resultWithAction
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current

    val viewModel = koinViewModel<RegisterViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event) {
                is RegisterEvent.OnRegister -> {
                    if (event.isSuccess) {
                        val result = snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_register_success),
                            actionLabel = context.getString(R.string.lbl_OK),
                            duration = SnackbarDuration.Short
                        )
                        resultWithAction(result, navigateBack)
                    } else {
                        snackbarHostState.showSnackbar(
                            message = event.message ?: context.getString(R.string.msg_register_failure)
                        )
                    }
                }
            }
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.username,
                onValueChange = viewModel::updateUsername,
                label = { Text(stringResource(R.string.lbl_username)) },
                isError = viewModel.username.isNotEmpty() && !viewModel.isUsernameValid,
                supportingText = {
                    if (viewModel.username.isNotEmpty() && !viewModel.isUsernameValid) {
                        Text(stringResource(R.string.msg_validation_username))
                    }
                },
                singleLine = true
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.lbl_password),
                value = viewModel.password,
                onValueChange = viewModel::updatePassword,
                contentDescription = stringResource(R.string.content_desc_toggle_password),
                isError = viewModel.password.isNotEmpty() && !viewModel.isPasswordValid,
                supportingText = {
                    if (viewModel.password.isNotEmpty() && !viewModel.isPasswordValid) {
                        Text(stringResource(R.string.msg_validation_password))
                    }
                }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.lbl_retype_password),
                value = viewModel.retypePassword,
                onValueChange = viewModel::updateRetypePassword,
                contentDescription = stringResource(R.string.content_desc_toggle_retype_password),
                isError = viewModel.retypePassword.isNotEmpty() && !viewModel.isPasswordMatch,
                supportingText = {
                    if (viewModel.retypePassword.isNotEmpty() && !viewModel.isPasswordMatch) {
                        Text(stringResource(R.string.msg_validation_retype_password))
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = defaultButtonModifier
                    .fillMaxWidth(),
                enabled = viewModel.isUsernameValid && viewModel.isPasswordValid && viewModel.isPasswordMatch,
                onClick = viewModel::register,
            ) {
                Text(text = stringResource(R.string.btn_register))
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    PokemonAppTheme {
        RegisterScreen(navigateBack = {})
    }
}