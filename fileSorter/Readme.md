Currently data is stored as: 
  - 1 `Env` \[a directory]
  - a `Dbi` for each tenant
    - for each `Dbi`/tenant, create a map of `timestamp -> data`.
  - *DOES NOT* support multiple data points for each index key \[timestamp to millisecond precision currently]

Proposed change: 
  - create separate stores for data and index
  - `data store`: a map of `Primary Key [PK]` to data, lookup data by `PK`
  - `index`: multimap of `timestamp` to `PK`, sorted by `timestamp` to enable range lookup.
    - also provide bucketing/partitioning by hour
      ```text
      storage
          ├── tenant00
          │   ├── 00
          │   ├── 01
          │   ├── 02
          │   ├── 03
          │   └── ... (for 24 hours ...)
          ├── tenant01
          │   ├── 00
          │   ├── 01
          │   ├── 02
          │   └── ...
          └── tenant02
              ├── 00
              ├── 01
              ├── 02
              ├── 03
              │    ├── data (primary key -> data)
              │    └── index (timestamp -> pk1, pk2, ....)
              └── ...
      ```

    Input data: 
      - 1k payload
      - 5 tenants
      - 30 mins of generated data, 1 data point for each milli-second
      - ~9 gb data

    Read throughput: 
      - worst case:
        - 200ms to read 5 min worth of data
        - 5 min => 300 mb of data/tenant, 300k records \[1k payload size]
