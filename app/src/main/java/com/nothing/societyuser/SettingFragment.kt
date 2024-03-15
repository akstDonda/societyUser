package com.nothing.societyuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.nothing.societyuser.databinding.FragmentSettingBinding
import com.nothing.societyuser.setting.ChangePasswordActivity
import com.nothing.societyuser.setting.informationAcctivity
import com.nothing.societyuser.welcome.LoginActivity

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Your initialization logic goes here
        binding.llChangePassword.setOnClickListener(){

            val intent = Intent(requireActivity(), ChangePasswordActivity::class.java)
            startActivity(intent)

        }
        binding.llCustomerCare.setOnClickListener(){
            //TODO:activity name change
            val intent = Intent(requireActivity(), cutomer_care::class.java)
            startActivity(intent)
        }



        binding.llInformation.setOnClickListener(){
            val intent = Intent(requireActivity(), informationAcctivity::class.java)
            startActivity(intent)
        }

        //logOut
        binding.logOutBtn.setOnClickListener() {
            val firebaseAuth = FirebaseAuth.getInstance()
            //TODO:DialogBox
            firebaseAuth.signOut()
            var intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
