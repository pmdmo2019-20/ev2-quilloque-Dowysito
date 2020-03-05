package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.quilloque.data.*
import java.util.*

class ContactsFragmentViewModel(private val repository: Repository): ViewModel() {


    var contactList: LiveData<List<Contact>> = repository.queryContacts()



    fun filter(string:String):List<Contact>{
        return contactList.value!!.filter { x -> x.name.contains(string,true) }
    }

    fun deleteContact(contact: Contact) {
        Thread{
            repository.deleteContact(contact)
        }.start()
    }

    fun createCall(contact: Contact) {
        Thread{
            val date: Date = Date(System.currentTimeMillis())
            repository.insertCall(Call(0, CALL_TYPE_MADE,contact.name,contact.phone,String.format("%s/%s/%s",date.day,date.month+1,date.year+1900),String.format("%s:%s",date.hours.toString(),date.minutes.toString())))
        }.start()
    }

    fun createVideoCall(contact: Contact) {
        Thread{
            val date: Date = Date(System.currentTimeMillis())
            repository.insertCall(Call(0, CALL_TYPE_VIDEO,contact.name,contact.phone,String.format("%s/%s/%s",date.day,date.month+1,date.year+1900),String.format("%s:%s",date.hours.toString(),date.minutes.toString())))
        }.start()
    }
}
