package com.conviva.ondemand.checker.functions;

import com.conviva.ondemand.checker.entities.TimeseriesResponse;
import com.conviva.ondemand.checker.entities.TopNResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public enum SerDe {
  ;

  public static final ObjectMapper mapper = create();
  public static final ObjectReader timeseriesReader = mapper.readerFor(TimeseriesResponse.class);
  public static final ObjectReader topNReader = mapper.readerFor(TopNResponse.class);

  private static final ObjectWriter prettyPrinter = mapper.writerWithDefaultPrettyPrinter();
  private static final ObjectWriter writer = mapper.writer();

  private static ObjectMapper create() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }

  public static <T> String prettyPrint(T t) throws JsonProcessingException {
    return prettyPrinter.writeValueAsString(t);
  }

  public static <T> String toJson(T t) throws JsonProcessingException {
    return writer.writeValueAsString(t);
  }


}
