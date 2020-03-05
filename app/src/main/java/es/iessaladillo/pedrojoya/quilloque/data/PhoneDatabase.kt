package es.iessaladillo.pedrojoya.quilloque.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Call::class,Contact::class], version = 1)
abstract class PhoneDatabase : RoomDatabase() {

    abstract val dao: DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PhoneDatabase? = null

        fun getInstance(context: Context): PhoneDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PhoneDatabase::class.java,
                            "database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}