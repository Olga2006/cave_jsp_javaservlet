<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html data-wf-page="5c8a4e5935905d04eb6497f8"
	data-wf-site="5bf300026add22d3cd0f2499">
<head>
<meta charset="utf-8">
<title><fmt:message key="inscription.title"></fmt:message></title>
<jsp:include page="/WEB-INF/jsp/head/head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/nav/nav.jsp"></jsp:include>

	<div class="imgbv w-clearfix">
		<img src="images/vetka.png" alt="" class="vetkaimg">
		<div class="core">
			<div class="espase"></div>
			<c:if test="${!empty sessionUtilisateur}"><jsp:include page="/WEB-INF/jsp/left/left.jsp"></jsp:include></c:if>
			<img src="images/zolotoy_kluch-600x190.png"
				style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0DEG) rotateY(0) rotateZ(0) skew(0, 0); 
				-moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0DEG) rotateY(0) rotateZ(0) skew(0, 0); 
				-ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0DEG) rotateY(0) rotateZ(0) skew(0, 0); 
				transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0DEG) rotateY(0) rotateZ(0) skew(0, 0); 
				transform-style: preserve-3d"
				alt="" class="keyimg">

						<c:choose>
                			<c:when test="${!empty sessionUtilisateur}">
								<div class="erreur"><fmt:message key="connection.update"></fmt:message></div>
                			</c:when>
                			<c:otherwise>
                            <a
                                 href="<c:url value="/connection"></c:url>"
                                class="buttonstandart"><fmt:message key="connection.meconnecter"></fmt:message></a>
                			</c:otherwise>
                		</c:choose>

			<div class="butformulairenreg">
				<div class="formblockconnectionsecond">
					<form id="email-form" name="email-form"
						method="post" action="<c:url value="/inscription">
						  <c:if test="${!empty sessionUtilisateur}"><c:param name="idUser" value="${ sessionUtilisateur.id }" /></c:if>
						  </c:url>" class="formaconnectioninscription">

						<jsp:include page="../restreint/formUser.jsp" ></jsp:include>

                            <c:choose>
                                <c:when test="${!empty sessionUtilisateur}">
                                  <input type="submit" value="<fmt:message key="button.valider"></fmt:message>"
                                      data-wait="<fmt:message key="button.wait"></fmt:message>"
                                      class="buttonstandart">

                                <input type="reset"
                                value="<fmt:message key="button.reinitialiser"></fmt:message>"
                                data-wait="<fmt:message key="button.wait"></fmt:message>"
                                class="buttonstandart" />

                                </c:when>
                                <c:otherwise>
                                <input type="submit" value="<fmt:message key="button.inscription"></fmt:message>"
                                    data-wait="<fmt:message key="button.wait"></fmt:message>"
                                    class="buttonstandart">
                                </c:otherwise>
                            </c:choose>


					</form>

				</div>
			</div>
		</div>
	</div>
<script src="js/jqcave.js" type="text/javascript"></script>
	<script src="js/jscave.js" type="text/javascript"></script>
	<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>