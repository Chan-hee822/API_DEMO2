package com.example.api_demo2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetWifiData {
    public static void getWifiData() throws IOException {
        int start = 1;
        int end = 999;
        while (true) {
            String url = String.format
                    ("http://openapi.seoul.go.kr:8088/584c7a434968616e34314a62796243/json/TbPublicWifiInfo/%d/%d/", start, (start + end));
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().
                        url(url)
                        .build();
                Response response = client.newCall(request).execute();

                String responseBody = response.body().string();

                if (response.isSuccessful()) {
                    // 응답 받아서 처리
                    ResponseBody body = response.body();
                    if (body != null) {
                        //System.out.println("Response:" + responseBody);
                    }
                } else
                    System.err.println("Error Occurred");

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject root = parser.parse(responseBody).getAsJsonObject();
                JsonObject tbPublicWifiInfo = root.getAsJsonObject("TbPublicWifiInfo");
                JsonArray row = tbPublicWifiInfo.getAsJsonArray("row");

                List<Wifi> wifis = new ArrayList<>();
                for (JsonElement element : row) {
                    Wifi station = gson.fromJson(element, Wifi.class);
                    wifis.add(station);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            start += (end + 1);
            if(start == 24001){
                break;
            }
        }
    }
}
