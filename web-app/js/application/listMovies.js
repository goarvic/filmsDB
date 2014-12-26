//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

var searchWord
var urlSearchMovies
var urlChangeSortMovies
var urlApplyFilterMovies
var urlRemoveMovies
var urlViewMovies

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


$(document).ready(
	function()
	{
		hideControlsAttendingToPath()
		setSearchInputText()
		setButtonSearchProperties()
	}
);


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function setSearchInputText()
{
	if ((searchWord != "") && (searchWord != null))
	{
		$('#searchMovies').val(searchWord)
	}
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function hideControlsAttendingToPath()
{
	var pathname = window.location.pathname;

	if (pathname.indexOf("search")>0)
	{
		$('#sortDiv').hide()
		$('#filterDiv').hide()
	}
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


function setButtonSearchProperties()
{
	if ($('#searchMovies').val().length < 3)
		$('#buttonSearch').attr("disabled", true)
	else
		$('#buttonSearch').removeAttr("disabled")


	$('#searchMovies').keyup(function(e){
		if ($('#searchMovies').val().length < 3)
			$('#buttonSearch').attr("disabled", true)
		else
			$('#buttonSearch').removeAttr("disabled")

		if(e.which == 13)
		{
			searchMovie(urlSearchMovies)
		}
	});
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


$(document).on( 'click', '#buttonSearch',
	function()
	{
		searchMovie(urlSearchMovies)
	}
);

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

$(document).on( 'change', '#sortMovies',
	function()
	{
		var url = urlChangeSortMovies
		var order
		$('#sortMovies option:selected').each(function() {
			order = $( this ).val()
		})
		url+="?order=" + order
		window.location.href=url
	}
);



//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

$(document).on( 'change', '#filterMovies',
	function()
	{
		var url = urlApplyFilterMovies
		var filter
		$('#filterMovies option:selected').each(function() {
			filter = $( this ).val()
		})
		url+="?filterGenre=" + filter
		window.location.href=url
	}
);


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************


function searchMovie(url)
{
	var search = $('#searchMovies').val()
	var urlWithParams = url + "?search=" + search

	if (search.length > 2)
		window.location.href = urlWithParams
}

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

$(document).on( 'click', '.btn-remove-film',
	function()
	{
		if (confirm("Are you sure you want delete this film?"))
		{
			$('#overlay').show();
			var elementId = $(this).attr("id")
			var savedFilmIdString = elementId.substr(elementId.indexOf("_") + 1, elementId.length)
			var savedFilmId = parseInt(savedFilmIdString)
			removeFilm(savedFilmId)
		}
	}
);

function removeFilm(idFilm)
{
	var requestObj = {
		savedFilmId: idFilm
	};

	$.ajax({
		type: 'POST',
		url: urlRemoveMovies,
		data: requestObj,
		success:
			function(response)
			{
				$('#overlay').hide();
				window.location.href = urlViewMovies;
			}
	})
}