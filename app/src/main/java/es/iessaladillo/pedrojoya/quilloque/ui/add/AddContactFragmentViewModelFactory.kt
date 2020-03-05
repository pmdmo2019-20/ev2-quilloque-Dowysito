package es.iessaladillo.pedrojoya.quilloque.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository

class AddContactFragmentViewModelFactory(val localRepository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddContactFragmentViewModel(localRepository) as T
    }
}
