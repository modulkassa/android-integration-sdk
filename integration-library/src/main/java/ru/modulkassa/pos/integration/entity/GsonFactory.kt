package ru.modulkassa.pos.integration.entity

import com.google.gson.Gson
import com.google.gson.GsonBuilder

internal class GsonFactory private constructor() {
    companion object {
        fun provide(): Gson = GsonBuilder().create()
    }
}
