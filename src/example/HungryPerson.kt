package example

import reporter.Subscriber
import reporter.Subscription

class HungryPerson : Subscriber {

    // ...

    @Subscription
    fun onFoodReady(event: FoodReadyEvent) {
        println("Hungry Person says: I'm going to eat some ${event.foodName}!")
    }
}

