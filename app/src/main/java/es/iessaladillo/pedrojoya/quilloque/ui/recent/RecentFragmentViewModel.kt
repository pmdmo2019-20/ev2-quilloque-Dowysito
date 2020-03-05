package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.quilloque.data.*
import java.util.*

class RecentFragmentViewModel(private val repository: Repository): ViewModel() {


    var callList: LiveData<List<RecentCall>> = repository.queryRecentCalls(10)


    fun deleteCall(recentCall: RecentCall) {
        Thread{
            repository.deleteCall(Call(recentCall.callId,recentCall.type,recentCall.contactName,recentCall.phoneNumber,recentCall.date,recentCall.time))
        }.start()
    }

    fun createCall(recentCall: RecentCall) {
        Thread{
            val date: Date = Date(System.currentTimeMillis())
            repository.insertCall(Call(0, CALL_TYPE_MADE,recentCall.contactName,recentCall.phoneNumber,String.format("%s/%s/%s",date.day,date.month+1,date.year+1900),String.format("%s:%s",date.hours.toString(),date.minutes.toString())))
        }.start()
    }

    fun createVideoCall(recentCall: RecentCall) {
        Thread{
            val date: Date = Date(System.currentTimeMillis())
            repository.insertCall(Call(0, CALL_TYPE_VIDEO,recentCall.contactName,recentCall.phoneNumber,String.format("%s/%s/%s",date.day,date.month+1,date.year+1900),String.format("%s:%s",date.hours.toString(),date.minutes.toString())))
        }.start()
    }
}
