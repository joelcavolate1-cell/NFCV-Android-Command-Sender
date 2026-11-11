package com.example.test_appweer2

import android.widget.Toast
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcV
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test_appweer2.nfc.RFCommands.*

class AdvancedCommands : ComponentActivity(), Fun_Interface {

    override val textState: MutableState<String> = mutableStateOf("")
    override var MessageBuffer: ByteArray = byteArrayOf()

    override var FileContent: String? = null
    private val nfcAdapter by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    private var pendingIntent: PendingIntent? = null
    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcV::class.java.name))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_SHORT).show()
        } else if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Please turn on NFC", Toast.LENGTH_SHORT).show()
        }
        // PendingIntent corretto per tutte le versioni Android
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        // IntentFilter NFC
        val nfcVFilter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        try {
            nfcVFilter.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            e.printStackTrace()
        }
        intentFiltersArray = arrayOf(nfcVFilter)

        setContent {
            WriteScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        SendCommands(intent, this)
    }


    @Composable
    fun WriteScreen() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                MessageBuffer = cmd_EventDetection_3D

            }) {

                Text("EVENT DETECTION_3D")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                MessageBuffer = cmd_EventDetection_A8

            }) {

                Text("EVENT DETECTION_A8")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                    MessageBuffer = cmd_readTagStatus

                }) {

                Text("Read status")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                MessageBuffer = cmd_EnableSRAM

            }) {

                Text("SRAM ENABLE")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                MessageBuffer = cmd_activateI2CSlave

            }) {

                Text("ACTIVATE cmd_activateI2CSlave")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                MessageBuffer = cmd_ReadSRAM
                Toast.makeText(applicationContext, "Successfully Read SRAM!", Toast.LENGTH_SHORT).show()

            }) {

                Text("READ SRAM")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                MessageBuffer = cmd_WriteSRAM
                Toast.makeText(applicationContext, "Successfully Written SRAM!", Toast.LENGTH_SHORT).show()

            }) {

                Text("WRITE SRAM")

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth(),
                onClick = {

                    MessageBuffer = cmd_Seriale
                    Toast.makeText(applicationContext, "Successfully Written SRAM!", Toast.LENGTH_SHORT).show()

                }) {

                Text("SCRIVI SERIALE")

            }
            Spacer(Modifier.height(20.dp))
            LoadFile(textState)
        }
    }
}



