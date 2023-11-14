//package com.famisalud.famisalud
package  com.famisalud.famisalud.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.famisalud.famisalud.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boton1 = view.findViewById<Button>(R.id.button1)
        val boton2 = view.findViewById<Button>(R.id.button2)

        boton1.setOnClickListener {
            Toast.makeText(context, "Presionaste el boton 1", Toast.LENGTH_LONG).show()
        }


        boton2.setOnClickListener {
            Toast.makeText(context, "Presionaste el boton 2", Toast.LENGTH_LONG).show()
        }
    }
}