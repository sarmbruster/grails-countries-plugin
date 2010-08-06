import grails.plugins.countries.Continent
import grails.plugins.countries.Country
import grails.plugins.countries.Continent
import grails.plugins.countries.Country
import javax.servlet.ServletContext

class CountriesBootStrap {

    def init = { servletContext ->
        if (Continent.count()==0) {
            [
/*                    ['asa'],
                    ['eur'],
                    ['nam'],
                    ['sam'],
                    ['aus'],
                    ['afa'],*/
                    ['AS'],
                    ['EU'],
                    ['AF'],
                    ['OC'],
                    ['NA'],
                    ['SA'],
                    ['AN'],
            ].each {
                assert new Continent(key:it[0]).save()
            }
        }

        if (Country.count()==0) {
            setupCountries(servletContext)
        }

    }

    def destroy = {
    }

    def setupCountries(ServletContext servletContext) {

        def domains = [:]
        servletContext.getResourceAsStream("WEB-INF/domains.csv").eachLine {
            def (num, domain) = it.tokenize(",")
            domains[num] = domain
        }
        def capitals = [:]
        servletContext.getResourceAsStream("WEB-INF/capitals.csv").eachLine {
            def (num, capital) = it.tokenize(",")
            capitals[num] = capital
        }

        def countries = servletContext.getResourceAsStream("WEB-INF/countries.csv").text
        countries.eachLine { line ->
            def fields = line.tokenize()
            log.debug "importing: $fields"
            def country = new Country(
                    iso3166Number: fields[3],
                    domain: domains.get(fields[3], null),
                    shortKey: fields[1],
                    key: fields[2],
                    capital: capitals.get(fields[3], null),
                    continent: Continent.findByKey(fields[0])
                    )
            assert country.save(), "$fields: $country.errors.allErrors"
            log.debug "imported ${country.dump()}"

        }

        log.error "imported ${Country.count()} countries"
    }

}
