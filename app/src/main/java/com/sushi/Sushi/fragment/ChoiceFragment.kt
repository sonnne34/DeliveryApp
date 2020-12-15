package com.sushi.Sushi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sushi.Sushi.MenuFragment
import com.sushi.Sushi.R
import com.sushi.Sushi.models.MenuModel
import com.sushi.Sushi.singleton.BasketSingleton
import com.sushi.Sushi.singleton.MenuSingleton
import kotlinx.android.synthetic.main.fragment_choice.*
import org.w3c.dom.Text


class ChoiceFragment : Fragment() {


    lateinit var btnPlus : Button
    lateinit var btnMinus: Button
    lateinit var textCount : TextView
    lateinit var textCost : TextView
    lateinit var btnAdd : Button
    lateinit var btnCancel : Button
    lateinit var menuFragment: MenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_choice, container, false)

        btnPlus = root.findViewById(R.id.btn_plus_choice)
        textCount = root.findViewById(R.id.txt_value_choice)
        btnMinus = root.findViewById(R.id.btn_minus_choice)
        textCost = root.findViewById(R.id.txt_cost_choice)
        btnAdd = root.findViewById(R.id.btn_add_choice)
        btnCancel = root.findViewById(R.id.btn_cancel_choice)




        plusbtn()
        minusbtn()
        addBtn()
        cancelbtn()





        return root
    }

    private fun addBtn() {
        btnAdd.setOnClickListener {
            var textCount = txt_value_choice.text
            var text = Integer.valueOf(textCount.toString())

            // колличество кладем в модель и передаем в SingleTon

            val modelMenu = MenuSingleton.menu
//            modelMenu.Count = text.toLong()
            BasketSingleton.addBasket(modelMenu)
            BasketSingleton.showBasket()

            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()

            val manager = (activity as AppCompatActivity).supportFragmentManager
            menuFragment = MenuFragment()
            manager.beginTransaction()
                    .replace(R.id.frame_layout, menuFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

        }

    }

    private fun cancelbtn() {
        btnCancel.setOnClickListener {
            val manager = (activity as AppCompatActivity).supportFragmentManager
            menuFragment = MenuFragment()
            manager.beginTransaction()
                    .replace(R.id.frame_layout, menuFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
        }
    }



    private fun minusbtn() {
        btnMinus.setOnClickListener {
            var textNumber = txt_value_choice.text
            var text = Integer.valueOf(textNumber.toString())
            if(text >= 1){
                text--
            }else{
                text = 0
            }
            txt_value_choice.text = Integer.toString(text)

            var count = txt_value_choice.text
            var txt  = Integer.valueOf(count.toString())
            val sums = (txt * 100)

            txt_cost_choice.text = sums.toString()
        }


    }

    private fun plusbtn() {
        btnPlus.setOnClickListener {
            var textNumber = txt_value_choice.text
            var text = Integer.valueOf(textNumber.toString())
            text++
            txt_value_choice.text = Integer.toString(text)

            var count = txt_value_choice.text
            var txt  = Integer.valueOf(count.toString())
            val sums = (txt * 100)

            txt_cost_choice.text = sums.toString()



        }
    }

}