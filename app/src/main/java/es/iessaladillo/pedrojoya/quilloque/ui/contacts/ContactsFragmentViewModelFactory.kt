package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository

class ContactsFragmentViewModelFactory(val localRepository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactsFragmentViewModel(localRepository) as T
    }
}