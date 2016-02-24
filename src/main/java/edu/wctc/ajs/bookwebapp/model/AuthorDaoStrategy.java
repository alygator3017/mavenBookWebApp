package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alyson
 */
public interface AuthorDaoStrategy {

    public abstract List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
    public abstract Author getAuthorById(Object id) throws ClassNotFoundException, SQLException, Exception;
    
    public abstract int deleteAuthorById(Object authorId) throws ClassNotFoundException, SQLException;
    
    public abstract int createNewAuthor(Author author)throws ClassNotFoundException, SQLException;
    
    public abstract int updateAuthorById(Object currAuthorId, Object authorId, Object authorName, Object dateAdded)  throws ClassNotFoundException, SQLException ;
    
    public DBStrategy getDb();
    
    public void setDb(DBStrategy db);
}
