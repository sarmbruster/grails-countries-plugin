includeTargets << grailsScript("Init")

target(main: "fetch the country list from wikipedia and convert it to csv") {

    URL url = new URL("http://en.wikipedia.org/wiki/List_of_countries_by_continent_%28data_file%29")
    def countriesCsv = "$basedir/web-app/countries.csv"
    url.withReader { reader ->
        def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
        def html = slurper.parse(reader)
        def countryList =  html.'**'.find {it.@id=='mw-content-text'}.pre.toString()

        File output = new File(countriesCsv)

        output.withWriter { Writer writer ->
            countryList.eachLine { String line ->
                if ((!line.empty) && (!(line =~ /null/))) {
                    writer.println(line)
                }
            }
        }


    }
    echo "successfully fetched and converted $countriesCsv from $url"

    copy(todir:"${basedir}/web-app") {
        fileset(dir: "$countriesPluginDir/web-app", includes: "*.csv")
    }

}

setDefaultTarget(main)
