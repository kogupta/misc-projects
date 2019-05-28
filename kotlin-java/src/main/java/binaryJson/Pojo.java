package binaryJson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "version",
    "customerId",
    "clientId",
    "sessionId",
    "isAudienceOnly",
    "assetName",
    "streamUrl",
    "ipHash",
    "geo.continent",
    "geo.country",
    "geo.state",
    "geo.city",
    "geo.dma",
    "geo.asn",
    "geo.isp",
    "geo.postalCode",
    "life.firstReceivedTimeMs",
    "life.latestReceivedTimeMs",
    "life.sessionTimeMs",
    "life.joinTimeMs",
    "life.playingTimeMs",
    "life.bufferingTimeMs",
    "life.rebufferingRatioPct",
    "life.networkRebufferingRatioPct",
    "life.averageBitrateKbps",
    "life.seekJoinTimeMs",
    "life.contentWatchedTimeMs",
    "life.contentWatchedPct",
    "life.averageFrameRate",
    "life.renderingQuality",
    "life.resourceIds",
    "life.cdns",
    "life.fatalErrorResourceIds",
    "life.fatalErrorCdns",
    "life.latestErrorResourceId",
    "life.latestErrorCdn",
    "life.joinResourceIds",
    "life.joinCdns",
    "life.lastJoinCdn",
    "life.lastJoinResourceId",
    "interval.startTimeMs",
    "switch.resourceId",
    "switch.cdn",
    "switch.justJoined",
    "switch.hasJoined",
    "switch.justJoinedAndLifeJoinTimeMsIsAccurate",
    "switch.isEndedPlay",
    "switch.isEndedPlayAndLifeAverageBitrateKbpsGT0",
    "switch.isVideoStartFailure",
    "switch.videoStartFailureErrors",
    "switch.isExitBeforeVideoStart",
    "switch.isVideoPlaybackFailure",
    "switch.isVideoStartSave",
    "switch.videoPlaybackFailureErrors",
    "switch.isAttempt",
    "switch.playingTimeMs",
    "switch.rebufferingTimeMs",
    "switch.networkRebufferingTimeMs",
    "switch.bitrateBytes",
    "switch.bitrateTimeMs",
    "switch.framesLoaded",
    "switch.framesPlayingTimeMs",
    "switch.seekJoinTimeMs",
    "switch.seekJoinCount",
    "switch.pcpIntervals5Sec",
    "switch.pcpIntervals",
    "tags.m3.dv.n",
    "tags.c3.pt.os",
    "tags.m3.dv.brv",
    "tags.c3.cmp.0._id",
    "tags.androidTV",
    "tags.availableRAM",
    "tags.c3.device.model",
    "tags.wifiSSID",
    "tags.c3.viewer.id",
    "tags.c3.cws.clv",
    "tags.screenSize",
    "tags.channel",
    "tags.totalRAM",
    "tags.c3.cfg.alt.name",
    "tags.playerVersion",
    "tags.c3.player.name",
    "tags.m3.dv.br",
    "tags.m3.dv.os",
    "tags.contentId",
    "tags.c3.adaptor.type",
    "tags.m3.dv.osf",
    "tags.c3.device.type",
    "tags.connectionType",
    "tags.applicationVersion",
    "tags.screenRatio",
    "tags.c3.cmp.0._type",
    "tags.c3.client.osf",
    "tags.c3.pt.ver",
    "tags.c3.device.cver.bld",
    "tags.c3.device.usesSdk",
    "tags.c3.pt",
    "tags.playerVendor",
    "tags.c3.protocol.level",
    "tags.c3.client.br",
    "tags.m3.dv.cat",
    "tags.category",
    "tags.c3.device.manufacturer",
    "tags.m3.dv.hwt",
    "tags.screenOrientation",
    "tags.c3.protocol.type",
    "tags.m3.dv.mrk",
    "tags.m3.dv.mnf",
    "tags.contentType",
    "tags.c3.client.osv",
    "tags.c3.device.ua",
    "tags.c3.client.osName",
    "tags.streamProtocol",
    "tags.versionCode",
    "tags.operator",
    "tags.c3.cluster.name",
    "tags.c3.cmp.0._ver",
    "tags.c3.pt.os.ver",
    "tags.c3.cws.sf",
    "tags.c3.pt.br.ver",
    "tags.c3.device.conn",
    "tags.screen",
    "tags.c3.cmp.0._cfg_ver",
    "tags.c3.device.brand",
    "tags.c3.pt.br",
    "tags.c3.video.isLive",
    "tags.m3.dv.osv",
    "tags.c3.device.cver",
    "tags.c3.sh"
})
public class Pojo {

