package com.example.littlelemon2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.example.littlelemon2.navigation.Home
import com.example.littlelemon2.navigation.Onboarding
import com.example.littlelemon2.navigation.Navigation
import com.example.littlelemon2.ui.theme.LittleLemon2Theme
import com.example.littlelemon2.utiltiy.AppDatabase
import com.example.littlelemon2.utiltiy.MenuItemNetwork
import com.example.littlelemon2.utiltiy.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(SharedPrefsKeys.SHARED_PREFS_NAME, MODE_PRIVATE)
        val isRegistered = sharedPreferences.getBoolean(SharedPrefsKeys.IS_REGISTERED, false)

        val database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "menuDatabase").build()

        enableEdgeToEdge()
        setContent {
            LittleLemon2Theme {

                suspend fun fetchMenu(): List<MenuItemNetwork> {
                    val client = HttpClient(Android) {
                        install(ContentNegotiation) {
                            json(contentType = ContentType("text", "plain"))
                        }
                    }
                    return try {
                        client
                            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                            .body<MenuNetwork>().menu
                    } catch (e: Exception) {
                        emptyList<MenuItemNetwork>()
                    }
                }

                suspend fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
                    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
                    withContext(Dispatchers.IO) {
                        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
                    }
                }

                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        if (database.menuItemDao().isEmpty()) {
                            val menuItemsNetwork = fetchMenu()
                            saveMenuToDatabase(menuItemsNetwork)
                        }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Navigation(if (isRegistered) Home.route else Onboarding.route, database)
                }
            }
        }
    }
}

