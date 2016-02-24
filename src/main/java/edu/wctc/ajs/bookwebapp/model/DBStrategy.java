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
    
    public abstract Map<String, Object> findRecordById(String tableName, String primaryKeyColName, Object primaryKeyValue) throws Exception;
    
    public abstract int deleteRecordById(String tableName, String columnName, Object primaryKey) throws SQLException;
    
    public abstract int createNewRecordInTable(String tableName, List colNames, List colData) throws SQLException;
    
    public abstract int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws SQLException;
}
