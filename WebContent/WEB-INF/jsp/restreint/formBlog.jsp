<%@ page pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<label for="nomArticle" class="labelformbouteille">nomArticle<span class="requis">*</span>
</label>
<input type="text" class="inputformbouteille w-input" required="required"
maxlength="30" name="nomArticle" value="<c:out value="${blog.nomArticle}" />" id="nomArticle">

<label for="subtheme" class="labelformbouteille"><strong class="labelform">subtheme</strong>
</label>
<input type="text" class="inputformbouteille w-input" maxlength="30"
name="subtheme" value="<c:out value="${blog.subtheme}" />" id="subtheme">

<label for="nomAuteur" class="labelformbouteille"><strong class="labelform">nomAuteur</strong>
</label>
<input type="text" class="inputformbouteille w-input" maxlength="30"
name="nomAuteur" value="<c:out value="${blog.nomAuteur}" />" id="nomAuteur">

<label for="article1" class="labelformbouteille">article1<span class="requis">*</span>
</label>
<textarea class="inputformbouteille w-input" required="required"
name="article1"  id="article1">${blog.article1}</textarea>

<label for="article2" class="labelformbouteille">article2
</label>
<textarea class="inputformbouteille w-input"
name="article2" id="article2">${blog.article2}</textarea>

<label for="article3" class="labelformbouteille">article3
</label>
<textarea class="inputformbouteille w-input"
name="article3" id="article3">${blog.article3}</textarea>

<label for="dateEdition" class="labelformbouteille">dateEdition
</label>
<input type="datetime-local" class="inputformbouteille w-input" maxlength="30"
name="dateEdition" value="<c:out value="${blog.dateEdition}" />" id="dateEdition">

<c:if test="${!empty form.erreurs['erreurDao']}">
  <span class="erreur">
    <fmt:message key="erreur.dao"></fmt:message>
    ${form.erreurs['erreurDao']}
  </span>
</c:if>
