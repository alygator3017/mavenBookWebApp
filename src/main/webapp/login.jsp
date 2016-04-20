<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--spring tag library, should be on every web page just in case you're using spring-->
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Jim Lombardo">

        <title>Spring Security Login Page</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <!--sticky footer -->
        <link href="css/stickyfooter.css" rel="stylesheet" type="text/css"/>   
        <script src="js/stickyfooter.js" type="text/javascript"></script>
        <!--bootstrap-->
        <link href="css/bootstrap.paper.min.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div class="wrapper">
            <nav class='navbar navbar-default'>
                <div class='container-fluid'>
                    <div class="navbar-header">
                        <!--header stuff-->

                        <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a href="index.html" class="navbar-brand" style="text-align: center">Book library</a>
                    </div>
                    <div class="navbar-collapse collapse" id="navbar-main">
                        <ul class="nav navbar-nav">
                            <li>
                                <a href="index.html">Home</a>
                            </li>
                            <li>
                                <a href='login.jsp'>Log In</a>
                            </li>
                            <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
                                <li>

                                    Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
                                    </li>
                                    <li>
                                        <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
                                </li>

                            </sec:authorize>  
                        </ul>
                    </div>
                </div>
            </nav>
            <form id="signInForm" role="form" method='POST' action="<c:url value='j_spring_security_check' />">
                <sec:csrfInput />


                <div class="col-sm-6">
                    <h3 style="font-weight: 200;">Sign in </h3>
                    <div class="form-group">
                        <input tabindex="1" class="form-control" id="j_username" name="j_username" placeholder="Email address" type="text" autofocus />
                        <input tabindex="2" class="form-control" id="j_password" name="j_password" type="password" placeholder="password" />
                    </div>
                    <div class="form-group">
                        <input class="btn btn-warning" name="submit" type="submit" value="Sign in" />
                    </div>
                </div>
            </form>

            <div class="push"></div>
        </div>
        <div class="footer">
            <br/>
            <hr/>
            <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
        </div>

        
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        

    </body>
</body>
</html>