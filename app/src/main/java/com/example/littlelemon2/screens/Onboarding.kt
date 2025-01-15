package com.example.littlelemon2.screens

import android.app.Activity.MODE_PRIVATE
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon2.R
import com.example.littlelemon2.navigation.Home
import com.example.littlelemon2.SharedPrefsKeys
import com.example.littlelemon2.ui.theme.Highlight1
import com.example.littlelemon2.ui.theme.Karla
import com.example.littlelemon2.ui.theme.Markazi
import com.example.littlelemon2.ui.theme.Primary1
import com.example.littlelemon2.ui.theme.Primary2

@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(SharedPrefsKeys.SHARED_PREFS_NAME, MODE_PRIVATE)
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
                    .size(width = 200.dp, height = 50.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Primary1
            )
        ) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier
                    .padding(50.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                color = Color.White,
                fontSize = 25.sp,
                fontFamily = Karla
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
                if(Submit(firstName, lastName, email, context, sharedPreferences)) {
                    navController.navigate(Home.route)
                }
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
                text = "Register",
                fontSize = 18.sp
            )
        }
    }
}

fun Submit(firstName: String, lastName: String, email: String, context: Context, sharedPreferences: SharedPreferences): Boolean {
    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
        Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
        return false
    }
    sharedPreferences.edit()
        .putBoolean(SharedPrefsKeys.IS_REGISTERED, true)
        .putString(SharedPrefsKeys.FIRST_NAME, firstName)
        .putString(SharedPrefsKeys.LAST_NAME, lastName)
        .putString(SharedPrefsKeys.EMAIL, email)
        .apply()
    Toast.makeText(context,"Registration successful!", Toast.LENGTH_SHORT).show()
    return true
}