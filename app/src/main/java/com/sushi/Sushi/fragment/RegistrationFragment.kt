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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sushi.Sushi.R

class RegistrationFragment : Fragment() {

    lateinit var paymentFragment: PaymentFragment
    lateinit var btnPay : Button
    lateinit var nameEdit : EditText
    lateinit var numberPhone : EditText
    lateinit var streatAddress : EditText
    lateinit var houseAddress : EditText
    lateinit var apartmentAddress : EditText
    lateinit var comiteOrder : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_registration, container, false)
        nameEdit = root.findViewById(R.id.name_person_date)
        numberPhone = root.findViewById(R.id.phone_person_data)
        streatAddress = root.findViewById(R.id.street_address)
        houseAddress = root.findViewById(R.id.home_address)
        apartmentAddress = root.findViewById(R.id.apartment_address)
        comiteOrder = root.findViewById(R.id.comment_person_data)


        btnPay = root.findViewById(R.id.btn_payy)

        btnPay()
        loadName()
        loadNumber()
        loadStreat()
        loadHouse()
        loadApartpent()
        loadComit()


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

    private fun loadStreat() {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadStreat = pref!!.getString("streatA", "")
        streatAddress.setText(loadStreat)
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


    @SuppressLint("CommitPrefEdits")
    private fun editeSave() {
       val name = nameEdit.text.toString()
       val number = numberPhone.text.toString()
       val streatA = streatAddress.text.toString()
       val houseA = houseAddress.text.toString()
       val apartmentA = apartmentAddress.text.toString()
       val comite = comiteOrder.text.toString()
        Log.d("name","name1 = " + name )

        val pref1 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref2 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref3 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref4 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref5 = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val pref6 = this.activity?.getPreferences(Context.MODE_PRIVATE)


        val savePerson: Editor = pref1!!.edit()
        savePerson.putString("name", name).toString()
        val savenumber: Editor = pref2!!.edit()
        savenumber.putString("number", number).toString()
        val savestreat: Editor = pref3!!.edit()
        savestreat.putString("streatA", streatA).toString()
        val saveHouse: Editor = pref4!!.edit()
        saveHouse.putString("houseA",houseA).toString()
        val saveApartament: Editor = pref5!!.edit()
        saveApartament.putString("apartmentA", apartmentA).toString()
        val saveComite: Editor = pref6!!.edit()
        saveComite.putString("comite", comite).toString()

        savePerson.apply()
        savenumber.apply()
        savestreat.apply()
        saveHouse.apply()
        saveApartament.apply()
        saveComite.apply()





    }

    private fun btnPay () {
        btnPay.setOnClickListener {
           editeSave()
            val manager = (activity as AppCompatActivity).supportFragmentManager
            paymentFragment = PaymentFragment()
            manager.beginTransaction()
                    .replace(R.id.frame_layout, paymentFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
        }
    }
}