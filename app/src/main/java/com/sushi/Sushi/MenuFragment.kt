package com.sushi.Sushi

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Transliterator
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.sushi.Sushi.adapters.CategoryAdapter
import com.sushi.Sushi.adapters.MenuAdapter
import com.sushi.Sushi.adapters.PromoAdapter
import com.sushi.Sushi.dialog.CitiDialog
import com.sushi.Sushi.dialog.OptionsDialog
import com.sushi.Sushi.dialog.PromoDialog
import com.sushi.Sushi.listener.EventListenerss
import com.sushi.Sushi.listener.RecyclerItemClickListenr
import com.sushi.Sushi.models.CatMenuModel
import com.sushi.Sushi.models.PromoModel
import com.sushi.Sushi.singleton.BasketSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
    private lateinit var  mPromoAdapter: PromoAdapter
    private lateinit var promoRecyclerView : RecyclerView
    lateinit var  menuRecyclerView  : RecyclerView
    private lateinit var adapter : MenuAdapter
    val menuList : ArrayList<CatMenuModel> = ArrayList()
    val categoryList: ArrayList<CatMenuModel> = ArrayList()
    val promoList: ArrayList<PromoModel> = ArrayList()
    private lateinit var optionsBtn: ImageButton
    private lateinit var btnUp: Button

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
        optionsBtn = root.findViewById(R.id.btn_options)
        btnUp = root.findViewById(R.id.btn_up)

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
        menuRecyclerView.setItemViewCacheSize(300)
        menuRecyclerView.isDrawingCacheEnabled = true

        promoRecyclerView = root.findViewById(R.id.recyclerview_promo)
        mPromoAdapter = PromoAdapter()
        promoRecyclerView.adapter = mPromoAdapter
        promoRecyclerView.layoutManager = LinearLayoutManager(
            root.context,
            RecyclerView.HORIZONTAL,
            false
        )
        promoRecyclerView.setHasFixedSize(true)

        categoryRecyclerView = root.findViewById(R.id.recyclerview_category)
        mCategoryAdapter = CategoryAdapter()
        categoryRecyclerView.adapter = mCategoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(
            root.context,
            RecyclerView.HORIZONTAL,
            false
        )
        categoryRecyclerView.setHasFixedSize(true)

        loadPromo()
        loadMenu()
//        loadCiti(root.context)

        CoroutineScope(Dispatchers.IO).launch {

            val online = isOnline(root.context)

            Log.d("FF", "Internet = " + online)
//            loadAddress(root.context, online)
        }

//        btnUp()
        promoClick(root.context, promoModel = PromoModel())
        scrollCat(root.context)
        btnOptions(root.context)
//        dateTime()

        return root
    }


    companion object {


        fun newInstance() = MenuFragment()

    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isOnline(context: Context): Boolean {
//        val connectivityManager =
//                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

    private fun loadPromo(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("PictureSale/Items")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {

                    val promoModel = ds.getValue(PromoModel::class.java)!!
                    promoList.add(promoModel)


                    Log.d("promo", "promo = $promoList")
                    Log.d("promo", "promo ds = $ds")
                    Log.d("promo", "promo data= ${dataSnapshot.children}")

                }
                mPromoAdapter.setupPromo(promoList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("dima", "Failed to read value.", error.toException())
            }
        })
    }

    private fun loadMenu() {
        Log.d("AA", "value =  прошло 1")

        val database = FirebaseDatabase.getInstance()
        Log.d("AA", "value =  прошло 2")
        val myRef = database.getReference("RestaurantsMenu/TeaTemple")
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

    private fun updateAdapterCategory() {
        mCategoryAdapter.setupCategory(categoryList)

        progress_bar_two.visibility = View.VISIBLE
        progress_bar_two.visibility = View.INVISIBLE

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun loadAddress(context: Context, boolean: Boolean) {
//
//
//        if(boolean == true){
//
//            if (ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//
//                return
//            }
//            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
//                val latitude = it.result?.latitude
//                val longitude = it.result?.longitude
//
//                Log.d("Result", "lat = " + latitude)
//                Log.d("Result", "llong = " + longitude)
//
//                if(latitude == null){
//                    Toast.makeText(context, "Интернета нет", Toast.LENGTH_SHORT).show()
//
//                }else{
//                    val locationA = Location("Point A")
//
//                    locationA.latitude = latitude!!
//                    locationA.longitude = longitude!!
//
//                    val locationB = Location("Point B")
//
//                    for (i in dangerousArea){
//                        locationB.longitude = i.longitude
//                        locationB.latitude = i.latitude
//                    }
//                    val currentDistance = locationA.distanceTo(locationB)
//
//                    if (currentDistance > 3000){
//
//                        btnGetLoc.setBackgroundResource(R.color.colorTangerine)
//                    }else{
//
//                        btnGetLoc.setBackgroundResource(R.color.colorgreen)
//                    }
//
//                    val geocoder = Geocoder(context, Locale.getDefault())
//
//                    val addresses = geocoder.getFromLocation(latitude!!, longitude!!, 1)
//
//
//                    val address = addresses[0].getAddressLine(0)
//
//
//                    Address.address = address
//
//
//                    progress_bar.visibility = View.INVISIBLE
//                    btnGetLoc.visibility = View.VISIBLE
//                    btnGetLoc.text = address
//
//                }
//            }
//        }
//    }

    private fun addArea() {

        dangerousArea = ArrayList()
        dangerousArea.add(LatLng(57.1344598, 65.4966976))
    }

    override fun updateRR() {
        updateMenuAdapter(menuList)
    }

    private fun scrollCat(context: Context){
        categoryRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListenr(context, menuRecyclerView,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {

                        val fff: CatMenuModel = categoryList[position]
                        Log.d("fff", "fff= " + fff)
                        (menuRecyclerView.layoutManager as LinearLayoutManager)
                            .scrollToPositionWithOffset(
                                adapter.scrollToCategory(fff.CategoryName),
                                0
                            )
                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }
                })
        )
    }

    private fun promoClick(context: Context, promoModel: PromoModel){
        promoRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListenr(context, promoRecyclerView,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {

                        PromoDialog.openDialog(context, promoModel, position, promoList)

                    }

                    override fun onItemLongClick(view: View?, position: Int) {

                    }
                })
        )
    }

    private fun btnOptions(context: Context){
        optionsBtn.setOnClickListener {
            OptionsDialog.openDialog(context)
        }
    }

    private fun btnUp(){

        btnUp.setOnClickListener {

            (menuRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(1)

        }
    }

    private fun loadCiti(context: Context) {
        val pref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val loadCiti = pref!!.getString("citi", "")

        if(loadCiti.toString().isEmpty()){
            CitiDialog.openDialog(context)
        }
    }
}


