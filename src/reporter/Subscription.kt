package reporter

/**
 * Every event-handling function should use this annotation.
 * Any function with this annotation must have only one parameter, being that parameter
 * an implementation of [Event].
 *
 * @author Xico
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subscription

