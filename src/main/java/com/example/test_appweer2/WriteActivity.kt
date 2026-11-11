package com.example.test_appweer2
// DA UNIRE CON L'ALTRA FUNZIONE TANTO FANNO LA STESSA COSA ADESSO (READACTIVITY)
import android.R.string.cancel
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcV
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.test_appweer2.nfc.RFCommands.cmd_WriteSRAM
import java.util.Date

class ComposeWriteActivity : ComponentActivity(), Fun_Interface {

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

        val DateTime = intent.getStringExtra("Payload")
        val Seriale = intent.getStringExtra("Seriale")

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
        val nfcVfilter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        try {
            nfcVfilter.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            e.printStackTrace()
        }
        intentFiltersArray = arrayOf(nfcVfilter)

        setContent {
            if (DateTime != null) {
                DialogBox(DateTime, Seriale)
            }
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
}