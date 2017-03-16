/** Pretty sure it's safe...! */
@file:Suppress("UNCHECKED_CAST")

package reporter

import org.jetbrains.annotations.NotNull
import java.lang.reflect.Method

/** @author Xico */

object Reporter {

    @JvmField val ILLEGAL_METHOD: String = "Illegal method with the @Subscription annotation!"
    @JvmField val NO_SUCH_EVENT: String = "No Such Event!"

    /**
     * Maps an [Event] to one or more [SubscriberEventHandler]s.
     * Thus, when an [Event] is triggered, all handlers will fire their handling methods.
     */
    private val map: MutableMap<Class<out Event>, MutableSet<SubscriberEventHandler>> = mutableMapOf()

    /**
     * Registers a new [Subscriber].
     * Any class that wants to listen to events must implement [Subscriber].
     * For any method of this class marked with the @Subscription annotation, a new [SubscriberEventHandler] is
     * created and registered on the map.
     *
     * @param subscriber subscriber being registered.
     */
    fun register(@NotNull subscriber: Subscriber) {
        subscriber.javaClass.declaredMethods.forEach {
            if (!it.hasSubscriptionAnnotation()) return@forEach
            if (!it.hasValidParameters()) return@forEach
            /*
            Extract the first and only parameter and check if it can be casted to some
            implementation of the [Event] interface.
            Failing this cast means that this method is using the [Subscription] annotation
            illegally, as its only parameter is not an [Event].
             */
            val param: Class<*> = it.parameterTypes[0]
            param as? Class<out Event> ?: error(ILLEGAL_METHOD)
            /* Generate a [SubscriberEventHandler] for this method and add it to the [map]. */
            map.getOrPut(param) { mutableSetOf() }.apply {
                val newSubscriberEventHandler = SubscriberEventHandler(subscriber, it)
                add(newSubscriberEventHandler)
            }
        }
    }

    /**
     * Register a variable number of [Subscriber]s.
     *
     * @param subscribers subscribers being registered.
     */
    fun register(@NotNull vararg subscribers: Subscriber) {
        subscribers.forEach { register(it) }
    }

    /**
     * Checks whether or not the given [Subscriber] is already registered in the Reporter.
     *
     * @param subscriber subscriber to be checked.
     * @return true if the subscriber is already registered, false otherwise.
     */
    fun subscriberAlreadyRegistered(subscriber: Subscriber): Boolean {
        map.values.forEach {
            it.forEach {
                if (it.subscriber == subscriber) return true
            }
        }
        return false
    }

    /**
     * @return whether or not a given method has the [Subscription] annotation.
     */
    private fun Method.hasSubscriptionAnnotation(): Boolean {
        return this.getAnnotation(Subscription::class.java) != null
    }

    /**
     * A method is considered to have invalid parameters if and only if its number of parameters is != 1.
     *
     * @return whether or not a given method has valid parameters.
     */
    private fun Method.hasValidParameters(): Boolean {
        val parameterTypes: Array<out Class<*>>? = this.parameterTypes ?: return false
        if (parameterTypes?.size != 1) return false
        return true
    }

    /**
     * Unregisters a given [Subscriber] by removing from the [map] all [SubscriberEventHandler]s
     * that are related to this subscriber's events.
     *
     * @param subscriber subscriber being removed.
     */
    fun unregister(@NotNull subscriber: Subscriber) {
        for (handlerSet: MutableSet<SubscriberEventHandler> in map.values) {
            handlerSet.removeAll { it.subscriber == subscriber }
        }
    }

    /**
     * Reports an [Event] to all the active subscribers of that event.
     *
     * @param event event being reported.
     * @see [SubscriberEventHandler.handleEvent]
     */
    fun report(@NotNull event: Event) {
        val interestedHandlers: MutableSet<SubscriberEventHandler> = map[event.javaClass] ?: error(NO_SUCH_EVENT)
        interestedHandlers.forEach { it.handleEvent(event) }
    }

    /**
     * Clears the whole [map].
     */
    fun clearAll() = map.clear()
}