  @JsonProperty("version")
  private String version;
  @JsonProperty("customerId")
  private long customerId;
  @JsonProperty("clientId")
  private String clientId;
  @JsonProperty("sessionId")
  private long sessionId;
  @JsonProperty("isAudienceOnly")
  private boolean isAudienceOnly;
  @JsonProperty("assetName")
  private String assetName;
  @JsonProperty("streamUrl")
  private String streamUrl;
  @JsonProperty("ipHash")
  private String ipHash;
  @JsonProperty("geo.continent")
  private long geoContinent;
  @JsonProperty("geo.country")
  private long geoCountry;
  @JsonProperty("geo.state")
  private long geoState;
  @JsonProperty("geo.city")
  private long geoCity;
  @JsonProperty("geo.dma")
  private long geoDma;
  @JsonProperty("geo.asn")
  private long geoAsn;
  @JsonProperty("geo.isp")
  private long geoIsp;
  @JsonProperty("geo.postalCode")
  private String geoPostalCode;
  @JsonProperty("life.firstReceivedTimeMs")
  private long lifeFirstReceivedTimeMs;
  @JsonProperty("life.latestReceivedTimeMs")
  private long lifeLatestReceivedTimeMs;
  @JsonProperty("life.sessionTimeMs")
  private long lifeSessionTimeMs;
  @JsonProperty("life.joinTimeMs")
  private long lifeJoinTimeMs;
  @JsonProperty("life.playingTimeMs")
  private long lifePlayingTimeMs;
  @JsonProperty("life.bufferingTimeMs")
  private long lifeBufferingTimeMs;
  @JsonProperty("life.rebufferingRatioPct")
  private double lifeRebufferingRatioPct;
  @JsonProperty("life.networkRebufferingRatioPct")
  private double lifeNetworkRebufferingRatioPct;
  @JsonProperty("life.averageBitrateKbps")
  private long lifeAverageBitrateKbps;
  @JsonProperty("life.seekJoinTimeMs")
  private long lifeSeekJoinTimeMs;
  @JsonProperty("life.contentWatchedTimeMs")
  private long lifeContentWatchedTimeMs;
  @JsonProperty("life.contentWatchedPct")
  private double lifeContentWatchedPct;
  @JsonProperty("life.averageFrameRate")
  private long lifeAverageFrameRate;
  @JsonProperty("life.renderingQuality")
  private long lifeRenderingQuality;
  @JsonProperty("life.resourceIds")
  private List<Long> lifeResourceIds = null;
  @JsonProperty("life.cdns")
  private List<String> lifeCdns = null;
  @JsonProperty("life.fatalErrorResourceIds")
  private List<Object> lifeFatalErrorResourceIds = null;
  @JsonProperty("life.fatalErrorCdns")
  private List<Object> lifeFatalErrorCdns = null;
  @JsonProperty("life.latestErrorResourceId")
  private Object lifeLatestErrorResourceId;
  @JsonProperty("life.latestErrorCdn")
  private Object lifeLatestErrorCdn;
  @JsonProperty("life.joinResourceIds")
  private List<Long> lifeJoinResourceIds = null;
  @JsonProperty("life.joinCdns")
  private List<String> lifeJoinCdns = null;
  @JsonProperty("life.lastJoinCdn")
  private String lifeLastJoinCdn;
  @JsonProperty("life.lastJoinResourceId")
  private long lifeLastJoinResourceId;
  @JsonProperty("interval.startTimeMs")
  private long intervalStartTimeMs;
  @JsonProperty("switch.resourceId")
  private long switchResourceId;
  @JsonProperty("switch.cdn")
  private String switchCdn;
  @JsonProperty("switch.justJoined")
  private boolean switchJustJoined;
  @JsonProperty("switch.hasJoined")
  private boolean switchHasJoined;
  @JsonProperty("switch.justJoinedAndLifeJoinTimeMsIsAccurate")
  private boolean switchJustJoinedAndLifeJoinTimeMsIsAccurate;
  @JsonProperty("switch.isEndedPlay")
  private boolean switchIsEndedPlay;
  @JsonProperty("switch.isEndedPlayAndLifeAverageBitrateKbpsGT0")
  private boolean switchIsEndedPlayAndLifeAverageBitrateKbpsGT0;
  @JsonProperty("switch.isVideoStartFailure")
  private boolean switchIsVideoStartFailure;
  @JsonProperty("switch.videoStartFailureErrors")
  private List<Object> switchVideoStartFailureErrors = null;
  @JsonProperty("switch.isExitBeforeVideoStart")
  private boolean switchIsExitBeforeVideoStart;
  @JsonProperty("switch.isVideoPlaybackFailure")
  private boolean switchIsVideoPlaybackFailure;
  @JsonProperty("switch.isVideoStartSave")
  private boolean switchIsVideoStartSave;
  @JsonProperty("switch.videoPlaybackFailureErrors")
  private List<Object> switchVideoPlaybackFailureErrors = null;
  @JsonProperty("switch.isAttempt")
  private boolean switchIsAttempt;
  @JsonProperty("switch.playingTimeMs")
  private long switchPlayingTimeMs;
  @JsonProperty("switch.rebufferingTimeMs")
  private long switchRebufferingTimeMs;
  @JsonProperty("switch.networkRebufferingTimeMs")
  private long switchNetworkRebufferingTimeMs;
  @JsonProperty("switch.bitrateBytes")
  private long switchBitrateBytes;
  @JsonProperty("switch.bitrateTimeMs")
  private long switchBitrateTimeMs;
  @JsonProperty("switch.framesLoaded")
  private long switchFramesLoaded;
  @JsonProperty("switch.framesPlayingTimeMs")
  private long switchFramesPlayingTimeMs;
  @JsonProperty("switch.seekJoinTimeMs")
  private long switchSeekJoinTimeMs;
  @JsonProperty("switch.seekJoinCount")
  private long switchSeekJoinCount;
  @JsonProperty("switch.pcpIntervals5Sec")
  private long switchPcpIntervals5Sec;
  @JsonProperty("switch.pcpIntervals")
  private long switchPcpIntervals;
  @JsonProperty("tags.m3.dv.n")
  private String tagsM3DvN;
  @JsonProperty("tags.c3.pt.os")
  private String tagsC3PtOs;
  @JsonProperty("tags.m3.dv.brv")
  private String tagsM3DvBrv;
  @JsonProperty("tags.c3.cmp.0._id")
  private String tagsC3Cmp0Id;
  @JsonProperty("tags.androidTV")
  private String tagsAndroidTV;
  @JsonProperty("tags.availableRAM")
  private String tagsAvailableRAM;
  @JsonProperty("tags.c3.device.model")
  private String tagsC3DeviceModel;
  @JsonProperty("tags.wifiSSID")
  private String tagsWifiSSID;
  @JsonProperty("tags.c3.viewer.id")
  private String tagsC3ViewerId;
  @JsonProperty("tags.c3.cws.clv")
  private String tagsC3CwsClv;
  @JsonProperty("tags.screenSize")
  private String tagsScreenSize;
  @JsonProperty("tags.channel")
  private String tagsChannel;
  @JsonProperty("tags.totalRAM")
  private String tagsTotalRAM;
  @JsonProperty("tags.c3.cfg.alt.name")
  private String tagsC3CfgAltName;
  @JsonProperty("tags.playerVersion")
  private String tagsPlayerVersion;
  @JsonProperty("tags.c3.player.name")
  private String tagsC3PlayerName;
  @JsonProperty("tags.m3.dv.br")
  private String tagsM3DvBr;
  @JsonProperty("tags.m3.dv.os")
  private String tagsM3DvOs;
  @JsonProperty("tags.contentId")
  private String tagsContentId;
  @JsonProperty("tags.c3.adaptor.type")
  private String tagsC3AdaptorType;
  @JsonProperty("tags.m3.dv.osf")
  private String tagsM3DvOsf;
  @JsonProperty("tags.c3.device.type")
  private String tagsC3DeviceType;
  @JsonProperty("tags.connectionType")
  private String tagsConnectionType;
  @JsonProperty("tags.applicationVersion")
  private String tagsApplicationVersion;
  @JsonProperty("tags.screenRatio")
  private String tagsScreenRatio;
  @JsonProperty("tags.c3.cmp.0._type")
  private String tagsC3Cmp0Type;
  @JsonProperty("tags.c3.client.osf")
  private String tagsC3ClientOsf;
  @JsonProperty("tags.c3.pt.ver")
  private String tagsC3PtVer;
  @JsonProperty("tags.c3.device.cver.bld")
  private String tagsC3DeviceCverBld;
  @JsonProperty("tags.c3.device.usesSdk")
  private String tagsC3DeviceUsesSdk;
  @JsonProperty("tags.c3.pt")
  private String tagsC3Pt;
  @JsonProperty("tags.playerVendor")
  private String tagsPlayerVendor;
  @JsonProperty("tags.c3.protocol.level")
  private String tagsC3ProtocolLevel;
  @JsonProperty("tags.c3.client.br")
  private String tagsC3ClientBr;
  @JsonProperty("tags.m3.dv.cat")
  private String tagsM3DvCat;
  @JsonProperty("tags.category")
  private String tagsCategory;
  @JsonProperty("tags.c3.device.manufacturer")
  private String tagsC3DeviceManufacturer;
  @JsonProperty("tags.m3.dv.hwt")
  private String tagsM3DvHwt;
  @JsonProperty("tags.screenOrientation")
  private String tagsScreenOrientation;
  @JsonProperty("tags.c3.protocol.type")
  private String tagsC3ProtocolType;
  @JsonProperty("tags.m3.dv.mrk")
  private String tagsM3DvMrk;
  @JsonProperty("tags.m3.dv.mnf")
  private String tagsM3DvMnf;
  @JsonProperty("tags.contentType")
  private String tagsContentType;
  @JsonProperty("tags.c3.client.osv")
  private String tagsC3ClientOsv;
  @JsonProperty("tags.c3.device.ua")
  private String tagsC3DeviceUa;
  @JsonProperty("tags.c3.client.osName")
  private String tagsC3ClientOsName;
  @JsonProperty("tags.streamProtocol")
  private String tagsStreamProtocol;
  @JsonProperty("tags.versionCode")
  private String tagsVersionCode;
  @JsonProperty("tags.operator")
  private String tagsOperator;
  @JsonProperty("tags.c3.cluster.name")
  private String tagsC3ClusterName;
  @JsonProperty("tags.c3.cmp.0._ver")
  private String tagsC3Cmp0Ver;
  @JsonProperty("tags.c3.pt.os.ver")
  private String tagsC3PtOsVer;
  @JsonProperty("tags.c3.cws.sf")
  private String tagsC3CwsSf;
  @JsonProperty("tags.c3.pt.br.ver")
  private String tagsC3PtBrVer;
  @JsonProperty("tags.c3.device.conn")
  private String tagsC3DeviceConn;
  @JsonProperty("tags.screen")
  private String tagsScreen;
  @JsonProperty("tags.c3.cmp.0._cfg_ver")
  private String tagsC3Cmp0CfgVer;
  @JsonProperty("tags.c3.device.brand")
  private String tagsC3DeviceBrand;
  @JsonProperty("tags.c3.pt.br")
  private String tagsC3PtBr;
  @JsonProperty("tags.c3.video.isLive")
  private String tagsC3VideoIsLive;
  @JsonProperty("tags.m3.dv.osv")
  private String tagsM3DvOsv;
  @JsonProperty("tags.c3.device.cver")
  private String tagsC3DeviceCver;
  @JsonProperty("tags.c3.sh")
  private String tagsC3Sh;
//  @JsonIgnore
//  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("version")
  public String getVersion() {
    return version;
  }

