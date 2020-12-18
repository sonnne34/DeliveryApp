package com.sushi.Sushi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.adapters.BasketAdapter
import com.sushi.Sushi.fragment.RegistrationFragment
import com.sushi.Sushi.listener.EventListenerss
import com.sushi.Sushi.models.MenuModel
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton


class BasketFragment : Fragment(){

    private lateinit var basketAdapter: BasketAdapter
    private lateinit var rvBasket : RecyclerView
    private  var listmodel : ArrayList<MenuModelcatMenu> = ArrayList()
    lateinit var btnRegistr : Button
    lateinit var registrationFragment: RegistrationFragment
    lateinit var txtHelloBasket : TextView
    lateinit var txtPriseTotal : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_basket, container, false)
        txtHelloBasket = root.findViewById(R.id.txt_hello_basket)
        txtPriseTotal = root.findViewById(R.id.txt_prise_total_basket)
        btnRegistr = root.findViewById(R.id.btn_registratoin)
        rvBasket = root.findViewById(R.id.basket_recyclerview)
        basketAdapter = BasketAdapter()
        rvBasket.adapter = basketAdapter
        rvBasket.layoutManager = LinearLayoutManager(root.context,RecyclerView.VERTICAL,false)
        rvBasket.setHasFixedSize(true)


        listmodel = BasketSingleton.basketItem



        setupAdapter(listmodel)
        btnReg()
        visible()
        updateTEXT()


        return root
    }


    private fun setupAdapter(modelList : ArrayList<MenuModelcatMenu> ) {
        rvBasket.visibility = View.VISIBLE
        basketAdapter.setupBasket(basketList = modelList)
    }

    private fun btnReg () {
        btnRegistr.setOnClickListener {
            val manager = (activity as AppCompatActivity).supportFragmentManager
            registrationFragment = RegistrationFragment()
            manager.beginTransaction()
                    .replace(R.id.frame_layout, registrationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
        }
    }

    private fun visible() {
        if (listmodel.isNotEmpty()) {
            txtHelloBasket.visibility = View.GONE
            txtPriseTotal.visibility = View.VISIBLE
            btnRegistr.visibility = View.VISIBLE}

        else {
            txtHelloBasket.visibility = View.VISIBLE
            txtPriseTotal.visibility = View.GONE
            btnRegistr.visibility = View.GONE
        }
    }

     fun updateTEXT() {
        val ss = BasketSingleton.count()

        txtPriseTotal.text = ss.toString()
    }



}
