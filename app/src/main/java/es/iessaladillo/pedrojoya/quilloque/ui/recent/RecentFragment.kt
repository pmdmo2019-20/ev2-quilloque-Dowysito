package es.iessaladillo.pedrojoya.quilloque.ui.recent

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.Contact
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository
import es.iessaladillo.pedrojoya.quilloque.data.PhoneDatabase
import es.iessaladillo.pedrojoya.quilloque.data.RecentCall
import es.iessaladillo.pedrojoya.quilloque.ui.contacts.RecentFragmentAdapter
import es.iessaladillo.pedrojoya.quilloque.ui.contacts.RecentFragmentViewModel
import es.iessaladillo.pedrojoya.quilloque.ui.contacts.RecentFragmentViewModelFactory
import kotlinx.android.synthetic.main.contacts_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.recent_fragment.*
import kotlinx.android.synthetic.main.recent_fragment.emptyView
import kotlinx.android.synthetic.main.recent_fragment_item.*
import kotlinx.android.synthetic.main.recent_fragment_item.view.*
import java.util.*


class RecentFragment: Fragment(R.layout.recent_fragment) {

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }


    private val viewModel: RecentFragmentViewModel by viewModels{
        RecentFragmentViewModelFactory(
            LocalRepository(PhoneDatabase.getInstance(requireContext()).dao)
        )
    }

    private val listAdapter: RecentFragmentAdapter = RecentFragmentAdapter().also {
        it.setOnItemClickListener {position,view ->
            val recentCall: RecentCall =it.getItem(position)
            if (view.btnCall.visibility== View.VISIBLE){
                view.btnCall.visibility= View.GONE
            }else{
                view.btnCall.visibility= View.VISIBLE
            }
            if (view.btnDelete.visibility== View.VISIBLE){
                view.btnDelete.visibility= View.GONE
            }else{
                view.btnDelete.visibility= View.VISIBLE
            }
            if (view.btnMessage.visibility== View.VISIBLE){
                view.btnMessage.visibility= View.GONE
            }else{
                view.btnMessage.visibility= View.VISIBLE
            }
            if (view.btnVideoCall.visibility== View.VISIBLE){
                view.btnVideoCall.visibility= View.GONE
            }else{
                view.btnVideoCall.visibility= View.VISIBLE
            }
        }
        it.setOnDeleteClickListener {position ->
            val recent: RecentCall = it.getItem(position)
            viewModel.deleteCall(recent)
        }
        it.setOnCallClickListener {position ->
            val recent: RecentCall = it.getItem(position)
            viewModel.createCall(recent)
            val date: Date = Date(System.currentTimeMillis())
            Toast.makeText(context,getString(R.string.dial_call_made, String.format("%s/%s/%s",date.day,date.month+1,date.year+1900), String.format("%s:%s",date.hours.toString(),date.minutes.toString())),
                Toast.LENGTH_SHORT).show()
        }
        it.setOnVideoCallClickListener {position ->
            val recent: RecentCall = it.getItem(position)
            viewModel.createVideoCall(recent)
            val date: Date = Date(System.currentTimeMillis())
            Toast.makeText(context,getString(R.string.dial_videocall_made, String.format("%s/%s/%s",date.day,date.month+1,date.year+1900), String.format("%s:%s",date.hours.toString(),date.minutes.toString())),
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setupAppBar()
        setupRecyclerView()
        observe()
    }

//    private fun setUpViews() {
//      No me ha dado tiempode meterlo en el viewHolder
//        lblCreateContact.setOnClickListener { navController.navigate(R.id.addContactFragment) }
//    }

    private fun observe() {
        viewModel.callList.observe(this){
            listAdapter.submitList(it)
            if (it.isEmpty()){
                emptyView.visibility = View.VISIBLE
            }else{
                emptyView.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.recent_title)
        }
    }

    private fun setupRecyclerView(){
        lstCalls.run{
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }

    }


}