package com.sushi.Sushi



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sushi.Sushi.databinding.ActivityMainBinding
import com.sushi.Sushi.singleton.BasketSingleton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    var activeFragment: Fragment? = null
    var fragmentList: ArrayList<Fragment> = ArrayList()

    val menuFragment =  MenuFragment()
    val basketFragment = BasketFragment()

    @SuppressLint("WrongConstant", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.frame_layout)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        navView.setupWithNavController(navController)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout,menuFragment)
            .commit()



        navView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menuButton -> {
//                    Toast.makeText(this, "Вы уже в меню", Toast.LENGTH_SHORT).show()
                }
                R.id.basketButton -> {

                }
            }
        }

        navView.setOnNavigationItemSelectedListener{item ->
            when (item.itemId) {
                R.id.menuButton -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout,menuFragment)
                        .commit()
                }
                R.id.basketButton -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout,basketFragment)
                        .commit()
                }
            }
            true
        }



    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var selectedFragment: Fragment? = null
//        when (item.getItemId()) {
//            R.id.menuFragment -> {
//                selectedFragment = MenuFragment()
//                replaceFragment(selectedFragment)
//                return true
//            }
//            R.id.basketFragment -> {
//                selectedFragment = BasketFragment()
//                replaceFragment(selectedFragment)
//                return true
//
//            }
//        }
//        return true
//    }
//
//
//    private fun replaceFragment(selectedFragment: Fragment) {
//        var lastOpened = false
//        for (i in fragmentList!!.indices) {
//            if (fragmentList!![i] == selectedFragment) {
//                lastOpened = true
//                break
//            }
//        }
//        if (!lastOpened) {
//            supportFragmentManager.beginTransaction().replace(R.id.content, selectedFragment)
//                .commit()
//        } else {
//            supportFragmentManager.beginTransaction().hide(activeFragment!!).show(selectedFragment)
//                .commit()
//        }
//        activeFragment = selectedFragment
//    }

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
//            supportFragmentManager.popBackStack()
                        navController.popBackStack()
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

    fun goReg(view: View) {
        val sum = BasketSingleton.count()
        if (sum < 1000) {
            Toast.makeText(this,
                "Сумма заказа должна быть не менее 1000 рублей",
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            navController.navigate(R.id.registrationFragment)
        }
    }

    fun goPay() {

        navController.navigate(R.id.paymentFragment)

    }

    fun clearBasket(view: View) {

        val clearDialog = AlertDialog.Builder(this
        )
        clearDialog.setTitle("Аннигилирование")
        clearDialog.setMessage("Очистить корзину?")
        clearDialog.setPositiveButton(
            "Да"
        ) { _, _ ->

            BasketSingleton.del()
            BasketSingleton.notifyTwo()

            navController.navigate(R.id.menuFragment)
//            navController.popBackStack()

        }
        clearDialog.setNegativeButton(
            "Ой, нет!"
        ) { _, _ -> }
        clearDialog.show()
    }


}