package com.github.emmpann.first_question.ui.thirdpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.first_question.adapter.LoadingStateAdapter
import com.github.emmpann.first_question.adapter.UserAdapter
import com.github.emmpann.first_question.data.DataItem
import com.github.emmpann.first_question.data.Result
import com.github.emmpann.first_question.databinding.ActivityThirdBinding
import com.github.emmpann.first_question.ui.secondpage.SecondActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private val viewModel: ThirdViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        setupClickListener()
        setupObserver()
        setupObserver()
    }

    private fun setupObserver() {

        binding.rvUser.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this@ThirdActivity,
                    LinearLayoutManager(this@ThirdActivity).orientation
                )
            )

            userAdapter = UserAdapter()
            layoutManager = LinearLayoutManager(this@ThirdActivity)
            adapter = userAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    userAdapter.retry()
                }
            )

            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItem) {
                    val secondPage = Intent(this@ThirdActivity, SecondActivity::class.java)
                    secondPage.putExtra(SELECTED_USERNAME, "${data.firstName} ${data.lastName}").putExtra(
                        SELECTED_AVATAR, data.avatar)
                    setResult(10, secondPage)
                    finish()
                }
            })

        }

        viewModel.getUsers(5).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    result.data.observe(this) { pagingData ->
                        userAdapter.submitData(lifecycle, pagingData)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUsers(5).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                    is Result.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        result.data.observe(this) { pagingData ->
                            userAdapter.submitData(lifecycle, pagingData)
                        }
                    }
                    is Result.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupClickListener() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        const val SELECTED_USERNAME = "username"
        const val SELECTED_AVATAR = "avatar_url"
    }
}