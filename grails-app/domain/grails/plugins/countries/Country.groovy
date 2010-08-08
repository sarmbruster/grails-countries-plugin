package grails.plugins.countries

class Country {

    Continent continent
    String shortKey // iso3166 2 letter code
    String key // iso3166 3 letter code
    String iso3166Number
    String domain
    String capital

    static constraints = {
        continent maxSize:3
        shortKey maxSize:2, unique:true
        key maxSize:3, unique:true
        iso3166Number maxSize:3, unique:true
        domain maxSize: 5, nullable:true
        capital maxSize:50, nullable:true
    }

    static mapping = {
    	cache usage:'read-only'
		key column:'ckey'
    }

}
