/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository.mysqldatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.semanticwb.linkeddata.spider.repository.DocumentTriples;
import org.semanticwb.linkeddata.spider.repository.Triple;

/**
 *
 * @author karen.najera
 */
public class Mysqldatabase {

    public String databasename;
    Connection conexion;
    BasicDataSource basicdatasource = new BasicDataSource();
    private DataSource dataSource = null;
    private int repeated = 0;
    private int repeatedURL = 0;

    public Mysqldatabase(String databasename) {
        this.databasename = databasename;
    }

    public void dbConnection() {
        basicdatasource.setDriverClassName("com.mysql.jdbc.Driver");
        basicdatasource.setUsername("root");
        basicdatasource.setPassword("root");
        basicdatasource.setUrl("jdbc:mysql://localhost/" + databasename);

        dataSource = basicdatasource;
    }

    public void addTriple(String suj, String pred, String obj, String url) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        if (!isRepeated(suj, pred, obj, url)) {
            try {
                con = dataSource.getConnection();
                String query = "insert into data (suj, pred, obj, urls_url) values (?, ?, ?, ?)";
                st = con.prepareStatement(query);
                st.setString(1, suj);
                st.setString(2, pred);
                st.setString(3, obj);
                st.setString(4, url);
                st.execute();
                System.out.println("Loaded: -" + url + " - " + suj + " " + pred + " " + obj);
            } catch (Exception e) {
                System.err.println("Error addTriple: -" + url + " - " + suj + " " + pred + " " + obj + " e:" + e);
            } finally {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    con.close();
                }
            }
        } else {
            repeated++;
            System.out.println("Repeated " + repeated + ": " + url + " - " + suj + " " + pred + " " + obj);
        }
    }

    public void insertURLandLastAccess(String url, String lastAccess) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();
            if (con != null) {
                if (!isRepeated(url, lastAccess)) {
                    String query = "insert into urls (url, lastAccess) values (?, ?)";

                    st = con.prepareStatement(query);
                    st.setString(1, url);
                    st.setString(2, lastAccess);
                    st.execute();
                    System.out.println("Loaded URL: " + url);
                } else {
                    repeatedURL++;
                    System.err.println("Repeated URL " + repeatedURL + ":" + url);
                }
            } else {
                System.out.println("con: " + con.toString());
            }
        } catch (SQLException e) {
            System.err.println("Error insertURL: " + url + "lastAccess: " + lastAccess + "Error: " + e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error insertURLandLastAccess: Close connection " + url + "lastAccess: " + lastAccess + "Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void updateLastAccess(String url, String lastAccess) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();
            if (con != null) {
                String query = "UPDATE urls SET lastAccess=\"" + lastAccess + "\" WHERE url=\"" + url + "\"";
                st = con.prepareStatement(query);
                //System.out.println("query: " + query);
                st.executeUpdate();
            } else {
                System.out.println("con: " + con.toString());
            }
        } catch (SQLException e) {

            System.err.println("Error updateLastAccess: " + url + "lastAccess: " + lastAccess + "Error: " + e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error updateLastAccess: Close connection " + url + "lastAccess: " + lastAccess + "Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public HashSet<String> LoadVisitedURLs() {
        Connection con = null;
        PreparedStatement st = null;
        HashSet<String> urls = new HashSet<String>();
        String query = "select url from urls";
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                urls.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error LoadVisitedURLs: Close connection Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return urls;
        }

    }

    public DocumentTriples LoadTriplesfromURL(String url) {
        DocumentTriples document = new DocumentTriples();
        Connection con = null;
        PreparedStatement st = null;
        HashSet<Triple> triples = new HashSet<Triple>();
        String query = "select * from data WHERE urls_url=\"" + url + "\"";
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString(1) + " . " + rs.getString(2) + " . " + rs.getString(3) + " . ");
                Triple triple = new Triple(rs.getString(1), rs.getString(2), rs.getString(3), true, false);
                triples.add(triple);
            }
        } catch (SQLException ex) {
            System.err.println("Error LoadTriplesfromURL: " + ex);
        } finally {
            document.add(url, triples);
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error LoadTriplesfromURL: Close connection Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return document;
        }

    }

    public String getLastAccess(String url) {
        Connection con = null;
        String lastAccess = null;
        PreparedStatement st = null;
        String query = "select lastAccess from urls where url= \"" + url + "\"";
        try {
            con = dataSource.getConnection();
            st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                lastAccess = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.err.println("Error getLastAccess: " + url + "lastAccess: " + lastAccess + "Error: " + ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error getLastAccess: Close connection " + url + "lastAccess: " + lastAccess + "Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return lastAccess;
        }

    }

    public void removeTriple(String suj, String pred, String obj, String url) {
        Connection con = null;
        PreparedStatement st = null;
        String query = "delete from data where suj= \"" + suj + "\" and pred= \"" + pred + "\" and obj= \"" + obj + "\" and urls_url= \"" + url + "\"";
        System.out.println(query);

        try {
            con = dataSource.getConnection();
            st = con.prepareStatement(query);
            st.execute();

        } catch (SQLException ex) {
            System.err.println("Error removeTriple: " + query + "Error: " + ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error removeTriple: Close connection " + query + "Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean isRepeated(String suj, String pred, String obj, String url) {
        Connection con = null;
        boolean isrepeated = false;
        PreparedStatement st = null;

        String query = "select * from data where suj= \"" + suj + "\" and pred= \"" + pred + "\" and obj= \"" + obj + "\" and urls_url= \"" + url + "\"";
        try {
            con = dataSource.getConnection();;
            st = con.prepareStatement(query);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (suj.equalsIgnoreCase(rs.getString(1)) && pred.equalsIgnoreCase(rs.getString(2)) && obj.equalsIgnoreCase(rs.getString(3)) && url.equalsIgnoreCase(rs.getString(4))) {
                    isrepeated = true;
                }
            }
            //System.out.println(query + " " + isrepeated);

        } catch (SQLException ex) {
            System.out.println("Error isRepeated: " + url + " - " + suj + " " + pred + " " + obj + " Error: " + ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error isRepeated: Close connection " + url + " - " + suj + " " + pred + " " + obj + "Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return isrepeated;
        }
    }

    private boolean isRepeated(String url, String lastAccess) {
        Connection con = null;
        boolean isrepeated = false;
        PreparedStatement st = null;

        String query = "select * from urls where url= \"" + url + "\" and lastAccess= \"" + lastAccess + "\"";
        try {
            con = dataSource.getConnection();;
            st = con.prepareStatement(query);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (url.equalsIgnoreCase(rs.getString(1)) && lastAccess.equalsIgnoreCase(rs.getString(2))) {
                    isrepeated = true;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error isRepeated: " + url + " - " + lastAccess + "Error: " + ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error isRepeated: Close connection " + url + "lastAccess: " + lastAccess + "Error: " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mysqldatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return isrepeated;
        }
    }

    public static void main(String[] args) {
        String databasename = "ldstore";
        //String url = "http://dbpedia.org/resource/Mercury_Topaz";
        String url = "http://xmlns.com/foaf/0.1/";
        String lastAccess = "14/08/2012";
        //String url = "http://www.lapaginadekaren.com";
        String suj = "http://www.lapaginadekaren.com#subj";
        String pred = "http://www.lapaginadekaren.com#pred";
        String obj = "http://www.lapaginadekaren.com#obj";

        Mysqldatabase mysqldb = new Mysqldatabase(databasename);

        mysqldb.dbConnection();
        /*
        try {
        
        //String lastAccess = "13/08/2012";
        //mysqldb.insertURLandLastAccess(url, lastAccess);
        //mysqldb.addTriple(suj, pred, obj, url);
        } catch (SQLException ex) {
        System.err.println("Error Mysqldatabase main: " + ex);
        }
         * 
         */

        //mysqldb.LoadTriplesfromURL(url);
        lastAccess = mysqldb.getLastAccess(url);
        //System.out.println("is repeated " + yes);
        //mysqldb.LoadVisitedURLs();
        //String lastAccess = mysqldb.getLastAccess(url);
        System.out.println("lastAccess: " + lastAccess);
        //mysqldb.removeTriple(suj, pred, obj, url);
        //lastAccess = "14/08/2012";
        //mysqldb.updateLastAccess(url, lastAccess);
    }
}
