package grails.plugins.countries

import grails.test.*

/**
 * check if there are valid i18n entries for each continent and country
 */
class DataCompletenessTests extends GrailsUnitTestCase {

    def messageSource

    def locales = [Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH, new Locale("AF"), new Locale("es"), new Locale("it"), new Locale("pt", "BR")]

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testContinentsTranslations() {
        List failed = checkListForLocales(Continent.list(), "continent")
        assertTrue "$failed", failed.empty
    }

    void testCountriesTranslations() {
        List failed = checkListForLocales(Country.list(), "country")
        assertTrue "$failed", failed.empty
    }

    protected List checkListForLocales(List<Continent> list, prefix) {
        def failed = []
        list.each { continent ->
            locales.each { locale ->
                String loc = messageSource.getMessage("$prefix.$continent.key", null, null, locale)
                println "$continent.key $locale $loc"
                if (!loc) {
                    failed <<  "$continent.key $locale $loc"
                }
            }
        }
        return failed
    }
}
