<%@ page pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />

<div class="descriptmainnomdansform">
	<fmt:message key="producteurs.isWineProducer"></fmt:message>
</div>


<div class="choisprodexist w-radio">
	<input type="radio" id="isWineproducer"
		name="isWineproducer" value="wineproducer"
		class="buttonchoisradio w-radio-input"
		<c:if test="${utilisateur.isWineproducer.toString().equals('true')}">checked</c:if>> <label
		for="isWineproducer" class="labelformbouteille"><fmt:message
			key="bouteilles.producteur.oui"></fmt:message></label>
</div>

<div class="choisprodexist w-radio">
	<input type="radio" id="isWineproducer"
		name="isWineproducer" value="nonWineproducer"
		class="buttonchoisradio w-radio-input"
		<c:if test="${utilisateur.isWineproducer.toString().equals('false')|| empty utilisateur}">checked</c:if>> <label
		for="isWineproducer" class="labelformbouteille"><fmt:message
			key="bouteilles.producteur.non"></fmt:message></label>
</div>

<label for="nom" class="labelformbouteille"
  ><fmt:message key="inscription.label.prenom"></fmt:message
  ><span class="requis">*</span></label
>
<c:if test="${!empty form.erreurs['nom']}">
  <span class="erreur"
    ><fmt:message key="inscription.erreur.nom"></fmt:message
  ></span>
</c:if>
<input type="text" class="inputformbouteille w-input" required="required"
maxlength="30" name="nom" value="<c:out value="${utilisateur.nom}" />" id="nom">

<label for="email" class="labelformbouteille"
  ><strong class="labelform"
    ><fmt:message key="connection.label.email"></fmt:message
    ><span class="requis">*</span></strong
  ></label
>
<c:if test="${!empty form.erreurs['email']}">
  <span class="erreur"><fmt:message key="inscription.erreur.email"></fmt:message
  ></span>
</c:if>
<input type="email" class="inputformbouteille w-input" maxlength="30"
name="email" value="<c:out value="${utilisateur.email}" />" id="email"
required="required">

<label for="motdepasse" class="labelformbouteille"
  ><fmt:message key="connection.label.motdepasseconnection"></fmt:message
  ><span class="requis"
    >*(<fmt:message key="inscription.span.required.motdepasse"></fmt:message
    >)</span
  ></label
>
<c:if test="${!empty form.erreurs['motdepasse']}">
  <span class="erreur"
    ><fmt:message key="inscription.erreur.motdepasse"></fmt:message
  ></span>
</c:if>
<input
  type="password"
  maxlength="30"
  name="motdepasse"
  required="required"
  id="motdepasse"
  class="inputformbouteille w-input"
/>

<c:if test="${!empty form.erreurs['confirmation']}">
  <span class="erreur"
    ><fmt:message key="inscription.erreur.confirmation"></fmt:message
  ></span>
</c:if>

<label for="confirmation" class="labelformbouteille"
  ><fmt:message key="inscription.label.motdepasseconfirmation"></fmt:message
  ><span class="requis">*</span></label
>
<input
  type="password"
  maxlength="30"
  name="confirmation"
  required="required"
  id="confirmation"
  class="inputformbouteille w-input"
/>
<c:if test="${!empty utilisateur && empty form}">
<div class="switch-line">
    <label class="switch">
      <input type="checkbox" id="isAllowedAD" name="isAllowedAD" value="true"
      data-incl="${inAllowedAd}"
      <c:if test="${isAllowedAd && inAllowedAd}">checked</c:if>>
      <span class="slider round"></span>
    </label>
    <label for="isAllowedAD" class="labelformbouteille">
      <fmt:message key="appsanspub"></fmt:message>
    </label>
</div>
<c:if test="${!inAllowedAd}">
    <label class="labelformbouteille attentionerrmin">
        <span class="requis"><fmt:message key="tip.option.propo"></fmt:message></span>
        <a style="display: flex" href="https://fr.tipeee.com/caveweb" >
            <img src="images/tip.png" alt="" class="imgsidemenu">
        </a>
    </label>
</c:if>
</c:if>

<c:if test="${isAdmin}">
  <label for="isPayed" class="labelformbouteille"
    >isPayed<span class="requis">*</span></label
  >
  <input type="number" class="inputformbouteille w-input" name="isPayed"
  value="<c:out value="${utilisateur.isPayed}" />" id="isPayed">
</c:if>

<label class="labelformbouteille"> <span class="requis">*<fmt:message key="title.champsobligatoires"></fmt:message></span></label>

<c:if test="${!empty form.unsuccessCreation}">
  <span class="erreur"
    >utilisateur.unsuccess.creation ${form.unsuccessCreation}</span
  >
</c:if>
<c:if test="${!empty form.unsuccessMaj}">
  <span class="erreur">utilisateur.unsuccess.maj ${form.unsuccessMaj}</span>
</c:if>

<c:if test="${!empty form.erreurs['erreurDao']}">
  <span class="erreur">
    <fmt:message key="erreur.dao"></fmt:message>
    ${form.erreurs['erreurDao']}
  </span>
</c:if>
