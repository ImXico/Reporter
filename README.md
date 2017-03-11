<img src="https://raw.githubusercontent.com/ImXico/Reporter/master/reporter-logo.png" width="373" height="98">

Tiny and lightweight event handling system for Java/Kotlin. It's an annotation-based implementation of the [observer pattern](https://en.wikipedia.org/wiki/Observer_pattern) that takes away all the boilerplate that usually comes with it, making it much quicker and easy to use.

--

### Events
Have all events implementing the ```Event``` interface.
```kotlin
class FoodReadyEvent(val foodName : String) : Event { ... }
```

### Subscribers
Every class that wants to subscribe to one or more ```Event``` must implement the ```Subscriber``` interface.
```kotlin
class HungryPerson : Subscriber { ... }
class SpyingNeighbour : Subscriber { ... }
```

### Subscriptions
Every method that is listening to events should be marked with the ```@Subscription``` annotation.

Those methods should only have **one** parameter, and that parameter should be an implementation of an ```Event```.
```kotlin
class HungryPerson : Subscriber {
 
    @Subscription
    fun onFoodReady(event: FoodReadyEvent) {
        println("Hungry Person says: I'm going to eat some ${event.foodName}!")
    }
}
```
```ruby
class SpyingNeighbour : Subscriber {

    @Subscription
    fun onOtherNeighbourFoodReady(event: FoodReadyEvent) {
        println("Spying Neighbour says: Looks like he's about to eat ${event.foodName}...")
    }
}
```

### Reporter
```Reporter``` provides the following public methods:
````kotlin
Reporter.register(subscriber: Subscriber)
Reporter.unregister(subscriber: Subscriber)
Reporter.report(event: Event)
Report.clearAll()
```


## Test Run
The code for this whole example is [here](https://github.com/ImXico/Reporter/tree/master/src/example).
```kotlin
fun main(args: Array<String>) {
    
    val hungryPerson: HungryPerson = HungryPerson()
    val spyingNeighbour: SpyingNeighbour = SpyingNeighbour()
    
    Reporter.register(hungryPerson)
    Reporter.register(spyingNeighbour)
    
    Reporter.report(FoodReadyEvent(foodName = "Broccoli"))
    
}
```

#### Output
```
Hungry Person says: I'm going to eat some Broccoli!
Spying Neighbour says: Looks like he's about to eat Broccoli.
```
