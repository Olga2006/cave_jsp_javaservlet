function openTabA() {
	$("#mainTabA").attr("class", "tabactive");
	$("#mainTabB").attr("class", "tabpassive");
	$("div#tabA").show();
	$("div#tabB").hide();
}

function openTabB() {
	$("#mainTabB").attr("class", "tabactive");
	$("#mainTabA").attr("class", "tabpassive");
	$("div#tabB").show();
	$("div#tabA").hide();
}

function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("text", ev.target.id);
	$(".descrcotenubouteille").hide();
}

function drop(ev) {
	ev.preventDefault();
	var dataNewPositionFull = ev.target.id;
	var dataNewPosition = dataNewPositionFull.split(" ");
	/*
	 * trouver
	 * idNewPosition et
	 * tab
	 */
	var dataId = dataNewPosition[0];
	var dataRow = dataNewPosition[1];

	var data = ev.dataTransfer.getData("text");

	var dataLastPosition = data.split(" ");
	/*
	 * trouver idBouteille
	 * idLastPosition
	 */
	var dataBouteille = dataLastPosition[0];
	var dataIdLastPosition = dataLastPosition[1];
	ev.target.appendChild(document.getElementById(data));
	/* alert("enregistrer changement"); */
	location.replace("ajouterBouteille?idPosition=" + dataId +
		"&idLastPosition=" + dataIdLastPosition + "&idBouteille=" +
		dataBouteille + "&tab=" + dataRow);

}

