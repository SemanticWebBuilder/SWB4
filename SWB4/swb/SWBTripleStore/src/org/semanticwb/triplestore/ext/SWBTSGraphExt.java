/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.triplestore.ext;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.semanticwb.Logger;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.triplestore.SWBTSGraph;
import org.semanticwb.triplestore.SWBTSUtil;

/**
 *
 * @author jei
 */
public class SWBTSGraphExt extends SWBTSGraph implements org.semanticwb.rdf.GraphExt
{
    private static Logger log = SWBUtils.getLogger(SWBTSGraphExt.class);


    public SWBTSGraphExt(int id, String name)
    {
        super(id, name);
    }
   

    public void performAdd(Triple t, Long id)
    {
        try
        {
            Connection con = SWBUtils.DB.getDefaultConnection();
            PreparedStatement ps = null;

            String subj=SWBTSUtil.node2String(t.getSubject());
            String hsubj=SWBTSUtil.getHashText(subj);
            String prop=SWBTSUtil.node2String(t.getPredicate());
            String hprop=SWBTSUtil.getHashText(prop);
            String obj=SWBTSUtil.node2String(t.getObject());
            String hobj=SWBTSUtil.getHashText(obj);
            
            //if(subj==null || prop==null || obj==null)return;

            String sext="";
            if(hsubj!=null)
            {
                sext+="|subj|"+subj.length()+"|"+subj;
                subj="lgs|"+hsubj;
            }
            if(hprop!=null)
            {
                sext+="|prop|"+prop.length()+"|"+prop;
                prop="lgp|"+hprop;
            }
            if(hobj!=null)
            {
                sext+="|obj|"+obj.length()+"|"+obj;
                obj="lgo|"+hobj;
            }
            
            //System.out.println("performAdd:"+subj+" "+prop+" "+obj);
            //new Exception().printStackTrace();
            
            String ord=SWBTSUtil.node2SortString(t.getObject());
            String stype=SWBTSUtil.getSTypeFromSUBJ(subj);

            if(sext.length()==0)
            {
                ps=con.prepareStatement("INSERT INTO swb_graph_ts"+getId()+" (subj, prop, obj, ord, stype, timems) VALUES (?, ?, ?, ?, ? ,?)");
            }else
            {
                ps=con.prepareStatement("INSERT INTO swb_graph_ts"+getId()+" (subj, prop, obj, ord, stype, timems, ext) VALUES (?, ?, ?, ?, ?, ?, ?)");
            }

            ps.setString(1, subj);
            ps.setString(2, prop);
            ps.setString(3, obj);
            ps.setString(4, ord);
            ps.setString(5, stype);
            ps.setLong(6, System.currentTimeMillis());
            if(sext.length()>0)
            {
                ps.setAsciiStream(7, SWBUtils.IO.getStreamFromString(sext), sext.length());
            }

            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e2)
        {
            log.error(e2);
            throw new SWBRuntimeException(e2.getMessage(), e2);
        }
    }
    
    public void performDelete(Triple t, Long id)
    {
        try
        {
            Connection con = SWBUtils.DB.getDefaultConnection();
            
            String subj=SWBTSUtil.node2HashString(t.getMatchSubject(),"lgs");
            String prop=SWBTSUtil.node2HashString(t.getMatchPredicate(),"lgp");
            String obj=SWBTSUtil.node2HashString(t.getMatchObject(),"lgo");

            //System.out.println("performDelete:"+subj+" "+prop+" "+obj);
            
            String query="delete from swb_graph_ts"+getId();
            String query2="";
            if(subj!=null)query2+=" subj=?";
            if(prop!=null)
            {
                if(query2.length()>0)query2 +=" and";
                query2 += " prop=?";
            }
            if(obj!=null)
            {
                if(query2.length()>0)query2 +=" and";
                query2 += " obj=?";
            }

            if(query2.length()>0)query+=" where"+query2;

            PreparedStatement ps=con.prepareStatement(query);
            int i=1;
            if(subj!=null)ps.setString(i++, subj);
            if(prop!=null)ps.setString(i++, prop);
            if(obj!=null)ps.setString(i++, obj);
            ps.executeUpdate();            
            ps.close();
            con.close();
        } catch (Exception e2)
        {
            log.error(e2);
            throw new SWBRuntimeException(e2.getMessage(), e2);            
        }
    }    

    public long count(TripleMatch tm, String stype)
    {
        long count=0;
        String subj=SWBTSUtil.node2HashString(tm.getMatchSubject(),"lgs");
        String prop=SWBTSUtil.node2HashString(tm.getMatchPredicate(),"lgp");
        String obj=SWBTSUtil.node2HashString(tm.getMatchObject(),"lgo");
        
        //System.out.println("subj:"+subj+" prop:"+prop+" obj:"+obj+" stype:"+stype);

        try
        {
            Connection con=SWBUtils.DB.getDefaultConnection();

            String query="select count(*) from swb_graph_ts"+this.getId();
            String query2="";
            if(subj!=null)query2+=" subj=?";
            if(prop!=null)
            {
                if(query2.length()>0)query2 +=" and";
                query2 += " prop=?";
            }
            if(obj!=null)
            {
                if(query2.length()>0)query2 +=" and";
                query2 += " obj=?";
            }
            if(stype!=null)
            {
                if(query2.length()>0)query2 +=" and";
                query2 += " stype=?";
            }

            if(query2.length()>0)query+=" where"+query2;

            PreparedStatement ps=con.prepareStatement(query);
            int i=1;
            if(subj!=null)ps.setString(i++, subj);
            if(prop!=null)ps.setString(i++, prop);
            if(obj!=null)ps.setString(i++, obj);
            if(stype!=null)ps.setString(i++, stype);
            ResultSet rs=ps.executeQuery();

            if(rs.next())
            {
                count=rs.getLong(1);
            }
            
            //System.out.println("cout:"+count+" rs:"+rs);
            
            
            rs.close();
            ps.close();
            con.close();
        }catch(SQLException e)
        {
            log.error(e);
            throw new SWBRuntimeException(e.getMessage(), e);
        }
        return count;
    }

    public ExtendedIterator<Triple> find(TripleMatch tm, String stype, Long limit, Long offset, String sortby)
    {
        return new SWBTSIteratorExt(this, tm, stype, limit, offset, sortby);
    }

}
