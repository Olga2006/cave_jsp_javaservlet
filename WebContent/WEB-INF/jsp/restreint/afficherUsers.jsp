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
						Users
					</h1>
				</div>

				
				<div class="collectionscroll">
					<input id="filterBouteille" type="text"
						placeholder="<fmt:message key="filter.placeholder"></fmt:message>"
						class="inputformbouteille">
					<!-- *********************************************************Collection Users********************************** -->
					<c:forEach items="${listUsers }"
						var="mapUsers" varStatus="boucle">
						<div class="ciblebouteille">
							<div class="blockoneitem">
								<div class="sousblockoneitem">

									<img src="images/supprimer.png" alt=""
										class="imgajouterinfouserdel delstyle"
										id="${ mapUsers.id} ${ mapUsers.nom}"
										title="<fmt:message key="title.supprimer"></fmt:message>">

									<a title="<fmt:message key="title.maj"></fmt:message>"
										href="<c:url value="/creationUser">
											<c:param name="idUser" value="${ mapUsers.id }" /></c:url>">
										<img src="images/corriger.png" alt=""
										class="imgajouterinfobouteilleupdate">
									</a>



									<div class="divnombrechaquebdanscave">

										<div class="divdescrp2lh">
											<div class="descriptmainnom">
												<c:out value="${ mapUsers.nom }" />
											</div>
										</div>

                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                id
                                                <c:out value="${ mapUsers.id}" />
                                            </div>
                                        </div>

										<div class="divdescrp2lh">
											<div class="descriptrest">
												email
												<c:out value="${ mapUsers.email}" />
											</div>
										</div>

										<div class="divdescrp2lh">
											<div class="descriptrest">
												motDePasse
												<c:out value="${ mapUsers.motDePasse}" />
											</div>
										</div>

                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                dateInscription
                                                <c:out value="${ mapUsers.dateInscription}" />
                                            </div>
                                        </div>
                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                isPayed
                                                <c:out value="${ mapUsers.isPayed}" />
                                            </div>
                                        </div>
                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                isWineproducer
                                                <c:out value="${ mapUsers.isWineproducer}" />
                                            </div>
                                        </div>
                                        <div class="divdescrp2lh">
                                            <div class="descriptrest">
                                                isWithoutAD
                                                <c:out value="${ mapUsers.isAllowedAD}" />
                                            </div>
                                        </div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<!-- *********************************************************Fin Collection Users************************************************************ -->

				</div>

		<a
			title="ajouter user"
			id="linkajouterelement" href="#"> <img src="images/addprod.png"
			data-w-id="2268c7f2-5bc3-7d72-53f7-e00c170ae7a0"
			style="-webkit-transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
			-moz-transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
			-ms-transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
			transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
			alt="" class="ajouteelemimg">

		</a>
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
				<c:when test="${!empty erreurs}">

					<c:if test="${!empty erreurs['echecDel']}">
						<span class="erreur">echec suppression</span>
					</c:if>

					<br>
					<c:if test="${!empty erreurs['erreurDao']}">
						<span class="erreur">erreur DAO
							${erreurs['erreurDao']}</span>
					</c:if>


					<div class="divhcenterelement">
						<a title="<fmt:message key="button.title.sortir"></fmt:message>"
							href="<c:url value="/listeUsers"></c:url>"
							class="buttonstandart"><fmt:message key="button.title.sortir"></fmt:message></a>
					</div>
				</c:when>

				<c:otherwise>
					<form method="post" action="" class="forminside" id="formDel">
						<label for="nom-5" class="warningdel">Êtes-vous sûr de vouloir supprimer utilisateur </label>
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





	<!-- **********************************************Form Update/Creation User***************************************************** -->

	<div class="divdisapppourtriggerupdate" id="divdisapppourtriggerupdate<c:if test="${empty utilisateur}">sansrecharger</c:if><c:if test="${!empty utilisateur}">user</c:if>"
		style="<c:if test="${empty form.erreurs && empty utilisateur}">display: none</c:if>">
		<div class="formmain">

			<div class="divhrightelementsmall">
				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
					<c:if test="${!empty utilisateur}"> href="<c:url value="/listeUsers"></c:url>"</c:if> class="sortirformsansrechargement" >
					
					 <img src="images/sortir.png" width="30"
					height="50"
					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
					-moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
					-ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
					transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
					alt="" class="sortirformimg">
				</a>
			</div>
			<form id="formCreation" name="email-form" data-name="Email Form"
				class="forminside" method="post"
				action="<c:url value="/creationUser"><c:if test="${!empty utilisateur}"><c:param name="idUser" value="${ utilisateur.id }" /></c:if></c:url>">
				<jsp:include page="formUser.jsp" ></jsp:include>
				<div class="divbutform">
					<input type="submit"
						value="<fmt:message key="button.valider"></fmt:message>"
						data-wait="<fmt:message key="button.wait"></fmt:message>"
						class="buttonstandart">
						<input type="reset"
						value="<fmt:message key="button.reinitialiser"></fmt:message>"
						data-wait="<fmt:message key="button.wait"></fmt:message>"
						class="buttonstandart" />
				</div>
			</form>
		</div>
	</div>

	<!-- **********************************************resultat succes***************************************************** -->

	<c:choose>
		<c:when
			test="${empty form.successCreation && empty form.successMaj && empty successDel}"></c:when>
		<c:otherwise>
			<script>
				var timer = setTimeout(function() {
					window.location = 'listeUsers'
				}, 3000);
			</script>
		</c:otherwise>
	</c:choose>

	<div class="resultat" id="resultat"
		style="<c:if test="${empty form.successCreation && empty form.successMaj && empty successDel}">display: none</c:if>">
		<div class="formmain">
			<div class="divhrightelementsmall">
				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
					href="<c:url value="/listeUsers"></c:url>"
					class="sortirformimg"> <img src="images/sortir.png" width="30"
					height="50"
					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
					alt="" class="sortirformimg">
				</a>
			</div>
			<div class="divhcenterelement">
				<c:if test="${!empty form.successCreation}">
					<span class="maj">Succ\u00e8s de la cr\u00e9ation user
						${form.successCreation}</span>
				</c:if>
				<c:if test="${!empty form.successMaj}">
					<span class="maj">Succ\u00e8s de la mise \u00e0 jour user
						${form.successMaj}</span>
				</c:if>
				<c:if test="${!empty successDel}">
					<span class="maj">user ${successDel}
						<fmt:message key="producteurs.success.delsecond"></fmt:message></span>
				</c:if>
			</div>

		</div>
	</div>


	<script src="js/jqcave.js" type="text/javascript"></script>
	<script src="js/jscave.js" type="text/javascript"></script>
	<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>