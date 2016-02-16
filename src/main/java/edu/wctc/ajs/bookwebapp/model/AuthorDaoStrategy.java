package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alyson
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
}
