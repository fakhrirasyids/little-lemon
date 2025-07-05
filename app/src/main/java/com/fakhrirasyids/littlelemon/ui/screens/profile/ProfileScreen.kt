package com.fakhrirasyids.littlelemon.ui.screens.profile

import com.fakhrirasyids.littlelemon.R
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fakhrirasyids.littlelemon.ui.navigation.OnboardingDestination
import com.fakhrirasyids.littlelemon.utils.*

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString(FIRST_NAME, "") ?: ""
    val lastName = sharedPreferences.getString(LAST_NAME, "") ?: ""
    val email = sharedPreferences.getString(EMAIL, "") ?: ""

    var firstNameState by remember { mutableStateOf(firstName) }
    var lastNameState by remember { mutableStateOf(lastName) }
    var emailState by remember { mutableStateOf(email) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .height(100.dp)
                .padding(top = 16.dp, bottom = 32.dp)
        )

        // Section title
        Text(
            text = "Personal information",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // First Name
        Text(
            text = "First name",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = firstNameState,
            onValueChange = { firstNameState = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Last Name
        Text(
            text = "Last name",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = lastNameState,
            onValueChange = { lastNameState = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Email
        Text(
            text = "Email",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            singleLine = true
        )

        // Log out Button
        Button(
            onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate(OnboardingDestination.route) {
                    popUpTo(OnboardingDestination.route) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC800),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Log out", fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}
