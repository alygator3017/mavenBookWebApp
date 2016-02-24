package edu.wctc.ajs.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

/**
 *
 * @author Alyson
 */
@Alternative
@SessionScoped
public class MockAuthorDao implements AuthorDaoStrategy, Serializable{
    @Inject
    private DBStrategy db;
    private List<Author> authors; 
    public MockAuthorDao(){
        initTestData();
    }

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    
    @Override
    public final List<Author> getAuthorList(){
        return authors;
    }
    
    private void initTestData() {
        authors = new ArrayList<>(Arrays.asList(new Author(1432, "Austen, Jane"), new Author(2354, "Bronte, Charolotte"), new Author(5341, "Martin, George R.R")));
    }
    
//    public static void main(String[] args) {
//        AuthorService as = new AuthorService();
//        List<Author> authors = as.getAllAuthors();
//        for(Author at: authors){
//            System.out.println(at.toString());
//        }
//    }

    @Override
    public int deleteAuthorById(Object authorId) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createNewAuthor(Author author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Author getAuthorById(Object id) throws ClassNotFoundException, SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateAuthorById(Object currAuthorId, Object authorId, Object authorName, Object dateAdded) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
