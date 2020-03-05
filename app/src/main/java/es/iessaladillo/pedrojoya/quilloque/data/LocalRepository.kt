package es.iessaladillo.pedrojoya.quilloque.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.quilloque.data.RecentCall
import es.iessaladillo.pedrojoya.quilloque.data.Sugerencia

class LocalRepository(private val dao: DatabaseDao) : Repository {

    override fun insertContact(contact: Contact): Long = dao.insertContact(contact)
    override fun insertCall(call: Call): Long = dao.insertCall(call)
    override fun queryContacts(): LiveData<List<Contact>> = dao.queryContacts()
    override fun queryRecentCalls(limit: Long): LiveData<List<RecentCall>> = dao.queryRecentCalls(limit)
    override fun querySugerencias(phoneNumber: String): LiveData<List<Sugerencia>> = dao.querySugerencias(phoneNumber)
    override fun deleteContact(contact: Contact): Int =dao.deleteContact(contact)
    override fun deleteCall(call: Call): Int = dao.deleteCall(call)
}