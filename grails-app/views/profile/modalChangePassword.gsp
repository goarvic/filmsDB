<script type="text/javascript">
    $(document).ready( function() {

        $('.validateForm').each(function(indice,valor) {
            var form = $(this);

            form.validate({

                rules: {
                    password : {
                        required: true, minlength: 4
                    },
                    passwordRetype: {
                        required: true, equalTo: "#password", minlength: 4
                    }
                },
                messages: {
                    password: {
                        required: "Enter non-empty password"
                    },
                    passwordRetype: {
                        equalTo: "Passwords don't match"
                    }
                },

                highlight: function(element) {
                    $(element).closest('.form-group').addClass('has-error');
                    $('#changePasswordButton').attr('disabled','disabled')
                },
                unhighlight: function(element) {
                    $(element).closest('.form-group').removeClass('has-error');
                    $('#changePasswordButton').removeAttr("disabled");
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


    });


    $(document).on( 'click', '#changePasswordButton',
            function()
            {
                //$('#myModal').show();
                //window.location.assign("#inviteDialog")
                /*window.location.assign("#inviteDialog")


                var groupName = $(this).parent().attr("id");
                groupName = groupName.substr(6,groupName.length-1);


                var inputGroupName = document.getElementById('groupNameInput');

                $('#groupNameInput').val(groupName);
                console.log($('#groupNameInput').val())*/


            }
    );
</script>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form action="changePassword"  class="validateForm" controller="profile" method="POST" enctype="multipart/form-data" name="changePassForm" id="changePassForm">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Change Password</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="row" style="margin: 30px;">
                            <div class="col-md-4">
                                <label for="password">New Password</label>
                            </div>
                            <div class="col-md-8">
                                <input type="password" class="form-control" name="password" id="password">
                            </div>
                        </div>
                        <div class="row" style="margin: 30px;">
                            <div class="col-md-4">
                                <label for="passwordRetype">Retype Password</label>
                            </div>
                            <div class="col-md-8">
                                <input type="password" class="form-control" name="passwordRetype" id="passwordRetype">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button id="changePasswordButton" type="submit" type="button" class="btn btn-primary">Save</button>
                </div>
            </g:form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->