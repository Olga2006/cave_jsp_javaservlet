<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html data-wf-page="5c87c9153f656c8f0ad43c3c"
	data-wf-site="5bf300026add22d3cd0f2499">
<head>
<meta charset="utf-8">
<title>Users</title>
<jsp:include page="/WEB-INF/jsp/head/head.jsp"></jsp:include>
</head>
<body class="body">
<div id="coverforformulaire" style="<c:if test="${empty form.erreurs && empty utilisateur}">display: none</c:if>"></div>
	<jsp:include page="/WEB-INF/jsp/left/left.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/nav/nav.jsp"></jsp:include>

	<div class="coremembre">
		<div class="bvcellar"></div>
				<div class="divbvdanscave">
					<h1 class="heading bvheading">
						Erreurs
					</h1>
				</div>

				
				<div class="collectionscroll">
					<input id="filterBouteille" type="text"
						placeholder="<fmt:message key="filter.placeholder"></fmt:message>"
						class="inputformbouteille">
					<!-- *********************************************************Collection Erreurs********************************** -->
					<c:forEach items="${listErreurs}"
						var="mapErreurs" varStatus="boucle">
						<div class="ciblebouteille">
							<div class="blockoneitem">
								<div class="sousblockoneitem">

									<img src="images/supprimer.png" alt=""
										class="imgajouterinfoerreurdel delstyle"
										id="${ mapErreurs.id}"
										title="<fmt:message key="title.supprimer"></fmt:message>">
									<div class="divnombrechaquebdanscave">

										<div class="divdescrp2lh">
											<div class="descriptmainnom">
												id: <c:out value="${ mapErreurs.id }" />
											</div>
										</div>

                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                id utilisateur:
                                                <c:out value="${ mapErreurs.utilisateurId}" />
                                            </div>
                                        </div>

										<div class="divdescrp2lh">
											<div class="descriptrest">
												codErreur:
												<c:out value="${ mapErreurs.codErreur}" />
											</div>
										</div>

										<div class="divdescrp2lh">
											<div class="descriptrest">
												txtErreur:
												<c:out value="${ mapErreurs.txtErreur}" />
											</div>
										</div>

                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                dateCreation:
                                                <c:out value="${ mapErreurs.dateCreat}" />
                                            </div>
                                        </div>

									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<!-- *********************************************************Fin Collection Erreurs************************************************************ -->
				</div>
	</div>


	<!-- *********************************************************Form Confirm Del************************************************************ -->
	<div class="divdisapppourtriggerdel" id="divdisapppourtriggerdel"
		style="<c:if test="${empty erreurs}">display: none</c:if>">
		<div class="formmain">
			<div class="divhrightelementsmall">
			    <a title="<fmt:message key="button.title.sortir"></fmt:message>"
					 class="sortirformsansrechargement"> 
					
					<img src="images/sortir.png" width="30"
					height="50"
					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
					-moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
					-ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
					transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
					alt="" class="sortirformimg">
				</a>
			</div>

			<c:choose>
				<c:when test="${!empty erreurs['erreurDao']}">
                    <span class="erreur">erreur DAO ${erreurs['erreurDao']}</span>

                    <div class="divhcenterelement">
                        <a title="<fmt:message key="button.title.sortir"></fmt:message>"
                            href="<c:url value="/listeErreurs"></c:url>"
                            class="buttonstandart"><fmt:message key="button.title.sortir"></fmt:message></a>
                    </div>
				</c:when>

				<c:otherwise>
					<form method="post" action="" class="forminside" id="formDel">
						<label for="nom-5" class="warningdel">Êtes-vous sûr de vouloir supprimer erreur </label>
						<div class="divhcenterelement">
							<input type="submit"
								value="<fmt:message key="button.valider"></fmt:message>"
								data-wait="<fmt:message key="button.wait"></fmt:message>"
								class="buttonstandart"> <a
								title="<fmt:message key="button.title.sortir"></fmt:message>"
								
								class="buttonstandart sortirformsansrechargement"> <fmt:message
									key="button.title.sortir"></fmt:message>
							</a>
						</div>
					</form>
				</c:otherwise>

			</c:choose>


		</div>
	</div>

	<!-- **********************************************resultat succes***************************************************** -->

	<c:choose>
		<c:when
			test="${empty successDel}"></c:when>
		<c:otherwise>
			<script>
				var timer = setTimeout(function() {
					window.location = 'listeErreurs'
				}, 3000);
			</script>
		</c:otherwise>
	</c:choose>

	<div class="resultat" id="resultat"
		style="<c:if test="${empty successDel}">display: none</c:if>">
		<div class="formmain">
			<div class="divhrightelementsmall">
				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
					href="<c:url value="/listeErreurs"></c:url>"
					class="sortirformimg"> <img src="images/sortir.png" width="30"
					height="50"
					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
					alt="" class="sortirformimg">
				</a>
			</div>
			<div class="divhcenterelement">
				<c:if test="${!empty successDel}">
					<span class="maj">erreur ${successDel} est deleted </span>
				</c:if>
			</div>

		</div>
	</div>


	<script src="js/jqcave.js" type="text/javascript"></script>
	<script src="js/jscave.js" type="text/javascript"></script>
	<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>