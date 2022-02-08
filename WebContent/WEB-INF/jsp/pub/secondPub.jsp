<%@ page pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />

 <div class="cible" style="<c:if test="${isAllowedAd}">display: none</c:if>">
    <div class="blockoneitem">
        <div class="sousblockoneitem">
           <div class="pubcontainer">
               <div class="slideshow-container">
                   <c:forEach items="${listSecondAD}" var="mapPub" varStatus="bouclePub">

                       <div class="pubslides fade">
                           <a href="${mapPub.url}">
                               <img class="slidesimgs" src="https://caveweb.s3.eu-west-3.amazonaws.com/${mapPub.image}" style="width:100%">
                           </a>
                       </div>
                   </c:forEach>
               </div>
           </div>
        </div>
    </div>
</div>

<script>
var slideIndex = 0;
showSlides();

function showSlides() {
  var i;
  var slides = document.getElementsByClassName("pubslides");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  slideIndex++;
  if (slideIndex > slides.length) {slideIndex = 1}
  slides[slideIndex-1].style.display = "block";
  setTimeout(showSlides, 3000); // Change image every 3 seconds
}
</script>
