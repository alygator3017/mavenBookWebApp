package edu.wctc.ajs.bookwebapp.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Alyson
 */
@Dependent
public class DBMySqlStrategy implements DBStrategy, Serializable {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * Make sure you open and close a connection when using this method. Future
     * optimizations may include changing the return type to an array.
     *
     * @param tableName name of the table being accessed
     * @param maxRecords - limit records found to first maxRecords or if
     * maxRecords is zero (0) then no limit
     * @return a list of maps containing a string as the key and an object as
     * the value.
     * @throws java.sql.SQLException exception
     */
    @Override
    public List<Map<String, Object>> findAllRecordsForTable(String tableName, int maxRecords) throws SQLException {
        String sql;
        if (maxRecords < 1) {
            sql = "SELECT * FROM " + tableName;
        } else {
            //prevents SQL injection which is a risk.
            sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        }

        Statement smt = conn.createStatement();
        ResultSet rs = smt.executeQuery(sql);
        //the result set knows information from the table, thats is called metadata
        //metadata is information about the table.
        ResultSetMetaData rsmd = rs.getMetaData();
        //get the colum count from the metadata
        int columnCount = rsmd.getColumnCount();
        //create list
        List<Map<String, Object>> records = new ArrayList<>();
        //loop over the records using the next statement on rs
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            //columns start at one
            for (int colNo = 1; colNo <= columnCount; colNo++) {
                //data for the column
                Object colData = rs.getObject(colNo);
                //name of column using the metaData
                String colName = rsmd.getColumnName(colNo);
                //add to the map
                record.put(colName, colData);
            }
            //add to the list
            records.add(record);
        }
        //return list of maps
        return records;
    }

    @Override
    public Map<String, Object> findRecordById(String tableName, String primaryKeyColName, Object primaryKeyValue) throws Exception {
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColName + " = ?";

        PreparedStatement smt = null;
        Map<String, Object> record = new HashMap();

        try {
            smt = conn.prepareStatement(sql);
            smt.setObject(1, primaryKeyValue);
            ResultSet rs = smt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                for (int colNo = 1; colNo <= columnCount; colNo++) {
                    //data for the column
                    Object colData = rs.getObject(colNo);
                    //name of column using the metaData
                    String colName = rsmd.getColumnName(colNo);
                    //add to the map
                    record.put(colName, colData);
                }
            }
        } catch (SQLException ex) {
            //fix this later add custom exception
            throw new Exception();
        } finally {
            smt.close();
            conn.close();
        }
        return record;

    }

    /**
     * Make sure you open and close a connection when using this method. Method
     * deletes a single record in a table. Decision on the key's value which has
     * been brought in as an object is decided through an instanceOf statement.
     * Only set up currrently for String and Int. Returns int to show how many
     * records have been deleted.
     *
     * @param tableName name of the table being accessed
     * @param columnName name of the primary key column
     * @param primaryKey value of the primary key
     * @throws SQLException exception
     */
    @Override
    public int deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException {
        if (tableName.isEmpty() || columnName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //need to grab record by author_id but because it is generic we can't actually grab by the author_id
        PreparedStatement deleteRecord = null;
        String deleteQryString = null;
        deleteQryString = "DELETE FROM " + tableName + " WHERE " + columnName + "=?";

        deleteRecord = conn.prepareStatement(deleteQryString);
//        if (primaryKey instanceof String) {
//            deleteRecord.setString(1, primaryKey.toString());
//        } else {
//            deleteRecord.setInt(1, Integer.parseInt(primaryKey.toString()));
//        }
        deleteRecord.setObject(1, primaryKey);
        int result = deleteRecord.executeUpdate();
        return result;

    }

    /**
     * Make sure you open and close a connection when using this method.
     *
     * @param tableName name of the table being accessed
     * @param colNames name of the columns from the table to apply values to
     * @param colData values to be added to the columns in the table. Values
     * must match up with the column names.
     * @return number of records created
     */
    @Override
    public int createNewRecordInTable(String tableName, List colNames, List colData) {
        PreparedStatement createRecord = null;
        int result;
        try {
            createRecord = buildInsertStatement(conn, tableName, colNames);
            int colDataSize = colData.size();
            for (int i = 0; i < colDataSize; i++) {
                Object obj = colData.get(i);
                createRecord.setObject((i + 1), obj);
            }

            result = createRecord.executeUpdate();
        } catch (SQLException sql) {
            System.out.println(sql.getMessage() + " error in dbMySqlStrategy create record");
            return result = 0;
        } finally {
            try {
                createRecord.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error in closing of record in dbMySqlStrategy in create record");
            }

        }
        return result;
    }

    /**
     * Make sure you open and close a connection when using this method.
     *
     * @param tableName name of the table being accessed
     * @param colNames names of the columns being updated in the table
     * @param colValues values of the columns being updated. Must match up in
     * order to the col names to be updated.
     * @param pkColName primary key column name.
     * @param pkValue primary key value for specific record being updated.
     * @return number of records updated
     * @throws SQLException exception
     */
    @Override
    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws SQLException {
        //using the list of column names to find the current values for multiple
        // column update
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        // do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj;

            // set params for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // and finally set param for wehere value
            pstmt.setObject(index, pkValue);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } // end try
        } // end finally

        return recsUpdated;
    }

    /**
     * Builds a java.sql.PreparedStatement for an sql update using only one
     * where clause test
     *
     * @param conn - a JDBC <code>Connection</code> object
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be updated.
     * @param whereField - a <code>String</code> representing the field name for
     * the search criteria.
     * @return java.sql.PreparedStatement
     * @throws SQLException
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
            List colDescriptors, String whereField)
            throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    /**
     * Builds a java.sql.PreparedStatement for an sql insert
     *
     * @param conn - a valid connection
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be inserted.
     * @return java.sql.PreparedStatement
     * @throws DataAccessException
     */
    private PreparedStatement buildInsertStatement(Connection conn, String tableName, List colDescriptors)
            throws SQLException {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        (sql.append(tableName)).append(" (");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(", ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ") VALUES (");
        for (int j = 0; j < colDescriptors.size(); j++) {
            sql.append("?, ");
        }
        final String finalSQL = (sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ")";
        //System.out.println(finalSQL);
        PreparedStatement psmt = null;
        try {
            psmt = conn.prepareStatement(finalSQL);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e.getCause());
        }
        return psmt;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        DBStrategy db = new DBMySqlStrategy();

        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin");
        List<Map<String, Object>> rawData = db.findAllRecordsForTable("author", 0);
        System.out.println(rawData);
//        int result = db.deleteRecordById("author", "author_id", 8);
//        System.out.println(result);;
        db.closeConnection();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin");
        List data = new ArrayList(Arrays.asList("John Green",
                "2007-01-02"));
        List colNames = new ArrayList(Arrays.asList("author_name", "date_added"));
        int result = db.createNewRecordInTable("author", colNames, data);
//        List<String> colNames = Arrays.asList("author_name", "date_added");
//        List<Object> colValues = Arrays.asList("My Little Pony Rainbow Dash", "1993-10-31");
//        int result = db.updateRecordById("author", colNames, colValues, "author_id", 2);
//        db.closeConnection();
//        db.openConnection("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root", "admin");
        db.closeConnection();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin");
        rawData = db.findAllRecordsForTable("author", 0);

        System.out.println(rawData);
        //Map<String, Object> result = db.findRecordById("author", "author_id", "1");
        db.closeConnection();
        System.out.println(result);

    }
}
