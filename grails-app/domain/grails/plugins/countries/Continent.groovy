package grails.plugins.countries

class Continent extends Region {

    static hasMany = [countries:Country]

    static constraints = {
    }

    static mapping = {
    	cache usage:'read-only'
    }
    
}
