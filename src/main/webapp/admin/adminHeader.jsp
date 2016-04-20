<%-- 
    Document   : adminHeader
    Created on : Apr 20, 2016, 5:19:03 PM
    Author     : Alyson
--%>

<c:set var="context" value="${pageContext.request.contextPath}" /> 
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
                    <a href="admin/adminHome.jsp">Back</a>
                </li>
                <li>
                    <a href='login.jsp'>Log In</a>
                </li>
                <li>

                    <a href ="#"> Logged in as: <sec:authentication property="principal.username"></sec:authentication> </a>
                </li>
                <li>
                    <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
                </li>
            </ul>
        </div>
    </div>
</nav>