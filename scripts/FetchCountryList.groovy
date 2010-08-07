includeTargets << grailsScript("Init")

target(main: "fetch the country list from wikipedia and convert it to csv") {

    URL url = new URL("http://en.wikipedia.org/wiki/List_of_countries_by_continent_%28data_file%29")
    def countriesCsv = "$basedir/web-app/countries.csv"
    url.withReader { reader ->
        def slurper = new XmlSlurper()
        slurper.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        def html = slurper.parse(reader)
        def countryList = html.body.div[2].div[2].pre.toString()

        File output = new File(countriesCsv)

        output.withWriter { Writer writer ->
            countryList.eachLine { String line ->
                if (!line.empty) {
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
