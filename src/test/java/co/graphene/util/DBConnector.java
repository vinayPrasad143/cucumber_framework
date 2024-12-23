package co.graphene.util;

import co.graphene.config.AppConfig;
import org.apache.commons.pool.ObjectPool;

import java.sql.*;

public class DBConnector {

    private String user_name = "";
    private String password = "";
    private String con_string = "";
    private String driver = "";
    private Connection con;

    private static ObjectPool pool;

    public String getDriver(){
        return this.driver;
    }

    public static void main(String[] args){
        DBConnector dbc = new DBConnector();
        dbc.setConString();
    }

    //Sets Connection String
    public boolean setConString(){

        try {
            this.user_name = AppConfig.getPropertyValue("DATABASE_USER");
            this.password = AppConfig.getPropertyValue("DATABASE_PASSWORD");
            String host = AppConfig.getPropertyValue("DATABASE_HOST");
            String port = AppConfig.getPropertyValue("DATABASE_PORT");
            String databaseName = AppConfig.getPropertyValue("DATABASE_NAME");

            this.con_string = "jdbc:sqlserver://"+host+":"+port+";DatabaseName="+databaseName;
            //this.con_string = "dbc:sqlserver://" + host + "\\\\sqlexpress";
            Debugger.println("Connection String..." + con_string);
            con = DriverManager.getConnection(con_string, user_name, password);
            if(con != null){
                DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
                //Debugger.println("Driver name: " + dm.getDriverName());
                //Debugger.println("Driver version: " + dm.getDriverVersion());
                //Debugger.println("Product name: " + dm.getDatabaseProductName());
                Debugger.println("Product version: " + dm.getDatabaseProductVersion());
            }

            return true;
        }catch(Exception exp){
            Debugger.println("DB Connection failure :"+exp);
            return false;
        }
    }
    public Connection getConnection() throws SQLException {
        try {
            if(this.con_string  == null || this.con_string.equals("")){
                if(!setConString()){
                    return null;
                }
            }
            //Statement stmt = con.createStatement();
            return con;
        } catch (Exception exp) {
            Debugger.println("ConnectionHelper: getConnection()" + exp);
            return null;
        }
    }
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if(con != null){
                    con.close();
                }
            }catch (Exception exp) {
                //Debugger.println("ConnectionHelper:close:Failed to return the connection to the pool"+ exp);
            }
        }
    }

}//end
