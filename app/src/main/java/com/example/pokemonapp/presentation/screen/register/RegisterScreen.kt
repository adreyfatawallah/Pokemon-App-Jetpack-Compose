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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemonapp.R
import com.example.pokemonapp.presentation.component.PasswordTextField
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.ui.theme.defaultButtonModifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateBack: () -> Unit,
) {
    val viewModel = viewModel<RegisterViewModel>()

    val username = viewModel.username
    val password = viewModel.password
    val retypePassword = viewModel.retypePassword

    val isUsernameValid = viewModel.isUsernameValid
    val isPasswordValid = viewModel.isPasswordValid
    val isPasswordMatch = viewModel.isPasswordMatch

    Scaffold(
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
                value = username,
                onValueChange = viewModel::updateUsername,
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
                onValueChange = viewModel::updatePassword,
                contentDescription = stringResource(R.string.content_desc_toggle_password),
                isError = password.isNotEmpty() && !isPasswordValid,
                supportingText = {
                    if (password.isNotEmpty() && !isPasswordValid) {
                        Text(stringResource(R.string.msg_validation_username))
                    }
                }
            )
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.lbl_retype_password),
                value = retypePassword,
                onValueChange = viewModel::updateRetypePassword,
                contentDescription = stringResource(R.string.content_desc_toggle_retype_password),
                isError = retypePassword.isNotEmpty() && !isPasswordMatch,
                supportingText = {
                    if (retypePassword.isNotEmpty() && !isPasswordMatch) {
                        Text(stringResource(R.string.msg_validation_password))
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = defaultButtonModifier
                    .fillMaxWidth(),
                enabled = isUsernameValid && isPasswordValid && isPasswordMatch,
                onClick = { },
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