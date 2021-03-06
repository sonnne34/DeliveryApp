package com.sushi.Sushi.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.google.android.material.textfield.TextInputLayout
import com.sushi.Sushi.MainActivity
import com.sushi.Sushi.MenuFragment
import com.sushi.Sushi.PaymentCardActivity
import com.sushi.Sushi.R
import com.sushi.Sushi.adapters.BasketAdapter
import com.sushi.Sushi.adapters.TotalAdapter
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton
import okhttp3.*
import java.io.IOException

class PaymentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TotalAdapter
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var nameText: TextView
    private lateinit var numberText: TextView
    private lateinit var comitText: TextView
    private lateinit var comitHeader: TextView
    private lateinit var streetText: TextView
    private lateinit var citiesText: TextView
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
    private lateinit var delivery: TextView
    private lateinit var layoutDelivery: LinearLayout
    private lateinit var discont: TextView

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

        basketAdapter = BasketAdapter(root.context)

        nameText = root.findViewById(R.id.name_pay_item)
        numberText = root.findViewById(R.id.phone_order)
        comitText = root.findViewById(R.id.comment_order_text)
        comitHeader = root.findViewById(R.id.comment_order)
        citiesText = root.findViewById(R.id.cities_total)
        streetText = root.findViewById(R.id.street_total)
        houseText = root.findViewById(R.id.home_total)
        apatanmentText = root.findViewById(R.id.apartment_total)
        levelText = root.findViewById(R.id.level_total)
        entranceText = root.findViewById(R.id.entrance_total)
        sumTotal = root.findViewById(R.id.sum_person_total)
        discont = root.findViewById(R.id.txt_new_prise_total_discont)

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
        delivery = root.findViewById(R.id.delivery_sum_total)
        layoutDelivery = root.findViewById(R.id.layout_delivery)

        loadinfoAdapter()
        loadinfoPerson()
        btnBack()
        btnDone()

        return root

    }

    private fun loadingFireBase() {

        val ss = BasketSingleton.count()
        val sumPersonTotal = BasketSingleton.count()

//        val deliveryS = arguments?.getString("delivery")
//        val sumPersonTotal= ss + deliveryS!!.toInt()

        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref1 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref2 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref3 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref4 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref5 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref6 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref7 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref8 = this.activity?.getPreferences(Context.MODE_PRIVATE)


        val loadname = pref!!.getString("name", "")
        val loadphone = pref1!!.getString("number", "")
        val comint = pref2!!.getString("comite", "")
        val cities = pref8!!.getString("citiesA", "")
        val street = pref3!!.getString("streetA", "")
        val house = pref4!!.getString("houseA", "")
        val appart = pref5!!.getString("apartmentA", "")
        val level = pref6!!.getString("level", "")
        val entrance = pref7!!.getString("entrance", "")
        val source = "Android"

        val list = BasketSingleton.basketItem
        val pay = "????????????"

        val methodPay: String = method
        val cashback: String = cashBack.text.toString()

        var send = ""
        send = send + "??????????: \n"

        for(i in list ){
            send = (send + i.Items?.CountDialog + "????" + " " + i.Items?.Name + " " + i.Items?.Cost + "??") + "\n"
        }

        val cityTwo = "????????????"
        val banknotePayment = "??????????????????"
//        send = "$send\n?????????????????? ???????????????? ???? ????????????: $deliveryS ??. \n "
        send = send + "\n" + "??????????: " + sumPersonTotal + " ??. \n "
        send = send + "???????????????????? ?? ????????????: \n" + loadname + "\n" + loadphone + "\n"
        send = send + "?????????? ????????????????: \n" + cities + ", ????. " + street + ", ??. " + house + ", ????./????. " + appart + ", ??????. " + entrance + ", ????. " + level + "\n";
        send = send + "?????????????? ????????????: \n" + methodPay + "\n" + "?????????? ??: \n" + cashback + "\n";
        send = send + "?????????????????????? ?? ????????????: \n" + comint;


        Log.d("OOO", "send \n$send")
        val ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%"
        val order = Uri.encode(send, ALLOWED_URI_CHARS)
        Log.d("RRRR", "mail = $order")


        val cityENG = "Tyumen"
        val restToran = "TeaTemple"

        var  base = "https://us-central1-kalibri-845e2.cloudfunctions.net/addOrder";
        base += "?money=" + sumPersonTotal; //????????????
        base += "&city=" + cityENG; //??????????
        base += "&restaurant=" + restToran; //???????????????? ??????????????????
        base += "&tel=" + loadphone; //??????????????
        base += "&order=" + order ; //??????????
        base += "&source=" + source// Android
        Log.d("RRRRTest", "Base = " + base);



        val client = OkHttpClient()

        val url = base
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {

                    val myResponse = response.body()!!.string()

                    Log.d("Spot", "myResponse = \n $myResponse")

                }
            }
        })

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
        val pref8 = this.activity?.getPreferences(Context.MODE_PRIVATE)

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

        val cities = pref8!!.getString("citiesA", "")
        citiesText.text = "?????????? $cities,"

        val street = pref3!!.getString("streetA", "")
        streetText.text = "????. $street,"

        val house = pref4!!.getString("houseA", "")
        houseText.text = "??. $house"

        val appart = pref5!!.getString("apartmentA", "")
        apatanmentText.text = "????./????. $appart,"
        if (appart!!.isEmpty()) {
            apatanmentText.visibility = View.GONE
        }

        val level = pref6!!.getString("level", "")
        levelText.text = " ????. $level"
        if (level!!.isEmpty()) {
            levelText.visibility = View.GONE
        }

        val entrance = pref7!!.getString("entrance", "")
        entranceText.text = " ??????. $entrance,"
        if (entrance!!.isEmpty()) {
            entranceText.visibility = View.GONE
        }

        val ss = BasketSingleton.count()
