package com.midtrans.api.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.midtrans.api.payload.global.Response;

import com.midtrans.api.payload.global.ResponseQr;
import com.midtrans.api.payload.gopay.QrCode;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Utility {
    public static String objectToString(Object o, boolean isFormating) {
        Gson g = new Gson();
        String json = g.toJson(o);
        if (isFormating)
            return formatingJson(json);
        return json;
    }

    public static Object jsonToObject(String json) {
        Gson g = new Gson();
        Object o = g.fromJson(json, Object.class);
        return o;
    }

    public static String formatingJson(String plainJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = JsonParser.parseString(plainJson).getAsJsonObject();
        String json = gson.toJson(jsonObject);
        return json;
    }

    public static ResponseEntity setResponse(int code, String status, String message, Object data) {
        Response resp = new Response();
        resp.setCode(code);
        resp.setStatus(status);
        resp.setMessage(message);
        resp.setData(data);
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    public static ResponseEntity setResponse(String message, Object data) {
        Response resp = new Response();
        resp.setCode(HttpStatus.OK.value());
        resp.setStatus(HttpStatus.OK.getReasonPhrase());
        resp.setMessage(message);
        resp.setData(data);
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    public static Map toMap(Throwable map) {
        Map<String, String> newMap = new HashMap<>();
        if (map != null) {
            String[] aArr = (map.toString()).split(",");
            for (String strA : aArr) {
                String[] bArr = strA.split("=");
                for (String strB : bArr) {
                    newMap.put(bArr[0], strB.trim());
                }
            }
        } else {
            newMap.put("error", "");
            newMap.put("error_description", "");
        }
        return newMap;
    }

    public static String writeFile(String id, String filename, byte[] content, String path) throws Exception {
        try {
            String resourceFile = path + "\\" + id + "\\" + filename;
            File file = new File(resourceFile);
            if (!file.exists()) {
                if (file.getParentFile().mkdirs()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
                file.createNewFile();
            }
            InputStream in = new ByteArrayInputStream(content);
            BufferedImage bImage = ImageIO.read(in);
            ImageIO.write(bImage, "png", new File(resourceFile));
            return "done";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static String writeFile(String id, String filename, String content, String path) throws Exception {
        try {
            String resourceFile = path + "\\" + id + "\\" + filename;
            File file = new File(path + "\\" + id + "\\" + filename);
            if (!file.exists()) {
                if (file.getParentFile().mkdirs()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fw);
            String[] contents = content.split("and");
            for (String word : contents) {
                bw.write(word);
                bw.newLine();
            }
            bw.close();
            return "done";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static String readFile(String id, String filename, String path) throws Exception {
        File file = new File(path + "\\" + id + "\\" + filename);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getPath());
            System.out.println("Total file size to read (in bytes) : " + fis.available());
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                System.out.print((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return ex.getMessage();
            }
        }
        return "done";
    }

    public static void writeLog(String id, String url, String method, String action, HttpHeaders header, Object req, Object resp, String path) throws Exception {
        String param = "";
        if (req == null)
            param = id;
        else
            param = objectToString(req, true);
        String fileName = action + "-" + id + ".txt";
        JSONObject objHeader = new JSONObject();
        for (String key : header.keySet()) {
            objHeader.put(key, header.get(key).toString());
        }
        String template = templateLog(objHeader.toString(), url, method, param, objectToString(resp, true));
        writeFile(id, fileName, template, path);
    }

    public static String templateLog(String header, String url, String method, String request, String response) {
        StringBuffer sb = new StringBuffer();
        sb.append("Header: ").append(header)
                .append("and").append("Url: ").append(url)
                .append("and").append("Method: ").append(method)
                .append("and").append("Request: ").append("and").append(request)
                .append("and").append("Response: ").append("and").append(response);
        return sb.toString();
    }

    public static QrCode getQrString(InputStream inputStream)
            throws IOException, NotFoundException {
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(inputStream))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
        Gson g = new Gson();
        QrCode qrCode = g.fromJson(qrCodeResult.getText(), QrCode.class);
        System.out.println(qrCodeResult.getText());
        return qrCode;
    }
}
