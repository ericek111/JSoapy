# JSoapy

Java bindings for [SoapySDR](https://github.com/pothosware/SoapySDR), a popular vendor and platform neutral SDR support library.

Partially auto-generated, but mostly hand-crafted to present all original functionality in a more familiar way without sacrificing (too much) performance.
We're calling Soapy's ABI through its exported C symbols using the new FFI from Project Panama. Since SoapySDR is a C++ library, 
strings and structs are copied back and forth to/from their native C++ equivalents.

The API is complete and I'm pretty happy with it, but this projct is still WIP and subject to refactoring.

## Tested features:
 - Enumerating devices and their features. See `Probe.java` -- its output should be identical to `SoapySDRUtil --probe` (except doubles are printed in the Java way).
 - Constructing devices by a string of arguments.
 - Reading I/Q, both using readStream and through the DMA API.
 - Reading device sensors.

## Conventions

Despite this project using the C API, the naming scheme follows the C++ API through the use of overloaded methods. I'm still split on whether this is the right way and it's possible that in some places, I've used the C name by accident. 