# Reporter

[![Build Status](https://travis-ci.org/ImXico/reporter.svg?branch=master)](https://travis-ci.org/ImXico/reporter)
[![kotlin](https://img.shields.io/badge/kotlin-1.1.0-orange.svg)](https://kotlinlang.org/)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/ImXico/reporter/blob/master/LICENSE.md)

> 🎤 Easy and painless event handling for Java/Kotlin.

Tiny and lightweight event handling system for Java/Kotlin. It's an annotation-based implementation of the [observer pattern](https://en.wikipedia.org/wiki/Observer_pattern) that takes away all the boilerplate that usually comes with it, making it super easy to use.

### Events
Have all events implementing the `Event` interface.
```kotlin
class PersonOnlineEvent(val name: String) : Event
```

### Subscribers
Every class that wants to subscribe to one or more ```Event``` must implement the ```Subscriber``` interface.
```kotlin
class Bob : Subscriber
class Joe : Subscriber
```

### Subscriptions
Every method that is listening to events should be marked with the `@Subscription` annotation.

Those methods should only have **one** parameter, and that parameter should be an implementation of an `Event`.
```kotlin
class Bob : Subscriber {

  @Subscription
  fun greet(event: PersonOnlineEvent) = println("Bob says: Hi, ${event.name}!")
}
```
```kotlin
class Joe : Subscriber {
  
  @Subscription
  fun greet(event: PersonOnlineEvent) = println("Joe says: Hey ${event.name}.")
}
```

## Sample Usage
```kotlin
val bob: Bob = Bob()
val joe: Joe = Joe()
Reporter.registerAll(bob, joe)
Reporter.report(PersonOnlineEvent(name = "Jon"))
```
```
Bob says: Hi, Jon!
Joe says: Hey Jon.
```
