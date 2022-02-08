<%@ page pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />

<label for="url" class="labelformbouteille"><strong class="labelform">url</strong>
</label>
<input type="text" class="inputformbouteille w-input"
name="url" value="<c:out value="${pub.url}" />" id="url">

<!-- <label for="codeHtml" class="labelformbouteille">html
     </label>
     <textarea class="inputformbouteille w-input"
     name="codeHtml"  id="codeHtml">${pub.codeHtml}</textarea> -->
<div class="block-switchLine">
    <div class="switch-line">
        <label class="switch">
          <input type="checkbox" id="isFirst" name="isFirst" value="true"
          data-inad="${inFirstAD}" data-adfree="${isFirstADFree}" data-ischecked="${pub.isFirst}"
          <c:if test="${pub.isFirst.toString().equals('true')&&inFirstAD}">checked</c:if>>
          <span class="slider round"></span>
        </label>
        <label for="isFirst" class="labelformbouteille">
          <fmt:message key="pub.categoryfirst"></fmt:message>
          <c:choose>
              <c:when test="${!inFirstAD}">
                  <span class="requis">***</span>
              </c:when>
              <c:otherwise>
                  <span class="requis">**</span>
              </c:otherwise>
          </c:choose>
        </label>

        <c:choose>
            <c:when test="${!inFirstAD}">
                <label class="labelformbouteille attentionerrmin">
                    <span class="requis"><fmt:message key="tip.option.propo"></fmt:message></span>
                    <a style="display: flex" href="https://fr.tipeee.com/caveweb" >
                        <img src="images/tip.png" alt="" class="imgsidemenu">
                    </a>
                </label>
            </c:when>
            <c:when test="${inFirstAD && !pub.isFirst.toString().equals('true') && !isFirstADFree}">
                <label class="labelformbouteille attentionerrmin">
                    <span class="requis"><fmt:message key="pub.errmultipleutilisation"></fmt:message></span>
                </label>
            </c:when>
        </c:choose>
    </div>

    <div class="switch-line">
        <label class="switch">
          <input type="checkbox" id="isSecond" name="isSecond" value="true"
          data-inad="${inSecondAD}" data-adfree="${isSecondADFree}" data-ischecked="${pub.isSecond}"
          <c:if test="${pub.isSecond.toString().equals('true')&&inSecondAD}">checked</c:if>>
          <span class="slider round"></span>
        </label>
        <label for="isSecond" class="labelformbouteille">
          <fmt:message key="pub.categorysecond"></fmt:message>
            <c:choose>
                <c:when test="${!inSecondAD}">
                    <span class="requis">***</span>
                </c:when>
                <c:otherwise>
                    <span class="requis">**</span>
                </c:otherwise>
            </c:choose>
        </label>
        <c:choose>
            <c:when test="${!inSecondAD}">
                <label class="labelformbouteille attentionerrmin">
                    <span class="requis"><fmt:message key="tip.option.propo"></fmt:message></span>
                    <a style="display: flex" href="https://fr.tipeee.com/caveweb">
                        <img src="images/tip.png" alt="" class="imgsidemenu">
                    </a>
                </label>
            </c:when>
            <c:when test="${inSecondAD && !pub.isSecond.toString().equals('true') && !isSecondADFree}">
                <label class="labelformbouteille attentionerrmin">
                    <span class="requis"><fmt:message key="pub.errmultipleutilisation"></fmt:message></span>
                </label>
            </c:when>
        </c:choose>
    </div>
</div>


<c:if test="${!empty form.erreurs['erreurDao']}">
  <span class="erreur">
    <fmt:message key="erreur.dao"></fmt:message>
    ${form.erreurs['erreurDao']}
  </span>
</c:if>
