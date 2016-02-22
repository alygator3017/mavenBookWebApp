package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alyson
 */
public interface AuthorDaoStrategy {

    public abstract List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
    public abstract int deleteAuthorById(Object authorId) throws ClassNotFoundException, SQLException;
    
    public abstract int createNewAuthor(String tableName, Author author)throws ClassNotFoundException, SQLException;
    
    public abstract int updateAuthorById(String tableName, List<Object> values, Object authorId);
}
