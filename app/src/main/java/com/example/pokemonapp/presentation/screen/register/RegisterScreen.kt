package com.example.pokemonapp.presentation.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.R
import com.example.pokemonapp.presentation.component.PasswordTextField
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.ui.theme.defaultButtonModifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateBack: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var retypePassword by remember { mutableStateOf("") }

    val isUsernameValid = username.length > 4
    val isPasswordValid = password.length > 4
    val isPasswordMatch = password == retypePassword

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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = {
                    username = it
                },
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
                onValueChange = { password = it },
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
                onValueChange = { retypePassword = it },
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