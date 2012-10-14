package grails.plugins.countries

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(CountryTagLib)
@Build([Country, Continent])
class CountryTagLibSpec extends Specification {

    def setup() {
        tagLib.metaClass.message = { map-> map.code}
    }

    def "test <name> with country attribute"() {
        when:
        def object = clazz.build(map)
        object.save()

        then:
        applyTemplate('<country:name object="${obj}"/>', [obj: object]) == result

        where:
        clazz     | map          | result
        Country   | [key: 'abc'] | "country.abc"
        Continent | [key: 'abc'] | "continent.abc"

    }

    def "test <select> tag in its simplest form"() {
        setup:
        def continent = Continent.build()
        ['abc','def'].eachWithIndex { item, index ->
            def c = Country.build(id: index, key:item, continents:[continent])
            c.save()
        }

        when:
        def s = applyTemplate('<country:select name="countrySelect"/>')

        then:
        Country.list().every {
            s =~ /<option value="${it.id}" >country.${it.key}<\/option>/
        }
    }

    def "test <select> tag with a continent as from attribute"() {

        setup:
        assert Continent.count()==0
        assert Country.count()==0
        def continent1 = Continent.build(key: 'c1')
        def continent2 = Continent.build(key: 'c2')
        def country1 = Country.build(key:'abc')
        def country2 = Country.build(key:'def')
        def country3 = Country.build(key:'gih')
        continent1.addToCountries(country1)
        continent1.addToCountries(country2)
        continent2.addToCountries(country3)


        when:
        def s = applyTemplate('<country:select name="countrySelect" from="${continent}"/>', [continent: continent1])

        then:
        s =~ /<option value="${country1.id}" >country.${country1.key}<\/option>/
        s =~ /<option value="${country2.id}" >country.${country2.key}<\/option>/
        !(s =~ /<option value="${country3.id}" >country.${country3.key}<\/option>/)
    }

    def "test <selectContinent> tag"() {

        setup:
        assert Continent.count()==0
        def continent1 = Continent.build(key: 'c1')
        def continent2 = Continent.build(key: 'c2')

        when:
        def s = applyTemplate('<country:selectContinent name="countrySelect" />')

        then:
        s =~ /<option value="${continent1.id}" >continent.${continent1.key}<\/option>/
        s =~ /<option value="${continent2.id}" >continent.${continent2.key}<\/option>/
    }

}
