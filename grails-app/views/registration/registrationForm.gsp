<%--
  Created by IntelliJ IDEA.
  User: goarvic
  Date: 24/03/2015
  Time: 10:13
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>VGA Films DB - Register</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <asset:javascript src="registration.js"/>

    <script type="text/javascript">
        var urlValidateUsername = '${createLink(controller: "registration", action: "isEmailAvailable")}'
    </script>



</head>

<body>
<div id="page-body" role="main" class="container">
    <div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">New User Formulary</h3>
    </div>



    <g:form action="addUser"  class="validateForm" controller="userInvitation" method="POST" enctype="multipart/form-data" name="addUserForm">
        <div class="panel-body">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="form-group">
                        <label for="email">E-mail / Username</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="email"></a>
                        <input class="form-control" name="email" id="email">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Password"></a>
                        <input class="form-control" name="password" type="password" id="password">
                    </div>

                    <div class="form-group">
                        <label for="password2">Confirm Password</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Confirm Password"></a>
                        <input class="form-control" name="password2" type="password" id="password2">
                    </div>

                    <div class="form-group">
                        <label for="completeName">Name</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Complete Name"></a>
                        <input class="form-control" name="completeName" id="completeName">
                    </div>
                </div>


                <div class="col-lg-6 col-md-6">
                    <div class="jumbotron">
                        <h1>Welcome to VGA Films</h1>
                        <p>Please fill the Registration Form</p>
                        <%--<p><a class="btn btn-primary btn-lg" role="button">Learn more</a></p>--%>
                    </div>
                </div>
            </div>
        </div>


        <div class="panel-footer">
            <button type="submit" class="btn btn-default" id="submitUser">Submit</button>
        </div>
        </div>
    </g:form>
    </div>
</div>
</body>
</html>