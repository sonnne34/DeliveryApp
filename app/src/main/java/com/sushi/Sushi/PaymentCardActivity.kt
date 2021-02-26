package com.sushi.Sushi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.craftman.cardform.CardForm
import com.sushi.Sushi.singleton.BasketSingleton

class PaymentCardActivity : AppCompatActivity() {

    private lateinit var cardForm: CardForm
    private lateinit var textPay: TextView
    private lateinit var btnPay: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_card)

        cardForm = findViewById(R.id.cardform)

        textPay = findViewById(R.id.payment_amount)
        btnPay = findViewById(R.id.btn_pay)

        val ss = BasketSingleton.count()
        textPay.text = "$ss руб."

    }
}