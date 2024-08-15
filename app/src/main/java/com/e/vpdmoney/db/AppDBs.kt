package com.e.vpdmoney.db

import android.app.Application
import androidx.room.*
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.util.Constants


@TypeConverters(


)
@Database(entities =
[
 TransferRequest::class

],
    version = 1, exportSchema = false)
abstract class AppDBs: RoomDatabase() {

    abstract fun transferTransactionDao():TransferTransactionDao
    companion object {
        fun getDb(app: Application): AppDBs {
            return  Room.databaseBuilder(app, AppDBs::class.java, Constants.Database.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}