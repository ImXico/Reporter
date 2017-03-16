import example.HungryPerson
import example.SpyingNeighbour
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import reporter.Reporter

class Tests {

    private val hungryPerson: HungryPerson = HungryPerson()
    private val spyingNeighbour: SpyingNeighbour = SpyingNeighbour()

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
}
