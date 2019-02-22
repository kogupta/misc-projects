package org.kogu.collections.benchmark;

import org.kogu.collections.test_helper.IMapTest;
import org.kogu.collections.test_helper.ITestSet;
import org.kogu.collections.test_helper.IntIntMap1Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapTestRunner {
  private static final int START_SIZE = 10_000;
  private static final int TOTAL_SIZE = 100_000_1000;
  private final static int[] MAP_SIZES = init();
  private static final float FILL_FACTOR = 0.5f;
  //share of unsuccessful get operations (approx)
  //increase this value significantly (Integer.MAX_VALUE is a good candidate) to revert to the original "always successful get" tests
  private static final int ONE_FAIL_OUT_OF = 2;
  private static final Class[] TESTS_ARTICLE = {
      IntIntMap1Test.class,
      FastUtilMapTest.class,
      JdkMapTest.class
  };
  @Param("1")
  public int m_mapSize;
  @Param("dummy")
  public String m_className;
  @Param({"get", "put", "remove"})
  public String m_testType;
  private IMapTest m_impl;

  private static int[] init() {
    int[] ints = new int[(int) (Math.log10(TOTAL_SIZE) - Math.log10(START_SIZE) + 1)];
    int start = START_SIZE;
    int p = 0;
    while (start <= TOTAL_SIZE) {
      ints[p++] = start;
      start *= 10;
    }
    return ints;
  }

  public static void main(String[] args) throws RunnerException, InstantiationException, IllegalAccessException {
    final LinkedHashMap<String, String> res = new LinkedHashMap<>(3);
    res.put("get", runTestSet("get"));
    res.put("put", runTestSet("put"));
    res.put("remove", runTestSet("remove"));

    for (final Map.Entry<String, String> entry : res.entrySet()) {
      System.out.println("Results for '" + entry.getKey() + "':");
      System.out.println(entry.getValue());
      System.out.println();
    }
  }

  private static String runTestSet(final String testSetName) throws RunnerException, InstantiationException, IllegalAccessException {
    final List<Class> tests = new ArrayList<>();
    tests.addAll(Arrays.asList(TESTS_ARTICLE));

    //first level: test class, second level - map size
    final Map<String, Map<Integer, String>> results = new HashMap<>();

    //pick map size first - we need to generate a set of keys to be used in all tests
    for (final int mapSize : MAP_SIZES) {
      //run tests one after another
      for (final Class testClass : tests) {
        Options opt = new OptionsBuilder()
            .include(".*" + MapTestRunner.class.getSimpleName() + ".*")
            .forks(1)
            .mode(Mode.SingleShotTime)
            .warmupBatchSize(TOTAL_SIZE / mapSize)
            .warmupIterations(10)
            .measurementBatchSize(TOTAL_SIZE / mapSize)
            .measurementIterations(8)
            .jvmArgsAppend("-Xmx30G")
            .param("m_mapSize", Integer.toString(mapSize))
            .param("m_className", testClass.getCanonicalName())
            .param("m_testType", testSetName)
            //.verbosity(VerboseMode.SILENT)
            .build();

        Collection<RunResult> res = new Runner(opt).run();
        for (RunResult rr : res) {
          System.out.println(testClass.getCanonicalName() + " (" + mapSize + ") = " + rr.getAggregatedResult().getPrimaryResult().getScore());
          Map<Integer, String> forClass = results.get(testClass.getCanonicalName());
          if (forClass == null)
            results.put(testClass.getCanonicalName(), forClass = new HashMap<>(4));
          forClass.put(mapSize, Integer.toString((int) rr.getAggregatedResult().getPrimaryResult().getScore()));
        }
        if (res.isEmpty()) {
          Map<Integer, String> forClass = results.get(testClass.getCanonicalName());
          if (forClass == null)
            results.put(testClass.getCanonicalName(), forClass = new HashMap<>(4));
          forClass.put(mapSize, "-1");
        }
      }
    }
    final String res = formatResults(results, MAP_SIZES, tests);
    System.out.println("Results for test type = " + testSetName + ":\n" + res);

    return res;
  }

  private static String formatResults(final Map<String, Map<Integer, String>> results, final int[] mapSizes, final List<Class> tests) {
    final StringBuilder sb = new StringBuilder(2048);
    //format results
    //first line - map sizes, should be sorted
    for (final int size : mapSizes)
      sb.append(",").append(size);
    sb.append("\n");
    //following lines - tests in the definition order
    for (final Class test : tests) {
      final Map<Integer, String> res = results.get(test.getCanonicalName());
      sb.append(test.getName());
      for (final int size : mapSizes)
        sb.append(",\"").append(res.get(size)).append("\"");
      sb.append("\n");
    }
    return sb.toString();
  }

  @Setup
  public void setup() {
    try {
      final ITestSet testSet = (ITestSet) Class.forName(m_className).newInstance();
      switch (m_testType) {
        case "get":
          m_impl = testSet.getTest();
          break;
        case "put":
          m_impl = testSet.putTest();
          break;
        case "remove":
          m_impl = testSet.removeTest();
          break;
      }
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    //share the same keys for all tests with the same map size
    m_impl.setup(KeyGenerator.getKeys(m_mapSize), FILL_FACTOR, ONE_FAIL_OUT_OF);
  }

  @Benchmark
  public int testRandom() {
    return m_impl.test();
  }

  public static class KeyGenerator {
    public static int s_mapSize;
    public static int[] s_keys;

    public static int[] getKeys(final int mapSize) {
      if (mapSize == s_mapSize)
        return s_keys;
      s_mapSize = mapSize;
      s_keys = null; //should be done separately so we don't keep 2 arrays in memory
      s_keys = new int[mapSize];
      final Random r = new Random(1234);
      for (int i = 0; i < mapSize; ++i)
        s_keys[i] = r.nextInt();
      return s_keys;
    }
  }


}