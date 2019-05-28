package binaryJson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "time",
    "channel",
    "namespace",
    "page",
    "user",
    "comment",
    "cityName",
    "regionName",
    "regionIsoCode",
    "metroCode",
    "countryName",
    "countryIsoCode",
    "isAnonymous",
    "isNew",
    "isMinor",
    "isRobot",
    "isUnpatrolled",
    "added",
    "delta",
    "deleted"
})
public class WikiTicker {
  @JsonProperty("time")
  private String time;
  @JsonProperty("channel")
  private String channel;
  @JsonProperty("namespace")
  private String namespace;
  @JsonProperty("page")
  private String page;
  @JsonProperty("user")
  private String user;
  @JsonProperty("comment")
  private String comment;
  @JsonProperty("cityName")
  private String cityName;
  @JsonProperty("regionName")
  private String regionName;
  @JsonProperty("regionIsoCode")
  private String regionIsoCode;
  @JsonProperty("countryName")
  private String countryName;
  @JsonProperty("countryIsoCode")
  private String countryIsoCode;
  @JsonProperty("isAnonymous")
  private boolean isAnonymous;
  @JsonProperty("isNew")
  private boolean isNew;
  @JsonProperty("isMinor")
  private boolean isMinor;
  @JsonProperty("isRobot")
  private boolean isRobot;
  @JsonProperty("isUnpatrolled")
  private boolean isUnpatrolled;
  @JsonProperty("added")
  private int added;
  @JsonProperty("delta")
  private int delta;
  @JsonProperty("deleted")
  private int deleted;
  @JsonProperty("metroCode")
  private String metroCode;

  @JsonProperty("time")
  public String getTime() { return time;}

  @JsonProperty("time")
  public void setTime(String time) { this.time = time;}

  @JsonProperty("channel")
  public String getChannel() { return channel;}

  @JsonProperty("channel")
  public void setChannel(String channel) { this.channel = channel;}

  @JsonProperty("namespace")
  public String getNamespace() { return namespace;}

  @JsonProperty("namespace")
  public void setNamespace(String namespace) { this.namespace = namespace;}

  @JsonProperty("page")
  public String getPage() { return page;}

  @JsonProperty("page")
  public void setPage(String page) { this.page = page;}

  @JsonProperty("user")
  public String getUser() { return user;}

  @JsonProperty("user")
  public void setUser(String user) { this.user = user;}

  @JsonProperty("comment")
  public String getComment() { return comment;}

  @JsonProperty("comment")
  public void setComment(String comment) { this.comment = comment;}

  @JsonProperty("cityName")
  public String getCityName() { return cityName;}

  @JsonProperty("cityName")
  public void setCityName(String cityName) { this.cityName = cityName;}

  @JsonProperty("regionName")
  public String getRegionName() { return regionName;}

  @JsonProperty("regionName")
  public void setRegionName(String regionName) { this.regionName = regionName;}

  @JsonProperty("regionIsoCode")
  public String getRegionIsoCode() { return regionIsoCode;}

  @JsonProperty("regionIsoCode")
  public void setRegionIsoCode(String regionIsoCode) { this.regionIsoCode = regionIsoCode;}

  @JsonProperty("countryName")
  public String getCountryName() { return countryName;}

  @JsonProperty("countryName")
  public void setCountryName(String countryName) { this.countryName = countryName;}

  @JsonProperty("countryIsoCode")
  public String getCountryIsoCode() { return countryIsoCode;}

  @JsonProperty("countryIsoCode")
  public void setCountryIsoCode(String countryIsoCode) { this.countryIsoCode = countryIsoCode;}

  @JsonProperty("isAnonymous")
  public boolean isIsAnonymous() { return isAnonymous;}

  @JsonProperty("isAnonymous")
  public void setIsAnonymous(boolean isAnonymous) { this.isAnonymous = isAnonymous;}

  @JsonProperty("isNew")
  public boolean isIsNew() { return isNew;}

  @JsonProperty("isNew")
  public void setIsNew(boolean isNew) { this.isNew = isNew;}

  @JsonProperty("isMinor")
  public boolean isIsMinor() { return isMinor;}

  @JsonProperty("isMinor")
  public void setIsMinor(boolean isMinor) { this.isMinor = isMinor;}

  @JsonProperty("isRobot")
  public boolean isIsRobot() { return isRobot;}

  @JsonProperty("isRobot")
  public void setIsRobot(boolean isRobot) { this.isRobot = isRobot;}

  @JsonProperty("isUnpatrolled")
  public boolean isIsUnpatrolled() { return isUnpatrolled;}

  @JsonProperty("isUnpatrolled")
  public void setIsUnpatrolled(boolean isUnpatrolled) { this.isUnpatrolled = isUnpatrolled;}

  @JsonProperty("added")
  public int getAdded() { return added;}

  @JsonProperty("added")
  public void setAdded(int added) { this.added = added;}

  @JsonProperty("delta")
  public int getDelta() { return delta;}

  @JsonProperty("delta")
  public void setDelta(int delta) { this.delta = delta;}

  @JsonProperty("deleted")
  public int getDeleted() { return deleted;}

  @JsonProperty("deleted")
  public void setDeleted(int deleted) { this.deleted = deleted;}


  @JsonProperty("metroCode")
  public String getMetroCode() { return metroCode; }

  @JsonProperty("metroCode")
  public void setMetroCode(String metroCode) { this.metroCode = metroCode; }
}