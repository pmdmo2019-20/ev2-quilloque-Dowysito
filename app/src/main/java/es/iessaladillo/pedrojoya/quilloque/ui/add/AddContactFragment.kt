package es.iessaladillo.pedrojoya.quilloque.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository
import es.iessaladillo.pedrojoya.quilloque.data.PhoneDatabase
import kotlinx.android.synthetic.main.contact_creation_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

class AddContactFragment:Fragment(R.layout.contact_creation_fragment) {
    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }

    private val viewModel: AddContactFragmentViewModel by viewModels{
        AddContactFragmentViewModelFactory(
            LocalRepository(PhoneDatabase.getInstance(requireContext()).dao)
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupAppBar()
    }

    private fun setupViews() {
         fabSave.setOnClickListener {
             if (!txtName.text.toString().isNullOrEmpty()&&!txtPhoneNumber.text.toString().isNullOrEmpty()){
                 viewModel.add(txtName.text.toString(),txtPhoneNumber.text.toString())
                 navController.navigateUp()
             }
         }
    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.contact_creation_title)
        }
    }

}