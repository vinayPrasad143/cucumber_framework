package co.graphene.util.takeda;

import co.graphene.util.DBConnector;
import co.graphene.util.Debugger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TAKEDA_DATAS {

    DBConnector dbc;
    Connection con;
    List<TAKEDA_DATA_ATU> takeda_data_atuList;

    public TAKEDA_DATAS(){
        dbc = new DBConnector();
        takeda_data_atuList = new ArrayList<TAKEDA_DATA_ATU>();
    }

    public String readATUPageData(String sp){
        try{
            //String sp = "usp_FT_CP_ACT";
            if(!takeda_data_atuList.isEmpty()){
                return "Success";//Aready loaded
            }
            if(con == null) {
                con = dbc.getConnection();
            }
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sp);
            while(rs.next()){
                //Debugger.println("Number of Rows: "+rs.getInt("count"));
                TAKEDA_DATA_ATU takeda_data_atu = new TAKEDA_DATA_ATU();
                takeda_data_atu.setBRAND_NAME(rs.getString("BRAND_NAME"));
                takeda_data_atu.setBUSINESS_UNIT(rs.getString("BUSINESS_UNIT"));
                takeda_data_atu.setCATEGORY_NAME(rs.getString("CATEGORY_NAME"));
                takeda_data_atu.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
                takeda_data_atu.setPERIOD_LABEL(rs.getString("PERIOD_LABEL"));
                takeda_data_atu.setSEGMENT_NAME(rs.getString("SEGMENT_NAME"));
                takeda_data_atu.setSUBCATEGORY_NAME(rs.getString("SUBCATEGORY_NAME"));
                takeda_data_atuList.add(takeda_data_atu);
            }
            if(takeda_data_atuList.size() == 0){
                return "No Datas loaded for the stored procedure :"+sp+", Ensure the SP is correct and connected to the right database";
            }
            Debugger.println("Number of Rows read by Takeda ATU SPOUT: "+takeda_data_atuList.size());
            return "Success";
        }catch(Exception exp){
            return "Exception from reading Takeda ATU Page data from Database:"+exp;
        }
    }

    public List<String> getAllBusinessUnits(){
        List<String> bus = new ArrayList<String>();
        for(int i=0; i<takeda_data_atuList.size(); i++){
            TAKEDA_DATA_ATU takeda_data_atu = takeda_data_atuList.get(i);
            if(!bus.contains(takeda_data_atu.getBUSINESS_UNIT())){
                bus.add(takeda_data_atu.getBUSINESS_UNIT());
            }
        }
        return bus;
    }
    public List<String> getAllBenchMarks(String bu){
        List<String> benchmarks = new ArrayList<String>();
        for(int i=0; i<takeda_data_atuList.size(); i++){
            TAKEDA_DATA_ATU takeda_data_atu = takeda_data_atuList.get(i);
            if(!takeda_data_atu.getBUSINESS_UNIT().equalsIgnoreCase(bu)){
                continue;
            }
            if(!benchmarks.contains(takeda_data_atu.getCATEGORY_NAME())){
                benchmarks.add(takeda_data_atu.getCATEGORY_NAME());
            }
        }
        return benchmarks;
    }
    public List<String> getAllIndications(String bu,String benchmark){
        List<String> indications = new ArrayList<String>();
        for(int i=0; i<takeda_data_atuList.size(); i++){
            TAKEDA_DATA_ATU takeda_data_atu = takeda_data_atuList.get(i);
            if(!takeda_data_atu.getBUSINESS_UNIT().equalsIgnoreCase(bu)){
                continue;
            }
            if(!takeda_data_atu.getCATEGORY_NAME().equalsIgnoreCase(benchmark)){
                continue;
            }
            if(!indications.contains(takeda_data_atu.getSUBCATEGORY_NAME())){
                indications.add(takeda_data_atu.getSUBCATEGORY_NAME());
            }
        }
        return indications;
    }
    public List<String> getAllCountries(String bu,String benchmark){
        List<String> countries = new ArrayList<String>();
        for(int i=0; i<takeda_data_atuList.size(); i++){
            TAKEDA_DATA_ATU takeda_data_atu = takeda_data_atuList.get(i);
            if(!takeda_data_atu.getBUSINESS_UNIT().equalsIgnoreCase(bu)){
                continue;
            }
            if(!takeda_data_atu.getCATEGORY_NAME().equalsIgnoreCase(benchmark)){
                continue;
            }
            if(!countries.contains(takeda_data_atu.getCOUNTRY_NAME())){
                countries.add(takeda_data_atu.getCOUNTRY_NAME());
            }
        }
        return countries;
    }
    public List<String> getAllBrands(String bu,String benchmark,String indication){
        List<String> brands = new ArrayList<String>();
        for(int i=0; i<takeda_data_atuList.size(); i++){
           TAKEDA_DATA_ATU takeda_data_atu = takeda_data_atuList.get(i);
           if(!takeda_data_atu.getBUSINESS_UNIT().equalsIgnoreCase(bu)){
               continue;
           }
            if(!takeda_data_atu.getCATEGORY_NAME().equalsIgnoreCase(benchmark)){
                continue;
            }
            if(!takeda_data_atu.getSUBCATEGORY_NAME().equalsIgnoreCase(indication)){
                continue;
            }
           if(!brands.contains(takeda_data_atu.getBRAND_NAME())){
               brands.add(takeda_data_atu.getBRAND_NAME());
           }
        }
       return brands;
    }

}//end
