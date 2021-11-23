# Dependency Injection Example

The purpose of this repository is to show dependency injection in an uncomplicated way.

The example code has been stripped down to the barest essentials for clarity.

## Layout

### DepInjEx

This project contains two examples, the first `ClassicBOMCalculator.java` does _not_ use DI.

The second, `BOMCalculator.java` contains an implementation using DI.

### TestDepInjEx

This project contains two examples, the first `TestClassicTotalCostCalculation.java` contains a traditional integration test that you might find a project without DI.

The second `TestTotalCostCalculation.java` contains tests that leverage DI to exercise different aspects of the code without a real database present. Because these tests do _not_ require a database they execute very quickly and do not require a cleanup step.

## Points of Interest (ClassicBOMCalculator)

1) `ClassicBOMCalculator` creates an  instance of `BOMFetcher` inside the method `TotalCost`. This means any test code is required to have configured data in the database. Since clean tests remove their test data, this also means tests will have to remove their test data. Because of the code structure, there are no reasonable options to get around this. Therefore, the tests will be slow and brittle.

2) `BOMFetcher` inside of `ClassicBOMCalculator.java` opens a new connection to the database on each invocation, if there were multiple concurrent executions of `ClassicBOMCalculator` we would create numerous new database connections. Therefore, conccurrent test execution could be very stressful on system resources.

## Points of Interest (BOMCalculator)

1) `BOMLineItem` matches `LineItem`, as does `BOMFetcher` and `BomFetcher`; the interfaces did not change, only the manner in which the implementations are structured.

2) `BOMFetcher` is injected into `BOMCalculator` through the constructor. This allows us to use a test-double.

3) Because `BOMCalculator` allows us to inject a `BOMFetcher` we can create tests easily using a test-double. This allows us to quickly test all possible scenarios.
