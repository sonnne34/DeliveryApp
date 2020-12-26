package com.sushi.Sushi.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craftman.cardform.CardForm
import com.sushi.Sushi.R
import com.sushi.Sushi.adapters.CategoryAdapter
import com.sushi.Sushi.adapters.TotalAdapter
import com.sushi.Sushi.models.MenuModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton
import kotlinx.android.synthetic.main.total_items.*

class PaymentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : TotalAdapter
    private lateinit var nameText : TextView
    private lateinit var numberText: TextView
    private lateinit var comitText : TextView
    private lateinit var streetText: TextView
    private lateinit var houseText: TextView
    private lateinit var apatanmentText: TextView
    private lateinit var levelText: TextView

    private lateinit var cardForm: CardForm
    private lateinit var textPay: TextView
    private lateinit var btnPay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_payment, container, false)

        recyclerView = root.findViewById(R.id.total_recyclerView)
        adapter = TotalAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
                LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        nameText = root.findViewById(R.id.name_pay_item)
        numberText = root.findViewById(R.id.phone_order)
        comitText = root.findViewById(R.id.comment_order_text)
        streetText = root.findViewById(R.id.street_total)
        houseText = root.findViewById(R.id.apartment_total)
        apatanmentText = root.findViewById(R.id.entrance_total)
        levelText = root.findViewById(R.id.level_total)

        cardForm = root.findViewById(R.id.cardform)
        textPay = root.findViewById(R.id.payment_amount)
        btnPay = root.findViewById(R.id.btn_pay)

        textPay.setText("2000р")
        btnPay.setText(String.format("Player %s",textPay.text))

        cardForm.setPayBtnClickListner{
            Toast.makeText(root.context,"Name : " + cardForm.card.name,Toast.LENGTH_SHORT).show()
        }

        loadinfoAdapter()

        loadinfoPerson()

       return root


    }

    private fun loadinfoPerson() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref1 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref2 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref3 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref4 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref5 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref6 = this.activity?.getPreferences(Context.MODE_PRIVATE)

        val loadname = pref!!.getString("name", "")
        nameText.setText(loadname)
        val loadphone = pref1!!.getString("number", "")
        numberText.setText(loadphone)
        val comint  = pref2!!.getString("comite","")
        comitText.setText(comint)
        val street = pref3!!.getString("streetA","")
        streetText.setText(street)
        val house = pref4!!.getString("houseA","")
        houseText.setText(house)
        val appart = pref5!!.getString("apartmentA","")
        apatanmentText.setText(appart)
        val level = pref6!!.getString("name","")
        levelText.setText(level)

    }

    private fun loadinfoAdapter() {
        val list = BasketSingleton.basketItem

        updateAdapter(list)

    }

    private fun updateAdapter(itemList: ArrayList<MenuModelcatMenu>) {
        adapter.setupTotal(itemList)
    }

}