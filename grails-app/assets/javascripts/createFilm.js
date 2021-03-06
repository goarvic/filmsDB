//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


var controllerAvailableSpaceUrl
var size
var languagesObj


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


$(document)
	.on('change', '.btn-file :file', function() {
		var input = $(this),
			numFiles = input.get(0).files ? input.get(0).files.length : 1,
			label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
		input.trigger('fileselect', [numFiles, label]);
	});

$(document).ready( function() {
	$('.btn-file :file').on('fileselect', function(event, numFiles, label) {

		var input = $(this).parents('.input-group').find(':text'),
			log = numFiles > 1 ? numFiles + ' files selected' : label;

		if( input.length ) {
			input.val(log);
		} else {
			if( log ) alert(log);
		}

	});
});


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************
function isAvailableSpaceOnDisk(discReference, size)
{
	var requestObj = {
		discReference: parseInt(discReference),
		size: parseInt(size)
	};

	$.ajax({
		type: 'POST',
		url: controllerAvailableSpaceUrl,
		dataType: 'json',
		contentType: "application/json",
		data: JSON.stringify(requestObj),
		success:
			function(response)
			{
				if (response.enoughSpace == true)
					$('#submitFilmFormulary').removeAttr("disabled")
				else
					$('#submitFilmFormulary').attr("disabled", "true")
			}
	})
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


function loadPreviewImageIfPossible(input)
{

	if (input.files && input.files[0])
	{
		var contentOfSelectPoster =  $(".btn-file :file").val()
		if (contentOfSelectPoster.indexOf(".jpg") < 0)
		{
			console.log("No se carga la imagen de preview por tener extension anomala")
			return null
		}


		if ((input.files[0].size/1000) > 1024)
		{
			console.log("No se carga la imagen de preview por superar 1MB")
			return null
		}


		var reader = new FileReader();
		reader.onload = function (e) {
			$('#posterLocal').attr('src', e.target.result);
			$('#posterFA').hide()
			$('#posterLocal').show()
		}
		reader.readAsDataURL(input.files[0]);
	}
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


function enableSubmitButtonIfMatchConditions()
{
	var allConditionsOk = true

	//allConditionsOk = enoughSpace

	$(".trackSelectLanguage option:selected").each(function(){
		if (this.value == "Unknown")
			allConditionsOk = false
	});

	var contentOfSelectPoster =  $(".btn-file :file").val()

	if (contentOfSelectPoster.indexOf(".jpg") < 0)
		allConditionsOk = false
	else
	{
		var posterLocalInputFile = document.getElementById("posterFileInput")
		if (!(posterLocalInputFile.files && posterLocalInputFile.files[0])
			|| ((posterLocalInputFile.files[0].size/1000) > 1024))
		{
			allConditionsOk = false
			console.log("No se habilita el botón de submit por no cumplir el fichero de poster las condiciones")
			console.log(posterLocalInputFile.files[0].size)
		}

	}

	if (($("#fileName").val() == "") || ($("#fileName").val() == null))
		allConditionsOk = false

	if (!(parseInt("0" + $("#discReference").val(), 10) > 0))
		allConditionsOk = false


	if (allConditionsOk)
		isAvailableSpaceOnDisk($("#discReference").val(), size)
	else
		$('#submitFilmFormulary').attr("disabled", "true")
}