$(document)
	.ready(
		function () {
			/*
			 * -------------------CHANGER LANGUE !!!!! Ne Marche pas----------------------------------
			 */
			var locationhref = window.location.href;
			$(".imglanguage").click(
				function () {
					var language = $(this).attr("data-language");
					$("#languagechoise").text(language);
					location.replace("changerLanguage?language=" + language + "&locationhref=" + locationhref);
				});

			/*
			 * -------------------REDACTEUR
			 * CAVE----------------------------------
			 */

			 $(".nomcompartiment").click(function(){
                 var inputblock = $(this).next();

                 if(inputblock.is(":hidden")){
                 inputblock.show();
                 } else inputblock.hide();
             });

           $(".validnamecompartment").click(function(){
                var params = $(this).prev().attr("data-name");
                var namecompartiment = $(this).parent().prev(".nomcompartiment").attr("data-name");
                var newnamecompartiment = $(this).prev().val();
                if (newnamecompartiment != undefined){
                newnamecompartiment = newnamecompartiment.toUpperCase();
                }
                if (namecompartiment != newnamecompartiment){
                window.location = params + "&newNameCompartiment=" + newnamecompartiment;
                } else {$(this).parent(".inputblocknomcompartiment").hide();}
           });

 $('.referencePositionOfCheckboxes').change(function () {
 if ($(this).is(":checked")){
 $(this).parents('.refbouteill').next().css({"border-width": "5px"});
 } else {
 $(this).parents('.refbouteill').next().css({"border-width": "0px"});
 }
 });

$('.nbrPosition').change(function () {
    var nbrPosition = $(this).val();
    var params = $(this).attr("id");
    window.location = params + "&nbrPosition=" + nbrPosition;
});

$('.nbrRangee').change(function () {
    var nbrRangee = $(this).val();
    var params = $(this).attr("id");
    window.location = params + "&nbrRangee=" + nbrRangee;
});

var dataHeaderExportProducteurs = $("#exportProducteurs").prev().text();
$("#exportProducteurs").attr(
    "href",
    "creationXml?objectxml=Producteurs&header=" + dataHeaderExportProducteurs);

var dataHeaderExportBouteilles = $("#exportBouteilles").prev().text();
$("#exportBouteilles").attr(
    "href",
    "creationXml?objectxml=Bouteilles&header=" + dataHeaderExportBouteilles);

			$(".tabpassive")
				.click(
					function () {

						var tabpassivevalue = $(this).attr(
							"data-name");
						var tabactivevalue = $(this).siblings(
							".tabactive").attr("data-name");
						$(this).attr("class", "tabactive");
						$(this).siblings(".tabactive").attr(
							"class", "tabpassive");
						$("div#" + tabpassivevalue).show();
						$("div#" + tabactivevalue).hide();
					});

			$(".tabactive").click(
				function () {
					var tabactivevalue = $(this).attr("data-name");
					var tabpassivevalue = $(this).siblings().attr(
						"data-name");
					$(this).attr("class", "tabactive");
					$(this).siblings().attr("class", "tabpassive");
					$("div#" + tabactivevalue).show();
					$("div#" + tabpassivevalue).hide();
				});

			/*
			 * transmition data bouteilleid et placeid pour les
			 * formulaires et animation place dans la cave pendant le
			 * chois de bouteille
			 */

			$(".afichecontenu")
				.click(
					function () {
						$(this).css({
							"border-width": "5"
						});
						$(this).parents('.detect').find('.referencePositionOfCheckboxes').prop("checked", true);

						$("#divmettrebouteilleform").show();
						$("#coverforformulaire").show();

                        var idSelectorAll = function() { return this.id; };
                        var refGrantedId = $("input[name=referencePositionOfCheckboxes]:checked").map(idSelectorAll).get();
                        var listIdPozChecked = [];
                        var listIdBouteilleChecked = [];
                        var listCaveAndPozRefChecked = [];

                        refGrantedId.forEach(setPozBouteille);
                        function setPozBouteille(item) {
                            listIdPozChecked.push(item.split(" ")[1]);
                            listCaveAndPozRefChecked.push(item.split(" ")[3] + item.split(" ")[4]);
                            var dataIdBouteilleChecked = item.split(" ")[5]
                            if (dataIdBouteilleChecked != 0) {
                            listIdBouteilleChecked.push(dataIdBouteilleChecked);
                            }
                        }
                        var data = $(this).attr("id").split(" ");
						var dataId = data[0];
						var dataRow = data[1];
						var dataCaveRef = data[2];
						var dataPozRef = data[3];

						if (!listIdPozChecked.includes(dataId)){
                        listIdPozChecked.push(dataId);
                        listCaveAndPozRefChecked.push(dataCaveRef + dataPozRef);
                        }

						var dataIdBouteille = data[4];
						if (dataIdBouteille != 0 || listIdBouteilleChecked.length > 0) {
							$("#sortirBouteille").show();
						} else if (dataIdBouteille == 0) {
							$("#sortirBouteille").hide();
						}

						var idBouteilleDansList = $("#ancre" +
							dataIdBouteille);
						var idPrevBouteilleDansList = idBouteilleDansList
							.prev().attr("id");
						if (idPrevBouteilleDansList != 'filterBouteille') {
							self.location.href = "#" +
								idPrevBouteilleDansList;
						}

						$("#ancre" + dataIdBouteille).css({
							"border-width": "5"
						});
						document.getElementById("afichageRefPoz").innerHTML = '';
						listCaveAndPozRefChecked.forEach(setCaveAndPozRef);
                        function setCaveAndPozRef(item) {
                            var node = document.createElement("li");
                            var textnode = document.createTextNode(item);
                            node.appendChild(textnode);

                            document.getElementById("afichageRefPoz").appendChild(node);
                        }

						$("#sortirBouteille").attr(
							"href",
							"ajouterBouteille?listIdPositions=" +
							listIdPozChecked + "&tab=" +
							dataRow + "&idPosition=" + dataId);

						$(".txtrefbouteillPourMettreDansCave")
							.click(
								function () {
									var dataBouteille = $(
										this).attr(
										"id");
									$(this)
										.attr(
											"href",
											"ajouterBouteille?listIdPositions=" +
											listIdPozChecked +
											"&tab=" +
											dataRow +
											"&idBouteille=" +
											dataBouteille + "&idPosition=" + dataId);
								});
						/*
						 * *************************creation
						 * bouteille depuis
						 * redacteur**************
						 */
						$("#linkajouterelementred").click(function () {
							$("#divdisapppourtriggerupdatesansrecharger").show();
							if ($('#divdisapppourtriggerupdatesansrecharger').is(':visible')) {
								$("input#nomP").attr('required', false);
							}
							$("#formCreationBouteille").attr("action", "creationBouteille?listIdPositions=" +
							listIdPozChecked +
							"&tab=" + dataRow + "&idPosition=" + dataId);


							$("#imageFromRed").change(

								function () {
									var dataIdRow = $("#formCreationBouteille").attr("action");
									var fileChooser = document.getElementById('imageFromRed');
									var results = document.getElementById('results');
									var acceptedFiles = ["image/jpg", "image/jpeg", "image/png", "image/gif"];

									$("#divchargeringimgmask").show();
									$('#erreurImg').hide();
									$('#erreurTaille').hide();
									$('#erreurFormat').hide();

									var file = fileChooser.files[0];
									if (file) {
										var params = {
											Bucket: 'caveweb',
											Key: "photobouteille/" + (new Date).getTime() + '-' + file.name,
											ContentType: file.type,
											Body: file,
											ACL: 'public-read'
										};

										if (!acceptedFiles.includes(file.type)) {
											$('#erreurImg').show();
											$('#erreurFormat').show();

											$("#formCreationBouteille").attr("action", dataIdRow);
											document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg";
										} else {
											$('#erreurFormat').hide();
											if (file.size > 2 * 1024 * 1024) {
												$('#erreurImg').show();
												$('#erreurTaille').show();
												$("#formCreationBouteille").attr("action", dataIdRow);
												document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg";
											} else {
												$('#erreurImg').hide();
												$('#erreurTaille').hide();
												$('#erreurFormat').hide();
												$("#formCreationBouteille").attr("action", dataIdRow + "&image=" + params.Key);

amconf

												var s3 = new AWS.S3();

												s3.putObject(params, function (err, res) {

													if (err) {
														$("#results").show();
														results.innerHTML = ("Error uploading data: ", err);
													} else {
														document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/" + params.Key;
														$("#divchargeringimgmask").hide();
													}
												});
											}
										}
									} else {
										$("#results").show();
										results.innerHTML = 'Nothing to upload.';
									}
								});
						});
					});

			var aficherRangee = $("#aficherRangee").attr("id");
			if (aficherRangee != null) {
				$("#aficherRangee").parent(".rangee").prev().attr("id",
					"ancreRangee");
				self.location.href = "#ancreRangee";
			}

			/*
			 * function distanger_bouteille(animationContenu) {
			 * animationContenu.css({"border-color": "#c9f7b7",
			 * "border-width": "5px", " border-style": "double"}); }
			 */
			/*
			 * function animate_loop(animationContenu) {
			 * animationContenu.animate({ opacity : 0.4 }, 1000,
			 * function() { animationContenu.animate({ opacity : 1 },
			 * 1000) animate_loop(animationContenu); }); }
			 */

			/* animate_sortir_form($("#linksortirformmettrebouteille")); */
			/* animate_loop($("#aficher")); */

			/*
			 * -------------------------------description bouteille dans
			 * le redacteur cave----------------------------------------
			 */
			$(".detect").mouseenter(function () {
				$(this).find(".descrcotenubouteille").show();
			});
			$(".detect").mouseleave(function () {
				$(this).find(".descrcotenubouteille").hide();
			});

			/*
			 * -------------------------------example
			 * compartiment----------------------------------------
			 */

			$(".infobulle").mouseenter(function () {
				$(this).find("#example").show();
			});

			$(".infobulle").mouseleave(function () {
				$(this).find("#example").hide();
			});

			/*
			 * -------------------------------show plus
			 * info----------------------------------------
			 */
			$(".divimgexpand").click(
				function () {
					$(this).siblings(".sousblockoneitemclosed")
						.slideDown();
					$(this).siblings(".divblog").css({
						"height": "auto"
					});
					$(this).hide();
					$(this).siblings(".divimgreduce").show();
				});

			$(".divimgexpandsansgradient").click(
				function () {
					$(this).siblings(".sousblockoneitemclosed")
						.slideDown();
					$(this).siblings(".divblog").css({
						"height": "auto"
					});
					$(this).hide();
					$(this).siblings(".divimgreduce").show();
				});


			$(".divimgreduce").click(function () {
				$(this).siblings(".sousblockoneitemclosed").slideUp();
				$(this).siblings(".divblog").css({
					"height": "150px"
				});
				$(this).hide();
				$(this).siblings(".divimgexpand").show();
				$(this).siblings(".divimgexpandsansgradient").show();
			});

			$(".descriptmainnom").click(
				function () {
					$(this).parentsUntil("blockoneitem").siblings(
						".sousblockoneitemclosed").slideToggle(
						"slow");
				});

			/* -------------------------------Delete---------------------------------------- */
             $(".imgajouterinfopubdel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					//var data = $(this).attr("id").split(" ");
					var dataIdPub = $(this).attr("data-id");
					var dataImage = $(this).attr("data-img");
					$("#formDel").attr(
						"action",
						"suppressionPub?idPub=" +
						dataIdPub + "&imagePub=" + dataImage);
					$("#coverforformulaire").show();
			});

			$(".imgajouterinfoblogdel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					var data = $(this).attr("id").split(" ");
					var dataIdBlog = data[0];
					var dataNomBlog = data[1];
					$("#formDel").attr(
						"action",
						"suppressionBlog?idBlog=" +
						dataIdBlog +
						"&nomBlog=" +
						dataNomBlog);
					$("#coverforformulaire").show();
				});

			$(".imgajouterinfouserdel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					var data = $(this).attr("id").split(" ");
					var dataIdUser = data[0];
					var dataNomUser = data[1];
					$("#formDel").attr(
						"action",
						"suppressionUser?idUser=" +
						dataIdUser +
						"&nomUser=" +
						dataNomUser);
					$("#coverforformulaire").show();
				});

			$(".imgajouterinfoerreurdel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					var data = $(this).attr("id").split(" ");
					var dataIdErreur = data[0];
					$("#formDel").attr(
						"action",
						"suppressionErreur?idErreur=" +
						dataIdErreur);
					$("#coverforformulaire").show();
				});

			$(".imgajouterinfoproducteurdel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					var data = $(this).attr("id").split(" ");
					var dataIdProd = data[0];
					var dataNomProd = data[1];
					$("#formDel").attr(
						"action",
						"suppressionProducteur?idProducteur=" +
						dataIdProd +
						"&nomProducteur=" +
						dataNomProd);
					$("#coverforformulaire").show();
				});

			$(".imgajouterinfocavedel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					var data = $(this).attr("id").split(" ");
					var dataIdCave = data[0];
					var dataNomCave = data[1];
					$("#formDel").attr(
						"action",
						"suppressionCave?idCave=" + dataIdCave +
						"&nomCave=" + dataNomCave);
					$("#coverforformulaire").show();
				});

			$(".imgajouterinfobouteilledel").click(
				function () {
					$("#divdisapppourtriggerdel").show();
					//var data = $(this).attr("id").split(" ");
					var dataIdBouteille = $(this).attr("data-id");
					var dataNomBouteille = $(this).attr("data-nom");
					var dataImage = $(this).attr("data-img");
					$("#formDel").attr(
						"action",
						"suppressionBouteille?idBouteille=" +
						dataIdBouteille +
						"&nomBouteille=" +
						dataNomBouteille +
						" &imageBouteille=" +
						dataImage);
					$("#coverforformulaire").show();
				});

			/*
			 * -------------------------------Changer quantité dans la
			 * liste de courses----------------------------------------
			 */

			$(".imgajouterinfobouteilleshoplist").click(
				function () {
					$("#divdisapppourtriggeraddshoplist").show();
					var data = $(this).attr("id").split(" ");
					var dataIdBouteille = data[0];
					var dataNomBouteille = data[1];
					var nbrlc = $(this).attr('data-nbr');
					$("#quantiteAcheter").val(nbrlc);
					$("#nombouteillepourquantiteacheter").text(
						dataNomBouteille);
					$("#formLC").attr(
						"action",
						"ajouterDansLC?idBouteille=" +
						dataIdBouteille +
						"&nomBouteille=" +
						dataNomBouteille);
					$("#coverforformulaire").show();
				});

			$(".imgajouterinfobouteilleshoplistfromlc").click(
				function () {
					$("#divdisapppourtriggeraddshoplist").show();
					var data = $(this).attr("id").split(" ");
					var dataIdBouteille = data[0];
					var dataNomBouteille = data[1];
					var nbrlc = $(this).attr('data-nbr');
					$("#quantiteAcheter").val(nbrlc);
					$("#nombouteillepourquantiteacheter").text(
						dataNomBouteille);
					$("#formLC").attr(
						"action",
						"ajouterDansLC?idBouteille=" +
						dataIdBouteille +
						"&nomBouteille=" +
						dataNomBouteille +
						"&isShopList=isShopList");
					$("#coverforformulaire").show();
				});

			/*
			 * -------------------------------Changer raiting
			 * ----------------------------------------
			 */

			$(".imgraiting").click(
				function () {
					$("#divdisapppourtriggerevaluation").show();
					var data = $(this).attr("id").split(" ");
					var dataIdBouteille = data[0];
					var dataNomBouteille = data[1];
					var eval = $(this).attr("alt");
					$("#evaluation").val(eval);
					$("#nombouteillepourevaluation").text(
						dataNomBouteille);
					$("#formEvaluation").attr(
						"action",
						"ajouterEvaluation?idBouteille=" +
						dataIdBouteille +
						"&nomBouteille=" +
						dataNomBouteille);
					$("#coverforformulaire").show();
				});
			/*
			 * -------------------------------Changer
			 * Commentaire----------------------------------------
			 */

			$(".imgajouterinfobouteilleaddcomment").click(
				function () {
					$("#divdisapppourtriggeraddcomment").show();
					var data = $(this).attr("id").split(" ");
					var dataIdBouteille = data[0];
					var dataNomBouteille = data[1];
					$("#nombouteillepourcomment").text(
						dataNomBouteille);
					var commentaire = $(this).attr('data-comment');
					$("#commentaire").text(commentaire);
					$("#formLCamment").attr(
						"action",
						"ajouterCommentaire?idBouteille=" +
						dataIdBouteille +
						"&nomBouteille=" +
						dataNomBouteille);
					$("#coverforformulaire").show();
				});

			/* -------------------------------Creation---------------------------------------- */
			$("#linkajouterelement").click(function () {
				$("#divdisapppourtriggerupdatesansrecharger").show();
				$("#coverforformulaire").show();
				if ($('#divdisapppourtriggerupdatesansrecharger').is(':visible')) {
					$("input#nomP").attr('required', false);
				}
			});
			if ($('#divdisapppourtriggerupdatesansrecharger').is(':visible')) {
				$("input#nomP").attr('required', false);
			}

			/*
			 * -------------------------------Creation
			 * producteur----------------------------------------
			 */
			$("#linkajouterelementprod").click(
				function () {
					$("#divdisapppourtriggerupdatesansrecharger").show();
					$("#coverforformulaire").show();
					$("input#nomP").attr('required', true);
				});
			if ($('#divdisapppourtriggerupdateprod').is(':visible')) {
				$("input#nomP").attr('required', true);
			}

			if ($('#divdisapppourtriggerupdatebouteille').is(':visible')) {
				$("input#nomP").attr('required', false);
			}

			/*            if ($('#divdisapppourtriggerupdateuser').is(':visible')) {
			                $("input#nomP").attr('required', true);
			            }*/
			/*
			 * -------------------------------sortir form sans
			 * rechargement----------------------------------------
			 */
			/*		if ($('#divdisapppourtriggerdel').is(':visible')) {
						 var fornulairecover = "<div class='coverforformulaire'></div>";
						$("body").append(fornulairecover);
					}*/


			$(".sortirformsansrechargement").click(
				function () {
					$("#divdisapppourtriggerdel").hide();
					$("#divdisapppourtriggeraddcomment").hide();
					$("#divdisapppourtriggeraddshoplist").hide();
					$("#divdisapppourtriggerevaluation").hide();
					$("#divdisapppourtriggerupdatesansrecharger").hide();
					$("#coverforformulaire").hide();
					if ($('#divmettrebouteilleform').is(':visible')) {
						$("#coverforformulaire").show();
					}
				});

			$("#sortirformsansrechargementmettrebouteille").click(
				function () {
				$('.referencePositionOfCheckboxes').prop("checked", false);
					$("#divmettrebouteilleform").hide();
					$("#coverforformulaire").hide();
					$(".afichecontenu").css({
						"border-width": "0"
					});
					$(".ciblebouteille").css({
						"border-width": "0"
					});
				});

			var modalprodavecrechargement = document.getElementById("divdisapppourtriggerupdateprod");
			var modalcaveavecrechargement = document.getElementById("divdisapppourtriggerupdatecave");
			var modalbouteilleavecrechargement = document.getElementById("divdisapppourtriggerupdatebouteille");
			var modaluseravecrechargement = document.getElementById("divdisapppourtriggerupdateuser");
			var modalpubavecrechargement = document.getElementById("divdisapppourtriggerupdatepub");

			var modal = document.getElementById("divdisapppourtriggerupdatesansrecharger");
			var modalmettrebouteille = document.getElementById("divmettrebouteilleform");
			var modaldel = document.getElementById("divdisapppourtriggerdel");
			var modalcomment = document.getElementById("divdisapppourtriggeraddcomment");
			var modalsl = document.getElementById("divdisapppourtriggeraddshoplist");
			var modalevaluation = document.getElementById("divdisapppourtriggerevaluation");

			window.onclick = function (event) {

				if (event.target == modal) {
					modal.style.display = "none";

					$("#coverforformulaire").hide();

				}
				if (event.target == modalcomment) {
					modalcomment.style.display = "none";
					$("#coverforformulaire").hide();
				}
				if ($('#divmettrebouteilleform').is(':visible')) {
					$("#coverforformulaire").show();
				}
				if (event.target == modalevaluation) {
					modalevaluation.style.display = "none";
					$("#coverforformulaire").hide();
				}
				if (event.target == modalsl) {
					modalsl.style.display = "none";
					$("#coverforformulaire").hide();
				}
				if (event.target == modaldel) {
					modaldel.style.display = "none";
					$("#coverforformulaire").hide();
				}
				if (event.target == modalmettrebouteille) {
					modalmettrebouteille.style.display = "none";
					$("#coverforformulaire").hide();
					$(".afichecontenu").css("border-width", "0");
					$('.referencePositionOfCheckboxes').prop("checked", false);
					$(".ciblebouteille").css({
						"border-width": "0"
					});
				}

				if (event.target == modalprodavecrechargement) {
					location.replace("listeProducteurs");
				}
				if (event.target == modalcaveavecrechargement) {
					location.replace("listeCaves");
				}
				if (event.target == modalbouteilleavecrechargement) {
					location.replace("listeBouteilles");
				}
				if (event.target == modaluseravecrechargement) {
					location.replace("listeUsers");
				}
				if (event.target == modalpubavecrechargement) {
                    location.replace("managerAD");
                }

			}

			/*
			 * ****************************Mote de passe
			 * oublié**********************************
			 */
			$(".tabmdpoblie").click(function () {
				$(".divdisapppourtriggerevaluation").show();
			});

			/*
			 * **********************filter de list des
			 * bouteilles************************************************
			 */

			$("#filterBouteille")
				.keyup(
					function () {
						var value = $(this).val().toLowerCase();
						$(".ciblebouteille")
							.filter(
								function () {
									$(this)
										.toggle(
											$(
												this)
											.text()
											.toLowerCase()
											.indexOf(
												value) > -1)
								});
					});

		});
