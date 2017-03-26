# utils-assertor

[![Build Status](https://api.travis-ci.org/Gilandel/utils-assertor.svg?branch=master)](https://travis-ci.org/Gilandel/utils-assertor/builds)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/778ffc15500647ce9d54b74fba878c8e)](https://www.codacy.com/app/gilles/utils-assertor)
[![Dependency Status](https://www.versioneye.com/user/projects/58b29b6f7b9e15003a17e544/badge.svg?style=flat)](https://www.versioneye.com/user/projects/58b29b6f7b9e15003a17e544)
[![codecov.io](https://codecov.io/github/Gilandel/utils-assertor/coverage.svg?branch=master)](https://codecov.io/github/Gilandel/utils-assertor?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-assertor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-assertor)
[![Javadocs](http://www.javadoc.io/badge/fr.landel.utils/utils-assertor.svg)](http://www.javadoc.io/doc/fr.landel.utils/utils-assertor)

[![Tokei LoC](https://tokei.rs/b1/github/Gilandel/utils-assertor)](https://github.com/Aaronepower/tokei)
[![Tokei NoFiles](https://tokei.rs/b1/github/Gilandel/utils-assertor?category=files)](https://github.com/Aaronepower/tokei)
[![Tokei LoComments](https://tokei.rs/b1/github/Gilandel/utils-assertor?category=comments)](https://github.com/Aaronepower/tokei)

[![codecov.io tree](https://codecov.io/gh/Gilandel/utils-assertor/branch/master/graphs/tree.svg)](https://codecov.io/gh/Gilandel/utils-assertor/branch/master)
[![codecov.io sunburst](https://codecov.io/gh/Gilandel/utils-assertor/branch/master/graphs/sunburst.svg)](https://codecov.io/gh/Gilandel/utils-assertor/branch/master)

Work progress:
![Code status](http://vbc3.com/script/progressbar.php?text=Code&progress=100)
![Test status](http://vbc3.com/script/progressbar.php?text=Test&progress=100)
![Benchmark status](http://vbc3.com/script/progressbar.php?text=Benchmark&progress=100)
![JavaDoc status](http://vbc3.com/script/progressbar.php?text=JavaDoc&progress=100)

```xml
<dependency>
	<groupId>fr.landel.utils</groupId>
	<artifactId>utils-assertor</artifactId>
	<version>1.0.2</version>
</dependency>
```

## Summary

1. [Summary](#summary)
1. [Description](#description)
   1. [Structure](#structure)
   1. [Message (locale, arguments and parameters)](#message-locale-arguments-and-parameters)
   1. [Behind the scene (how it works?)](#behind-the-scene-how-it-works)
1. [Output details](#output-details)
   1. [orElseThrow](#orelsethrow)
   1. [isOK](#isok)
   1. [getErrors](#geterrors)
   1. [get](#get)
   1. [getNullable](#getnullable)
   1. [asResult](#asresult)
   1. [asDefault](#asdefault)
1. [Operators](#operators)
   1. [NOT](#not)
   1. [AND](#and)
   1. [OR](#or)
   1. [XOR](#xor)
   1. [NAND](#nand)
   1. [NOR](#nor)
1. [Available methods](#available-methods)
   1. [For all (Object, Boolean...)](#for-all-object-boolean)
      1. [isNull](#isnull)
      1. [isNotNull](#isnotnull)
      1. [isEqual](#isequal)
      1. [isNotEqual](#isnotequal)
      1. [isInstance](#isinstance)
      1. [isAssignableFrom](#isassignablefrom)
      1. [hasHashCode](#hashashcode)
      1. [validates](#validates)
   1. [Array](#array)
      1. [hasLength](#haslength)
      1. [isEmpty](#isempty)
      1. [isNotEmpty](#isnotempty)
      1. [contains](#contains)
      1. [containsAll](#containsall)
      1. [containsAny](#containsany)
   1. [Boolean](#boolean)
      1. [isTrue](#istrue)
      1. [isFalse](#isfalse)
   1. [CharSequence](#charsequence)
      1. [hasLength](#haslength)
      1. [isEmpty](#isempty-1)
      1. [isNotEmpty](#isnotempty-1)
      1. [isBlank](#isblank)
      1. [isNotBlank](#isnotblank)
      1. [isEqual](#isequal-1)
      1. [isNotEqual](#isnotequal-1)
      1. [isEqualIgnoreCase](#isequalignorecase)
      1. [isNotEqualIgnoreCase](#isnotequalignorecase)
      1. [isEqualIgnoreLineReturns](#isequalignorelinereturns)
      1. [isNotEqualIgnoreLineReturns](#isnotequalignorelinereturns)
      1. [isEqualIgnoreCaseAndLineReturns](#isequalignorecaseandlinereturns)
      1. [isNotEqualIgnoreCaseAndLineReturns](#isnotequalignorecaseandlinereturns)
   1. [contains](#contains-1)
      1. [startsWith](#startswith)
      1. [startsWithIgnoreCase](#startswithignorecase)
      1. [endsWith](#endswith)
      1. [endsWithIgnoreCase](#endswithignorecase)
      1. [matches](#matches)
      1. [find](#find)
   1. [Class](#class)
      1. [isAssignableFrom](#isassignablefrom-1)
      1. [hasName](#hasname)
      1. [hasSimpleName](#hassimplename)
      1. [hasCanonicalName](#hascanonicalname)
      1. [hasPackageName](#haspackagename)
      1. [hasTypeName](#hastypename)
   1. [Date & Calendar](#date-calendar)
      1. [isEqual](#isequal-2)
      1. [isNotEqual](#isnotequal-2)
      1. [isAround](#isaround)
      1. [isNotAround](#isnotaround)
      1. [isAfter](#isafter)
      1. [isAfterOrEqual](#isafterorequal)
      1. [isBefore](#isbefore)
      1. [isBeforeOrEqual](#isbeforeorequal)
   1. [Temporal](#temporal)
      1. [isEqual](#isequal-3)
      1. [isNotEqual](#isnotequal-3)
      1. [isAround](#isaround-1)
      1. [isNotAround](#isnotaround-1)
      1. [isAfter](#isafter-1)
      1. [isAfterOrEqual](#isafterorequal-1)
      1. [isBefore](#isbefore-1)
      1. [isBeforeOrEqual](#isbeforeorequal-1)
   1. [Enum](#enum)
      1. [hasName](#hasname-1)
      1. [hasNameIgnoreCase](#hasnameignorecase)
      1. [hasOrdinal](#hasordinal)
   1. [Iterable](#iterable)
      1. [hasSize](#hassize)
      1. [isEmpty](#isempty-1)
      1. [isNotEmpty](#isnotempty-1)
      1. [contains](#contains-2)
      1. [containsAll](#containsall-1)
      1. [containsAny](#containsany-1)
   1. [Map](#map)
      1. [hasSize](#hassize-1)
      1. [isEmpty](#isempty-2)
      1. [isNotEmpty](#isnotempty-2)
      1. [contains](#contains-3)
      1. [containsAll](#containsall-2)
      1. [containsAny](#containsany-2)
   1. [Number](#number)
      1. [isEqual](#isequal-4)
      1. [isNotEqual](#isnotequal-4)
      1. [isZero](#iszero)
      1. [isPositive](#ispositive)
      1. [isNegative](#isnegative)
      1. [isGT](#isgt)
      1. [isGTE](#isgte)
      1. [isLT](#islt)
      1. [isLTE](#islte)
   1. [Throwable](#throwable)
      1. [isAssignableFrom](#isassignablefrom-2)
      1. [isInstanceOf](#isinstanceof-1)
      1. [hasCauseNull](#hascausenull)
      1. [hasCauseNotNull](#hascausenotnull)
      1. [hasCauseAssignableFrom](#hascauseassignablefrom)
      1. [hasCauseInstanceOf](#hascauseinstanceof)
1. [TODO](#todo)
1. [License](#license)

## Description

This module allows to assert parameters.

For now it manages:
- Object (all other objects)
- Array
- Boolean
- CharSequence (String, StringBuilder...)
- Class
- Date & Calendar
- Temporal (LocalDateTime...)
- Enum
- Iterable (Set, List...)
- Map
- Number (Byte, Short, Integer, Long, Float, Double, BigInteger, BigDecimal)
- Throwable (Exception...)

### Structure

All assertions start with  `Assertor.that(object)` and following the type of the object, some methods are available.

About structure, an assertion can be cut in three parts:
- The definition of what we check: `Assertor.that(myObject))...`
- The check: `...isNull().or().isInstance(Color.class)...`
- The output: `...orElseThrow()`

Multiples objects can be check in the same line:
```java
Assertor.that(object1).isNull().and(object2).isNotNull().orElseThrow();
Assertor.that(object1).isNull().or().not().isInstance(Color.class).or(object2).isEqual(object3).isOk();
```

Multiple outputs are available:
- orElseThrow: throws an exception if assertion is false, otherwise returns the last checked parameter,
- isOK: get the boolean result of the assertion,
- getErrors: get the error message (java.util.Optional),
- get: the result (java.util.Optional),
- getNullable: the result,
- asResult: the result (fr.landel.utils.commons.Result),
- asDefault: the result (fr.landel.utils.commons.Default).

These outputs methods are considerate as final.

### Message (locale, arguments and parameters)

In each method, that manages intermediate errors (isBlank, contains...) or final errors (orElseThrow...), a locale can be specified.
The locale can be used to manage number and date (see [String.format](http://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html)).

Disambiguation:
- Parameters means: all objects used to check the variables.  
`Assertor.that(variable1).contains(parameter1).and(variable2).hasName(parameter2).orElseThrow()`
- Arguments means: the message arguments.  
`Assertor.that(variable1).contains(parameter1, myErrorMessage, argument1, argument2).orElseThrow()`

```java
String text = "text";
// ...
Assertor.that(text).hasLength(5, "Bad length: '%1$d', expected: '%2$d*', text: '%1$s*'", text.length()).getErrors();
// "text" is the first parameter
// 5 is the second parameter
// 4 is the first argument
// Message thrown -> "Bad length '4', expected: '5', text: 'text'"
```

As the previous example demonstrates it, in message, variables, parameters and arguments can be injected. The syntax is exactly the same as default [String.format](http://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html) arguments, just suffix it by the character asterisk/star '*' to inject a parameter/variable.

The message builder works in two different ways, following the context:
- in intermediate methods (ex: `.isBlank()`), only current checked parameter and the method variables can be injected,
- in final methods (ex: `.orElseThrow()`), all parameters and variables can be injected.

```java
Assertor.that(Math.PI).isLT(1d, Locale.US, "'%.3f*' isn't lower than '%f*'")
	.or(Math.E).isLT(2d, Locale.FRANCE, "'%.3f*' isn't lower than '%f*'")
	.orElseThrow();
	
// same with indexes

Assertor.that(Math.PI).isLT(1d, Locale.US, "'%1$.3f*' isn't lower than '%2$f*'")
	.or(Math.E).isLT(2d, Locale.FRANCE, "'%1$.3f*' isn't lower than '%2$f*'")
	.orElseThrow();

// -> throws "'3.142' isn't lower than '1.000000' OR '2,718' isn't lower than '2,000000'"
// first error use Locale.US and second one Locale.FRANCE
// the second error can see current variale (Math.E) as first injectable and the method parameter (2d) as second injectable 
// but can't see the first variable (Math.PI) and first method parameter (1d)

Assertor.that(Math.PI).isLT(1d)
	.or(Math.E).isLT(2d)
	.orElseThrow(Locale.FRANCE, "'%.3f*' isn't lower than '%f*' OR '%.3f*' isn't lower than '%f*'");
	
// same with indexes

Assertor.that(Math.PI).isLT(1d)
	.or(Math.E).isLT(2d)
	.orElseThrow(Locale.FRANCE, "'%1$.3f*' isn't lower than '%2$f*' OR '%3$.3f*' isn't lower than '%4$f*'");

// -> throws "'3,142' isn't lower than '1,000000' OR '2,718' isn't lower than '2,000000'"
// final methods can see all variables (Math.PI, Math.E) and parameters (1d, 2d)
```

### Behind the scene (how it works?)

The Assertor has two types of steps:
- Check step: where variables are checked,
- Intermediate step: where operator or final method can be called.

Each step generates a lambda expression, that is sent to the next one (steps are saved in StepAssertor object).

This chain can be represented like this:  
`(init) -> (check) -> (intermediate) -> ... -> (check) -> (intermediate) -> (check) -> (end)`

The 'init' step always start with `Assertor.that()`. The 'end' step has multiple outputs (see [Output details](#output-details)).

As always, there is an exception, two intermediate steps can be side by side if a `not()` is applied.

At the end each steps (StepAssertor) are checked and combined (before this final step, nothing is done, except the appending).
 
If the step has preconditions, they are validated, if they are OK, checks are done, otherwise the chain combining is stopped.  
`Assertor.that("text").contains("t").orElseThrow();`  
For example, `contains("t")` requires that "text" is not null and "t" is not null and not empty.  
If preconditions are valid, it searches if "text" contains "t".

If a precondition doesn't match, all the chain is considered in error.  
This can be avoided; here 'contains' preconditions never failed:  
`Assertor.that(text).isEmpty().or().contains(word).orElseThrow();`  
`Assertor.that(text).isNotEmpty().and().contains(word).orElseThrow();`  

The combining function tries to avoid unnecessary checks, analysis is prematurely stopped in these cases:
- if first check is 'true' followed by the operator 'OR' -> OK
- if first check is 'false' followed by the operator 'NOR' -> OK
- if first check is 'false' followed by the operator 'AND' -> KO
- if first check is 'true' followed by the operator 'NAND' -> KO

Messages are only generated when it's required, if assertion is in error:
- `orElseThrow()` -> generates the error message and throws it
- `orElseThrow(CharSequence message, Object... arguments)` -> doesn't generate the error message, throws the provided one
- `orElseThrow(Locale locale, CharSequence message, Object... arguments)` -> doesn't generate the error message, throws the provided one
- `orElseThrow(Supplier<E> exceptionSupplier)` -> doesn't generate the error message, throws the provided one
- `orElseThrow(E exception)` -> doesn't generate the error message, throws the provided one
- `orElseThrow(E exception, boolean injectSuppressed)` -> generates the error message if 'injectSuppressed' is set to 'true'
- `orElseThrow(BiFunction<CharSequence, Object[], E> exceptionBuilder)` -> generates the error message and inject it in the provided lambda
- `isOK()` -> doesn't generate the error message, returns 'false'
- `getErrors()` -> generates the error message
- `get()` -> doesn't generate the error message, returns the checked variable as an 'Optional'
- `getNullable()` -> doesn't generate the error message, returns the checked variable
- `asResult()` -> doesn't generate the error message, returns the checked variable as a 'Result'
- `asDefault(T defaultValue)` -> doesn't generate the error message, returns the checked variable as a 'Default'

For now, each message is formatted independently to respect 'Locale'.

## Output details

### orElseThrow
Throws an exception if the assertion is false, otherwise returns the last checked value.
Multiple ways to personalize the exception are provided:
- a message:
	The message can be personalized via arguments injection and locale.
	Back-side the method String.format will be called with these arguments.
	Parameters can also be injected.
- an exception:
	An exception can be also used (default: IllegalArgumentException).
- a supplier:
	An exception supplier
- a function:
	A bi-function (a function with two parameters) can be passed.
	This function is only called if statement is false.
	The two parameters received are the combined errors messages and all the parameters.

* Signatures:
	- `orElseThrow()`
	- `orElseThrow(CharSequence message, Object... arguments)`
	- `orElseThrow(Locale locale, CharSequence message, Object... arguments)`
	- `orElseThrow(Supplier<E> exceptionSupplier)`
	- `orElseThrow(E exception)` // not supported by JDK 1.8.0_121 [Issue JDK-8175535](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8175535)
 	- `orElseThrow(E exception, boolean injectSuppressed)` // if true the internal error is generated and added as suppressed to the given exception
	- `orElseThrow(BiFunction<CharSequence, Object[], E> exceptionBuilder)`
		- 1st bi-function argument: the default error message,
		- 2nd bi-function argument: the list of parameters and variables used (for each, returns: the object, its type (if recognized) and if it's checked (true = parameter, false = variable))

* Examples:
```java
Assertor.that("text").isNotBlank().orElseThrow(); // -> returns "text" instance
Assertor.that("text").isNotBlank().or(12).isGT(11).orElseThrow(); // -> returns 12

Assertor.that("").isNotBlank().orElseThrow(); // -> throws the default message 'the char sequence should be NOT null, NOT empty and NOT blank'
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow(); // -> throws the personalized message 'The first name is invalid'

Assertor.that("").isNotBlank().orElseThrow("Invalid field"); // -> throws the personalized message 'Invalid field'
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow("Invalid field"); // -> throws the personalized message 'Invalid field'

Assertor.that("").isNotBlank().orElseThrow(Locale.FRANCE, "Invalid field (%.2fms)", 2.356); // -> throws the personalized message 'Invalid field (2,36ms)'
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow(Locale.FRANCE, "Invalid field (%.2fms)", 2.356); // -> throws the personalized message 'Invalid field (2,36ms)'

Assertor.that("").isNotBlank().orElseThrow(new IOException("Invalid data"), false); // -> throws the personalized exception
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow(new IOException("Invalid data"), true); // -> throws the personalized exception

Assertor.that("").isNotBlank().orElseThrow(() -> new IOException("Invalid data")); // -> throws the personalized exception
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow(IOException::new); // -> throws the personalized exception

Assertor.that("text").isBlank().orElseThrow((errors, parameters) -> new MyException("text should be blank")); // -> throws a MyException with message: text should be blank
// 'errors' contains: the char sequence 'text' should be null, empty or blank
// 'parameters' contains: [{"text", EnumType.CHAR_SEQUENCE}]

Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank().orElseThrow((errors, parameters) -> new MyException(errors)); // -> throws a MyException
// 'errors' contains: the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte12' should NOT start with 'text'" OR the char sequence 'texte12' should be null, empty or blank
// 'parameters' contains: [{"texte11", EnumType.CHAR_SEQUENCE}, {"texte12", EnumType.CHAR_SEQUENCE}, {"text", EnumType.CHAR_SEQUENCE}]
// to display the first parameter in MyException call: parameters.get(0).getKey()

```

### isOK
This method returns 'true' if the assertion is valid, otherwise returns 'false'.

* Signatures:
	- `isOK()`

* Examples:
```java
Assertor.that("").isNotBlank().isOK(); // -> return false
Assertor.that("").isBlank("The first name is invalid").isOK(); // -> return true
```

### getErrors
This method returns the assertion errors.

* Signatures:
	- `getErrors()`

* Examples:
```java
Assertor.that("").isNotBlank().getErrors(); // -> return Optional.of("the char sequence should be NOT null, NOT empty and NOT blank")
Assertor.that("").isBlank("The first name is invalid").getErrors(); // -> return Optional.empty()
```

### get
To get the last checked object as an Optional (java.util.Optional).

* Signatures:
	- `get()`

* Examples:
```java
Assertor.that(object).isNotNull().get() // => returns an Optional.ofNullable(object) if matches or Optional.empty()
Assertor.that(object1).isNotNull().and(object2).isInstanceOf(MyClass.class).get() // => returns an Optional.ofNullable(object2) if matches or Optional.empty()
```

### getNullable
To get the last checked object.

* Signatures:
	- `getNullable()`

* Examples:
```java
Assertor.that(object).isNotNull().getNullable() // => returns 'object' if matches or 'null'
Assertor.that(object1).isNotNull().and(object2).isInstanceOf(MyClass.class).getNullable() // => returns 'object2' if matches or 'null'
```

### asResult
To get the last checked object as a Result (fr.landel.utils.commons.Result).
The aim is to differentiate if assertor returns null, if it's valid or not.

* Signatures:
	- `asResult()`

* Examples:
```java
Assertor.that(object).isNull().asResult() // => returns an Result.ofNullable(object) if matches or Result.empty()
// if object is null:
// => returns Result.ofNullable(null):
//  - Result.ofNullable(null).isPresent() => true
//  - Result.ofNullable(null).isNull() => true
//  - Result.ofNullable(null).get() => null
// if object is not null:
// => returns Result.empty():
//  - Result.empty().isPresent() => false
//  - Result.empty().isNull() => true
//  - Result.empty().get() => throws NoSuchElementException

Assertor.that(object1).isNotNull().and(object2).isInstanceOf(MyClass.class).asResult() // => returns an Result.ofNullable(object2) if matches or Result.empty()
```

### asDefault
To get the last checked object as a Default (fr.landel.utils.commons.Default).
The aim is to prepare the default value and by the way gets in all cases a valid result.
The default value is prepared by the supplier as opposite of Optional that let the consumer to define the default value (orElse).
The default value cannot be null.

* Signatures:
	- `asDefault(T defaultValue)`

* Examples:
```java
Assertor.that("test").contains("e").asDefault("unknown") // => returns Default.ofNullable("test", "unknown") => get() = "test" 
Assertor.that("test").contains("x").asDefault("unknown") // => returns Default.empty("unknown") => get() = "unknown"

// Be aware, here in all cases the return is the default object:
Assertor.that(object).isNull().asDefault(defaultObject) // => returns an Default.ofNullable(object, defaultObject) if matches or Default.empty(defaultObject)
// if object is null:
// => returns Default.ofNullable(null, defaultObject):
//  - Default.ofNullable(null).isPresent() => true
//  - Default.ofNullable(null).get() => defaultObject
// if object is not null:
// => returns Default.empty(defaultObject):
//  - Default.empty().isPresent() => false
//  - Default.empty().get() => defaultObject

Assertor.that(object1).isNotNull().and(object2).isInstanceOf(MyClass.class).asResult() // => returns an Default.ofNullable(object2, defaultObject) if matches or Default.empty(defaultObject)
```

## Operators

### NOT
The 'not' function is here to negate the next method (can be applied on any method, prerequisites are checked any way).

* Signatures:
	- `not()`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).not().isNull().orElseThrow(); // become isNotNull
Assertor.that(strings).not().contains("text").orElseThrow(); // become does not contain
Assertor.that("text").not().hasLength(5, Locale.US, "The parameter '%s*' cannot be filled").orElseThrow(); // become has not length = 5
```

### AND
The 'and' function is here to combine the previous and the next methods with the operator 'and'.
With a parameter, 'and' creates an sub assertor for the specified parameter.

* Signatures:
	- `and()`
	- `and(Object object)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNull().and().isInstance(MyClass.class).orElseThrow(); // is null or is and instance of MyClass
Assertor.that(12).isGT(12).and("text").contains("ex").orElseThrow(); // 12 > 12 and 'text' contains 'ex'
```

### OR
The 'or' function is here to combine the previous and the next methods with the operator 'or'.
With a parameter, 'or' creates an sub assertor for the specified parameter.

* Signatures:
	- `or()`
	- `or(Object object)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNull().or().isInstance(MyClass.class).orElseThrow(); // is null or is an instance of MyClass
Assertor.that(12).isGT(12).or("text").contains("ex").orElseThrow(); // 12 > 12 or 'text' contains 'ex'
```

### XOR
The 'xor' function is here to combine the previous and the next methods with the operator 'xor'.
With a parameter, 'xor' creates an sub assertor for the specified parameter.

* Signatures:
	- `xor()`
	- `xor(Object object)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNull().xor().isInstance(MyClass.class).orElseThrow(); // is null xor is an instance of MyClass
Assertor.that(12).iGT(12).xor("text").contains("ex").orElseThrow(); // 12 > 12 xor 'text' contains 'ex'
```

### NAND
The 'nand' function is here to combine the previous and the next methods with the operator 'nand'.
With a parameter, 'nand' creates an sub assertor for the specified parameter.

* Signatures:
	- `nand()`
	- `nand(Object object)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNull().nand().isInstance(MyClass.class).orElseThrow();
// is null nand is an instance of MyClass
// !(object is null) && !(instanceOf MyClass)
// Equivalent:
Assertor.that(object).isNotNull().and().not().isInstance(MyClass.class).orElseThrow();

Assertor.that(12).isGT(12).nand("text").contains("ex").orElseThrow();
// 12 > 12 nand 'text' contains 'ex'
// !(12 > 12) && !('text' contains 'ex')
// Equivalent:
Assertor.that(12).isLTE(12).and().not().contains("ex").orElseThrow();
```

### NOR
The 'nor' function is here to combine the previous and the next methods with the operator 'nor'.
With a parameter, 'nor' creates an sub assertor for the specified parameter.

* Signatures:
	- `nor()`
	- `nor(Object object)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNull().nor().isInstance(MyClass.class).orElseThrow();
// is null nor is an instance of MyClass
// !(object is null) || !(instanceOf MyClass)
// Equivalent:
Assertor.that(object).isNotNull().or().not().isInstance(MyClass.class).orElseThrow();

Assertor.that(12).isGT(12).nor("text").contains("ex").orElseThrow();
// 12 > 12 nor 'text' contains 'ex'
// !(12 > 12) || !('text' contains 'ex')
// Equivalent:
Assertor.that(12).isLTE(12).or().not().contains("ex").orElseThrow();
```

## Available methods

### For all (Object, Boolean...)

#### isNull
Assert that the object is null.

* Signatures:
	- `isNull()`
	- `isNull(CharSequence message, Object[] arguments)`
	- `isNull(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNull().orElseThrow();
Assertor.that(object).isNull("Cannot be filled").orElseThrow();
Assertor.that(object).isNull(Locale.US, "The parameter '%s*' cannot be filled").orElseThrow();
```

#### isNotNull
Assert that the object is NOT null.

* Signatures:
	- `isNotNull()`
	- `isNotNull(CharSequence message, Object[] arguments)`
	- `isNotNull(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNotNull().orElseThrow();
Assertor.that(name).isNotNull("Name cannot be null").orElseThrow();
```

#### isEqual
Assert that the object is equal to another.

* Signatures:
	- `isEqual(Object object)`
	- `isEqual(Object object, CharSequence message, Object[] arguments)`
	- `isEqual(Object object, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isEqual(object2).orElseThrow();
Assertor.that(name).isEqual(object2, "Name '%s*' is not equal to '%s*'").orElseThrow();
```

* Info
This method is override by each other object types if necessary (like Date, Number).

#### isNotEqual
Assert that the object is NOT equal to another.

* Signatures:
	- `isNotEqual(Object object)`
	- `isNotEqual(Object object, CharSequence message, Object[] arguments)`
	- `isNotEqual(Object object, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNotEqual(object2).orElseThrow();
Assertor.that(name).isNotEqual(object2, "Name '%s*' already exists").orElseThrow();
```

* Info
This method is override by each other object types if necessary (like Date, Number).

#### isInstance
Assert that the object is an instance of the specified class.

* Signatures:
	- `isInstance(Class class)`
	- `isInstance(Class class, CharSequence message, Object[] arguments)`
	- `isInstance(Class class, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- checked object NOT null
	- class NOT null

* Examples:
```java
Assertor.that(object).isInstance(class1).orElseThrow();
Assertor.that(object).isInstance(class1, "Input is not an instance of the class '%2$s*'").orElseThrow();

// prerequisite errors
Assertor.that(null).isInstance(class1).orElseThrow(); // -> throws an exception
Assertor.that(object).isInstance(null).orElseThrow(); // -> throws an exception
Assertor.that(null).not().isInstance(class1).orElseThrow(); // -> throws an exception
Assertor.that(object).not().isInstance(null).orElseThrow(); // -> throws an exception
```

#### isAssignableFrom
Assert that the object is assignable from the specified class.

* Signatures:
	- `isAssignableFrom(Class class)`
	- `isAssignableFrom(Class class, CharSequence message, Object[] arguments)`
	- `isAssignableFrom(Class class, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- checked object NOT null
	- class NOT null

* Examples:
```java
Assertor.that(object).isAssignableFrom(class1).orElseThrow();
Assertor.that(object).isAssignableFrom(class1, "Input is not assignable from the class '%2$s*'").orElseThrow();

// prerequisite errors
Assertor.that(null).isAssignableFrom(class1).orElseThrow(); // -> throws an exception
Assertor.that(object).isAssignableFrom(null).orElseThrow(); // -> throws an exception
Assertor.that(null).not().isAssignableFrom(class1).orElseThrow(); // -> throws an exception
Assertor.that(object).not().isAssignableFrom(null).orElseThrow(); // -> throws an exception
```

#### hasHashCode
Assert that the object hash code equals the specified value.

* Signatures:
	- `matches(int hashCode)`
	- `matches(int hashCode, CharSequence message, Object[] arguments)`
	- `matches(int hashCode, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that(colors).hasHashCode(0).orElseThrow();
Assertor.that(colors).hasHashCode(45, "The hash codes don't match (%d != %d*)", Objects.hashCode(colors)).orElseThrow();
```

#### validates
Assert that the object validates the predicate.

* Signatures:
	- `validates(PredicateThrowable<T, E> predicate)`
	- `validates(PredicateThrowable<T, E> predicate, CharSequence message, Object[] arguments)`
	- `validates(PredicateThrowable<T, E> predicate, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- predicate NOT null

* Examples:
```java
Assertor.that(object).validates((o) -> o != null).orElseThrow();
Assertor.that(object).validates((o) -> o != null, "The object is invalid").orElseThrow();
Assertor.that(object).validates((Object obj) -> {
    return obj != null;
}, Locale.US, "Object is null!!!").orElseThrow();

Assertor.that("/var/log/dev.log").validates((path) -> {
    return Paths.get(path).endsWith("dev.log");
}, Locale.US, "Path is invalid").isOK();

// prerequisite errors
Assertor.that(object).validates(null).orElseThrow(); // -> throws an exception
Assertor.that(object).not().validates(null).orElseThrow(); // -> throws an exception
```

### Array
#### hasLength
Assert that array has the specified length.

* Signatures:
	- `hasLength(int length)`
	- `hasLength(int length, CharSequence message, Object[] arguments)`
	- `hasLength(int length, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- array NOT null
	- length >= 0

* Examples:
```java
Assertor.that(new String[] {"text"}).hasLength(3).orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).hasLength(1, "Bad status").orElseThrow(); // -> OK
Assertor.that(new String[] {"text"}).not().hasLength(3).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((Object[]) null).hasLength(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).hasLength(-1, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that((Object[]) null).not().hasLength(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).not().hasLength(-1, "Bad status").orElseThrow(); // -> throws an exception
```

#### isEmpty
Assert that array is empty or null.

* Signatures:
	- `isEmpty()`
	- `isEmpty(CharSequence message, Object[] arguments)`
	- `isEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(new String[] {"text"}).isEmpty().orElseThrow(); // -> throws an exception
Assertor.that((String[]) null).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that(new String[0]).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that(new String[] {"text"}).not().isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
```

#### isNotEmpty
Assert that array is NOT empty and NOT null.

* Signatures:
	- `isNotEmpty()`
	- `isNotEmpty(CharSequence message, Object[] arguments)`
	- `isNotEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that((String[]) null).isNotEmpty().orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).isNotEmpty().orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).isNotEmpty("Param '%1$s*' empty or null").orElseThrow(); // -> OK
Assertor.that(new String[0]).not().isNotEmpty("Param '%1$s*' empty or null").orElseThrow(); // -> OK
```

#### contains
Assert that array contains the element.

* Signatures:
	- `contains(T element)`
	- `contains(T element, CharSequence message, Object[] arguments)`
	- `contains(T element, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- array NOT null and NOT empty

* Examples:
```java
Assertor.that(new String[] {"text"}).contains("text").orElseThrow(); // -> OK
Assertor.that(new String[] {null, ""}).contains(null, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String[]) null).contains("t", "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).contains(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((String[]) null).not().contains("t", "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).not().contains(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

#### containsAll
Assert that array contains all elements.

* Signatures:
	- `containsAll(T[] elements)`
	- `containsAll(T[] elements, CharSequence message, Object[] arguments)`
	- `containsAll(T[] elements, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- array NOT null and NOT empty
	- elements NOT null and NOT empty

* Examples:
```java
Assertor.that(new String[] {"text"}).containsAll(new String[] {"text"}).orElseThrow(); // -> OK
Assertor.that(new String[] {null, ""}).containsAll(new String[] {null}, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(new String[] {null, ""}).containsAll(new String[] {null, "text"}, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((String[]) null).containsAll(new String[] {"t"}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).containsAll(new String[] {null}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).containsAll(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).containsAll(new String[0], "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((String[]) null).not().containsAll(new String[] {"t"}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).not().containsAll(new String[] {null}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).not().containsAll(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).not().containsAll(new String[0], "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

#### containsAny
Assert that array contains any elements.

* Signatures:
	- `containsAny(T[] elements)`
	- `containsAny(T[] elements, CharSequence message, Object[] arguments)`
	- `containsAny(T[] elements, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- array NOT null and NOT empty
	- elements NOT null and NOT empty

* Examples:
```java
Assertor.that(new String[] {"text"}).containsAny(new String[] {"text"}).orElseThrow(); // -> OK
Assertor.that(new String[] {null, ""}).containsAny(new String[] {null}, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(new String[] {null, ""}).containsAny(new String[] {null, "text"}, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String[]) null).containsAny(new String[] {"t"}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).containsAny(new String[] {null}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).containsAny(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).containsAny(new String[0], "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((String[]) null).not().containsAny(new String[] {"t"}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[0]).not().containsAny(new String[] {null}, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).not().containsAny(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(new String[] {"text"}).not().containsAny(new String[0], "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

### Boolean
#### isTrue
Assert that the boolean is true.

* Signatures:
	- `isTrue()`
	- `isTrue(CharSequence message, Object[] arguments)`
	- `isTrue(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(bool).isTrue().orElseThrow(); // -> throws an exception, if bool == true
Assertor.that(false).isTrue("Bad status").orElseThrow(); // -> OK
Assertor.that(true).not().isTrue("Bad status").orElseThrow(); // -> OK
```

#### isFalse
Assert that the boolean is false.

* Signatures:
	- `isFalse()`
	- `isFalse(CharSequence message, Object[] arguments)`
	- `isFalse(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(bool).isFalse().orElseThrow(); // -> throws an exception, if bool == true
Assertor.that(false).isFalse("Bad status").orElseThrow(); // -> OK
Assertor.that(true).not().isFalse("Bad status").orElseThrow(); // -> OK
```

### CharSequence
#### hasLength
Assert that char sequence has the specified length.

* Signatures:
	- `hasLength(int length)`
	- `hasLength(int length, CharSequence message, Object[] arguments)`
	- `hasLength(int length, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- length >= 0

* Examples:
```java
Assertor.that("text").hasLength(3).orElseThrow(); // -> throws an exception
Assertor.that("text").hasLength(4, "Bad status").orElseThrow(); // -> OK
Assertor.that("text").not().hasLength(3).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).hasLength(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that("text").hasLength(-1, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().hasLength(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that("text").not().hasLength(-1, "Bad status").orElseThrow(); // -> throws an exception
```

#### isEmpty
Assert that char sequence is empty or null.

* Signatures:
	- `isEmpty()`
	- `isEmpty(CharSequence message, Object[] arguments)`
	- `isEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that("text").isEmpty().orElseThrow(); // -> throws an exception
Assertor.that((String) null).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that("").isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that("text").not().isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
```

#### isNotEmpty
Assert that char sequence is NOT empty and NOT null.

* Signatures:
	- `isNotEmpty()`
	- `isNotEmpty(CharSequence message, Object[] arguments)`
	- `isNotEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that((String) null).isNotEmpty().orElseThrow(); // -> throws an exception
Assertor.that("").isNotEmpty().orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEmpty("Param '%1$s*' empty or null").orElseThrow(); // -> OK
Assertor.that("").not().isNotEmpty("Param '%1$s*' empty or null").orElseThrow(); // -> OK
```

#### isBlank
Assert that char sequence is blank or empty or null.

* Signatures:
	- `isBlank()`
	- `isBlank(CharSequence message, Object[] arguments)`
	- `isBlank(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that("text").isBlank().orElseThrow(); // -> throws an exception
Assertor.that((String) null).isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("").isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("   ").isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("text").not().isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
```

#### isNotBlank
Assert that char sequence is NOT blank and NOT empty and NOT null.

* Signatures:
	- `isNotBlank()`
	- `isNotBlank(CharSequence message, Object[] arguments)`
	- `isNotBlank(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that("text").isNotBlank().orElseThrow(); // -> OK
Assertor.that("text").isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("text").isNotBlank().orElseThrow(); // -> OK
Assertor.that((String) null).isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("").isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("   ").isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### isEqual
Assert that char sequence is equal to the string

* Signatures:
	- `isEqual(CharSequence string)`
	- `isEqual(CharSequence string, CharSequence message, Object[] arguments)`
	- `isEqual(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqual("text").orElseThrow(); // -> OK
Assertor.that("text").isEqual("ex", "Param '%1$s*' not equal").orElseThrow(); -> throws an exception
Assertor.that("text").isEqual("TexT").orElseThrow(); -> throws an exception
Assertor.that("text").isEqual("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().isEqual("text").orElseThrow(); // -> throws an exception
```

#### isNotEqual
Assert that char sequence is NOT equal to the string

* Signatures:
	- `isNotEqual(CharSequence string)`
	- `isNotEqual(CharSequence string, CharSequence message, Object[] arguments)`
	- `isNotEqual(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqual("text").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqual("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqual("TexT").orElseThrow(); // -> OK
Assertor.that("text").isNotEqual("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqual("text").orElseThrow(); -> throws an exception
```

#### isEqualIgnoreCase
Assert that char sequence is equal to the string, ignoring case considerations

* Signatures:
	- `isEqualIgnoreCase(CharSequence string)`
	- `isEqualIgnoreCase(CharSequence string, CharSequence message, Object[] arguments)`
	- `isEqualIgnoreCase(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqualIgnoreCase("text").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCase("ex", "Param '%1$s*' equal").orElseThrow(); // -> throws an exception
Assertor.that("text").isEqualIgnoreCase("TexT").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCase("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().isEqualIgnoreCase("text").orElseThrow(); -> throws an exception

#### isNotEqualIgnoreCase
Assert that char sequence is NOT equal to the string, ignoring case considerations

* Signatures:
	- `isNotEqualIgnoreCase(CharSequence string)`
	- `isNotEqualIgnoreCase(CharSequence string, CharSequence message, Object[] arguments)`
	- `isNotEqualIgnoreCase(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqualIgnoreCase("text").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreCase("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreCase("TexT").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreCase("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqualIgnoreCase("text").orElseThrow(); -> OK
```

#### isEqualIgnoreLineReturns
Assert that char sequence is equal to the string, ignoring line returns considerations

* Signatures:
	- `isEqualIgnoreLineReturns(CharSequence string)`
	- `isEqualIgnoreLineReturns(CharSequence string, CharSequence message, Object[] arguments)`
	- `isEqualIgnoreLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqualIgnoreLineReturns("text").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> throws an exception
Assertor.that("text").isEqualIgnoreLineReturns("tex\nt").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreLineReturns("Tex\nT").orElseThrow(); // -> throws an exception
Assertor.that("text").isEqualIgnoreLineReturns("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().isEqualIgnoreLineReturns("text").orElseThrow(); -> throws an exception
```

#### isNotEqualIgnoreLineReturns
Assert that char sequence is NOT equal to the string, ignoring line returns considerations

* Signatures:
	- `isNotEqualIgnoreLineReturns(CharSequence string)`
	- `isNotEqualIgnoreLineReturns(CharSequence string, CharSequence message, Object[] arguments)`
	- `isNotEqualIgnoreLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqualIgnoreLineReturns("text").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreLineReturns("tex\nt").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreLineReturns("Tex\nT").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreLineReturns("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqualIgnoreLineReturns("text").orElseThrow(); -> OK
```

#### isEqualIgnoreCaseAndLineReturns
Assert that char sequence is equal to the string, ignoring case and line returns considerations

* Signatures:
	- `isEqualIgnoreCaseAndLineReturns(CharSequence string)`
	- `isEqualIgnoreCaseAndLineReturns(CharSequence string, CharSequence message, Object[] arguments)`
	- `isEqualIgnoreCaseAndLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("text").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> throws an exception
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("tex\nt").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("Tex\nT").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().isEqualIgnoreCaseAndLineReturns("text").orElseThrow(); -> throws an exception
```

#### isNotEqualIgnoreCaseAndLineReturns
Assert that char sequence is NOT equal to the string, ignoring case and line returns considerations

* Signatures:
	- `isNotEqualIgnoreCaseAndLineReturns(CharSequence string)`
	- `isNotEqualIgnoreCaseAndLineReturns(CharSequence string, CharSequence message, Object[] arguments)`
	- `isNotEqualIgnoreCaseAndLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("text").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("tex\nt").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("Tex\nT").orElseThrow(); // -> throws an exception
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqualIgnoreCaseAndLineReturns("text").orElseThrow(); -> OK
```

#### contains
Assert that char sequence contains the substring.

* Signatures:
	- `contains(CharSequence substring)`
	- `contains(CharSequence substring, CharSequence message, Object[] arguments)`
	- `contains(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").contains("t").orElseThrow(); // -> OK
Assertor.that("text").contains("ex", "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that("text").contains("text").orElseThrow(); // -> OK
Assertor.that("text").contains("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().contains("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).contains("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").contains(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").contains("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().contains("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().contains(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().contains("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### startsWith
Assert that char sequence starts with the substring.
* Signatures:
	- `startsWith(CharSequence substring)`
	- `startsWith(CharSequence substring, CharSequence message, Object[] arguments)`
	- `startsWith(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").startsWith("t").orElseThrow(); // -> OK
Assertor.that("text").startsWith("T").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWith("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWith("text").orElseThrow(); // -> OK
Assertor.that("text").startsWith("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().startsWith("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).startsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().startsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().startsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().startsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### startsWithIgnoreCase
Assert that char sequence starts with the substring (case insensitive).
* Signatures:
	- `startsWithIgnoreCase(CharSequence substring)`
	- `startsWithIgnoreCase(CharSequence substring, CharSequence message, Object[] arguments)`
	- `startsWithIgnoreCase(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").startsWithIgnoreCase("t").orElseThrow(); // -> OK
Assertor.that("text").startsWithIgnoreCase("T").orElseThrow(); // -> OK
Assertor.that("text").startsWithIgnoreCase("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWithIgnoreCase("text").orElseThrow(); // -> OK
Assertor.that("text").startsWithIgnoreCase("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().startsWithIgnoreCase("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).startsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").startsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().startsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().startsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().startsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### endsWith
Assert that char sequence ends with the substring.
* Signatures:
	- `endsWith(CharSequence substring)`
	- `endsWith(CharSequence substring, CharSequence message, Object[] arguments)`
	- `endsWith(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").endsWith("t").orElseThrow(); // -> OK
Assertor.that("text").endsWith("T").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWith("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWith("text").orElseThrow(); // -> OK
Assertor.that("text").endsWith("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().endsWith("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).endsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().endsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().endsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().endsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### endsWithIgnoreCase
Assert that char sequence ends with the substring (case insensitive).
* Signatures:
	- `endsWithIgnoreCase(CharSequence substring)`
	- `endsWithIgnoreCase(CharSequence substring, CharSequence message, Object[] arguments)`
	- `endsWithIgnoreCase(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").endsWithIgnoreCase("t").orElseThrow(); // -> OK
Assertor.that("text").endsWithIgnoreCase("T").orElseThrow(); // -> OK
Assertor.that("text").endsWithIgnoreCase("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWithIgnoreCase("text").orElseThrow(); // -> OK
Assertor.that("text").endsWithIgnoreCase("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().endsWithIgnoreCase("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).endsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").endsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().endsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().endsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().endsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### matches
Assert that char sequence matches the specified pattern / regex.
* Signatures:
	- `matches(CharSequence regex)`
	- `matches(CharSequence regex, CharSequence message, Object[] arguments)`
	- `matches(CharSequence regex, Locale locale, CharSequence message, Object[] arguments)`
	- `matches(Pattern pattern)`
	- `matches(Pattern pattern, CharSequence message, Object[] arguments)`
	- `matches(Pattern pattern, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- pattern / regex NOT null

* Examples:
```java
Assertor.that("text").matches("[xet]{4}").orElseThrow(); // -> OK
Assertor.that("text").matches("[xet]{3}").orElseThrow(); // -> throws an exception
Assertor.that("text").matches("\\w+").orElseThrow(); // -> OK
Assertor.that("text").matches("xt").orElseThrow(); // -> throws an exception
Assertor.that("text").matches("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").matches("text").orElseThrow(); // -> OK
Assertor.that("text").matches("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().matches("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).matches("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").matches(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().matches("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().matches(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

#### find
Assert that char sequence contains the specified pattern / regex.
* Signatures:
	- `find(CharSequence regex)`
	- `find(CharSequence regex, CharSequence message, Object[] arguments)`
	- `find(CharSequence regex, Locale locale, CharSequence message, Object[] arguments)`
	- `find(Pattern pattern)`
	- `find(Pattern pattern, CharSequence message, Object[] arguments)`
	- `find(Pattern pattern, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- char sequence NOT null
	- pattern / regex NOT null

* Examples:
```java
Assertor.that("text").find("[xet]{4}").orElseThrow(); // -> OK
Assertor.that("text").find("[xet]{3}").orElseThrow(); // -> OK
Assertor.that("text").find("\\w+").orElseThrow(); // -> OK
Assertor.that("text").find("xt").orElseThrow(); // -> OK
Assertor.that("text").find("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").find("text").orElseThrow(); // -> OK
Assertor.that("text").find("y").orElseThrow(); // -> throws an exception
Assertor.that("text").not().find("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((String) null).find("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").find(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that((String) null).not().find("t", "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
Assertor.that("text").not().find(null, "Param '%1$s*' not blank").orElseThrow(); // -> throws an exception
```

### Class
#### isAssignableFrom
Assert that class is assignable from clazz.
* Signatures:
	- `isAssignableFrom(Class<?> clazz)`
	- `isAssignableFrom(Class<?> clazz, CharSequence message, Object[] arguments)`
	- `isAssignableFrom(Class<?> clazz, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- classes NOT null

* Examples:
```java
Assertor.that(IOException.class).isAssignableFrom(Exception.class).orElseThrow(); // -> OK
Assertor.that(Exception.class).isAssignableFrom(Exception.class).orElseThrow(); // -> OK
Assertor.that(Exception.class).isAssignableFrom(IOException.class).orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).isAssignableFrom(IOException.class).orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).isAssignableFrom(null).orElseThrow(); // -> throws an exception
```

#### hasName
Assert that class has the specified name.
* Signatures:
	- `hasName(CharSequence name)`
	- `hasName(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasName(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- class NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(IOException.class).hasName("java.io.IOException").orElseThrow(); // -> OK
Assertor.that(Exception.class).hasName("java.lang.Exception").orElseThrow(); // -> OK
Assertor.that(Exception.class).hasName("Exception").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasName("java.lang.Exception").orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasName(null).orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasName("").orElseThrow(); // -> throws an exception
```

#### hasSimpleName
Assert that class has the specified simple name.
* Signatures:
	- `hasSimpleName(CharSequence name)`
	- `hasSimpleName(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasSimpleName(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- class NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(IOException.class).hasSimpleName("IOException").orElseThrow(); // -> OK
Assertor.that(Exception.class).hasSimpleName("Exception").orElseThrow(); // -> OK
Assertor.that(Exception.class).hasSimpleName("java.lang.Exception").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasSimpleName("Exception").orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasSimpleName(null).orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasSimpleName("").orElseThrow(); // -> throws an exception
```

#### hasCanonicalName
Assert that class has the specified canonical name.
* Signatures:
	- `hasCanonicalName(CharSequence name)`
	- `hasCanonicalName(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasCanonicalName(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- class NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(IOException.class).hasCanonicalName(IOException.class.getCanonicalName()).orElseThrow(); // -> OK
Assertor.that(Exception.class).hasCanonicalName("java.lang.Exception").orElseThrow(); // -> OK
Assertor.that(Exception.class).hasCanonicalName("Exception").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasCanonicalName("Exception").orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasCanonicalName(null).orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasCanonicalName("").orElseThrow(); // -> throws an exception
```

#### hasPackageName
Assert that class has the specified package name.
* Signatures:
	- `hasPackageName(CharSequence name)`
	- `hasPackageName(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasPackageName(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- class NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(IOException.class).hasPackageName(IOException.class.getPackage().getName()).orElseThrow(); // -> OK
Assertor.that(Exception.class).hasPackageName("java.lang").orElseThrow(); // -> OK
Assertor.that(Exception.class).hasPackageName("java").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasPackageName("java.lang").orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasPackageName(null).orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasPackageName("").orElseThrow(); // -> throws an exception
```

#### hasTypeName
Assert that class has the specified package name.
* Signatures:
	- `hasTypeName(CharSequence name)`
	- `hasTypeName(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasTypeName(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- class NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(long.class).hasTypeName(lang.class.getTypeName()).orElseThrow(); // -> OK
Assertor.that(Long.class).hasTypeName("java.lang.Long").orElseThrow(); // -> OK
Assertor.that(Long.class).hasTypeName("long").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasTypeName("java.lang").orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasTypeName(null).orElseThrow(); // -> throws an exception
Assertor.that(Exception.class).hasTypeName("").orElseThrow(); // -> throws an exception
```

### Date & Calendar
#### isEqual
Assert that dates are equal.

* Signatures:
	- `isEqual(Calendar date)`
	- `isEqual(Calendar date, CharSequence message, Object[] arguments)`
	- `isEqual(Calendar date, Locale locale, CharSequence message, Object[] arguments)`
	- `isEqual(Date date)`
	- `isEqual(Date date, CharSequence message, Object[] arguments)`
	- `isEqual(Date date, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 5, 5, 6);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);

Assertor.that(date1).isEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isEqual(date1).orElseThrow(); // -> OK
Assertor.that((Calendar) null).isEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isEqual(null).orElseThrow(); // -> throws an exception
Assertor.that(date1).isEqual(date2, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isEqual(date2).orElseThrow(); // -> throws an exception
```

#### isNotEqual
Assert that dates are NOT equal.

* Signatures:
	- `isNotEqual(Calendar date)`
	- `isNotEqual(Calendar date, CharSequence message, Object[] arguments)`
	- `isNotEqual(Calendar date, Locale locale, CharSequence message, Object[] arguments)`
	- `isNotEqual(Date date)`
	- `isNotEqual(Date date, CharSequence message, Object[] arguments)`
	- `isNotEqual(Date date, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 5, 5, 6);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);

Assertor.that(date1).isNotEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotEqual(date1).orElseThrow(); // -> OK
Assertor.that((Calendar) null).isNotEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotEqual(null).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotEqual(date2, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isNotEqual(date2).orElseThrow(); // -> throws an exception
```

#### isAround
Assert that date1 is around the date2.
* Signatures:
	- `isAround(Calendar date, int calendarField, int calendarAmount)`
	- `isAround(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isAround(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`
	- `isAround(Date date, int calendarField, int calendarAmount)`
	- `isAround(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isAround(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 5, 5, 6);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);

Assertor.that(date1).isAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAround(date2, Calendar.MILLISECOND, 5).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isAround(date2, Calendar.MILLISECOND, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isAround(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
Assertor.that(null).not().isAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isAround(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
```

#### isNotAround
Assert that date1 is not around the date2.
* Signatures:
	- `isNotAround(Calendar date, int calendarField, int calendarAmount)`
	- `isNotAround(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isNotAround(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`
	- `isNotAround(Date date, int calendarField, int calendarAmount)`
	- `isNotAround(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isNotAround(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = Calendar.getInstance();
final Calendar date2 = Calendar.getInstance();

date1.set(2016, 05, 29, 5, 5, 5);
date2.set(2016, 05, 29, 6, 5, 5);

Assertor.that(date1).isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isNotAround(date2, Calendar.MINUTE, 5).orElseThrow(); // -> OK
Assertor.that(date1).isNotAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isNotAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isNotAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isNotAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isNotAround(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isNotAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
Assertor.that(null).not().isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isNotAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isNotAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isNotAround(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isNotAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
```

#### isAfter
Assert that date1 is after the date2.
* Signatures:
	- `isAfter(Calendar date)`
	- `isAfter(Calendar date, CharSequence message, Object[] arguments)`
	- `isAfter(Calendar date, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfter(Calendar date, int calendarField, int calendarAmount)`
	- `isAfter(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isAfter(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfter(Date date)`
	- `isAfter(Date date, CharSequence message, Object[] arguments)`
	- `isAfter(Date date, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfter(Date date, int calendarField, int calendarAmount)`
	- `isAfter(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isAfter(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 6, 5, 5);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);

Assertor.that(date1).isAfter(date2).orElseThrow(); // -> OK
Assertor.that(date2).isAfter(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isAfter(date1).orElseThrow(); // -> OK

Assertor.that(date1).isAfter(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAfter(date2, Calendar.MINUTE, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAfter(date2, Calendar.HOUR, 5).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isAfter(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAfter(date2).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfter(null).orElseThrow(); // -> throws an exception (date null)

Assertor.that(null).isAfter(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfter(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfter(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isAfter(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isAfter(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
Assertor.that(null).not().isAfter(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAfter(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAfter(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isAfter(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isAfter(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
```

#### isAfterOrEqual
Assert that date1 is after or equals the date2.
* Signatures:
	- `isAfterOrEqual(Calendar date)`
	- `isAfterOrEqual(Calendar date, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Calendar date, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Calendar date)`
	- `isAfterOrEqual(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Date date)`
	- `isAfterOrEqual(Date date, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Date date, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Date date, int calendarField, int calendarAmount)`
	- `isAfterOrEqual(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 6, 5, 5);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);

Assertor.that(date1).isAfterOrEqual(date1).orElseThrow(); // -> OK
Assertor.that(date1).isAfterOrEqual(date2).orElseThrow(); // -> OK
Assertor.that(date2).isAfterOrEqual(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isAfterOrEqual(date1).orElseThrow(); // -> OK

Assertor.that(date1).isAfterOrEqual(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAfterOrEqual(date2, Calendar.MINUTE, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAfterOrEqual(date2, Calendar.HOUR, 5).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isAfterOrEqual(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAfterOrEqual(date2).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfterOrEqual(null).orElseThrow(); // -> throws an exception (date null)

Assertor.that(null).isAfterOrEqual(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfterOrEqual(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfterOrEqual(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isAfterOrEqual(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isAfterOrEqual(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
Assertor.that(null).not().isAfterOrEqual(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAfterOrEqual(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAfterOrEqual(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isAfterOrEqual(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isAfterOrEqual(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
```

#### isBefore
Assert that date1 is before the date2.
* Signatures:
	- `isBefore(Calendar date)`
	- `isBefore(Calendar date, CharSequence message, Object[] arguments)`
	- `isBefore(Calendar date, Locale locale, CharSequence message, Object[] arguments)`
	- `isBefore(Calendar date, int calendarField, int calendarAmount)`
	- `isBefore(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isBefore(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`
	- `isBefore(Date date)`
	- `isBefore(Date date, CharSequence message, Object[] arguments)`
	- `isBefore(Date date, Locale locale, CharSequence message, Object[] arguments)`
	- `isBefore(Date date, int calendarField, int calendarAmount)`
	- `isBefore(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isBefore(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 6, 5, 5);

Assertor.that(date1).isBefore(date2).orElseThrow(); // -> OK
Assertor.that(date2).isBefore(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isBefore(date1).orElseThrow(); // -> OK

Assertor.that(date1).isBefore(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isBefore(date2, Calendar.MINUTE, 5).orElseThrow(); // -> OK
Assertor.that(date1).isBefore(date2, Calendar.HOUR, 5).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isBefore(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isBefore(date2).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBefore(null).orElseThrow(); // -> throws an exception (date null)

Assertor.that(null).isBefore(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBefore(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBefore(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isBefore(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isBefore(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
Assertor.that(null).not().isBefore(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isBefore(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isBefore(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isBefore(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isBefore(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
```

#### isBeforeOrEqual
Assert that date1 is before or equals the date2.
* Signatures:
	- `isBeforeOrEqual(Calendar date)`
	- `isBeforeOrEqual(Calendar date, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Calendar date, Locale locale, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Calendar date)`
	- `isBeforeOrEqual(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Date date)`
	- `isBeforeOrEqual(Date date, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Date date, Locale locale, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Date date, int calendarField, int calendarAmount)`
	- `isBeforeOrEqual(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = new GregorianCalendar(2016, 05, 29, 5, 5, 5);
final Calendar date2 = new GregorianCalendar(2016, 05, 29, 6, 5, 5);

Assertor.that(date1).isBeforeOrEqual(date1).orElseThrow(); // -> OK
Assertor.that(date1).isBeforeOrEqual(date2).orElseThrow(); // -> OK
Assertor.that(date2).isBeforeOrEqual(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isBeforeOrEqual(date1).orElseThrow(); // -> OK

Assertor.that(date1).isBeforeOrEqual(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isBeforeOrEqual(date2, Calendar.MINUTE, 5).orElseThrow(); // -> OK
Assertor.that(date1).isBeforeOrEqual(date2, Calendar.HOUR, 5).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isBeforeOrEqual(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isBeforeOrEqual(date2).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBeforeOrEqual(null).orElseThrow(); // -> throws an exception (date null)

Assertor.that(null).isBeforeOrEqual(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBeforeOrEqual(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBeforeOrEqual(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isBeforeOrEqual(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).isBeforeOrEqual(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
Assertor.that(null).not().isBeforeOrEqual(date2, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isBeforeOrEqual(null, Calendar.SECOND, 5).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isBeforeOrEqual(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isBeforeOrEqual(date2, -100, -5).orElseThrow(); // -> throws an exception (invalid calendarField)
Assertor.that(date1).not().isBeforeOrEqual(date2, Calendar.HOUR, 0).orElseThrow(); // -> throws an exception (invalid calendarAmount)
```

### Temporal
#### isEqual
Assert that dates are equal.

* Signatures:
	- `isEqual(T temporal)`
	- `isEqual(T temporal, CharSequence message, Object[] arguments)`
	- `isEqual(T temporal, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
LocalDateTime date1 = LocalDateTime.now();
LocalDateTime date2 = LocalDateTime.now().plusHours(1);

Assertor.that(date1).isEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isEqual(date1).orElseThrow(); // -> OK
Assertor.that((LocalDateTime) null).isEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isEqual(null).orElseThrow(); // -> throws an exception
Assertor.that(date1).isEqual(date2, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isEqual(date2).orElseThrow(); // -> throws an exception
```

#### isNotEqual
Assert that dates are NOT equal.

* Signatures:
	- `isNotEqual(T temporal)`
	- `isNotEqual(T temporal, CharSequence message, Object[] arguments)`
	- `isNotEqual(T temporal, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now();
final LocalDateTime date2 = LocalDateTime.now().plusHours(1);

Assertor.that(date1).isNotEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotEqual(date1).orElseThrow(); // -> OK
Assertor.that((LocalDateTime) null).isNotEqual(date2).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotEqual(null).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotEqual(date2, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isNotEqual(date2).orElseThrow(); // -> throws an exception
```

#### isAround
Assert that date1 is around the date2.
* Signatures:
	- `isAround(T temporal, TemporalAmount temporalAmount)`
	- `isAround(T temporal, TemporalAmount temporalAmount, CharSequence message, Object[] arguments)`
	- `isAround(T temporal, TemporalAmount temporalAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- temporals NOT null, only if temporalAmount NOT null

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now().plusSeconds(1);
final LocalDateTime date2 = LocalDateTime.now();

Assertor.that(date1).isAround(date2, Duration.ofSeconds(5)).orElseThrow(); // -> OK
Assertor.that(date1).isAround(date2, Duration.ofHours(5)).orElseThrow(); // -> OK
Assertor.that(date1).isAround(date2, Duration.ofMillis(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isAround(date2, Duration.ofMillis(5)).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAround(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAround(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(null).not().isAround(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAround(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
```

#### isNotAround
Assert that date1 is NOT around the date2.
* Signatures:
	- `isNotAround(T temporal, TemporalAmount temporalAmount)`
	- `isNotAround(T temporal, TemporalAmount temporalAmount, CharSequence message, Object[] arguments)`
	- `isNotAround(T temporal, TemporalAmount temporalAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- temporals NOT null, only if temporalAmount NOT null

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now().plusSeconds(1);
final LocalDateTime date2 = LocalDateTime.now();

Assertor.that(date1).isNotAround(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotAround(date2, Duration.ofHours(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).isNotAround(date2, Duration.ofMillis(5)).orElseThrow(); // -> OK
Assertor.that(date1).not().isNotAround(date2, Duration.ofMillis(5)).orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that(null).isAround(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAround(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(null).not().isAround(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAround(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
```

#### isAfter
Assert that date1 is after the date2.
* Signatures:
	- `isAfter(T temporal)`
	- `isAfter(T temporal, CharSequence message, Object[] arguments)`
	- `isAfter(T temporal, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfter(T temporal, TemporalAmount temporalAmount)`
	- `isAfter(T temporal, TemporalAmount temporalAmount, CharSequence message, Object[] arguments)`
	- `isAfter(T temporal, TemporalAmount temporalAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- temporals NOT null, only if temporalAmount NOT null

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now().plusHours(1);
final LocalDateTime date2 = LocalDateTime.now();

Assertor.that(date1).isAfter(date2).orElseThrow(); // -> OK
Assertor.that(date1).isAfter(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).isAfter(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isAfter(date1).orElseThrow(); // -> OK

Assertor.that(date1).isAfter(date2, Duration.ofSeconds(5)).orElseThrow(); // -> OK
Assertor.that(date1).isAfter(date2, Duration.ofMinutes(5)).orElseThrow(); // -> OK
Assertor.that(date1).isAfter(date2, Duration.ofHours(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isAfter(date2, Duration.ofHours(5)).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAfter(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfter(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(null).not().isAfter(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAfter(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
```

#### isAfterOrEqual
Assert that date1 is after or equals the date2.
* Signatures:
	- `isAfterOrEqual(T temporal)`
	- `isAfterOrEqual(T temporal, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(T temporal, Locale locale, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(T temporal, TemporalAmount temporalAmount)`
	- `isAfterOrEqual(T temporal, TemporalAmount temporalAmount, CharSequence message, Object[] arguments)`
	- `isAfterOrEqual(T temporal, TemporalAmount temporalAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- temporals NOT null, only if temporalAmount NOT null

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now().plusHours(1);
final LocalDateTime date2 = LocalDateTime.now();

Assertor.that(date1).isAfterOrEqual(date2).orElseThrow(); // -> OK
Assertor.that(date1).isAfterOrEqual(date1).orElseThrow(); // -> OK
Assertor.that(date2).isAfterOrEqual(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isAfterOrEqual(date1).orElseThrow(); // -> OK

Assertor.that(date1).isAfterOrEqual(date2, Duration.ofSeconds(5)).orElseThrow(); // -> OK
Assertor.that(date1).isAfterOrEqual(date2, Duration.ofMinutes(5)).orElseThrow(); // -> OK
Assertor.that(date1).isAfterOrEqual(date2, Duration.ofHours(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isAfterOrEqual(date2, Duration.ofHours(5)).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAfterOrEqual(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isAfterOrEqual(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(null).not().isAfterOrEqual(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isAfterOrEqual(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
```

#### isBefore
Assert that date1 is before the date2.
* Signatures:
	- `isBefore(T temporal)`
	- `isBefore(T temporal, CharSequence message, Object[] arguments)`
	- `isBefore(T temporal, Locale locale, CharSequence message, Object[] arguments)`
	- `isBefore(T temporal, TemporalAmount temporalAmount)`
	- `isBefore(T temporal, TemporalAmount temporalAmount, CharSequence message, Object[] arguments)`
	- `isBefore(T temporal, TemporalAmount temporalAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- temporals NOT null, only if temporalAmount NOT null

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now();
final LocalDateTime date2 = LocalDateTime.now().plusHours(1);

Assertor.that(date1).isBefore(date2).orElseThrow(); // -> OK
Assertor.that(date1).isBefore(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).isBefore(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isBefore(date1).orElseThrow(); // -> OK

Assertor.that(date1).isBefore(date2, Duration.ofSeconds(5)).orElseThrow(); // -> OK
Assertor.that(date1).isBefore(date2, Duration.ofMinutes(5)).orElseThrow(); // -> OK
Assertor.that(date1).isBefore(date2, Duration.ofHours(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isBefore(date2, Duration.ofHours(5)).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isBefore(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBefore(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(null).not().isBefore(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isBefore(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
```

#### isBeforeOrEqual
Assert that date1 is before or equals the date2.
* Signatures:
	- `isBeforeOrEqual(T temporal)`
	- `isBeforeOrEqual(T temporal, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(T temporal, Locale locale, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(T temporal, TemporalAmount temporalAmount)`
	- `isBeforeOrEqual(T temporal, TemporalAmount temporalAmount, CharSequence message, Object[] arguments)`
	- `isBeforeOrEqual(T temporal, TemporalAmount temporalAmount, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- temporals NOT null, only if temporalAmount NOT null

* Examples:
```java
final LocalDateTime date1 = LocalDateTime.now();
final LocalDateTime date2 = LocalDateTime.now().plusHours(1);

Assertor.that(date1).isBeforeOrEqual(date2).orElseThrow(); // -> OK
Assertor.that(date1).isBeforeOrEqual(date1).orElseThrow(); // -> OK
Assertor.that(date2).isBeforeOrEqual(date1).orElseThrow(); // -> throws an exception
Assertor.that(date2).not().isBeforeOrEqual(date1).orElseThrow(); // -> OK

Assertor.that(date1).isBeforeOrEqual(date2, Duration.ofSeconds(5)).orElseThrow(); // -> OK
Assertor.that(date1).isBeforeOrEqual(date2, Duration.ofMinutes(5)).orElseThrow(); // -> OK
Assertor.that(date1).isBeforeOrEqual(date2, Duration.ofHours(5)).orElseThrow(); // -> throws an exception
Assertor.that(date1).not().isBeforeOrEqual(date2, Duration.ofHours(5)).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isBeforeOrEqual(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).isBeforeOrEqual(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(null).not().isBeforeOrEqual(date2, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
Assertor.that(date1).not().isBeforeOrEqual(null, Duration.ofSeconds(5)).orElseThrow(); // -> throws an exception (date null)
```

### Enum
#### hasName
Assert that enumeration has the specified name.
* Signatures:
	- `hasName(CharSequence name)`
	- `hasName(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasName(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- enumeration NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(Type.LONG).hasName("LONG").orElseThrow(); // -> OK
Assertor.that(Type.LONG).hasName("Long").orElseThrow(); // -> throws an exception
Assertor.that(Type.LONG).hasName("INTEGER").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Type) null).hasName("LONG").orElseThrow(); // -> throws an exception
Assertor.that(Type.LONG).hasName(null).orElseThrow(); // -> throws an exception
Assertor.that(Type.LONG).hasName("").orElseThrow(); // -> throws an exception
```

#### hasNameIgnoreCase
Assert that enumeration has the specified name.
* Signatures:
	- `hasNameIgnoreCase(CharSequence name)`
	- `hasNameIgnoreCase(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasNameIgnoreCase(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- enumeration NOT null
	- name NOT null and NOT empty

* Examples:
```java
Assertor.that(Type.LONG).hasNameIgnoreCase("LONG").orElseThrow(); // -> OK
Assertor.that(Type.LONG).hasNameIgnoreCase("Long").orElseThrow(); // -> OK
Assertor.that(Type.LONG).hasNameIgnoreCase("INTEGER").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Type) null).hasNameIgnoreCase("LONG").orElseThrow(); // -> throws an exception
Assertor.that(Type.LONG).hasNameIgnoreCase(null).orElseThrow(); // -> throws an exception
Assertor.that(Type.LONG).hasNameIgnoreCase("").orElseThrow(); // -> throws an exception
```

#### hasOrdinal
Assert that enumeration has the specified ordinal.
* Signatures:
	- `hasOrdinal(CharSequence name)`
	- `hasOrdinal(CharSequence name, CharSequence message, Object[] arguments)`
	- `hasOrdinal(CharSequence name, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- enumeration NOT null
	- ordinal cannot be lower than 0

* Examples:
```java
Assertor.that(Type.LONG).hasOrdinal(0).orElseThrow(); // -> OK
Assertor.that(Type.LONG).hasOrdinal(-1).orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Type) null).hasOrdinal(0).orElseThrow(); // -> throws an exception
Assertor.that(Type.LONG).hasOrdinal(-1).orElseThrow(); // -> throws an exception
```

### Iterable
#### hasSize
Assert that iterable has the specified size.

* Signatures:
	- `hasSize(int size)`
	- `hasSize(int size, CharSequence message, Object[] arguments)`
	- `hasSize(int size, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- iterable NOT null
	- length >= 0

* Examples:
```java
Assertor.that(Arrays.asList("text")).hasSize(3).orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).hasSize(1, "Bad status").orElseThrow(); // -> OK
Assertor.that(Arrays.asList("text")).not().hasSize(3).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((List<String>) null).hasSize(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).hasSize(-1, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that((List<String>) null).not().hasSize(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).not().hasSize(-1, "Bad status").orElseThrow(); // -> throws an exception
```

#### isEmpty
Assert that iterable is empty or null.

* Signatures:
	- `isEmpty()`
	- `isEmpty(CharSequence message, Object[] arguments)`
	- `isEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(Arrays.asList("text")).isEmpty().orElseThrow(); // -> throws an exception
Assertor.that((List<String>) null).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that(Collections.emptySet()).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that(Arrays.asList("text")).not().isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
```

#### isNotEmpty
Assert that iterable is NOT empty or null.

* Signatures:
	- `isNotEmpty()`
	- `isNotEmpty(CharSequence message, Object[] arguments)`
	- `isNotEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(Arrays.asList("text")).isNotEmpty().orElseThrow(); // -> OK
Assertor.that((List<String>) null).isNotEmpty("Param '%1$s*' not empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).isNotEmpty("Param '%1$s*' not empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).not().isNotEmpty("Param '%1$s*' not empty").orElseThrow(); // -> throws an exception
```

#### contains
Assert that iterable contains the element.

* Signatures:
	- `contains(T element)`
	- `contains(T element, CharSequence message, Object[] arguments)`
	- `contains(T element, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- iterable NOT null and NOT empty

* Examples:
```java
Assertor.that(Arrays.asList("text")).contains("text").orElseThrow(); // -> OK
Assertor.that(Arrays.asList(null, "")).contains(null, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((List<String>) null).contains("t", "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).contains(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((List<String>) null).not().contains("t", "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).not().contains(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

#### containsAll
Assert that iterable contains all elements.

* Signatures:
	- `containsAll(Iterable<T> elements)`
	- `containsAll(Iterable<T> elements, CharSequence message, Object[] arguments)`
	- `containsAll(Iterable<T> elements, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- iterable NOT null and NOT empty
	- elements NOT null and NOT empty

* Examples:
```java
Assertor.that(Arrays.asList("text")).containsAll(Arrays.asList("text")).orElseThrow(); // -> OK
Assertor.that(Arrays.asList(null, "")).containsAll(Arrays.asList(null), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(Arrays.asList(null, "")).containsAll(Arrays.asList(null, "text"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((List<String>) null).containsAll(Arrays.asList("t"), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).containsAll(Arrays.asList((String) null), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).containsAll(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).containsAll(Collections.emptySet(), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((List<String>) null).not().containsAll(Arrays.asList("t"), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).not().containsAll(Arrays.asList((String) null), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).not().containsAll(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).not().containsAll(Collections.emptySet(), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

#### containsAny
Assert that iterable contains any elements.

* Signatures:
	- `containsAny(Iterable<T> elements)`
	- `containsAny(Iterable<T> elements, CharSequence message, Object[] arguments)`
	- `containsAny(Iterable<T> elements, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- iterable NOT null and NOT empty
	- elements NOT null and NOT empty

* Examples:
```java
Assertor.that(Arrays.asList("text")).containsAny(Arrays.asList("text")).orElseThrow(); // -> OK
Assertor.that(Arrays.asList(null, "")).containsAny(Arrays.asList(null), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(Arrays.asList(null, "")).containsAny(Arrays.asList(null, "text"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((List<String>) null).containsAny(Arrays.asList("t"), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).containsAny(Arrays.asList(null), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).containsAny(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).containsAny(Collections.emptySet(), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((List<String>) null).not().containsAny(Arrays.asList("t"), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptySet()).not().containsAny(Arrays.asList((String) null), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).not().containsAny(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Arrays.asList("text")).not().containsAny(Collections.emptySet(), "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

### Map
#### hasSize
Assert that map has the specified size.

* Signatures:
	- `hasSize(int size)`
	- `hasSize(int size, CharSequence message, Object[] arguments)`
	- `hasSize(int size, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- map NOT null
	- length >= 0

* Examples:
```java
Assertor.that(MapUtils2.newHashMap("key", "value")).hasSize(3).orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key", "value")).hasSize(1, "Bad status").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key", "value")).not().hasSize(3).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((Map<String, String>) null).hasSize(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key", "value")).hasSize(-1, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that((Map<String, String>) null).not().hasSize(4, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key", "value")).not().hasSize(-1, "Bad status").orElseThrow(); // -> throws an exception
```

#### isEmpty
Assert that map is empty or null.

* Signatures:
	- `isEmpty()`
	- `isEmpty(CharSequence message, Object[] arguments)`
	- `isEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(MapUtils2.newHashMap("key", "value")).isEmpty().orElseThrow(); // -> throws an exception
Assertor.that((Map<String, String>) null).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that(Collections.emptyMap()).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key", "value")).not().isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
```

#### isNotEmpty
Assert that map is NOT empty or null.

* Signatures:
	- `isNotEmpty()`
	- `isNotEmpty(CharSequence message, Object[] arguments)`
	- `isNotEmpty(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: None

* Examples:
```java
Assertor.that(MapUtils2.newHashMap("key", "value")).isNotEmpty().orElseThrow(); // -> OK
Assertor.that((Map<String, String>) null).isNotEmpty("Param '%1$s*' not empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptyMap()).isNotEmpty("Param '%1$s*' not empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key", "value")).not().isNotEmpty("Param '%1$s*' not empty").orElseThrow(); // -> throws an exception
```

#### contains
Assert that map contains the element.

* Signatures:
	- `contains(K key)`
	- `contains(K key, CharSequence message, Object[] arguments)`
	- `contains(K key, Locale locale, CharSequence message, Object[] arguments)`
	- `contains(K key, V value)`
	- `contains(K key, V value, CharSequence message, Object[] arguments)`
	- `contains(K key, V value, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- map NOT null and NOT empty

* Examples:
```java
Assertor.that(MapUtils2.newHashMap("key", "value")).contains("text").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).contains(null, "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK

Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).contains(null, "value2",  "Param '%1$s*' not contains '%2$s*' / '%3$s*'").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).contains(null, "value3", "Param '%1$s*' not contains '%2$s*' / '%3$s*'").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Map<String, String>) null).contains("t", "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptyMap()).contains(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that((Map<String, String>) null).not().contains("t", "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(Collections.emptyMap()).not().contains(null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

#### containsAll
Assert that iterable contains all elements.

* Signatures:
	- `containsAll(Iterable<K> keys)`
	- `containsAll(Iterable<K> keys, CharSequence message, Object[] arguments)`
	- `containsAll(Iterable<K> keys, Locale locale, CharSequence message, Object[] arguments)`
	- `containsAll(Map<K, V> map)`
	- `containsAll(Map<K, V> map, CharSequence message, Object[] arguments)`
	- `containsAll(Map<K, V> map, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- map NOT null and NOT empty
	- keys NOT null and NOT empty
	- second map NOT null and NOT empty

* Examples:
```java
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAll(Arrays.asList("key1"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAll(Arrays.asList("key1", "key2"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAll(MapUtils2.newHashMap(null, "value2"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAll(MapUtils2.newHashMap(null, "value2", "key3", "value3"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that(MapUtils2.newHashMap("key1", "value1")).containsAll((List<String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1")).containsAll((Map<String, String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1")).not().containsAll((List<String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1")).not().containsAll((Map<String, String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```

#### containsAny
Assert that iterable contains any elements.

* Signatures:
	- `containsAny(Iterable<K> keys)`
	- `containsAny(Iterable<K> keys, CharSequence message, Object[] arguments)`
	- `containsAny(Iterable<K> keys, Locale locale, CharSequence message, Object[] arguments)`
	- `containsAny(Map<K, V> map)`
	- `containsAny(Map<K, V> map, CharSequence message, Object[] arguments)`
	- `containsAny(Map<K, V> map, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- map NOT null and NOT empty
	- keys NOT null and NOT empty
	- second map NOT null and NOT empty

* Examples:
```java
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAny(Arrays.asList("key1"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAny(Arrays.asList("key1", "key2"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAny(MapUtils2.newHashMap(null, "value2"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that(MapUtils2.newHashMap("key1", "value1", null, "value2")).containsAny(MapUtils2.newHashMap(null, "value2", "key3", "value3"), "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(MapUtils2.newHashMap("key1", "value1")).containsAny((List<String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1")).containsAny((Map<String, String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1")).not().containsAny((List<String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
Assertor.that(MapUtils2.newHashMap("key1", "value1")).not().containsAny((Map<String, String>) null, "Param '%1$s*' not null or empty").orElseThrow(); // -> throws an exception
```


### Number
#### isEqual
Assert that numbers are equal.

* Signatures:
	- `isEqual(N number)`
	- `isEqual(N number, CharSequence message, Object[] arguments)`
	- `isEqual(N number, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that(-12).isEqual(11).orElseThrow(); // -> throws an exception
Assertor.that(1).isEqual(1).orElseThrow(); // -> OK
Assertor.that((Long) null).isEqual(1).orElseThrow(); // -> throws an exception
Assertor.that(1).isEqual(null).orElseThrow(); // -> throws an exception
Assertor.that(12).isEqual(1, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(-12).not().isEqual(1).orElseThrow(); // -> OK
```

#### isNotEqual
Assert that numbers are NOT equal.

* Signatures:
	- `isNotEqual(N number)`
	- `isNotEqual(N number, CharSequence message, Object[] arguments)`
	- `isNotEqual(N number, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that(-12).isNotEqual(-12).orElseThrow(); // -> throws an exception
Assertor.that(1).isNotEqual(12).orElseThrow(); // -> OK
Assertor.that((Long) null).isNotEqual(1).orElseThrow(); // -> OK
Assertor.that(1).isNotEqual(null).orElseThrow(); // -> OK
Assertor.that(12).isNotEqual(12, "Bad status").orElseThrow(); // -> throws an exception
Assertor.that(-12).not().isNotEqual(1).orElseThrow(); // -> throws an exception
```

#### isZero
Assert that number equals zero.

* Signatures:
	- `isZero()`
	- `isZero(CharSequence message, Object[] arguments)`
	- `isZero(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that(-12).isZero().orElseThrow(); // -> throws an exception
Assertor.that(0).isZero().orElseThrow(); // -> OK
Assertor.that((Long) null).isZero().orElseThrow(); // -> throws an exception
Assertor.that(12).isZero("Bad status").orElseThrow(); // -> throws an exception
Assertor.that(-12).not().isZero().orElseThrow(); // -> throws an exception
```

#### isPositive
Assert that number is positive.

* Signatures:
	- `isPositive()`
	- `isPositive(CharSequence message, Object[] arguments)`
	- `isPositive(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that(-12).isPositive().orElseThrow(); // -> throws an exception
Assertor.that((Integer) null).isPositive().orElseThrow(); // -> throws an exception
Assertor.that(0).isPositive().orElseThrow(); // -> throws an exception
Assertor.that(12).isPositive("Bad status").orElseThrow(); // -> OK
Assertor.that(-12).not().isPositive().orElseThrow(); // -> OK
```

#### isNegative
Assert that number is negative.

* Signatures:
	- `isNegative()`
	- `isNegative(CharSequence message, Object[] arguments)`
	- `isNegative(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites: none

* Examples:
```java
Assertor.that(12).isNegative().orElseThrow(); // -> throws an exception
Assertor.that(0).isNegative().orElseThrow(); // -> throws an exception
Assertor.that((Integer) null).isNegative().orElseThrow(); // -> throws an exception
Assertor.that(-12).isNegative("Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isNegative().orElseThrow(); // -> OK
```

#### isGT
Assert that number is greater than specified number.

* Signatures:
	- `isGT(Number number)`
	- `isGT(Number number, CharSequence message, Object[] arguments)`
	- `isGT(Number number, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isGT(12).orElseThrow(); // -> throws an exception
Assertor.that(12).isGT(10, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isGT(12).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isGT(12).orElseThrow(); // -> throws an exception
Assertor.that(12).isGT(null).orElseThrow(); // -> throws an exception
Assertor.that(null).not().isGT(12).orElseThrow(); // -> throws an exception
Assertor.that(12).not().isGT(null).orElseThrow(); // -> throws an exception
```

#### isGTE
Assert that number is greater than or equal to specified number.

* Signatures:
	- `isGTE(Number number)`
	- `isGTE(Number number, CharSequence message, Object[] arguments)`
	- `isGTE(Number number, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isGTE(13).orElseThrow(); // -> throws an exception
Assertor.that(12).isGTE(12).orElseThrow(); // -> OK
Assertor.that(12).isGTE(10, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isGTE(13).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isGTE(12).orElseThrow(); // -> throws an exception
Assertor.that(12).isGTE(null).orElseThrow(); // -> throws an exception
Assertor.that(null).not().isGTE(12).orElseThrow(); // -> throws an exception
Assertor.that(12).not().isGTE(null).orElseThrow(); // -> throws an exception
```

#### isLT
Assert that number is lower than specified number.

* Signatures:
	- `isLT(Number number)`
	- `isLT(Number number, CharSequence message, Object[] arguments)`
	- `isLT(Number number, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isLT(12).orElseThrow(); // -> throws an exception
Assertor.that(12).isLT(13, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isLT(12).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isLT(12).orElseThrow(); // -> throws an exception
Assertor.that(12).isLT(null).orElseThrow(); // -> throws an exception
Assertor.that(null).not().isLT(12).orElseThrow(); // -> throws an exception
Assertor.that(12).not().isLT(null).orElseThrow(); // -> throws an exception
```

#### isLTE
Assert that number is lower than or equal to specified number.

* Signatures:
	- `isLTE(Number number)`
	- `isLTE(Number number, CharSequence message, Object[] arguments)`
	- `isLTE(Number number, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isLTE(11).orElseThrow(); // -> throws an exception
Assertor.that(12).isLTE(12).orElseThrow(); // -> OK
Assertor.that(12).isLTE(13, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isLTE(11).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isLTE(12).orElseThrow(); // -> throws an exception
Assertor.that(12).isLTE(null).orElseThrow(); // -> throws an exception
Assertor.that(null).not().isLTE(12).orElseThrow(); // -> throws an exception
Assertor.that(12).not().isLTE(null).orElseThrow(); // -> throws an exception
```

### Throwable
#### isAssignableFrom
Assert that throwable is assignable from clazz and has the specified message or matches the pattern.
* Signatures:
	- `isAssignableFrom(Class<?> clazz, CharSequence throwableMessage)`
	- `isAssignableFrom(Class<?> clazz, CharSequence throwableMessage, CharSequence message, Object[] arguments)`
	- `isAssignableFrom(Class<?> clazz, CharSequence throwableMessage, Locale locale, CharSequence message, Object[] arguments)`
	- `isAssignableFrom(Class<?> clazz, Pattern pattern)`
	- `isAssignableFrom(Class<?> clazz, Pattern pattern, CharSequence message, Object[] arguments)`
	- `isAssignableFrom(Class<?> clazz, Pattern pattern, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- throwable NOT null
	- class NOT null
	- pattern NOT null (only for pattern signature)

* Examples:
```java
Assertor.that(new IOException("test")).isAssignableFrom(Exception.class, "test").orElseThrow(); // -> OK
Assertor.that(new Exception("error")).isAssignableFrom(Exception.class, "error").orElseThrow(); // -> OK
Assertor.that(new IOException()).isAssignableFrom(Exception.class, (String) null).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).isAssignableFrom(IOException.class, "error").orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).isAssignableFrom(Exception.class, "tes").orElseThrow(); // -> throws an exception

Assertor.that(new IOException("test")).isAssignableFrom(Exception.class, Pattern.compile("^t.*t$")).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).isAssignableFrom(Exception.class, Pattern.compile("^e.*$")).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).isAssignableFrom(IOException.class, Pattern.compile("^x.*$")).orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).isAssignableFrom(Exception.class, Pattern.compile("^t")).orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).isAssignableFrom(Exception.class, "error").orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isAssignableFrom(null, "error").orElseThrow(); // -> throws an exception

Assertor.that((Exception) null).isAssignableFrom(Exception.class, "error").orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isAssignableFrom(null, Pattern.compile("^e.*$")).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isAssignableFrom(Exception.class, (Pattern) null).orElseThrow(); // -> throws an exception
```

#### isInstanceOf
Assert that throwable is an instance of object and has the specified message or matches the pattern.
* Signatures:
	- `isInstanceOf(Class<?> clazz, CharSequence throwableMessage)`
	- `isInstanceOf(Class<?> clazz, CharSequence throwableMessage, CharSequence message, Object[] arguments)`
	- `isInstanceOf(Class<?> clazz, CharSequence throwableMessage, Locale locale, CharSequence message, Object[] arguments)`
	- `isInstanceOf(Class<?> clazz, Pattern pattern)`
	- `isInstanceOf(Class<?> clazz, Pattern pattern, CharSequence message, Object[] arguments)`
	- `isInstanceOf(Class<?> clazz, Pattern pattern, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- throwable NOT null
	- class NOT null
	- pattern NOT null (only for pattern signature)

* Examples:
```java
Assertor.that(new IOException("test")).isInstanceOf(Exception.class, "test").orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isInstanceOf(Exception.class, "error").orElseThrow(); // -> OK
Assertor.that(new IOException()).isInstanceOf(Exception.class, null).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isInstanceOf(IOException.class, "error").orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).isInstanceOf(Exception.class, "tes").orElseThrow(); // -> throws an exception

Assertor.that(new IOException("test")).isInstanceOf(Exception.class, Pattern.compile("^t.*t$")).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isInstanceOf(Exception.class, Pattern.compile("^e.*$")).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).isInstanceOf(IOException.class, Pattern.compile("^x.*$")).orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).isInstanceOf(Exception.class, Pattern.compile("^t")).orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).isInstanceOf(Exception.class, "error").orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isInstanceOf(null, "error").orElseThrow(); // -> throws an exception

Assertor.that((Exception) null).isInstanceOf(Exception.class, Pattern.compile("^e.*$")).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isInstanceOf(null, Pattern.compile("^e.*$")).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).isInstanceOf(Exception.class, (Pattern) null).orElseThrow(); // -> throws an exception
```

#### hasCauseNull
Assert that throwable has a cause.
* Signatures:
	- `hasCauseNull()`
	- `hasCauseNull(CharSequence message, Object[] arguments)`
	- `hasCauseNull(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- throwable NOT null

* Examples:
```java
Assertor.that(new IOException(new Exception("test"))).hasCauseNull().orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).hasCauseNull().orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((Exception) null).hasCauseNull().orElseThrow(); // -> throws an exception
```

#### hasCauseNotNull
Assert that throwable has no cause.
* Signatures:
	- `hasCauseNotNull()`
	- `hasCauseNotNull(CharSequence message, Object[] arguments)`
	- `hasCauseNotNull(Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- throwable NOT null

* Examples:
```java
Assertor.that(new IOException(new Exception("test"))).hasCauseNotNull().orElseThrow(); // -> OK
Assertor.that(new IOException("test")).hasCauseNotNull().orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasCauseNotNull().orElseThrow(); // -> throws an exception
```

#### hasCauseAssignableFrom
Assert that throwable is assignable from clazz and has the specified message.
If recursively is set to true, the cause of exception are checked recursively until cause matches.
* Signatures:
	- `hasCauseAssignableFrom(Class<?> clazz, CharSequence throwableMessage, boolean recursively)`
	- `hasCauseAssignableFrom(Class<?> clazz, CharSequence throwableMessage, boolean recursively, CharSequence message, Object[] arguments)`
	- `hasCauseAssignableFrom(Class<?> clazz, CharSequence throwableMessage, boolean recursively, Locale locale, CharSequence message, Object[] arguments)`
	- `hasCauseAssignableFrom(Class<?> clazz, Pattern pattern, boolean recursively)`
	- `hasCauseAssignableFrom(Class<?> clazz, Pattern pattern, boolean recursively, CharSequence message, Object[] arguments)`
	- `hasCauseAssignableFrom(Class<?> clazz, Pattern pattern, boolean recursively, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- throwable NOT null
	- class NOT null
	- pattern NOT null (only for pattern signature)

* Examples:
```java
Assertor.that(new IOException("test")).hasCauseAssignableFrom(Exception.class, "test", false).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).hasCauseAssignableFrom(Exception.class, "error", false).orElseThrow(); // -> OK
Assertor.that(new Exception(new IOException("error"))).hasCauseAssignableFrom(Exception.class, "error", true).orElseThrow(); // -> OK
Assertor.that(new Exception(new IOException("error"))).hasCauseAssignableFrom(IOException.class, "error", false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException()).hasCauseAssignableFrom(Exception.class, null, false).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).hasCauseAssignableFrom(IOException.class, "error", false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).hasCauseAssignableFrom(Exception.class, "tes, false").orElseThrow(); // -> throws an exception

Assertor.that(new IOException("test")).hasCauseAssignableFrom(Exception.class, Pattern.compile("^t.*t$"), false).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).hasCauseAssignableFrom(Exception.class, Pattern.compile("^e.*$"), false).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).hasCauseAssignableFrom(IOException.class, Pattern.compile("^e.*$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).hasCauseAssignableFrom(Exception.class, Pattern.compile("^t.*$"), false).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that((Exception) null).hasCauseAssignableFrom(Exception.class, "error", false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseAssignableFrom(null, "error", false).orElseThrow(); // -> throws an exception

Assertor.that((Exception) null).hasCauseAssignableFrom(Exception.class, Pattern.compile("^e.*$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseAssignableFrom(null, Pattern.compile("^e.*$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException()).hasCauseAssignableFrom(Exception.class, (Pattern) null, false).orElseThrow(); // -> throws an exception
```

#### hasCauseInstanceOf
Assert that throwable is an instance of object and has the specified message.
If recursively is set to true, the cause of exception are checked recursively until cause matches.
* Signatures:
	- `hasCauseInstanceOf(Class<?> clazz, CharSequence throwableMessage, boolean recursively)`
	- `hasCauseInstanceOf(Class<?> clazz, CharSequence throwableMessage, boolean recursively, CharSequence message, Object[] arguments)`
	- `hasCauseInstanceOf(Class<?> clazz, CharSequence throwableMessage, boolean recursively, Locale locale, CharSequence message, Object[] arguments)`
	- `hasCauseInstanceOf(Class<?> clazz, Pattern pattern, boolean recursively)`
	- `hasCauseInstanceOf(Class<?> clazz, Pattern pattern, boolean recursively, CharSequence message, Object[] arguments)`
	- `hasCauseInstanceOf(Class<?> clazz, Pattern pattern, boolean recursively, Locale locale, CharSequence message, Object[] arguments)`

* Prerequisites:
	- throwable NOT null
	- class NOT null
	- pattern NOT null (only for pattern signature)

* Examples:
```java
Assertor.that(new IOException("test")).hasCauseInstanceOf(Exception.class, "test", false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseInstanceOf(Exception.class, "error", false).orElseThrow(); // -> OK
Assertor.that(new Exception(new IOException("error"))).hasCauseInstanceOf(IOException.class, "error", true).orElseThrow(); // -> OK
Assertor.that(new Exception(new IOException("error"))).hasCauseInstanceOf(IOException.class, "error", false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException()).hasCauseInstanceOf(Exception.class, null, false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseInstanceOf(IOException.class, "error", false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).hasCauseInstanceOf(Exception.class, "tes, false").orElseThrow(); // -> throws an exception

Assertor.that(new IOException("test")).hasCauseInstanceOf(Exception.class, Pattern.compile("^t.*t$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseInstanceOf(Exception.class, Pattern.compile("^e.*$"), false).orElseThrow(); // -> OK
Assertor.that(new Exception("error")).hasCauseInstanceOf(IOException.class, Pattern.compile("^e.*$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException("test")).hasCauseInstanceOf(Exception.class, Pattern.compile("^t.*$"), false).orElseThrow(); // -> throws an exception

// prerequisite errors
Assertor.that((Exception) null).hasCauseInstanceOf(Exception.class, "error", false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseInstanceOf(null, "error", false).orElseThrow(); // -> throws an exception

Assertor.that((Exception) null).hasCauseInstanceOf(Exception.class, Pattern.compile("^e.*$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new Exception("error")).hasCauseInstanceOf(null, Pattern.compile("^e.*$"), false).orElseThrow(); // -> throws an exception
Assertor.that(new IOException()).hasCauseInstanceOf(Exception.class, (Pattern) null, false).orElseThrow(); // -> throws an exception
```

## TODO

- Allow new extension (change PredicateStep design)
- Build all messages in one step at the end only if all message locales are same

## License
Apache License, version 2.0