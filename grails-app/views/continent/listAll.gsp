<%--
  Created by IntelliJ IDEA.
  User: stefan
  Date: 02.08.2010
  Time: 15:43:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple GSP page</title></head>
  <body>Place your content here</body>
<ul>
<g:each in="${continents}" var="continent">
  <li><country:name object="${continent}"/>

  <ul>
    <g:each in="${continent.countries}" var="country">
      <li><country:name object="${country}"/></li>
    </g:each>

  </ul>
  </li>
</g:each>
  </ul>

<g:form>
  Continents: <country:selectContinent name="continent"/><br/>
  all countries: <country:select name="allCountries" value="21"/> <br/>
  all countries from Europe:   <country:select name="continent" from="${grails.plugins.countries.Continent.findByKey('eur')}"/> <br/>

  <g:submitButton name="abc"/>
</g:form>
</html>