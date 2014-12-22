<script type="text/javascript">
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
                        <div class="row">
                            <div class="col-md-3">
                                <label for="password">New Password</label>
                            </div>
                            <div class="col-md-9">
                                <input class="form-control" name="password" id="password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label for="passwordRetype">Retype Password</label>
                            </div>
                            <div class="col-md-9">
                                <input class="form-control" name="passwordRetype" id="passwordRetype">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button id="buttonSendInvitation" type="submit" type="button" class="btn btn-primary">Save</button>
                </div>
            </g:form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->