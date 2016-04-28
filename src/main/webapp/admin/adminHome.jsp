<%-- 
    Document   : adminHome
    Created on : Apr 20, 2016, 1:53:39 PM
    Author     : Alyson
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Database</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <!--sticky footer --> 
        <link href="../css/stickyfooter.css" rel="stylesheet" type="text/css"/>
        <!--bootstrap-->
        <link href="../css/bootstrap.paper.min.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
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
                                    <a href="../index.html">User Home</a>
                                </li>
                                <li>
                                    <a href='../login.jsp'>Log In</a>
                                </li>
                                <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
                                    <li>

                                        <a href ="#"> Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::</a>
                                        </li>
                                        <li>
                                            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
                                    </li>

                                </sec:authorize>  
                            </ul>
                        </div>
                    </div>
                </nav>
                <div class="row">
                    <h1 class="col-lg-4">Book Database</h1>
                    <img src="../images/books8.png" alt="" class="img-responsive col-lg-2" style="padding-top: 1%;max-width: 100px; max-height: 100px;"/>
                </div>
                <div class="col-lg-4">
                    <form name="allBooks" id="allBooks" class="forms form-horizontal" method="POST" action="<%= response.encodeURL("../AuthorController?action=adminList")%>" onsubmit="">
                        <fieldset>

                            <div class='form-group'>
                                <div class="col-lg-10 col-lg-offset-2">
                                    <input type="submit" name="submit" value="Get All Authors"/>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="col-lg-4">
                    <form name="allBooks" id="allBooks" class="forms form-horizontal" method="POST" action="<%= response.encodeURL("../BookController?action=adminList")%>" onsubmit="">
                        <fieldset>

                            <div class='form-group'>
                                <div class="col-lg-10 col-lg-offset-2">
                                    <input type="submit" name="submit" value="Get All Books"/>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>

                <div class="push"></div>
            </div>
            <div class="footer">
                <br/>
                <hr/>
                <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
            </div>

            <script src="../js/stickyfooter.js" type="text/javascript"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        </sec:authorize>
            
    </body>
</html>

