package com.test.fcm_test;

import static java.nio.charset.StandardCharsets.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class HttpUtility {
    public static String getRest(String Url, String Type, String Data) {
        String totalUrl = "";
        if(Data != null && Data.length() > 0 && !Data.contains("null")) {
            totalUrl = Url.trim().toString() + "?" + Type + "=" + Data.trim().toString();
        } else {
            totalUrl = Url.trim().toString();
        }
        URL url = null;

        String responseData = "";
        BufferedReader br = null;
        StringBuffer sb = null;

        String returnData = "";

        try {
            url = new URL(totalUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), UTF_8));
            sb = new StringBuffer();
            while ((responseData = br.readLine()) != null) {
                sb.append(responseData);
            }
            returnData = sb.toString();

            conn.disconnect();

            return  returnData;

        } catch (IOException e) {
            e.printStackTrace();
            return  returnData;
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void putToken(String Url, String email, String token) {
        try {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT"); // http ?????????
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type ??????
            conn.setDoInput(true); // ????????? ????????? ?????? ????????? true
            conn.setDoOutput(true); // ??????????????? ?????? ?????? ????????? true

            // ????????? ????????? ??????
            JSONObject info = new JSONObject();
            info.put("email", email);
            info.put("token", token);

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                bw.write(info.toString()); // ????????? ??????
                bw.flush(); // ????????? ?????? ????????? ??????
            }

            // ??????????????? ????????? ????????????
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) { // ?????? ??? ?????? ??? ?????? ??????
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString()); // json?????? ?????? (????????????)
            System.out.println("email: " + obj.getString("email") + "\n" + "token: " + obj.getString("token"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * http patch
     * @param Url request url
     * @param json json
     */
    public static void patch(String Url, JSONObject json) {
        try {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            conn.setRequestMethod("POST"); // http ?????????
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type ??????
            conn.setDoInput(true); // ????????? ????????? ?????? ????????? true
            conn.setDoOutput(true); // ??????????????? ?????? ?????? ????????? true

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                bw.write(json.toString()); // ????????? ??????
                bw.flush(); // ????????? ?????? ????????? ??????
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public static void getJson(String Url, JSONObject jsonObject) {
        try {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET"); // http ?????????
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type ??????
            conn.setRequestProperty("auth", "myAuth"); // header??? auth ??????
            conn.setDoOutput(true); // ??????????????? ?????? ?????? ????????? true

            // ??????????????? ????????? ????????????
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // ?????? ??? ?????? ??? ?????? ??????
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString()); // json?????? ?????? (????????????)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMessagesArray(String Url, String token) throws IOException {
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET"); // http ?????????
        conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type ??????

        conn.setRequestProperty("token", token);

        conn.setDoOutput(true); // ??????????????? ?????? ?????? ????????? true

        // ??????????????? ????????? ????????????
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // ?????? ??? ?????? ??? ?????? ??????
                sb.append(line.trim());
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }*/
}
