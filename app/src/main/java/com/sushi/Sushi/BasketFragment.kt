package com.sushi.Sushi

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sushi.Sushi.adapters.BasketAdapter
import com.sushi.Sushi.fragment.RegistrationFragment
import com.sushi.Sushi.listener.EventListenerss
import com.sushi.Sushi.models.MenuModelcatMenu
import com.sushi.Sushi.singleton.BasketSingleton


class BasketFragment : Fragment(), EventListenerss{

    private lateinit var basketAdapter: BasketAdapter
    private lateinit var rvBasket : RecyclerView
    private  var listmodel : ArrayList<MenuModelcatMenu> = ArrayList()
    lateinit var btnRegistr : Button
    lateinit var registrationFragment: RegistrationFragment
    lateinit var txtHelloBasket : TextView
    lateinit var txtHelloBasket2 : TextView
    lateinit var txtPriseTotal : TextView
    lateinit var layoutPriseTotal : LinearLayout
    lateinit var txtHeader : TextView
    lateinit var imgHello: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_basket, container, false)
        txtHelloBasket = root.findViewById(R.id.txt_hello_basket)
        txtHelloBasket2 = root.findViewById(R.id.txt_hello_basket2)
        imgHello = root.findViewById(R.id.img_logo_hello_basket)
//        val typeface = Typeface.createFromAsset(context?.assets, "fonts/Slimamif.ttf")
//        txtHelloBasket.typeface = typeface
        txtPriseTotal = root.findViewById(R.id.txt_prise_total_basket)
        layoutPriseTotal = root.findViewById(R.id.layout_prise_total)
        btnRegistr = root.findViewById(R.id.btn_registratoin)
        txtHeader = root.findViewById(R.id.txt_header_basket)
        rvBasket = root.findViewById(R.id.basket_recyclerview)
        basketAdapter = BasketAdapter()
        rvBasket.adapter = basketAdapter
        rvBasket.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        rvBasket.setHasFixedSize(true)

        BasketSingleton.subscribe(this)
        listmodel = BasketSingleton.basketItem



        setupAdapter(listmodel)
        btnReg()
        visible()
        updateTEXT()


        return root
    }


    private fun setupAdapter(modelList: ArrayList<MenuModelcatMenu>) {
        rvBasket.visibility = View.VISIBLE
        basketAdapter.setupBasket(basketList = modelList)

    }

    private fun btnReg () {
            btnRegistr.setOnClickListener {
                val sum = BasketSingleton.count()
                if (sum < 700) {
                    val toast = Toast.makeText(context,
                        "Но сумма заказа должна быть не менее 700 рублей...",
                        Toast.LENGTH_LONG
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else {
                val manager = (activity as AppCompatActivity).supportFragmentManager
                registrationFragment = RegistrationFragment()
                manager.beginTransaction()
                    .replace(R.id.frame_layout, registrationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
    }

    private fun visible() {
        if (listmodel.isNotEmpty()) {
            txtHelloBasket.visibility = View.GONE
            txtHelloBasket2.visibility = View.GONE
            imgHello.visibility = View.GONE
            txtHeader.visibility = View.VISIBLE
            layoutPriseTotal.visibility = View.VISIBLE
            btnRegistr.visibility = View.VISIBLE}

        else {
            txtHelloBasket.visibility = View.VISIBLE
            txtHelloBasket2.visibility = View.VISIBLE
            imgHello.visibility = View.VISIBLE
            layoutPriseTotal.visibility = View.INVISIBLE
            txtHeader.visibility = View.GONE
            btnRegistr.visibility = View.GONE
        }
    }

     fun updateTEXT() {
        val ss = BasketSingleton.count()

        txtPriseTotal.text = ss.toString()
    }

    override fun updateRR() {
        var menufile = BasketSingleton.basketItem
        basketAdapter.setupBasket(basketList = menufile)
        rvBasket.visibility = View.VISIBLE

       updateTEXT()
    }
}