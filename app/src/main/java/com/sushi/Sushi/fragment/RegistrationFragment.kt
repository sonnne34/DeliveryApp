package com.sushi.Sushi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.sushi.Sushi.R

class RegistrationFragment : Fragment() {

    lateinit var paymentFragment: PaymentFragment
    lateinit var btnPay : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_registration, container, false)

        btnPay = root.findViewById(R.id.btn_pay)

        btnPay()

        return root
    }

    private fun btnPay () {
        btnPay.setOnClickListener {
            val manager = (activity as AppCompatActivity).supportFragmentManager
            paymentFragment = PaymentFragment()
            manager.beginTransaction()
                    .replace(R.id.frame_layout, paymentFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
        }
    }
}