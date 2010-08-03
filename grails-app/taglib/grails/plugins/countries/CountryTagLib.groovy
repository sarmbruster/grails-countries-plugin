package grails.plugins.countries

class CountryTagLib {

    static namespace = "country"


    def name = { attrs ->
        def object = getRequiredAttribute(attrs, "object", "name")
        def prefix = object.getClass().simpleName.toLowerCase()
        assert prefix
        def code = "$prefix.${object?.key}"
        out << message(code: code, default: object?.key)
    }

    def selectContinent = { attrs ->
        Map map = createSortedMap(Continent.list())
        insertMapForIteration(map, attrs)
        out << g.select(attrs)
    }

    def select = { attrs ->
        def list = attrs.from ?: Country.list()
        if (list instanceof Continent) {
            list = list.countries
        }
        assert list instanceof Collection
        Map map = createSortedMap(list)
        insertMapForIteration(map, attrs)
        out << g.select(attrs)
    }

    protected def insertMapForIteration(Map map, attrs) {
        attrs.from = map.entrySet()
        attrs.optionKey = "value"
        attrs.optionValue = "key"
    }

    protected SortedMap createSortedMap(def list) {
        SortedMap map = new TreeMap() // forces ordering
        list.each {
            String localizedName = name(object: it)
            map[localizedName] = it.id
        }
        log.debug "map $map"
        return map
    }

    protected void writeDocumentReady(writer, String javascript) {
        writer << """
      <script>
      \$(document).ready(function() {
         ${javascript}
      });
      </script>
      """
    }

    protected String resolveText(attrs) {
        String messageCode = attrs.remove('messageCode')
        if (messageCode) {
            return message(code: messageCode)
        }

        return attrs.remove('text')
    }

    protected getRequiredAttribute(attrs, String name, String tagName) {
        if (!attrs.containsKey(name)) {
            throwTagError("Tag [$tagName] is missing required attribute [$name]")
        }
        attrs.remove name
    }

    protected void writeRemainingAttributes(writer, attrs) {
        writer << attrs.collect { k, v -> """ $k="$v" """ }.join('')
    }


}
