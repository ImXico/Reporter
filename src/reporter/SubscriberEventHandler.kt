package reporter

import org.jetbrains.annotations.NotNull
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
class SubscriberEventHandler(@NotNull val subscriber: Subscriber, @NotNull private val handlerMethod: Method) {

    fun handleEvent(@NotNull event: Event) {
        this.handlerMethod.invoke(subscriber, event)
    }
}

