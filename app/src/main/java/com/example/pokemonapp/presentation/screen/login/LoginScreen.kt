package com.example.pokemonapp.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemonapp.R
import com.example.pokemonapp.presentation.component.PasswordTextField
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.ui.theme.defaultButtonModifier
import com.example.pokemonapp.util.MySnackbarHost

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit
) {
    val viewModel = viewModel<LoginViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }

    val username = viewModel.username
    val password = viewModel.password

    LaunchedEffect(Unit) {
        viewModel.events.collect {event ->
            when(event) {
                is LoginEvent.CallBack -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            MySnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = viewModel::updateUsername,
                label = { Text(stringResource(R.string.lbl_username)) },
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.lbl_password),
                value = password,
                onValueChange = viewModel::updatePassword,
                contentDescription = stringResource(R.string.content_desc_toggle_password),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = defaultButtonModifier
                    .fillMaxWidth(),
                enabled = username.isNotEmpty() && password.isNotEmpty(),
                onClick = viewModel::login
            ) {
                Text(text = stringResource(R.string.btn_login))
            }
            OutlinedButton(
                modifier = defaultButtonModifier
                    .fillMaxWidth(),
                onClick = navigateToRegister
            ) {
                Text(text = stringResource(R.string.btn_register))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PokemonAppTheme {
        LoginScreen(navigateToRegister = {})
    }
}