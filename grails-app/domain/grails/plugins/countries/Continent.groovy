package grails.plugins.countries

import org.springframework.context.i18n.LocaleContextHolder

class Continent {

    String key
    static hasMany = [countries:Country]


    static constraints = {
        key maxSize: 3, unique:true
    }

    static mapping = {
    	cache usage:'read-only'
		key column: 'ckey' // some db's don't like column name 'key'
    }
    
}
