package org.semanticwb.triplestore.ext;

import org.semanticwb.triplestore.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.util.db.GenericDB;

/**
 *
 * @author jei
 */
public class SWBTSModelMakerExt extends SWBTSModelMaker
{
    private static Logger log=SWBUtils.getLogger(SWBTSModelMakerExt.class);

    public SWBTSModelMakerExt()
    {
        super();
    }
    
    public Model getModel(String name)
    {
        Integer id=getMap().get(name);
        if(id!=null)
        {
            return new ModelCom(new SWBTSGraphExt(id,name));
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
                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb2_[name].xml");
                db.executeSQLScript(xml.replace("[name]", ""+id), SWBUtils.DB.getDatabaseName(), null);

                PreparedStatement ps=con.prepareStatement("INSERT INTO swb_graph (id,name) VALUES (?,?)");
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.executeUpdate();
                ps.close();
                con.close();

                model=new ModelCom(new SWBTSGraphExt(id,name));
                getMap().put(name, id);

            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
        return model;
    }



}
