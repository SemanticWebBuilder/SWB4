/**
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
**/


/*
 * DBTopicMap.java
 *
 * Created on 20 de junio de 2002, 11:20
 */
package com.infotec.topicmaps.db;

import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.appfw.lib.AFAppObject;
import com.infotec.appfw.util.AFUtils;

import java.sql.*;
import java.util.*;

import com.infotec.appfw.util.db.*;

import java.io.*;

/** objeto: manejador de objetos de datos de TopicMaps
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class DBTopicMap implements AFAppObject, AFObserver
{
    static private DBTopicMap instance;       // The single instance

    private Hashtable topicmaps = null;
    private Hashtable topics = null;
    private Hashtable associations = null;
    private Hashtable occurrences = null;
    private ArrayList observers = new ArrayList();

    public int xmlconftp = 3;


    /** Creates new DBTopicMap */
    private DBTopicMap()
    {
        AFUtils.log("Initializing DBTopicMap", true);
    }

    public void destroy()
    {
        try
        {
            /*
            WBUtils.getInstance().XMLObjectEncoder(topicmaps, "DBTopicMap.xml");
            WBUtils.getInstance().XMLObjectEncoder(topics, "DBTopic.xml");
            WBUtils.getInstance().XMLObjectEncoder(associations, "DBAssociation.xml");
            WBUtils.getInstance().XMLObjectEncoder(occurrences, "DBOccurrence.xml");
            */
        } catch (Exception e)
        {
        }
        AFUtils.log("DBTopicMap finalized", true);
        instance=null;
    }

    static synchronized public DBTopicMap getInstance()
    {
        if (instance == null)
        {
            instance = new DBTopicMap();
            instance.init();
        }
        return instance;
    }
    
     /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
    public void registerObserver(AFObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }
    
    public void loadRecTopicMap(String recFile)
    {
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(recFile)));
            String aux=null;
            int x=0;
            while((aux=in.readLine())!=null)
            {
                RecTopicMap ru = new RecTopicMap(new ObjectDecoder(aux));
                x++;
                ru.setVirtual(true);
                addRecTopicMap(ru);
            }
            in.close();  
            AFUtils.log("TopicMaps in memory:\t" + x +" (ADMIN)", true);
        } catch (Exception e){}          
    }
    
    public void loadRecTopic(String recFile)
    {
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(recFile)));
            String aux=null;
            int x=0, y=0;
            while((aux=in.readLine())!=null)
            {
                RecTopic ru = new RecTopic(new ObjectDecoder(aux));
                ru.setVirtual(true);
                if(addRecTopic(ru))x++;
                else y++;
            }
            in.close();     
            AFUtils.log("Topics in memory:\t" + x+" (ADMIN)", true);
            if(y>0)AFUtils.log("Topics not loaded:\t"+y+" (ADMIN)",true);
        } catch (Exception e){} 
    }
    
    public void loadRecAssociation(String recFile)
    {
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(recFile)));
            String aux=null;
            int x=0,y=0;
            while((aux=in.readLine())!=null)
            {
                RecAssociation ru = new RecAssociation(new ObjectDecoder(aux));
                ru.setVirtual(true);
                if(addRecAssociation(ru))x++;
                else y++;
            }
            in.close();     
            AFUtils.log("Associations in memory:\t" + x+" (ADMIN)", true);
            if(y>0)AFUtils.log("Associations not loaded:\t"+y+" (ADMIN)",true);
        } catch (Exception e){}     
    }
    
    public void loadRecOccurrence(String recFile)
    {    
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(recFile)));
            String aux=null;
            int x=0,y=0;
            while((aux=in.readLine())!=null)
            {
                RecOccurrence ru = new RecOccurrence(new ObjectDecoder(aux));
                ru.setVirtual(true);
                if(addRecOccurrence(ru))x++;
                else y++;
            }
            in.close();     
            AFUtils.log("Occurrences in memory:\t" + x+" (ADMIN)", true);
            if(y>0)AFUtils.log("Ocurrences not loaded:\t"+y+" (ADMIN)",true);
        } catch (Exception e){}      
    }

    public void init()
    {
        String ret = "\r";
        topicmaps = new Hashtable();
        topics = new Hashtable();
        associations = new Hashtable();
        occurrences = new Hashtable();
/*        
        try
        {
            topicmaps = (Hashtable) WBUtils.getInstance().XMLObjectDecoder("DBTopicMap.xml");
            topics = (Hashtable) WBUtils.getInstance().XMLObjectDecoder("DBTopic.xml");
            associations = (Hashtable) WBUtils.getInstance().XMLObjectDecoder("DBAssociation.xml");
            occurrences = (Hashtable) WBUtils.getInstance().XMLObjectDecoder("DBOccurrence.xml");
            AFUtils.log("DBTopicMap recobrado de session anterior...", true);
            return;
        } catch (Exception e)
        {
        }
*/
        if(!AFUtils.getEnv("wb/adminDev","false").equalsIgnoreCase("true"))
        {
            loadRecTopicMap("/RecTopicMap.rec");
            loadRecTopic("/RecTopic.rec");
            loadRecAssociation("/RecAssociation.rec");
            loadRecOccurrence("/RecOccurrence.rec");
        }

        DBConnectionManager mgr = DBConnectionManager.getInstance();
        Connection con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
        if (con != null)
        {
            try
            {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from wbtopicmap");
                int x=0;
                while (rs.next())
                {
                    RecTopicMap ru = new RecTopicMap(rs.getString("id"), rs.getString("idadm"), rs.getString("title"), rs.getString("home"), rs.getString("lang"), rs.getString("description"), rs.getInt("active"), com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml")), rs.getTimestamp("created"), rs.getTimestamp("lastupdate"), rs.getInt("deleted"),rs.getInt("system"),rs.getString("repository"),rs.getString("indexer"));
                    AFUtils.debug("TopicMap:"+ru.getId()+" x:"+x,9);                        
                    addRecTopicMap(ru);
                    x++;
                }
                rs.close();
                st.close();
                AFUtils.log("TopicMaps in memory:\t" + x, true);
            } catch (Exception e)
            {
                AFUtils.log(e, "Error to load cache of wbtopicmap...", true);
            }
            try
            {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from wbtopic");

                //detectar tipo de dato
                ResultSetMetaData md = rs.getMetaData();
                switch (md.getColumnType(6)) //xmlconf
                {
                    case java.sql.Types.LONGVARBINARY:  //-4:
                    case java.sql.Types.VARBINARY:      //-3:
                    case java.sql.Types.BINARY:         //-2:
                        xmlconftp = 3;                        //bytes
                        break;
                    case java.sql.Types.CHAR:           //1:
                    case java.sql.Types.VARCHAR:        //12:
                        xmlconftp = 2;                        //string
                        break;
                    case java.sql.Types.LONGVARCHAR:    //-1:
                    case java.sql.Types.BLOB:           //2004:
                    case java.sql.Types.CLOB:           //2005:
                        xmlconftp = 3;                        //string
                        break;
                    default:
                        xmlconftp = 3;                        //string
                        break;
                }
                int x=0,y=0;
                while (rs.next())
                {
                    try
                    {
                        RecTopic ru = new RecTopic(rs.getString("id"), rs.getString("idtm"), rs.getString("idadm"), rs.getInt("active"), com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml")), rs.getString("xmlconf"), rs.getTimestamp("created"), rs.getInt("system"), rs.getTimestamp("lastupdate"), rs.getInt("deleted"), rs.getLong("views"),rs.getInt("indexable"),rs.getInt("hidden"));
                        AFUtils.debug("Topic:"+ru.getId()+":"+ru.getIdtm()+" x:"+x+" y:"+y,9);                        
                        if(addRecTopic(ru))x++;
                        else y++;
                        //System.out.print(ret);
                    }catch(Exception e){AFUtils.log(e,"Error to load topics from database...");}
                }
                rs.close();
                st.close();
                AFUtils.log("Topics in memory:\t" + x, true);
                if(y>0)AFUtils.log("Topics not loaded:\t"+y,true);
            } catch (Exception e)
            {
                AFUtils.log(e, "Error to load cache of wbtopic...", true);
            }
            try
            {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from wbassociation");
                int x=0,y=0;
                while (rs.next())
                {
                    try
                    {
                        RecAssociation ru = new RecAssociation(rs.getString("id"), rs.getString("idtm"), com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml")), rs.getTimestamp("lastupdate"));
                        AFUtils.debug("Association:"+ru.getId()+":"+ru.getIdtm()+" x:"+x+" y:"+y,9);                        
                        if(addRecAssociation(ru))x++;
                        else y++;
                        //System.out.print(x);
                        //System.out.print(ret);
                    }catch(Exception e){AFUtils.log(e,"Error to load associations from database...");}
                }
                rs.close();
                st.close();
                AFUtils.log("Associations in memory:\t" + x, true);
                if(y>0)AFUtils.log("Associations not loaded:\t"+y,true);
            } catch (Exception e)
            {
                AFUtils.log(e, "Error al inicializar cache de wbassociation...", true);
            }
            try
            {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from wboccurrence");
                int x=0,y=0;
                while (rs.next())
                {
                    try
                    {
                        //System.out.println("occ:"+rs.getString("id"));
                        RecOccurrence ru = new RecOccurrence(rs.getString("id"), rs.getString("idtm"), rs.getString("idtp"), rs.getInt("active"), com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml")), rs.getTimestamp("lastupdate"), rs.getInt("deleted"), rs.getString("flow"), rs.getInt("status"), rs.getString("step"), null,rs.getInt("priority"));
                        AFUtils.debug("Occurrence:"+ru.getId()+":"+ru.getIdtm()+" x:"+x+" y:"+y,9);                        
                        try
                        {
                            Timestamp ft=rs.getTimestamp("flowtime");
                            ru.setFlowtime(ft);
                        }catch(SQLException e){}
                        if(addRecOccurrence(ru))x++;
                        else y++;
                        //System.out.print(x);
                        //System.out.print(ret);
                    }catch(Exception e){AFUtils.log(e,"Error to load occurrences from database...");}
                }
                rs.close();
                st.close();
                AFUtils.log("Occurrences in memory:\t" + x, true);
                if(y>0)AFUtils.log("Ocurrences not loaded:\t"+y,true);
            } catch (Exception e)
            {
                AFUtils.log(e, "Error to load cache of wboccurrence...", true);
            }
            try
            {
                con.close();
            } catch (Exception e)
            {
                AFUtils.log("Error to load cache of topics...");
            }
        } else
        {
            //tira una exception
        }
        mgr.release();
    }
    
    private void addRecTopicMap(RecTopicMap ru)
    {
        topicmaps.put(ru.getId(), ru);
        topics.put(ru.getId(), new Hashtable());
        associations.put(ru.getId(), new Hashtable());
        occurrences.put(ru.getId(), new Hashtable());        
    }

    private boolean addRecTopic(RecTopic ru)
    {
        Hashtable map = (Hashtable) topics.get(ru.getIdtm());
        if (map != null)
        {
            map.put(ru.getId(), ru);
            return true;
        }
        return false;
    }
    
    private boolean addRecAssociation(RecAssociation ru)
    {
        //System.out.println("ru J-0:"+ru);
        Hashtable hash=(Hashtable)associations.get(ru.getIdtm());
        if(hash!=null)
        {
            hash.put(ru.getId(),ru);
            return true;
        }
        return false;
    }

    private boolean addRecOccurrence(RecOccurrence ru)
    {
        //System.out.println("ru J:"+ru);
        Hashtable hash=(Hashtable)occurrences.get(ru.getIdtm());
        if(hash!=null)
        {
            hash.put(ru.getId(),ru);
            return true;
        }
        return false;
    }

    public Enumeration getTopicMaps()
    {
        return topicmaps.elements();
    }

    public Enumeration getTopics(String TopicMapID)
    {
        Hashtable hash = (Hashtable) topics.get(TopicMapID);
        if (hash == null) return null;
        return hash.elements();
    }

    public Enumeration getAssociations(String TopicMapID)
    {
        Hashtable hash = (Hashtable) associations.get(TopicMapID);
        if (hash == null) return null;
        return hash.elements();
    }

    public Enumeration getOccurrences(String TopicMapID)
    {
        Hashtable hash = (Hashtable) occurrences.get(TopicMapID);
        if (hash == null) return null;
        return hash.elements();
    }

    public RecTopicMap getTopicMap(String id) throws com.infotec.appfw.exception.AFException
    {
        RecTopicMap rec = (RecTopicMap) topicmaps.get(id);
        if (rec == null) throw new com.infotec.appfw.exception.AFException("No se encontro el recurso...", "DBTopicMap:getTopicMap()");
        return rec;
    }
    
    public RecTopic getTopic(String TopicMapID, String id) throws com.infotec.appfw.exception.AFException
    {
        Hashtable hash = (Hashtable) topics.get(TopicMapID);
        if (hash == null) throw new com.infotec.appfw.exception.AFException("No se encontro el mapa...", "DBTopicsMap:getTopic()");
        RecTopic rec = (RecTopic) hash.get(id);
        if (rec == null) throw new com.infotec.appfw.exception.AFException("No se encontro el topico...", "DBTopicsMap:getTopic()");
        return rec;
    }

    public RecAssociation getAssociation(String TopicMapID, String id) throws com.infotec.appfw.exception.AFException
    {
        Hashtable hash = (Hashtable) associations.get(TopicMapID);
        if (hash == null) throw new com.infotec.appfw.exception.AFException("No se encontro el mapa...", "DBTopicsMap:getTopic()");
        RecAssociation rec = (RecAssociation) hash.get(id);
        if (rec == null) throw new com.infotec.appfw.exception.AFException("No se encontro la associacion...", "DBTopicMap:getAssociation()");
        return rec;
    }

    public RecOccurrence getOccurrence(String TopicMapID, String id) throws com.infotec.appfw.exception.AFException
    {
        Hashtable hash = (Hashtable) occurrences.get(TopicMapID);
        if (hash == null) throw new com.infotec.appfw.exception.AFException("No se encontro el mapa...", "DBTopicsMap:getTopic()");
        RecOccurrence rec = (RecOccurrence) hash.get(id);
        if (rec == null) throw new com.infotec.appfw.exception.AFException("No se encontro la occurrencia...", "DBTopicMap:RecOccurrence()");
        return rec;
    }

    public void refresh()
    {
    }

    /** Avisa al observador de que se ha producido un cambio.
     */
    public void sendDBNotify(String s, Object obj)
    {
        if (s.equals("remove"))
        {
            if (obj instanceof RecTopicMap)
            {
                RecTopicMap rectm = (RecTopicMap) obj;
                topicmaps.remove(rectm.getId());
                topics.remove(rectm.getId());
                associations.remove(rectm.getId());
                occurrences.remove(rectm.getId());
            } else if (obj instanceof RecTopic)
            {
                RecTopic rectp = (RecTopic) obj;
                ((Hashtable) topics.get(rectp.getIdtm())).remove(rectp.getId());
            } else if (obj instanceof RecAssociation)
            {
                RecAssociation recass = (RecAssociation) obj;
                ((Hashtable) associations.get(recass.getIdtm())).remove(recass.getId());
            } else if (obj instanceof RecOccurrence)
            {
                RecOccurrence recocc = (RecOccurrence) obj;
                ((Hashtable) occurrences.get(recocc.getIdtm())).remove(recocc.getId());
            }
        } else if (s.equals("create"))
        {
            if (obj instanceof RecTopicMap)
            {
                RecTopicMap rectm = (RecTopicMap) obj;
                topicmaps.put(rectm.getId(), rectm);
                topics.put(rectm.getId(), new Hashtable());
                associations.put(rectm.getId(), new Hashtable());
                occurrences.put(rectm.getId(), new Hashtable());
            } else if (obj instanceof RecTopic)
            {
                RecTopic rectp = (RecTopic) obj;
                ((Hashtable) topics.get(rectp.getIdtm())).put(rectp.getId(), rectp);
            } else if (obj instanceof RecAssociation)
            {
                RecAssociation recass = (RecAssociation) obj;
                ((Hashtable) associations.get(recass.getIdtm())).put(recass.getId(), recass);
            } else if (obj instanceof RecOccurrence)
            {
                RecOccurrence recocc = (RecOccurrence) obj;
                ((Hashtable) occurrences.get(recocc.getIdtm())).put(recocc.getId(), recocc);
            }
        } else if (s.equals("load"))
        {
            if (obj instanceof RecTopicMap)
            {
                RecTopicMap rectm = (RecTopicMap) obj;
                if (topicmaps.get(rectm.getId()) == null)
                {
                    topicmaps.put(rectm.getId(), rectm);
                    topics.put(rectm.getId(), new Hashtable());
                    associations.put(rectm.getId(), new Hashtable());
                    occurrences.put(rectm.getId(), new Hashtable());
                }
            } else if (obj instanceof RecTopic)
            {
                RecTopic rectp = (RecTopic) obj;
                if (((Hashtable) topics.get(rectp.getIdtm())).get(rectp.getId()) == null)
                    ((Hashtable) topics.get(rectp.getIdtm())).put(rectp.getId(), rectp);
            } else if (obj instanceof RecAssociation)
            {
                RecAssociation recass = (RecAssociation) obj;
                if (((Hashtable) associations.get(recass.getIdtm())).get(recass.getId()) == null)
                    ((Hashtable) associations.get(recass.getIdtm())).put(recass.getId(), recass);
            } else if (obj instanceof RecOccurrence)
            {
                RecOccurrence recocc = (RecOccurrence) obj;
                if (((Hashtable) occurrences.get(recocc.getIdtm())).get(recocc.getId()) == null)
                    ((Hashtable) occurrences.get(recocc.getIdtm())).put(recocc.getId(), recocc);
            }
        }
        
        //envia a sus propios observadores
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            try{
                ((AFObserver) it.next()).sendDBNotify(s, obj);
            }catch(Exception e){
                AFUtils.log(e);
            }
        }
        
    }

}