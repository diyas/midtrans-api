package com.midtrans.api.main;

import com.midtrans.api.MidtransApiApplication;
import com.midtrans.api.domain.QrTransactionSuccess;
import com.midtrans.api.payload.gopay.ResponseGopay;
import com.midtrans.api.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/app")
public class ApplicationCtl {

    Logger logger = LoggerFactory.getLogger(ApplicationCtl.class);

//    @Autowired
//    private DbListener dbListener;

    @PostMapping(value = "/restart")
    @ApiIgnore
    public void restartApp(){
        MidtransApiApplication.restart();
        logger.info("MidtransApiApplication.restarting..");
    }


//    @PostMapping(value = "/transaction")
//    public ResponseEntity postTransaction(@RequestBody ResponseGopay responseGopay){
//        QrTransactionSuccess tr = dbListener.insertTransaction(responseGopay);
//        return Utility.setResponse("",tr);
//    }
//
//    @DeleteMapping(value = "/transaction")
//    public ResponseEntity deleteTransaction(@RequestBody ResponseGopay responseGopay){
//        dbListener.deleteTrasaction(responseGopay);
//        return Utility.setResponse("", HttpStatus.OK);
//    }
}