  @JsonProperty("version")
  public void setVersion(String version) {
    this.version = version;
  }

  @JsonProperty("customerId")
  public long getCustomerId() {
    return customerId;
  }

  @JsonProperty("customerId")
  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  @JsonProperty("clientId")
  public String getClientId() {
    return clientId;
  }

  @JsonProperty("clientId")
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @JsonProperty("sessionId")
  public long getSessionId() {
    return sessionId;
  }

  @JsonProperty("sessionId")
  public void setSessionId(long sessionId) {
    this.sessionId = sessionId;
  }

  @JsonProperty("isAudienceOnly")
  public boolean isIsAudienceOnly() {
    return isAudienceOnly;
  }

  @JsonProperty("isAudienceOnly")
  public void setIsAudienceOnly(boolean isAudienceOnly) {
    this.isAudienceOnly = isAudienceOnly;
  }

  @JsonProperty("assetName")
  public String getAssetName() {
    return assetName;
  }

  @JsonProperty("assetName")
  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  @JsonProperty("streamUrl")
  public String getStreamUrl() {
    return streamUrl;
  }

  @JsonProperty("streamUrl")
  public void setStreamUrl(String streamUrl) {
    this.streamUrl = streamUrl;
  }

  @JsonProperty("ipHash")
  public String getIpHash() {
    return ipHash;
  }

  @JsonProperty("ipHash")
  public void setIpHash(String ipHash) {
    this.ipHash = ipHash;
  }

  @JsonProperty("geo.continent")
  public long getGeoContinent() {
    return geoContinent;
  }

  @JsonProperty("geo.continent")
  public void setGeoContinent(long geoContinent) {
    this.geoContinent = geoContinent;
  }

  @JsonProperty("geo.country")
  public long getGeoCountry() {
    return geoCountry;
  }

  @JsonProperty("geo.country")
  public void setGeoCountry(long geoCountry) {
    this.geoCountry = geoCountry;
  }

  @JsonProperty("geo.state")
  public long getGeoState() {
    return geoState;
  }

  @JsonProperty("geo.state")
  public void setGeoState(long geoState) {
    this.geoState = geoState;
  }

  @JsonProperty("geo.city")
  public long getGeoCity() {
    return geoCity;
  }

  @JsonProperty("geo.city")
  public void setGeoCity(long geoCity) {
    this.geoCity = geoCity;
  }

  @JsonProperty("geo.dma")
  public long getGeoDma() {
    return geoDma;
  }

  @JsonProperty("geo.dma")
  public void setGeoDma(long geoDma) {
    this.geoDma = geoDma;
  }

  @JsonProperty("geo.asn")
  public long getGeoAsn() {
    return geoAsn;
  }

  @JsonProperty("geo.asn")
  public void setGeoAsn(long geoAsn) {
    this.geoAsn = geoAsn;
  }

  @JsonProperty("geo.isp")
  public long getGeoIsp() {
    return geoIsp;
  }

  @JsonProperty("geo.isp")
  public void setGeoIsp(long geoIsp) {
    this.geoIsp = geoIsp;
  }

  @JsonProperty("geo.postalCode")
  public String getGeoPostalCode() {
    return geoPostalCode;
  }

  @JsonProperty("geo.postalCode")
  public void setGeoPostalCode(String geoPostalCode) {
    this.geoPostalCode = geoPostalCode;
  }

  @JsonProperty("life.firstReceivedTimeMs")
  public long getLifeFirstReceivedTimeMs() {
    return lifeFirstReceivedTimeMs;
  }

  @JsonProperty("life.firstReceivedTimeMs")
  public void setLifeFirstReceivedTimeMs(long lifeFirstReceivedTimeMs) {
    this.lifeFirstReceivedTimeMs = lifeFirstReceivedTimeMs;
  }

  @JsonProperty("life.latestReceivedTimeMs")
  public long getLifeLatestReceivedTimeMs() {
    return lifeLatestReceivedTimeMs;
  }

  @JsonProperty("life.latestReceivedTimeMs")
  public void setLifeLatestReceivedTimeMs(long lifeLatestReceivedTimeMs) {
    this.lifeLatestReceivedTimeMs = lifeLatestReceivedTimeMs;
  }

  @JsonProperty("life.sessionTimeMs")
  public long getLifeSessionTimeMs() {
    return lifeSessionTimeMs;
  }

  @JsonProperty("life.sessionTimeMs")
  public void setLifeSessionTimeMs(long lifeSessionTimeMs) {
    this.lifeSessionTimeMs = lifeSessionTimeMs;
  }

  @JsonProperty("life.joinTimeMs")
  public long getLifeJoinTimeMs() {
    return lifeJoinTimeMs;
  }

  @JsonProperty("life.joinTimeMs")
  public void setLifeJoinTimeMs(long lifeJoinTimeMs) {
    this.lifeJoinTimeMs = lifeJoinTimeMs;
  }

  @JsonProperty("life.playingTimeMs")
  public long getLifePlayingTimeMs() {
    return lifePlayingTimeMs;
  }

  @JsonProperty("life.playingTimeMs")
  public void setLifePlayingTimeMs(long lifePlayingTimeMs) {
    this.lifePlayingTimeMs = lifePlayingTimeMs;
  }

  @JsonProperty("life.bufferingTimeMs")
  public long getLifeBufferingTimeMs() {
    return lifeBufferingTimeMs;
  }

  @JsonProperty("life.bufferingTimeMs")
  public void setLifeBufferingTimeMs(long lifeBufferingTimeMs) {
    this.lifeBufferingTimeMs = lifeBufferingTimeMs;
  }

  @JsonProperty("life.rebufferingRatioPct")
  public double getLifeRebufferingRatioPct() {
    return lifeRebufferingRatioPct;
  }

  @JsonProperty("life.rebufferingRatioPct")
  public void setLifeRebufferingRatioPct(double lifeRebufferingRatioPct) {
    this.lifeRebufferingRatioPct = lifeRebufferingRatioPct;
  }

  @JsonProperty("life.networkRebufferingRatioPct")
  public double getLifeNetworkRebufferingRatioPct() {
    return lifeNetworkRebufferingRatioPct;
  }

