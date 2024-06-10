package com.lambui.demomodular.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.jack.testenverfit.ui.views.IgnoreLastDividerItemDecoration
import com.lambui.demomodular.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ScheduleAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            IgnoreLastDividerItemDecoration(
                this,
                IgnoreLastDividerItemDecoration.VERTICAL
            ).apply {
                ContextCompat.getDrawable(this@MainActivity, R.drawable.divider)
                    ?.let {
                        setDrawable(it)
                    }
            })
        lifecycleScope.launch {
            mainViewModel.schedule.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}