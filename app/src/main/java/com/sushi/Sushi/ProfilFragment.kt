package com.sushi.Sushi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.sushi.Sushi.fragment.AuthorizationFragment
import com.sushi.Sushi.fragment.SetupProfilFragment
import kotlinx.android.synthetic.main.fragment_profil.*

class ProfilFragment : Fragment() {


    lateinit var authorizationFragment: AuthorizationFragment
    lateinit var setupProfilFragment: SetupProfilFragment
    var status = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(status == 0){
            val manager = (activity as AppCompatActivity).supportFragmentManager
            authorizationFragment = AuthorizationFragment()
            manager.beginTransaction()
                    .replace(R.id.frame_layout,authorizationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

        }else{
            btn_setup.setOnClickListener {


                val manager = (activity as AppCompatActivity).supportFragmentManager
                setupProfilFragment = SetupProfilFragment()
                manager.beginTransaction()
                        .replace(R.id.frame_layout,setupProfilFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
            }

        }

    }

}
