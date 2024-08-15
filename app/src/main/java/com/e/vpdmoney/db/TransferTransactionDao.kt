package com.e.vpdmoney.db

import androidx.room.*

import com.e.vpdmoney.model.request.TransferRequest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface TransferTransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun bulkInsertTransaction(vararg transaction: TransferRequest): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransferRequestLocally(transaction: TransferRequest): Long

    @Transaction
    @Query("select * from Transfer ")
     fun getTransaction(): MutableList<TransferRequest>

    @Query("select * from Transfer where sender_account_number = :subAccount")
     fun getTransactionBySubAccNumber(subAccount:  String): MutableList<TransferRequest>

    @Query("select * from Transfer where reference = :reference")
    fun  getTransactionByReference(reference:  String): TransferRequest?
}