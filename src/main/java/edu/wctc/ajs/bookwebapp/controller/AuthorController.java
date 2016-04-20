package edu.wctc.ajs.bookwebapp.controller;

import edu.wctc.ajs.bookwebapp.entity.Author;
import edu.wctc.ajs.bookwebapp.service.AuthorService;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Alyson
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String RESULTS_PAGE = "/authorList.jsp";
    private static final String RESULTS_ADMIN_PAGE = "/adminAuthorList.jsp";
    private static final String DETAILS_PAGE = "/authorDetails.jsp";
    private static final String ERR = "data cannot be found";
    private static final String ACTION = "action";
    private static final String SUBMIT_ACTION = "submit";
    private static final String ACTION_LIST = "list";
    private static final String ACTION_ADMIN_LIST = "adminList";
    private static final String ACTION_DETAILS = "details";
    private static final String ACTION_EDIT_DELETE = "editDelete";
    private static final String ACTION_CREATE = "create";
    private static final String ACTION_DELETE = "Delete";
    private static final String ACTION_EDIT = "Save Edit";
    private static final String ACTION_BACK = "Back";
    private static final String ACTION_ADMIN_BACK = "Back To Author List";
    private static final String ACTION_ADD_NEW_AUTHOR = "addNewAuthor";
    private static int recordsCreated = 0;

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbJndiName;

    private AuthorService authService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException classNotFoundException
     * @throws java.sql.SQLException SqlException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        // use init parameters to config database connection

        String pageDestination = RESULTS_PAGE;
        String action = request.getParameter(ACTION);
        String errorMessage = "";
        String msg = "";

        try {
//            configDbConnection();
            switch (action) {
                case ACTION_LIST:
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case ACTION_ADMIN_LIST:
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_ADMIN_PAGE;
                    break;
                case ACTION_BACK:
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    msg = "";
                    request.setAttribute("msg", msg);
                    break;
                case ACTION_ADMIN_BACK:
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_ADMIN_PAGE;
                    msg = "";
                    request.setAttribute("msg", msg);
                    break;
                case ACTION_DETAILS:
                    String id = request.getParameter("authorId");
                    if (id == null) {
                        //error because id is null
                    } else {
                        //// BIG CHANGE DUE TO SPRING JPA DUE TO LAZY LOADING OF BOOKS //////
                        Author author = authService.findByIdAndFetchBooksEagerly(id);
                        if (author == null) {
                            author = authService.findById(id);
                            author.setBookSet(new LinkedHashSet<>());
                        }
                        ////////////////////////////////////////
                        request.setAttribute("author", author);
                    }
                    pageDestination = DETAILS_PAGE;
                    break;
                case ACTION_EDIT_DELETE:
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    if (subAction.equals(ACTION_EDIT)) {

                        String authorId = request.getParameter("authorId");
                        String authorName = request.getParameter("authorName");
                        String dateAdded = request.getParameter("dateAdded");
                        //check for repeat id's
                        try {
                            //// BIG CHANGE DUE TO SPRING JPA DUE TO LAZY LOADING OF BOOKS //////
                            Author author = authService.findByIdAndFetchBooksEagerly(authorId);
                            if (author == null) {
                                author = authService.findById(authorId);
                                author.setBookSet(new LinkedHashSet<>());
                            }
                            ////////////////////////////////////////
                            author.setAuthorName(authorName);
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            author.setDateAdded(df.parse(dateAdded));
                            authService.edit(author);
                            msg = "edit update complete";
                        } catch (Exception ex) {
                            msg = ex.getMessage();
                            request.setAttribute("msg", msg);
                            this.getAuthorList(request, authService);
                            pageDestination = RESULTS_PAGE;
                        }
                        request.setAttribute("msg", msg);
                        this.getAuthorList(request, authService);
                        pageDestination = RESULTS_PAGE;
                        break;
                    } else if (subAction.equals(ACTION_BACK)) {
                        this.getAuthorList(request, authService);
                        msg = "";
                        request.setAttribute("msg", msg);
                        pageDestination = RESULTS_PAGE;
                        break;
                    } else {
                        //assuming this is the delete button
                        String authId = request.getParameter("authorId");
                        try {
                            //// BIG CHANGE DUE TO SPRING JPA DUE TO LAZY LOADING OF BOOKS //////
                            Author author = authService.findByIdAndFetchBooksEagerly(authId);
                            if (author == null) {
                                author = authService.findById(authId);
                                author.setBookSet(new LinkedHashSet<>());
                            }
                            ////////////////////////////////////////
                            authService.remove(author);
                            msg = "Deletion of auth ID: " + authId + " is complete";
                        } catch (Exception ex) {
                            msg = ex.getMessage();
                        }
                        request.setAttribute("msg", msg);
                        this.getAuthorList(request, authService);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                case ACTION_CREATE:
                    pageDestination = DETAILS_PAGE;
                    break;
                case ACTION_ADD_NEW_AUTHOR:
                    String newAuthorName = request.getParameter("newAuthorName");
                    try {
                        Author newAuth = new Author(0);
                        newAuth.setAuthorName(newAuthorName);
                        newAuth.setDateAdded(new Date());
                        authService.edit(newAuth);
                        msg = "Creation of Author: " + newAuth + " completed";
                    } catch (Exception ex) {
                        msg = ex.getMessage();
                    }
                    request.setAttribute("msg", msg);
                    recordsCreated++;
                    String rc = "" + recordsCreated;
                    session.setAttribute("recordsCreated", rc);
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    break;

                default:
                    request.setAttribute("msg", ERR);
                    pageDestination = RESULTS_PAGE;
                    break;
            }
        } catch (Exception e) {
            //request.setAttribute("errorMsg", ERR + " " + e.getCause().getMessage());
            errorMessage = errorMessage.concat(e.getMessage());
            request.setAttribute("msg", errorMessage);
        }

        this.getAuthorList(request, authService);
        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
        view.forward(request, response);
//        response.sendRedirect(response.encodeRedirectURL(pageDestination));

    }

    private void getAuthorList(HttpServletRequest request, AuthorService as) throws ClassNotFoundException, SQLException {
        List<Author> authors = as.findAll();
        request.setAttribute("authorsList", authors);
    }
//
//    private void configDbConnection() throws NamingException, Exception {
//        if (dbJndiName == null) {
//            authService.getDao().initDao(driverClass, url, userName, password);
//        } else {
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
//            authService.getDao().initDao(ds);
//        }
//
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
//    @Override
//    public void init() throws ServletException {
//        // Get init params from web.xml
////        driverClass = getServletContext().getInitParameter("db.driver.class");
////        url = getServletContext().getInitParameter("db.url");
////        userName = getServletContext().getInitParameter("db.username");
////        password = getServletContext().getInitParameter("db.password");
//        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
//    }
    /**
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        authService = (AuthorService) ctx.getBean("authorService");

    }
}
