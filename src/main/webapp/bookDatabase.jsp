<%-- 
    Document   : bookDatabase
    Created on : Feb 8, 2016, 10:26:10 AM
    Author     : Alyson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Database</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <!--sticky footer -->
        <link href="casss/stickyfooter.css" rel="stylesheet" type="text/css"/>   
        <!--bootstrap-->
        <link href="casss/bootstrap.paper.min.css" rel="stylesheet" type="text/css"/>
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
                            <!--<li class='dropdown'>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" id='labs'>Labs <span class="caret"></span></a>
                                <ul class="dropdown-menu" aria-labelledby='labs'>
                                    <li><a href="lab1.html">Lab 1</a></li>
                                    <li><a href="lab2.jsp">Lab 2</a></li>
                                    <li class='active'><a href="lab3.jsp">Lab 3</a></li>
                                </ul>-->
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="row">
                <h1 class="col-lg-4">Book Database</h1>
                <img src="imgs/books8.png" alt="" class="img-responsive col-lg-2" style="padding-top: 1%;max-width: 100px; max-height: 100px;"/>
            </div>
            <div class="col-lg-4">
                <form name="allBooks" id="allBooks" class="forms form-horizontal" method="POST" action="AuthorController?page=same" onsubmit="">
                    <fieldset>
                        <legend>Get Books</legend>

                        <div class='form-group'>
                            <div class="col-lg-10 col-lg-offset-2">
                                <input type="submit" name="submit" value="Get All Authors"/>
                            </div>
                        </div>
                    </fieldset>
                </form>
               <!-- <form name="allBooks2" id="allBooks" class="forms form-horizontal" method="POST" action="AuthorController?page=not" onsubmit="">
                    <fieldset>
                        <div class='form-group'>
                            <div class="col-lg-10 col-lg-offset-2">
                                <input type="submit" name="submit" value="Get All Authors To Another Page"/>
                            </div>
                        </div>
                    </fieldset>
                </form> -->
            </div>
           
            <div class="push"></div>
        </div>
        <div class="footer">
            <br/>
            <hr/>
            <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
        </div>

        <script src="javascript/stickyfooter.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        

    </body>
</html>
