package com.github.emmpann.first_question.firstpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.emmpann.first_question.R
import com.github.emmpann.first_question.databinding.ActivityMainBinding
import com.github.emmpann.first_question.secondpage.SecondActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            val secondPage = Intent(this, SecondActivity::class.java)
            secondPage.putExtra(USERNAME, binding.tvName.text.toString())
            if (binding.tvName.text.toString().isEmpty()) {
                binding.tvNameLayout.isHelperTextEnabled = true
            } else {
                startActivity(secondPage)
                binding.tvNameLayout.isHelperTextEnabled = false
            }
        }

        binding.tvPalindromeLayout.setOnClickListener {
            binding.tvPalindromeLayout.isHelperTextEnabled = false
        }

        binding.btnCheck.setOnClickListener {
            if (binding.tvPalindrome.text.toString().isEmpty()) {
                binding.tvPalindromeLayout.isHelperTextEnabled = true
            } else {
                binding.tvPalindromeLayout.isHelperTextEnabled = false
                if (isPalindrome(binding.tvPalindrome.text.toString())) {
                    showDialog("isPalindrome")
                } else {
                    showDialog("not palindrome")
                }
            }
        }
    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setNeutralButton("Ok") { _, _ -> }
            .show()
    }

    private fun isPalindrome(str: String): Boolean {
        val cleanStr = str.replace(Regex("[^a-zA-Z0-9]"), "").toLowerCase()

        var left = 0
        var right = cleanStr.length - 1

        while (left < right) {
            if (cleanStr[left] != cleanStr[right]) {
                return false
            }
            left++
            right--
        }
        return true
    }

    companion object {
        const val USERNAME = "user_name"
    }
}