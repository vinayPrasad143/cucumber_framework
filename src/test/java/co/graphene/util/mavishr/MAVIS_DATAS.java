package co.graphene.util.mavishr;

import co.graphene.util.DBConnector;
import co.graphene.util.Debugger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MAVIS_DATAS {
    DBConnector dbc;
    Connection con;
    public static List<MavisDeepDive> positiveThemes;
    public static List<MavisDeepDive> negativeThemes;
    public static List<KeyValue> industryOptions = new ArrayList<KeyValue>();//Category
    public static List<KeyValue> organizationOptions = new ArrayList<KeyValue>();
    public static List<KeyValue> countryOptions = new ArrayList<KeyValue>();
    public static List<KeyValue> periodOptions = new ArrayList<KeyValue>();
    public static List<MavisMenu> mavisMenus;
    public static MavisHeaderFooter mavisHeaderFooter;

    public static List<DR_SummaryView> drSummaryViewsPositive;

    public MAVIS_DATAS(){
        dbc = new DBConnector();
        positiveThemes = new ArrayList<MavisDeepDive>();
        negativeThemes = new ArrayList<MavisDeepDive>();
        mavisMenus     = new ArrayList<MavisMenu>();
        mavisHeaderFooter = new MavisHeaderFooter();
        drSummaryViewsPositive = new ArrayList<DR_SummaryView>();
    }
    public List<KeyValue> getIndustryOptions(){
        return industryOptions;
    }
    public List<KeyValue> getOrganizationOptions(){
        return organizationOptions;
    }
    public List<KeyValue> getCountryOptions(){
        return countryOptions;
    }
    public List<KeyValue> getPeriodOptions(){
        return periodOptions;
    }
    public List<MavisDeepDive> getPositiveThemes(){
        return positiveThemes;
    }
    public int noOfPositiveThemes(){
        return positiveThemes.size();
    }
    public List<MavisMenu> getMavisMenus(){ return mavisMenus;}

    public static MavisHeaderFooter getMavisHeaderFooter() {
        return mavisHeaderFooter;
    }

    public String getPositiveThemePercentFor(String themeName){
        String score = "-1";
        String theme = "";
        for(int i=0; i<positiveThemes.size(); i++) {
            theme = positiveThemes.get(i).getThemeName();
            if(theme.toString().equalsIgnoreCase(themeName.toString())){
                score = positiveThemes.get(i).getThemePercent();
                positiveThemes.remove(i);//Removing for easy search for next item
                break;
            }
        }
        if(score.equalsIgnoreCase("-1")) {
            return themeName + " not present in DB";
        }else {
            return score;
        }
    }
    public int noOfNegativeThemes(){
        return negativeThemes.size();
    }
    public String getNegativeThemePercentFor(String themeName){
        for(int i=0; i<negativeThemes.size(); i++) {
            //Debugger.println("DB Positive Theme: "+positiveThemes.get(i).getThemeName()+"..."+positiveThemes.get(i).getThemePercent());
            if(negativeThemes.get(i).getThemeName().equalsIgnoreCase(themeName)){
                return negativeThemes.get(i).getThemePercent();
            }
        }
        return themeName+" not present in DB";
    }
    public List<MavisDeepDive> getNegativeThemes(){
        return negativeThemes;
    }
    public String readDeepDivePositiveThemeData(String category,String brand,String country,String time_label){
        try{
            positiveThemes.clear();
            if(con == null) {
                con = dbc.getConnection();
            }
            String query = "select DISTINCT THEME,THEME_PERC from FT_DeepDiveDefault \n" +
                    "WHERE TIME_LABEL='"+time_label+"' AND BRAND_KEY=(SELECT BRAND_KEY from DM_BRAND where DISPLAY_NAME='"+brand+"') \n" +
                    "AND COUNTRY_KEY=(SELECT COUNTRY_KEY from DM_COUNTRY where COUNTRY_NAME='"+country+"') \n" +
                    "AND CATEGORY_KEY=(SELECT CATEGORY_KEY from DM_CATEGORY where DISPLAY_NAME='"+category+"') \n" +
                    "AND channel_key=1 AND SENTIMENT_KEY=1 order by theme_perc desc";
            //Debugger.println("DD: Positive Themes Query: "+query);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            double percent = 0;
            while(rs.next()){
                //Debugger.println("Number of Rows: "+rs.getInt("count"));
                MavisDeepDive mavisDeepDive = new MavisDeepDive();
                mavisDeepDive.setThemeName(rs.getString("THEME"));
                percent = rs.getFloat("THEME_PERC");
                percent = Math.round(percent*Math.pow(10,1))/Math.pow(10,1);
                mavisDeepDive.setThemePercent(percent+"");
                positiveThemes.add(mavisDeepDive);
            }
            return "Success";
        }catch(Exception exp){
            return "Exception from reading readDeepDiveDataFor:"+exp;
        }
    }
    public String readDeepDiveNegativeThemeData(String category,String brand,String country,String time_label){
        try{
            negativeThemes.clear();
            if(con == null) {
                con = dbc.getConnection();
            }
            String query = "select DISTINCT THEME,THEME_PERC from FT_DeepDiveDefault \n" +
                    "WHERE TIME_LABEL='"+time_label+"' AND BRAND_KEY=(SELECT BRAND_KEY from DM_BRAND where DISPLAY_NAME='"+brand+"') \n" +
                    "AND COUNTRY_KEY=(SELECT COUNTRY_KEY from DM_COUNTRY where COUNTRY_NAME='"+country+"') \n" +
                    "AND CATEGORY_KEY=(SELECT CATEGORY_KEY from DM_CATEGORY where DISPLAY_NAME='"+category+"') \n" +
                    "AND channel_key=1 AND SENTIMENT_KEY=2 order by theme_perc desc";
           // Debugger.println("DD: Negative Themes Query: "+query);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            double percent = 0;
            while(rs.next()){
                //Debugger.println("Number of Rows: "+rs.getInt("count"));
                MavisDeepDive mavisDeepDive = new MavisDeepDive();
                mavisDeepDive.setThemeName(rs.getString("THEME"));
                percent = rs.getFloat("THEME_PERC");
                percent = Math.round(percent*Math.pow(10,1))/Math.pow(10,1);
                mavisDeepDive.setThemePercent(percent+"");
                negativeThemes.add(mavisDeepDive);
            }
            return "Success";
        }catch(Exception exp){
            return "Exception from reading readDeepDiveDataFor:"+exp;
        }
    }
    public String readLatestDataTransformDateForDeepDive(){
        try{
            if(con == null) {
                con = dbc.getConnection();
            }
            //This Query has to be re-checked
            String query = "select DISTINCT MONTH,QUARTER,YEAR from FT_DeepDiveDefault Order by QUARTER desc";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return rs.getString("MONTH")+" "+rs.getInt("YEAR");
            }
            return "MM YYYY";
        }catch(Exception exp){
            return "Exception from readLatestDataTransformDateForDeepDive:"+exp;
        }
    }
    public List<String> readFilterOptionsFromMaster(String masterName){
        List<String> filterOptions = new ArrayList<String>();
        try{
            if(con == null) {
                con = dbc.getConnection();
            }
            //This Query has to be re-checked
            String query = "";
            Statement stmt = con.createStatement();
            if (masterName.equalsIgnoreCase("Category")){//Industry
                query = "SELECT CATEGORY_KEY,DISPLAY_NAME from DM_Category";
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    KeyValue kv = new KeyValue();
                    kv.setKey(rs.getInt("CATEGORY_KEY"));
                    kv.setValue(rs.getString("DISPLAY_NAME"));
                    filterOptions.add(kv.getValue());
                    industryOptions.add(kv);
                }
            }else if (masterName.equalsIgnoreCase("Brand")){
                query = "SELECT BRAND_KEY,DISPLAY_NAME from DM_BRAND";
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    KeyValue kv = new KeyValue();
                    kv.setKey(rs.getInt("BRAND_KEY"));
                    kv.setValue(rs.getString("DISPLAY_NAME"));
                    if(kv.getKey() != 2) {//Bench mark
                        filterOptions.add(kv.getValue());
                    }
                    organizationOptions.add(kv);
                }
                //return organizationOptions;
            }else if (masterName.equalsIgnoreCase("Country")){
                query = "SELECT COUNTRY_KEY,COUNTRY_NAME from DM_Country";
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    KeyValue kv = new KeyValue();
                    kv.setKey(rs.getInt("COUNTRY_KEY"));
                    kv.setValue(rs.getString("COUNTRY_NAME"));
                    filterOptions.add(kv.getValue());
                    countryOptions.add(kv);
                }
                //return countryOptions;
            }else if (masterName.equalsIgnoreCase("Period")){
                //Please note that if different dates expected for DeepDive and Drivers of Rating,
                //then the query also may need to change.
                //select DISTINCT MONTH,QUARTER,YEAR from FT_ECommDriversDefault Order by QUARTER desc
                query = "select DISTINCT MONTH,QUARTER,YEAR,PERIOD_KEY,TIME_LABEL from FT_DeepDiveDefault Order by QUARTER desc";
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    KeyValue kv = new KeyValue();
                    kv.setKey(rs.getInt("PERIOD_KEY"));
                    kv.setValue(rs.getString("TIME_LABEL"));
                    filterOptions.add(kv.getValue());
                    periodOptions.add(kv);
                }
            }
            return filterOptions;
        }catch(Exception exp){
            Debugger.println("Exception from readFilterOptionsFromMaster:"+exp);
            return filterOptions;
        }
    }
    public String readSidebarMenuPagesConfiguration(){
        try{
            if(con == null) {
                con = dbc.getConnection();
            }
            //This Query has to be re-checked
            String query = "select * from UI_FrameworkConfigPage where IS_ACTIVE=1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                MavisMenu mvm = new MavisMenu();
                mvm.setPage_name(rs.getString("PAGE_NAME"));
                mvm.setShort_name(rs.getString("SHORT_NAME"));
                mvm.setPage_order(rs.getInt("PAGE_ORDER"));
                mavisMenus.add(mvm);
            }
            return "Success";
        }catch(Exception exp){
            return "Exception from readSidebarMenuPagesConfiguration:"+exp;
        }
    }
    public String readHeaderFooterAndTitle(){
        try{
            if(con == null) {
                con = dbc.getConnection();
            }
            //This Query has to be re-checked
            String query = "select * from UI_FrameworkConfigApp";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
               mavisHeaderFooter.setHeaderLogo(rs.getString("LOGO"));
               mavisHeaderFooter.setFaviconImg(rs.getString("FAVICON"));
               mavisHeaderFooter.setBrowserTitle(rs.getString("TITLE"));
               mavisHeaderFooter.setFooterLogo(rs.getString("FOOTER_LOGO"));
               mavisHeaderFooter.setCopyRight(rs.getInt("COPYRIGHT"));
            }
            return "Success";
        }catch(Exception exp){
            return "Exception from readHeaderFooterAndTitle:"+exp;
        }
    }
    //DRIVERS OF RATING
    public int noOfEmployersInDriversOfRating(){
        return drSummaryViewsPositive.size();
    }
    public String getEmployerScoreFor(String employerName){
        for(int i=0; i<drSummaryViewsPositive.size(); i++) {
            //Debugger.println("DB Positive Theme: "+positiveThemes.get(i).getThemeName()+"..."+positiveThemes.get(i).getThemePercent());
            if(drSummaryViewsPositive.get(i).getEmployerName().equalsIgnoreCase(employerName)){
                return drSummaryViewsPositive.get(i).getEmployerScore()+"";
            }
        }
        return employerName+" not present in DB";
    }
    public String getPositiveDriverScoreFor(String employerName,String driverName){
        for(int i=0; i<drSummaryViewsPositive.size(); i++) {
            if(drSummaryViewsPositive.get(i).getEmployerName().equalsIgnoreCase(employerName) &&
                    drSummaryViewsPositive.get(i).getDriverName().equalsIgnoreCase(driverName) ){
                return drSummaryViewsPositive.get(i).getDriverScore()+"";
            }
        }
        return employerName+" and "+driverName+" combination not present in DB";
    }
    public String readLatestDataTransformDateForDriversOfRating(){
        try{
            if(con == null) {
                con = dbc.getConnection();
            }
            //This Query has to be re-checked
            String query = "select DISTINCT MONTH,QUARTER,YEAR from FT_ECommDriversDefault Order by QUARTER desc";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return rs.getString("MONTH")+" "+rs.getInt("YEAR");
            }
            return "MM YYYY";
        }catch(Exception exp){
            return "Exception from readLatestDataTransformDateForDriversOfRating:"+exp;
        }
    }

    public String getEmployerDriverSummaryViewPositiveScore(int category_key, int country_key , String time_label){
        try {
            if (con == null) {
                con = dbc.getConnection();
            }
            drSummaryViewsPositive.clear();
            String query = "SELECT ECDD.BRAND_KEY,ECDD.DRIVER_SCORE,ECDD.EQUITY_SCORE,ECDD.CBI,DMB.DISPLAY_NAME,DMD.DRIVER_NAME";
            query += " FROM FT_ECommDriversDefault ECDD,DM_Brand DMB,DM_DRIVER DMD";
            query += " WHERE ECDD.BRAND_KEY=DMB.BRAND_KEY AND ECDD.DRIVER_KEY=DMD.DRIVER_KEY";
            query += " AND ECDD.CATEGORY_KEY=" + category_key + " AND ECDD.COUNTRY_KEY=" + country_key + " AND TIME_LABEL='" + time_label + "' AND ECDD.SENTIMENT_KEY=1";
            query += " GROUP BY ECDD.BRAND_KEY,ECDD.DRIVER_SCORE,ECDD.EQUITY_SCORE,ECDD.CBI,DMB.DISPLAY_NAME,DMD.DRIVER_NAME";
            query += " ORDER BY ECDD.CBI DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DR_SummaryView drSummaryView = new DR_SummaryView();
                drSummaryView.setEmployerName(rs.getString("DISPLAY_NAME"));
                drSummaryView.setEmployerScore(rs.getInt("CBI"));
                drSummaryView.setDriverName(rs.getString("DRIVER_NAME"));
                drSummaryView.setDriverScore(rs.getInt("DRIVER_SCORE"));
                drSummaryViewsPositive.add(drSummaryView);
            }//while
            return "Success";
        }catch(Exception exp){
            Debugger.println("Exception in Reading getEmployerDriverSummaryViewPositiveScore "+exp);
            return "Exception in Reading getEmployerDriverSummaryViewPositiveScore "+exp;
        }
    }
    public String getEmployerDriverSummaryViewNegativeScore(int category_key, int country_key , String time_label){
        try {
            if (con == null) {
                con = dbc.getConnection();
            }
            drSummaryViewsPositive.clear();
            String query = "SELECT ECDD.BRAND_KEY,ECDD.DRIVER_SCORE,ECDD.EQUITY_SCORE,ECDD.CBI,DMB.DISPLAY_NAME,DMD.DRIVER_NAME";
            query += " FROM FT_ECommDriversDefault ECDD,DM_Brand DMB,DM_DRIVER DMD";
            query += " WHERE ECDD.BRAND_KEY=DMB.BRAND_KEY AND ECDD.DRIVER_KEY=DMD.DRIVER_KEY";
            query += " AND ECDD.CATEGORY_KEY=" + category_key + " AND ECDD.COUNTRY_KEY=" + country_key + " AND TIME_LABEL='" + time_label + "' AND ECDD.SENTIMENT_KEY=2";
            query += " GROUP BY ECDD.BRAND_KEY,ECDD.DRIVER_SCORE,ECDD.EQUITY_SCORE,ECDD.CBI,DMB.DISPLAY_NAME,DMD.DRIVER_NAME";
            query += " ORDER BY ECDD.CBI DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DR_SummaryView drSummaryView = new DR_SummaryView();
                drSummaryView.setEmployerName(rs.getString("DISPLAY_NAME"));
                drSummaryView.setEmployerScore(rs.getInt("CBI"));
                drSummaryView.setDriverName(rs.getString("DRIVER_NAME"));
                drSummaryView.setDriverScore(rs.getInt("DRIVER_SCORE"));
                drSummaryViewsPositive.add(drSummaryView);
            }//while
            return "Success";
        }catch(Exception exp){
            Debugger.println("Exception in Reading getEmployerDriverSummaryViewNegativeScore "+exp);
            return "Exception in Reading getEmployerDriverSummaryViewNegativeScore "+exp;
        }
    }
}//end