/*
 * **********************filter bouteille pour afichage combien anee de la
 * consomation ************************************************
 */

$('#filterBouteilleConsomer').change(function () {
	var value = $(this).val();
	window.location = "listeBouteillesAConsomer?maxAnee=" + value;

});
$('#tribouteilleLC').change(function () {
	var value = $(this).val();
	window.location = "listeBouteillesAConsomer?triBouteillesConsomer=" + value;
});

$('#tribouteilleAffiche').change(function () {
	var value = $(this).val();
	window.location = "listeBouteilles?triBouteillesAfficher=" + value;
});

$('#commonObjectsBouteille').change(function () {
	var value = $(this).val() === 'true' ? false : true;
	window.location = "listeBouteilles?commonObjectsLoaderBouteille=" + value;
});
$('#myObjectsBouteille').change(function () {
	var value = $(this).val() === 'true' ? false : true;
	window.location = "listeBouteilles?myObjectsLoaderBouteille=" + value;
});

$('#commonObjectsProducteur').change(function () {
	var value = $(this).val() === 'true' ? false : true;
	window.location = "listeProducteurs?commonObjectsLoaderProducteur=" + value;
});
$('#myObjectsProducteur').change(function () {
	var value = $(this).val() === 'true' ? false : true;
	window.location = "listeProducteurs?myObjectsLoaderProducteur=" + value;
});


