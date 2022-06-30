package com.example.qarzdaftarchasi.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.qarzdaftarchasi.adapters.UsersAdapter
import com.example.qarzdaftarchasi.databaseRoom.database.AppDatabase
import com.example.qarzdaftarchasi.databaseRoom.entities.Contact
import com.example.qarzdaftarchasi.databinding.DialogViewBinding
import com.example.qarzdaftarchasi.databinding.FragmentBerMoneyBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BerMoneyFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentBerMoneyBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var adapter: UsersAdapter
    private lateinit var list: ArrayList<Contact>
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBerMoneyBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        list = ArrayList(appDatabase.contactDao().getAllContacts())
        adapter = UsersAdapter(list)
        adapter.notifyItemInserted(list.size)
        binding.rv.adapter = adapter

        askPermission()

        dialogService()

        return binding.root
    }

    private fun dialogService() {
        binding.addBtn.setOnClickListener {
            val itemBinding = DialogViewBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(itemBinding.root)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
            itemBinding.saveUser.setOnClickListener {
                val name = itemBinding.name.text.toString()
                val number = itemBinding.number.text.toString()
                val contact = Contact(name = name, number = number, image = uri.toString())
                list.add(contact)
                adapter = UsersAdapter(list)
                appDatabase.contactDao().addContact(contact)
                adapter.notifyItemInserted(list.size)
                binding.rv.adapter = adapter
                dialog.dismiss()
            }
            itemBinding.imageInGallery.setOnClickListener {
                onAddImagesClicked()
                itemBinding.imageDialog.setImageURI(uri)
            }
        }
    }

    private fun onAddImagesClicked() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent = Intent.createChooser(intent, "Choose a file")
        someActivityResultLauncher.launch(intent)
    }

    private fun askPermission() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }
            }).check()
    }

    private var someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            uri = data?.data
            uri?.let {
                requireActivity().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BerMoneyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}