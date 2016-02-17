package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alyson
 */
public class AuthorService {
//high level class in DAO
    //hardcoded currently but will change later
    private AuthorDaoStrategy dao = new AuthorDao();
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        return dao.getAuthorList();
    }
    
    /**
     *
     * @param authorId
     * @return author deletion confirmation
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String deleteAuthorById(int authorId) throws ClassNotFoundException, SQLException{
        String deleteMsg = dao.deleteAuthorById(authorId);
        return deleteMsg;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
        // works
        //String msg = srv.deleteAuthorById(3);
        //System.out.println(msg);
        authors = srv.getAuthorList();
        System.out.println(authors);
    }
}
