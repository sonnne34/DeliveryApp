package com.sushi.Sushi.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.sushi.Sushi.R

/**
 * A simple [Fragment] subclass.
 */
class SetupProfileFragment : Fragment() {
    private lateinit var btnSave : Button
    private lateinit var btnCancelSave : Button
    private lateinit var profileFragment: ProfilFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_setup_profil, container, false)
        btnSave = root.findViewById(R.id.btn_done_setup)
        btnCancelSave = root.findViewById(R.id.btn_cancel_setup)

        btnSave()
        btnSaveCancel()

        return root
    }

    private fun btnSave() {
        btnSave.setOnClickListener {
            val manager = (activity as AppCompatActivity).supportFragmentManager
            profileFragment = ProfilFragment()
            manager.beginTransaction()
                .replace(R.id.frame_layout,profileFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    private fun btnSaveCancel() {
        btnCancelSave.setOnClickListener {
            val manager = (activity as AppCompatActivity).supportFragmentManager
            profileFragment = ProfilFragment()
            manager.beginTransaction()
                .replace(R.id.frame_layout,profileFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }
}
