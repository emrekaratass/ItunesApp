package com.example.itunesapp.shared.extension

import com.example.itunesapp.shared.reader.AssetReader
import java.io.IOException

@Throws(IOException::class)
fun asset(fileName: String) = AssetReader.getJsonDataFromAsset(fileName)
