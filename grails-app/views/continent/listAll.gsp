<%--
  Created by IntelliJ IDEA.
  User: stefan
  Date: 02.08.2010
  Time: 15:43:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="grails.plugins.countries.Region" contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple GSP page</title></head>
  <body>Place your content here</body>

<g:form action="listAll">
  Continents: <country:selectContinent name="continent"/><br/>
  all countries: <country:select name="allCountries" value="21"/> <br/>
  all countries from Europe:   <country:select name="continent" from="${grails.plugins.countries.Continent.findByKey('EU')}"/> <br/>

  combined: <country:select name="all" from="${Region.list()}"/><br/>

  <g:submitButton name="abc"/>
</g:form>
</html>