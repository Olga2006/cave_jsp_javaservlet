<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />

<div data-w-id="3282efeb-b036-c5b8-761f-b8864321ce3b" class="sidemenu">
	 <a title="<fmt:message key="menu.consultercaves"></fmt:message>" data-w-id="518b3de9-ff38-a88a-99d4-8fd16f17c843" 
	 href="listeCaves" class="sidemenulink w-inline-block"><img src="images/caves.png" alt="" class="imgsidemenu"></a>
	 
	 <a data-w-id="3282efeb-b036-c5b8-761f-b8864321ce3c" 
	 href="listeBouteilles" class="sidemenulink w-inline-block"><img src="images/menuBouteilles.png" title="<fmt:message key="menu.listebouteilles"></fmt:message>" alt="" class="imgsidemenu"></a>
	 
	 <a data-w-id="3282efeb-b036-c5b8-761f-b8864321ce3e" 
	 href="listeProducteurs" class="sidemenulink w-inline-block"><img src="images/menuProducteur.png" title="<fmt:message key="menu.listevignerons"></fmt:message>" alt="" class="imgsidemenu"></a>
	 
	 <a title="<fmt:message key="menu.listebouteillesconsommer"></fmt:message>" data-w-id="3282efeb-b036-c5b8-761f-b8864321ce40" 
	 href="listeBouteillesAConsomer" class="sidemenulink w-inline-block"><img src="images/aConsomer.png" alt="" class="imgsidemenu"></a>
	 
	 <a title="<fmt:message key="menu.listecourses"></fmt:message>" data-w-id="3282efeb-b036-c5b8-761f-b8864321ce42" 
	 href="listeCourses" class="sidemenulink w-inline-block"><img src="images/menuListCours.png" alt="" class="imgsidemenu"></a>




    <div data-w-id="3282efeb-b036-c5b8-761f-b8864321ce44" class="sidemenulink"><img src="images/menu.png" alt="" class="imgsidemenumain"></div>

            <c:if test="${!empty sessionUtilisateur}">
              <a title="<fmt:message key="title.majmonprofil"></fmt:message>" class="sidemenulink"
    			href="<c:url value="/inscription">
    				<c:param name="idUser" value="${ sessionUtilisateur.id }" />
    				</c:url>">
    			<img src="images/corriger.png" alt=""
    			class="imgsidemenu">
    		  </a>
            </c:if>
            <c:if test="${!empty sessionUtilisateur && sessionUtilisateur.isWineproducer}">
              <a title="<fmt:message key="pub.title.adeditor"></fmt:message>" class="sidemenulink"
                href="<c:url value="/managerAD"></c:url>">
                <img src="images/pubredacteur.png" alt=""
                class="imgsidemenu">
              </a>
            </c:if>
                 <c:if test="${isAdmin}">
                     <a title="admin"
                     href="listeUsers" class="sidemenulink w-inline-block descripattention" alt="" class="imgsidemenu">admin Users</a>
                     <a title="admin"
                     href="listeErreurs" class="sidemenulink w-inline-block descripattention" alt="" class="imgsidemenu">admin Erreurs</a>
                     <a title="admin"
                     href="sqlUpdate" class="sidemenulink w-inline-block descripattention" alt="" class="imgsidemenu">request SQL</a>
                 </c:if>

 </div>

  
  
  
  
  
  
  
  
  
  
  
  
  
  