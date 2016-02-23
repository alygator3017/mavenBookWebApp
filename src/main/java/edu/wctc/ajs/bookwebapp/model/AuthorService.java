package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Alyson
 */
public class AuthorService {
//high level class in DAO
    //hardcoded currently but will change later
    @Inject
    private AuthorDaoStrategy dao;
    private static final String TABLE_NAME = "author";
    
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
     * @param authorName
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
    
    public String updateAuthorById(List<String> colNamesToBeUpdate, List<Object> values, int authorId ) throws ClassNotFoundException, SQLException{
        String msg;
        int result = dao.updateAuthorById(colNamesToBeUpdate, values, authorId);
        if(result == 1){
            msg = "Update of author: " + authorId + " was successful";
        }else if(result > 1){
            msg = "Error: Something went wrong, update of too many authors for some reason?";
        }else{
            msg = "Error: Update of author: " + authorId + " was not completed";
        }
        return msg;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
        // works
        //String msg = srv.deleteAuthorById(3);
        //System.out.println(msg);
        //String msg = srv.createNewAuthor("Lizzie Bennet", new Date());
        String msg = srv.updateAuthorById(new ArrayList(Arrays.asList("author_id")), new ArrayList(Arrays.asList("5")), 11);
        System.out.println(msg);
        authors = srv.getAuthorList();
        System.out.println(authors);
    }
}
