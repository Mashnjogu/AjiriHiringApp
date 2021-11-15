package com.sriyank.ajirihiringapp.taskCharges

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sriyank.ajirihiringapp.R
import java.lang.NumberFormatException

private const val TAG = "WholePriceActivity"
class WholePrice: Fragment() {

    private lateinit var edt_whp: EditText
    private lateinit var txt_amt: TextView

     var amount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wholeprice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edt_whp = view.findViewById(R.id.edt_whp)
        txt_amt = view.findViewById(R.id.edt_amt)
        onTextChange()
    }

    private fun onTextChange(){
        edt_whp.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    amount = Integer.parseInt(edt_whp.text.toString())
                    txt_amt.setText("$amount")
                }catch (nfe: NumberFormatException){
                    Log.d(TAG, "The error is: $nfe")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}