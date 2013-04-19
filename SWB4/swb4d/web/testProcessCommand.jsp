<%-- 
    Document   : processCommand
    Created on : 22-oct-2012, 13:40:01
    Author     : javier.solis.g
--%><%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%><%@page import="org.semanticwb.nlp.CommandTranslator.Command"%><%@page import="java.util.ArrayList"%><%@page import="java.util.HashMap"%><%@page import="org.semanticwb.nlp.CommandTranslator.CommandParser"%><%@page import="java.util.List"%><%@page import="org.semanticwb.domotic.server.WebSocketServlet"%><%@page import="org.semanticwb.domotic.model.DomContext"%><%@page import="org.semanticwb.model.GenericObject"%><%@page import="org.semanticwb.platform.SemanticObject"%><%@page import="org.semanticwb.model.SWBContext"%><%@page import="org.semanticwb.domotic.model.SWB4DContext"%><%@page import="org.semanticwb.model.WebSite"%><%@page import="org.semanticwb.domotic.model.DomGroup"%><%@page import="org.semanticwb.domotic.model.DomDevice"%><%@page import="java.util.Iterator"%><%@page import="org.semanticwb.SWBUtils"%><%@page import="org.semanticwb.model.SWBModel"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%!

    static HashSet m_filter = null;    
    static HashMap m_actions = null;    

    public Map<Object, ArrayList> getNodes(SWBModel model, String lang)
    {
        HashMap<Object, ArrayList> map=new HashMap();
        {
            Iterator<DomDevice> it = DomDevice.ClassMgr.listDomDevices(model);
            while (it.hasNext())
            {
                DomDevice obj = it.next();
                String title=obj.getDisplayTitle(lang);
                map.put(obj, filterStrings(title));
            }
        }
        {
            Iterator<DomGroup> it = DomGroup.ClassMgr.listDomGroups(model);
            while (it.hasNext())
            {
                DomGroup obj = it.next();
                String title=obj.getDisplayTitle(lang);
                map.put(obj, filterStrings(title));
            }
        }
        {
            Iterator<DomContext> it = DomContext.ClassMgr.listDomContexts(model);
            while (it.hasNext())
            {
                DomContext obj = it.next();
                String title=obj.getDisplayTitle(lang);
                map.put(obj, filterStrings(title));
            }
        }
        return map;
    }    
    
    public Set<String> getFilter()
    {
        if(m_filter==null)
        {
            m_filter=new HashSet();
            //determinados
            m_filter.add("el");
            m_filter.add("la");
            m_filter.add("los");
            m_filter.add("las");
            //indeterminados
            m_filter.add("un");
            m_filter.add("una");
            m_filter.add("unos");
            m_filter.add("unas");
            
            m_filter.add("de");
            m_filter.add("lo");
            m_filter.add("a");
            m_filter.add("al");
            m_filter.add("del");            
        }                   
        return m_filter;
    }            
    
    public Map<String, String> getActions()
    {
        if(m_actions==null)
        {
            m_actions=new HashMap();
            m_actions.put("encender","active");
            m_actions.put("enciende","active");
            m_actions.put("enciendele","active");
            m_actions.put("conecta","active");
            m_actions.put("conectale","active");
            m_actions.put("conectar","active");
            m_actions.put("activar","active");
            m_actions.put("activa","active");
            m_actions.put("activale","active");
            m_actions.put("prende","active");
            m_actions.put("prendele","active");
            m_actions.put("prender","active");
            m_actions.put("cambia","active");
            m_actions.put("cambiale","active");
            m_actions.put("cambiar","active");
            
            m_actions.put("apagar","unactive");
            m_actions.put("apaga","unactive");
            m_actions.put("apagale","unactive");
            m_actions.put("desactivar","unactive");
            m_actions.put("desactiva","unactive");
            m_actions.put("desactivale","unactive");
            m_actions.put("corta","unactive");
            m_actions.put("cortale","unactive");
            m_actions.put("cortar","unactive");
            m_actions.put("desconectar","unactive");    
            m_actions.put("desconecta","unactive");   
            m_actions.put("desconectale","unactive");   
            
            m_actions.put("estado de","status");    
            m_actions.put("estatus de","status");   
            m_actions.put("se encuentra","status");   
            m_actions.put("como esta","status");    
        }
        return m_actions;
    }
    
    //Regresa mapa con palabras y posicion original del texto
    public ArrayList filterStrings(String txt)
    {
        ArrayList arr=new ArrayList();
        
        String tokens[]=txt.split(" ");            
        
        Set set=getFilter();
        for(int x=0;x<tokens.length;x++)
        {
            String tk=tokens[x].trim();
            if(tk.length()>0 && !set.contains(tk))arr.add(tk);
        }
        
        return arr;
    }
    
    public boolean findArray(ArrayList sub, ArrayList all)
    {
        int ind=0;
        
        int isub=0;
        int iall=0;
        
        while(isub<sub.size() && iall<all.size())
        {
            if(sub.get(isub)==all.get(iall))
            {
                isub++;
                iall++;
            }else
            {
                iall++;
            }
        }
        
        if(isub==sub.size())return true;        
        return false;
    }
    
    public Set<Node> findNodes(Map devs, String commands)
    {
        HashMap<Object, Node> map=new HashMap();
       //try{ret=SWBUtils.TEXT.decode(ret, "utf8");}catch(Exception e){e.printStackTrace();}
        if (commands.startsWith("["))
        {
            commands = commands.substring(1, commands.length() - 1);
        }
        
        /*
        Pattern p = Pattern.compile("\\d+%");
        Matcher m = p.matcher(commands); 
        if(m.find()) {
           System.out.println(m.group());
        }
 *      */
        

        String sentences[]=commands.split(",");  
        for(int x=0;x<sentences.length;x++)
        {
            String line=sentences[x];
            System.out.println(line);
            
            ArrayList tokens=filterStrings(line);
            String intenc=null;
            String action=null;
            Object obj=null;
            System.out.println(tokens);
            
            Iterator<Map.Entry<Object, ArrayList>> it=devs.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Object, ArrayList> entry=it.next();
                if(findArray(entry.getValue(), tokens))
                {
                    Node node=map.get(entry.getKey());
                    if(node==null)
                    {
                        node=new Node();
                        node.obj=entry.getKey();
                        node.size=entry.getValue().size();
                        map.put(node.obj, node);
                    }
                    node.counter++;
                }
            }
            
            
        }
        
        TreeSet<Node> set=new TreeSet();
        Iterator<Node> it=map.values().iterator();
        while(it.hasNext())
        {
            set.add(it.next());
        }
        return set;
    }
    
    class Node
    {
        public Object obj=null;
        public int counter=0;
        public int size=0;
        public String action=null;
        public String inte=null;
    }

    

%><%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");

    WebSite site = SWBContext.getWebSite("dom");
    
    String commands="[enciende oficina de javier al 50%, enciende oficina de javier el 50 por ciento, enciende oficina de javier al 50 por ciento, enciende oficina de javier el 50 %, enciende oficina de javier el 50%, enciende oficina de javier al 50 %, enciende oficina de salir del 50 por ciento, enciende oficina de javier del 50 por ciento, enciende oficina de salir del 50%, enciende oficina de javier del 50%, enciende oficina de javier un 50 por ciento, enciende oficina de salir el 50 por ciento, enciende oficina de salir al 50%, enciende oficina de javier el cincuenta por ciento, enciende oficina de javi el 50 por ciento, canción de oficina de javier al 50%, entiende oficina de javier al 50%, canción de oficina de javier el 50 por ciento, enciende oficina de salir al 50 por ciento, enciende oficinas de javier al 50%, enciende oficina de salir el 50 %, enciende oficina de xavier el 50 por ciento, enciende oficina de salir el 50%, entiende oficina de javier el 50 por ciento]";
    
    Set<Node> nodes=findNodes(getNodes(site, "es"),commands);
    
%>
