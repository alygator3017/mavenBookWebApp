<%-- 
    Document   : adminBookDetails
    Created on : Apr 20, 2016, 1:55:18 PM
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
        <c:set var="context" value="${pageContext.request.contextPath}" /> 
        <title>Book Database</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <!--sticky footer -->
        <link href="${context}/css/stickyfooter.css" rel="stylesheet" type="text/css"/>   
        <!--bootstrap-->
        <link href="${context}/css/bootstrap.paper.min.css" rel="stylesheet" type="text/css"/>
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
                                <a href="<%= response.encodeURL(this.getServletContext().getContextPath() + "/BookController?action=list")%>">Back</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <h3>Book Details</h3>
            <form method="POST" action="<%= response.encodeURL(this.getServletContext().getContextPath() + "/BookController?action=editDelete")%>" class="detailsForm col-lg-8">
                <fieldset>
                    <legend>${book.title}: Details</legend>
                    <div class="form-group">
                        <label for="bookId" class="col-lg-2 control-label">Book Id:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="bookId" id="bookId" value="${book.bookId}" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="title" class="col-lg-2 control-label">Title:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="title" id="title" value="${book.title}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="isbn" class="col-lg-2 control-label">ISBN:</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="isbn" id="isbn" value="${book.isbn}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="author" class="col-lg-2 control-label">Author</label>
                        <div class="col-lg-10">
                            <select id="authorDropDown" name="authorId">
                                <c:choose>
                                    <c:when test="${not empty book.authorId}">
                                        <option value="">None</option>
                                        <c:forEach var="author" items="${authors}">                                       
                                            <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                                            <option value="${author.authorId}" <c:if test="${rowCount.count == 1}">selected</c:if>>${author.authorName}</option>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                    </div>
                        <input type="submit" value="Save Edit" name="submit" />&nbsp;
                        <input type="submit" value="Delete" name="submit" />
                </fieldset>
            </form>
            <form method="POST" action="<%= response.encodeURL(this.getServletContext().getContextPath() + "/BookController?action=adminBack")%>" class="detailsForm col-lg-8">
                <input type="submit" value="Back" name="submit" class="btn btn-primary"/>
            </form>


            <div class="push"></div>
        </div>
        <div class="footer">
            <br/>
            <hr/>
            <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
        </div>

        <script src="${context}/js/stickyfooter.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        

    </body>
</html>
