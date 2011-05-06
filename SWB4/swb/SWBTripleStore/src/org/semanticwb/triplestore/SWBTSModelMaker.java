/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.triplestore;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.util.db.GenericDB;

/**
 *
 * @author jei
 */
public class SWBTSModelMaker
{
    private static Logger log=SWBUtils.getLogger(SWBTSModelMaker.class);

    private HashMap<String,Integer> map;

    public SWBTSModelMaker()
    {
        map=new HashMap();

        try
        {
            Connection con=SWBUtils.DB.getDefaultConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select * from swb_graph");
            while(rs.next())
            {
                int id=rs.getInt("id");
                String name=rs.getString("name");
                map.put(name, id);
            }
            rs.close();
            st.close();
            con.close();
        }catch(SQLException e)
        {
            GenericDB db = new GenericDB();
            try
            {
                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_graph.xml");
                db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
                //xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_longs.xml");
                //db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
                xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_prefix.xml");
                db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
    }

    public Iterator<String> listModelNames()
    {
        return map.keySet().iterator();
    }

    public Model getModel(String name)
    {
        Integer id=map.get(name);
        if(id!=null)
        {
            return new ModelCom(new SWBTSGraph(id,name));
        }else
        {
            return null;
        }
    }

    public Model createModel(String name)
    {
        Model model=getModel(name);
        if(model==null)
        {
            try
            {
                Connection con=SWBUtils.DB.getDefaultConnection();
                int id=1;
                synchronized(this)
                {
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("select max(id) from swb_graph");
                    if(rs.next())id=rs.getInt(1)+1;
                    rs.close();
                    st.close();
                }

                GenericDB db = new GenericDB();
                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_[name].xml");
                db.executeSQLScript(xml.replace("[name]", ""+id), SWBUtils.DB.getDatabaseName(), null);

                PreparedStatement ps=con.prepareStatement("INSERT INTO swb_graph (id,name) VALUES (?,?)");
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.executeUpdate();
                ps.close();
                con.close();

                model=new ModelCom(new SWBTSGraph(id,name));
                map.put(name, id);

            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
        return model;
    }

    public void removeModel(String name)
    {
        Model model=getModel(name);
        if(model!=null)
        {
            int id=((SWBTSGraph)model.getGraph()).getId();
            try
            {

                Connection con=SWBUtils.DB.getDefaultConnection();
                PreparedStatement ps=con.prepareStatement("drop table swb_graph_ts"+id);
                ps.executeUpdate();
                ps.close();

                ps=con.prepareStatement("remove from swb_graph where id=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();

                ps=con.prepareStatement("remove from swb_longs where graphid=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();

                ps=con.prepareStatement("remove from swb_prefix where graphid=?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();

                con.close();

                map.remove(name);

            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
    }

}
