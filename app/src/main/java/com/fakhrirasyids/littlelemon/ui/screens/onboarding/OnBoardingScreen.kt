package com.fakhrirasyids.littlelemon.ui.screens.onboarding

import com.fakhrirasyids.littlelemon.R
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fakhrirasyids.littlelemon.ui.navigation.HomeDestination
import com.fakhrirasyids.littlelemon.utils.EMAIL
import com.fakhrirasyids.littlelemon.utils.FIRST_NAME
import com.fakhrirasyids.littlelemon.utils.LAST_NAME
import com.fakhrirasyids.littlelemon.utils.USER_PROFILE
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isValidEmail by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .height(100.dp)
                .padding(top = 24.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Dark banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Section title
            Text(
                text = "Personal information",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF333333),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // First Name
            Text(
                text = "First name",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            // Last Name
            Text(
                text = "Last name",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            // Email
            Text(
                text = "Email",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(top = 16.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isValidEmail = Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                isError = !isValidEmail,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                supportingText = {
                    if (!isValidEmail) Text("Invalid email", color = MaterialTheme.colorScheme.error)
                },
                trailingIcon = {
                    if (!isValidEmail)
                        Icon(Icons.Filled.Info, contentDescription = "error", tint = MaterialTheme.colorScheme.error)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Register button
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        Toast.makeText(
                            context,
                            "Registration unsuccessful. Please enter all data.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        sharedPreferences.edit(commit = true) {
                            putString(FIRST_NAME, firstName)
                            putString(LAST_NAME, lastName)
                            putString(EMAIL, email)
                        }
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate(HomeDestination.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC800),
                    contentColor = Color.Black
                )
            ) {
                Text("Register", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    OnBoardingScreen(navController)
}
