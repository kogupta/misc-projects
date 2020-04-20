#!/usr/bin/env sh

# To execute 10 daily queries for 5 metrics
time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-25T00:00:00/2020-03-26T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-25_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-24T00:00:00/2020-03-25T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-24_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-23T00:00:00/2020-03-24T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-23_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-22T00:00:00/2020-03-23T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-22_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-21T00:00:00/2020-03-22T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-21_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-20T00:00:00/2020-03-21T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-20_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-19T00:00:00/2020-03-20T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-19_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-18T00:00:00/2020-03-19T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-18_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-17T00:00:00/2020-03-18T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-17_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-16T00:00:00/2020-03-17T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1M"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-16_1D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'


# To execute 10 weekly queries for 5 metrics
time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-19T00:00:00/2020-03-26T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-19_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-12T00:00:00/2020-03-19T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-12_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-03-05T00:00:00/2020-03-12T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-03-05_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-02-27T00:00:00/2020-03-05T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-02-27_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-02-20T00:00:00/2020-02-27T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-02-20_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-02-13T00:00:00/2020-02-20T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-02-13_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-02-06T00:00:00/2020-02-13T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-02-06_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-01-30T00:00:00/2020-02-06T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-01-30_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-01-23T00:00:00/2020-01-30T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-01-23_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-01-16T00:00:00/2020-01-23T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-01-16_7D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'


# To execute 11 monthly queries for 5 metrics
time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-02-26T00:00:00/2020-03-26T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-02-26_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-02-11T00:00:00/2020-03-11T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-02-11_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-01-25T00:00:00/2020-02-25T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-01-25_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2020-01-10T00:00:00/2020-02-10T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2020-01-10_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-12-26T00:00:00/2020-01-26T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-12-26_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-12-11T00:00:00/2020-01-11T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-12-11_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-11-27T00:00:00/2019-12-27T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-11-27_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-11-12T00:00:00/2019-12-12T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-11-12_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-10-27T00:00:00/2019-11-27T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-10-27_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-10-12T00:00:00/2019-11-12T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-10-12_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'

time curl -X POST -H 'Content-Type: application/json' 'http://ondemand-druid.iad1.prod.conviva.com:8888/druid/v2/' -d '{
  "queryType": "timeseries",
  "dataSource": {
    "type": "union",
    "dataSources": [
      "experience_insights.session_summaries.PT1H.6",
      "experience_insights.session_summaries.PT1H.1",
      "experience_insights.session_summaries.PT1H.3",
      "experience_insights.session_summaries.PT1H.4",
      "experience_insights.session_summaries.PT1H.5",
      "experience_insights.session_summaries.PT1H.2"
    ]
  },
  "intervals": ["2019-09-28T00:00:00/2019-10-28T00:00:00"],
  "granularity": {
    "type": "period",
    "period": "PT1H"
  },
  "filter": {
    "type": "and",
    "fields": [
      {"type": "selector", "dimension": "customerId", "value": "1960180565"},
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "geo.city", "value": "288482165539265064"},
          {"type": "selector", "dimension": "geo.dma", "value": "501"},
          {"type": "selector", "dimension": "geo.dma", "value": "803"}
        ]
      },
      {
        "type": "and",
        "fields": [
          {"type": "selector", "dimension": "tags.m3.dv.os", "value": "iOS"},
          {"type": "selector", "dimension": "tags.m3.dv.br", "value": "Safari"}
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {
            "type": "and",
            "fields": [
              {"type": "selector", "dimension": "life.cdns", "value": "AKAMAI"},
              {
                "type": "or",
                "fields": [
                  {"type": "selector", "dimension": "customerId", "value": "1960180532"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182269"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182409"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180616"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182561"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182461"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181225"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180442"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180386"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180423"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181665"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181545"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180559"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181845"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183029"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181213"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180565"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182197"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183173"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183205"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183257"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183265"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183249"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180434"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180544"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183465"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183469"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180542"},
                  {"type": "selector", "dimension": "customerId", "value": "1960181701"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183685"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183829"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183097"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182757"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182641"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183517"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183689"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182833"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183425"},
                  {"type": "selector", "dimension": "customerId", "value": "1960182529"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183417"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183061"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180612"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183321"},
                  {"type": "selector", "dimension": "customerId", "value": "1960183601"},
                  {"type": "selector", "dimension": "customerId", "value": "1960184069"},
                  {"type": "selector", "dimension": "customerId", "value": "1960180595"}
                ]
              }
            ]
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {"type": "selector", "dimension": "customerId", "value": "1960183329"},
          {"type": "selector", "dimension": "life.joinCdns", "value": "AKAMAI"}
        ]
      }
    ]
  },
  "aggregations": [
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isAttempt", "value": "true"},
      "aggregator": {"name": "agg_countAttempts", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.isExitBeforeVideoStart", "value": "true"},
      "aggregator": {"name": "agg_countExitsBeforeVideoStart", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoined", "value": "true"},
      "aggregator": {"name": "agg_countPlays", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_countPlaysWithValidJoinTime", "type": "count"}
    },
    {
      "type": "filtered",
      "filter": {"type": "selector", "dimension": "switch.justJoinedAndLifeJoinTimeMsIsAccurate", "value": "true"},
      "aggregator": {"name": "agg_sumLifeJoinTimeMsForPlaysWithValidJoinTime", "type": "longSum", "fieldName": "life.joinTimeMs"}
    }
  ],
  "context": {
    "queryId": "gateway_local:cdn-test_customer-filter-hulu_2020-03-26_2019-09-28_30D",
    "priority": 186,
    "timeout": 260000,
    "useCache": false,
    "grandTotal": false,
    "useResultLevelCache": false,
    "populateResultLevelCache": false,
    "populateCache": false
  }
}'
