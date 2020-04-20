package com.conviva.ondemand.checker.entities;

public enum Metric {
  Attempts,
  Bitrate,
  ConcurrentPlays,
  ConnectionInducedRebufferingRatio,
  EndedPlays,
  EndedPlaysPerUniqueDevice,
  ExitBeforeVideoStarts,
  ExitBeforeVideoStartsPercentage,
  Framerate,
  MinutesPlayedPerUniqueDevice,
  MinutesPlayedPerEndedPlay,
  MinutesPlayed,
  PercentageComplete,
  Plays,
  PlaysWithValidJoinTime,
  PlaysPercentage,
  RebufferingRatio,
  VideoRestartTime,
  VideoStartFailures,
  VideoStartFailuresPercentage,
  VideoPlaybackFailures,
  VideoPlaybackFailuresPercentage,
  VideoStartTime,
  UniqueDevices,
  AverageLifePlayingTimeMins,

  ActivePlays,
  // AE metrics
//    AdAttempts, -> Attempts
//    AdStartFailures, -> VideoStartFailuresPercentage
//    EBAS, -> ExitBeforeVideoStartsPercentage
//    AdStartupTime, -> VideoStartTime
  AdSlateDurationPct,
  AdSlatePlaysPct,
  //    AdImpressions, -> Plays
//    AdPCP, -> ConcurrentPlays
//    AdFreqPerUniqDevice, -> EndedPlaysPerUniqueDevice
//    AdEndedPlays, -> EndedPlays
  AdActualDuration,
  //    AdPlaybackFailures, -> VideoPlaybackFailuresPercentage
  AdCompletedCreativePlays,
  AdsNotStarted,
  AdsNotStartedPct,
  AdsWithErrors,
  AdsWithErrorsPct,
  AdTotalMinutes
  ;
}
