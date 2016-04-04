<%-- 
    Document   : details
    Created on : Feb 23, 2016, 9:38:16 AM
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
                            <li>
                                <a href="<%= response.encodeURL("AuthorController?action=list")%>">Back</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <h3>Author Details</h3>
            <form method="POST" action="<%= response.encodeURL("AuthorController?action=editDelete")%>" class="detailsForm col-lg-8">
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
                            <input type="text" class="form-control" name="authorName" id="authorName" value="${author.authorName}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="authorId" class="col-lg-2 control-label">Date Added:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="dateAdded" id="dateAdded" value="${author.dateAdded}" readonly>
                        </div>
                    </div>
                    <input type="submit" value="Save Edit" name="submit" />&nbsp;
                    <input type="submit" value="Delete" name="submit" />

                </fieldset>
            </form>
            <form method="POST" action="<%= response.encodeURL("AuthorController?action=Back")%>" class="detailsForm col-lg-8">
                <input type="submit" value="Back" name="submit" class="btn btn-primary"/>
            </form>


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

