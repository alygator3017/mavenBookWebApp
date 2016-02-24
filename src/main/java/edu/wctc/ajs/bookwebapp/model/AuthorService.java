package edu.wctc.ajs.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Alyson
 */
@SessionScoped
public class AuthorService implements Serializable{
//high level class in DAO
    //hardcoded currently but will change later
    //@Inject
    //private AuthorDaoStrategy dao;
    //test dao
    private AuthorDaoStrategy dao = new AuthorDao();
    private static final String TABLE_NAME = "author";

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    /**
     * Connection to the DAO class to retrieve the author list.
     * @return The list of authors in the database.
     * @throws ClassNotFoundException exception
     * @throws SQLException exception.
     */
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        return dao.getAuthorList();
    }
    
    /**
     * Connection to the dao class to retrieve a single author by their id
     * @param authorId id of the other to be retrieved from the database.
     * @return the author being retrieved by the id.
     * @throws SQLException exception
     * @throws Exception exception
     */
    public Author getAuthorById(Object authorId) throws SQLException, Exception{
        return dao.getAuthorById(authorId);
    }
    
    /**
     * Connection to the dao class to delete an author from the database by their id
     * @param authorId id of the author to be deleted from the database
     * @return author deletion confirmation
     * @throws ClassNotFoundException exception
     * @throws SQLException exception
     */
    public String deleteAuthorById(Object authorId) throws ClassNotFoundException, SQLException{
        String msg = null;
        int result = dao.deleteAuthorById(authorId);
                if(result == 1){
            msg = "Deletion of authorId: " + authorId + " successful.";
        }else if(result > 1){
            msg = "Error: Deletion of authorID " + authorId + " failed. Too many records deleted";
        }else{
            msg = "Error: Deletion of authorID " + authorId + " failed.";
        }
        return msg;
    }
    
    /**
     * Creating a new author in the database. This includes both the name of the
     * author and the date. The ID is auto incremented in the table. 
     * @param authorName name of the author being created
     * @param date will be formatted in this method to yyyy.M.d when adding to database
     * @return a message on whether the creation was successful or not.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String createNewAuthor(String authorName, Date date) throws ClassNotFoundException, SQLException{
        String msg;
        Author author = new Author();
        author.setAuthorName(authorName);
        author.setDateAdded(date);
        int result = dao.createNewAuthor(author);
        if(result == 1){
            msg = "Creation of author: " + author.toString() + " was successful";
        }else if(result > 1){
            msg = "Error: creation of too many authors";
        }else{
            msg = "Error: Creation of author: " + author + " was unsuccessful";
        }
        return msg;
    }
    
    public String updateAuthorById(Object currAuthorId, Object authorId, Object authorName, Object dateAdded ) throws ClassNotFoundException, SQLException{
        String msg;
        int result = dao.updateAuthorById(currAuthorId, authorId, authorName, dateAdded);
        if(result == 1){
            msg = "Update of author: " + authorId + " was successful";
        }else if(result > 1){
            msg = "Error: Something went wrong, update of too many authors for some reason?";
        }else{
            msg = "Error: Update of author: " + authorId + " was not completed";
        }
        return msg;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
        // works
        //String msg = srv.deleteAuthorById(3);
        //System.out.println(msg);
        //String msg = srv.createNewAuthor("Lizzie Bennet", new Date());
        String msg = srv.updateAuthorById(5,5,"Charlotte Bronte", new Date());
        System.out.println(msg);
        //Author author = srv.getAuthorById(1);
        //System.out.println(author);
        authors = srv.getAuthorList();
        System.out.println(authors);
    }
}
