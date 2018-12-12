# String benchmark
Yet another benchmark to test the String concatenation with: 

- `StringBuilder`
- `StringBuffer`
- `.contat()` method
- `+` operator
- `String.format()` method

The benchmark has been executed on my Apple iMac with macOS Mojave (10.14), 3.5 GHz Intel Core i7 and 16 GB 1600 MHz DDR3.

## Result

| Java Version  |`StringBuffer` |`StringBuilder`|`.concat()`    |`+`            |`String.format()`| 
| ------------- |--------------:|--------------:|--------------:|--------------:|----------------:|      
| Java 6        | 11.873        | 12.276        | 11.327        | 11.825        | 0.861           |
| Java 7        | 10.964        | 13.177        | 11.846        | 21.639        | 1.054           |
| Java 8        | 12.150        | 12.651        | 12.271        | 20.198        | 1.033           |
| Java 9        | 12.349        | 13.894        | 14.257        | 18.554        | 1.320           |
| Java 10       | 12.231        | 13.031        | 13.690        | 18.450        | 1.223           |


I'm measuring the operations per time (**ops/time**), where one operation consists of 1000 executions of the test method.
All jdk from Oracle btw...

Here you see more in detail the output: 

```text
# JMH version: 1.21
# VM version: JDK 1.8.0_192, Java HotSpot(TM) 64-Bit Server VM, 25.192-b12
# VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home/jre/bin/java
# VM options: -server
# Warmup: 5 iterations, 10 s each, 1000 calls per op
# Measurement: 10 iterations, 10 s each, 1000 calls per op
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: ch.erard22.benchmark.string.StringBenchmark.stringBuffer

#...

Benchmark                       Mode  Cnt   Score   Error   Units
StringBenchmark.stringBuffer   thrpt   10  12.150 ± 0.100  ops/ms
StringBenchmark.stringBuilder  thrpt   10  12.651 ± 0.085  ops/ms
StringBenchmark.stringConcat   thrpt   10  12.271 ± 0.060  ops/ms
StringBenchmark.stringFormat   thrpt   10   1.033 ± 0.091  ops/ms
StringBenchmark.stringPlus     thrpt   10  20.198 ± 0.349  ops/ms

```


https://dzone.com/articles/string-concatenation-performacne-improvement-in-ja
http://www.pellegrino.link/2015/08/22/string-concatenation-with-java-8.html
