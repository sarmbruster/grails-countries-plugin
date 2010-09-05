package grails.plugins.countries

class Region implements Serializable {

    String key // iso3166 3 letter code

    static constraints = {
        key maxSize:3, unique:true
    }

    static mapping = {
    	cache usage:'read-only'
		key column:'ckey'
    }
    
}   
