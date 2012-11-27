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
package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;
import org.semanticwb.rdf.GraphCached;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.triplestore.SWBTSUtil;

/**
 *
 * @author serch
 */
public class SWBRTSConn implements Runnable
{
    private static Logger log = SWBUtils.getLogger(SWBRTSConn.class);    
    
    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    SWBRTSConn(Socket socket)
    {
        try
        {
            socket.setTcpNoDelay(true);
        }catch(Exception e)
        {
            log.error(e);
        }
        System.out.println("Handler...");
        this.socket = socket;
    }

    public void run()
    {
        long time=System.currentTimeMillis();
        System.out.println("Connection inited...,"+time);
        try
        {
            this.in=new DataInputStream(socket.getInputStream());
            this.out=new DataOutputStream(socket.getOutputStream());
        }catch(Exception e)
        {
            log.error(e);
        }        
        
        try
        {
            List list;
            while(true)
            {   
                list=readCommands();
                if(list.size()==1 && list.get(0).equals(Command.CLOSE))break;
                //time=System.currentTimeMillis();
                processCommand(list);
                //System.out.println("process:"+(System.currentTimeMillis()-time)+" "+list);
            }
        }catch(Exception e)
        {
            log.error(e);
        }

        //Close Connection
        try
        {
            out.close();
            socket.close();
        }catch(Exception e)
        {
            log.error(e);
        }
        System.out.println("Connection closed...,"+time);
    }
    
    public List<String> readCommands() throws IOException
    {
        ArrayList arr=new ArrayList();
        int n=Integer.parseInt(in.readUTF());
        for(int x=0;x<n;x++)
        {
            String s=in.readUTF();
            if(s.equals(Command.NULL))s=null;
            arr.add(s);
        }
        return arr;           
    }
    
    public void writeCommands(List<String> list) throws IOException
    {
        out.writeUTF(String.valueOf(list.size()));
        Iterator<String> it=list.iterator();
        while (it.hasNext())
        {
            String string = it.next();
            if(string==null)string=Command.NULL;
            out.writeUTF(string);
        }
    }  
    
    public void processCommand(List<String> list) throws IOException
    {
        //long time=System.currentTimeMillis();
        List<String> ret=action(list);        
        writeCommands(ret);
        //System.out.println("process:"+(System.currentTimeMillis()-time));
    }        
    
