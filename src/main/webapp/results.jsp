<%-- 
    Document   : AllAuthorList
    Created on : Feb 8, 2016, 2:56:54 PM
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
        <!--layout-->
        <link href="casss/layout.css" rel="stylesheet" type="text/css"/>
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
                                <a href="bookDatabase.jsp">Back</a>
                            </li>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="row">
                <h1 class="col-lg-5">Author Database</h1>
                <img src="imgs/books8.png" alt="" class="img-responsive col-lg-2" style="padding-top: 1%;max-width: 100px; max-height: 100px;"/>
            </div>

            <div  class="col col-lg-8">
                <div id="answer">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createNewAuthorModal">Create New Author</button>

                    <table class="table table-striped table-hover ">
                        <thead>
                            <tr>
                                <th>Author ID</th>
                                <th>Author</th>
                                <th>Date Added</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="author" items="${authorsList}">
                                <tr>
                                    <td>
                                        <c:out value="${author.authorId}" />
                                    </td>
                                    <td><form method="POST" action="AuthorController?action=details">
                                            <input type="submit" class="submitLink" value="${author.authorName}" name="submit"> 
                                            <input type="hidden" id="authorId" name="authorId" value="${author.authorId}"/>
                                        </form>
                                    </td>
                                    <td>
                                        <c:out value="${author.dateAdded}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </form>
                </div>
            </div>
            <div class="col-lg-12 message" name="message" id="message">${msg}</div>

            <div class="modal fade" tabindex="-1" role="dialog" id="createNewAuthorModal" aria-labelledby="createNewAuthorModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Create New Author</h4>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="AuthorController?action=addNewAuthor" class="detailsForm">
                                <fieldset>
                                    <legend></legend>
                                    <div class="form-group">
                                        <label for="authorName" class="control-label">Author Name:</label>
                                        <div>
                                            <input type="text" class="form-control" name="newAuthorName" id="newAuthorName" value="">
                                        </div>
                                    </div>
                                    
                                    <input type="submit" value="Create" name="submit" class="btn btn-primary"/>
                                </fieldset>
                            </form>
                        </div>
                        <hr/>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->

            <div class="push"></div>
        </div>
        <div class="footer">
            <br/>
            <hr/>
            <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
        </div>

        <script src="javascript/stickyfooter.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="javascript/modal.js" type="text/javascript"></script>
    </body>
</html>

