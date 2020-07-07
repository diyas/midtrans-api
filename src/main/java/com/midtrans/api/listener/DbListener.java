package com.midtrans.api.listener;

import com.midtrans.api.domain.QrTransaction;
import com.midtrans.api.domain.QrTransactionSuccess;
import com.midtrans.api.payload.gopay.ResponseGopay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbListener {

//    @Autowired
//    QrTransactionRepo qrTransactionRepo;

//    @Autowired
//    QrTransactionSuccessRepo qrTransactionSuccessRepo;

//    public QrTransactionSuccess insertTransaction(ResponseGopay tr){
//        QrTransactionSuccess transaction = new QrTransactionSuccess(tr);
//        return qrTransactionSuccessRepo.save(transaction);
//    }

    public void deleteTrasaction(ResponseGopay tr){
//        QrTransaction transaction = qrTransactionRepo.findByTransactionId(tr.getTransactionId());
//        qrTransactionRepo.delete(transaction);
    }
}
