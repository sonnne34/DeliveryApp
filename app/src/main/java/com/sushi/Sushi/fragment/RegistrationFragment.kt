package com.sushi.Sushi.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListPopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.sushi.Sushi.BasketFragment
import com.sushi.Sushi.MainActivity
import com.sushi.Sushi.R

class RegistrationFragment : Fragment() {

    var paymentFragment: PaymentFragment = PaymentFragment()
    lateinit var basketFragment: BasketFragment
    lateinit var btnPay : Button
    lateinit var nameEdit : EditText
    lateinit var numberPhone : EditText
    lateinit var citiesAddress : EditText
    lateinit var streetAddress : EditText
    lateinit var houseAddress : EditText
    lateinit var apartmentAddress : EditText
    lateinit var comiteOrder : EditText
    lateinit var btnBack : ImageButton
    lateinit var inputName : TextInputLayout
    lateinit var inputPhone : TextInputLayout
    lateinit var inputStreet : TextInputLayout
    lateinit var inputCities : TextInputLayout
    lateinit var inputHome : TextInputLayout
    lateinit var entrance : EditText
    lateinit var level : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_registration, container, false)
        nameEdit = root.findViewById(R.id.name_person_date)
        numberPhone = root.findViewById(R.id.phone_person_data)
        citiesAddress = root.findViewById(R.id.cities_address)
        streetAddress = root.findViewById(R.id.street_address)
        houseAddress = root.findViewById(R.id.home_address)
        apartmentAddress = root.findViewById(R.id.apartment_address)
        comiteOrder = root.findViewById(R.id.comment_person_data)
        entrance = root.findViewById(R.id.entrance_address)
        level = root.findViewById(R.id.level_address)

        btnBack = root.findViewById(R.id.btn_reg_back)
        inputName = root.findViewById(R.id.input_name)
        inputPhone = root.findViewById(R.id.input_phone)
        inputCities = root.findViewById(R.id.input_cities)
        inputStreet = root.findViewById(R.id.input_street)
        inputHome = root.findViewById(R.id.input_home)

        btnPay = root.findViewById(R.id.btn_payy)
        btnPay.isFocusable = true
        btnPay.requestFocus()

        btnPay()
        btnBack()
        loadName()
        loadNumber()
        loadCities()
        loadStreet()
        loadHouse()
        loadApartpent()
        loadComit()
        loadEntrance()
        loadLevel()

        return root
    }

    private fun loadComit() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadcomit = pref!!.getString("comite", "")
        comiteOrder.setText(loadcomit)

    }

    private fun loadApartpent() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadAppartment = pref!!.getString("apartmentA", "")
        apartmentAddress.setText(loadAppartment)
    }

    private fun loadHouse() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadhouse = pref!!.getString("houseA", "")
        houseAddress.setText(loadhouse)
    }

    private fun loadCities() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadCities = pref!!.getString("citiesA", "")
        citiesAddress.setText(loadCities)
    }

    private fun loadStreet() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadStreet = pref!!.getString("streetA", "")
        streetAddress.setText(loadStreet)
    }

    private fun loadNumber() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadNumber = pref!!.getString("number", "")
        numberPhone.setText(loadNumber)
    }

    private fun loadName() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadName = pref!!.getString("name", "")
        nameEdit.setText(loadName)
    }

    private fun loadLevel() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadlevel = pref!!.getString("level", "")
        level.setText(loadlevel)
    }

    private fun loadEntrance() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadEntrance = pref!!.getString("entrance", "")
        entrance.setText(loadEntrance)
    }


    @SuppressLint("CommitPrefEdits")
    private fun editeSave() {
       val name = nameEdit.text.toString()
       val number = numberPhone.text.toString()
       val citiesA = citiesAddress.text.toString()
       val streetA = streetAddress.text.toString()
       val houseA = houseAddress.text.toString()
       val apartmentA = apartmentAddress.text.toString()
       val comite = comiteOrder.text.toString()
       val level = level.text.toString()
       val entrance = entrance.text.toString()
        Log.d("name", "name1 = " + name)

        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref1 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref2 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref3 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref4 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref5 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref6 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref7 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref8 = this.activity?.getPreferences(Context.MODE_PRIVATE)

        val savePerson: Editor = pref!!.edit()
        savePerson.putString("name", name).toString()
        val savenumber: Editor = pref1!!.edit()
        savenumber.putString("number", number).toString()
        val savecities: Editor = pref8!!.edit()
        savecities.putString("citiesA", citiesA).toString()
        val savestreet: Editor = pref2!!.edit()
        savestreet.putString("streetA", streetA).toString()
        val saveHouse: Editor = pref3!!.edit()
        saveHouse.putString("houseA", houseA).toString()
        val saveApartament: Editor = pref4!!.edit()
        saveApartament.putString("apartmentA", apartmentA).toString()
        val saveComite: Editor = pref5!!.edit()
        saveComite.putString("comite", comite).toString()
        val saveLevel: Editor = pref6!!.edit()
        saveLevel.putString("level", level).toString()
        val saveEntrance: Editor = pref7!!.edit()
        saveEntrance.putString("entrance", entrance).toString()

        Log.d("dataS", "street= $streetA")

        savePerson.apply()
        savenumber.apply()
        savecities.apply()
        savestreet.apply()
        saveHouse.apply()
        saveApartament.apply()
        saveComite.apply()
        saveEntrance.apply()
        saveLevel.apply()

    }

    private fun btnPay () {
        btnPay.setOnClickListener {

            //уведомление об обязательном заполнении полей
            if (numberPhone.text.isEmpty() || nameEdit.text.isEmpty() || citiesAddress.text.isEmpty() || streetAddress.text.isEmpty() || houseAddress.text.isEmpty()) {
                if (numberPhone.text.isEmpty()) {
                    inputPhone.error = "Обязательное поле"
                } else {
                    inputPhone.error = null
                }
                if (nameEdit.text.isEmpty()) {
                    inputName.error = "Обязательное поле"
                } else {
                    inputName.error = null
                }
                if (citiesAddress.text.isEmpty()) {
                    inputCities.error = "Обязательное поле"
                } else {
                    inputCities.error = null
                }
                if (streetAddress.text.isEmpty()) {
                    inputStreet.error = "Обязательное поле"
                } else {
                    inputStreet.error = null
                }
                if (houseAddress.text.isEmpty()) {
                    inputHome.error = "Обязательное поле"
                } else {
                    inputHome.error = null
                }
            } else {
                //сохранение данных
                editeSave()

//                val delivery = arguments?.getString("delivery").toString()
//                val args = Bundle()
//                args.putString("delivery", delivery)
//                paymentFragment.arguments = args
//                Log.d("delivery", "delivery = $delivery")
//
                //переход к сл.фрагменту
                val manager = (activity as AppCompatActivity).supportFragmentManager
                manager.beginTransaction()
//                    .replace(R.id.frame_layout, paymentFragment, args.toString())
                    .replace(R.id.frame_layout, paymentFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
    }

    private fun btnBack() {
        btnBack.setOnClickListener {

            basketFragment = BasketFragment()
            val manager = (activity as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.frame_layout, basketFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }
}