/* ---------------FORMULAIRE CREATION BOUTEILLE----------------- */
/*
 * **********************Listjson*****************************************
 */

ajaxGet(
	"js/provenance.json",
	function (reponse) {
		// Transforme la réponse en un tableau
		// d'articles
		var obj, i, j, k, listItems = "",
			listItemsRegions = "",
			listItemsAppelation = "";
		obj = JSON.parse(reponse);

		/*
		 * listItems = '<option value="">-Select-</option>';
		 * listItemsRegions = '<option value="" >-Select-</option>';
		 * listItemsAppelation = '<option value="">-Select-</option>';
		 */
		for (i in obj.pays) {
			listItems += "<div  class='divchoice'>" + obj.pays[i].label +
				"</div>";
			for (j in obj.pays[i].region) {
				listItemsRegions += "<div  class='divchoice' data-name='" +
					obj.pays[i].label + "'>" +
					obj.pays[i].region[j].label + "</div>";

				for (k in obj.pays[i].region[j].appelation) {
					listItemsAppelation += "<div  class='divchoice' data-name='" +
						obj.pays[i].region[j].label +
						"'>" +
						obj.pays[i].region[j].appelation[k].label +
						"</div>";

				}
			}
		}
		$("#payschoice").html(listItems);
		$("#regionchoice").html(listItemsRegions);
		$("#appelationchoice").html(listItemsAppelation);

		$(".divchoice")
			.click(
				function () {
					var value = $(this).text().toLowerCase();
					var valueinput = $(this).text();
					$(this).parent(".divinputchoice").prev(
						".inputformbouteille").val(valueinput);
					$(this).parent(".divinputchoice").hide();

					if ($(this).parent(".divinputchoice")
						.attr("id").toLowerCase().indexOf(
							"payschoice") > -1) {

						$("#regionchoice")
							.children(".divchoice")
							.filter(
								function () {
									var choisevalue = $(
											this).attr(
											"data-name")
										.toLowerCase();
									$(this)
										.toggle(
											choisevalue
											.indexOf(value) > -1)
								});

					} else if ($(this).parent(".divinputchoice")
						.attr("id").toLowerCase().indexOf(
							"regionchoice") > -1) {

						$("#appelationchoice")
							.children(".divchoice")
							.filter(
								function () {
									var choisevalue = $(
											this).attr(
											"data-name")
										.toLowerCase();
									$(this)
										.toggle(
											choisevalue
											.indexOf(value) > -1)
								});
					}

				});

	});

