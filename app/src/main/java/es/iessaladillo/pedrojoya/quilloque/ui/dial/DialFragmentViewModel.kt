package es.iessaladillo.pedrojoya.quilloque.ui.dial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.quilloque.data.*
import java.util.*

class DialFragmentViewModel(private val repository: Repository) : ViewModel() {



    val number: MutableLiveData<String> = MutableLiveData("")

    val sugerenceList: LiveData<List<Sugerencia>> = repository.querySugerencias(number.value!!)


    fun createCall(number: String) {
        Thread{
            val date: Date = Date(System.currentTimeMillis())
            repository.insertCall(Call(0, CALL_TYPE_MADE,null,number,String.format("%s/%s/%s",date.day,date.month+1,date.year+1900),String.format("%s:%s",date.hours.toString(),date.minutes.toString())))
        }.start()
    }

    fun createVideoCall(number: String) {
        Thread{
            val date: Date = Date(System.currentTimeMillis())
            repository.insertCall(Call(0, CALL_TYPE_VIDEO,null,number,String.format("%s/%s/%s",date.day,date.month+1,date.year+1900),String.format("%s:%s",date.hours.toString(),date.minutes.toString())))
        }.start()
    }
}