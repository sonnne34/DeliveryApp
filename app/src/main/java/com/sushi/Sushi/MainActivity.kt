package com.sushi.Sushi


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sushi.Sushi.fragment.ProfilFragment
import com.sushi.Sushi.fragment.StatusFragment


class MainActivity : AppCompatActivity() {

    private lateinit var menuFragment: MenuFragment
    private lateinit var statusFragment: StatusFragment
    private lateinit var profilFragment: ProfilFragment
    private lateinit var basketFragment: BasketFragment



    @SuppressLint("WrongConstant", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            123
        )



        menuFragment = MenuFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, menuFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        bottomNavigation.setOnNavigationItemSelectedListener  { item ->
            when(item.itemId){
//                R.id.status -> {
//                    statusFragment = StatusFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, statusFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                }

                R.id.menu -> {
                    menuFragment = MenuFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, menuFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

//                R.id.profil -> {
//
//                    profilFragment = ProfilFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, profilFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                }

                R.id.basket -> {

                    basketFragment = BasketFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, basketFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }
    }

    override fun onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        openQuitDialog()
    }

    private fun openQuitDialog() {
        val quitDialog = AlertDialog.Builder(this
        )
        quitDialog.setTitle("Выход")
        quitDialog.setTitle("Вы уверенны, что хотите выйти?")
        quitDialog.setPositiveButton(
            "Да"
        ) { dialog, which ->
            onDestroy()
//            finish()
        }
        quitDialog.setNegativeButton(
            "Ой, нет!"
        ) { dialog, which -> }
        quitDialog.show()
    }

}