  @JsonProperty("life.networkRebufferingRatioPct")
  public void setLifeNetworkRebufferingRatioPct(double lifeNetworkRebufferingRatioPct) {
    this.lifeNetworkRebufferingRatioPct = lifeNetworkRebufferingRatioPct;
  }

  @JsonProperty("life.averageBitrateKbps")
  public long getLifeAverageBitrateKbps() {
    return lifeAverageBitrateKbps;
  }

  @JsonProperty("life.averageBitrateKbps")
  public void setLifeAverageBitrateKbps(long lifeAverageBitrateKbps) {
    this.lifeAverageBitrateKbps = lifeAverageBitrateKbps;
  }

  @JsonProperty("life.seekJoinTimeMs")
  public long getLifeSeekJoinTimeMs() {
    return lifeSeekJoinTimeMs;
  }

  @JsonProperty("life.seekJoinTimeMs")
  public void setLifeSeekJoinTimeMs(long lifeSeekJoinTimeMs) {
    this.lifeSeekJoinTimeMs = lifeSeekJoinTimeMs;
  }

  @JsonProperty("life.contentWatchedTimeMs")
  public long getLifeContentWatchedTimeMs() {
    return lifeContentWatchedTimeMs;
  }

  @JsonProperty("life.contentWatchedTimeMs")
  public void setLifeContentWatchedTimeMs(long lifeContentWatchedTimeMs) {
    this.lifeContentWatchedTimeMs = lifeContentWatchedTimeMs;
  }

  @JsonProperty("life.contentWatchedPct")
  public double getLifeContentWatchedPct() {
    return lifeContentWatchedPct;
  }

  @JsonProperty("life.contentWatchedPct")
  public void setLifeContentWatchedPct(double lifeContentWatchedPct) {
    this.lifeContentWatchedPct = lifeContentWatchedPct;
  }

  @JsonProperty("life.averageFrameRate")
  public long getLifeAverageFrameRate() {
    return lifeAverageFrameRate;
  }

  @JsonProperty("life.averageFrameRate")
  public void setLifeAverageFrameRate(long lifeAverageFrameRate) {
    this.lifeAverageFrameRate = lifeAverageFrameRate;
  }

  @JsonProperty("life.renderingQuality")
  public long getLifeRenderingQuality() {
    return lifeRenderingQuality;
  }

  @JsonProperty("life.renderingQuality")
  public void setLifeRenderingQuality(long lifeRenderingQuality) {
    this.lifeRenderingQuality = lifeRenderingQuality;
  }

  @JsonProperty("life.resourceIds")
  public List<Long> getLifeResourceIds() {
    return lifeResourceIds;
  }

  @JsonProperty("life.resourceIds")
  public void setLifeResourceIds(List<Long> lifeResourceIds) {
    this.lifeResourceIds = lifeResourceIds;
  }

  @JsonProperty("life.cdns")
  public List<String> getLifeCdns() {
    return lifeCdns;
  }

  @JsonProperty("life.cdns")
  public void setLifeCdns(List<String> lifeCdns) {
    this.lifeCdns = lifeCdns;
  }

  @JsonProperty("life.fatalErrorResourceIds")
  public List<Object> getLifeFatalErrorResourceIds() {
    return lifeFatalErrorResourceIds;
  }

  @JsonProperty("life.fatalErrorResourceIds")
  public void setLifeFatalErrorResourceIds(List<Object> lifeFatalErrorResourceIds) {
    this.lifeFatalErrorResourceIds = lifeFatalErrorResourceIds;
  }

  @JsonProperty("life.fatalErrorCdns")
  public List<Object> getLifeFatalErrorCdns() {
    return lifeFatalErrorCdns;
  }

  @JsonProperty("life.fatalErrorCdns")
  public void setLifeFatalErrorCdns(List<Object> lifeFatalErrorCdns) {
    this.lifeFatalErrorCdns = lifeFatalErrorCdns;
  }

  @JsonProperty("life.latestErrorResourceId")
  public Object getLifeLatestErrorResourceId() {
    return lifeLatestErrorResourceId;
  }

  @JsonProperty("life.latestErrorResourceId")
  public void setLifeLatestErrorResourceId(Object lifeLatestErrorResourceId) {
    this.lifeLatestErrorResourceId = lifeLatestErrorResourceId;
  }

  @JsonProperty("life.latestErrorCdn")
  public Object getLifeLatestErrorCdn() {
    return lifeLatestErrorCdn;
  }

  @JsonProperty("life.latestErrorCdn")
  public void setLifeLatestErrorCdn(Object lifeLatestErrorCdn) {
    this.lifeLatestErrorCdn = lifeLatestErrorCdn;
  }

  @JsonProperty("life.joinResourceIds")
  public List<Long> getLifeJoinResourceIds() {
    return lifeJoinResourceIds;
  }

  @JsonProperty("life.joinResourceIds")
  public void setLifeJoinResourceIds(List<Long> lifeJoinResourceIds) {
    this.lifeJoinResourceIds = lifeJoinResourceIds;
  }

  @JsonProperty("life.joinCdns")
  public List<String> getLifeJoinCdns() {
    return lifeJoinCdns;
  }

  @JsonProperty("life.joinCdns")
  public void setLifeJoinCdns(List<String> lifeJoinCdns) {
    this.lifeJoinCdns = lifeJoinCdns;
  }

  @JsonProperty("life.lastJoinCdn")
  public String getLifeLastJoinCdn() {
    return lifeLastJoinCdn;
  }

  @JsonProperty("life.lastJoinCdn")
  public void setLifeLastJoinCdn(String lifeLastJoinCdn) {
    this.lifeLastJoinCdn = lifeLastJoinCdn;
  }

  @JsonProperty("life.lastJoinResourceId")
  public long getLifeLastJoinResourceId() {
    return lifeLastJoinResourceId;
  }

  @JsonProperty("life.lastJoinResourceId")
  public void setLifeLastJoinResourceId(long lifeLastJoinResourceId) {
    this.lifeLastJoinResourceId = lifeLastJoinResourceId;
  }

  @JsonProperty("interval.startTimeMs")
  public long getIntervalStartTimeMs() {
    return intervalStartTimeMs;
  }

  @JsonProperty("interval.startTimeMs")
  public void setIntervalStartTimeMs(long intervalStartTimeMs) {
    this.intervalStartTimeMs = intervalStartTimeMs;
  }

  @JsonProperty("switch.resourceId")
  public long getSwitchResourceId() {
    return switchResourceId;
  }

  @JsonProperty("switch.resourceId")
  public void setSwitchResourceId(long switchResourceId) {
    this.switchResourceId = switchResourceId;
  }

  @JsonProperty("switch.cdn")
  public String getSwitchCdn() {
    return switchCdn;
  }

  @JsonProperty("switch.cdn")
  public void setSwitchCdn(String switchCdn) {
    this.switchCdn = switchCdn;
  }

  @JsonProperty("switch.justJoined")
  public boolean isSwitchJustJoined() {
    return switchJustJoined;
  }

  @JsonProperty("switch.justJoined")
  public void setSwitchJustJoined(boolean switchJustJoined) {
    this.switchJustJoined = switchJustJoined;
  }

  @JsonProperty("switch.hasJoined")
  public boolean isSwitchHasJoined() {
    return switchHasJoined;
  }

  @JsonProperty("switch.hasJoined")
  public void setSwitchHasJoined(boolean switchHasJoined) {
    this.switchHasJoined = switchHasJoined;
  }

