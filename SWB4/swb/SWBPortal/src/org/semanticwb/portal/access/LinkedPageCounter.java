/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.access;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @author javier.solis.g
 */
public class LinkedPageCounter
{
    private static int hits=0;
    private static int links=0;
    private static int sess=0;
    private static int resess=0;
    private static HashMap<String, LinkedPageCounter> pages=new HashMap();
    private static boolean loading=false;
    
    private static Comparator comparator=new Comparator<Map.Entry<String,Integer>>()
    {
        @Override
        public int compare(Map.Entry<String, Integer> t, Map.Entry<String, Integer> t1)
        {
            return t.getValue().compareTo(t1.getValue())>0?1:-1;
        }
    };
    
    private static Comparator pagecomp=new Comparator<LinkedPageCounter>()
    {
        @Override
        public int compare(LinkedPageCounter t, LinkedPageCounter t1)
        {
            return Integer.valueOf(t.getCounter()).compareTo(t1.getCounter())>0?1:-1;
        }
    };
    
    
    private String id=null;
    private int counter=0;
    private int totChildCounter=0;
    private int totParentCounter=0;
    private HashMap<String,Integer> child=new HashMap();
    private HashMap<String,Integer> parent=new HashMap();

    /**
     * Regresa bandera de si esta cargando datos del log
     * @return 
     */
    public static boolean isLoading()
    {
        return loading;
    }
    
    /**
     * Asigna bandera de carga de datos
     * @param loading 
     */
    public static void setLoading(boolean loading)
    {
        LinkedPageCounter.loading=loading;
    }
    
    /**
     * Reinicia contadores
     */
    public static void reset()
    {
        sess=0;
        resess=0;
        hits=0;
        links=0;
        pages=new HashMap();
        loading=false;        
    }
    
    /**
     * Incrementa contador de pagina 
     * @param id identificador de la pagina
     * @return 
     */
    public static LinkedPageCounter incCounter(String id)
    {
        hits++;
        LinkedPageCounter page=getCounter(id);
        page.incCounter();
        return page;
    }
    
    /**
     * Regresa Objeto LinkedPageCounter de la pagina
     * @param id identificador de la pagina
     * @return 
     */
    public static LinkedPageCounter getCounter(String id)
    {
        LinkedPageCounter page=pages.get(id);
        if(page==null)
        {
            page=new LinkedPageCounter(id);
            pages.put(id, page);
        }
        return page;
    }
    
    /**
     * Numero total de paginas almacenadas
     * @return 
     */
    public static int size()
    {
        return pages.size();
    }
    
    public static TreeSet listPages()
    {
        TreeSet set=new TreeSet(pagecomp);
        set.addAll(pages.values());
        return set;
    }
    
    /**
     * Numero total de hits de todas la paginas
     * @return 
     */
    public static int getTotalHits()
    {
        return hits;
    }
    
    /**
     * Numero total de ligas entre las paginas
     * @return 
     */
    public static int getTotalLinks()
    {
        return links;
    }  
    
    /**
     * Regresa total de sessiones
     * @return 
     */
    public static int getTotalSessions()
    {
        return sess;
    }
    
    /**
     * Regresa total de sessiones
     * @return 
     */
    public static int getTotalSessionsReused()
    {
        return resess;
    }
    
    /**
     * Procesa el archivo de log indicado
     * @param logpath
     * @return 
     */
    public static boolean loadFile(String logpath)
    {
        if(!isLoading())
        {
            setLoading(true);
            
            HashMap<String,String> sessions=new HashMap();
            LinkedList<String> lsessions=new LinkedList();
            try
            {
                FileInputStream fin=new FileInputStream(logpath);    
                DataInputStream in=new DataInputStream(fin);
                String line;

                while((line=in.readLine())!=null)
                {
                    StringTokenizer st=new StringTokenizer(line,"|");
                    String date=st.nextToken();
                    String userip=st.nextToken();
                    String serverip=st.nextToken();
                    String session=st.nextToken();
                    String site=st.nextToken();
                    String page=st.nextToken();
                    String userrep=st.nextToken();
                    String userid=st.nextToken();
                    String usertype=st.nextToken();
                    String dev=st.nextToken();
                    String lang=st.nextToken();


                    LinkedPageCounter p=LinkedPageCounter.incCounter(page);

                    String old=sessions.get(session);
                    if(old==null)
                    {
                        sessions.put(session, page);
                        lsessions.push(session);
                        sess++;
                        if(sessions.size()>5000)
                        {
                            String s=lsessions.removeLast();
                            sessions.remove(s);
                        }
                    }else
                    {
                        resess++;
                        LinkedPageCounter o=LinkedPageCounter.getCounter(old);
                        o.incChildLinkCounter(page);
                        sessions.put(session, page);
                    }
                    if(LinkedPageCounter.getTotalHits()%10000==0)System.out.println(LinkedPageCounter.getTotalHits());
                }
                setLoading(false);
                return true;
            }catch(Exception e)
            {
                e.printStackTrace();
            }      
            setLoading(false);
        }
        return false;
    }
    
    
    
    private LinkedPageCounter(String id)
    {
        this.id=id;
    }

    /**
     * Identificador de la pagina
     * @return 
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * Total de hits de la pagina
     * @return 
     */
    public int getCounter()
    {
        return counter;
    }
    
    /**
     * Incrementar contador de hits de la pagina
     * @return 
     */
    public int incCounter()
    {
        return ++counter;
    }
    
    /**
     * Incrementa contador de vinculo entre esta pagina y una pagina hija
     * @param id
     * @return 
     */
    public int incChildLinkCounter(String id)
    {
        //Parent
        LinkedPageCounter p=getCounter(id);
        Integer pa=p.parent.get(this.getId());
        p.parent.put(this.getId(), pa==null?1:pa+1);
        p.totParentCounter++;
        
        //Child
        totChildCounter++;
        Integer ch=child.get(id);
        if(ch==null)
        {
            links++;
            ch=1;
        }else
        {
            ch++;
        }
        child.put(id, ch);
        return ch;
    }

    /**
     * Regresa mapa de vinculos hijos
     * @return 
     */
    public HashMap<String, Integer> getChild()
    {
        return child;
    }
    
    public TreeSet<Map.Entry<String,Integer>> getSortedChildSet()
    {
        TreeSet<Map.Entry<String,Integer>> set=new TreeSet(comparator);
        set.addAll(child.entrySet());        
        return set;
    }

    /**
     * Regresa mapa de vicnulos padre
     * @return 
     */
    public HashMap<String, Integer> getParent()
    {
        return parent;
    }
    
    public TreeSet<Map.Entry<String,Integer>> getSortedParentSet()
    {
        TreeSet<Map.Entry<String,Integer>> set=new TreeSet(comparator);
        set.addAll(parent.entrySet());        
        return set;
    }

    /**
     * Regresa total de hits de hijos vinculados
     * @return 
     */
    public int getTotalChildCounter()
    {
        return totChildCounter;
    }

    /**
     * Regresa total de padres vinculados
     * @return 
     */
    public int getTotalParentCounter()
    {
        return totParentCounter;
    }
    
    
}

