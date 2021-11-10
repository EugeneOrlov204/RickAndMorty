package com.shpp.eorlov.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shpp.eorlov.rickandmorty.App
import com.shpp.eorlov.rickandmorty.R
import com.shpp.eorlov.rickandmorty.di.ContactComponent

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // Stores an instance of RegistrationComponent so that its Fragments can access it
    lateinit var contactComponent: ContactComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        contactComponent = (application as App).appComponent.contactComponent().create()

        // Injects this activity to the just created Registration component
        contactComponent.inject(this)

        super.onCreate(savedInstanceState)
    }
}