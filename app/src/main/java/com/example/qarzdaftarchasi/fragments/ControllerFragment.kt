package com.example.qarzdaftarchasi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.qarzdaftarchasi.R
import com.example.qarzdaftarchasi.databinding.FragmentControllerBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ControllerFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentControllerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentControllerBinding.inflate(inflater,container,false)
        val fm:FragmentManager = parentFragmentManager
        val fragment = BerMoneyFragment()
        fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit()

        binding.bottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.fragmentMenu1 ->{
                    val fragment1 = BerMoneyFragment()
                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment1).commit()
                }
                R.id.fragmentMenu2 ->{
                    val fragment2 = OlMoneyFragment()
                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment2).commit()
                }
            }
            true
        }

        menuService()

        return binding.root
    }

    private fun menuService() {

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ControllerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}