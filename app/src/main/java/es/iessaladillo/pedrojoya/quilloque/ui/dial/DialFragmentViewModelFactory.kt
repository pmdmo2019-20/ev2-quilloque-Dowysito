package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.quilloque.data.LocalRepository
import es.iessaladillo.pedrojoya.quilloque.ui.dial.DialFragmentViewModel

class DialFragmentViewModelFactory(val localRepository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DialFragmentViewModel(localRepository) as T
    }
}