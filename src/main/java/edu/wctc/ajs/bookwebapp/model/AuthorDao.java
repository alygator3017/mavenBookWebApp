package edu.wctc.ajs.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alyson
 */
public class AuthorDao implements AuthorDaoStrategy {

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/book";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    //dbstrategy object
    private DBStrategy db = new DBMySqlStrategy();

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER_CLASS, URL, USER, PASSWORD);

        List<Map<String, Object>> rawData = db.findAllRecordsForTable("author", 0);
        List<Author> authors = new ArrayList<>();
        //put raw data into authors
        for (Map rec : rawData) {
            //create author object
            Author author = new Author();
            //convert to integer author id
            Integer id = new Integer(rec.get("author_id").toString());
            author.setAuthorId(id);
            //author name
            String name = rec.get("author_name") == null ? "" : rec.get("author_name").toString();
            author.setAuthorName(name);
            //author date
            Date date = rec.get("date_added") == null ? null : (Date) rec.get("date_added");
            author.setDateAdded(date);
            //add to list
            authors.add(author);
        }

        db.closeConnection();
        return authors;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();

        List<Author> authors = dao.getAuthorList();

        System.out.println(authors);
    }
}
