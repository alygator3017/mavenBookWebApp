package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alyson
 */
public class MockAuthorDao implements AuthorDaoStrategy{
    
    private List<Author> authors; 
    public MockAuthorDao(){
        initTestData();
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
    public int createNewAuthor(String tableName, Author author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateAuthorById(String tableName, List<Object> values, Object authorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
