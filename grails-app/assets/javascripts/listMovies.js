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
var urlGoPage

var pagesSize

//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

$(document).ready(
	function()
	{
		setButtonSearchProperties()
		getTopGenre()
		getTopDirector()
		getTopActor()
		setPaginateTabProperties()
	}
);


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function updateQueryStringParameter(uri, key, value) {
	var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	var separator = uri.indexOf('?') !== -1 ? "&" : "?";
	if (uri.match(re)) {
		return uri.replace(re, '$1' + key + "=" + value + '$2');
	}
	else {
		return uri + separator + key + "=" + value;
	}
}


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function setPaginateTabProperties()
{

	$('.goPage').keyup(function(e){
		if(e.which == 13)
		{
			var page = $(this).val()
			if (!isNaN(parseInt(page)) && (parseInt(page) <= pagesSize) && parseInt(page)>0 )
			{
				url = updateQueryStringParameter(urlGoPage, "page", page);
				window.location.href = url;
			}
		}
	});
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
		var sortBy
		$('#sortMovies option:selected').each(function() {
			sortBy = $( this ).val()
		})
		url = updateQueryStringParameter(urlGoPage, "sortBy", sortBy);
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
		url = updateQueryStringParameter(urlGoPage, "filterGenre", filter);
		url = updateQueryStringParameter(url, "page", 1);
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