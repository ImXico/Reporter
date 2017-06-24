package example

import reporter.Reporter

fun main(args: Array<String>) {

  // ...

  val hungryPerson: HungryPerson = HungryPerson()
  val spyingNeighbour: SpyingNeighbour = SpyingNeighbour()

  Reporter.register(hungryPerson)
  Reporter.register(spyingNeighbour)

  // ...

  Reporter.report(FoodReadyEvent(foodName = "Broccoli"))
}
