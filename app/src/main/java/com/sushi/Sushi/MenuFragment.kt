package com.sushi.Sushi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
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
    private lateinit var  menuRecyclerView  : RecyclerView
    private lateinit var adapter : MenuAdapter
    val menuList : ArrayList<CatMenuModel> = ArrayList()

    private var mCategoryRef: DatabaseReference? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("MMM", "OnAttah = ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
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

        addArea()
        BasketSingleton.subscribe(this)
        categoryRecyclerView = root.findViewById(R.id.recyclerview_category)
        mCategoryAdapter = CategoryAdapter()
        categoryRecyclerView.adapter = mCategoryAdapter
        categoryRecyclerView.layoutManager =
                LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
        categoryRecyclerView.setHasFixedSize(true)

        menuRecyclerView = root.findViewById(R.id.recycler_view_menu)
        adapter = MenuAdapter()
        menuRecyclerView.adapter = adapter
        menuRecyclerView.layoutManager = LinearLayoutManager(
            root.context,
            RecyclerView.VERTICAL,
            false
        )
        menuRecyclerView.setHasFixedSize(true)

        LoadCategory()
        LoadMenu()

        CoroutineScope(Dispatchers.IO).launch {

            val online = isOnline(root.context)

            Log.d("FF", "Internet = " + online)
//            loadAddress(root.context, online)
        }

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


    private fun LoadMenu() {


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("RestarauntMenu")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (ds in dataSnapshot.children) {
                    val value = ds.getValue(CatMenuModel::class.java)!!

                    Log.d("AA", "value = " + value.CategoryName)



                    menuList.add(value)
                }

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

    private fun LoadCategory() {

        val category: ArrayList<CategoryModel> = ArrayList()
        val database = FirebaseDatabase.getInstance()


        mCategoryRef = database.getReference("Category")

        mCategoryRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dss in dataSnapshot.children) {
                    val items = dss.value as Map<String, String>?

                    val Mcategory = CategoryModel()
                    Mcategory.name = items!!["name"].toString()

                    category.add(Mcategory)
                }

                updateAdapterCategory(category)
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }

    private fun updateAdapterCategory(categoryList: ArrayList<CategoryModel>) {

        mCategoryAdapter.setupCategory(categoryList = categoryList)

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

    override fun onStart() {
        super.onStart()
        Log.d("MMM", "onStart = ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MMM", "OnResume = ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MMM", "OnPause = ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MMM", "OnStop = ")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("MMM", "OnDestroyView = ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MMM", "OnDestroy = ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("MMM", "OnDetach = ")
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

}
