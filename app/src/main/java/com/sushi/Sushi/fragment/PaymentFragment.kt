package com.sushi.Sushi.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craftman.cardform.CardForm
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sushi.Sushi.MainActivity
import com.sushi.Sushi.MenuFragment
import com.sushi.Sushi.PaymentCardActivity
import com.sushi.Sushi.R
import com.sushi.Sushi.adapters.BasketAdapter
import com.sushi.Sushi.adapters.TotalAdapter
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.models.OrderModel
import com.sushi.Sushi.singleton.BasketSingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pay_items.*
import java.util.*
import kotlin.collections.ArrayList

class PaymentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TotalAdapter
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var nameText: TextView
    private lateinit var numberText: TextView
    private lateinit var comitText: TextView
    private lateinit var comitHeader: TextView
    private lateinit var streetText: TextView
    private lateinit var houseText: TextView
    private lateinit var apatanmentText: TextView
    private lateinit var entranceText: TextView
    private lateinit var levelText: TextView
    private lateinit var sumTotal: TextView
    private lateinit var statusFragment: StatusFragment
    private lateinit var menuFragment: MenuFragment
    private lateinit var mainActivity: MainActivity

    private lateinit var btnDone: Button
    private lateinit var btnDoneCard: Button
    private lateinit var btnBack: ImageButton
    private lateinit var registrationFragment: RegistrationFragment
    private lateinit var cashBack: EditText
    private lateinit var inputCash: TextInputLayout
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonCash: RadioButton
    private lateinit var radioButtonCard: RadioButton
    private lateinit var checkBoxPromo: CheckBox
    private lateinit var layoutPromo: LinearLayout
    private lateinit var editTextPromo: EditText
    private lateinit var btnPromo: Button

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

        basketAdapter = BasketAdapter()

        nameText = root.findViewById(R.id.name_pay_item)
        numberText = root.findViewById(R.id.phone_order)
        comitText = root.findViewById(R.id.comment_order_text)
        comitHeader = root.findViewById(R.id.comment_order)
        streetText = root.findViewById(R.id.street_total)
        houseText = root.findViewById(R.id.home_total)
        apatanmentText = root.findViewById(R.id.apartment_total)
        levelText = root.findViewById(R.id.level_total)
        entranceText = root.findViewById(R.id.entrance_total)
        sumTotal = root.findViewById(R.id.sum_person_total)

        btnBack = root.findViewById(R.id.btn_pay_back)
        btnDone = root.findViewById(R.id.btn_done)
        btnDoneCard = root.findViewById(R.id.btn_done_card)
        inputCash = root.findViewById(R.id.input_cash)
        cashBack = root.findViewById(R.id.banknote_payment)

        radioGroup = root.findViewById(R.id.selection_method_payment)
        radioButtonCard = root.findViewById(R.id.method_card_payment)
        radioButtonCard.setOnClickListener(radioButtonClickListener)
        radioButtonCash = root.findViewById(R.id.method_cash_payment)
        radioButtonCash.isChecked = true
        radioButtonCash.setOnClickListener(radioButtonClickListener)

        checkBoxPromo = root.findViewById(R.id.checkbox_promo_payment_total)
        layoutPromo = root.findViewById(R.id.layout_promo_payment_total)
        layoutPromo.visibility = View.GONE
        editTextPromo = root.findViewById(R.id.edit_promo_payment_total)
        btnPromo = root.findViewById(R.id.btn_promo_payment_total)

//        btnPay.setText(String.format("Player %s", textPay.text))

//        cardForm.setPayBtnClickListner{
//            Toast.makeText(root.context, "Name : " + cardForm.card.name, Toast.LENGTH_SHORT).show()
//        }

        loadinfoAdapter()
        loadinfoPerson()
        checkBoxPromo()
        btnBack()
