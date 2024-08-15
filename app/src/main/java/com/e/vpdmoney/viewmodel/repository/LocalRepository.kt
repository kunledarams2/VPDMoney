package com.e.vpdmoney.viewmodel.repository


import android.util.Log
import com.e.vpdmoney.BuildConfig

import com.e.vpdmoney.db.AppDBs
import com.e.vpdmoney.db.DataResult
import com.e.vpdmoney.db.Resource
import com.e.vpdmoney.model.request.TransferRequest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepository @Inject constructor(

    private val db: AppDBs,

) {

    private val ioDispatcher = Dispatchers.IO

    suspend fun storeTransferTransaction(transferTransaction: TransferRequest): Resource<Long> =
        withContext(ioDispatcher) {
            try {
                val farmerUpdateId =  db.transferTransactionDao().insertTransferRequestLocally(transferTransaction)
                farmerUpdateId?.let {
                    return@withContext Resource.success(it,"")
                }
                return@withContext Resource.error( "Error occur")

            } catch (e: Exception) {
                Log.e("addTransaction", ": ${e.localizedMessage}", e.cause)
                if (BuildConfig.DEBUG) {
                    return@withContext Resource.error("Local error"
                    )
                } else {
                    return@withContext Resource.error("Local error")
                }
            }
        }

    suspend fun getTransferTransaction(): Resource<MutableList<TransferRequest>> = withContext(ioDispatcher) {
        try {
            val transactions =db. transferTransactionDao().getTransaction()
            return@withContext Resource.success(transactions,"")

        } catch (e: Exception) {
            Log.e("getTransaction", ": ${e.localizedMessage}", e.cause)
            if (BuildConfig.DEBUG) {
                return@withContext Resource.error("Error from get all transaction"
                )
            } else {
                return@withContext Resource.error("Local Error from get all transaction")

            }
        }
    }

    suspend fun getTransferTransactionBySubAccount(subAccNumber: String): Resource<MutableList<TransferRequest>> =
        withContext(ioDispatcher) {
            try {
                val transaction = db.transferTransactionDao().getTransactionBySubAccNumber(subAccNumber)
                return@withContext Resource.success(transaction,"")


            } catch (e: Exception) {
                Log.e("getFarmerById", ": ${e.localizedMessage}", e.cause)
                if (BuildConfig.DEBUG) {
                    return@withContext Resource.error("Error"
                    )
                } else {
                    return@withContext Resource.error("Error")
                }
            }
        }





}