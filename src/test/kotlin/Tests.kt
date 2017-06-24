import example.HungryPerson
import example.SpyingNeighbour
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import reporter.Reporter

class Tests {

  private val hungryPerson: HungryPerson = HungryPerson()
  private val spyingNeighbour: SpyingNeighbour = SpyingNeighbour()

  @Before
  fun resetReporter() = Reporter.clearEverything()

  @Test
  fun successfullyRegistered() {
    Reporter.register(hungryPerson)
    assertTrue(Reporter.subscriberAlreadyRegistered(hungryPerson))
  }

  @Test
  fun successfullyUnregistered() {
    Reporter.unregister(hungryPerson)
    assertFalse(Reporter.subscriberAlreadyRegistered(hungryPerson))
  }

  @Test
  fun mapGetsCleared() {
    Reporter.register(hungryPerson)
    Reporter.clearEverything()
    assertTrue(Reporter.isEmpty())
  }

  @Test
  fun successfullyRegisteredMultiple() {
    Reporter.registerAll(hungryPerson, spyingNeighbour)
    val registeredOne: Boolean = Reporter.subscriberAlreadyRegistered(hungryPerson)
    val registeredTwo: Boolean = Reporter.subscriberAlreadyRegistered(spyingNeighbour)
    assertTrue(registeredOne && registeredTwo)
  }

  @Test
  fun successfullyUnregisteredMultiple() {
    Reporter.registerAll(hungryPerson, spyingNeighbour)
    Reporter.unregisterAll(hungryPerson, spyingNeighbour)
    assertTrue(Reporter.isEmpty())
  }
}
