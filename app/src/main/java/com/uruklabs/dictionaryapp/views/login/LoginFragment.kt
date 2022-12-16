package com.uruklabs.dictionaryapp.views.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.uruklabs.dictionaryapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        verifySaveCredentials()
        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val preferences = requireActivity().getSharedPreferences("credentials", 0)
                val editor = preferences.edit()
                editor.putString("email", binding.edtEmail.text.toString())
                editor.putString("password", binding.edtPassword.text.toString())
                editor.apply()
                editor.commit()
            } else {
                val preferences = requireActivity().getSharedPreferences("credentials", 0)
                val editor = preferences.edit()
                editor.clear()
                editor.commit()
            }
        }


    }


    private fun verifySaveCredentials(): Boolean {
        val preferences = requireActivity().getSharedPreferences("credentials", 0)
        val email = preferences.getString("email", "")
        val password = preferences.getString("password", "")
        if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
            return false
        } else {
            binding.edtEmail.setText(email)
            binding.edtPassword.setText(password)
            binding.switchCompat.isChecked = true
            return true
        }
    }


    private fun login() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.apply {
            isEnabled = false
            visibility = View.GONE
        }

        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            binding.edtEmail.error = "Email is required"
            binding.edtPassword.error = "Password is required"
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.apply {
                isEnabled = true
                visibility = View.VISIBLE
            }
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.apply {
                        isEnabled = true
                        isVisible = true
                    }
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
                }

            .addOnFailureListener(OnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.apply {
                    isEnabled = true
                    isVisible = true
                }
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            })


    }

}