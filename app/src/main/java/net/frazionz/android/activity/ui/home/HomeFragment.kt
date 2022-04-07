package net.frazionz.android.activity.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import net.frazionz.android.R
import net.frazionz.android.activity.PostListView
import net.frazionz.android.databinding.FragmentHomeBinding
import net.frazionz.android.utils.FZUtils
import net.frazionz.android.utils.Post


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    var listView: ListView? = null
    private var currentPage = 1
    var adapter: PostListView? = null
    private var root: LinearLayout? = null
    private var moreData = true
    private val footer: View? = null
    private val snackbar: Snackbar? = null
    private var watchMorePrg: ProgressBar? = null
    private var watchMoreTxt: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        root = binding!!.getRoot()
        initializeViews()
        val footerListViewPost: View =
            (root!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.fragment_home_listview_footer,
                null,
                false
            )
        watchMorePrg = footerListViewPost.findViewById(R.id.watchMorePrg)
        watchMoreTxt = footerListViewPost.findViewById(R.id.watchMoreTxt)
        if (currentPage >= Post.getMaxPage()) {
            moreData = false
            watchMoreTxt!!.text = "Tu as atteint la fin :'("
        }
        listView!!.addFooterView(footerListViewPost)
        adapter = PostListView(root!!.context, R.layout.item_post)
        listView!!.adapter = adapter
        watchMoreTxt!!.setOnClickListener(View.OnClickListener {
            if (moreData) {
                listView!!.isEnabled = false
                watchMoreTxt!!.setText("Veuillez patienter..")
                watchMorePrg!!.setVisibility(View.VISIBLE)
                val handler = Handler() // hear is the handler for testing purpose
                handler.postDelayed({
                    currentPage += 1
                    bindData(true, currentPage)
                }, 1000)
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initializeViews() {
        listView = root!!.findViewById<View>(R.id.list) as ListView
    }

    private fun bindData(loadData: Boolean, page: Int) {
        if (loadData) {
            if (moreData) {
                FZUtils.loadMorePosts(root!!.context, page.toString(), false)
                if (adapter != null) adapter!!.notifyDataSetChanged()
                listView!!.isEnabled = true
                watchMoreTxt!!.text = "Voir plus"
                watchMorePrg!!.visibility = View.GONE
            }
            if (currentPage >= Post.getMaxPage()) {
                moreData = false
                watchMoreTxt!!.text = "Tu as atteint la fin :'("
                watchMorePrg!!.visibility = View.GONE
            }
            listView!!.isEnabled = true
        }
    }
}