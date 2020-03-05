package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.Contact
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository
import es.iessaladillo.pedrojoya.quilloque.data.PhoneDatabase
import kotlinx.android.synthetic.main.contacts_fragment.*
import kotlinx.android.synthetic.main.contacts_fragment_item.view.*
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*

class ContactsFragment: Fragment(R.layout.contacts_fragment) {


    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }

    private val viewModel: ContactsFragmentViewModel by viewModels{
        ContactsFragmentViewModelFactory(
            LocalRepository(PhoneDatabase.getInstance(requireContext()).dao)
        )
    }

    private val listAdapter: ContactsFragmentAdapter = ContactsFragmentAdapter().also {
        it.setOnItemClickListener {position,view ->
            val contact: Contact =it.getItem(position)
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
            val contact: Contact = it.getItem(position)
            viewModel.deleteContact(contact)
        }
        it.setOnCallClickListener {position ->
            val contact: Contact = it.getItem(position)
            viewModel.createCall(contact)
            val date: Date = Date(System.currentTimeMillis())
            Toast.makeText(context,getString(R.string.dial_call_made, String.format("%s/%s/%s",date.day,date.month+1,date.year+1900), String.format("%s:%s",date.hours.toString(),date.minutes.toString())),
                Toast.LENGTH_SHORT).show()
        }
        it.setOnVideoCallClickListener {position ->
            val contact: Contact = it.getItem(position)
            viewModel.createVideoCall(contact)
            val date: Date = Date(System.currentTimeMillis())
            Toast.makeText(context,getString(R.string.dial_videocall_made, String.format("%s/%s/%s",date.day,date.month+1,date.year+1900), String.format("%s:%s",date.hours.toString(),date.minutes.toString())),
                Toast.LENGTH_SHORT).show()
        }
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setupViews()
        setupRecyclerView()
        observe()
        setupAppBar()
    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.contacts_title)
        }
    }

    private fun observe() {
        viewModel.contactList.observe(this){
            listAdapter.submitList(it)
            if (it.isEmpty()){
                emptyView.visibility = View.VISIBLE
            }else{
                emptyView.visibility = View.INVISIBLE
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> navController.navigate(R.id.addContactFragment)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.contact_fragment_menu, menu)

        for (i in 0 until menu.size()) {
            var menuItem = menu.getItem(i)

            val icon = menuItem.icon
            if (icon != null) {
                DrawableCompat.setTint(
                    icon,
                    ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                )
            }
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupViews() {
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.contactList=MutableLiveData(viewModel.filter(p0.toString()))
            }
        })
    }

    private fun setupRecyclerView(){
        lstContacts.run{
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }

    }

}