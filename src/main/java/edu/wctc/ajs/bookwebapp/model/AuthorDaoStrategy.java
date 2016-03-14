package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

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
    
    public abstract DBStrategy getDb();
    
    public abstract void setDb(DBStrategy db);
    
    public abstract void initDao(DataSource ds) throws Exception;
    
    public abstract void initDao(String driver, String url, String user, String password);
    
    public abstract String getDriver();
    
    public abstract void setDriver(String driver);
    
    public abstract String getUrl();

    public abstract void setUrl(String url);

    public abstract String getUser();

    public abstract void setUser(String user);

    public abstract String getPwd();

    public abstract void setPwd(String pwd);
}
