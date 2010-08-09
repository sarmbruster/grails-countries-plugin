package grails.plugins.countries

class Continent extends Region {

    static hasMany = [countries:Country]

    static constraints = {
        //key maxSize: 3, unique:true
    }

    static mapping = {
    	cache usage:'read-only'
        //key column:'ckey'
    }
    
}
