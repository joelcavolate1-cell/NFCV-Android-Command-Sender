package com.example.test_appweer2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.test_appweer2.ui.theme.TEST_APPWEER2Theme
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity() : ComponentActivity(), Fun_Interface{

    override val textState: MutableState<String> = mutableStateOf("")
    override var MessageBuffer: ByteArray = byteArrayOf()
    override var FileContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TEST_APPWEER2Theme {
                TEST_APPWEER2App()
            }
        }
    }


    //Global Variable
    enum class AppDestinations(
        val label: String,
        val icon: ImageVector,
    ) {
        HOME(label = "Home", icon = Icons.Default.Home),
        ADVANCED(label = "Advanced", icon = Icons.Default.Star),
        PROFILE(label = "Profile", icon = Icons.Default.AccountBox),
        //SIGMA(label = "SIGMA", icon = Icons.Default.Build),
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TEST_APPWEER2App() {
        var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "DIGITAL CONTROL",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    AppDestinations.entries.forEach {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    it.icon,
                                    contentDescription = it.label
                                )
                            },
                            label = { Text(it.label) },
                            selected = it == currentDestination,
                            onClick = { currentDestination = it }
                        )
                    }
                }
            }
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {

                when (currentDestination) {
                    AppDestinations.HOME -> HomeScreen()
                    AppDestinations.ADVANCED -> Dev_auth()
                    AppDestinations.PROFILE -> Text("Profile")
                }
            }
        }
    }

    @Composable
    fun Dev_auth() {
        val context = LocalContext.current
        val password = "digital"
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("PASSWORD ADMIN") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (text == password) {
             context.startActivity(
                 Intent(context, AdvancedCommands::class.java)
                )
        }
    }


    @Composable
    fun HomeScreen() {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(Modifier.height(16.dp))
            ButtonNFC_DATETIME()
            Spacer(Modifier.height(16.dp))
            ButtonNFC_READ()
        }
    }


    @Composable
    private fun ButtonNFC_DATETIME() {
        val sdf = SimpleDateFormat("yyMMdd", Locale.getDefault())
        // current date and time and calling a simple date format
        val currentDateAndTime = sdf.format(Date())
        OneClickProg(currentDateAndTime)
        //Call_WriteActivity(currentDateAndTime) //Da reinserire nella versione definitiva e rimuovere oneclickprog usata solo per la prog in laboratorio.
    }

    @Composable
    private fun Call_WriteActivity(ToWrite: String) {
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(200.dp),
            onClick = {
                context.startActivity(
                    Intent(context, ComposeWriteActivity::class.java)
                            .putExtra("Payload", ToWrite)
                            .putExtra("Seriale", "")
                )
            },

            ) {
            Text(
                text = stringResource(id = R.string.WRITE),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
    @Composable
    fun ButtonNFC_READ() {
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(200.dp),
            onClick = {
                context.startActivity(
                    Intent(context, ReadActivity::class.java)
                )
            }
        ) {
            Text(
                text = stringResource(id = R.string.READ),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}