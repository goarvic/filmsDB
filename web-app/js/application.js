//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************
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

function buttonFormulary(idOfButton)
{
	var buttonClicked = document.getElementById("idOfButton")
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
	else
	{
		showSubtitleTracks()
	}
}