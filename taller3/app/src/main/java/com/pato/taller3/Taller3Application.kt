package com.pato.taller3

import android.app.Application
import com.pato.taller3.data.AppProvider

class Taller3Application : Application() {
    val appProvider by lazy { AppProvider(this) }
}
