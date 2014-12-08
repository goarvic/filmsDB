//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


var controllerAvailableSpaceUrl
var size
var languagesObj


function showAudioTracks()
{
	$('.formularyPart').hide()
	$('#filmDetailsAudio').show()
}


function showFilmDetails()
{
	$('.formularyPart').hide()
	$('#filmDetailsFA').show()
}


function showSubtitleTracks()
{
	$('.formularyPart').hide()
	$('#filmDetailsSubtitles').show()
}

function showVideoDetails()
{
	$('.formularyPart').hide()
	$('#filmDetailsVideo').show()
}


function buttonFormulary(idOfButton)
{
	$('.buttonPartFormulary').removeClass("btn-primary")
	$('.buttonPartFormulary').addClass("btn-default")
	$('#' + idOfButton).addClass("btn-primary")

	if (idOfButton == "buttonFilmInfo")
	{
		showFilmDetails()
	}
	else if (idOfButton == "buttonAudioTracks")
	{
		showAudioTracks()
	}
	else if (idOfButton == "buttonSubtitleTracks")
	{
		showSubtitleTracks()
	}
	else
	{
		showVideoDetails()
	}
}



/*function changeLanguageAttrs(idOfSelect)
{
	var selectedElement = document.getElementById(idOfSelect)
	var languageIdAssociated = document.getElementById(idOfSelect.substr(0 , idOfSelect.length-4) + "id")

	for (var i=0; i<languagesObj.length;i++)
	{
		if (languagesObj[i].code == selectedElement.value)
		{
			languageIdAssociated.value = languagesObj[i].id
		}
		break;
	}

}*/


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


	if (!(parseInt("0" + $("#discReference").val(), 10) > 0))
		allConditionsOk = false


	if (allConditionsOk)
		isAvailableSpaceOnDisk($("#discReference").val(), size)
	else
		$('#submitFilmFormulary').attr("disabled", "true")
}
