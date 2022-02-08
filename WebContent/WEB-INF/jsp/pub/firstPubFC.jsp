<%@ page pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<div class="coverforpub"></div>
<div class="divdisapppourtriggerupdate">
    <div class="pubcontainer">
        <div class="divhrightelementsmall">
            <a title="<fmt:message key="button.title.sortir"></fmt:message>" class="sortirpubsansrechargement" >
                 <img src="images/sortir.png" width="30"
                height="50"
                style="-webkit-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0);
                -moz-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0);
                -ms-transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0);
                transform: translate3d(0, 0, 0) scale3d(1, 1, 1) rotateX(0) rotateY(0) rotateZ(0) skew(0, 0)"
                alt="" class="sortirformimg">
            </a>
        </div>
        <div class="slideshow-container">
            <c:forEach items="${listFirstAD}" var="mapPub" varStatus="bouclePub">
                <div class="pubslidesFC fade">
                    <a href="${mapPub.url}">
                        <img class="slidesimg" src="https://caveweb.s3.eu-west-3.amazonaws.com/${mapPub.image}" style="width:100%">
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script>
var slideIndexFC = 0;
showSlidesFC();

function showSlidesFC() {
  var i;
  var slides = document.getElementsByClassName("pubslidesFC");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  slideIndexFC++;
  if (slideIndexFC > slides.length) {slideIndexFC = 1}
  slides[slideIndexFC-1].style.display = "block";
  setTimeout(showSlidesFC, 2000); // Change image every 2 seconds
}
</script>
