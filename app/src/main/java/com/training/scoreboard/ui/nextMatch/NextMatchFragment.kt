package com.training.scoreboard.ui.nextMatch


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner

import com.training.scoreboard.R
import com.training.scoreboard.data.model.NextMatch
import com.training.scoreboard.ui.base.BaseFragment
import com.training.scoreboard.ui.detail.next.DetailNextActivity
import com.training.scoreboard.utils.extension.invisible
import com.training.scoreboard.utils.extension.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject

class NextMatchFragment : BaseFragment(), AnkoComponent<Context>, NextMatchView {

    companion object {
        const val TAG = "NextMatchFragment"
        fun newInstance(): NextMatchFragment = NextMatchFragment()
    }

    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    @Inject
    internal lateinit var presenter: NextMatchPresenter<NextMatchView>

    @Inject
    internal lateinit var mAdapter: NextMatchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onAttach(this)

        listEvent.adapter = mAdapter
        presenter.getNextMatchList("4328")

        swipeRefresh.onRefresh {
            presenter.getNextMatchList("4328")
        }

        mAdapter.setOnItemCLickListener {
            ctx.startActivity<DetailNextActivity>(DetailNextActivity.DETAIL_NEXT_INTENT to it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            createView(AnkoContext.create(ctx))

    override fun showNextMatchList(data: List<NextMatch>) {
        swipeRefresh.isRefreshing = false
        mAdapter.addToList(data)
    }

    override fun showProgress() {
        progressBar.visible()
    }

    override fun hideProgress() {
        progressBar.invisible()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = R.id.next_match_fragment
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.listEventNext
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}
