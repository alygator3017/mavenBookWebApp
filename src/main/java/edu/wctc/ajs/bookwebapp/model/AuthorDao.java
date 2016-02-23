package edu.wctc.ajs.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Alyson
 */
@SessionScoped
public class AuthorDao implements AuthorDaoStrategy, Serializable{

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/book";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    //table constants
    private static final String TABLE_NAME = "author";
    private static final String PRIMARY_KEY_COLUMN_NAME = "author_id";
    private static final String AUTHOR_NAME_COL_NAME = "author_name";
    private static final String DATE_ADDED_COL_NAME = "date_added";
    //dbstrategy object
    //live object
    @Inject
    private DBStrategy db;
    //testing object
    //private DBStrategy db = new DBMySqlStrategy();
    

    public AuthorDao() {
    }

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }

    
    
    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
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

    /**
     *
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public int deleteAuthorById(Object authorId) throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER_CLASS, URL, USER, PASSWORD);
        Object primaryKey = authorId;

        int result = db.deleteRecordById(TABLE_NAME, PRIMARY_KEY_COLUMN_NAME, primaryKey);
        db.closeConnection();
        return result;

    }

    /**
     *
     * @param tableName
     * @param author
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public int createNewAuthor(Author author) throws ClassNotFoundException, SQLException {
        
        
        db.openConnection(DRIVER_CLASS, URL, USER, PASSWORD);
        DateFormat df = new SimpleDateFormat("yyyy.M.d");
        List recordData = new ArrayList(Arrays.asList(author.getAuthorName(), df.format(author.getDateAdded())));
        List colNames = new ArrayList(Arrays.asList(AUTHOR_NAME_COL_NAME,DATE_ADDED_COL_NAME));
        int result = db.createNewRecordInTable(TABLE_NAME,colNames,recordData);
        db.closeConnection();
        return result;
    }

    /**
     *
     * @param tableName
     * @param colNamesToBeUpdated
     * @param values
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public int updateAuthorById(List<String> colNamesToBeUpdated, List<Object> values, int authorId) throws ClassNotFoundException, SQLException  {
        db.openConnection(DRIVER_CLASS, URL, USER, PASSWORD);
        int result = db.updateRecordById(TABLE_NAME, colNamesToBeUpdated, values, PRIMARY_KEY_COLUMN_NAME, authorId);
        db.closeConnection();
        return result;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();

        List<Author> authors = dao.getAuthorList();

        System.out.println(authors);

        int deleteComplete = dao.deleteAuthorById(10);

        System.out.println(deleteComplete);
        
//        Author ranAuthor = new Author();
//        ranAuthor.setAuthorName("Henry Nobely");
//        ranAuthor.setDateAdded(new Date());
//        int result = dao.createNewAuthor(ranAuthor);
//        System.out.println(result);
        authors = dao.getAuthorList();
        System.out.println(authors);
        List<String> colNames = new ArrayList<>(Arrays.asList("author_id"));
        List<Object> colValues = new ArrayList<>(Arrays.asList(4));
        int results = dao.updateAuthorById(colNames, colValues, 2);
        System.out.println(results);
        authors = dao.getAuthorList();
        System.out.println(authors);
    }

    
}
