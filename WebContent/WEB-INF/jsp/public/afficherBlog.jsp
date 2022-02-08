<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html data-wf-page="5c87c9153f656c8f0ad43c3c"
	data-wf-site="5bf300026add22d3cd0f2499">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="blog.title"></fmt:message></title>
    <meta name="description" content="<fmt:message key="blog.description"></fmt:message>">
    <meta name="keywords" content="<fmt:message key="blog.keywords"></fmt:message>">
    <jsp:include page="/WEB-INF/jsp/head/head.jsp"></jsp:include>
    <script src="https://sdk.amazonaws.com/js/aws-sdk-2.19.0.min.js"></script>
</head>
<body class="body">
<div id="coverforformulaire" style="<c:if test="${empty form.erreurs && empty blog}">display: none</c:if>"></div>
	<c:if test="${ !empty sessionUtilisateur}"><jsp:include
			page="/WEB-INF/jsp/left/left.jsp"></jsp:include></c:if>

	<jsp:include page="/WEB-INF/jsp/nav/nav.jsp"></jsp:include>

	<div class="coremembre">
		<div class="bvcellar"></div>
		<div class="divbvdanscave descriptnom">
			<h1 class="heading bvheading">Blog</h1>
		</div>


		<c:choose>
			<c:when test="${ empty blogs }">

				<div class="divbvdanscave descriptnom">
					<h1 class="heading bvheading">Coming soon</h1>
				</div>

			</c:when>
			<c:otherwise>
				<div class="collectionscroll">
					<c:forEach items="${blogs }" var="mapBlog" varStatus="boucle">
						<div class="cibleblog" style="width: 100%;">
							<div class="blockoneitem">

								<div class="divblog" style="height: 150px;">

								     <c:if test="${isAdmin}">
                                         <img src="images/supprimer.png" alt=""
                                             class="imgajouterinfoblogdel delstyle"
                                             id="${ mapBlog.id} ${ mapBlog.nomArticle}"
                                             title="<fmt:message key="title.supprimer"></fmt:message>">

                                         <a title="<fmt:message key="title.maj"></fmt:message>"
                                             href="<c:url value="/creationBlog">
                                                 <c:param name="idBlog" value="${ mapBlog.id }" /></c:url>">
                                             <img src="images/corriger.png" alt=""
                                             class="imgajouterinfobouteilleupdate">
                                         </a>
                                     </c:if>



									<div class="column-6 w-col w-col-6 w-col-small-6 w-col-tiny-6">

										<div class="divdescrp2lh" style="width: auto;">

                                        <img
                                            src="<c:choose>
                                            <c:when test="${!empty mapBlog.image}">https://caveweb.s3.eu-west-3.amazonaws.com/${mapBlog.image}</c:when>
                                            <c:otherwise>https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg</c:otherwise></c:choose>"
                                            alt="" class="imgblog" >

										</div>
									</div>
									<div class="column-6 w-col w-col-6 w-col-small-6 w-col-tiny-6">
										<div class="divdescrp2lh">
											<div class="descriptmainnom">
												<c:out value="${ mapBlog.nomArticle }" />
											</div>
											<div class="descripattention">
												<c:out value="${ mapBlog.subtheme }" />
											</div>
										</div>
										<div class="divdescrp2lh">
											<div class="descripattention">
												<fmt:formatDate pattern="yyyy-MM-dd"
													value="${mapBlog.dateEdition}" />

											</div>
										</div>
										<div class="divdescrp2lh ">
											<div class="descriptrest">
												<c:out value="${ mapBlog.nomAuteur }" />
											</div>
										</div>
										<div class="descriptrest">
											<p>
												<c:out value="${ mapBlog.article1 }" />
											</p>
										</div>

									</div>



									<div class="divdescrp2lh ">

										<div class="descriptrest">
											<p>
												<c:out value="${ mapBlog.article2 }" />
											</p>
										</div>
										<div class="descriptrest">
											<p>
												<c:out value="${ mapBlog.article3 }" />
											</p>
										</div>


									</div>

								</div>



								<div class="divimgexpand" style="cursor: pointer;">
									<div class="divdescrrightbottom">
										<div style="cursor: pointer;">
											<fmt:message key="title.plusinformations"></fmt:message>
										</div>
									</div>
									<img src="images/expand.png" class="imgexpand"
										title="<fmt:message key="title.plusinformations"></fmt:message>">
								</div>


								<div class="divimgreduce"
									style="cursor: pointer; display: none;">
									<div class="divdescrrightbottom">
										<div style="cursor: pointer;">
											<fmt:message key="title.reduireinformations"></fmt:message>
										</div>
									</div>
									<img src="images/expandh.png" class="imgexpand"
										title="<fmt:message key="title.reduireinformations"></fmt:message>">
								</div>

							</div>

						</div>

					</c:forEach>

				</div>

			</c:otherwise>
		</c:choose>
        <c:if test="${isAdmin}">
			<a
			title="ajouter blog"
			id="linkajouterelement" href="#"> <img src="images/addprod.png"
			data-w-id="2268c7f2-5bc3-7d72-53f7-e00c170ae7a0"
			style="-webkit-transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
			-moz-transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
			-ms-transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); 
			transform: translate3d(0, -143PX, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
			alt="" class="ajouteelemimg">
			</a>
        </c:if>
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

    					<c:if test="${!empty erreurs['erreurDao']}">
    						<span class="erreur">echec suppression blog ${erreurs['echecDel']}. Erreur DAO ${erreurs['erreurDao']}
    						${erreurs['echecDel']}</span>
    					</c:if>

    					<div class="divhcenterelement">
    						<a title="<fmt:message key="button.title.sortir"></fmt:message>"
    							href="<c:url value="/blog"></c:url>"
    							class="buttonstandart"><fmt:message key="button.title.sortir"></fmt:message></a>
    					</div>
    				</c:when>

    				<c:otherwise>
    					<form method="post" action="" class="forminside" id="formDel">
    						<label for="nom-5" class="warningdel">Êtes-vous sûr de vouloir supprimer blog </label>
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





    	<!-- **********************************************Form Update/Creation Blog***************************************************** -->

    	<div class="divdisapppourtriggerupdate" id="divdisapppourtriggerupdate<c:if test="${empty blog}">sansrecharger</c:if><c:if test="${!empty utilisateur}">user</c:if>"
    		style="<c:if test="${empty form.erreurs && empty blog}">display: none</c:if>">
    		<div class="formmain">

    			<div class="divhrightelement">
    				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
    					<c:if test="${!empty blog}"> href="<c:url value="/blog"></c:url>"</c:if> class="sortirformsansrechargement" >

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
                    data-name="${ blog.id }" enctype="multipart/form-data"
                    class="forminside" action="">

                    <div class="erreur" id="results" style="display: none;"></div>
                    <div id="idBlog" style="display: none;"><c:if test="${ !empty blog}">${ blog.id}</c:if>
                    </div>
                    <span class="erreur" style="display: none" id="erreurImg">
                     <fmt:message key="erreurImage"></fmt:message>
                        </span>

                        <span class="erreur" style="display: none;" id="erreurFormat">
                          <fmt:message key="erreurImage.format"></fmt:message>
                        </span>

                        <span class="erreur" style="display: none" id="erreurTaille">
                         <fmt:message key="erreurImage.taille"></fmt:message>
                        </span>
                    <div id="divimgacharger">
                    <img
                        src="<c:choose>
                        <c:when test="${!empty blog.image}">https://caveweb.s3.eu-west-3.amazonaws.com/${blog.image}</c:when>
                        <c:otherwise>https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg</c:otherwise></c:choose>"
                        alt="" class="imgetiquette" id="imgetiquette" >

                        <div id="divchargeringimgmask" style="display: none;">
                        <img id="chargeringimg" alt="" src="https://caveweb.s3.eu-west-3.amazonaws.com/photosite/lg.ajax-spinner-gif.gif"> </div>
                    </div>

                    <a id="butchargerimg" class="tabformul"><fmt:message
                            key="image.changer"></fmt:message></a>

                    <div id="ciblechargerimg" style="display: none;">
                        <label for="image" class="labelformbouteille"><fmt:message
                                key="image.charger"></fmt:message>
                                <span
                            class="requis"><br>(<fmt:message
                                    key="image.formats"></fmt:message>)</span> </label>
                                     <input
                            type="file" id="imageBlog" name="image" class="inputformbouteille" />
                    </div>

                </form>


    			<form id="formCreation" name="email-form" data-name="Email Form"
    				class="forminside" method="post"
    				action="<c:url value="/creationBlog"><c:if test="${!empty blog}"><c:param name="idBlog" value="${ blog.id }" /><c:param name="image" value="${ blog.image }" /></c:if></c:url>">


    				<jsp:include page="../restreint/formBlog.jsp" ></jsp:include>

    				<label class="labelformbouteille"> <span class="requis">*<fmt:message
    							key="title.champsobligatoires"></fmt:message></span></label>
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
    					window.location = 'blog'
    				}, 3000);
    			</script>
    		</c:otherwise>
    	</c:choose>

    	<div class="resultat" id="resultat"
    		style="<c:if test="${empty form.successCreation && empty form.successMaj && empty successDel}">display: none</c:if>">
    		<div class="formmain">
    			<div class="divhrightelementsmall">
    				<a title="<fmt:message key="button.title.sortir"></fmt:message>"
    					href="<c:url value="/blog"></c:url>"
    					class="sortirformimg"> <img src="images/sortir.png" width="30"
    					height="50"
    					style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); -ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0); transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
    					alt="" class="sortirformimg">
    				</a>
    			</div>
    			<div class="divhcenterelement">
    				<c:if test="${!empty form.successCreation}">
    					<span class="maj">Succ\u00e8s de la cr\u00e9ation blog
    						${form.successCreation}</span>
    				</c:if>
    				<c:if test="${!empty form.successMaj}">
    					<span class="maj">Succ\u00e8s de la mise \u00e0 jour blog
    						${form.successMaj}</span>
    				</c:if>
    				<c:if test="${!empty successDel}">
    					<span class="maj">blog ${successDel}
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