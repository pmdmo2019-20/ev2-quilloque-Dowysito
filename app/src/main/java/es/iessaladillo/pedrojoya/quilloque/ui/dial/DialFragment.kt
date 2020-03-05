package es.iessaladillo.pedrojoya.quilloque.ui.dial

import android.content.Context
import android.opengl.Visibility
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
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository
import es.iessaladillo.pedrojoya.quilloque.data.PhoneDatabase
import es.iessaladillo.pedrojoya.quilloque.data.Sugerencia
import es.iessaladillo.pedrojoya.quilloque.ui.contacts.DialFragmentViewModelFactory
import kotlinx.android.synthetic.main.dial_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.recent_fragment.*
import java.util.*

class DialFragment: Fragment(R.layout.dial_fragment) {

    private val viewModel: DialFragmentViewModel by viewModels{
        DialFragmentViewModelFactory(
            LocalRepository(PhoneDatabase.getInstance(requireContext()).dao)
        )
    }

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }

    private val listAdapter: DialFragmentAdapter = DialFragmentAdapter().also {
        it.setOnItemClickListener {position ->
            val sugerencia: Sugerencia =it.getItem(position)
            lblNumber.text=sugerencia.phoneNumber
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setupViews()
        setupAppBar()
        setupRecyclerView()
        observe()
    }

    private fun setupRecyclerView(){
        lstSuggestions.run{
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }

    }

    private fun observe() {
        viewModel.number.observe(this){
            lblNumber.text=it
            if (it == ""){
                imgBackspace.visibility= View.INVISIBLE
                imgVideo.visibility= View.INVISIBLE
            }else{
                imgBackspace.visibility= View.VISIBLE
                imgVideo.visibility= View.VISIBLE
            }
        }

        viewModel.sugerenceList.observe(this){
            listAdapter.submitList(it)
            if (it.isEmpty()){
                lblCreateContact.visibility= View.INVISIBLE
            }else{
                lblCreateContact.visibility= View.VISIBLE
            }
        }
    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setTitle(R.string.dial_title)
        }
    }

    private fun setupViews() {
        imgBackspace.visibility= View.INVISIBLE
        imgVideo.visibility= View.INVISIBLE
        lblOne.setOnClickListener { viewModel.number.value=viewModel.number.value+"1" }
        lblTwo.setOnClickListener { viewModel.number.value=viewModel.number.value+"2" }
        lblThree.setOnClickListener { viewModel.number.value=viewModel.number.value+"3" }
        lblFour.setOnClickListener { viewModel.number.value=viewModel.number.value+"4" }
        lblFive.setOnClickListener { viewModel.number.value=viewModel.number.value+"5" }
        lblSix.setOnClickListener { viewModel.number.value=viewModel.number.value+"6" }
        lblSeven.setOnClickListener { viewModel.number.value=viewModel.number.value+"7" }
        lblEight.setOnClickListener { viewModel.number.value=viewModel.number.value+"8" }
        lblNine.setOnClickListener { viewModel.number.value=viewModel.number.value+"9" }
        lblZero.setOnClickListener { viewModel.number.value=viewModel.number.value+"0" }
        lblAstherisc.setOnClickListener { viewModel.number.value=viewModel.number.value+"*" }
        lblPound.setOnClickListener { viewModel.number.value=viewModel.number.value+"#" }
        imgBackspace.setOnClickListener { viewModel.number.value=viewModel.number.value!!.substring(0,viewModel.number.value!!.length-1) }
        imgBackspace.setOnLongClickListener { borrarLargo()}
        imgVideo.setOnClickListener {
            val date: Date = Date(System.currentTimeMillis())
            Toast.makeText(context,getString(R.string.dial_videocall_made, String.format("%s/%s/%s",date.day,date.month+1,date.year+1900), String.format("%s:%s",date.hours.toString(),date.minutes.toString())),Toast.LENGTH_SHORT).show()
            viewModel.createVideoCall(lblNumber.text.toString())
        }
        fabCall.setOnClickListener {
            val date: Date = Date(System.currentTimeMillis())
            Toast.makeText(context,getString(R.string.dial_videocall_made, String.format("%s/%s/%s",date.day,date.month+1,date.year+1900), String.format("%s:%s",date.hours.toString(),date.minutes.toString())),Toast.LENGTH_SHORT).show()
            viewModel.createCall(lblNumber.text.toString())
        }
        lblCreateContact.setOnClickListener { navController.navigate(R.id.addContactFragment) }

    }

    private fun borrarLargo(): Boolean {
        viewModel.number.value =
            viewModel.number.value!!.substring(0, viewModel.number.value!!.length - 1)

        return true
    }

}