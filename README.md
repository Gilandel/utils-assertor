# utils-assertor

[![Build Status](https://api.travis-ci.org/Gilandel/utils-assertor.svg?branch=master)](https://travis-ci.org/Gilandel/utils-assertor)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/e34c82e78aaf45a797721e62a7a31a0a)](https://www.codacy.com/app/gilles/utils-assertor)
[![Dependency Status](https://www.versioneye.com/user/projects/58b29b6f7b9e15003a17e544/badge.svg?style=flat)](https://www.versioneye.com/user/projects/58b29b6f7b9e15003a17e544)
[![codecov.io](https://codecov.io/github/Gilandel/utils-assertor/coverage.svg?branch=master)](https://codecov.io/github/Gilandel/utils-assertor?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-assertor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-assertor)

Work progress:
![Code status](http://vbc3.com/script/progressbar.php?text=Code&progress=100)
![Test status](http://vbc3.com/script/progressbar.php?text=Test&progress=100)
![JavaDoc status](http://vbc3.com/script/progressbar.php?text=JavaDoc&progress=100)

```xml
<dependency>
	<groupId>fr.landel.utils</groupId>
	<artifactId>utils-assertor</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Summary

1. [Summary](#summary)
2. [Description](#description)
  1. [Structure](#structure)
  2. [Reset explanations](#reset-explanations)
  3. [Message (locale, arguments and parameters)](#message-locale-arguments-and-parameters)
3. [Output details](#output-details)
  1. [orElseThrow](#orelsethrow)
  2. [isOK](#isok)
  3. [getErrors](#geterrors)
  4. [get](#get)
  5. [getNullable](#getnullable)
  6. [asResult](#asresult)
  7. [asDefault](#asdefault)
4. [Operators](#operators)
  1. [NOT](#not)
  2. [AND](#and)
  3. [OR](#or)
  4. [XOR](#xor)
  5. [NAND](#nand)
  6. [NOR](#nor)
5. [Available methods](#available-methods)
  1. [For all (Object, Boolean...)](#for-all-object-boolean)
    1. [isNull](#isnull)
    2. [isNotNull](#isnotnull)
    3. [isEqual](#isequal)
    4. [isNotEqual](#isnotequal)
    5. [isInstance](#isinstance)
    6. [isAssignableFrom](#isassignablefrom)
    7. [hasHashCode](#hashashcode)
    8. [validates](#validates)
  2. [Array](#array)
    1. [hasLength](#haslength)
    2. [isEmpty](#isempty)
    3. [isNotEmpty](#isnotempty)
    4. [contains](#contains)
    5. [containsAll](#containsall)
    6. [containsAny](#containsany)
  3. [Boolean](#boolean)
    1. [isTrue](#istrue)
    2. [isFalse](#isfalse)
  4. [CharSequence](#charsequence)
    1. [hasLength](#haslength)
    2. [isEmpty](#isempty-1)
    3. [isNotEmpty](#isnotempty-1)
    4. [isBlank](#isblank)
    5. [isNotBlank](#isnotblank)
    6. [isEqual](#isequal-1)
    7. [isNotEqual](#isnotequal-1)
    8. [isEqualIgnoreCase](#isequalignorecase)
    9. [isNotEqualIgnoreCase](#isnotequalignorecase)
    10. [isEqualIgnoreLineReturns](#isequalignorelinereturns)
    11. [isNotEqualIgnoreLineReturns](#isnotequalignorelinereturns)
    12. [isEqualIgnoreCaseAndLineReturns](#isequalignorecaseandlinereturns)
    13. [isNotEqualIgnoreCaseAndLineReturns](#isnotequalignorecaseandlinereturns)
    14. [contains](#contains-1)
    15. [startsWith](#startswith)
    16. [startsWithIgnoreCase](#startswithignorecase)
    17. [endsWith](#endswith)
    18. [endsWithIgnoreCase](#endswithignorecase)
    19. [matches](#matches)
    20. [find](#find)
  5. [Class](#class)
    1. [isAssignableFrom](#isassignablefrom-1)
    2. [hasName](#hasname)
    3. [hasSimpleName](#hassimplename)
    4. [hasCanonicalName](#hascanonicalname)
    5. [hasPackageName](#haspackagename)
    6. [hasTypeName](#hastypename)
  6. [Date & Calendar](#date-calendar)
    1. [isAround](#isaround)
    2. [isNotAround](#isnotaround)
    3. [isAfter](#isafter)
    4. [isAfterOrEqual](#isafterorequal)
    5. [isBefore](#isbefore)
    6. [isBeforeOrEqual](#isbeforeorequal)
  7. [Temporal](#temporal)
    1. [isAround](#isaround-1)
    2. [isNotAround](#isnotaround-1)
    3. [isAfter](#isafter-1)
    4. [isAfterOrEqual](#isafterorequal-1)
    5. [isBefore](#isbefore-1)
    6. [isBeforeOrEqual](#isbeforeorequal-1)
  8. [Enum](#enum)
    1. [hasName](#hasname-1)
    2. [hasNameIgnoreCase](#hasnameignorecase)
    3. [hasOrdinal](#hasordinal)
  9. [Iterable](#iterable)
    1. [hasSize](#hassize)
    2. [isEmpty](#isempty-1)
    3. [isNotEmpty](#isnotempty-1)
    4. [contains](#contains-2)
    5. [containsAll](#containsall-1)
    6. [containsAny](#containsany-1)
  10. [Map](#map)
    1. [hasSize](#hassize-1)
    2. [isEmpty](#isempty-2)
    3. [isNotEmpty](#isnotempty-2)
    4. [contains](#contains-3)
    5. [containsAll](#containsall-2)
    6. [containsAny](#containsany-2)
  11. [Number](#number)
    1. [isEqual](#isequal-2)
    2. [isNotEqual](#isnotequal-2)
    3. [isZero](#iszero)
    4. [isPositive](#ispositive)
    5. [isNegative](#isnegative)
    6. [isGT](#isgt)
    7. [isGTE](#isgte)
    8. [isLT](#islt)
    9. [isLTE](#islte)
6. [Others](#others)
  1. [Expect](#expect)
7. [TODO](#todo)

## Description

This module allow to assert parameters.

For now it manages:
- Object (all other objects)
- Array
- Boolean
- CharSequence (String, StringBuilder...)
- Class
- Date & Calendar
- Enum
- Iterable (Set, List...)
- Map
- Number (Byte, Short, Integer, Long, Float, Double, BigInteger, BigDecimal)

### Structure

All assertions start with 'Assertor.that(object)' and following the type of the object, some methods are available.

About structure, an assertion can be cut in three parts:
- The definition of what we check: Assertor.that(myObject))...
- The check: ...isNull().or().isInstance(Color.class)...
- The output: ...orElseThrow()

Multiples objects can be check in the same line:
```java
Assertor.that(object1).isNull().and(object2).isNotNull().orElseThrow();
Assertor.that(object1).isNull().or().not().isInstance(Color.class).or(object2).isEqual(object3).isOk();
```

Mulitple outputs are available:
- orElseThrow: throw an exception if assertion is false, otherwise returns the last checked parameter,
- isOk: get the boolean result of the assertion,
- getErrors: get the error message (java.util.Optional),
- get: the result (java.util.Optional),
- result: the result (fr.landel.utils.commons.Result).

These three output methods are considerate as final.
So when these methods are called a clear of intermediate conditions is done.

### Reset explanations

Like explain in the previous chapter, to avoid the clearing of intermediate steps, the parameter 'reset' can be set to 'false' (default: true).
A about the clearing, only the checked value is kept, any intermediate checks are cleared.
```java
AssertCharSequence<String> assertion = Assertor.that("text1");
assertion.isBlank().and("text2").isNotEmpty().isOK(); // returns false
assertion.isNotBlank().isOK(); // returns true (all assertions are cleared, for 'text1' and 'text2') 
assertion.isBlank().and("text2").isNotEmpty().isOK(false); // returns false (reset set to false)
assertion.isNotBlank().isOK(); // returns false (here the isNotBlank call is linked to the previous through the operator AND)
// the last line is equivalent to:
Assertor.that("text1").isBlank().and().isNotBlank().isOK(); // the second check "text2" is lost
```

### Message (locale, arguments and parameters)

In each method, that manages intermediate errors (isBlank, contains...) or final errors (orElseThrow...) a locale can be specified.
The locale can be used to manage number and date (see [String.format](http://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html)).

Also parameters and arguments can be injected.
Parameters are all the variables and other parameters sent to be used during the check.
Arguments are the message arguments.
```java
String text = "text";
...
Assertor.that(text).hasLength(5, "Bad length: '%1$d', expected: '%2$d*', text: '%1$s*'", text.length()).getErrors();
// "text" is the first parameter
// 5 is the second parameter
// 4 is the first argument
// Message thrown -> "Bad length '4' expected '5' for word 'text'"
```

As the previous example demonstrates it, parameters can be injected.
The syntax is exactly the same as default [String.format](http://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html) arguments, just suffix it by the character asterisk/star '*'.

## Output details

### orElseThrow
Throw an exception if the assertion is false, otherwise returns the last checked value.
Three ways to personalize the exception exist:
- a message:
	The message can be personalized via arguments injection and locale.
	Back-side the method String.format will be called with these arguments.
	Parameters can also be injected.
- an exception:
	An exception can be also used (default: IllegalArgumentException).
- a function:
	A bi-function (a function with two parameters) can be passed.
	This function is only called if statement is false.
	The two parameters received are the combined errors messages and all the parameters.

* Signatures:
	- orElseThrow()
	- orElseThrow(CharSequence message, Object... arguments)
	- orElseThrow(Locale locale, CharSequence message, Object... arguments)
	- orElseThrow(E exception)
	- orElseThrow(BiFunction<CharSequence, Object[], E> exceptionBuilder)
	- orElseThrow(boolean reset)
	- orElseThrow(boolean reset, CharSequence message, Object... arguments)
	- orElseThrow(boolean reset, Locale locale, CharSequence message, Object... arguments)
	- orElseThrow(boolean reset, E exception)
	- orElseThrow(boolean reset, BiFunction<CharSequence, Object[], E> exceptionBuilder)

* Examples:
```java
Assertor.that("text").isNotBlank().orElseThrow(); // -> returns "text" instance
Assertor.that("text").isNotBlank().or(12).isGT(11).orElseThrow(); // -> returns 12

Assertor.that("").isNotBlank().orElseThrow(); // -> throw the default message 'the char sequence should be NOT null, NOT empty and NOT blank'
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow(); // -> throw the personalized message 'The first name is invalid'

Assertor.that("").isNotBlank().orElseThrow("Invalid field"); // -> throw the personalized message 'Invalid field'
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow("Invalid field"); // -> throw the personalized message 'Invalid field'

Assertor.that("").isNotBlank().orElseThrow(Locale.FRANCE, "Invalid field (%.2fms)", 2.356); // -> throw the personalized message 'Invalid field (2,36ms)'
Assertor.that("").isNotBlank("The first name is invalid").orElseThrow(Locale.FRANCE, "Invalid field (%.2fms)", 2.356); // -> throw the personalized message 'Invalid field (2,36ms)'

Assertor.that("").isNotBlank().orElseThrow(new IOException("Invalid data")); // -> throw the personalized exception
Assertor.that("").isNotBlank(The first name is invalid").orElseThrow(new IOException("Invalid data")); -> throw the personalized exception

Assertor.that("text").isBlank().orElseThrow((errors, parameters) -> new MyException("text should be blank")); // -> throw a MyException with message: text should be blank
// 'errors' contains: the char sequence 'text' should be null, empty or blank
// 'parameters' contains: [{"text", EnumType.CHAR_SEQUENCE}]

Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank().orElseThrow((errors, parameters) -> new MyException(errors)); // -> throw a MyException
// 'errors' contains: the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte12' should NOT start with 'text'" OR the char sequence 'texte12' should be null, empty or blank
// 'parameters' contains: [{"texte11", EnumType.CHAR_SEQUENCE}, {"texte12", EnumType.CHAR_SEQUENCE}, {"text", EnumType.CHAR_SEQUENCE}]
// to display the first parameter in MyException call: parameters.get(0).getKey()

```

As explain at the end of the description section, the reset parameter can be set to 'false' through these methods.
```java
Assertor.that("").isNotBlank().orElseThrow(false);
```

This mean:
```java
boolean reset = false;
Operator<AssertCharSequence<String>, String> operator = Assertor.that("").isNotBlank();
if (!operator.isOk(reset)) {
	LOGGER.error(operator.getErrors(reset));
	operator.orElseThrow(); // only here the assertion is cleared
	// if we catch exception, and retry at this point 'operator.orElseThrow()', no exception will be thrown
}
```

### isOK
This method returns 'true' if the assertion is valid, otherwise returns 'false'.

* Signatures:
	- isOk()
	- isOK(boolean reset)

* Examples:
```java
	Assertor.that("").isNotBlank().isOK(); // -> return false
	Assertor.that("").isBlank("The first name is invalid").isOK(); // -> return true
```

At the call of 'isOK()', the assertion is cleared, to avoid this, the parameter 'reset' can be set to 'false' (default: true).

### getErrors
This method returns the assertion errors.

* Signatures:
	- getErrors()
	- getErrors(boolean reset)

* Examples:
```java
Assertor.that("").isNotBlank().getErrors(); // -> return Optional.of("the char sequence should be NOT null, NOT empty and NOT blank")
Assertor.that("").isBlank("The first name is invalid").getErrors(); // -> return Optional.empty()
```

At the call of 'getErrors()', the assertion is cleared, to avoid this, the parameter 'reset' can be set to 'false' (default: true).

### get
To get the last checked object as an Optional (java.util.Optional).

* Signatures:
	- get()

* Examples:
```java
Assertor.that(object).isNotNull().get() // => returns an Optional.ofNullable(object) if matches or Optional.empty()
Assertor.that(object1).isNotNull().and(object2).isInstanceOf(MyClass.class).get() // => returns an Optional.ofNullable(object2) if matches or Optional.empty()
```

### getNullable
To get the last checked object.

* Signatures:
	- getNullable()

* Examples:
```java
Assertor.that(object).isNotNull().getNullable() // => returns 'object' if matches or 'null'
Assertor.that(object1).isNotNull().and(object2).isInstanceOf(MyClass.class).getNullable() // => returns 'object2' if matches or 'null'
```

### asResult
To get the last checked object as a Result (fr.landel.utils.commons.Result).
The aim is to differentiate if assertor returns null, if it's valid or not.

* Signatures:
	- asResult()

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
	- asDefault(T defaultValue)

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
	- not()

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
	- and()
	- and(Object object)

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
	- or()
	- or(Object object)

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
	- xor()
	- xor(Object object)

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
	- nand()
	- nand(Object object)

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
	- nor()
	- nor(Object object)

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
	- isNull()
	- isNull(CharSequence message, Object[] arguments)
	- isNull(Locale locale, CharSequence message, Object[] arguments)

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
	- isNotNull()
	- isNotNull(CharSequence message, Object[] arguments)
	- isNotNull(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that(object).isNotNull().orElseThrow();
Assertor.that(name).isNotNull("Name cannot be null").orElseThrow();
```

#### isEqual
Assert that the object is equal to another.

* Signatures:
	- isEqual(Object object)
	- isEqual(Object object, CharSequence message, Object[] arguments)
	- isEqual(Object object, Locale locale, CharSequence message, Object[] arguments)

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
	- isNotEqual(Object object)
	- isNotEqual(Object object, CharSequence message, Object[] arguments)
	- isNotEqual(Object object, Locale locale, CharSequence message, Object[] arguments)

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
	- isInstance(Class class)
	- isInstance(Class class, CharSequence message, Object[] arguments)
	- isInstance(Class class, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- checked object NOT null
	- class NOT null

* Examples:
```java
Assertor.that(object).isInstance(class1).orElseThrow();
Assertor.that(object).isInstance(class1, "Input is not an instance of the class '%2$s*'").orElseThrow();

// prerequisite errors
Assertor.that(null).isInstance(class1).orElseThrow(); // -> throw an exception
Assertor.that(object).isInstance(null).orElseThrow(); // -> throw an exception
Assertor.that(null).not().isInstance(class1).orElseThrow(); // -> throw an exception
Assertor.that(object).not().isInstance(null).orElseThrow(); // -> throw an exception
```

#### isAssignableFrom
Assert that the object is assignable from the specified class.

* Signatures:
	- isAssignableFrom(Class class)
	- isAssignableFrom(Class class, CharSequence message, Object[] arguments)
	- isAssignableFrom(Class class, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- checked object NOT null
	- class NOT null

* Examples:
```java
Assertor.that(object).isAssignableFrom(class1).orElseThrow();
Assertor.that(object).isAssignableFrom(class1, "Input is not assignable from the class '%2$s*'").orElseThrow();

// prerequisite errors
Assertor.that(null).isAssignableFrom(class1).orElseThrow(); // -> throw an exception
Assertor.that(object).isAssignableFrom(null).orElseThrow(); // -> throw an exception
Assertor.that(null).not().isAssignableFrom(class1).orElseThrow(); // -> throw an exception
Assertor.that(object).not().isAssignableFrom(null).orElseThrow(); // -> throw an exception
```

#### hasHashCode
Assert that the object hash code equals the specified value.

* Signatures:
	- matches(int hashCode)
	- matches(int hashCode, CharSequence message, Object[] arguments)
	- matches(int hashCode, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that(colors).hasHashCode(0).orElseThrow();
Assertor.that(colors).hasHashCode(45, "The hash codes don't match (%d != %d*)", Objects.hashCode(colors)).orElseThrow();
```

#### validates
Assert that the object validates the predicate.

* Signatures:
	- validates(PredicateThrowable<T, E> predicate)
	- validates(PredicateThrowable<T, E> predicate, CharSequence message, Object[] arguments)
	- validates(PredicateThrowable<T, E> predicate, Locale locale, CharSequence message, Object[] arguments)

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
Assertor.that(object).validates(null).orElseThrow(); // -> throw an exception
Assertor.that(object).not().validates(null).orElseThrow(); // -> throw an exception
```

### Array
#### hasLength
#### isEmpty
#### isNotEmpty
#### contains
#### containsAll
#### containsAny

### Boolean

#### isTrue
Assert that the boolean is true.

* Signatures:
	- isTrue()
	- isTrue(CharSequence message, Object[] arguments)
	- isTrue(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that(bool).isTrue().orElseThrow(); // -> throw an exception, if bool == true
Assertor.that(false).isTrue("Bad status").orElseThrow(); // -> OK
Assertor.that(true).not().isTrue("Bad status").orElseThrow(); // -> OK
```

#### isFalse
Assert that the boolean is false.

* Signatures:
	- isFalse()
	- isFalse(CharSequence message, Object[] arguments)
	- isFalse(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that(bool).isFalse().orElseThrow(); // -> throw an exception, if bool == true
Assertor.that(false).isFalse("Bad status").orElseThrow(); // -> OK
Assertor.that(true).not().isFalse("Bad status").orElseThrow(); // -> OK
```

### CharSequence
#### hasLength
Assert that char sequence has the specified length.

* Signatures:
	- hasLength(int length)
	- hasLength(int length, CharSequence message, Object[] arguments)
	- hasLength(int length, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- length >= 0

* Examples:
```java
Assertor.that("text").hasLength(3).orElseThrow(); // -> throw an exception
Assertor.that("text").hasLength(4, "Bad status").orElseThrow(); // -> OK
Assertor.that("text").not().hasLength(3).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).hasLength(4, "Bad status").orElseThrow(); // -> throw an exception
Assertor.that("text").hasLength(-1, "Bad status").orElseThrow(); // -> throw an exception
Assertor.that(null).not().hasLength(4, "Bad status").orElseThrow(); // -> throw an exception
Assertor.that("text").not().hasLength(-1, "Bad status").orElseThrow(); // -> throw an exception
```

#### isEmpty
Assert that char sequence is empty or null.

* Signatures:
	- isEmpty()
	- isEmpty(CharSequence message, Object[] arguments)
	- isEmpty(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that("text").isEmpty().orElseThrow(); // -> throw an exception
Assertor.that((CharSequence) null).isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that("").isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
Assertor.that("text").not().isEmpty("Param '%1$s*' not empty").orElseThrow(); // -> OK
```

#### isNotEmpty
Assert that char sequence is NOT empty and NOT null.

* Signatures:
	- isNotEmpty()
	- isNotEmpty(CharSequence message, Object[] arguments)
	- isNotEmpty(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that((CharSequence) null).isNotEmpty().orElseThrow(); // -> throw an exception
Assertor.that("").isNotEmpty().orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEmpty("Param '%1$s*' empty or null").orElseThrow(); // -> OK

```

#### isBlank
Assert that char sequence is blank or empty or null.

* Signatures:
	- isBlank()
	- isBlank(CharSequence message, Object[] arguments)
	- isBlank(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that("text").isBlank().orElseThrow(); // -> throw an exception
Assertor.that(null).isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("").isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("   ").isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("text").not().isBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
```

#### isNotBlank
Assert that char sequence is NOT blank and NOT empty and NOT null.

* Signatures:
	- isNotBlank()
	- isNotBlank(CharSequence message, Object[] arguments)
	- isNotBlank(Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: None

* Examples:
```java
Assertor.that("text").isNotBlank().orElseThrow(); // -> OK
Assertor.that("text").isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> OK
Assertor.that("text").isNotBlank().orElseThrow(); // -> OK
Assertor.that(null).isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("").isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("   ").isNotBlank("Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### isEqual
Assert that char sequence is equal to the string

* Signatures:
	- isEqual(CharSequence string)
	- isEqual(CharSequence string, CharSequence message, Object[] arguments)
	- isEqual(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqual("text").orElseThrow(); // -> OK
Assertor.that("text").isEqual("ex", "Param '%1$s*' not equal").orElseThrow(); -> throw an exception
Assertor.that("text").isEqual("TexT").orElseThrow(); -> throw an exception
Assertor.that("text").isEqual("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().isEqual("text").orElseThrow(); // -> throw an exception
```

#### isNotEqual
Assert that char sequence is NOT equal to the string

* Signatures:
	- isNotEqual(CharSequence string)
	- isNotEqual(CharSequence string, CharSequence message, Object[] arguments)
	- isNotEqual(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqual("text").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqual("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqual("TexT").orElseThrow(); // -> OK
Assertor.that("text").isNotEqual("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqual("text").orElseThrow(); -> throw an exception
```

#### isEqualIgnoreCase
Assert that char sequence is equal to the string, ignoring case considerations

* Signatures:
	- isEqualIgnoreCase(CharSequence string)
	- isEqualIgnoreCase(CharSequence string, CharSequence message, Object[] arguments)
	- isEqualIgnoreCase(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqualIgnoreCase("text").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCase("ex", "Param '%1$s*' equal").orElseThrow(); // -> throw an exception
Assertor.that("text").isEqualIgnoreCase("TexT").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCase("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().isEqualIgnoreCase("text").orElseThrow(); -> throw an exception

#### isNotEqualIgnoreCase
Assert that char sequence is NOT equal to the string, ignoring case considerations

* Signatures:
	- isNotEqualIgnoreCase(CharSequence string)
	- isNotEqualIgnoreCase(CharSequence string, CharSequence message, Object[] arguments)
	- isNotEqualIgnoreCase(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqualIgnoreCase("text").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreCase("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreCase("TexT").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreCase("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqualIgnoreCase("text").orElseThrow(); -> OK
```

#### isEqualIgnoreLineReturns
Assert that char sequence is equal to the string, ignoring line returns considerations

* Signatures:
	- isEqualIgnoreLineReturns(CharSequence string)
	- isEqualIgnoreLineReturns(CharSequence string, CharSequence message, Object[] arguments)
	- isEqualIgnoreLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqualIgnoreLineReturns("text").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> throw an exception
Assertor.that("text").isEqualIgnoreLineReturns("tex\nt").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreLineReturns("Tex\nT").orElseThrow(); // -> throw an exception
Assertor.that("text").isEqualIgnoreLineReturns("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().isEqualIgnoreLineReturns("text").orElseThrow(); -> throw an exception
```

#### isNotEqualIgnoreLineReturns
Assert that char sequence is NOT equal to the string, ignoring line returns considerations

* Signatures:
	- isNotEqualIgnoreLineReturns(CharSequence string)
	- isNotEqualIgnoreLineReturns(CharSequence string, CharSequence message, Object[] arguments)
	- isNotEqualIgnoreLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqualIgnoreLineReturns("text").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreLineReturns("tex\nt").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreLineReturns("Tex\nT").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreLineReturns("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqualIgnoreLineReturns("text").orElseThrow(); -> OK
```

#### isEqualIgnoreCaseAndLineReturns
Assert that char sequence is equal to the string, ignoring case and line returns considerations

* Signatures:
	- isEqualIgnoreCaseAndLineReturns(CharSequence string)
	- isEqualIgnoreCaseAndLineReturns(CharSequence string, CharSequence message, Object[] arguments)
	- isEqualIgnoreCaseAndLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("text").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> throw an exception
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("tex\nt").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("Tex\nT").orElseThrow(); // -> OK
Assertor.that("text").isEqualIgnoreCaseAndLineReturns("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().isEqualIgnoreCaseAndLineReturns("text").orElseThrow(); -> throw an exception
```

#### isNotEqualIgnoreCaseAndLineReturns
Assert that char sequence is NOT equal to the string, ignoring case and line returns considerations

* Signatures:
	- isNotEqualIgnoreCaseAndLineReturns(CharSequence string)
	- isNotEqualIgnoreCaseAndLineReturns(CharSequence string, CharSequence message, Object[] arguments)
	- isNotEqualIgnoreCaseAndLineReturns(CharSequence string, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites: none

* Examples:
```java
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("text").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("ex", "Param '%1$s*' equal").orElseThrow(); // -> OK
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("tex\nt").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("Tex\nT").orElseThrow(); // -> throw an exception
Assertor.that("text").isNotEqualIgnoreCaseAndLineReturns("y").orElseThrow(); // -> OK
Assertor.that("text").not().isNotEqualIgnoreCaseAndLineReturns("text").orElseThrow(); -> OK
```

#### contains
Assert that char sequence contains the substring.

* Signatures:
	- contains(CharSequence substring)
	- contains(CharSequence substring, CharSequence message, Object[] arguments)
	- contains(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").contains("t").orElseThrow(); // -> OK
Assertor.that("text").contains("ex", "Param '%1$s*' not contains '%2$s*'").orElseThrow(); // -> OK
Assertor.that("text").contains("text").orElseThrow(); // -> OK
Assertor.that("text").contains("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().contains("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).contains("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").contains(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").contains("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().contains("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().contains(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().contains("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### startsWith
Assert that char sequence starts with the substring.
* Signatures:
	- startsWith(CharSequence substring)
	- startsWith(CharSequence substring, CharSequence message, Object[] arguments)
	- startsWith(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").startsWith("t").orElseThrow(); // -> OK
Assertor.that("text").startsWith("T").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWith("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWith("text").orElseThrow(); // -> OK
Assertor.that("text").startsWith("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().startsWith("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).startsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().startsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().startsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().startsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### startsWithIgnoreCase
Assert that char sequence starts with the substring (case insensitive).
* Signatures:
	- startsWithIgnoreCase(CharSequence substring)
	- startsWithIgnoreCase(CharSequence substring, CharSequence message, Object[] arguments)
	- startsWithIgnoreCase(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").startsWithIgnoreCase("t").orElseThrow(); // -> OK
Assertor.that("text").startsWithIgnoreCase("T").orElseThrow(); // -> OK
Assertor.that("text").startsWithIgnoreCase("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWithIgnoreCase("text").orElseThrow(); // -> OK
Assertor.that("text").startsWithIgnoreCase("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().startsWithIgnoreCase("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).startsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").startsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().startsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().startsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().startsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### endsWith
Assert that char sequence ends with the substring.
* Signatures:
	- endsWith(CharSequence substring)
	- endsWith(CharSequence substring, CharSequence message, Object[] arguments)
	- endsWith(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").endsWith("t").orElseThrow(); // -> OK
Assertor.that("text").endsWith("T").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWith("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWith("text").orElseThrow(); // -> OK
Assertor.that("text").endsWith("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().endsWith("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).endsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().endsWith("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().endsWith(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().endsWith("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### endsWithIgnoreCase
Assert that char sequence ends with the substring (case insensitive).
* Signatures:
	- endsWithIgnoreCase(CharSequence substring)
	- endsWithIgnoreCase(CharSequence substring, CharSequence message, Object[] arguments)
	- endsWithIgnoreCase(CharSequence substring, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- substring NOT null and NOT empty

* Examples:
```java
Assertor.that("text").endsWithIgnoreCase("t").orElseThrow(); // -> OK
Assertor.that("text").endsWithIgnoreCase("T").orElseThrow(); // -> OK
Assertor.that("text").endsWithIgnoreCase("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWithIgnoreCase("text").orElseThrow(); // -> OK
Assertor.that("text").endsWithIgnoreCase("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().endsWithIgnoreCase("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).endsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").endsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().endsWithIgnoreCase("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().endsWithIgnoreCase(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().endsWithIgnoreCase("", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### matches
Assert that char sequence matches the specified pattern / regex.
* Signatures:
	- matches(CharSequence regex)
	- matches(CharSequence regex, CharSequence message, Object[] arguments)
	- matches(CharSequence regex, Locale locale, CharSequence message, Object[] arguments)
	- matches(Pattern pattern)
	- matches(Pattern pattern, CharSequence message, Object[] arguments)
	- matches(Pattern pattern, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- pattern / regex NOT null

* Examples:
```java
Assertor.that("text").matches("[xet]{4}").orElseThrow(); // -> OK
Assertor.that("text").matches("[xet]{3}").orElseThrow(); // -> throw an exception
Assertor.that("text").matches("\\w+").orElseThrow(); // -> OK
Assertor.that("text").matches("xt").orElseThrow(); // -> throw an exception
Assertor.that("text").matches("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").matches("text").orElseThrow(); // -> OK
Assertor.that("text").matches("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().matches("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).matches("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").matches(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().matches("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().matches(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

#### find
Assert that char sequence contains the specified pattern / regex.
* Signatures:
	- find(CharSequence regex)
	- find(CharSequence regex, CharSequence message, Object[] arguments)
	- find(CharSequence regex, Locale locale, CharSequence message, Object[] arguments)
	- find(Pattern pattern)
	- find(Pattern pattern, CharSequence message, Object[] arguments)
	- find(Pattern pattern, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- char sequence NOT null
	- pattern / regex NOT null

* Examples:
```java
Assertor.that("text").find("[xet]{4}").orElseThrow(); // -> OK
Assertor.that("text").find("[xet]{3}").orElseThrow(); // -> OK
Assertor.that("text").find("\\w+").orElseThrow(); // -> OK
Assertor.that("text").find("xt").orElseThrow(); // -> OK
Assertor.that("text").find("ex", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").find("text").orElseThrow(); // -> OK
Assertor.that("text").find("y").orElseThrow(); // -> throw an exception
Assertor.that("text").not().find("y").orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).find("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").find(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that(null).not().find("t", "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
Assertor.that("text").not().find(null, "Param '%1$s*' not blank").orElseThrow(); // -> throw an exception
```

### Class
#### isAssignableFrom
Assert that class is assignable from .
* Signatures:
	- 

* Prerequisites:
	- char sequence NOT null
	- pattern / regex NOT null

* Examples:
```java
```

#### hasName
#### hasSimpleName
#### hasCanonicalName
#### hasPackageName
#### hasTypeName

### Date & Calendar
#### isAround
Assert that date1 is around the date2.
* Signatures:
	- isAround(Calendar date, int calendarField, int calendarAmount)
	- isAround(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)
	- isAround(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)
	- isAround(Date date, int calendarField, int calendarAmount)
	- isAround(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)
	- isAround(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- dates NOT null
	- calendarField valid (see calendar field, ex: Calendar.ERA, Calendar.YEAR...)
	- calendarAmount NOT equal to zero

* Examples:
```java
final Calendar date1 = Calendar.getInstance();
final Calendar date2 = Calendar.getInstance();

date1.set(2016, 05, 29, 5, 5, 6);
date2.set(2016, 05, 29, 5, 5, 5);

Assertor.that(date1).isAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK
Assertor.that(date1).isAround(date2, Calendar.MILLISECOND, 5).orElseThrow(); // -> throw an exception
Assertor.that(date1).not().isAround(date2, Calendar.MILLISECOND, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).isAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).isAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).isAround(date2, -100, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).isAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throw an exception (invalid calendarAmount)
Assertor.that(null).not().isAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).not().isAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).not().isAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).not().isAround(date2, -100, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).not().isAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throw an exception (invalid calendarAmount)
```

#### isNotAround
Assert that date1 is not around the date2.
* Signatures:
	- isNotAround(Calendar date, int calendarField, int calendarAmount)
	- isNotAround(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)
	- isNotAround(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)
	- isNotAround(Date date, int calendarField, int calendarAmount)
	- isNotAround(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)
	- isNotAround(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)

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
Assertor.that(date1).isNotAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> throw an exception
Assertor.that(date1).not().isNotAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).isNotAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).isNotAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).isNotAround(date2, -100, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).isNotAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throw an exception (invalid calendarAmount)
Assertor.that(null).not().isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).not().isNotAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).not().isNotAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).not().isNotAround(date2, -100, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).not().isNotAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throw an exception (invalid calendarAmount)
```

#### isAfter
Assert that date1 is after the date2.
* Signatures:
	- isAfter(Calendar date, int calendarField, int calendarAmount)
	- isAfter(Calendar date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)
	- isAfter(Calendar date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)
	- isAfter(Date date, int calendarField, int calendarAmount)
	- isAfter(Date date, int calendarField, int calendarAmount, CharSequence message, Object[] arguments)
	- isAfter(Date date, int calendarField, int calendarAmount, Locale locale, CharSequence message, Object[] arguments)

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
Assertor.that(date1).isNotAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> throw an exception
Assertor.that(date1).not().isNotAround(date2, Calendar.HOUR, 5).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).isNotAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).isNotAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).isNotAround(date2, -100, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).isNotAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throw an exception (invalid calendarAmount)
Assertor.that(null).not().isNotAround(date2, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).not().isNotAround(null, Calendar.SECOND, 5).orElseThrow(); // -> throw an exception (date null)
Assertor.that(date1).not().isNotAround(date2, Calendar.FIELD_COUNT, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).not().isNotAround(date2, -100, -5).orElseThrow(); // -> throw an exception (invalid calendarField)
Assertor.that(date1).not().isNotAround(date2, Calendar.HOUR, 0).orElseThrow(); // -> throw an exception (invalid calendarAmount)
```

#### isAfterOrEqual
#### isBefore
#### isBeforeOrEqual

### Temporal
#### isAround
#### isNotAround
#### isAfter
#### isAfterOrEqual
#### isBefore
#### isBeforeOrEqual

### Enum
#### hasName
#### hasNameIgnoreCase
#### hasOrdinal

### Iterable
#### hasSize
#### isEmpty
#### isNotEmpty
#### contains
#### containsAll
#### containsAny

### Map
#### hasSize
#### isEmpty
#### isNotEmpty
#### contains
#### containsAll
#### containsAny

### Number
#### isEqual
#### isNotEqual
#### isZero
#### isPositive
#### isNegative
#### isGT
Assert that number is greater than specified number.

* Signatures:
	- isGT(Number number)
	- isGT(Number number, CharSequence message, Object[] arguments)
	- isGT(Number number, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isGT(12).orElseThrow(); // -> throw an exception
Assertor.that(12).isGT(10, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isGT(12).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isGT(12).orElseThrow(); // -> throw an exception
Assertor.that(12).isGT(null).orElseThrow(); // -> throw an exception
Assertor.that(null).not().isGT(12).orElseThrow(); // -> throw an exception
Assertor.that(12).not().isGT(null).orElseThrow(); // -> throw an exception
```

#### isGTE
Assert that number is greater than or equal to specified number.

* Signatures:
	- isGTE(Number number)
	- isGTE(Number number, CharSequence message, Object[] arguments)
	- isGTE(Number number, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isGTE(13).orElseThrow(); // -> throw an exception
Assertor.that(12).isGTE(12).orElseThrow(); // -> OK
Assertor.that(12).isGTE(10, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isGTE(13).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isGTE(12).orElseThrow(); // -> throw an exception
Assertor.that(12).isGTE(null).orElseThrow(); // -> throw an exception
Assertor.that(null).not().isGTE(12).orElseThrow(); // -> throw an exception
Assertor.that(12).not().isGTE(null).orElseThrow(); // -> throw an exception
```

#### isLT
Assert that number is lower than specified number.

* Signatures:
	- isLT(Number number)
	- isLT(Number number, CharSequence message, Object[] arguments)
	- isLT(Number number, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isLT(12).orElseThrow(); // -> throw an exception
Assertor.that(12).isLT(13, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isLT(12).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isLT(12).orElseThrow(); // -> throw an exception
Assertor.that(12).isLT(null).orElseThrow(); // -> throw an exception
Assertor.that(null).not().isLT(12).orElseThrow(); // -> throw an exception
Assertor.that(12).not().isLT(null).orElseThrow(); // -> throw an exception
```

#### isLTE
Assert that number is lower than or equal to specified number.

* Signatures:
	- isLTE(Number number)
	- isLTE(Number number, CharSequence message, Object[] arguments)
	- isLTE(Number number, Locale locale, CharSequence message, Object[] arguments)

* Prerequisites:
	- both number NOT null

* Examples:
```java
Assertor.that(12).isLTE(11).orElseThrow(); // -> throw an exception
Assertor.that(12).isLTE(12).orElseThrow(); // -> OK
Assertor.that(12).isLTE(13, "Bad status").orElseThrow(); // -> OK
Assertor.that(12).not().isLTE(11).orElseThrow(); // -> OK

// prerequisite errors
Assertor.that(null).isLTE(12).orElseThrow(); // -> throw an exception
Assertor.that(12).isLTE(null).orElseThrow(); // -> throw an exception
Assertor.that(null).not().isLTE(12).orElseThrow(); // -> throw an exception
Assertor.that(12).not().isLTE(null).orElseThrow(); // -> throw an exception
```

## Others
### Expect
Validate a thrown exception and its message.

* Signatures:
	- exception(AssertConsumer<Throwable> consumer, Class<? extends Throwable> expectedException)
	- exception(AssertConsumer<Throwable> consumer, Class<? extends Throwable> expectedException, String expectedMessage)
	- exception(AssertConsumer<Throwable> consumer, Class<? extends Throwable> expectedException, final TriFunction<Boolean, String, String, E> exceptionFunction)
	- exception(AssertConsumer<Throwable> consumer, Class<? extends Throwable> expectedException, String expectedMessage, final TriFunction<Boolean, String, String, E> exceptionFunction)

* Parameters:
	- consumer: The consumer or where the code to checked can be placed
	- expectedException: The class of expected exception
	- expectedMessage: The expected exception message
	- exceptionFunction: The exception builder, only called on mismatch. Has 3 parameters:
		- first: true, if the expected exception matches
		- second: the expected message
		- third: the actual message

* Prerequisites:
	- consumer NOT null
	- expectedException NOT null

* Examples:
```java
Expect.exception(() -> {
    // throw new IllegalArgumentException("parameter cannot be null");
    getMyType(null);
}, IllegalArgumentException.class); // -> OK

Expect.exception(() -> {
    // throw new IOException("parameter cannot be null");
    getMyType(null);
}, IllegalArgumentException.class); // -> throw an ExpectException



Expect.exception(() -> {
     // throw new IllegalArgumentException("parameter cannot be null");
     getMyType(null);
 }, IllegalArgumentException.class, "parameter cannot be null"); // -> OK
 
 Expect.exception(() -> {
     // throw new IllegalArgumentException("type cannot be null");
     getMyType(null);
 }, IllegalArgumentException.class, "parameter cannot be null");  // -> throw an ExpectException



// Obviously, you can save this in a static variable to share it.
// This function is not provided by module to avoid a JUnit dependency.
// ComparisonFailure come from: org.junit.ComparisonFailure

TriFunction<Boolean, String, String> junitError = (catched, expected, actual) -> {
    if (catched) {
        return new ComparisonFailure("The exception message don't match.", expected, actual);
    } else {
        return new AssertionError("The expected exception never came up");
    }
};



Expect.exception(() -> {
    // throw new IllegalArgumentException("parameter cannot be null");
    getMyType(null);
}, IllegalArgumentException.class, junitError); // -> OK

Expect.exception(() -> {
    // throw new IOException("parameter cannot be null");
    getMyType(null);
}, IllegalArgumentException.class, junitError); // -> throw an AssertionError


Expect.exception(() -> {
     // throw new IllegalArgumentException("parameter cannot be null");
     getMyType(null);
 }, IllegalArgumentException.class, "parameter cannot be null", junitError); // -> OK
 
 Expect.exception(() -> {
     // throw new IllegalArgumentException("type cannot be null");
     getMyType(null);
 }, IllegalArgumentException.class, "parameter cannot be null", junitError);  // -> throw a ComparisonFailure
```

## TODO

- Build all messages in one step at the end (one call to String.format, which locale, if multiple?)