//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

var urlSearchMovies
var urlChangeSortMovies
var urlApplyFilterMovies
var urlRemoveMovies
var urlViewMovies
var urlTopDirector
var urlTopActor
var urlTopGenre

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

$(document).ready(
	function()
	{
		setButtonSearchProperties()
		getTopDirector()
		getTopActor()
		getTopGenre()
	}
);

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

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function getTopDirector()
{
	$.ajax({
		type: 'GET',
		url: urlTopDirector,
		success:
			function(response)
			{
				$('#loadingTopDirector').hide();
				if (response == "null")
					$('#topDirector').append("-")
				else
					$('#topDirector').append(response)
				$('#topDirector').show()
			}
	})
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function getTopActor()
{
	$.ajax({
		type: 'GET',
		url: urlTopActor,
		success:
			function(response)
			{
				$('#loadingTopActor').hide();
				if (response == "null")
					$('#topActor').append("-")
				else
					$('#topActor').append(response)
				$('#topActor').show()
			}
	})
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function getTopGenre()
{
	$.ajax({
		type: 'GET',
		url: urlTopGenre,
		success:
			function(response)
			{
				$('#loadingTopGenre').hide();
				if (response == "null")
					$('#topGenre').append("-")
				else
					$('#topGenre').append(response)
				$('#topGenre').show()
			}
	})
}