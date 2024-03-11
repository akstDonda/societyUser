package com.nothing.societyuser

import HomeCategoryAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nothing.societyuser.Model.HomeCategoryModel
import com.nothing.societyuser.complain.ComplainRaiseActivity
import com.nothing.societyuser.wallet.WalletActivity
import com.nothing.societyuser.welcome.HomeActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: HomeCategoryAdapter
    private lateinit var categoryList: ArrayList<HomeCategoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView = view.findViewById(R.id.category_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter= HomeCategoryAdapter(categoryList)

        adapter.onItemClickListener = object : HomeCategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> {
                        intentFun(WalletActivity::class.java)
                    }
                    1 ->{
                        // Replace the current fragment with XYZFragment
                        intentFun(ComplainRaiseActivity::class.java)

                    }
                    2 ->{
                        intentFun(ComplainRaiseActivity::class.java)

                    }
                    4->{
                        intentFun(WalletActivity::class.java)
                    }

                    else -> {
                        // Handle other positions if necessary
                    }
                }
            }
        }

        recyclerView.adapter=adapter
    }

    private fun dataInitialize() {
        categoryList = ArrayList()

        categoryList.add(HomeCategoryModel(R.drawable.pay_icon, "Pay"))
        categoryList.add(HomeCategoryModel(R.drawable.baseline_add_complain_24, "complain"))
        categoryList.add(HomeCategoryModel(R.drawable.baseline_meeting_24, "meeting"))
        categoryList.add(HomeCategoryModel(R.drawable.baseline_chat_24, "chat"))
        categoryList.add(HomeCategoryModel(R.drawable.wallet_icon, "wallet"))
        categoryList.add(HomeCategoryModel(R.drawable.baseline_more_forward_ios_24, "More"))

    }
    fun intentFun(destination: Class<*>) {
        val intent = Intent(requireContext(), destination)
        startActivity(intent)
    }

    //toast


    private fun startHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}