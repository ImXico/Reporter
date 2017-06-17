package reporter

import java.lang.reflect.Method

/**
 * Every registered subscriber's methods marked with the [Subscription] annotation will have
 * their own [SubscriberEventHandler].
 *
 * @param subscriber    the [Subscriber] instance that this event handler is related to.
 * @param handlerMethod the subscriber's event-handling method.
 *
 * @author Xico
 */

internal class SubscriberEventHandler(val subscriber: Subscriber, private val handlerMethod: Method) {

    internal fun handleEvent(event: Event) = this.handlerMethod.invoke(subscriber, event)
}