  @JsonProperty("switch.justJoinedAndLifeJoinTimeMsIsAccurate")
  public boolean isSwitchJustJoinedAndLifeJoinTimeMsIsAccurate() {
    return switchJustJoinedAndLifeJoinTimeMsIsAccurate;
  }

  @JsonProperty("switch.justJoinedAndLifeJoinTimeMsIsAccurate")
  public void setSwitchJustJoinedAndLifeJoinTimeMsIsAccurate(boolean switchJustJoinedAndLifeJoinTimeMsIsAccurate) {
    this.switchJustJoinedAndLifeJoinTimeMsIsAccurate = switchJustJoinedAndLifeJoinTimeMsIsAccurate;
  }

  @JsonProperty("switch.isEndedPlay")
  public boolean isSwitchIsEndedPlay() {
    return switchIsEndedPlay;
  }

  @JsonProperty("switch.isEndedPlay")
  public void setSwitchIsEndedPlay(boolean switchIsEndedPlay) {
    this.switchIsEndedPlay = switchIsEndedPlay;
  }

  @JsonProperty("switch.isEndedPlayAndLifeAverageBitrateKbpsGT0")
  public boolean isSwitchIsEndedPlayAndLifeAverageBitrateKbpsGT0() {
    return switchIsEndedPlayAndLifeAverageBitrateKbpsGT0;
  }

  @JsonProperty("switch.isEndedPlayAndLifeAverageBitrateKbpsGT0")
  public void setSwitchIsEndedPlayAndLifeAverageBitrateKbpsGT0(boolean switchIsEndedPlayAndLifeAverageBitrateKbpsGT0) {
    this.switchIsEndedPlayAndLifeAverageBitrateKbpsGT0 = switchIsEndedPlayAndLifeAverageBitrateKbpsGT0;
  }

  @JsonProperty("switch.isVideoStartFailure")
  public boolean isSwitchIsVideoStartFailure() {
    return switchIsVideoStartFailure;
  }

  @JsonProperty("switch.isVideoStartFailure")
  public void setSwitchIsVideoStartFailure(boolean switchIsVideoStartFailure) {
    this.switchIsVideoStartFailure = switchIsVideoStartFailure;
  }

  @JsonProperty("switch.videoStartFailureErrors")
  public List<Object> getSwitchVideoStartFailureErrors() {
    return switchVideoStartFailureErrors;
  }

  @JsonProperty("switch.videoStartFailureErrors")
  public void setSwitchVideoStartFailureErrors(List<Object> switchVideoStartFailureErrors) {
    this.switchVideoStartFailureErrors = switchVideoStartFailureErrors;
  }

  @JsonProperty("switch.isExitBeforeVideoStart")
  public boolean isSwitchIsExitBeforeVideoStart() {
    return switchIsExitBeforeVideoStart;
  }

  @JsonProperty("switch.isExitBeforeVideoStart")
  public void setSwitchIsExitBeforeVideoStart(boolean switchIsExitBeforeVideoStart) {
    this.switchIsExitBeforeVideoStart = switchIsExitBeforeVideoStart;
  }

  @JsonProperty("switch.isVideoPlaybackFailure")
  public boolean isSwitchIsVideoPlaybackFailure() {
    return switchIsVideoPlaybackFailure;
  }

  @JsonProperty("switch.isVideoPlaybackFailure")
  public void setSwitchIsVideoPlaybackFailure(boolean switchIsVideoPlaybackFailure) {
    this.switchIsVideoPlaybackFailure = switchIsVideoPlaybackFailure;
  }

  @JsonProperty("switch.isVideoStartSave")
  public boolean isSwitchIsVideoStartSave() {
    return switchIsVideoStartSave;
  }

  @JsonProperty("switch.isVideoStartSave")
  public void setSwitchIsVideoStartSave(boolean switchIsVideoStartSave) {
    this.switchIsVideoStartSave = switchIsVideoStartSave;
  }

  @JsonProperty("switch.videoPlaybackFailureErrors")
  public List<Object> getSwitchVideoPlaybackFailureErrors() {
    return switchVideoPlaybackFailureErrors;
  }

  @JsonProperty("switch.videoPlaybackFailureErrors")
  public void setSwitchVideoPlaybackFailureErrors(List<Object> switchVideoPlaybackFailureErrors) {
    this.switchVideoPlaybackFailureErrors = switchVideoPlaybackFailureErrors;
  }

  @JsonProperty("switch.isAttempt")
  public boolean isSwitchIsAttempt() {
    return switchIsAttempt;
  }

  @JsonProperty("switch.isAttempt")
  public void setSwitchIsAttempt(boolean switchIsAttempt) {
    this.switchIsAttempt = switchIsAttempt;
  }

  @JsonProperty("switch.playingTimeMs")
  public long getSwitchPlayingTimeMs() {
    return switchPlayingTimeMs;
  }

  @JsonProperty("switch.playingTimeMs")
  public void setSwitchPlayingTimeMs(long switchPlayingTimeMs) {
    this.switchPlayingTimeMs = switchPlayingTimeMs;
  }

  @JsonProperty("switch.rebufferingTimeMs")
  public long getSwitchRebufferingTimeMs() {
    return switchRebufferingTimeMs;
  }

  @JsonProperty("switch.rebufferingTimeMs")
  public void setSwitchRebufferingTimeMs(long switchRebufferingTimeMs) {
    this.switchRebufferingTimeMs = switchRebufferingTimeMs;
  }

  @JsonProperty("switch.networkRebufferingTimeMs")
  public long getSwitchNetworkRebufferingTimeMs() {
    return switchNetworkRebufferingTimeMs;
  }

  @JsonProperty("switch.networkRebufferingTimeMs")
  public void setSwitchNetworkRebufferingTimeMs(long switchNetworkRebufferingTimeMs) {
    this.switchNetworkRebufferingTimeMs = switchNetworkRebufferingTimeMs;
  }

  @JsonProperty("switch.bitrateBytes")
  public long getSwitchBitrateBytes() {
    return switchBitrateBytes;
  }

  @JsonProperty("switch.bitrateBytes")
  public void setSwitchBitrateBytes(long switchBitrateBytes) {
    this.switchBitrateBytes = switchBitrateBytes;
  }

  @JsonProperty("switch.bitrateTimeMs")
  public long getSwitchBitrateTimeMs() {
    return switchBitrateTimeMs;
  }

  @JsonProperty("switch.bitrateTimeMs")
  public void setSwitchBitrateTimeMs(long switchBitrateTimeMs) {
    this.switchBitrateTimeMs = switchBitrateTimeMs;
  }

  @JsonProperty("switch.framesLoaded")
  public long getSwitchFramesLoaded() {
    return switchFramesLoaded;
  }

  @JsonProperty("switch.framesLoaded")
  public void setSwitchFramesLoaded(long switchFramesLoaded) {
    this.switchFramesLoaded = switchFramesLoaded;
  }

  @JsonProperty("switch.framesPlayingTimeMs")
  public long getSwitchFramesPlayingTimeMs() {
    return switchFramesPlayingTimeMs;
  }

  @JsonProperty("switch.framesPlayingTimeMs")
  public void setSwitchFramesPlayingTimeMs(long switchFramesPlayingTimeMs) {
    this.switchFramesPlayingTimeMs = switchFramesPlayingTimeMs;
  }

  @JsonProperty("switch.seekJoinTimeMs")
  public long getSwitchSeekJoinTimeMs() {
    return switchSeekJoinTimeMs;
  }

