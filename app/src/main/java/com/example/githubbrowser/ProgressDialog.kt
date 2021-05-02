package com.example.githubbrowser

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater

class ProgressDialog(private val myactivity: Activity ) {
    private lateinit var dialog: AlertDialog
    fun startProgressDialog(){

        val builder = AlertDialog.Builder(myactivity)
        val inflator = myactivity.layoutInflater
        builder.setView(inflator.inflate(R.layout.progress_dialog,null))
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()
    }
    fun dismissProgressDialog()
    {
        dialog.dismiss()
    }
}