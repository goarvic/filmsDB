//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************
function showAudioTracks()
{
    $('.formularyPart').hide()
    $('#filmDetailsAudio').show()
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function showFilmDetails()
{
    $('.formularyPart').hide()
    $('#filmDetailsFA').show()
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function showSubtitleTracks()
{
    $('.formularyPart').hide()
    $('#filmDetailsSubtitles').show()
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function showVideoDetails()
{
    $('.formularyPart').hide()
    $('#filmDetailsVideo').show()
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function showTrailerDetails()
{
    $('.formularyPart').hide()
    $('#filmDetailsTrailer').show()
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function buttonFormulary(idOfButton)
{
    $('.buttonPartFormulary').removeClass("btn-primary")
    $('.buttonPartFormulary').addClass("btn-default")
    $('#' + idOfButton).addClass("btn-primary")

    if (idOfButton == "buttonFilmInfo")
    {
        showFilmDetails()
    }
    else if (idOfButton == "buttonTrailer")
    {
        showTrailerDetails()
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