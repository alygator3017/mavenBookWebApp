package edu.wctc.ajs.bookwebapp.controller;

import edu.wctc.ajs.bookwebapp.ejb.AbstractFacade;
import edu.wctc.ajs.bookwebapp.model.Author;
import edu.wctc.ajs.bookwebapp.model.Book;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alyson
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    private static final String RESULTS_PAGE = "/bookList.jsp";
    private static final String DETAILS_PAGE = "/bookDetails.jsp";
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
    private static final String ACTION_ADD_NEW_BOOK = "addNewBook";
    private static int recordsCreated = 0;

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbJndiName;

    @Inject
    private AbstractFacade<Book> bookService;
    @Inject
    private AbstractFacade<Author> authService;

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
        Book book = null;

        try {
//            configDbConnection();
            switch (action) {
                case ACTION_LIST:
                    this.getBookList(request, bookService);
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case ACTION_BACK:
                    this.getBookList(request, bookService);
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    msg = "";
                    request.setAttribute("msg", msg);
                    break;
                case ACTION_DETAILS:
                    String id = request.getParameter("bookId");
                    if (id == null) {
                        //error because id is null
                    } else {
                        int bookId = new Integer(id);
                        book = bookService.find(bookId);
                        request.setAttribute("book", book);
                    }
                    pageDestination = DETAILS_PAGE;
                    break;
                case ACTION_EDIT_DELETE:
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    if (subAction.equals(ACTION_EDIT)) {

                        String bookId = request.getParameter("bookId");
                        String bookTitle = request.getParameter("bookTitle");
                        String isbn = request.getParameter("isbn");
                        String authorId = request.getParameter("authorId");
                        //check for repeat id's
                        try {
                            book = bookService.find(bookId);
                            book.setTitle(bookTitle);
                            book.setIsbn(isbn);
                            Author author = null;
                            if(authorId != null){
                                author = authService.find(new Integer(authorId));
                                book.setAuthorId(author);
                            }
                            bookService.edit(book);
                            msg = "edit update complete";
                        } catch (Exception ex) {
                            msg = ex.getMessage();
                        }
                        request.setAttribute("msg", msg);
                        this.getBookList(request, bookService);
                        this.getAuthorList(request, authService);
                        pageDestination = RESULTS_PAGE;
                        break;
                    } else if (subAction.equals(ACTION_BACK)) {
                        this.getBookList(request, bookService);
                        msg = "";
                        request.setAttribute("msg", msg);
                        pageDestination = RESULTS_PAGE;
                        break;
                    } else {
                        //assuming this is the delete button
                        String bookId = request.getParameter("bookId");
                        try {
                            book = bookService.find(new Integer(bookId));
                            bookService.remove(book);
                            msg = "Deletion of auth ID: " + bookId + " is complete";
                        } catch (Exception ex) {
                            msg = ex.getMessage();
                        }
                        request.setAttribute("msg", msg);
                        this.getBookList(request, bookService);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                case ACTION_CREATE:
                    pageDestination = DETAILS_PAGE;
                    break;
                case ACTION_ADD_NEW_BOOK:
                    String newBookTitle = request.getParameter("newBookName");
                    String isbn = request.getParameter("isbn");
                    String authorId = request.getParameter("author");
                    try {
                        Book newBook = new Book();
                        newBook.setTitle(newBookTitle);
                        newBook.setIsbn(isbn);
                        Author author;
                        if(authorId != null){
                            author = authService.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                        bookService.create(newBook);
                        msg = "Creation of Author: " + newBook + " completed";
                    } catch (Exception ex) {
                        msg = ex.getMessage();
                    }
                    request.setAttribute("msg", msg);
                    recordsCreated++;
                    String rc = "" + recordsCreated;
                    session.setAttribute("recordsCreated", rc);
                    this.getBookList(request, bookService);
                    this.getAuthorList(request, authService);
                    pageDestination = RESULTS_PAGE;
                    break;

                default:
                    request.setAttribute("msg", ERR);
                    this.getAuthorList(request, authService);
                    this.getBookList(request, bookService);
                    pageDestination = RESULTS_PAGE;
                    break;
            }
        } catch (ClassNotFoundException | SQLException | NumberFormatException e) {
            //request.setAttribute("errorMsg", ERR + " " + e.getCause().getMessage());
            errorMessage = errorMessage.concat(e.getMessage());
            request.setAttribute("msg", errorMessage);
        }

        this.getBookList(request, bookService);
        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
        view.forward(request, response);
//        response.sendRedirect(response.encodeRedirectURL(pageDestination));

    }

    private void getBookList(HttpServletRequest request, AbstractFacade<Book> bs) throws ClassNotFoundException, SQLException {
        List<Book> book = bs.findAll();
        request.setAttribute("booksList", book);
    }

    private void getAuthorList(HttpServletRequest request, AbstractFacade<Author> as) throws ClassNotFoundException, SQLException {
        List<Author> authors = as.findAll();
        request.setAttribute("booksList", authors);
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
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
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