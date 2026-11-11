package com.example.test_appweer2

import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter.ACTION_TECH_DISCOVERED
import android.nfc.NfcAdapter.EXTRA_TAG
import android.nfc.Tag
import android.nfc.tech.NfcV
import android.util.Log
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.test_appweer2.nfc.RFCommands.*


interface Fun_Interface {

    // -------------------------------------------------------------------------
    // Stato condiviso
    // -------------------------------------------------------------------------
    val textState: MutableState<String>
    var MessageBuffer: ByteArray
    var FileContent: String?


    // -------------------------------------------------------------------------
    // Invio dei comandi NFC
    // -------------------------------------------------------------------------
    fun SendCommands(intent: Intent, context: Context) {
        val payload: List<ByteArray> = splitOnDelimiter(MessageBuffer)
        var logRes: ByteArray

        if (ACTION_TECH_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(EXTRA_TAG) ?: return
            val nfcV = NfcV.get(tag) ?: return

            if (MessageBuffer.isNotEmpty()) {
                try {
                    nfcV.connect()
                    for (packet in payload) {
                        logRes = nfcV.transceive(packet)
                        Thread.sleep(200)

                        Log.d(
                            "NFCV",
                            logRes.joinToString(" ") { "%02X".format(it) }
                        )

                        Toast.makeText(
                            context,
                            logRes.toHexString().format(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    nfcV.close()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
                MessageBuffer = byteArrayOf()
            }
        }
    }


    // -------------------------------------------------------------------------
    // Preparazione del file da inviare via NFC (da completare)
    // -------------------------------------------------------------------------
    fun Packetize() {
        val sizeFileContent = 128
        val contentBuffer = arrayOfNulls<String>(sizeFileContent)
        // TODO: implementare la logica di packetizzazione
    }


    // -------------------------------------------------------------------------
    // Dialog di inserimento data/seriale
    // --------------------------------------------------------S-----------------
    @Composable
    fun DialogBox(dateTime: String, seriale: String?) {
        // Etichette UI
        val codice = "Codice:"
        val tensione = "Tensione:"
        val dataAttuale = "Data corrente:"
        val dataScadenza = "Data scadenza:"
        val dataProduzione = "Data produzione:"

        var showDialog by remember { mutableStateOf(true) }

        if (showDialog) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(top = 200.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    // ----- Titolo e descrizione -----
                    Text(
                        text = "Data Writer / Reader",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp, start = 50.dp, top = 15.dp),
                        fontSize = 30.sp,
                        style = TextStyle()
                    )
                    Text(
                        text = "Avvicina il Transponder al NFC del dispositivo e premi CONFIRM",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(16.dp)          // padding interno al riquadro
                    )
                    // ----- Pulsante di conferma -----
                    Button(onClick = {
                        when {
                            dateTime.isEmpty() -> {
                                MessageBuffer = cmd_ReadCodice
                                MessageBuffer += cmd_ReadSRAM
                                MessageBuffer += cmd_StartI2CRead
                            }

                            seriale.isNullOrEmpty() -> {
                                MessageBuffer = CreatePacketDataCurrent(dateTime)
                                MessageBuffer += CreatepacketDataEnd(dateTime)
                                MessageBuffer += cmd_StartI2CRead
                            }

                            else -> {
                                MessageBuffer = CreatePacketDataCurrent(dateTime)
                                MessageBuffer += CreatepacketDataEnd(dateTime)
                                MessageBuffer += PacketizeSerial(seriale)
                                MessageBuffer += cmd_StartI2CRead
                            }
                        }
                    }) {
                        Text("Confirm")
                    }
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) { // ----- Etichette -----
                    listOf(
                        codice,
                        tensione,
                        dataAttuale,
                        dataScadenza,
                        dataProduzione
                    ).forEach { label ->
                        Text(
                            text = label,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = Color.DarkGray,
                            fontSize = 20.sp,
                            style = TextStyle()
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }


    // -------------------------------------------------------------------------
    // Creazione pacchetti data
    // -------------------------------------------------------------------------
    private fun CreatePacketDataCurrent(dateTime: String): ByteArray {
        val packet = cmd_WriteDateStart.copyOf()
        val startIdx = 9
        val bytes = intTo3Bytes(dateTime.toInt())

        require(startIdx + bytes.size <= packet.size) {
            "Spazio insufficiente nell'array di destinazione"
        }

        bytes.forEachIndexed { offset, b -> packet[startIdx + offset] = b }
        return packet
    }

    private fun CreatepacketDataEnd(dateTime: String): ByteArray {
        val packet = cmd_WriteDateEnd.copyOf()
        val startIdx = 9
        val endValue = dateTime.toInt() + 10_000
        val bytes = intTo3Bytes(endValue)

        require(startIdx + bytes.size <= packet.size) {
            "Spazio insufficiente nell'array di destinazione"
        }

        bytes.forEachIndexed { offset, b -> packet[startIdx + offset] = b }
        return packet
    }


    // -------------------------------------------------------------------------
    // Caricamento file firmware
    // -------------------------------------------------------------------------
    @Composable
    fun LoadFile(textState: MutableState<String>) {
        val context = LocalContext.current

        val pickFile = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                FileContent = context.contentResolver
                    .openInputStream(it)
                    ?.bufferedReader()
                    ?.use { r -> r.readText() }

                textState.value = FileContent ?: ""
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { pickFile.launch("text/plain") }
        ) {
            Text("LOAD FIRMWARE")
        }
    }


    // -------------------------------------------------------------------------
    // UI per programmazione rapida (solo test)
    // -------------------------------------------------------------------------
    @Composable
    fun OneClickProg(dateTime: String) {
        val context = LocalContext.current
        var seriale by remember { mutableStateOf(SerialeFun()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = seriale,
                onValueChange = { seriale = it },
                label = { Text("Enter seriale") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    context.startActivity(
                        Intent(context, ComposeWriteActivity::class.java)
                            .putExtra("Payload", dateTime)
                            .putExtra("Seriale", seriale)
                    )
                }
            ) {
                Text("Programma Data+Seriale")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    context.startActivity(
                        Intent(context, ReadActivity::class.java)
                    )
                }
            ) {
                Text("Leggi Data+Seriale+Battery Level")
            }
        }
    }


    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------
    private fun SerialeFun(): String {
        var num = 1_000_000
        num++
        return num.toString()
    }

    private fun PacketizeSerial(ser: String): ByteArray {
        val array = cmd_Seriale.copyOf()
        val startIdx = 7
        val bytes = intTo3Bytes(ser.toInt())

        require(startIdx + bytes.size <= array.size) {
            "Spazio insufficiente nell'array di destinazione"
        }

        bytes.forEachIndexed { offset, b -> array[startIdx + offset] = b }
        return array
    }

    fun intTo3Bytes(value: Int): ByteArray {
        require(value in 0..0xFFFFFF) { "value must fit in 3 bytes" }
        return byteArrayOf(
            ((value ushr 16) and 0xFF).toByte(),
            ((value ushr 8) and 0xFF).toByte(),
            (value and 0xFF).toByte()
        )
    }

    fun splitOnDelimiter(
        source: ByteArray,
        delimiter: Byte = 0x0d.toByte()
    ): List<ByteArray> {
        val result = mutableListOf<ByteArray>()
        var start = 0
        val blockSize = 240

        // Controllo header specifico
        val header = source[1].toInt() and 0xFF
        if (header != 0xC1 && header != 0xC0 && header != 0xD2) {
            for (i in source.indices) {
                if (source[i] == delimiter) {
                    val fragment = source.copyOfRange(start, i + 1)
                    val padded = ByteArray(blockSize)
                    fragment.copyInto(padded)
                    result.add(padded)
                    start = i + 1
                }
            }
            if (start < source.size) {
                val fragment = source.copyOfRange(start, source.size)
                val padded = ByteArray(blockSize)
                fragment.copyInto(padded)
                result.add(padded)
            }
        } else {
            result.add(source)
        }
        return result
    }
}
