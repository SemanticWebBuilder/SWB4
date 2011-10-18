package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;
import org.semanticwb.remotetriplestore.protocol.EOT;
import org.semanticwb.remotetriplestore.protocol.OOK;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;
import org.semanticwb.remotetriplestore.protocol.TripleString;
import org.semanticwb.triplestore.SWBTSUtil;

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
//            while(true)
            {
                ObjectInputStream objDataIn = new ObjectInputStream(sock.getInputStream());
                SWBRTSCmd cmd = (SWBRTSCmd) objDataIn.readObject();
                //System.out.println("Run:"+cmd+" "+sock);
//                if(!cmd.cmd.equals(Command.CONN_CLOSE))
                {
                    String[] params = null;
                   //System.out.println("SWBRTSConn: cmd "+cmd.cmd+" #"+cmd.paramNumber);
                    if (cmd.paramNumber > 0)
                    {
                        params = new String[cmd.paramNumber];
                        for (int i = 0; i < cmd.paramNumber; i++)
                        {
                            params[i] = (String) objDataIn.readObject();
                            //System.out.println("read param: "+params[i]);
                        }
                    }
                    Object obj = objDataIn.readObject();
                    if (!(obj instanceof EOT)) log.event("not EOT when spected");
                    action(cmd, params);
//                }else
//                {
                    objDataIn.close();                    
                    sock.close();
                    //System.out.println("sock close:"+sock);
//                    break;
                }
            }

        } catch (Exception e)
        {
            log.error(e);
        }
    }

    private void action(SWBRTSCmd cmd, String[] params)
    {
        try
        {
            /*
            System.out.print(cmd.cmd);
            if(params!=null)
            {
                for(int x=0;x<params.length;x++)
                {
                    System.out.print(" "+params[x]);                
                }
            }
            System.out.println();                
            */
            
            ObjectOutputStream objDataOut = new ObjectOutputStream(sock.getOutputStream());
            AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
            String name;
            String subj;
            String prop;
            String obj;
            String id;
            Response resp = new Response();
            Model model;
            switch (cmd.cmd)
            {
                case LIST_MODEL_NAMES:
                    Iterator<String> it = store.listModelNames();
                    ArrayList<String> objData = new ArrayList<String>();
                    while (it.hasNext())
                    {
                        objData.add(it.next());
                    }
                    resp.data = objData;
                    objDataOut.writeObject(resp);
                    break;

                case GET_MODEL:
                    name=params[0];
                    if(store.getModel(name)!=null)
                    {
                        resp.data = new OOK();
                    }
                    objDataOut.writeObject(resp);
                    break;
                case CREATE_MODEL:
                    name=params[0];
                    if(store.loadModel(name)!=null)
                    {
                        resp.data  = new OOK();
                        //SWBPlatform.getSemanticMgr().loadDBModels();
                    }
                    objDataOut.writeObject(resp);
                    break;
                case REMOVE_MODEL:
                    name=params[0];
                    store.removeModel(name);
                    //SWBPlatform.getSemanticMgr().loadDBModels();
                    resp.data  = new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case GRAPH_BASE_FIND:
                    name = params[0];
                    subj = params[1];
                    prop = params[2];
                    obj = params[3];
                    resp.data=getFind(name, subj, prop, obj);
                    objDataOut.writeObject(resp);
                    break;
                case GET_NS_PREFIX_MAP:
                    name = params[0];
                    model=store.getModel(name);
                    resp.data=model.getNsPrefixMap();
                    objDataOut.writeObject(resp);
                    break;
                case GET_NS_PREFIX_URI:
                    name = params[0];
                    model=store.getModel(name);
                    resp.data=model.getNsPrefixURI(params[1]);
                    objDataOut.writeObject(resp);
                    break;
                case SET_NS_PREFIX:
                    name = params[0];
                    model=store.getModel(name);
                    model.setNsPrefix(params[1], params[2]);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case REMOVE_NS_PREFIX:
                    name = params[0];
                    model=store.getModel(name);
                    model.removeNsPrefix(params[1]);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case GRAPH_ADD:
                    name = params[0];
                    subj = params[1];
                    prop = params[2];
                    obj = params[3];
                    id = params[4];
                    doAdd(name, subj, prop, obj, id);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case GRAPH_REMOVE:
                    name = params[0];
                    subj = params[1];
                    prop = params[2];
                    obj = params[3];
                    id = params[4];
                    doRemove(name, subj, prop, obj, id);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;
                case TRANS_BEGIN:
                    name = params[0];
                    id = params[1];                    
                    begin(name, id);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;                    
                case TRANS_ABORT:
                    name = params[0];
                    id = params[1];                    
                    abort(name, id);
                    resp.data=new OOK();
                    objDataOut.writeObject(resp);
                    break;                    
                case TRANS_COMMINT:
                    name = params[0];
                    id = params[1];                    
                    commint(name, id);
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

    private ArrayList<TripleString> getFind(String name, String subj, String prop, String obj)
    {
        ArrayList<TripleString> list = new ArrayList<TripleString> ();
        
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);

        //System.out.println("getFind:"+subj+":"+prop+":"+obj);
        
        Iterator<Triple> it=model.getGraph().find(SWBTSUtil.string2Node(subj,null), SWBTSUtil.string2Node(prop,null), SWBTSUtil.string2Node(obj,null));
        while (it.hasNext()) {
            Triple triple = it.next();
            TripleString  next = new TripleString();
            next.subj=SWBTSUtil.node2String(triple.getSubject());
            next.prop=SWBTSUtil.node2String(triple.getPredicate());
            next.obj=SWBTSUtil.node2String(triple.getObject());
            list.add(next);

        }
        return list;
    }
    
    private void begin(String name, String sid)
    {
        //System.out.println("begin:"+name+" "+sid);
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RTransactionHandler trans=(RTransactionHandler)model.getGraph().getTransactionHandler();
        trans.begin(id);
    }
    
    private void commint(String name, String sid)
    {
        //System.out.println("commint:"+name+" "+sid);
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RTransactionHandler trans=(RTransactionHandler)model.getGraph().getTransactionHandler();
        trans.commit(id);
    }
    
    private void abort(String name, String sid)
    {
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RTransactionHandler trans=(RTransactionHandler)model.getGraph().getTransactionHandler();
        trans.abort(id);
    }

    private void doAdd(String name, String subj, String prop, String obj, String sid)
    {
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        ((RGraph)model.getGraph()).performAdd(new Triple(SWBTSUtil.string2Node(subj,null), SWBTSUtil.string2Node(prop,null), SWBTSUtil.string2Node(obj,null)),id);
    }

    private void doRemove(String name, String subj, String prop, String obj, String sid)
    {
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        ((RGraph)model.getGraph()).performDelete(new Triple(SWBTSUtil.string2Node(subj,null), SWBTSUtil.string2Node(prop,null), SWBTSUtil.string2Node(obj,null)),id);
    }
}
