package grails.plugins.countries

class Country extends Region {

    Continent continent
    String shortKey // iso3166 2 letter code
    String iso3166Number
    String domain
    String capital

    static constraints = {
        //key maxSize: 3, unique:true
        shortKey maxSize:2, unique:true
        iso3166Number maxSize:3, unique:true
        domain maxSize: 5, nullable:true
        capital maxSize:50, nullable:true
    }

    static mapping = {
    	cache usage:'read-only'
        //key column:'ckey'
    }

}
