package com.sushi.Sushi

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.sushi.Sushi.adapters.CategoryAdapter
import com.sushi.Sushi.adapters.MenuAdapter
import com.sushi.Sushi.listener.EventListenerss
import com.sushi.Sushi.models.*
import com.sushi.Sushi.singleton.Address
import com.sushi.Sushi.singleton.BasketSingleton
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment(), EventListenerss {

    private lateinit var  progress_bar : ProgressBar
    private lateinit var progress_bar_two : ProgressBar
    private lateinit var btnGetLoc : Button
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var dangerousArea: MutableList<LatLng>
    private lateinit var  mCategoryAdapter: CategoryAdapter
    private lateinit var categoryRecyclerView : RecyclerView
    lateinit var  menuRecyclerView  : RecyclerView
    private lateinit var adapter : MenuAdapter
    private lateinit var mCatMenuModel: CatMenuModel
    val menuList : ArrayList<CatMenuModel> = ArrayList()
    val categoryList: ArrayList<CatMenuModel> = ArrayList()

    private lateinit var btnCat : RelativeLayout

    private var mCategoryRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Log.d("MMM", "OnCreate = ")

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        Log.d("MMM", "OnCreateView = ")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(root.context)
        btnGetLoc = root.findViewById(R.id.location_btn)
        progress_bar = root.findViewById(R.id.progress_bar)
        progress_bar_two = root.findViewById(R.id.progress_two)

        btnCat = root.findViewById(R.id.cat_menu)
        btnCat.visibility = View.INVISIBLE

        addArea()
        BasketSingleton.subscribe(this)


        menuRecyclerView = root.findViewById(R.id.recycler_view_menu)
        adapter = MenuAdapter()
        menuRecyclerView.adapter = adapter
        menuRecyclerView.layoutManager = LinearLayoutManager(
            root.context,
            RecyclerView.VERTICAL,
            false
        )
        menuRecyclerView.setHasFixedSize(true)

        menuRecyclerView.recycledViewPool.setMaxRecycledViews(100, 100)
        menuRecyclerView.setItemViewCacheSize(150)
        menuRecyclerView.isDrawingCacheEnabled = true


        categoryRecyclerView = root.findViewById(R.id.recyclerview_category)
        mCategoryAdapter = CategoryAdapter()
        categoryRecyclerView.adapter = mCategoryAdapter
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
        categoryRecyclerView.setHasFixedSize(true)

//        loadCategory()
        Log.d("CATTT", "cat = $categoryList")
        loadMenu()

        CoroutineScope(Dispatchers.IO).launch {

            val online = isOnline(root.context)

            Log.d("FF", "Internet = " + online)
//            loadAddress(root.context, online)
        }

        btnCat(root.context)

        return root
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (connectivityManager != null) {
//            val capabilities =
//                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//            if (capabilities != null) {
//                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
//                    return true
//                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
//                    return true
//                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
//                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
//                    return true
//                }
//            }
//        }

        return false
    }


    private fun loadMenu() {
        Log.d("AA", "value =  прошло 1")

        val database = FirebaseDatabase.getInstance()
        Log.d("AA", "value =  прошло 2")
        val myRef = database.getReference("RestaurantsMenu/Avocado")
        Log.d("AA", "value =  прошло 3")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("AA", "value =  прошло4 ")

                for (ds in dataSnapshot.children) {
                    Log.d("AA", "value =  прошло 5 " + ds.getValue(CatMenuModel::class.java))
                    val value = ds.getValue(CatMenuModel::class.java)!!

                    Log.d("AA", "value = 5" + value.CategoryName)


                    categoryList.add(value)
                    menuList.add(value)
                }
                btnCat.visibility = View.VISIBLE
                updateAdapterCategory()
                updateMenuAdapter(menuList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("dima", "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateMenuAdapter(menuList: ArrayList<CatMenuModel>) {

        adapter.setupMenu(menuList)

    }

//    private fun loadCategory() {
//        val database = FirebaseDatabase.getInstance()
//        mCategoryRef = database.getReference("CategoryName")
//        mCategoryRef!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (dss in dataSnapshot.children) {
//                    val items = dss.value as Map<*, *>?
//                    val category = CatMenuModel()
//                    category.CategoryName = items!!["CategoryName"].toString()
//                    categoryList.add(category)
//                }
//                updateAdapterCategory()
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
//    }

    private fun updateAdapterCategory() {

        mCategoryAdapter.setupCategory(categoryList)

        progress_bar_two.visibility = View.VISIBLE
        progress_bar_two.visibility = View.INVISIBLE

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("MMM", "OnActivityCreate = ")

        startFragment(savedInstanceState)
    }

    private fun startFragment(savedInstanceState: Bundle?) {

        if(savedInstanceState != null){
            val parcelable: Parcelable = savedInstanceState.getParcelable("Adapter")!!

        }else{
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun loadAddress(context: Context, boolean: Boolean) {


        if(boolean == true){

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return
            }
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                val latitude = it.result?.latitude
                val longitude = it.result?.longitude

                Log.d("Result", "lat = " + latitude)
                Log.d("Result", "llong = " + longitude)

                if(latitude == null){
                    Toast.makeText(context, "Интернета нет", Toast.LENGTH_SHORT).show()

                }else{
                    val locationA = Location("Point A")

                    locationA.latitude = latitude!!
                    locationA.longitude = longitude!!

                    val locationB = Location("Point B")

                    for (i in dangerousArea){
                        locationB.longitude = i.longitude
                        locationB.latitude = i.latitude
                    }
                    val currentDistance = locationA.distanceTo(locationB)

                    if (currentDistance > 3000){

                        btnGetLoc.setBackgroundResource(R.color.colorTangerine)
                    }else{

                        btnGetLoc.setBackgroundResource(R.color.colorgreen)
                    }

                    val geocoder = Geocoder(context, Locale.getDefault())

                    val addresses = geocoder.getFromLocation(latitude!!, longitude!!, 1)


                    val address = addresses[0].getAddressLine(0)


                    Address.address = address


                    progress_bar.visibility = View.INVISIBLE
                    btnGetLoc.visibility = View.VISIBLE
                    btnGetLoc.text = address

                }
            }
        }
    }

    private fun addArea() {

        dangerousArea = ArrayList()
        dangerousArea.add(LatLng(57.1344598, 65.4966976))
    }

    override fun updateRR() {
        updateMenuAdapter(menuList)
    }

    fun catScroll(position: Int) {
//        val mlenuRecyclerView: RecyclerView? = null
//        val cat: CatMenuModel = categoryList[position]
//        mlenuRecyclerView?.layoutManager?.scrollToPosition(adapter.scrollToCategory(cat.CategoryName))
//        adapter.setupMenuScroll(menuList)
//        Log.d("scroll", "pos= $position")
    }

    private fun btnCat(context: Context){

        btnCat.setOnClickListener{

            val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item)
            for (ff in categoryList) arrayAdapter.add(ff.CategoryName)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Выберите категорию")
            builder.setNegativeButton(
                "Отмена"
            ) { dialog, which -> dialog.dismiss() }

            builder.setAdapter(
                arrayAdapter
            ) { dialog, which ->
                val fff: CatMenuModel = categoryList.get(which)
                (menuRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(adapter.scrollToCategory(fff.CategoryName), 0)
            }
            builder.show()
        }
    }
}


