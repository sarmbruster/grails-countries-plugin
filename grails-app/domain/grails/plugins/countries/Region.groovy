package grails.plugins.countries

import org.springframework.context.MessageSourceResolvable

abstract class Region implements Serializable, MessageSourceResolvable {

    static transients = ["codes", "arguments", "defaultMessage"]

    String key // iso3166 3 letter code

    static constraints = {
        key maxSize:3, unique:true
    }

    static mapping = {
    	cache usage:'read-only'
		key column:'ckey'
    }
    
    String[] getCodes() {
        ["${getClass().simpleName.toLowerCase()}.$key"] as String[]
    }

    Object[] getArguments() {
        [] as Object[]
    }

    String getDefaultMessage() {
        "${getClass().name}: $key"
    }

    String toString() {
        key
    }
}