//        btnDoneCard()
        btnDone()


        return root

    }

    private fun loadinFireBase() {

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val ref: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("Order/" + year + "/" + month + "/" + day)

        val list = BasketSingleton.basketItem
        val menu = OrderModel()
        val ss = BasketSingleton.count()
        menu.date = (day.toString() + "." + month.toString() + "." + year.toString())
        menu.money = ss.toString()
        menu.status = "new"
        menu.os = "Android"
        menu.order = list

        ref.push().setValue(menu)
    }

    @SuppressLint("SetTextI18n")
    private fun loadinfoPerson() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref1 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref2 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref3 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref4 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref5 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref6 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref7 = this.activity?.getPreferences(Context.MODE_PRIVATE)

        val loadname = pref!!.getString("name", "")
        nameText.setText(loadname)

        val loadphone = pref1!!.getString("number", "")
        numberText.setText(loadphone)

        val comint = pref2!!.getString("comite", "")
        comitText.setText(comint)
        if (comint!!.isEmpty()) {
            comitHeader.visibility = View.GONE
            comitText.visibility = View.GONE
        }

        val street = pref3!!.getString("streetA", "")
        streetText.text = "ул. $street,"

        val house = pref4!!.getString("houseA", "")
        houseText.text = "д. $house,"

        val appart = pref5!!.getString("apartmentA", "")
        apatanmentText.text = "кв./оф. $appart,"
        if (appart!!.isEmpty()) {
            apatanmentText.visibility = View.GONE
        }

        val level = pref6!!.getString("level", "")
        levelText.text = "эт. $level"
        if (level!!.isEmpty()) {
            levelText.visibility = View.GONE
        }

        val entrance = pref7!!.getString("entrance", "")
        entranceText.text = "под. $entrance,"
        if (entrance!!.isEmpty()) {
            entranceText.visibility = View.GONE
        }

        val ss = BasketSingleton.count()
        sumTotal.text = "итого: $ss руб.  "

        Log.d("data", "street= $street")


    }

    private fun loadinfoAdapter() {
        val list = BasketSingleton.basketItem

        updateAdapter(list)

    }

    private fun updateAdapter(itemList: ArrayList<MenuModelcatMenu>) {
        adapter.setupTotal(itemList)
    }

    private fun checkBoxPromo() {
        checkBoxPromo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                layoutPromo.visibility = View.VISIBLE
            } else {
                layoutPromo.visibility = View.GONE
            }
        }
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
//                    btnDoneCard.visibility = View.VISIBLE
//                    btnDone.visibility = View.GONE
                    btnDone.visibility = View.VISIBLE

                }
                R.id.method_cash_payment -> {
                    method =
                        "Наличными" //при нажатии "Наличными" значение переменной - "Наличными"
                    inputCash.visibility = View.VISIBLE //чтобы возвращалась строка "Сдача с"
                    btnDoneCard.visibility = View.GONE
                    btnDone.visibility = View.VISIBLE
//                    btnDone()
                }
                else -> {
                }
            }
        }

    private fun btnDoneCard() {
        btnDoneCard.setOnClickListener {

            //при методе оплаты картой переходим к PaymentCardActivity
            val intent = Intent(context, PaymentCardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun btnDone() {
        btnDone.setOnClickListener {

            //при методе оплаты не картой (наличными) "Сдача с" становится обязательным полем
            if (method !== "Картой") {

                if (cashBack.text.isEmpty()) { // если вес "Сдача с" = 0, то
                    inputCash.error = "Обязательное поле"
                } //ошибка

                else {
                    inputCash.error =
                        null //если не равно 0 то отправляем заказ и переходим в статус-фрагмент

                    Toast.makeText(context, "Дело сделано!)", Toast.LENGTH_LONG).show()

//                    loadinFireBase()
                    openDoneDialog()

                }
            }
            else {
                Toast.makeText(context, "Дело сделано!)", Toast.LENGTH_LONG).show()
                //                    loadinFireBase()
                openDoneDialog()
            }
        }
    }

    private fun openDoneDialog() {
        val quitDialog = AlertDialog.Builder(activity as AppCompatActivity
        )
        quitDialog.setTitle("Заказ оправлен!")
        quitDialog.setTitle("В ближайшее время мы свяжемся с Вами для подтверждения заказа =)")
        quitDialog.setPositiveButton(
            "Ясненько!"
        ) { dialog, which ->

                    menuFragment = MenuFragment()
                    val manager = (activity as AppCompatActivity).supportFragmentManager
                    manager.beginTransaction()
                        .replace(R.id.frame_layout, menuFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
        }
        quitDialog.setNegativeButton(
            "Понятненько!"
        ) { dialog, which ->

            menuFragment = MenuFragment()
            val manager = (activity as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.frame_layout, menuFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        quitDialog.show()
    }
}
