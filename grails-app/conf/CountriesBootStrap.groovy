import grails.plugins.countries.Continent
import grails.plugins.countries.Country
import grails.plugins.countries.Continent
import grails.plugins.countries.Country
import javax.servlet.ServletContext
import grails.util.Environment

class CountriesBootStrap {

    def init = { servletContext ->

        //org.hsqldb.util.DatabaseManager.main('-url' , "jdbc:hsqldb:mem:devDB")
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
        getResourceAsStream(servletContext, "domains.csv").eachLine {
            def (num, domain) = it.tokenize(",")
            domains[num] = domain
        }
        def capitals = [:]
        getResourceAsStream(servletContext, "capitals.csv").eachLine {
            def (num, capital) = it.tokenize(",")
            capitals[num] = capital
        }

        def countries = getResourceAsStream(servletContext, "countries.csv").text
        countries.eachLine { line ->
            def fields = line.tokenize()
            log.debug "importing: $fields"

            def country = Country.findOrSaveWhere(
                    iso3166Number: fields[3],
                    domain: domains.get(fields[3], null),
                    shortKey: fields[1],
                    key: fields[2],
                    capital: capitals.get(fields[3], null)
            )
            country.addToContinents(Continent.findByKey(fields[0]))

            log.debug "imported ${country.dump()}"
        }
        log.info "imported ${Country.count()} countries"
    }

    /**
     * there seems to be different behaviour in the root dir for getResourceAsStream when doing run-app compared to test-app
     * wrapping this to prevent failures
     */
    def getResourceAsStream(ServletContext servletContext, String res) {
        def resource = (Environment.current == Environment.TEST) ? new FileInputStream("web-app/$res") : servletContext.getResourceAsStream(res)
        assert resource, "could not find $res, consider running 'grails fetch-country-list'" 
        return resource
    }

}
