package com.example.littlelemon2.screens

import android.app.Activity.MODE_PRIVATE
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon2.R
import com.example.littlelemon2.navigation.Onboarding
import com.example.littlelemon2.SharedPrefsKeys
import com.example.littlelemon2.navigation.Home
import com.example.littlelemon2.ui.theme.Highlight1
import com.example.littlelemon2.ui.theme.Markazi
import com.example.littlelemon2.ui.theme.Primary2

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(SharedPrefsKeys.SHARED_PREFS_NAME, MODE_PRIVATE)
    var firstName = sharedPreferences.getString(SharedPrefsKeys.FIRST_NAME, "") ?: ""
    var lastName = sharedPreferences.getString(SharedPrefsKeys.LAST_NAME, "") ?: ""
    var email = sharedPreferences.getString(SharedPrefsKeys.EMAIL, "") ?: ""

    val focusRequesters = List(3) { FocusRequester() }
    val registerButtonFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .clickable {
                        navController.navigate(Home.route)
                    },
                contentScale = ContentScale.FillBounds
            )
        }

        Text(
            text = "Personal Information",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            fontFamily = Markazi,
            modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp)
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = {firstName = it},
            label = { Text("First Name") },
            singleLine = true,
            enabled = false,
            keyboardOptions = KeyboardOptions (
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesters[1].requestFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .focusRequester(focusRequesters[0])
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = {lastName = it},
            label = { Text("Last Name") },
            singleLine = true,
            enabled = false,
            keyboardOptions = KeyboardOptions (
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesters[2].requestFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .focusRequester(focusRequesters[1])
        )
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            singleLine = true,
            enabled = false,
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    registerButtonFocusRequester.requestFocus()
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .focusRequester(focusRequesters[2])
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate(Onboarding.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary2,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Highlight1),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .focusRequester(registerButtonFocusRequester)
        ) {
            Text(
                text = "Log Out",
                fontSize = 18.sp
            )
        }
    }
}