package com.github.emmpann.first_question.thirdpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.first_question.R
import com.github.emmpann.first_question.data.DataItem
import com.github.emmpann.first_question.databinding.ActivityThirdBinding
import com.github.emmpann.first_question.secondpage.SecondActivity
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
                footer = LoadingStateAdapter()
            )

            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItem) {
                    val secondPage = Intent(this@ThirdActivity, SecondActivity::class.java)
                    secondPage.putExtra(SELECTED_USERNAME, "${data.firstName} ${data.lastName}")
                    setResult(10, secondPage)
                    finish()
                }
            })
        }

        viewModel.user.observe(this) {
            userAdapter.submitData(lifecycle, it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            userAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupClickListener() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        const val SELECTED_USERNAME = "username"
    }
}