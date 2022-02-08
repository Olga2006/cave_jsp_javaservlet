$("#imagePub").change(
    function () {
        var fileChooser = document.getElementById('imagePub');
        var results = document.getElementById('results');
        var idPub = document.getElementById('idPub').innerText;
        var idUser = document.getElementById('idUser').innerText;
        var acceptedFiles = ["image/jpg", "image/jpeg", "image/png", "image/gif"];
        $("#divchargeringimgmask").show();
        $('#erreurImg').hide();
        $('#erreurTaille').hide();
        $('#erreurFormat').hide();
        $("#requiredimg").hide();

        var file = fileChooser.files[0];
        if (file) {
            var params = {
                Bucket: 'caveweb',
                Key: "pub/" + idUser+'_'+ (new Date).getTime(),
                ContentType: file.type,
                Body: file,
                ACL: 'public-read'
            };

            if (!acceptedFiles.includes(file.type)) {
                $('#erreurImg').show();
                $('#erreurFormat').show();
                document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultepubphoto.png";
            } else {
                $('#erreurFormat').hide();
                $('#erreurImg').hide();
                if (file.size > 2 * 1024 * 1024) {
                    $('#erreurImg').show();
                    $('#erreurTaille').show();
                    document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultepubphoto.png";
                } else {
                    $('#erreurImg').hide();
                    $('#erreurTaille').hide();
                    $('#erreurFormat').hide();

amconf

                    var s3 = new AWS.S3();

                    s3.putObject(params, function (err, res) {

                        if (err) {
                            $("#results").show();
                            results.innerHTML = ("Error uploading data: ", err);
                        } else {
                            document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/" + params.Key;
                            $("#divchargeringimgmask").hide();
                            $("#formCreationPub").attr("data-img", params.Key)
                        }
                    });
                }
            }
        } else {
            $("#results").show();
            results.innerHTML = 'Nothing to upload.';
        }
    });

$("#formCreationPub").on("submit", function (e) {
    var fileChooser = document.getElementById('imagePub');
    var file = fileChooser.files[0];
    var dataImg = $("#formCreationPub").attr("data-img");
    var dataId = $("#formCreationPub").attr("data-id");
    if (!dataImg) {
        e.preventDefault(e);
        $("#formCreation").attr("action", "");
        $("#requiredimg").show();
    } else {
        $("#formCreationPub").attr("action", "creationPub?image=" + dataImg + "&idPub=" + dataId);
        $("#formCreationPub").submit();
    }
})

$("#formCreationPub").on("reset", function (e) {
    e.preventDefault(e);
    $("#imagePub").val(null);
    document.getElementById('imgetiquette').src = "https://caveweb.s3.eu-west-3.amazonaws.com/photosite/defaultepubphoto.png";
    $("#url").val(null);
    $("#isFirst").prop('checked', false);
    $("#isSecond").prop('checked', false);
})

$('#isFirst').change(function () {
    var switchEl = $('#isFirst');
    manageSwichPub(switchEl);
});

$('#isSecond').change(function () {
    var switchEl = $('#isSecond');
    manageSwichPub(switchEl);
});

function manageSwichPub(switchEl) {
    var dataInad = switchEl.attr("data-inad");
    var dataAdFree = switchEl.attr("data-adfree");
    var isChecked = switchEl.attr("data-ischecked");
    if (dataInad != "true") {
        switchEl.prop('checked', false);
        return;
    }
    if (isChecked != "true" && dataAdFree != "true") {
        switchEl.prop('checked', false);
    }
}

/*$("#codeHtml").on('change keyup paste', function() {
var value = $(this).val();
$("#divhtmlprev").empty();
$("#divhtmlprev").append(value);
});*/