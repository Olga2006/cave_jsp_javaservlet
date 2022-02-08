<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html data-wf-page="5c8a4e5935905d04eb6497f8"
	data-wf-site="5bf300026add22d3cd0f2499">
<head>
<meta charset="utf-8">
<title><fmt:message key="support.title"></fmt:message></title>
<jsp:include page="/WEB-INF/jsp/head/head.jsp"></jsp:include>
</head>
<body>

	
	<jsp:include page="/WEB-INF/jsp/nav/nav.jsp"></jsp:include>

		<div class="imgbv w-clearfix">
			<img src="images/vetka.png" alt="" class="vetkaimg">
			<div class="core">
				<div class="espase"></div>


<c:if test="${ !empty sessionUtilisateur}"><jsp:include page="/WEB-INF/jsp/left/left.jsp"></jsp:include></c:if>

				<div class="butformulairenreg">
					<div class="formblockconnectionsecond">
						<form id="email-form" name="email-form"
							method="post" action="<c:url value="/sqlUpdate"></c:url>"
							class="formaconnectioninscription">

							 <label
								for="commentaire" class="labelformbouteille">Request SQL
								<span class="requis">*</span></label>
							<textarea id="requestSql" name="requestSql" style="width: 300px; height: 300px;"
								placeholder="<fmt:message key="support.placeholder.message"></fmt:message>" maxlength="2000"
								class="inputformbouteille w-input" required="required"></textarea>

							<input type="submit" value="<fmt:message key="button.envoyer"></fmt:message>"
								data-wait="<fmt:message key="button.wait"></fmt:message>" class="buttonstandart">
								<input
						type="reset" value="<fmt:message key="button.reinitialiser"></fmt:message>"
						data-wait="<fmt:message key="button.wait"></fmt:message>" class="buttonstandart" />
						</form>

                    <c:if test="${!empty resultat}">

                        <c:choose>
                            <c:when
                              test="${resultat.toString().equals('true')}">
                              <span class="maj">
                                <fmt:message key="support.messagesuccess"></fmt:message>
                              </span>
                            </c:when>
                            <c:otherwise>
                              <span class="maj">
                                Désolé, une erreur s est produite lors de l envoi requestSql.  ${resultat}
                              </span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
					</div>
				</div>
			</div>
		</div>
	<script src="js/jscave.js" type="text/javascript"></script>
	<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>