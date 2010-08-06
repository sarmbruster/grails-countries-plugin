includeTargets << grailsScript("Init")

target(main: "fetch the country list from wikipedia and convert it to csv") {

    URL url = new URL("http://en.wikipedia.org/wiki/List_of_countries_by_continent_%28data_file%29")
    url.withReader { reader ->
        def slurper = new XmlSlurper()
        slurper.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        def html = slurper.parse(reader)
        def countryList = html.body.div[2].div[2].pre.toString()

        File output = new File("$basedir/web-app/WEB-INF/countries.csv")

        output.withWriter { Writer writer ->
            countryList.eachLine { String line ->
                if (!line.empty) {
                    writer.println(line)
                }
            }
        }


    }

}

setDefaultTarget(main)