$('input[name=choixNouveauProducteur]:radio').click(function () {
	$("div#nouveauProducteur").hide();
	$("div#ancienProducteur").hide();
	var divId = jQuery(this).val();
	$("div#" + divId).show();

	if (divId == 'ancienProducteur') {
		$("input#nomP").attr('required', false);
	} else {
		$("input#nomP").attr("required", "required");
	}
});
/* idem avec choix ajouter producteur */
$('input[name=choixAjouterProducteur]:radio').click(function () {
	$("div#ajouterProducteur").hide();
	var divIdA = jQuery(this).val();
	$("div#" + divIdA).show();
	if (divIdA == 'nonAjouterProducteur') {
		$("input#nomP").attr('required', false);
	} else {
		$("input#nomP").attr("required", "required");
	}

});

$("#image").change(
	function () {
		var fileChooser = document.getElementById('image');
		var results = document.getElementById('results');
		var idBouteille = document.getElementById('idBouteille').innerText;
		var idUser = document.getElementById('idUser').innerText;
		var acceptedFiles = ["image/jpg", "image/jpeg", "image/png", "image/gif"];
		$("#divchargeringimgmask").show();
		$('#erreurImg').hide();
		$('#erreurTaille').hide();
		$('#erreurFormat').hide();

		var file = fileChooser.files[0];
		if (file) {
			var params = {
				Bucket: 'caveweb',
				Key: "photobouteille/" + idUser+'_'+ (new Date).getTime(),
				ContentType: file.type,
				Body: file,
				ACL: 'public-read'
			};

			if (!acceptedFiles.includes(file.type)) {
				$('#erreurImg').show();
				$('#erreurFormat').show();
				$("#formCreation").attr("action", "creationBouteille?idBouteille=" + idBouteille);
				document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg";
			} else {
				$('#erreurFormat').hide();
				if (file.size > 2 * 1024 * 1024) {
					$('#erreurImg').show();
					$('#erreurTaille').show();
					$("#formCreation").attr("action", "creationBouteille?idBouteille=" + idBouteille);
					document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg";
				} else {
					$('#erreurImg').hide();
					$('#erreurTaille').hide();
					$('#erreurFormat').hide();
					$("#formCreation").attr("action", "creationBouteille?image=" + params.Key + "&idBouteille=" + idBouteille);

amconf

					var s3 = new AWS.S3();

					s3.putObject(params, function (err, res) {

						if (err) {
							$("#results").show();
							results.innerHTML = ("Error uploading data: ", err);
						} else {
							document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/" + params.Key;
							$("#divchargeringimgmask").hide();
						}
					});
				}
			}
		} else {
			$("#results").show();
			results.innerHTML = 'Nothing to upload.';
		}
	});


