package com.midtrans.api.listener;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.midtrans.api.model.*;
import com.midtrans.api.payload.global.Request;
import com.midtrans.api.payload.global.Response;
import com.midtrans.api.payload.gopay.*;
import com.midtrans.api.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GopayListener {

    @Autowired
    private SettingProperties settingProperties;

    @Autowired
    private FirstPaymentProperties fPProperties;

//    @Autowired
//    private QrTransactionRepo qrTransactionRepo;

    HttpHeaders headers = new HttpHeaders();

    public HttpHeaders getHeader(boolean isCredential, String userId){
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        if (isCredential) {
            if (userId == null)
                headers.set("X-Override-Notification", settingProperties.getApi().getNotifurl().replace("/user-id",""));
            else
                headers.set("X-Override-Notification", settingProperties.getApi().getNotifurl().replace("user-id", userId));
            headers.setBasicAuth(settingProperties.getApi().getCredential());
        }
        return headers;
    }

    public HttpHeaders getHeader(boolean isCredential){
        return getHeader(isCredential, null);
    }

//    public HttpHeaders getHeader(boolean isCredential, String userid){
//        headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        if (isCredential) {
//            headers.set("X-Override-Notification", settingProperties.getApi().getNotifurl()+"/"+userid);
//            headers.setBasicAuth(settingProperties.getApi().getCredential());
//        }
//        return headers;
//    }

    private void setHeader(HttpHeaders httpHeaders){
        this.headers = httpHeaders;
    }

//    public ResponseEntity<Response> postCharge(Request req, HttpServletRequest servletRequest, HttpHeaders httpHeaders, String userId){
//        return postCharge(req, servletRequest, getHeader(true, userId));
//
//    }

    public ResponseEntity<Response> postCharge(Request req, HttpServletRequest servletRequest, HttpHeaders httpHeaders) throws HttpClientErrorException{
        ResponseEntity<ResponseGopay> responseEntity = null;
        RequestCharge rc = new RequestCharge();
        Gopay gopay = new Gopay();
        gopay.setEnableCallback(true);
        gopay.setCallbackUrl(settingProperties.getApi().getNotifurl());
        TransactionDetails td = new TransactionDetails();
        td.setOrderId(req.getOrderId());
        td.setGrossAmount(req.getAmount());
        rc.setPaymentType(req.getPaymentType());
        rc.setTransactionDetails(td);

        String url = settingProperties.getApi().getBaseurl().get(0) + settingProperties.getApi().getPath().get(0).getLink();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<RequestCharge> entity = new HttpEntity<RequestCharge>(rc, httpHeaders);
        try {
            responseEntity =  restTemplate.exchange(url, HttpMethod.POST, entity, ResponseGopay.class);
            ResponseGopay resp = responseEntity.getBody();
            List<Action> actions = setAction(resp.getTransactionId(), servletRequest);
            resp.setActions(actions);
//            Utility.writeLog(resp.getTransactionId(),
//                    url,
//                    settingProperties.getApi().getPath().get(0).getMethod().name(),
//                    "charge",
//                    responseEntity.getHeaders(),
//                    rc,
//                    resp,
//                    settingProperties.getApi().getLogdir());
            if (resp.getPaymentType() == null)
                return Utility.setResponse(Integer.parseInt(resp.getStatusCode()),"", resp.getStatusMessage(), null);
            //QrTransaction transaction = new QrTransaction(resp);
            //qrTransactionRepo.save(transaction);
            return Utility.setResponse("", responseEntity.getBody());
        } catch (HttpClientErrorException e){
            return Utility.setResponse(e.getMessage(), null);
        } catch (HttpServerErrorException e){
            return Utility.setResponse(e.getMessage(), null);
        } catch(Exception e){
            System.out.println("Write File: " +e.getMessage());
            return Utility.setResponse(e.getMessage(), null);
        }
    }

    public ResponseEntity<Response> getAction(String orderId, String action) throws HttpClientErrorException{
        ResponseEntity<ResponseGopay> responseEntity = null;
        String baseUrl = settingProperties.getApi().getBaseurl().get(1);
        SettingProperties.Path path = settingProperties.getPathAction(action);
        String url = baseUrl + path.getLink().replace("order_id", orderId);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>(getHeader(true));
        try {
            responseEntity = restTemplate.exchange(url, path.getMethod(), entity, ResponseGopay.class);
            ResponseGopay resp = responseEntity.getBody();
//            Utility.writeLog(resp.getTransactionId(),
//                    url,
//                    settingProperties.getApi().getPath().get(0).getMethod().name(),
//                    action,
//                    responseEntity.getHeaders(),
//                    null,
//                    resp,
//                        settingProperties.getApi().getLogdir());
            if (resp.getPaymentType() == null)
                return Utility.setResponse(Integer.parseInt(resp.getStatusCode()),"", resp.getStatusMessage(), null);
            return Utility.setResponse("", resp);
        } catch (HttpClientErrorException e){
            return Utility.setResponse(e.getMessage(), null);
        } catch (HttpServerErrorException e){
            return Utility.setResponse(e.getMessage(), null);
        } catch(Exception e){
            System.out.println("Write File: " +e.getMessage());
            return Utility.setResponse(e.getMessage(), null);
        }
    }

    public ResponseEntity<byte[]> getQr(String orderId){
        String baseUrl = settingProperties.getApi().getBaseurl().get(1);
        SettingProperties.Path path = settingProperties.getPathAction("qr");
        String url = baseUrl + path.getLink().replace("payment_type", "gopay").replace("order_id", orderId);
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>(1);
        converters.add(new ByteArrayHttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        HttpEntity<byte[]> request = new HttpEntity<byte[]>(getHeader(false));

        ResponseEntity<byte[]> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, byte[].class);
            //Utility.writeFile(orderId,"qr-"+orderId+".png", response.getBody(), settingProperties.getApi().getLogdir());
        } catch (HttpClientErrorException e){
            throw e;
        } catch(HttpServerErrorException e){
            throw e;
        } catch(Exception e){
            System.out.println("Write File: " +e.getMessage());
        }
        return response;
    }

    private List<Action> setAction(String id, HttpServletRequest servletRequest){
        List<Action> actionList = new ArrayList<>();
        for (FirstPaymentProperties.Path path : fPProperties.getApi().getPath()) {
            Action act = new Action();
            act.setName(path.getName());
            act.setUrl(fPProperties.getApi().getBaseurl().get(0) + path.getLink() + id);
            act.setMethod(path.getMethod().name());
            actionList.add(act);
        }
        return actionList;
    }

    public void writeQr(String transctionId, QrCode qrCode) throws IOException, WriterException {
        String QR_CODE_IMAGE_PATH = "GOPAY-QR-"+transctionId+".png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(Utility.objectToString(qrCode, false), BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(settingProperties.getApi().getLogdir()+QR_CODE_IMAGE_PATH);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
