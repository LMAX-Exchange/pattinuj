## Pattinuj

Pattinuj is three components to aid TAP integration.

0. `TapFormatter`

Takes a list of `TapResult`s and outputs those results formatted as TAP.

1. `TapListener`

A JUnit `RunListener` which collects `TapResult`s and writes them to a provided `TapFormatter` at the end of the run.

2. `JunitTapCommandLineRunner`

A command-line runner set up with a `TapListener`.