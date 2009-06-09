/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.junit.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.model.catalogs.*;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;

/**
 *
 * @author serch
 */
public class TestCatalog
{

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.setUseDB(false);
        SWBPlatform.createInstance(null);
    //SWBPlatform.
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    //@Test
    public void lock() throws FileNotFoundException
    {
        System.out.println(this);
        System.out.println("Memory: total:" + Runtime.getRuntime().totalMemory() + " Libre:" + Runtime.getRuntime().freeMemory() + " ocupada:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        //SWBPlatform.getSemanticMgr().getModel("swbcatalogs").write(new FileOutputStream("/model.owl"));
        SWBPlatform.getSemanticMgr().getModel("swbcatalogs").write(new FileOutputStream("/catalogData.nt"), "N-TRIPLE");
    }

    //@Test
    public void loadCatalogs() throws SQLException
    {
        Connection con = SWBUtils.DB.getConnection("catalogs", "Conexion Carga CP's");
        PreparedStatement ps = con.prepareStatement("select pais_clave, pais from pais");
        ResultSet rs = ps.executeQuery();
        //SemanticModel model = SWBPlatform.getSemanticMgr().createModel("catalogs", "http://www.semanticwb.org/catalogs");
        //SWBModel model = WebSite.createWebSite("swbcatalogs", "http://www.semanticwb.org/SWBCatalogs");
        SWBModel model = Catalogs.createCatalogs("swbcatalogs", "http://catalogs.semanticwb.org/");

        while (rs.next())
        {

            //String uri = model.getObjectUri(rs.getString(1), Country.sclass);

            //System.out.println("*******************>"+uri);
            //Country country = (Country) model.createGenericObject(uri, Country.sclass);
            Country country = Country.createCountry(rs.getString(1), model);
            country.setCountryCode(rs.getString(1));
            country.setName(rs.getString(2));
        }
        rs.close();
        ps.close();
        ps = con.prepareStatement("select id_entidad_federativa2, entidad_federativa from entidad_relacion order by id_entidad_federativa2 ");
        rs = ps.executeQuery();
        //Country country = (Country) model.getGenericObject(model.getObjectUri("MX", Country.sclass),Country.sclass);
        Country country = Country.getCountry("MX", model);
        PreparedStatement ps1, ps2, ps3;
        ResultSet rs1, rs2, rs3;
        ps1 = con.prepareStatement("select id_municipio2, municipio from municipio_delegacion_rel where id_entidad_federativa2=? order by id_municipio2");
        ps2 = con.prepareStatement("select d_codigo, d_asenta from cat_geo_codigospostales where c_estado = ? and c_mnpio  =  ? order by d_codigo");
        while (rs.next())
        {
            //String uri = model.getObjectUri("MX_"+rs.getString(1), State.sclass);
            //System.out.println("*******************>"+uri);
            //State state = (State) model.createGenericObject(uri, State.sclass);
            String strCountry = "MX_" + rs.getString(1);
            State state = State.createState(strCountry, model);
            state.setStateCode(rs.getString(1));
            state.setName(rs.getString(2));
            state.setCountry(country);
            //state.setStateBelongsTo(country);
            ps1.setInt(1, rs.getInt(1));
            rs1 = ps1.executeQuery();
            while (rs1.next())
            {
                //String uri1 = model.getObjectUri("MX_"+rs1.getString(1), County.sclass);
                //System.out.println("*******************>"+uri1);
                //County county = (County) model.createGenericObject(uri1, County.sclass);
                County county = County.createCounty(strCountry+"_" + rs1.getString(1), model);
                county.setCountyCode(rs1.getString(1));
                county.setName(rs1.getString(2));
                county.setState(state);
                ps2.setInt(1, rs.getInt(1));
                ps2.setInt(2, rs1.getInt(1));
                rs2 = ps2.executeQuery();
                while (rs2.next())
                {
                    //String uricp = model.getObjectUri("MX_"+rs2.getString(1), PostalCode.sclass);
                    //System.out.println("*******************>"+uricp);
                    //PostalCode postalcode = (PostalCode) model.createGenericObject(uricp, PostalCode.sclass);
                    PostalCode postalcode = null;
                    postalcode = PostalCode.getPostalCode("MX_z_" + rs2.getString(1), model);
                    if (null == postalcode)
                    {
                        postalcode = PostalCode.createPostalCode("MX_z_" + rs2.getString(1), model);
                    }
                    postalcode.setZip(rs2.getString(1));
                    City city = City.createCity(model);
                    city.setName(rs2.getString(2));
                    city.setCounty(county);
                    city.setPostalCode(postalcode);
                //postalcode.addCity(city);
                }
                rs2.close();
            }
            rs1.close();
        }
        /**
         * select start_ip, end_ip, location_id, country, region, city,  latitude, longitude from blocks, locations  where blocks.location_id=locations.id order by country, region
         */
        con.close();
    }

    //@Test
    public void loadGeo() throws FileNotFoundException, IOException{
        FileInputStream file = new FileInputStream("/Users/serch/Downloads/geonames_dd_dms_date_20090512.txt");
        InputStreamReader in = new InputStreamReader(file,"UTF-8");
        BufferedReader reader = new BufferedReader(in);
/*
0 RC
1 UFI
2 UNI
3 LAT
4 LONG
5 DMS_LAT
6 DMS_LONG
7 MGRS
8 JOG
9 FC
10 DSG
11 PC
12 CC1
13 ADM1
14 ADM2
15 POP
16 ELEV
17 CC2
18 NT
19 LC
20 SHORT_FORM
21 GENERIC
22 SORT_NAME
23 FULL_NAME
24 FULL_NAME_ND
25 MODIFY_DATE

 */
        String tmp = reader.readLine();
        //tmp = reader.readLine();
//        String[] heads = tmp.split("\\t");
        int i=0;
//        for (String head:heads){
//
//        System.out.println(""+i+" "+head);
        i++;
//        }
        while (tmp != null)
        {
            try{
                String[] fields = tmp.split("\\t");

            if ((i%100000)==0) {
                System.out.println(fields[12]+" "+i);

            }
            
            if ("MX".equals(fields[12]) && "09".equals(fields[13]))
            System.out.println(""+fields[12]+" - "+fields[13]+" - "+fields[14]+" - "+fields[17]+" - "+fields[23]);
            } catch (Exception e){
                System.out.println("Err: "+tmp);
            }
            tmp = reader.readLine();
            i++;
        }
        System.out.println(i);
        file.close();
    }

    //@Test
    public void LoadBlocks() throws FileNotFoundException, IOException, SQLException
    {
        Connection con = SWBUtils.DB.getConnection("geoloc", "Conexion geoloc");

        //BufferedReader reader = new BufferedReader(new FileReader("/Users/serch/Downloads/GeoLiteCity_20090401-1/GeoLiteCity-Blocks.csv"));
        FileInputStream file = new FileInputStream("/Users/serch/Downloads/GeoLiteCity_20090401-1/GeoLiteCity-Blocks.csv");
        InputStreamReader in = new InputStreamReader(file,"UTF-8");
        BufferedReader reader = new BufferedReader(in);
        String tmp = reader.readLine();
        tmp = reader.readLine();
        tmp = reader.readLine();
        String[] lista = new String[9];
        int idx, idx1, lidx;
        lidx = 0;
        Statement st = con.createStatement();
//        while (tmp != null)
//        {
//            if (lidx % 10000 == 0)
//            {
//                System.out.println("Memory: total:" + Runtime.getRuntime().totalMemory() + " Libre:" + Runtime.getRuntime().freeMemory() + " ocupada:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//            }
//            idx = tmp.indexOf("\"");
//            idx1 = tmp.indexOf("\"", idx + 1);
//            lista[0] = tmp.substring(idx + 1, idx1);
//            idx = tmp.indexOf("\"", idx1 + 1);
//            idx1 = tmp.indexOf("\"", idx + 1);
//            lista[1] = tmp.substring(idx + 1, idx1);
//            idx = tmp.indexOf("\"", idx1 + 1);
//            idx1 = tmp.indexOf("\"", idx + 1);
//            lista[2] = tmp.substring(idx + 1, idx1);
//
//            st.executeUpdate("insert into blocks ( start_ip, end_ip, location_id) values ( '" + lista[0] + "', '" + lista[1] + "', '" + lista[2] + "')");
//            //System.out.println("inicio:"+lista[0]+" fin:"+lista[1]+" localidad: "+lista[2]);
//            tmp = reader.readLine();
//            lidx++;
//        }
        reader.close();
        System.out.println("inicio:" + lista[0] + " fin:" + lista[1] + " localidad: " + lista[2]);
        //reader = new BufferedReader(new FileReader("/Users/serch/Downloads/GeoLiteCity_20090401-1/GeoLiteCity-Location.csv"));
        file = new FileInputStream("/Users/serch/Downloads/GeoLiteCity_20090401-1/GeoLiteCity-Location.csv");
         in = new InputStreamReader(file,"ISO-8859-1");
         reader = new BufferedReader(in);
        tmp = reader.readLine();
        tmp = reader.readLine();
        tmp = reader.readLine();
        lista = new String[9];

        while (tmp != null)
        {
            if (lidx % 10000 == 0)
            {
                System.out.println("Memory: total:" + Runtime.getRuntime().totalMemory() + " Libre:" + Runtime.getRuntime().freeMemory() + " ocupada:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            }
            idx = tmp.indexOf(",");

            lista[0] = tmp.substring(0, idx);
            idx = tmp.indexOf("\"", idx + 1);
            idx1 = tmp.indexOf("\"", idx + 1);
            lista[1] = tmp.substring(idx + 1, idx1);
            idx = tmp.indexOf("\"", idx1 + 1);
            idx1 = tmp.indexOf("\"", idx + 1);
            lista[2] = tmp.substring(idx + 1, idx1);
            idx = tmp.indexOf("\"", idx1 + 1);
            idx1 = tmp.indexOf("\"", idx + 1);
            lista[3] = tmp.substring(idx + 1, idx1);
            idx = tmp.indexOf("\"", idx1 + 1);
            idx1 = tmp.indexOf("\"", idx + 1);
            lista[4] = tmp.substring(idx + 1, idx1);
            idx = tmp.indexOf(",", idx1 + 1);
            idx1 = tmp.indexOf(",", idx + 1);
            lista[5] = tmp.substring(idx + 1, idx1);
            idx = tmp.indexOf(",", idx1 + 1);

            lista[6] = tmp.substring(idx1 + 1, idx);
            idx1 = tmp.indexOf(",", idx + 1);

            lista[7] = tmp.substring(idx + 1, idx1);
            lista[8] = tmp.substring(idx1 + 1);
            if ("".equals(lista[7]))
            {
                lista[7] = "0";
            }
            if ("".equals(lista[8]))
            {
                lista[8] = "0";
            }
          //  if (!"MX".equals(lista[1])) {tmp = reader.readLine();
          //  lidx++; continue;}
            System.out.println(tmp);
            System.out.println("insert into locations ( id, country, region, city, postal_code, latitude, longitude, metro_code, area_code) values " +
                    "( '" + lista[0] + "', '" + lista[1] + "', '" + lista[2] + "', \"" + lista[3] + "\", '" + lista[4] + "', '" + lista[5] + "', '" + lista[6] + "', '" + lista[7] + "', '" + lista[8] + "')");
            //con.createStatement()
            st.executeUpdate("insert into locations ( id, country, region, city, postal_code, latitude, longitude, metro_code, area_code) values " +
                    "( '" + lista[0] + "', '" + lista[1] + "', '" + lista[2] + "', \"" + lista[3] + "\", '" + lista[4] + "', '" + lista[5] + "', '" + lista[6] + "', '" + lista[7] + "', '" + lista[8] + "')");
            tmp = reader.readLine();
            lidx++;
        }
        System.out.println("- " + lista[0] + " - " + lista[1] + " - " + lista[2] + " - " + lista[3] + " - " + lista[4] + " - " + lista[5] + " - " + lista[6] + " - " + lista[7] + " - " + lista[8]);
        reader.close();
        con.close();
    }

    //@Test
    public void addLocation() throws SQLException{
        Connection con = SWBUtils.DB.getConnection("geoloc", "Conexion geoloc");

        //select location_id from blocks where 1210199536 >= start_ip and 1210199536 <= end_ip;
        String sql = "select id, country, region, city,  latitude, longitude from locations where country='MX' and region = '09' order by country, region, city";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        SWBModel model = Catalogs.getCatalogs("swbcatalogs");
        while (rs.next()){
        long id = rs.getLong(1);
        String country = rs.getString(2);
        
        String region = ""+rs.getInt(3);

        String city = rs.getString(4);
        String lat = rs.getString(5);
        String lon = rs.getString(6);
        System.out.println("Testing:"+country+"|"+region+"|"+city);
        Country c = Country.getCountry(country, model);
        if (c==null) continue;
        if (null!=region){
           State s = State.getState(country+"_"+region, model);
           if (null!=s && null!=city)
           {
               Iterator <LocationEntity> itc = s.listCountyInvs();
               LocationEntity co = null;
               while (itc.hasNext()){
               co = itc.next(); System.out.print(".");
               if (city.equalsIgnoreCase(co.getName())){
               break;
               } else co = null;
               }
               System.out.println();
               if (null!=co)
               System.out.println(country + " - "+c.getName()+" : "+region + " - "+s+ " : "+city+ " - "+co);

           } 
           else System.out.println(country + " - "+c.getName()+" : "+region + " - "+s);

        } else System.out.println(country + " - "+c);
        
        }

    }

    @Test
    public void listDF()
    {
    SWBModel model = Catalogs.getCatalogs("swbcatalogs");
    State s = State.getState("MX_9", model);
    Iterator <LocationEntity> itc = s.listCountyInvs();
               LocationEntity co = null;
               while (itc.hasNext()){
               co = itc.next(); System.out.println(co.getName());
               }
    }
    //@Test
    public void checkip() throws SQLException
    {
        Connection con = SWBUtils.DB.getConnection("geoloc", "Conexion geoloc");

        //select location_id from blocks where 1210199536 >= start_ip and 1210199536 <= end_ip;
        String sql = "select location_id, country, region, city,  latitude, longitude from blocks, locations  where ? >= start_ip and ? <= end_ip and blocks.location_id=locations.id";
        PreparedStatement ps = con.prepareStatement(sql);
        String[] ips =
        {
            "200.38.186.139", "208.109.181.4", "72.34.45.240", "195.66.240.130"
        };
        for (String a : ips)
        {
            long ip = convertIP(a);
            ps.setLong(1, ip);
            ps.setLong(2, ip);
            long time = System.currentTimeMillis();
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                System.out.println("Pais:" + rs.getString(2) + " Ciudad:" + rs.getString(3) + " ->" + (System.currentTimeMillis() - time));
            } else
            {
                System.out.println("Sin encontrar...");
            }
        }



    }

    public long convertIP(String ip)
    {

        InetAddress addr;
        try
        {
            addr = InetAddress.getByName(ip);
        } catch (UnknownHostException e)
        {
            return 0;
        }
        return bytesToLong(addr.getAddress());

    }

    private static long bytesToLong(byte[] address)
    {
        long ipnum = 0;
        for (int i = 0; i < 4; ++i)
        {
            long y = address[i];
            if (y < 0)
            {
                y += 256;
            }
            ipnum += y << ((3 - i) * 8);
        }
        return ipnum;
    }
}
