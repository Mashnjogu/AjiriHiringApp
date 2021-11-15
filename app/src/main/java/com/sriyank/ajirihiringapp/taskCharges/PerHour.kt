package com.sriyank.ajirihiringapp.taskCharges

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sriyank.ajirihiringapp.R
import java.lang.NumberFormatException

private const val TAG = "BudgetActivity"
class PerHour: Fragment() {

    private lateinit var edt_shilling: EditText
    private lateinit var edt_hours: EditText
    private lateinit var txt_ttlmat: TextView

     var shillings = 0
     var hours = 0
     var total = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.perhour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edt_shilling = view.findViewById(R.id.edt_shilling)
        edt_hours = view.findViewById(R.id.edt_hrs)
        txt_ttlmat = view.findViewById(R.id.edt_totalamt)


        edt_hours.addTextChangedListener {  }
        validateData()
    }

    private fun validateData(){
        edt_shilling.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    shillings = Integer.parseInt(edt_shilling.text.toString())
                    hours = Integer.parseInt(edt_hours.text.toString())
                    total = shillings * hours
                }catch(nfe: NumberFormatException){
                    Log.d(TAG, "The error is: $nfe")
                }
                txt_ttlmat.setText("$total")
            }

        })

        edt_hours.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    shillings = Integer.parseInt(edt_shilling.text.toString())
                    hours = Integer.parseInt(edt_hours.text.toString())
                    total = shillings * hours
                }catch(nfe: NumberFormatException){
                    Log.d(TAG, "The error is: $nfe")
                }
                txt_ttlmat.setText("$total")
            }

        })
    }
}