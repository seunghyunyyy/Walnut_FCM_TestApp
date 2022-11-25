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

            conn.setRequestMethod("PUT"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true); // 서버에 전달할 값이 있다면 true
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            // 서버에 데이터 전달
            JSONObject info = new JSONObject();
            info.put("email", email);
            info.put("token", token);

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                bw.write(info.toString()); // 버퍼에 담기
                bw.flush(); // 버퍼에 담긴 데이터 전달
            }

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString()); // json으로 변경 (역직렬화)
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
            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true); // 서버에 전달할 값이 있다면 true
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                bw.write(json.toString()); // 버퍼에 담기
                bw.flush(); // 버퍼에 담긴 데이터 전달
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

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setRequestProperty("auth", "myAuth"); // header의 auth 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString()); // json으로 변경 (역직렬화)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMessagesArray(String Url, String token) throws IOException {
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET"); // http 메서드
        conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보

        conn.setRequestProperty("token", token);

        conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

        // 서버로부터 데이터 읽어오기
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line.trim());
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }*/
}