$("#imageBlog").change(
	function () {
		var fileChooser = document.getElementById('imageBlog');
		var results = document.getElementById('results');
		var idBlog = document.getElementById('idBlog').innerText;
		var acceptedFiles = ["image/jpg", "image/jpeg", "image/png", "image/gif"];
		$("#divchargeringimgmask").show();
		$('#erreurImg').hide();
		$('#erreurTaille').hide();
		$('#erreurFormat').hide();

		var file = fileChooser.files[0];
		if (file) {
			var params = {
				Bucket: 'caveweb',
				Key: "photoblog/" + (new Date).getTime() + '-' + file.name,
				ContentType: file.type,
				Body: file,
				ACL: 'public-read'
			};

			if (!acceptedFiles.includes(file.type)) {
				$('#erreurImg').show();
				$('#erreurFormat').show();
				$("#formCreation").attr("action", "creationBlog?idBlog=" + idBlog);
				document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg";
			} else {
				$('#erreurFormat').hide();
				if (file.size > 2 * 1024 * 1024) {
					$('#erreurImg').show();
					$('#erreurTaille').show();
					$("#formCreation").attr("action", "creationBlog?idBlog=" + idBlog);
					document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultetiquette.jpg";
				} else {
					$('#erreurImg').hide();
					$('#erreurTaille').hide();
					$('#erreurFormat').hide();
					$("#formCreation").attr("action", "creationBlog?image=" + params.Key + "&idBlog=" + idBlog);

amconf

					var s3 = new AWS.S3();

					s3.putObject(params, function (err, res) {

						if (err) {
							$("#results").show();
							results.innerHTML = ("Error uploading data: ", err);
						} else {
							document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/" + params.Key;
							$("#divchargeringimgmask").hide();
						}
					});
				}
			}
		} else {
			$("#results").show();
			results.innerHTML = 'Nothing to upload.';
		}
	});