  @JsonProperty("switch.seekJoinTimeMs")
  public void setSwitchSeekJoinTimeMs(long switchSeekJoinTimeMs) {
    this.switchSeekJoinTimeMs = switchSeekJoinTimeMs;
  }

  @JsonProperty("switch.seekJoinCount")
  public long getSwitchSeekJoinCount() {
    return switchSeekJoinCount;
  }

  @JsonProperty("switch.seekJoinCount")
  public void setSwitchSeekJoinCount(long switchSeekJoinCount) {
    this.switchSeekJoinCount = switchSeekJoinCount;
  }

  @JsonProperty("switch.pcpIntervals5Sec")
  public long getSwitchPcpIntervals5Sec() {
    return switchPcpIntervals5Sec;
  }

  @JsonProperty("switch.pcpIntervals5Sec")
  public void setSwitchPcpIntervals5Sec(long switchPcpIntervals5Sec) {
    this.switchPcpIntervals5Sec = switchPcpIntervals5Sec;
  }

  @JsonProperty("switch.pcpIntervals")
  public long getSwitchPcpIntervals() {
    return switchPcpIntervals;
  }

  @JsonProperty("switch.pcpIntervals")
  public void setSwitchPcpIntervals(long switchPcpIntervals) {
    this.switchPcpIntervals = switchPcpIntervals;
  }

  @JsonProperty("tags.m3.dv.n")
  public String getTagsM3DvN() {
    return tagsM3DvN;
  }

  @JsonProperty("tags.m3.dv.n")
  public void setTagsM3DvN(String tagsM3DvN) {
    this.tagsM3DvN = tagsM3DvN;
  }

  @JsonProperty("tags.c3.pt.os")
  public String getTagsC3PtOs() {
    return tagsC3PtOs;
  }

  @JsonProperty("tags.c3.pt.os")
  public void setTagsC3PtOs(String tagsC3PtOs) {
    this.tagsC3PtOs = tagsC3PtOs;
  }

  @JsonProperty("tags.m3.dv.brv")
  public String getTagsM3DvBrv() {
    return tagsM3DvBrv;
  }

  @JsonProperty("tags.m3.dv.brv")
  public void setTagsM3DvBrv(String tagsM3DvBrv) {
    this.tagsM3DvBrv = tagsM3DvBrv;
  }

  @JsonProperty("tags.c3.cmp.0._id")
  public String getTagsC3Cmp0Id() {
    return tagsC3Cmp0Id;
  }

  @JsonProperty("tags.c3.cmp.0._id")
  public void setTagsC3Cmp0Id(String tagsC3Cmp0Id) {
    this.tagsC3Cmp0Id = tagsC3Cmp0Id;
  }

  @JsonProperty("tags.androidTV")
  public String getTagsAndroidTV() {
    return tagsAndroidTV;
  }

  @JsonProperty("tags.androidTV")
  public void setTagsAndroidTV(String tagsAndroidTV) {
    this.tagsAndroidTV = tagsAndroidTV;
  }

  @JsonProperty("tags.availableRAM")
  public String getTagsAvailableRAM() {
    return tagsAvailableRAM;
  }

  @JsonProperty("tags.availableRAM")
  public void setTagsAvailableRAM(String tagsAvailableRAM) {
    this.tagsAvailableRAM = tagsAvailableRAM;
  }

  @JsonProperty("tags.c3.device.model")
  public String getTagsC3DeviceModel() {
    return tagsC3DeviceModel;
  }

  @JsonProperty("tags.c3.device.model")
  public void setTagsC3DeviceModel(String tagsC3DeviceModel) {
    this.tagsC3DeviceModel = tagsC3DeviceModel;
  }

  @JsonProperty("tags.wifiSSID")
  public String getTagsWifiSSID() {
    return tagsWifiSSID;
  }

  @JsonProperty("tags.wifiSSID")
  public void setTagsWifiSSID(String tagsWifiSSID) {
    this.tagsWifiSSID = tagsWifiSSID;
  }

  @JsonProperty("tags.c3.viewer.id")
  public String getTagsC3ViewerId() {
    return tagsC3ViewerId;
  }

  @JsonProperty("tags.c3.viewer.id")
  public void setTagsC3ViewerId(String tagsC3ViewerId) {
    this.tagsC3ViewerId = tagsC3ViewerId;
  }

  @JsonProperty("tags.c3.cws.clv")
  public String getTagsC3CwsClv() {
    return tagsC3CwsClv;
  }

  @JsonProperty("tags.c3.cws.clv")
  public void setTagsC3CwsClv(String tagsC3CwsClv) {
    this.tagsC3CwsClv = tagsC3CwsClv;
  }

  @JsonProperty("tags.screenSize")
  public String getTagsScreenSize() {
    return tagsScreenSize;
  }

  @JsonProperty("tags.screenSize")
  public void setTagsScreenSize(String tagsScreenSize) {
    this.tagsScreenSize = tagsScreenSize;
  }

  @JsonProperty("tags.channel")
  public String getTagsChannel() {
    return tagsChannel;
  }

  @JsonProperty("tags.channel")
  public void setTagsChannel(String tagsChannel) {
    this.tagsChannel = tagsChannel;
  }

  @JsonProperty("tags.totalRAM")
  public String getTagsTotalRAM() {
    return tagsTotalRAM;
  }

  @JsonProperty("tags.totalRAM")
  public void setTagsTotalRAM(String tagsTotalRAM) {
    this.tagsTotalRAM = tagsTotalRAM;
  }

  @JsonProperty("tags.c3.cfg.alt.name")
  public String getTagsC3CfgAltName() {
    return tagsC3CfgAltName;
  }

  @JsonProperty("tags.c3.cfg.alt.name")
  public void setTagsC3CfgAltName(String tagsC3CfgAltName) {
    this.tagsC3CfgAltName = tagsC3CfgAltName;
  }

  @JsonProperty("tags.playerVersion")
  public String getTagsPlayerVersion() {
    return tagsPlayerVersion;
  }

  @JsonProperty("tags.playerVersion")
  public void setTagsPlayerVersion(String tagsPlayerVersion) {
    this.tagsPlayerVersion = tagsPlayerVersion;
  }

  @JsonProperty("tags.c3.player.name")
  public String getTagsC3PlayerName() {
    return tagsC3PlayerName;
  }

  @JsonProperty("tags.c3.player.name")
  public void setTagsC3PlayerName(String tagsC3PlayerName) {
    this.tagsC3PlayerName = tagsC3PlayerName;
  }

  @JsonProperty("tags.m3.dv.br")
  public String getTagsM3DvBr() {
    return tagsM3DvBr;
  }

  @JsonProperty("tags.m3.dv.br")
  public void setTagsM3DvBr(String tagsM3DvBr) {
    this.tagsM3DvBr = tagsM3DvBr;
  }

  @JsonProperty("tags.m3.dv.os")
  public String getTagsM3DvOs() {
    return tagsM3DvOs;
  }

  @JsonProperty("tags.m3.dv.os")
  public void setTagsM3DvOs(String tagsM3DvOs) {
    this.tagsM3DvOs = tagsM3DvOs;
  }

  @JsonProperty("tags.contentId")
  public String getTagsContentId() {
    return tagsContentId;
  }

  @JsonProperty("tags.contentId")
  public void setTagsContentId(String tagsContentId) {
    this.tagsContentId = tagsContentId;
  }

  @JsonProperty("tags.c3.adaptor.type")
  public String getTagsC3AdaptorType() {
    return tagsC3AdaptorType;
  }

