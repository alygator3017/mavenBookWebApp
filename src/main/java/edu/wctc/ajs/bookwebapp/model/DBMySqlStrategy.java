package edu.wctc.ajs.bookwebapp.model;

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
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alyson
 */
public class DBMySqlStrategy implements DBStrategy {

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
     * @param tableName
     * @param maxRecords - limit records found to first maxRecords or if
     * maxRecords is zero (0) then no limit
     * @return
     * @throws java.sql.SQLException
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

    /**
     * Make sure you open and close a connection when using this method.
     * @param tableName
     * @param columnName
     * @param primaryKey
     * @throws SQLException
     */
    @Override
    public void deleteRecordInTable(String tableName, String columnName, Object primaryKey) throws SQLException {
        if (tableName.isEmpty() || columnName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //need to grab record by author_id but because it is generic we can't actually grab by the author_id
        PreparedStatement deleteRecord = null;
        String deleteQryString = null;
//        if (primaryKey instanceof String) {
//            deleteQryString = "DELETE FROM " + tableName + " WHERE " + columnName + "= '" + primaryKey + "'";
//        }else{
//            deleteQryString = "DELETE FROM " + tableName + " WHERE " + columnName + "= " + primaryKey + "";
//        }
        deleteQryString = "DELETE FROM " + tableName + " WHERE " + columnName + "=?";

        deleteRecord = conn.prepareStatement(deleteQryString);
        if (primaryKey instanceof String) {
            deleteRecord.setString(1, primaryKey.toString());
        }else{
            deleteRecord.setInt(1, Integer.parseInt(primaryKey.toString()));
        }
        System.out.println(deleteRecord);
        deleteRecord.executeUpdate();
        System.out.println("Record has been deleted");

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBStrategy db = new DBMySqlStrategy();

        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin");
        List<Map<String, Object>> rawData = db.findAllRecordsForTable("author", 0);
        System.out.println(rawData);
        db.deleteRecordInTable("author", "author_id", 3);
        rawData = db.findAllRecordsForTable("author", 0);
        db.closeConnection();
        System.out.println(rawData);
    }
}
