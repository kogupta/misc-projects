package binaryJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"x", "y"})
public class Point {
  @JsonProperty
  int x;
  @JsonProperty int y;
}
