package com.jakelaurie.squadspook.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.jakelaurie.squadspook.R
import com.jakelaurie.squadspook.model.Player
import com.jakelaurie.squadspook.ui.adapter.PlayerListAdapter
import com.jakelaurie.squadspook.ui.misc.LineDividerDecoration
import com.jakelaurie.squadspook.ui.viewmodel.serverdetail.PlayerListViewModel
import com.jakelaurie.squadspook.ui.viewmodel.serverdetail.PlayerListViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class ServerDetailActivity: BaseActivity() {
    companion object {
        private const val keyServerName = "kServerName"
        private const val keyServerId = "kServerId"

        fun getLaunchArgs(serverName: String, serverId: String): Bundle {
            val bundle = Bundle()
            bundle.putString(keyServerName, serverName)
            bundle.putString(keyServerId, serverId)
            return bundle
        }
    }

    @Inject lateinit var playerListViewModelFactory: PlayerListViewModelFactory
    @Inject lateinit var playerListAdapter: PlayerListAdapter
    @Inject lateinit var playerListViewModel: PlayerListViewModel

    lateinit var serverName: String
    lateinit var serverId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val extras = intent.extras
        serverName = extras.getString(keyServerName, "-")
        serverId = extras.getString(keyServerId)

        setupUI()

        playerListSwipeRefresh.setOnRefreshListener {
            loadPlayers(true)
        }

        setupViewModelListeners()
        loadPlayers(false)
    }

    private fun setupUI() {
        title = serverName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_white)

        playerListRecycler.layoutManager = LinearLayoutManager(this)
        playerListRecycler.adapter = playerListAdapter

        playerListRecycler.addItemDecoration(LineDividerDecoration(this))
        playerListSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModelListeners() {
        playerListViewModel = ViewModelProviders.of(this, playerListViewModelFactory)
                .get(PlayerListViewModel::class.java)


        playerListViewModel.playersResult.observe(this, Observer<List<Player>> {
            onLoadFinished()
            playerListAdapter.data = it?: emptyList()
        })

        playerListViewModel.playersError.observe(this, Observer<String> {
            onLoadFinished()
            showError(it, getString(R.string.retry), { loadPlayers(false) })
        })
    }

    private fun loadPlayers(fromRefresh: Boolean) {
        if(!fromRefresh) {
            showLoadingIndicator(true)
        }

        playerListViewModel.loadPlayers(serverId)
    }

    private fun onLoadFinished() {
        playerListSwipeRefresh.isRefreshing = false
        showLoadingIndicator(false)
    }

    override fun onDestroy() {
        playerListViewModel.disposeElements()
        super.onDestroy()
    }
}