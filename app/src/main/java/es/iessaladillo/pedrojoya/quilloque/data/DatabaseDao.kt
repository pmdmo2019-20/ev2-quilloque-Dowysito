package es.iessaladillo.pedrojoya.quilloque.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DatabaseDao {

    @Insert
    fun insertContact(contact: Contact): Long

    @Insert
    fun insertCall(call: Call): Long

    @Query("SELECT * FROM contact order by name")
    fun queryContacts(): LiveData<List<Contact>>

    @Query("SELECT C.id AS callId, C.phoneNumber AS phoneNumber, C.type AS type, C.date AS date, C.time AS time, T.id AS contactId, T.name AS contactName FROM Call AS C LEFT JOIN Contact AS T ON C.phoneNumber = T.phoneNumber ORDER BY C.id DESC LIMIT :limit")
    fun queryRecentCalls(limit: Long): LiveData<List<RecentCall>>

    @Query("SELECT name AS contactName, phoneNumber AS phoneNumber " +
            "FROM Contact " +
            "WHERE phoneNumber like :phoneNumber " +
            "UNION " +
            "SELECT DISTINCT phoneNumber AS contactName, phoneNumber AS phoneNumber " +
            "FROM Call " +
            "WHERE phoneNumber like :phoneNumber " +
            "AND phoneNumber NOT IN (SELECT phoneNumber FROM Contact)")
    fun querySugerencias(phoneNumber: String): LiveData<List<Sugerencia>>

    @Delete
    fun deleteContact(contact: Contact): Int

    @Delete
    fun deleteCall(call: Call): Int

}