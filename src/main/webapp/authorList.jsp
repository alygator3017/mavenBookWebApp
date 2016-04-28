<%-- 
    Document   : AllAuthorList
    Created on : Feb 8, 2016, 2:56:54 PM
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
        
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Book Database</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <!--sticky footer -->
        <link href="css/stickyfooter.css" rel="stylesheet" type="text/css"/>   
        <!--bootstrap-->
        <link href="css/bootstrap.paper.min.css" rel="stylesheet" type="text/css"/>
        <!--layout-->
        <link href="css/layout.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">

            <jsp:include page="header.jsp"/>
            <div class="row col-lg-12">
                <h1 class="col-lg-5">Author Database</h1>
                <img src="images/books8.png" alt="" class="img-responsive col-lg-2" style="padding-top: 1%;max-width: 100px; max-height: 100px;"/>
            </div>
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <h1>REGULAR USER SHOULD NOT SEE THIS</h1>
            </sec:authorize>
            <div class="row col-md-12">
                <div  class="col-md-8">
                    <div id="answer">
                        <table class="table table-striped table-hover col-md-8">
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
                                        <td><form method="POST" action="<%= response.encodeURL("AuthorController?action=details")%>">
                                                <input type="submit" class="submitLink" value="${author.authorName}" name="submit"/> 
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
                    <div class="col-lg-12 message" name="message" id="message">${msg}</div>
                </div>

                <div class="col-md-4" style="margin-left: -10px">
                    <img src="images/bookcase.jpg" alt="" class="img-responsive" style="margin-top: -20%; box-shadow: 0px 0px 30px #2A7811;"/>
                </div>
            </div>
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

        <script src="js/stickyfooter.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
    </body>
</html>

