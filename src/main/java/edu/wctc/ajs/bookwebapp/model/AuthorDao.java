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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author Alyson
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {

    //table constants
    private static final String TABLE_NAME = "author";
    private static final String PRIMARY_KEY_COLUMN_NAME = "author_id";
    private static final String AUTHOR_NAME_COL_NAME = "author_name";
    private static final String DATE_ADDED_COL_NAME = "date_added";

    //dbstrategy object
    //live object
    //@Inject
    //private DBStrategy db;
    //testing object
    private DBStrategy db = new DBMySqlStrategy();
    private DataSource ds;

    private String driver;
    private String url;
    private String user;
    private String password;

    public AuthorDao() {
    }

    @Override
    public void initDao(DataSource ds) throws Exception {
        setDs(ds);
    }

    @Override
    public void initDao(String driver, String url, String user, String password) {
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(password);
    }

    @Override
    public DBStrategy getDb() {
        return db;
    }

    @Override
    public void setDb(DBStrategy db) {
        this.db = db;
    }

    /**
     * Method opens and closes connection to retrieve full list of authors
     *
     * @return List of authors in database
     * @throws ClassNotFoundException exception
     * @throws SQLException exception
     */
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        if (ds == null) {
            db.openConnection(driver, url, user, password);
        } else {
            try {
                db.openConnection(ds);
            } catch (Exception ex) {
                Logger.getLogger(AuthorDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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

    @Override
    public Author getAuthorById(Object id) throws ClassNotFoundException, SQLException, Exception {
        db.openConnection(driver, url, user, password);
        Map<String, Object> rawData = db.findRecordById(TABLE_NAME, PRIMARY_KEY_COLUMN_NAME, id);
        Author author = new Author();
        Integer authorId = new Integer(rawData.get("author_id").toString());
        author.setAuthorId(authorId);
        String name = rawData.get("author_name") == null ? "" : rawData.get("author_name").toString();
        author.setAuthorName(name);
        Date date = rawData.get("date_added") == null ? null : (Date) rawData.get("date_added");
        author.setDateAdded(date);
        db.closeConnection();
        return author;

    }

    /**
     * Deletes an author by specified ID returning the number of records delete.
     * Opens and closes connection within the method.
     *
     * @param authorId id of the author to be deleted
     * @return number of records deleted
     * @throws ClassNotFoundException exception
     * @throws SQLException exception
     */
    @Override
    public int deleteAuthorById(Object authorId) throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, password);
        Object primaryKey = authorId;

        int result = db.deleteRecordById(TABLE_NAME, PRIMARY_KEY_COLUMN_NAME, primaryKey);
        db.closeConnection();
        return result;

    }

    /**
     * Opens and closes connection within the method. Adds a new author to the
     * database.
     *
     * @param author object to be added to database
     * @return number of records added
     * @throws ClassNotFoundException exception
     * @throws SQLException exception.
     */
    @Override
    public int createNewAuthor(Author author) throws ClassNotFoundException, SQLException {

        db.openConnection(driver, url, user, password);
        DateFormat df = new SimpleDateFormat("yyyy.M.d");
        List recordData = new ArrayList(Arrays.asList(author.getAuthorName(), df.format(author.getDateAdded())));
        List colNames = new ArrayList(Arrays.asList(AUTHOR_NAME_COL_NAME, DATE_ADDED_COL_NAME));
        int result = db.createNewRecordInTable(TABLE_NAME, colNames, recordData);
        db.closeConnection();
        return result;
    }

    /**
     * Open and close connection within the method. updates a current record in
     * the database with the values specified.
     *
     * @param colNamesToBeUpdated names of the columns that need to be updated
     * @param values values of the columns being updated in order. Must be in
     * the same order as the column names specified
     * @param authorId id of the author to be updated
     * @return number of records updated in database.
     * @throws ClassNotFoundException exception
     * @throws SQLException exception
     */
    @Override
    public int updateAuthorById(Object currAuthorId, Object authorId, Object authorName, Object dateAdded) throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, password);
        List<String> colNames = new ArrayList<>(Arrays.asList(PRIMARY_KEY_COLUMN_NAME, AUTHOR_NAME_COL_NAME, DATE_ADDED_COL_NAME));
        List<Object> colValues = new ArrayList<>(Arrays.asList(authorId, authorName, dateAdded));
        int result = db.updateRecordById(TABLE_NAME, colNames, colValues, PRIMARY_KEY_COLUMN_NAME, currAuthorId);
        db.closeConnection();
        return result;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        AuthorDaoStrategy dao = new AuthorDao();

        List<Author> authors = dao.getAuthorList();

        System.out.println(authors);

//        int deleteComplete = dao.deleteAuthorById(10);
//
//        System.out.println(deleteComplete);
//        Author ranAuthor = new Author();
//        ranAuthor.setAuthorName("Henry Nobely");
//        ranAuthor.setDateAdded(new Date());
//        int result = dao.createNewAuthor(ranAuthor);
//        System.out.println(result);
        authors = dao.getAuthorList();
        System.out.println(authors);
//        List<String> colNames = new ArrayList<>(Arrays.asList("author_id"));
//        List<Object> colValues = new ArrayList<>(Arrays.asList(4));
//        int results = dao.updateAuthorById(colNames, colValues, 2);
//        System.out.println(results);
        Author author = dao.getAuthorById(1);
        System.out.println(author);
        authors = dao.getAuthorList();
        System.out.println(authors);
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPwd() {
        return password;
    }

    @Override
    public void setPwd(String password) {
        this.password = password;
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

}
