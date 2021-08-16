package com.example.android.netgurucompass.intent

sealed class Intent() {
    object GetCoordinates: Intent()

    object None: Intent()
}