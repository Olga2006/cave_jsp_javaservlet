<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html data-wf-page="5c8a4e5935905d04eb6497f8"
	data-wf-site="5bf300026add22d3cd0f2499">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="fonctionalites.title"></fmt:message></title>
    <meta name="description" content="<fmt:message key="fonctionalites.description"></fmt:message>">
    <meta name="keywords" content="<fmt:message key="fonctionalites.keywords"></fmt:message>">
    <jsp:include page="/WEB-INF/jsp/head/head.jsp"></jsp:include>
</head>
<body class="body">
    <c:if test="${ !empty sessionUtilisateur}">
    <jsp:include page="/WEB-INF/jsp/left/left.jsp"></jsp:include>
    </c:if>
	<jsp:include page="/WEB-INF/jsp/nav/nav.jsp"></jsp:include>

	<div class="coremembre">
		<div class="bvcellar"></div>
		  <div class="divbvdanscave descriptnom">
		  <h1 class="heading bvheading">FONCTIONNALITÉS</h1>
		</div>
        <div class="collectionscrollcaves">
            <div class="cibleblog">

                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-4 containercentercontent">
                           <img src="images/pagesfonct/f1.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-8">
                            <div class="descripattention descriptiontitle">
                               <fmt:message key="fonct.title1"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                               <fmt:message key="fonct.descr1a"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-4 containercentercontent">
                           <img src="images/pagesfonct/f2.jpg" alt="" class="imgblog" class="max-height: 50px;">
                        </div>
                        <div class="w-col w-col-8">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title2"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr2a"></fmt:message>
                                <img src="images/menu.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr2b"></fmt:message>
                                <fmt:message key="fonct.descr21a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                               <img src="images/caves.png" class="imgiconfonc"><fmt:message key="fonct.descr22a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <img src="images/menuBouteilles.png" class="imgiconfonc"><fmt:message key="fonct.descr23a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                               <img src="images/menuProducteur.png" class="imgiconfonc"><fmt:message key="fonct.descr24a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                               <img src="images/aConsomer.png" class="imgiconfonc"><fmt:message key="fonct.descr25a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <img src="images/menuListCours.png" class="imgiconfonc"><fmt:message key="fonct.descr26a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                               <fmt:message key="fonct.descr27a"></fmt:message>
                               <img src="images/corriger.png" class="imgiconfonc">
                               <fmt:message key="fonct.descr27b"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>




                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-6 containercentercontent">
                           <img src="images/pagesfonct/f3.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-6">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title3"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr3a"></fmt:message> <img src="images/adddcave.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr3b"></fmt:message> <img src="images/corriger.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr3c"></fmt:message> <img src="images/supprimer.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr3d"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-6 containercentercontent">
                           <img src="images/pagesfonct/f4.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-6">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title4"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr4a"></fmt:message>
                                <img src="images/adddcave.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr4b"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr41a"></fmt:message>
                                <img src="images/entreecave.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr41b"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-6 containercentercontent">
                           <img src="images/pagesfonct/f5.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-6">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title5"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr5a"></fmt:message>
                                <img src="images/addBouteille.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr5b"></fmt:message>
                                <img src="images/sortirbouteille.png" class="imgiconfonc" style="width: auto;">
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr51a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr52a"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-6 containercentercontent">
                           <img src="images/pagesfonct/f6b.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-6">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title6"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr6a"></fmt:message>
                                <img src="images/menuBouteilles.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr6b"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr61a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr62a"></fmt:message>
                                <img src="images/addboutshoppinglist.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr62b"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr63a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr64a"></fmt:message>
                                <img src="images/creerXml.png" class="imgiconfonc">
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr65a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr66a"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="blockoneitem">
                    <div class="divblog">
                        <div class="w-col w-col-6 containercentercontent">
                           <img src="images/pagesfonct/f7.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-6">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title7"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr7a"></fmt:message>
                                <img src="images/menuProducteur.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr7b"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr71a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr72a"></fmt:message><img src="images/addprod.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr72b"></fmt:message><img src="images/corriger.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr72c"></fmt:message><img src="images/supprimer.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr72d"></fmt:message><img src="images/creerXml.png" class="imgiconfonc">

                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr73a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr74a"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="blockoneitem">
                    <div class="divblog" >
                        <div class="w-col w-col-6 containercentercontent">
                           <img src="images/pagesfonct/f8.jpg" alt="" class="imgblog">
                        </div>
                        <div class="w-col w-col-6">
                            <div class="descripattention descriptiontitle">
                                <fmt:message key="fonct.title8"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr8a"></fmt:message>
                                <img src="images/aConsomer.png" class="imgiconfonc">
                                <fmt:message key="fonct.descr8b"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr81a"></fmt:message>
                            </div>
                            <div class="descriptrest descriptionitem">
                                <fmt:message key="fonct.descr82a"></fmt:message>
                                <img src="images/signeboire.jpg" class="imgiconfonc">
                                <fmt:message key="fonct.descr82b"></fmt:message>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
	</div>








	<script src="js/jqcave.js" type="text/javascript"></script>
	<script src="js/jscave.js" type="text/javascript"></script>
	<!-- [if lte IE 9]><script src="https://cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif] -->
</body>
</html>