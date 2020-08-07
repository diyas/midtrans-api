package com.midtrans.api.main;

import com.google.common.io.ByteSource;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.midtrans.api.listener.GopayListener;
import com.midtrans.api.payload.global.Request;
import com.midtrans.api.payload.global.Response;
import com.midtrans.api.payload.global.ResponseQr;
import com.midtrans.api.payload.global.ResponseQrversion;
import com.midtrans.api.payload.gopay.QrCode;
import com.midtrans.api.payload.gopay.ResponseGopay;
import com.midtrans.api.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class GopayCtl {

    @Autowired
    private GopayListener gopayListener;

    @PostMapping(value = "/gopay/checkout", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> checkoutPayment(@RequestBody Request req, HttpServletRequest servletRequest){
        ResponseEntity resp = gopayListener.postCharge(req, servletRequest, gopayListener.getHeader(true));
        return resp;
    }

    @GetMapping(value = "/gopay/{action}/{orderId}")
    public ResponseEntity<Response> checkStatus(@PathVariable String orderId, @PathVariable String action){
        ResponseEntity resp = gopayListener.getAction(orderId, action);
        return resp;
    }

    @GetMapping(value = "/gopay/qrcode/{orderId}")
    public ResponseEntity<Response> getQr(@PathVariable String orderId){
        ResponseEntity resp = gopayListener.getQr(orderId);
        return resp;
    }

    @PostMapping(value = "/gopay/qr")
    public ResponseQr getQr(@RequestBody Request req, HttpServletRequest servletRequest) throws IOException, NotFoundException {
        ResponseQr responseQr = new ResponseQr();
        ResponseQrversion responseQrversion = new ResponseQrversion();
        req.setOrderId("Swipepay-"+System.currentTimeMillis());
        req.setPaymentType("gopay");
        ResponseEntity<Response> respCharge = gopayListener.postCharge(req, servletRequest, gopayListener.getHeader(true, req.getUserId()));
        ResponseGopay respGopay = (ResponseGopay) respCharge.getBody().getData();
        ResponseEntity<byte[]> resp = gopayListener.getQr(respGopay.getTransactionId());
        InputStream input = new ByteArrayInputStream(resp.getBody());
        QrCode qrCode = Utility.getQrString(input);
        responseQr.setHsQrStringEnc(Utility.objectToString(qrCode, false));
        responseQr.setBatchGroupName("GOPAY");
        responseQr.setBatchGroupId(2);
        responseQr.setQrPayAppName("GOPAY");
        responseQr.setBaseAmount(qrCode.getData().getAmount());
        responseQr.setMidwareTimestamp(""+System.currentTimeMillis());
        responseQr.setIsQrisFlag(false);
        responseQr.setResponseCode("0000");
        responseQr.setHsQrStringLen(512);
        responseQr.setMerchantAddress1("Jalan mawar no 7 sukahati cibinong");
        responseQr.setMerchantAddress2("Bogor");
        responseQr.setMerchantName("Kedai Juragan Bebek");
        responseQr.setMerchantId(1);
        responseQr.setMessage("SUCCESS TO GENERATE QR CODE.");
        responseQr.setPrintReceiptMerchantName("First Payment");
        responseQr.setInvoiceNum(respGopay.getTransactionId());
        responseQr.setGeneralKsnIndex("FF8FF");
        responseQr.setPrintReceiptAddressLine1("Jalan First Payment No.11");
        responseQr.setPrintReceiptAddressLine2("Development");
        responseQr.setStatus("OK");
        responseQrversion.setDeviceCryptoVer("v0.3.0");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = format.format(new Date());
        responseQr.setCreateAt(dateString);
        responseQr.setVersion(responseQrversion);
        responseQr.setQrPayAppId(4);
        return responseQr;
    }

    @PostMapping(value = "/gopay/write/qr")
    private void writeQr(@RequestBody QrCode req) throws WriterException, IOException {
        gopayListener.writeQr("req", req);
    }


}
