package org.semanticwb.remotetriplestore;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.EOT;
import org.semanticwb.remotetriplestore.protocol.OOK;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;
import org.semanticwb.remotetriplestore.protocol.TripleString;
import org.semanticwb.triplestore.SWBTSModelMaker;
import org.semanticwb.triplestore.SWBTripleStore;

/**
 *
 * @author serch
 */
public class SWBRTSConn implements Runnable
{

    private Socket sock = null;
    private boolean running = false;
    private static Logger log = SWBUtils.getLogger(SWBRTSConn.class);

    SWBRTSConn(Socket sock)
    {
        this.sock = sock;
    }

    @Override
    public void run()
    {
        try
        {
            ObjectInputStream objDataIn = new ObjectInputStream(sock.getInputStream());
            SWBRTSCmd cmd = (SWBRTSCmd) objDataIn.readObject();
            String[] params = null;
           // System.out.println("SWBRTSConn: cmd "+cmd.cmd+" #"+cmd.paramNumber);
            if (cmd.paramNumber > 0)
            {
                params = new String[cmd.paramNumber];
                for (int i = 0; i < cmd.paramNumber; i++)
                {
                    params[i] = (String) objDataIn.readObject();
                //    System.out.println("read param: "+params[i]);
                }
            }
            Object obj = objDataIn.readObject();
            if (!(obj instanceof EOT)) log.event("not EOT when spected");
            action(cmd, params);
            objDataIn.close();
            sock.close();

        } catch (Exception e)
        {
            log.error(e);
        }
    }

    private void action(SWBRTSCmd cmd, String[] params)
    {
        try
        {
            ObjectOutputStream objDataOut = new ObjectOutputStream(sock.getOutputStream());
            SWBTripleStore store = (SWBTripleStore) SWBPlatform.getSemanticMgr().getSWBStore();
            SWBTSModelMaker maker = store.getMaker();
            Integer id = null;
            String subj;
            String prop;
            String obj;
            String ext;
            Response resp = new Response();
            switch (cmd.cmd)
            {
                case LIST_MODEL_NAMES:
                    Iterator<String> it = maker.listModelNames();
                    ArrayList<String> objData = new ArrayList<String>();
                    while (it.hasNext())
                    {
                        objData.add(it.next());
                    }
                    resp.data = objData;
                    objDataOut.writeObject(resp);
                    break;

                case GET_MODEL:
                    id = maker.getMap().get(params[0]);
                    resp.data  = id;
                    objDataOut.writeObject(resp);
                    break;
                case CREATE_MODEL:
                    maker.createModel(params[0]);
                    id = maker.getMap().get(params[0]);
                    resp.data  = id;
                    maker.listModelNames();
                    SWBPlatform.getSemanticMgr().loadDBModels();
                    objDataOut.writeObject(resp);
                    break;
                case REMOVE_MODEL:
                    maker.removeModel(params[0]);
                    maker.listModelNames();
                    SWBPlatform.getSemanticMgr().loadDBModels();
                    resp.data  = new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case GRAPH_BASE_FIND:
                    id = maker.getMap().get(params[0]);
                    subj = params[1];
                    prop = params[2];
                    obj = params[3];
                    resp.data=getFind(id, subj, prop, obj);
                    objDataOut.writeObject(resp);
                    break;
                case GET_NS_PREFIX_MAP:
                    resp.data=maker.getModel(params[0]).getNsPrefixMap();
                    objDataOut.writeObject(resp);
                    break;
                case GET_NS_PREFIX_URI:
                    resp.data=maker.getModel(params[0]).getNsPrefixURI(params[1]);
                    objDataOut.writeObject(resp);
                    break;
                case SET_NS_PREFIX:
                    maker.getModel(params[0]).setNsPrefix(params[1], params[2]);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case REMOVE_NS_PREFIX:
                    maker.getModel(params[0]).removeNsPrefix(params[1]);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case GRAPH_ADD:
                    id = maker.getMap().get(params[0]);
                    subj = params[1];
                    prop = params[2];
                    obj = params[3];
                    ext = params[4];
                    doAdd(id, subj, prop, obj, ext);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case GRAPH_REMOVE:
                    id = maker.getMap().get(params[0]);
                    subj = params[1];
                    prop = params[2];
                    obj = params[3];
                    doRemove(id, subj, prop, obj);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
            }
            objDataOut.flush();
            objDataOut.close();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    private ArrayList<TripleString> getFind(Integer id, String subj, String prop, String obj)
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<TripleString> list = new ArrayList<TripleString> ();

        try
        {
            con=SWBUtils.DB.getDefaultConnection();

            String query="select * from swb_graph_ts"+id;
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

            ps=con.prepareStatement(query);
            int i=1;
            if(subj!=null)ps.setString(i++, subj);
            if(prop!=null)ps.setString(i++, prop);
            if(obj!=null)ps.setString(i++, obj);
            rs=ps.executeQuery();

            while(rs.next())
            {
                TripleString  next = new TripleString();
                InputStream sext=rs.getAsciiStream("ext");
                try
                {
                    if(sext!=null)next.ext=SWBUtils.IO.readInputStream(sext);
                }catch(Exception e){log.error(e);}

                next.subj=rs.getString("subj");
                next.prop=rs.getString("prop");
                next.obj=rs.getString("obj");
                list.add(next);
            }
            rs.close();
            ps.close();
            con.close();
        }catch(SQLException e)
        {
            log.error(e);
        }
        return list;
    }

    private void doAdd(Integer id, String subj, String prop, String obj, String sext)
    {
        Connection con;
        PreparedStatement ps;
        try
        {
            con=SWBUtils.DB.getDefaultConnection();
            if(sext.length()==0)
            {
                ps=con.prepareStatement("INSERT INTO swb_graph_ts"+id+" (subj, prop, obj) VALUES (?, ?, ?)");
            }else
            {
                ps=con.prepareStatement("INSERT INTO swb_graph_ts"+id+" (subj, prop, obj, ext) VALUES (?, ?, ?, ?)");
            }

            ps.setString(1, subj);
            ps.setString(2, prop);
            ps.setString(3, obj);
            if(sext.length()>0)ps.setAsciiStream(4, SWBUtils.IO.getStreamFromString(sext),sext.length());

            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    private void doRemove(Integer id, String subj, String prop, String obj)
    {
        try
        {
            Connection con = SWBUtils.DB.getDefaultConnection();
            String query="delete from swb_graph_ts"+id;
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
        }
    }
}
