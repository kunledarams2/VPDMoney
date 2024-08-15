package com.e.vpdmoney.util

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import android.widget.Toast
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec

import javax.crypto.spec.IvParameterSpec


object GlobalUtil {

    fun currencyFormat(number: Double): String? {
        val newFormat = NumberFormat.getCurrencyInstance(Locale.US) as DecimalFormat
        val symbols = newFormat.decimalFormatSymbols
        symbols.currencySymbol = "₦" // Don't use null.
        newFormat.decimalFormatSymbols = symbols
        return newFormat.format(number)
    }


    // Method to copy text to clipboard
    fun copyToClipboard(context: Context, textToCopy: String, textDescription: String) {
        // Get the ClipboardManager
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // Create a ClipData object to hold the text
        val clip = ClipData.newPlainText("Copied Text", textToCopy)

        // Set the ClipData on the clipboard
        clipboard.setPrimaryClip(clip)

        // Display a toast message to indicate successful copying
        Toast.makeText(context, "$textDescription copied to clipboard", Toast.LENGTH_SHORT).show()
    }

     fun readJsonFromRawResource(resourceId: Int, context: Context): String {
        val inputStream = context.resources.openRawResource(resourceId)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }

     fun getFileInfo(uri: Uri, context: Context): Pair<String, Long> {
        var name = ""
        var size: Long = 0

         context. contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (nameIndex != -1) {
                    name = cursor.getString(nameIndex)
                }
                if (sizeIndex != -1) {
                    size = cursor.getLong(sizeIndex)
                }
            }
        }

        return Pair(name, size)
    }

     fun convertImageToBase64(uri: Uri, context: Context): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int

        while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }

        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
















}