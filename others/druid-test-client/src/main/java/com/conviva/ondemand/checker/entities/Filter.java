package com.conviva.ondemand.checker.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Filter.And.class, name = "and"),
    @JsonSubTypes.Type(value = Filter.Or.class, name = "or"),
    @JsonSubTypes.Type(value = Filter.Not.class, name = "not"),
    @JsonSubTypes.Type(value = Filter.Equal.class, name = "equal")
})
public abstract class Filter {
  private FilterType type;

  public FilterType getType() {
    return type;
  }

  public enum FilterType {
    and,
    or,
    not,
    equal
  }

  public static final class And extends Filter {
    private final FilterType type = FilterType.and;
    private final List<Filter> filters;

    public And(List<Filter> filters) {
      super();
      this.filters = filters;
    }

    public FilterType getType() { return type; }

    public List<Filter> getFilters() { return filters;}

    public static And of(Filter... fs) {
      return new And(Arrays.asList(fs));
    }
  }

  public static final class Or extends Filter {
    private FilterType type = FilterType.or;
    private List<Filter> filters;

    public Or(List<Filter> filters) {
      this.filters = filters;
    }

    @Override
    public FilterType getType() { return type;}

    public List<Filter> getFilters() { return filters;}

    public static Or of(Filter... fs) {
      return new Or(Arrays.asList(fs));
    }
  }

  public static final class Not extends Filter {
    private final FilterType type = FilterType.not;
    private final Filter filter;

    public Not(Filter filter) {
      super();
      this.filter = filter;
    }

    @Override
    public FilterType getType() { return type; }

    public Filter getFilter() { return filter; }
  }

  public static final class Equal extends Filter {
    private FilterType type = FilterType.equal;
    private final String dimension;
    private final String value;

    public Equal(String dimension, String value) {
      this.dimension = dimension;
      this.value = value;
    }

    @Override
    public FilterType getType() { return type; }

    public String getDimension() { return dimension; }

    public String getValue() { return value; }
  }

}
