package es.iessaladillo.pedrojoya.quilloque.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call")
data class Call(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "contactName")
    var contactName: String?,
    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "time")
    var time: String
)


data class Sugerencia(
    val contactName: String?,
    val phoneNumber: String
)

data class RecentCall(
    val callId: Long,
    val phoneNumber: String,
    val type: String,
    val date: String,
    val time: String,
    val contactId: Long,
    val contactName: String?
)