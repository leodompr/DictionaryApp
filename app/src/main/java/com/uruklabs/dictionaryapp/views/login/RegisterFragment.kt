package com.uruklabs.dictionaryapp.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.uruklabs.dictionaryapp.R
import com.uruklabs.dictionaryapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar2.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.fill_all_fields_msg), Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(requireContext(), getString(R.string.register_sucess_msg), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(email, password))
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(requireContext(), getString(R.string.register_failed_msg), Toast.LENGTH_SHORT).show()
                }
            }

            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

    }



}