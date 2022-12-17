package com.uruklabs.dictionaryapp.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.uruklabs.dictionaryapp.R
import com.uruklabs.dictionaryapp.databinding.FragmentLoginBinding



class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth : FirebaseAuth
    private val args by navArgs<LoginFragmentArgs>()

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
                val preferences = requireActivity().getSharedPreferences(KEY_PREFERENCS_CREDENTIALS, 0)
                val editor = preferences.edit()
                editor.putString(KEY_PREFERENCS_EMAIL, binding.edtEmail.text.toString())
                editor.putString(KEY_PREFERENCS_PASSWORD, binding.edtPassword.text.toString())
                editor.apply()
                editor.commit()
            } else {
                val preferences = requireActivity().getSharedPreferences(KEY_PREFERENCS_CREDENTIALS, 0)
                val editor = preferences.edit()
                editor.clear()
                editor.commit()
            }
        }


    }

    override fun onResume() {
        super.onResume()
        if (args.email != " " && args.passowrd != " ") {
            binding.edtEmail.setText(args.email)
            binding.edtPassword.setText(args.passowrd)
        }
    }


    private fun verifySaveCredentials(): Boolean {
        val preferences = requireActivity().getSharedPreferences(KEY_PREFERENCS_CREDENTIALS, 0)
        val email = preferences.getString(KEY_PREFERENCS_EMAIL, "")
        val password = preferences.getString(KEY_PREFERENCS_PASSWORD, "")
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
            binding.edtEmail.error = getString(R.string.email_required)
            binding.edtPassword.error = getString(R.string.passowrd_required)
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
                    Toast.makeText(requireContext(), getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
                }
                }

            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.apply {
                    isEnabled = true
                    isVisible = true
                }
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            }


    }

    companion object {
        const val KEY_PREFERENCS_CREDENTIALS = "credentials"
        const val KEY_PREFERENCS_EMAIL = "email"
        const val KEY_PREFERENCS_PASSWORD = "password"
    }
}