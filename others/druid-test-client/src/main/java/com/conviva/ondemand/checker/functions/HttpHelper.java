package com.conviva.ondemand.checker.functions;

import com.conviva.ondemand.checker.entities.TimeseriesQuery;
import com.conviva.ondemand.checker.entities.TimeseriesResponse;
import com.conviva.ondemand.checker.entities.TopNResponse;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;

import static com.conviva.ondemand.checker.functions.SerDe.timeseriesReader;
import static com.conviva.ondemand.checker.functions.SerDe.topNReader;

public enum HttpHelper {
  ;

  public static final OkHttpClient client = new OkHttpClient();
  public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  public static TimeseriesResponse timeseries(TimeseriesQuery query, String url) throws IOException {
    String json = SerDe.toJson(query);
    Request request = new Request.Builder()
        .url(url)
        .post(RequestBody.create(json, JSON))
        .build();

    try (Response response = client.newCall(request).execute()) {
      InputStream is = response.body().byteStream();
      TimeseriesResponse o = timeseriesReader.readValue(is);
      return o;
    }
  }

  public static TopNResponse topn(String query, String url) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .post(RequestBody.create(query, JSON))
        .build();

    try (Response response = client.newCall(request).execute()) {
      InputStream is = response.body().byteStream();
      TopNResponse o = topNReader.readValue(is);
      return o;
    }
  }
}
