package grails.plugins.countries

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

/**
 * check if there are valid i18n entries for each region and country
 */
class DataCompletenessSpec extends IntegrationSpec {

    def messageSource

    //def locales = [Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH, new Locale("AF"), new Locale("es"), new Locale("it"), new Locale("pt", "BR")]


    @Unroll("check continent transalations for locale #locale")
    def "check continent translations for completeness"() {
        when:
        List failed = checkListForLocale(Continent.list(), "continent", locale)

        then: "all translations are in place"
        failed.size() == numberMissing

        where:
        locale                 | numberMissing
        Locale.GERMAN          | 0
        Locale.ENGLISH         | 0
        Locale.FRENCH          | 0
        new Locale("AF")       | 0
        new Locale("es")       | 0
        new Locale("it")       | 0
        new Locale("pt", "BR") | 0

    }

    @Unroll("check country translations for locale #locale")
    def "check country translations for completeness"() {
        when:
        List failed = checkListForLocale(Country.list(), "country", locale)

        then: "all translations are in place"
        failed.size() == numberMissing

        where:
        locale                 | numberMissing
        Locale.GERMAN          | 0
        Locale.ENGLISH         | 0
        Locale.FRENCH          | 0
        new Locale("AF")       | 0
        new Locale("es")       | 0
        new Locale("it")       | 0
        new Locale("pt", "BR") | 0

    }

    def "Turkey should have multiple continents"() {
        when:
        def turkey = Country.findByKey("TUR")

        then:
        turkey.continents.size() == 2
    }

    protected List checkListForLocale(List<Region> list, prefix, locale) {
        def failed = []
        list.each { region ->
            String loc = messageSource.getMessage("$prefix.$region", null, null, locale)
            if (!loc) {
                failed << "$region.key"
            }
        }
        return failed
    }
}
