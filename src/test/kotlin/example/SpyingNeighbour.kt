package example

import reporter.Subscriber
import reporter.Subscription

class SpyingNeighbour : Subscriber {

    // ...

    @Subscription
    fun onOtherNeighbourFoodReady(event: FoodReadyEvent) {
        println("Spying Neighbour says: Looks like he's about to eat ${event.foodName}.")
    }
}

