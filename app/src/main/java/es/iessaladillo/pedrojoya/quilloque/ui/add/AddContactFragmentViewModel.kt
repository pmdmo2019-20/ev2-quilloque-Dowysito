package es.iessaladillo.pedrojoya.quilloque.ui.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.quilloque.data.Contact
import es.iessaladillo.pedrojoya.quilloque.data.Repository
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable

class AddContactFragmentViewModel(private val repository: Repository): ViewModel() {


    val contactList: MutableLiveData<List<Contact>> = MutableLiveData(emptyList())



    fun add(name:String, phone:String){
        Thread{
            println(repository.insertContact(Contact(0,name,phone)))
        }.start()
    }
}
