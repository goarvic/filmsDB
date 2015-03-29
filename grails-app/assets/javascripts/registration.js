//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

var urlValidateUsername


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

$(document).ready
(

	function ()
	{
		console.log (urlValidateUsername)
		// Para los tooltips de los titulos
		$('.tooltipDemo').tooltip({
			placement: 'right',
			container: 'body',
			delay: { show: 300, hide: 100 }
		});

		$('.validateForm').each(function(indice,valor) {
			var form = $(this);

			console.log("validando")
			form.validate({
				rules: {
					email: {
						required: true,
						email: true,
						remote: {
							url: urlValidateUsername,
							type: "post",
							data: {
								email: function() {
									console.log($("#email").val())
									return $("#email").val();
								}
							}
						}
					},

					password: "required",
					password2: {
						equalTo: "#password"
					}
				},
				messages: {
					username: {
						required: "Enter a valid email",
						remote: "This email already exists on database"
					}
				},

				highlight: function(element) {
					$(element).closest('.form-group').addClass('has-error');
					$('#submitUser').attr('disabled','disabled')
				},
				unhighlight: function(element) {
					$(element).closest('.form-group').removeClass('has-error');
					$('#submitUser').removeAttr("disabled");
				},
				errorElement: 'span',
				errorClass: 'help-block',
				errorPlacement: function(error, element) {
					if(element.parent('.input-group').length) {
						error.insertAfter(element.parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});

		jQuery.validator.addClassRules("required_input", {
			required: true
		});
	}
);


//*********************************************************************************************
//*********************************************************************************************
//*********************************************************************************************

function IsEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
}