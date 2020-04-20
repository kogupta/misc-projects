package com.conviva.ondemand.checker.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@JsonSerialize(using = Interval.Serializer.class)
public class Interval {
  private final LocalDateTime from;
  private final LocalDateTime to;

  public Interval(LocalDateTime from, LocalDateTime to) {
    this.from = from;
    this.to = to;
  }

  public LocalDateTime getFrom() { return from; }

  public LocalDateTime getTo() { return to; }

  public static final class Serializer extends StdSerializer<Interval> {
    public Serializer() {
      this(null);
    }

    protected Serializer(Class<Interval> t) {
      super(t);
    }

    @Override
    public void serialize(Interval value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      String start = value.from.format(DateTimeFormatter.ISO_DATE_TIME);
      String end = value.to.format(DateTimeFormatter.ISO_DATE_TIME);
      gen.writeString(start + "/" + end);
    }
  }

}
