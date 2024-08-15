package com.e.vpdmoney.viewmodel

import androidx.lifecycle.*
import com.e.vpdmoney.db.LoadingStatus
import com.e.vpdmoney.db.Resource
import com.e.vpdmoney.db.Status
import com.e.vpdmoney.model.request.TransferRequest
import com.e.vpdmoney.viewmodel.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel@Inject constructor(
    private val localRepository: LocalRepository,
   ) : ViewModel() {

    private var storeTransferTransactionLiveData: MutableLiveData<Resource<Long>> = MutableLiveData()
    private var storeTransferTransactionMediatorLiveData = MediatorLiveData<Resource<Long>>()


    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

     private val _transferTransaction = MutableLiveData<MutableList<TransferRequest>?>()
      val transferTransaction : MutableLiveData<MutableList<TransferRequest>?>
        get() = _transferTransaction

    fun storeTransferTransaction(transferRequest: TransferRequest) {
        viewModelScope.launch {
            val result = localRepository.storeTransferTransaction(transferRequest)
             when(result.status){
                 Status.LOADING-> _loadingStatus.value = LoadingStatus.Loading("Sending...")
                 Status.SUCCESS->_loadingStatus.value = LoadingStatus.Success
                 Status.ERROR -> _loadingStatus.value = LoadingStatus.Error(404, "Transaction Error")
             }

            }
        }

    fun getTransferTransaction() {

        viewModelScope.launch {
            val result = localRepository.getTransferTransaction()
            when(result.status){
                Status.LOADING-> _loadingStatus.value = LoadingStatus.Loading("Loading...")
                Status.SUCCESS->{
                    _transferTransaction.value = result.data
                    _loadingStatus.value = LoadingStatus.Success
                }
                Status.ERROR -> _loadingStatus.value = LoadingStatus.Error(404, "Transaction Error")
            }

        }
    }

    fun getTransferTransactionBySubAccount(subAccount:String) {

        viewModelScope.launch {
            val result = localRepository.getTransferTransactionBySubAccount(subAccount)
            when(result.status){
                Status.LOADING-> _loadingStatus.value = LoadingStatus.Loading("Loading...")
                Status.SUCCESS->{
                    _transferTransaction.value = result.data
                    _loadingStatus.value = LoadingStatus.Success
                }
                Status.ERROR -> _loadingStatus.value = LoadingStatus.Error(404, "Transaction Error")
            }

        }
    }
   }

