<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html data-wf-page="5c87c9153f656c8f0ad43c3c"
	data-wf-site="5bf300026add22d3cd0f2499">
<head>
<meta charset="utf-8">
<title>AD</title>
<jsp:include page="/WEB-INF/jsp/head/head.jsp"></jsp:include>
<script src="https://sdk.amazonaws.com/js/aws-sdk-2.19.0.min.js"></script>
</head>
<body class="body">
<div id="coverforformulaire" style="<c:if test="${empty form.erreurs && empty pub}">display: none</c:if>"></div>
	<c:if test="${ !empty sessionUtilisateur}"><jsp:include
			page="/WEB-INF/jsp/left/left.jsp"></jsp:include></c:if>

	<jsp:include page="/WEB-INF/jsp/nav/nav.jsp"></jsp:include>

	<div class="coremembre">
		<div class="bvcellar"></div>



		<c:choose>
			<c:when test="${empty sessionUtilisateur.pubs}">

				<div class="divbvdanscave descriptnom">
					<h1 class="heading bvheading">${sessionUtilisateur.nom },</h1>
                    <h1 class="heading bvheading">
                        <fmt:message key="pub.messagecarnetpubvide"></fmt:message>
                    </h1>
				</div>

			</c:when>
			<c:otherwise>
					<div class="divbvdanscave descriptnom">
            			<h1 class="heading bvheading">
                            <fmt:message key="pub.title"></fmt:message>
                        </h1>
            		</div>
				<div class="collectionscroll">
					<c:forEach items="${sessionUtilisateur.pubs}" var="mapPub" varStatus="boucle">
						<div class="cibleblog" style="width: 100%;">
							<div class="blockoneitem">

								<div class="divblog" style="height: 150px; width:100%">
                                     <img src="images/supprimer.png" alt=""
                                         class="imgajouterinfopubdel delstyle"
                                         data-id="${mapPub.id}" data-img="${mapPub.image}"
                                         title="<fmt:message key="title.supprimer"></fmt:message>">

                                     <a title="<fmt:message key="title.maj"></fmt:message>"
                                         href="<c:url value="/creationPub">
                                             <c:param name="idPub" value="${mapPub.id}" /></c:url>">
                                         <img src="images/corriger.png" alt=""
                                         class="imgajouterinfobouteilleupdate">
                                     </a>

									<div class="column-6 w-col w-col-6 w-col-small-6 w-col-tiny-6">

										<div class="divdescrp2lh" style="width: 100%;">

                                        <img
                                            src="<c:choose>
                                            <c:when test="${!empty mapPub.image}">https://caveweb.s3.eu-west-3.amazonaws.com/${mapPub.image}</c:when>
                                            <c:otherwise>https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultepubphoto.png</c:otherwise></c:choose>"
                                            alt="" class="imgpub" >

										</div>
									</div>
									<div class="column-6 w-col w-col-6 w-col-small-6 w-col-tiny-6" style="margin-top: 20px;">
										<div class="divdescrp2lh">
											
											<div class="descripattention">
												URL:
											</div>
											<div class="descriptrest">
                                                <c:out value="${mapPub.url}" />
                                            </div>

										</div>
										<div class="divdescrp2lh ">
											<div>
											<label class="labelformbouteille">

                                                <c:choose>
                                                    <c:when test="${(mapPub.isFirst.toString().equals('true')&&inFirstAD) || (mapPub.isSecond.toString().equals('true')&&inSecondAD)}">
                                                        <div class="descripattention">
                                                          <label><fmt:message key="pub.category"></fmt:message>:</label>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="descripattention">
                                                          <label><fmt:message key="pub.nonaffiche"></fmt:message></label>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>


                                                 <c:if test="${mapPub.isFirst.toString().equals('true')&&inFirstAD}">
                                                   <div class="descriptrest"><fmt:message key="pub.categoryfirst"></fmt:message></div>
                                                 </c:if>

												<br>
                                                <c:if test="${mapPub.isSecond.toString().equals('true')&&inSecondAD}">
                                                  <div class="descriptrest"><fmt:message key="pub.categorysecond"></fmt:message></div>
                                                </c:if>
											</div>
										</div>

									</div>


								</div>


							</div>

						</div>

					</c:forEach>

				</div>

			</c:otherwise>
		</c:choose>
			<a
			title="<fmt:message key="pub.title.ajouterpub"></fmt:message>"
			id="linkajouterelement" href="#"> <img src="images/addpub.png"
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
                            <span class="erreur"><fmt:message
                                    key="pub.resultat.delfirst"></fmt:message>
                                ${erreurs['echecDel']} <fmt:message
                                    key="pub.unsuccess.delsecond"></fmt:message></span>
                        </c:if>
                        <br>
                        <c:if test="${!empty erreurs['erreurDao']}">
                            <span class="erreur"><fmt:message key="erreur.dao"></fmt:message>
                                ${erreurs['erreurDao']}</span>
                        </c:if>

    					<div class="divhcenterelement">
    						<a title="<fmt:message key="button.title.sortir"></fmt:message>"
    							href="<c:url value="/managerAD"></c:url>"
    							class="buttonstandart"><fmt:message key="button.title.sortir"></fmt:message></a>
    					</div>
    				</c:when>

    				<c:otherwise>
    					<form method="post" action="" class="forminside" id="formDel">
    						<label for="nom-5" class="warningdel">
    						<fmt:message key="pub.del.warningdel"></fmt:message></label>
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





    	<!-- **********************************************Form Update/Creation pub***************************************************** -->

    	<div class="divdisapppourtriggerupdate" id="divdisapppourtriggerupdate<c:if test="${empty pub}">sansrecharger</c:if><c:if test="${!empty pub}"></c:if>"
    		style="<c:if test="${empty form.erreurs && empty pub}">display: none</c:if>">
    		<div class="formmain">

    			<div class="divhrightelement">
    				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
    					<c:if test="${!empty pub}"> href="<c:url value="/managerAD"></c:url>"</c:if> class="sortirformsansrechargement" >

    					 <img src="images/sortir.png" width="30"
    					height="50"
    					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0);
    					-moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0);
    					-ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0);
    					transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
    					alt="" class="sortirformimg">
    				</a>
    			</div>

                <form id="prechargerimgform" method="post"
                    data-name="${pub.id}" enctype="multipart/form-data"
                    class="forminside" action="">

                    <div class="erreur" id="results" style="display: none;"></div>
                    <div id="idPub" style="display: none;"><c:if test="${!empty pub}">${pub.id}</c:if></div>
                    <div id="idUser" style="display: none;">${sessionUtilisateur.id}</div>
                    <span class="erreur" style="display: none" id="erreurImg">
                     <fmt:message key="erreurImage"></fmt:message>
                        </span>

                        <span class="erreur" style="display: none;" id="erreurFormat">
                          <fmt:message key="erreurImage.format"></fmt:message>
                        </span>

                        <span class="erreur" style="display: none" id="erreurTaille">
                         <fmt:message key="erreurImage.taille"></fmt:message>
                        </span>

                        <span class="erreur" style="display: none" id="requiredimg">
                           <fmt:message key="pub.requiredimg"></fmt:message>
                        </span>

                    <div id="divimgacharger"><img
                        src="<c:choose>
                        <c:when test="${!empty pub.image}">https://caveweb.s3.eu-west-3.amazonaws.com/${pub.image}</c:when>
                        <c:otherwise>https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultepubphoto.png</c:otherwise></c:choose>"
                        alt="" class="imgetiquette" id="imgetiquette" >
                        <div id="divchargeringimgmask" style="display: none;">
                        <img id="chargeringimg" alt="" src="https://caveweb.s3.eu-west-3.amazonaws.com/photosite/lg.ajax-spinner-gif.gif"> </div>
                    </div>

                    <a id="butchargerimg" class="tabformul" style="<c:if test="${empty pub.image}">display: none</c:if>"><fmt:message
                            key="image.changer"></fmt:message></a>

                    <div id="ciblechargerimg" style="<c:if test="${!empty pub.image}">display: none</c:if>">
                        <label for="image" class="labelformbouteille"><fmt:message
                                key="image.charger"></fmt:message><span class="requis">*</span>
                                <span
                            class="requis"><br>(<fmt:message
                                    key="image.formats"></fmt:message>)</span> </label>
                                     <input
                            type="file" id="imagePub" name="image" class="inputformbouteille" />
                    </div>
                </form>

    			<form id="formCreationPub" name="email-form" data-name="Email Form"
    				class="forminside" method="post" data-img = "${pub.image}" data-id = "${pub.id}">

    				<jsp:include page="../restreint/formPub.jsp" ></jsp:include>
                    <label class="labelformbouteille" id="txtchampsobligatoires" style="<c:if test="${!empty pub.image}">display: none</c:if>">
                      <span class="requis">*<fmt:message key="title.champsobligatoires"></fmt:message></span>
					</label>


                  <c:if test="${inFirstAD || inSecondAD}">
                     <label class="labelformbouteille">
                       <span class="requis">**<fmt:message key="pub.singleutilisation"></fmt:message></span>
                     </label>
                  </c:if>
                  <c:if test="${!inFirstAD || !inSecondAD}">
                      <label class="labelformbouteille">
                        <span class="requis">***<fmt:message key="tip.option"></fmt:message></span>
                      </label>
                  </c:if>


    				<div class="divbutform">
    					<input type="submit" id="submitPost"
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
    					window.location = 'managerAD'
    				}, 3000);
    			</script>
    		</c:otherwise>
    	</c:choose>

    	<div class="resultat" id="resultat"
    		style="<c:if test="${empty form.successCreation && empty form.successMaj && empty successDel}">display: none</c:if>">
    		<div class="formmain">
    			<div class="divhrightelementsmall">
    				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
    					href="<c:url value="/managerAD"></c:url>"
    					class="sortirformimg"> <img src="images/sortir.png" width="30"
    					height="50"
    					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
    					alt="" class="sortirformimg">
    				</a>
    			</div>
    			<div class="divhcenterelement">
    				<c:if test="${!empty form.successCreation}">
    					<span class="maj"><fmt:message
                            key="pub.success.creation"></fmt:message>
                        ${form.successCreation}</span>
    				</c:if>
    				<c:if test="${!empty form.successMaj}">
    					<span class="maj"><fmt:message key="pub.success.maj"></fmt:message></span>
    				</c:if>
    				<c:if test="${!empty successDel}">
    					<span class="maj"><fmt:message
                            key="pub.resultat.delfirst"></fmt:message> ${successDel}
                        <fmt:message key="pub.success.delsecond"></fmt:message></span>
    				</c:if>
    			</div>

    		</div>
    	</div>

	<script src="js/jqcave.js" type="text/javascript"></script>
	<script src="js/jscave.js" type="text/javascript"></script>
	<script src="js/pub.js" type="text/javascript"></script>
	<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>