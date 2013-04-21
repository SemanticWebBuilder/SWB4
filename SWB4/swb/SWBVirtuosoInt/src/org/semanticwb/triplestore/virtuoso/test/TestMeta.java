/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import virtuoso.jena.driver.VirtGraph;

/**
 *
 * @author javier.solis.g
 */
public class TestMeta
{

    /**
     * Executes a SPARQL query against a virtuoso url and prints results.
     */
    public static void main(String[] args)
    {
        try
        {
            VirtGraph set = new VirtGraph ("jdbc:virtuoso://swb4d.semanticbuilder.com:1111", "dba", "dba");
            
            Connection c1 = set.getConnection();

            DatabaseMetaData metadata = null;
            metadata = c1.getMetaData();

            String tabla[] =
            {
                "TABLE"
            };

            //ontologia es la tabla a buscar en la base de datos almacenamiento
            ResultSet rs = metadata.getTables(null, null, "swb_graph", tabla);
            while(rs.next())
            {
                for(int x=1;x<=rs.getMetaData().getColumnCount();x++)
                {
                    //System.out.print(rs.getMetaData().getColumnName(x)+" ");
                    System.out.println(rs.getString(x)+" ");
                    
                }
                System.out.println("");
            }
            rs.close();
            c1.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
