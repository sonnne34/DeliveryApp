package com.sushi.Sushi.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craftman.cardform.CardForm
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sushi.Sushi.R
import com.sushi.Sushi.StatusFragment
import com.sushi.Sushi.adapters.TotalAdapter
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.models.ModelTest
import com.sushi.Sushi.singleton.BasketSingleton
import kotlinx.android.synthetic.main.pay_items.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    private lateinit var sumTotal: TextView
    private lateinit var statusFragment: StatusFragment

    private lateinit var cardForm: CardForm
    private lateinit var textPay: TextView
    private lateinit var btnPay: Button
    private lateinit var btnDone: Button
    private lateinit var btnBack : ImageButton
    private lateinit var registrationFragment: RegistrationFragment
    private lateinit var cashBack : EditText
    private lateinit var inputCash : TextInputLayout
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonCash: RadioButton
    private lateinit var radioButtonCard: RadioButton
    private var method = String()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        sumTotal = root.findViewById(R.id.sum_person_total)

        cardForm = root.findViewById(R.id.cardform)
        textPay = root.findViewById(R.id.payment_amount)
        btnPay = root.findViewById(R.id.btn_pay)

        btnBack = root.findViewById(R.id.btn_pay_back)
        btnDone = root.findViewById(R.id.btn_done)
        inputCash = root.findViewById(R.id.input_cash)
        cashBack = root.findViewById(R.id.banknote_payment)

        radioGroup = root.findViewById(R.id.selection_method_payment)
        radioButtonCard = root.findViewById(R.id.method_card_payment)
        radioButtonCard.setOnClickListener(radioButtonClickListener)
        radioButtonCash = root.findViewById(R.id.method_cash_payment)
        radioButtonCash.isChecked = true
        radioButtonCash.setOnClickListener(radioButtonClickListener)

//        textPay.setText("2000р")

        btnPay.setText(String.format("Player %s", textPay.text))

        cardForm.setPayBtnClickListner{
            Toast.makeText(root.context, "Name : " + cardForm.card.name, Toast.LENGTH_SHORT).show()
        }

        loadinfoAdapter()

        loadinfoPerson()



        btnBack()
        btnDone()

       return root


    }

    private fun loadinFireBase() {

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        Log.d("ddd","Month = " + month)


        val ref : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Order/" + year + "/" + month + "/" + day)

        val menu = ModelTest()

        menu.Name = "Бурятия"
        menu.Description = "Ксасный вкусный"
        menu.Cost = 300
        menu.CountDialog = 2
        


        ref.push().setValue(menu)
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
        val comint  = pref2!!.getString("comite", "")
        comitText.setText(comint)
        val street = pref3!!.getString("streetA", "")
        streetText.setText(street)
        val house = pref4!!.getString("houseA", "")
        houseText.setText(house)
        val appart = pref5!!.getString("apartmentA", "")
        apatanmentText.setText(appart)
        val level = pref6!!.getString("name", "")
        levelText.setText(level)

        val ss = BasketSingleton.count()
        sumTotal.text = "$ss руб."

        textPay.text = "$ss руб."

    }

    private fun loadinfoAdapter() {
        val list = BasketSingleton.basketItem

        updateAdapter(list)

    }

    private fun updateAdapter(itemList: ArrayList<MenuModelcatMenu>) {
        adapter.setupTotal(itemList)
    }

    private fun btnBack() {
        btnBack.setOnClickListener {
            registrationFragment = RegistrationFragment()
            val manager = (activity as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.frame_layout, registrationFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    private var radioButtonClickListener =
        View.OnClickListener { view ->
            val card = view as RadioButton
            when (card.id) {
                R.id.method_card_payment -> {
                    method =
                        "Картой" //при нажатии на "Картой" значение переменной - "Картой"
                    inputCash.error =
                        null //при нажатии на "Картой" ошибок с обязательным полем чтобы не всплывало
                    inputCash.visibility = View.GONE
                    cardForm.visibility = View.VISIBLE
                    btnDone.visibility = View.GONE
                }
                R.id.method_cash_payment -> {
                    method =
                        "Наличными" //при нажатии "Наличными" значение переменной - "Наличными"
                    inputCash.visibility = View.VISIBLE //чтобы возвращалась строка "Сдача с"
                    cardForm.visibility = View.INVISIBLE
                    btnDone.visibility = View.VISIBLE
                    btnDone()
                }
                else -> {
                }
            }
        }

    private fun btnDone() {
        btnDone.setOnClickListener {
//            val banknote: String = banknotePayment.getText().toString()
//            BanknotePaymentMenu.getBanknotePaymentMenu().addBanknoteMenuFile(banknote)
//            MethodPaymentMenu.getMethodPaymentMenu().addMethodMenuFile(method)
            //при методе оплаты не картой (наличными) "Сдача с" становится обязательным полем
            if (method !== "Картой") {
                if (cashBack.text.isEmpty()) { // если вес "Сдача с" = 0, то
                    inputCash.error = "Обязательное поле"
                } //ошибка
                else {
                    inputCash.error = null //если не равно 0 то отправляем заказ и переходим в статус-фрагмент

                    Toast.makeText(context,"Дело сделано!)",Toast.LENGTH_LONG).show()

                    loadinFireBase()

                    statusFragment = StatusFragment()
                    val manager = (activity as AppCompatActivity).supportFragmentManager
                    manager.beginTransaction()
                        .replace(R.id.frame_layout, statusFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
        }
    }
}