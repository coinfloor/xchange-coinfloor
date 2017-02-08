# xchange-coinfloor

This module adapts Coinfloor's [Java client library][java-library] to implement the [XChange][] API.

[java-library]: https://github.com/coinfloor/java-library
[XChange]: http://knowm.org/open-source/xchange/

## Building

This project is built with [Maven][]. It depends on the [Coinfloor Java client library][java-library], which you must fetch and install first.

```
$ git clone -b v1.0 https://github.com/coinfloor/java-library.git coinfloor-library
$ cd coinfloor-library
$ mvn install
$ cd ..
$ git clone https://github.com/coinfloor/xchange-coinfloor.git
$ cd xchange-coinfloor
$ mvn package
```

[Maven]: https://maven.apache.org/

## Example usage

See [Example.java][] for an example of how to use this module.

[Example.java]: https://github.com/coinfloor/xchange-coinfloor/blob/master/src/test/java/uk/co/coinfloor/xchange/Example.java