function verticalNoTitleAnddiscription() {

	var loading = new Loading({
		animationOriginColor: 'rgb(217, 83, 79)',
		defaultApply: true,
	});

	loadingOut(loading);
}


$("#butchargerimg").click(function () {
	$("#ciblechargerimg").show();
	$("#txtchampsobligatoires").show();
	$("#results").hide();
});

$(".inputformbouteille").focus(function () {
	$(this).next(".divinputchoice").show();
});

$(".inputformbouteille").blur(function () {
	$(this).next(".divinputchoice").animate({
		height: 'toggle'
	});
});

$(".inputformbouteille").on("keyup", function () {
	var value = $(this).val().toLowerCase();
	$(this).next(".divinputchoice").children().filter(function () {
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
});

$('#isAllowedCLProd').change(function() {
    var switchEl = $('#isAllowedCLProd');
    manageSwichCL(switchEl);
});

$('#isAllowedCLBut').change(function() {
    var switchEl = $('#isAllowedCLBut');
    manageSwichCL(switchEl);
});

$('#isAllowedAD').change(function() {
    var switchEl = $('#isAllowedAD');
    manageSwichCL(switchEl);
});

function manageSwichCL(switchEl) {
    var dataIncl = switchEl.attr("data-incl");
    if(dataIncl!="true"){
      switchEl.prop('checked', false);
      return;
    }
}


$(".sortirpubsansrechargement").click(
    function () {
        $(".coverforpub").hide();
        $(".divdisapppourtriggerupdate").hide();
        $(".pub-inlist").show();
    });



/*
 * 1 - Au lancement de la page, on cache le bloc d'éléments du formulaire
 * correspondant aux producteurs existants
 */
/* $("div#ancienProducteur").hide(); */
/*
 * 2 - Au clic sur un des deux boutons radio "choixNouveauProducteur", on
 * affiche le bloc d'éléments correspondant (nouveau ou ancien PRODUCTEUR)
 */

// Exécute un appel AJAX GET
// Prend en paramètres l'URL cible et la fonction callback appelée en cas de
// succès
function ajaxGet(url, callback) {
	var req = new XMLHttpRequest();
	req.open("GET", url);
	req.addEventListener("load", function () {
		if (req.status >= 200 && req.status < 400) {
			// Appelle la fonction callback en lui passant la réponse de la
			// requête
			callback(req.responseText);
		} else {
			console.error(req.status + " " + req.statusText + " " + url);
		}
	});
	req.addEventListener("error", function () {
		console.error("Erreur réseau avec l'URL " + url);
	});
	req.send(null);
}