Sample: 

```text
+----------------+----------+---------------+
| Record Index   | Country  | Sector        |
+----------------+----------+---------------+
| 0              | GB       | Financials    |
| 1              | DE       | Manufacturing |
| 2              | FR       | Agriculturals |
| 3              | FR       | Financials    |
| 4              | GB       | Energies      |
+----------------+----------+---------------+

```

From: [How bitmap works!](http://richardstartin.uk/how-a-bitmap-index-works/)

>For each distinct value of each indexed attribute of your data, compute the set of indices of records matching the predicate. For each attribute, create a map from the attribute values to the sets of corresponding record indices.


`Country` index: 
- `each distinct value of each indexed attribute `
  - `GB`, `DE`, `FR`
- `compute the set of indices of records `
  - `GB: [0, 4]`
  - `DE: [1]`
  - `FR: [2, 3]`
- `create a map from the attribute values to the sets of corresponding record indices`:
  ```text
  +--------+-----------------+----------+
  | Value  | Record Indices  | Bitmap   |
  +--------+-----------------+----------+
  | GB     |  0, 4           | 0x10001  |
  | DE     |  1              | 0x10     |
  | FR     |  2, 3           | 0x1100   |
  +--------+-----------------+----------+
  
  ```

`Sector` index: 
- `each distinct value of each indexed attribute `
  - `Energies`, `Agriculturals`, `Manufacturing`, `Financials`
- `compute the set of indices of records `
  - `Financials: [0, 3]`
  - `Manufacturing: [1]`
  - `Agriculturals: [2]`
  - `Energies: [4]`
- `create a map from the attribute values to the sets of corresponding record indices`:
  ```text
  +---------------+-----------------+----------+
  | Value         | Record Indices  | Bitmap   |
  +---------------+-----------------+----------+
  | Financials    |  0, 3           | 0x1001   |
  | Manufacturing |  1              | 0x10     |
  | Agriculturals |  2              | 0x100    |
  | Energies      |  4              | 0x10000  |
  +---------------+-----------------+----------+
  
  ```

It’s worth noting three patterns in the tables above : 
- The number of bitmaps for an attribute is the attribute’s distinct count.
  - distinct count `Country`: 3
  - distinct count `Sector`: 4
- There are typically runs of zeroes or ones, and the lengths of these runs depend on the sort order of the record index attribute
- A bitmap index on the record index attribute itself would be as large as the data itself, and a much less concise representation. 
  - Bitmap indices __do not compete with B-tree indices__ for primary key attributes.



