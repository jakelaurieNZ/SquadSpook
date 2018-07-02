package com.jakelaurie.squadspook.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.jakelaurie.squadspook.R
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.model.ServersFilter
import com.jakelaurie.squadspook.ui.adapter.ServerListAdapter
import com.jakelaurie.squadspook.ui.misc.LineDividerDecoration
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModel
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class ServerListActivity : BaseActivity() {

    @Inject lateinit var serverListViewModelFactory: ServerListViewModelFactory
    @Inject lateinit var serverListAdapter: ServerListAdapter
    private lateinit var serverListViewModel: ServerListViewModel

    private var optionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()

        serverListSwipeRefresh.setOnRefreshListener {
            loadServers(true)
        }

        setupViewModelListeners()
        loadFilter()
        loadServers(false)
    }

    private fun setupUI() {
        serverListRecycler.layoutManager = LinearLayoutManager(this)
        serverListRecycler.adapter = serverListAdapter
        serverListAdapter.clickListener = {
            val intent = Intent(this, ServerDetailActivity::class.java)
            if(it != null) {
                intent.putExtras(ServerDetailActivity.getLaunchArgs(it.name, it.id))
                startActivity(intent)
            }
        }

        serverListRecycler.addItemDecoration(LineDividerDecoration(this))
        serverListSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_server_list, menu)
        optionsMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menuItemFilterEmptyServers -> {
                val menuItem = getMenuItem(R.id.menuItemFilterEmptyServers)
                if(menuItem != null) {
                    menuItem.isChecked = !menuItem.isChecked
                    serverListViewModel.setFilter(menuItem.isChecked)
                    return false
                }
                return true
            }
            R.id.menuItemLicenses -> {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModelListeners() {
        serverListViewModel = ViewModelProviders.of(this, serverListViewModelFactory)
                .get(ServerListViewModel::class.java)

        serverListViewModel.serversFilter.observe(this, Observer<ServersFilter> {
            getMenuItem(R.id.menuItemFilterEmptyServers)?.isChecked = it?.filterEmptyServers ?: false
        })

        serverListViewModel.serversResult.observe(this, Observer<List<Server>> {
            onLoadFinished()
            serverListAdapter.data = it?: emptyList()
        })

        serverListViewModel.serversError.observe(this, Observer<String> {
            onLoadFinished()
            showError(it, getString(R.string.retry), { loadServers(false) })
        })
    }

    private fun getMenuItem(@IdRes menuRes: Int): MenuItem? {
        return optionsMenu?.findItem(menuRes)
    }

    private fun loadFilter() {
        serverListViewModel.fetchFilter()
    }

    private fun loadServers(fromRefresh: Boolean) {
        serverListViewModel.refresh()
        if(!fromRefresh) {
            showLoadingIndicator(true)
        }
    }

    private fun onLoadFinished() {
        serverListSwipeRefresh.isRefreshing = false
        showLoadingIndicator(false)
    }

    override fun onDestroy() {
        serverListViewModel.disposeElements()
        super.onDestroy()
    }
}
