<%-- 
    Document   : adminBookList
    Created on : Apr 20, 2016, 1:55:09 PM
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
        <!--layout-->
        <link href="${context}/css/layout.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="adminHeader.jsp"/>
            <div class="row col-md-12">
                <h1 class="col-md-5">Book Database</h1>
                <img src="${context}/images/books8.png" alt="" class="img-responsive col-lg-2" style="padding-top: 1%;max-width: 100px; max-height: 100px;"/>
            </div>

            <div  class="col col-md-8">
                <div id="answer">
                    <sec:authorize access="hasAnyRole('ROLE_MGR')">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createNewBookModal">Add New Book</button>
                    </sec:authorize>
                    <table class="table table-striped table-hover ">
                        <thead>
                            <tr>
                                <th>Book ID</th>
                                <th>Title</th>
                                <th>ISBN</th>
                                <th>Author</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${booksList}">
                                <tr>
                                    <td>
                                        <c:out value="${book.bookId}" />
                                    </td>
                                    <td><form method="POST" action="<%= response.encodeURL(this.getServletContext().getContextPath() + "/BookController?action=adminDetails")%>">
                                            <input type="submit" class="submitLink" value="${book.title}" name="submit"> 
                                            <input type="hidden" id="bookId" name="bookId" value="${book.bookId}"/>
                                        </form>
                                    </td>
                                    <td>
                                        <c:out value="${book.isbn}"/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty book.authorId}">
                                                ${book.authorId.authorName}
                                            </c:when>
                                            <c:otherwise>
                                                None
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </form>
                </div>
                <div class="col-md-12" name="recordsCreated" id="recordsCreated">Records Created in Session: ${recordsCreated}</div>
                <div class="col-md-12 message" name="message" id="message">${msg}</div>
            </div>

            <div class="col-md-4" style="margin-left: -10px">
                <img src="${context}/images/bookcase.jpg" alt="" class="img-responsive" style="margin-top: -20%; box-shadow: 0px 0px 30px #2A7811;"/>
            </div>


            <div class="modal fade" tabindex="-1" role="dialog" id="createNewBookModal" aria-labelledby="createNewBookModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Add New Book</h4>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="<%= response.encodeURL(this.getServletContext().getContextPath() + "/BookController?action=addNewBook")%>" class="detailsForm">
                                <fieldset>
                                    <legend></legend>
                                    <div class="form-group">
                                        <label for="newBookName" class="control-label">Book Name:</label>
                                        <div>
                                            <input type="text" class="form-control" name="newBookName" id="newBookName" value="" required>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="isbn" class="control-label">ISBN:</label>
                                        <div>
                                            <input type="text" class="form-control" name="isbn" id="isbn" value="" required>
                                        </div>
                                    <div class="form-group">
                                        <label for="author" class="control-label">Author:</label>
                                        <div>
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
            <%
                Object count = application.getAttribute("sessionOpenCount");
                String sessionCount = (count == null) ? "1" : count.toString();
            %>
            <p>There are currently <%= sessionCount%> user sessions active</p>
            <br/>
            <hr/>
            <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>             is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>
        </div>

        <script src="${context}/js/stickyfooter.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="${context}/js/modal.js" type="text/javascript"></script>
    </body>
</html>

