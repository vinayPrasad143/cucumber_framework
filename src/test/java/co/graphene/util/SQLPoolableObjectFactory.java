package co.graphene.util;
import java.sql.DriverManager;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class SQLPoolableObjectFactory extends BasePoolableObjectFactory {

    private String driver;
    private String url;
    private String user;
    private String password;

    public SQLPoolableObjectFactory(String driver,String url,String user, String password) {

        this.driver = driver;
        this.url	= url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Object makeObject() throws Exception {
        try{
            //SNSDebug.println("SQLPoolableObjectFactory:Creating Connection...\n"+this.url+"\nusername"+this.user+"/"+this.password);
            Class.forName(driver).newInstance();
            //url = url + "?autoReconnect=true";
            return DriverManager.getConnection(this.url, this.user, this.password);
        }catch(Exception exp){
            Debugger.println("SQLPoolableObjectFactory:makeObject:Exception\n"+exp);
            return null;
        }
    }//end

}//end
