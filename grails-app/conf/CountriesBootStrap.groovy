import grails.plugins.countries.Continent
import grails.plugins.countries.Country
import grails.plugins.countries.Continent
import grails.plugins.countries.Country
import javax.servlet.ServletContext

class CountriesBootStrap {

    def init = { servletContext ->
        if (Continent.count()==0) {
            [
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
        getResourceAsStream(servletContext, "WEB-INF/domains.csv").eachLine {
            def (num, domain) = it.tokenize(",")
            domains[num] = domain
        }
        def capitals = [:]
        getResourceAsStream(servletContext, "WEB-INF/capitals.csv").eachLine {
            def (num, capital) = it.tokenize(",")
            capitals[num] = capital
        }

        def countries = getResourceAsStream(servletContext, "WEB-INF/countries.csv").text
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

    /**
     * there seems to be different behaviour in the root dir for getResourceAsStream when doing run-app compared to test-app
     * wrapping this to prevent failures
     */
    def getResourceAsStream(ServletContext servletContext, String res) {
        servletContext.getResourceAsStream(res) ?: servletContext.getResourceAsStream("web-app/$res")
    }

}