//        val deliveryS = arguments?.getString("delivery")

//        if (deliveryS == "0"){
//            layoutDelivery.visibility = View.GONE
//            sumTotal.text = "??????????: $ss ??????.  "
//        } else {
//            layoutDelivery.visibility = View.VISIBLE
//            delivery.text = "$deliveryS ??????.  "
//            val sumTotalS = ss + deliveryS!!.toInt()
//            sumTotal.text = "??????????: $sumTotalS ??????.  "


        val sum1 = BasketSingleton.count()
        //?????????? ???? ??????????????
        val sum2 = BasketSingleton.countNew()
        //???????????? ?? ????????
        val newCostSum = sum1 - sum2
        //?????????? ????????
        //???????? ?????????? ???????? ????????
        if(sum1 != sum2){
            //???? ???????????????????? ???????????? ????????????
            discont.text = "???????????? ????????????????????: $newCostSum ??."
            discont.visibility = View.VISIBLE
            sumTotal.text = "??????????: $sum2 ??."

        } else {
            discont.visibility = View.GONE
            sumTotal.text = "??????????: $sum1 ??."
        }
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
                        "????????????" //?????? ?????????????? ???? "????????????" ???????????????? ???????????????????? - "????????????"
                    inputCash.error =
                        null //?????? ?????????????? ???? "????????????" ???????????? ?? ???????????????????????? ?????????? ?????????? ???? ??????????????????
                    
                    inputCash.visibility = View.GONE
//                    btnDoneCard.visibility = View.VISIBLE
//                    btnDone.visibility = View.GONE
                    btnDone.visibility = View.VISIBLE

                }
                R.id.method_cash_payment -> {
                    method =
                        "??????????????????" //?????? ?????????????? "??????????????????" ???????????????? ???????????????????? - "??????????????????"
                    inputCash.visibility = View.VISIBLE //?????????? ???????????????????????? ???????????? "?????????? ??"
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

            //?????? ???????????? ???????????? ???????????? ?????????????????? ?? PaymentCardActivity
            val intent = Intent(context, PaymentCardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun btnDone() {
        btnDone.setOnClickListener {
            //?????? ???????????? ???????????? ???? ???????????? (??????????????????) "?????????? ??" ???????????????????? ???????????????????????? ??????????
            if (method !== "????????????") {

                if (cashBack.text.isEmpty()) { // ???????? ?????? "?????????? ??" = 0, ????
                    inputCash.error = "???????????????????????? ????????"
                } //????????????

                else {
                    inputCash.error =
                        null //???????? ???? ?????????? 0 ???? ???????????????????? ?????????? ?? ?????????????????? ?? ????????????-????????????????

                    loadingFireBase()
                    openDoneDialog()

                }
            } else {
                loadingFireBase()
                openDoneDialog()
            }
        }
    }

    private fun openDoneDialog() {

        val quitDialog = AlertDialog.Builder(
            activity as AppCompatActivity
        )
        quitDialog.setTitle("?????????? ????????????????!")
        quitDialog.setMessage("?? ?????????????????? ?????????? ???? ???????????????? ?? ???????? ?????? ?????????????????????????? ???????????? =)")
        quitDialog.setPositiveButton(
            "????????????????!"
        ) { dialog, which ->
            BasketSingleton.del()
                    menuFragment = MenuFragment()
                    val manager = (activity as AppCompatActivity).supportFragmentManager
                    manager.beginTransaction()
                        .replace(R.id.frame_layout, menuFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
            Toast.makeText(context, "???????? ??????????????!)", Toast.LENGTH_LONG).show()
        }
        quitDialog.setNegativeButton(
            "??????????????????????!"
        ) { _, _ ->
            BasketSingleton.del()
            menuFragment = MenuFragment()
            val manager = (activity as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.frame_layout, menuFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
            Toast.makeText(context, "???????? ??????????????!)", Toast.LENGTH_LONG).show()
        }
        quitDialog.show()
    }
}
