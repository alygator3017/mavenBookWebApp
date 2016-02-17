package edu.wctc.ajs.bookwebapp.model;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alyson
 */
public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException;

    public abstract void closeConnection() throws SQLException;
    
    public abstract List<Map<String, Object>> findAllRecordsForTable(String tableName, int maxRecords) throws SQLException;
    
    public abstract void deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException;
    
    public abstract void createNewRecordInTable(String tableName, Object[] recordData) throws SQLException;
}
