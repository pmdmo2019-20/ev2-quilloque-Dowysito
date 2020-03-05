package es.iessaladillo.pedrojoya.quilloque.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.quilloque.data.RecentCall
import es.iessaladillo.pedrojoya.quilloque.data.Sugerencia

interface Repository {




    fun insertContact(contact: Contact): Long
    fun insertCall(call: Call): Long
    fun queryContacts(): LiveData<List<Contact>>
    fun queryRecentCalls(limit: Long): LiveData<List<RecentCall>>
    fun querySugerencias(phoneNumber: String): LiveData<List<Sugerencia>>
    fun deleteContact(contact: Contact): Int
    fun deleteCall(call: Call): Int

}