    private List<String> action(List<String> params)
    {
        String cmd=params.get(0);
        ArrayList<String> arr = new ArrayList<String>();
        try
        {
            AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
            String name;
            String subj;
            String prop;
            String obj;
            String id;
            Model model;
            
            if(cmd.equals(Command.LIST_MODEL_NAMES))
            {
                Iterator<String> it = store.listModelNames();
                while (it.hasNext())
                {
                    arr.add(it.next());
                }
            }else if(cmd.equals(Command.GET_MODEL))
            {
                name=params.get(1);
                if(store.getModel(name)!=null)
                {
                    arr.add(Command.OOK);
                }
            }else if(cmd.equals(Command.CREATE_MODEL))
            {
                name=params.get(1);
                if(store.loadModel(name)!=null)
                {
                    arr.add(Command.OOK);
                }
            }else if(cmd.equals(Command.REMOVE_MODEL))
            {
                name=params.get(1);
                store.removeModel(name);
                //SWBPlatform.getSemanticMgr().loadDBModels();
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.GRAPH_BASE_FIND))
            {
                name = params.get(1);
                subj = params.get(2);
                prop = params.get(3);
                obj = params.get(4);                    
                arr=getFind(name, subj, prop, obj);
            }else if(cmd.equals(Command.GET_NS_PREFIX_MAP))
            {
                name = params.get(1);
                model = store.getModel(name);
                Map<String,String> map=model.getNsPrefixMap();
                
                Iterator<Map.Entry<String,String>> it=map.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry<String, String> entry = it.next();
                    arr.add(entry.getKey());
                    arr.add(entry.getValue());
                }
            }else if(cmd.equals(Command.GET_NS_PREFIX_URI))
            {
                name = params.get(1);
                model=store.getModel(name);
                arr.add(model.getNsPrefixURI(params.get(2)));
            }else if(cmd.equals(Command.SET_NS_PREFIX))
            {
                name = params.get(1);
                model=store.getModel(name);
                model.setNsPrefix(params.get(2), params.get(3));
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.REMOVE_NS_PREFIX))
            {
                name = params.get(1);
                model=store.getModel(name);
                model.removeNsPrefix(params.get(2));
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.GRAPH_ADD))
            {
                name = params.get(1);
                subj = params.get(2);
                prop = params.get(3);
                obj = params.get(4);
                id = params.get(5);
                doAdd(name, subj, prop, obj, id);
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.GRAPH_REMOVE))
            {
                name = params.get(1);
                subj = params.get(2);
                prop = params.get(3);
                obj = params.get(4);
                id = params.get(5);
                doRemove(name, subj, prop, obj, id);
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.TRANS_BEGIN))
            {
                System.out.println("Begin:"+params.get(2));
                name = params.get(1);
                id = params.get(2);                    
                begin(name, id);
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.TRANS_ABORT))
            {
                System.out.println("Abort:"+params.get(2));
                name = params.get(1);
                id = params.get(2);                    
                abort(name, id);
                arr.add(Command.OOK);
            }else if(cmd.equals(Command.TRANS_COMMINT))
            {
                System.out.println("Commit:"+params.get(2));
                name = params.get(1);
                id = params.get(2);                    
                commit(name, id);
                arr.add(Command.OOK);
            }            
        } catch (Throwable e)
        {
            log.error(e);
            arr.add(Command.CLOSE);
        }
        
        return arr;
    }
     


    private ArrayList<String> getFind(String name, String subj, String prop, String obj)
    {
        ArrayList<String> list = new ArrayList<String> ();
        
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);

        //System.out.println("getFind:"+subj+":"+prop+":"+obj);
        //long time=System.currentTimeMillis();
        
        Iterator<Triple> it=model.getGraph().find(SWBTSUtil.string2Node(subj,null), SWBTSUtil.string2Node(prop,null), SWBTSUtil.string2Node(obj,null));
        //System.out.println("time1:"+(System.currentTimeMillis()-time));
        while (it.hasNext()) {
            Triple triple = it.next();
            list.add(SWBTSUtil.node2String(triple.getSubject()));
            list.add(SWBTSUtil.node2String(triple.getPredicate()));
            list.add(SWBTSUtil.node2String(triple.getObject()));
        }
        //System.out.println("time2:"+(System.currentTimeMillis()-time));
        return list;
    }
    
    private void begin(String name, String sid)
    {
        //System.out.println("begin:"+name+" "+sid);
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RGraph g;
        if(model.getGraph() instanceof GraphCached)
        {
            g=(RGraph)((GraphCached)model.getGraph()).getGraphBase();
        }else
        {
            g=((RGraph)model.getGraph());
        }        
        RTransactionHandler trans=(RTransactionHandler)(g.getTransactionHandler());
        trans.begin(id);
    }
    
    private void commit(String name, String sid)
    {
        //System.out.println("commint:"+name+" "+sid);
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RGraph g;
        if(model.getGraph() instanceof GraphCached)
        {
            g=(RGraph)((GraphCached)model.getGraph()).getGraphBase();
        }else
        {
            g=((RGraph)model.getGraph());
        }        
        RTransactionHandler trans=(RTransactionHandler)(g.getTransactionHandler());
        trans.commit(id);
    }
    
    private void abort(String name, String sid)
    {
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RGraph g;
        if(model.getGraph() instanceof GraphCached)
        {
            g=(RGraph)((GraphCached)model.getGraph()).getGraphBase();
        }else
        {
            g=((RGraph)model.getGraph());
        }        
        RTransactionHandler trans=(RTransactionHandler)(g.getTransactionHandler());
        trans.abort(id);
    }

    private void doAdd(String name, String subj, String prop, String obj, String sid)
    {
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RGraph g;
        if(model.getGraph() instanceof GraphCached)
        {
            g=(RGraph)((GraphCached)model.getGraph()).getGraphBase();
        }else
        {
            g=((RGraph)model.getGraph());
        }
        g.performAdd(new Triple(SWBTSUtil.string2Node(subj,null), SWBTSUtil.string2Node(prop,null), SWBTSUtil.string2Node(obj,null)),id);
    }

    private void doRemove(String name, String subj, String prop, String obj, String sid)
    {
        Long id=null;
        if(sid!=null)id=Long.parseLong(sid);
        AbstractStore store = SWBPlatform.getSemanticMgr().getSWBStore();
        Model model=store.getModel(name);
        RGraph g;
        if(model.getGraph() instanceof GraphCached)
        {
            g=(RGraph)((GraphCached)model.getGraph()).getGraphBase();
        }else
        {
            g=((RGraph)model.getGraph());
        }
        g.performDelete(new Triple(SWBTSUtil.string2Node(subj,null), SWBTSUtil.string2Node(prop,null), SWBTSUtil.string2Node(obj,null)),id);
    }
}
