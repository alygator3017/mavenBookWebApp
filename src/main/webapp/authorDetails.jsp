<%-- 
    Document   : details
    Created on : Feb 23, 2016, 9:38:16 AM
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
        <link href="css/stickyfooter.css" rel="stylesheet" type="text/css"/>   
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
                                <a href="<%= response.encodeURL("AuthorController?action=list")%>">Back</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <h3>Author Details</h3>
            <form method="POST" action="" class="detailsForm col-lg-8">
                <fieldset>
                    <legend>${author.authorName}: Details</legend>
                    <div class="form-group">
                        <label for="authorId" class="col-lg-2 control-label">Author Id:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="authorId" id="authorId" value="${author.authorId}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="authorName" class="col-lg-2 control-label">Author Name:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="authorName" id="authorName" value="${author.authorName}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="authorId" class="col-lg-2 control-label">Date Added:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="dateAdded" id="dateAdded" value="${author.dateAdded}" readonly>
                        </div>
                    </div>
                </fieldset>
            </form>


            <div class="push"></div>
        </div>
        <div class="footer">
            <br/>
            <hr/>
            <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
        </div>

        <script src="js/stickyfooter.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        

    </body>
</html>