  @JsonProperty("tags.c3.adaptor.type")
  public void setTagsC3AdaptorType(String tagsC3AdaptorType) {
    this.tagsC3AdaptorType = tagsC3AdaptorType;
  }

  @JsonProperty("tags.m3.dv.osf")
  public String getTagsM3DvOsf() {
    return tagsM3DvOsf;
  }

  @JsonProperty("tags.m3.dv.osf")
  public void setTagsM3DvOsf(String tagsM3DvOsf) {
    this.tagsM3DvOsf = tagsM3DvOsf;
  }

  @JsonProperty("tags.c3.device.type")
  public String getTagsC3DeviceType() {
    return tagsC3DeviceType;
  }

  @JsonProperty("tags.c3.device.type")
  public void setTagsC3DeviceType(String tagsC3DeviceType) {
    this.tagsC3DeviceType = tagsC3DeviceType;
  }

  @JsonProperty("tags.connectionType")
  public String getTagsConnectionType() {
    return tagsConnectionType;
  }

  @JsonProperty("tags.connectionType")
  public void setTagsConnectionType(String tagsConnectionType) {
    this.tagsConnectionType = tagsConnectionType;
  }

  @JsonProperty("tags.applicationVersion")
  public String getTagsApplicationVersion() {
    return tagsApplicationVersion;
  }

  @JsonProperty("tags.applicationVersion")
  public void setTagsApplicationVersion(String tagsApplicationVersion) {
    this.tagsApplicationVersion = tagsApplicationVersion;
  }

  @JsonProperty("tags.screenRatio")
  public String getTagsScreenRatio() {
    return tagsScreenRatio;
  }

  @JsonProperty("tags.screenRatio")
  public void setTagsScreenRatio(String tagsScreenRatio) {
    this.tagsScreenRatio = tagsScreenRatio;
  }

  @JsonProperty("tags.c3.cmp.0._type")
  public String getTagsC3Cmp0Type() {
    return tagsC3Cmp0Type;
  }

  @JsonProperty("tags.c3.cmp.0._type")
  public void setTagsC3Cmp0Type(String tagsC3Cmp0Type) {
    this.tagsC3Cmp0Type = tagsC3Cmp0Type;
  }

  @JsonProperty("tags.c3.client.osf")
  public String getTagsC3ClientOsf() {
    return tagsC3ClientOsf;
  }

  @JsonProperty("tags.c3.client.osf")
  public void setTagsC3ClientOsf(String tagsC3ClientOsf) {
    this.tagsC3ClientOsf = tagsC3ClientOsf;
  }

  @JsonProperty("tags.c3.pt.ver")
  public String getTagsC3PtVer() {
    return tagsC3PtVer;
  }

  @JsonProperty("tags.c3.pt.ver")
  public void setTagsC3PtVer(String tagsC3PtVer) {
    this.tagsC3PtVer = tagsC3PtVer;
  }

  @JsonProperty("tags.c3.device.cver.bld")
  public String getTagsC3DeviceCverBld() {
    return tagsC3DeviceCverBld;
  }

  @JsonProperty("tags.c3.device.cver.bld")
  public void setTagsC3DeviceCverBld(String tagsC3DeviceCverBld) {
    this.tagsC3DeviceCverBld = tagsC3DeviceCverBld;
  }

  @JsonProperty("tags.c3.device.usesSdk")
  public String getTagsC3DeviceUsesSdk() {
    return tagsC3DeviceUsesSdk;
  }

  @JsonProperty("tags.c3.device.usesSdk")
  public void setTagsC3DeviceUsesSdk(String tagsC3DeviceUsesSdk) {
    this.tagsC3DeviceUsesSdk = tagsC3DeviceUsesSdk;
  }

  @JsonProperty("tags.c3.pt")
  public String getTagsC3Pt() {
    return tagsC3Pt;
  }

  @JsonProperty("tags.c3.pt")
  public void setTagsC3Pt(String tagsC3Pt) {
    this.tagsC3Pt = tagsC3Pt;
  }

  @JsonProperty("tags.playerVendor")
  public String getTagsPlayerVendor() {
    return tagsPlayerVendor;
  }

  @JsonProperty("tags.playerVendor")
  public void setTagsPlayerVendor(String tagsPlayerVendor) {
    this.tagsPlayerVendor = tagsPlayerVendor;
  }

  @JsonProperty("tags.c3.protocol.level")
  public String getTagsC3ProtocolLevel() {
    return tagsC3ProtocolLevel;
  }

  @JsonProperty("tags.c3.protocol.level")
  public void setTagsC3ProtocolLevel(String tagsC3ProtocolLevel) {
    this.tagsC3ProtocolLevel = tagsC3ProtocolLevel;
  }

  @JsonProperty("tags.c3.client.br")
  public String getTagsC3ClientBr() {
    return tagsC3ClientBr;
  }

  @JsonProperty("tags.c3.client.br")
  public void setTagsC3ClientBr(String tagsC3ClientBr) {
    this.tagsC3ClientBr = tagsC3ClientBr;
  }

  @JsonProperty("tags.m3.dv.cat")
  public String getTagsM3DvCat() {
    return tagsM3DvCat;
  }

  @JsonProperty("tags.m3.dv.cat")
  public void setTagsM3DvCat(String tagsM3DvCat) {
    this.tagsM3DvCat = tagsM3DvCat;
  }

  @JsonProperty("tags.category")
  public String getTagsCategory() {
    return tagsCategory;
  }

  @JsonProperty("tags.category")
  public void setTagsCategory(String tagsCategory) {
    this.tagsCategory = tagsCategory;
  }

  @JsonProperty("tags.c3.device.manufacturer")
  public String getTagsC3DeviceManufacturer() {
    return tagsC3DeviceManufacturer;
  }

  @JsonProperty("tags.c3.device.manufacturer")
  public void setTagsC3DeviceManufacturer(String tagsC3DeviceManufacturer) {
    this.tagsC3DeviceManufacturer = tagsC3DeviceManufacturer;
  }

  @JsonProperty("tags.m3.dv.hwt")
  public String getTagsM3DvHwt() {
    return tagsM3DvHwt;
  }

  @JsonProperty("tags.m3.dv.hwt")
  public void setTagsM3DvHwt(String tagsM3DvHwt) {
    this.tagsM3DvHwt = tagsM3DvHwt;
  }

  @JsonProperty("tags.screenOrientation")
  public String getTagsScreenOrientation() {
    return tagsScreenOrientation;
  }

  @JsonProperty("tags.screenOrientation")
  public void setTagsScreenOrientation(String tagsScreenOrientation) {
    this.tagsScreenOrientation = tagsScreenOrientation;
  }

  @JsonProperty("tags.c3.protocol.type")
  public String getTagsC3ProtocolType() {
    return tagsC3ProtocolType;
  }

  @JsonProperty("tags.c3.protocol.type")
  public void setTagsC3ProtocolType(String tagsC3ProtocolType) {
    this.tagsC3ProtocolType = tagsC3ProtocolType;
  }

  @JsonProperty("tags.m3.dv.mrk")
  public String getTagsM3DvMrk() {
    return tagsM3DvMrk;
  }

  @JsonProperty("tags.m3.dv.mrk")
  public void setTagsM3DvMrk(String tagsM3DvMrk) {
    this.tagsM3DvMrk = tagsM3DvMrk;
  }

  @JsonProperty("tags.m3.dv.mnf")
  public String getTagsM3DvMnf() {
    return tagsM3DvMnf;
  }

