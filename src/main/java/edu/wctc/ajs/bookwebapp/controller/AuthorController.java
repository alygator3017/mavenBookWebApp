package edu.wctc.ajs.bookwebapp.controller;

import edu.wctc.ajs.bookwebapp.model.Author;
import edu.wctc.ajs.bookwebapp.model.AuthorService;
import edu.wctc.ajs.bookwebapp.model.MockAuthorDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alyson
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String RESULTS_PAGE = "/results.jsp";
    private static final String DETAILS_PAGE = "/details.jsp";
    private static final String ERR = "data cannot be found";
    private static final String ACTION = "action";
    private static final String SUBMIT_ACTION = "submit";
    private static final String ACTION_LIST = "list";
    private static final String ACTION_DETAILS = "details";
    private static final String ACTION_EDIT_DELETE = "editDelete";
    private static final String ACTION_CREATE = "create";
    private static final String ACTION_DELETE = "Delete";
    private static final String ACTION_EDIT = "Save Edit";
    private static final String ACTION_BACK = "Back";

    @Inject
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
        String pageDestination = RESULTS_PAGE;
        String action = request.getParameter(ACTION);
        String errorMessage = "";
        try {
            switch (action) {
                case ACTION_LIST:
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case ACTION_BACK:
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case ACTION_DETAILS:
                    String id = request.getParameter("authorId");
                    if (id == null) {
                        System.out.println("ISSUES WITH IDS in ACTION_DETAILS case");
                    } else {
                        String authorId = id;
                        Author author = authService.getAuthorById(authorId);
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
                        String currDetailsAuthId = request.getParameter("currAuthorId");
                        //check for repeat id's
                        String msg = authService.updateAuthorById(currDetailsAuthId, authorId, authorName, dateAdded);
                        request.setAttribute("msg", msg);
                        this.getAuthorList(request, authService);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }

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
        RequestDispatcher view = getServletContext().getRequestDispatcher(pageDestination);
        view.forward(request, response);

    }

    private void getAuthorList(HttpServletRequest request, AuthorService as) throws ClassNotFoundException, SQLException {
        List<Author> authors = as.getAuthorList();
        request.setAttribute("authorsList", authors);
    }

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

}
