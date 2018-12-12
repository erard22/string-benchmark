# String benchmark
Yet another benchmark to test the String concatenation.
The benchmark has been executed on my Apple iMac with macOS Mojave (10.14), 3.5 GHz Intel Core i7 and 16 GB 1600 MHz DDR3.

## First test - Simple concatenation

Here we simply measure the costs of a simple concatenation of three different `Strings`. More or less: 

`"A" + "B" + "C"`

Where the `Strings` are randomly generated before each execution to prevent the JVM of doing some optimizations. We simply measure the costs of the operation itself.

We tested the following ways to concatenate the random strings: 

- `StringBuilder` class
- `StringBuffer` class
- `.contat()` method
- `+` operator
- `String.format()` method

Where the last method just was use to show how bad it is.

| Java Version  |`StringBuffer` |`StringBuilder`|`.concat()`    |`+`            |`String.format()`| 
| ------------- |--------------:|--------------:|--------------:|--------------:|----------------:|      
| Java 6        | 11.873        | 12.276        | 11.327        | 11.825        | 0.861           |
| Java 7        | 10.964        | 13.177        | 11.846        | 21.639        | 1.054           |
| Java 8        | 12.150        | 12.651        | 12.271        | 20.198        | 1.033           |
| Java 9        | 12.349        | 13.894        | 14.257        | 18.554        | 1.320           |
| Java 10       | 12.231        | 13.031        | 13.690        | 18.450        | 1.223           |
| Java 11       | 12.205        | 12.521        | 13.621        | 18.622        | 1.306           |
| Java 12       | 12.631        | 13.981        | 14.032        | 18.999        | 1.180           |

I'm measuring the operations per time (**ops/time**), where one operation consists of 1000 executions of the test method.
All jdk from Oracle btw... Except java 12, that was an openjdk preview.

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

### Conclusion

If you concatenate only a few strings, the fastest way to do so is to use the `+` operator. But where comes the brake?
Because there is a reason why we say in every code review: "Please use `StringBuilder` here." But yeah, we shouldn't if 
someone is concatenating just 2-3 strings.

And besides this, you see, java is getting slightly faster with every version. Not a lot and not for every test, 
but overall it's true.

## Second test - In a loop

For the second benchmark I changed the behaviour. Now I'm concatenating the strings with every run. Means the string is
getting longer and longer, as it would be in a loop.

`"A" + "B" + "C" + "D" + "E" + ...`

I removed the concatenation with `String.format()` because it does not make any sense in this scenario (and in others).

- `StringBuilder` class
- `StringBuffer` class
- `.contat()` method
- `+` operator
- ~~`String.format()` method~~

| Java Version  |`StringBuffer` |`StringBuilder`|`.concat()`    |`+`            |
| ------------- |--------------:|--------------:|--------------:|--------------:|  
| Java 6        | 18.864        | 19.150        | 19.101        | 0.003         | 


https://dzone.com/articles/string-concatenation-performacne-improvement-in-ja
http://www.pellegrino.link/2015/08/22/string-concatenation-with-java-8.html
