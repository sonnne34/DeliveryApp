package com.sushi.Sushi


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sushi.Sushi.databinding.ActivityMainBinding
import com.sushi.Sushi.fragment.ProfilFragment
import com.sushi.Sushi.fragment.StatusFragment

class MainActivity : AppCompatActivity() {




    private lateinit var binding: ActivityMainBinding

    @SuppressLint("WrongConstant", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.frame_layout)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        navView.setupWithNavController(navController)

//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            123
//        )


//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.frame_layout, MenuFragment.newInstance())
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()
//
//
//
//        bottomNavigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
////                R.id.status -> {
////                    statusFragment = StatusFragment()
////                    supportFragmentManager
////                        .beginTransaction()
////                        .replace(R.id.frame_layout, statusFragment)
////                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
////                        .commit()
////                }
//
//                R.id.menu -> {
//
//                    supportFragmentManager
//                        .beginTransaction()
//                        .add(R.id.frame_layout, MenuFragment.newInstance())
//                        .addToBackStack(null)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                    true
//                }
//
////                R.id.profil -> {
////
////                    profilFragment = ProfilFragment()
////                    supportFragmentManager
////                        .beginTransaction()
////                        .replace(R.id.frame_layout, profilFragment)
////                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
////                        .commit()
////                }
//
//
//                R.id.basket -> {
//                    supportFragmentManager
//                        .beginTransaction()
//                        .add(R.id.frame_layout, BasketFragment.newInstance())
//                        .addToBackStack(null)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        bottomNavigation.setOnNavigationItemReselectedListener { item ->
//            when (item.itemId) {
//                R.id.menu -> {
////                    Toast.makeText(this, "Вы уже в меню", Toast.LENGTH_SHORT).show()
//                }
//                R.id.basket -> {
////                    Toast.makeText(this, "Вы уже в корзине", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

    }

    override fun onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        openQuitDialog()


        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
//            super.onBackPressed()
            //additional code
            openQuitDialog()

        } else {
            supportFragmentManager.popBackStack()
        }

    }

    private fun openQuitDialog() {
        val quitDialog = AlertDialog.Builder(
            this
        )
        quitDialog.setTitle("Выход")
        quitDialog.setTitle("Вы уверенны, что хотите выйти?")
        quitDialog.setPositiveButton(
            "Да"
        ) { _, _ ->
            onDestroy()
//            finish()
        }
        quitDialog.setNegativeButton(
            "Ой, нет!"
        ) { _, _ -> }
        quitDialog.show()
    }
}