  @JsonProperty("tags.m3.dv.mnf")
  public void setTagsM3DvMnf(String tagsM3DvMnf) {
    this.tagsM3DvMnf = tagsM3DvMnf;
  }

  @JsonProperty("tags.contentType")
  public String getTagsContentType() {
    return tagsContentType;
  }

  @JsonProperty("tags.contentType")
  public void setTagsContentType(String tagsContentType) {
    this.tagsContentType = tagsContentType;
  }

  @JsonProperty("tags.c3.client.osv")
  public String getTagsC3ClientOsv() {
    return tagsC3ClientOsv;
  }

  @JsonProperty("tags.c3.client.osv")
  public void setTagsC3ClientOsv(String tagsC3ClientOsv) {
    this.tagsC3ClientOsv = tagsC3ClientOsv;
  }

  @JsonProperty("tags.c3.device.ua")
  public String getTagsC3DeviceUa() {
    return tagsC3DeviceUa;
  }

  @JsonProperty("tags.c3.device.ua")
  public void setTagsC3DeviceUa(String tagsC3DeviceUa) {
    this.tagsC3DeviceUa = tagsC3DeviceUa;
  }

  @JsonProperty("tags.c3.client.osName")
  public String getTagsC3ClientOsName() {
    return tagsC3ClientOsName;
  }

  @JsonProperty("tags.c3.client.osName")
  public void setTagsC3ClientOsName(String tagsC3ClientOsName) {
    this.tagsC3ClientOsName = tagsC3ClientOsName;
  }

  @JsonProperty("tags.streamProtocol")
  public String getTagsStreamProtocol() {
    return tagsStreamProtocol;
  }

  @JsonProperty("tags.streamProtocol")
  public void setTagsStreamProtocol(String tagsStreamProtocol) {
    this.tagsStreamProtocol = tagsStreamProtocol;
  }

  @JsonProperty("tags.versionCode")
  public String getTagsVersionCode() {
    return tagsVersionCode;
  }

  @JsonProperty("tags.versionCode")
  public void setTagsVersionCode(String tagsVersionCode) {
    this.tagsVersionCode = tagsVersionCode;
  }

  @JsonProperty("tags.operator")
  public String getTagsOperator() {
    return tagsOperator;
  }

  @JsonProperty("tags.operator")
  public void setTagsOperator(String tagsOperator) {
    this.tagsOperator = tagsOperator;
  }

  @JsonProperty("tags.c3.cluster.name")
  public String getTagsC3ClusterName() {
    return tagsC3ClusterName;
  }

  @JsonProperty("tags.c3.cluster.name")
  public void setTagsC3ClusterName(String tagsC3ClusterName) {
    this.tagsC3ClusterName = tagsC3ClusterName;
  }

  @JsonProperty("tags.c3.cmp.0._ver")
  public String getTagsC3Cmp0Ver() {
    return tagsC3Cmp0Ver;
  }

  @JsonProperty("tags.c3.cmp.0._ver")
  public void setTagsC3Cmp0Ver(String tagsC3Cmp0Ver) {
    this.tagsC3Cmp0Ver = tagsC3Cmp0Ver;
  }

  @JsonProperty("tags.c3.pt.os.ver")
  public String getTagsC3PtOsVer() {
    return tagsC3PtOsVer;
  }

  @JsonProperty("tags.c3.pt.os.ver")
  public void setTagsC3PtOsVer(String tagsC3PtOsVer) {
    this.tagsC3PtOsVer = tagsC3PtOsVer;
  }

  @JsonProperty("tags.c3.cws.sf")
  public String getTagsC3CwsSf() {
    return tagsC3CwsSf;
  }

  @JsonProperty("tags.c3.cws.sf")
  public void setTagsC3CwsSf(String tagsC3CwsSf) {
    this.tagsC3CwsSf = tagsC3CwsSf;
  }

  @JsonProperty("tags.c3.pt.br.ver")
  public String getTagsC3PtBrVer() {
    return tagsC3PtBrVer;
  }

  @JsonProperty("tags.c3.pt.br.ver")
  public void setTagsC3PtBrVer(String tagsC3PtBrVer) {
    this.tagsC3PtBrVer = tagsC3PtBrVer;
  }

  @JsonProperty("tags.c3.device.conn")
  public String getTagsC3DeviceConn() {
    return tagsC3DeviceConn;
  }

  @JsonProperty("tags.c3.device.conn")
  public void setTagsC3DeviceConn(String tagsC3DeviceConn) {
    this.tagsC3DeviceConn = tagsC3DeviceConn;
  }

  @JsonProperty("tags.screen")
  public String getTagsScreen() {
    return tagsScreen;
  }

  @JsonProperty("tags.screen")
  public void setTagsScreen(String tagsScreen) {
    this.tagsScreen = tagsScreen;
  }

  @JsonProperty("tags.c3.cmp.0._cfg_ver")
  public String getTagsC3Cmp0CfgVer() {
    return tagsC3Cmp0CfgVer;
  }

  @JsonProperty("tags.c3.cmp.0._cfg_ver")
  public void setTagsC3Cmp0CfgVer(String tagsC3Cmp0CfgVer) {
    this.tagsC3Cmp0CfgVer = tagsC3Cmp0CfgVer;
  }

  @JsonProperty("tags.c3.device.brand")
  public String getTagsC3DeviceBrand() {
    return tagsC3DeviceBrand;
  }

  @JsonProperty("tags.c3.device.brand")
  public void setTagsC3DeviceBrand(String tagsC3DeviceBrand) {
    this.tagsC3DeviceBrand = tagsC3DeviceBrand;
  }

  @JsonProperty("tags.c3.pt.br")
  public String getTagsC3PtBr() {
    return tagsC3PtBr;
  }

  @JsonProperty("tags.c3.pt.br")
  public void setTagsC3PtBr(String tagsC3PtBr) {
    this.tagsC3PtBr = tagsC3PtBr;
  }

  @JsonProperty("tags.c3.video.isLive")
  public String getTagsC3VideoIsLive() {
    return tagsC3VideoIsLive;
  }

  @JsonProperty("tags.c3.video.isLive")
  public void setTagsC3VideoIsLive(String tagsC3VideoIsLive) {
    this.tagsC3VideoIsLive = tagsC3VideoIsLive;
  }

  @JsonProperty("tags.m3.dv.osv")
  public String getTagsM3DvOsv() {
    return tagsM3DvOsv;
  }

  @JsonProperty("tags.m3.dv.osv")
  public void setTagsM3DvOsv(String tagsM3DvOsv) {
    this.tagsM3DvOsv = tagsM3DvOsv;
  }

  @JsonProperty("tags.c3.device.cver")
  public String getTagsC3DeviceCver() {
    return tagsC3DeviceCver;
  }

  @JsonProperty("tags.c3.device.cver")
  public void setTagsC3DeviceCver(String tagsC3DeviceCver) {
    this.tagsC3DeviceCver = tagsC3DeviceCver;
  }

  @JsonProperty("tags.c3.sh")
  public String getTagsC3Sh() {
    return tagsC3Sh;
  }

  @JsonProperty("tags.c3.sh")
  public void setTagsC3Sh(String tagsC3Sh) {
    this.tagsC3Sh = tagsC3Sh;
  }

//  @JsonAnyGetter
//  public Map<String, Object> getAdditionalProperties() {
//    return this.additionalProperties;
//  }

//  @JsonAnySetter
//  public void setAdditionalProperty(String name, Object value) {
//    this.additionalProperties.put(name, value);
//  }

}