package grails.plugins.countries

import grails.test.*

class CountryTagLibTests extends TagLibUnitTestCase {
    protected void setUp() {
        super.setUp()
        tagLib.metaClass.message = { map-> map.code}
        tagLib.metaClass.getG = { -> [
                select: {
                    it.from.inject([:]) { map, item ->
                        map[item[it.optionKey].toString()] = item[it.optionValue]
                        map
                    }
                }
            ]
        }
    }


    protected void tearDown() {
        super.tearDown()
    }

    void testNameWithCountry() {
        tagLib.name([object: new Country(key:'abc')])
        assertEquals "country.abc", tagLib.out.toString()
    }

    void testNameWithContinent() {
        tagLib.name([object: new Continent(key:'abc')])
        assertEquals "continent.abc", tagLib.out.toString()
    }

    void testSelectAllCountries() {

        mockDomain Country, [
                new Country(id:1, key:'abc'),
                new Country(id:2, key:'def')
        ]

        //println tagLib.g
        tagLib.select([:])

        String out = tagLib.out.toString()
        assertTrue out.contains("1:country.abc")
        assertTrue out.contains("2:country.abccountry.def") //TODO: change to 2:country.def when   http://jira.codehaus.org/browse/GRAILS-6267 is fixed

    }

    void testSelectCountriesFromContinent() {

        def continent1 = new Continent(id:1, key:'cont1')
        def continent2 = new Continent(id:2, key:'cont2')
        mockDomain Continent, [
                continent1,
                continent2,
        ]
        def country1 = new Country(id:1, key:'abc')
        def country2 = new Country(id:2, key:'def')
        def country3 = new Country(id:3, key:'gih')
        mockDomain Country, [ country1, country2, country3 ]
        continent1.addToCountries(country1)
        continent1.addToCountries(country2)
        continent2.addToCountries(country3)

        //println tagLib.g
        tagLib.select([from:continent1])

        String out = tagLib.out
        assertTrue out.contains("2:country.def")
        assertTrue out.contains("1:country.defcountry.abc") //TODO: change to 2:country.def when   http://jira.codehaus.org/browse/GRAILS-6267 is fixed
        assertFalse  out.contains("gih")

    }


    void testSelectContinent() {

        mockDomain Continent, [
                new Continent(id:1, key:'abc'),
                new Continent(id:2, key:'def')
        ]

        //println tagLib.g
        tagLib.selectContinent([:])

        String out = tagLib.out
        assertTrue out.contains("1:continent.abc")
        assertTrue out.contains("2:continent.abccontinent.def") //TODO: change to 2:country.def when   http://jira.codehaus.org/browse/GRAILS-6267 is fixed

    }
}
