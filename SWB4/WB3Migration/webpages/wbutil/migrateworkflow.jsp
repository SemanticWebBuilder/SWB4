<%@page contentType="text/html;charset=windows-1252" import="java.util.*,org.w3c.dom.*,com.infotec.topicmaps.*, com.infotec.wb.core.db.*,com.infotec.appfw.util.*,java.sql.*,com.infotec.topicmaps.bean.*,com.infotec.wb.core.*,com.infotec.wb.services.*"%>
<html>
<head><title>JSP Page</title></head>
<body>

<%
try
{
    int flujos=0;
    int occurrences=0;
    if(request.getParameter("tm")!=null)
    {
                String topicmap=request.getParameter("tm");

                Connection con=null;
                try
                {         
                    String destino="wb";
                    String origen="origen";
                    if(request.getParameter("conexion1")!=null && !request.getParameter("conexion1").equals(""))
                    {
                        origen=request.getParameter("conexion1");
                    }
                    if(request.getParameter("conexion2")!=null && !request.getParameter("conexion2").equals(""))
                    {
                        destino=request.getParameter("conexion2");
                    }
                    con=AFUtils.getDBConnection(origen);
                    if(con!=null)
                    {
                        Vector types=new Vector();
                        Connection con3=AFUtils.getInstance().getDBConnection(destino);
                        PreparedStatement ps2=con3.prepareStatement("select * from wbresourcetype where (idtm=? or idtm=?) and (type=1 or type=3)");
                        ps2.setString(1,topicmap);
                        ps2.setString(2,"WBAGlobal");
                        ResultSet rsTypes=ps2.executeQuery();
                        while(rsTypes.next())
                        {
                            ResourceType type=new ResourceType();
                            type.name=rsTypes.getString("name");
                            type.type=rsTypes.getString("id");
                            type.topicmap=rsTypes.getString("idtm");
                            types.add(type);
                        }
                        rsTypes.close();
                        ps2.close();
                        con3.close();

                        Statement st=con.createStatement();
                        ResultSet rs=st.executeQuery("select * from wbpflow");
                        while(rs.next())
                        {
                            flujos++;
                            String userid=rs.getString("idadm")+"_wb";                        
                            if(topicmap==null)
                            {
                                topicmap=TopicMgr.getInstance().getGlobalTopicMap().getId();
                            }
                            String description=rs.getString("description");
                            String title=rs.getString("title");
                            Document oldxml=AFUtils.getInstance().XmltoDom(rs.getString("xml"));
                            Document xml=AFUtils.getInstance().getNewDocument();

                            int id=rs.getInt("id");
                            Transform2To3(id, topicmap,title,description,oldxml, xml,types,AFUtils.getDBConnection(destino));
                            Connection con2=AFUtils.getInstance().getDBConnection(destino);
                            PreparedStatement ps=con2.prepareStatement("insert into wbpflow(id,idtm,title,description,created,xml,lastupdate,idadm) values(?,?,?,?,?,?,?,?)");
                            ps.setInt(1,id);
                            ps.setString(2,topicmap);
                            ps.setString(3,title);
                            ps.setString(4,description);
                            ps.setTimestamp(5,rs.getTimestamp("created"));
                            ps.setString(6,AFUtils.getInstance().DomtoXml(xml));
                            ps.setTimestamp(7,rs.getTimestamp("lastupdate"));
                            ps.setString(8,rs.getString("idadm")+"_wb");
                            ps.execute();
                            ps.close();
                            con2.close();
                        }
                        rs.close();
                        st.close();
                    }
                    else
                    {
                        %> <br>No se encontro la conexión <%=origen%><%
                    }
                }
                catch(SQLException e)
                {
                    AFUtils.log(e);
                }
                finally
                {
                    if(con!=null)
                    {
                        try
                        {
                            con.close();
                        }
                        catch(Exception e){}
                    }
                }     

                // modifica occurrencias en flujo
                con=null;
                try
                {
                    String destino="wb";
                    String origen="origen";
                    if(request.getParameter("conexion1")!=null && !request.getParameter("conexion1").equals(""))
                    {
                        origen=request.getParameter("conexion1");
                    }
                    if(request.getParameter("conexion2")!=null && !request.getParameter("conexion2").equals(""))
                    {
                        destino=request.getParameter("conexion2");
                    }
                    con=AFUtils.getInstance().getDBConnection(origen);
                    if(origen!=null)
                    {
                        PreparedStatement ps=con.prepareStatement("select * from wboccurrence");
                        ResultSet rs=ps.executeQuery();
                        while(rs.next())
                        {
                            String occurrenceid=rs.getString("id");
                            String topicmapid=rs.getString("idtm");
                            String topicid=rs.getString("idtp");

                            String step=rs.getString("step");
                            String status=rs.getString("status");
                            String ActivityName=null;
                            int  Newstatus=0;
                            
                            if(step.equals("0"))
                            {
                                // esta por enviar o rechazado al generador de contenido
                                if(status.equals("1"))  // rechazado al autor de contenido
                                {
                                    Newstatus=-1;                                                
                                    Connection con2=AFUtils.getInstance().getDBConnection(destino);    
                                    PreparedStatement psupdate=con2.prepareStatement("update wboccurence set step=?, status=?,flow=? where id=? and idtm=?");
                                    psupdate.setString(1,null);
                                    psupdate.setInt(2,Newstatus); 
                                    psupdate.setString(3,null);
                                    psupdate.setString(4,occurrenceid);
                                    psupdate.setString(5,topicmap);
                                    psupdate.executeUpdate();
                                    occurrences++;
                                    psupdate.close();
                                    con2.close();
                                }
                            }
                            else if (step.equals("1"))
                            {
                                // siempre esta aurotizado en este paso
                                Newstatus=2;                                                
                                Connection con2=AFUtils.getInstance().getDBConnection(destino);    
                                PreparedStatement psupdate=con2.prepareStatement("update wboccurence set step=?, status=?,flow=? where id=? and idtm=?");
                                psupdate.setString(1,null);
                                psupdate.setInt(2,Newstatus); 
                                psupdate.setString(3,null);
                                psupdate.setString(4,occurrenceid);
                                psupdate.setString(5,topicmap);
                                psupdate.executeUpdate();
                                occurrences++;
                                psupdate.close();
                                con2.close();
                            }
                            else
                            {
                                String idflow="1";  // debe encontrar primero cual es el flujo asociado TODO:
                                
                                PFlowSrv pflowsvr=new PFlowSrv();
                                com.infotec.topicmaps.TopicMap map=TopicMgr.getInstance().getTopicMap(topicmap);                                
                                Iterator it=pflowsvr.getFlowsToSendContent(map.getTopic(topicid).getOccurrence(occurrenceid));
                                if(it.hasNext())
                                {
                                    PFlow pflow=(PFlow)it.next();
                                    Vector nodes=new Vector();
                                    PreparedStatement psflow=con.prepareStatement("select * from wbpflow where id=?");
                                    psflow.setInt(1,Integer.parseInt(idflow));
                                    ResultSet rsflow=psflow.executeQuery();
                                    while(rsflow.next())
                                    {
                                        Document docxml=AFUtils.getInstance().XmltoDom(rsflow.getString("xml"));

                                        NodeList nodesl=docxml.getElementsByTagName("node");
                                        for(int i=0;i<nodesl.getLength();i++)
                                        {   
                                            nodes.add(nodesl.item(i));
                                        }                                   
                                    }
                                    String newFlowId=pflow.getId()+"|"+pflow.getTopicMapId()+"|1";
                                    String activityName=getActivityName(step,nodes);
                                    Newstatus=2;     
                                    if(activityName!=null)
                                    {
                                        if(status.equals("0")) // en revision
                                        {
                                            Newstatus=1;
                                        }
                                        else if(status.equals("1")) // rechazado
                                        {
                                            Newstatus=3;
                                        }
                                        else if(status.equals("2")) // autorizado en revision
                                        {
                                            Newstatus=1;
                                        }
                                        Connection con2=AFUtils.getInstance().getDBConnection(destino);    
                                        PreparedStatement psupdate=con2.prepareStatement("update wboccurence set step=?, status=?,flow=? where id=? and idtm=?");
                                        psupdate.setString(1,activityName);
                                        psupdate.setInt(2,Newstatus); 
                                        psupdate.setString(3,newFlowId);
                                        psupdate.setString(4,occurrenceid);
                                        psupdate.setString(5,topicmap);
                                        psupdate.executeUpdate();
                                        occurrences++;
                                        psupdate.close();
                                        con2.close();
                                    }
                                }
                            }
                        }
                        ps.close();
                    }
                    else
                    {
                        %> <br>No se encontro la conexión <%=origen%><%
                    }
                }
                catch(Exception e)
                {
                }
                finally
                {
                    try
                    {
                        if(con!=null)
                        {
                            con.close();
                        }
                    }catch(Exception e){}
                }
    %>
        <br>Se agregaron <%=flujos%> flujos de publicación en el sitio <%=topicmap%>
        <br>Se modificaron <%=occurrences%> contenidos, para actualizar su estado en flujo

    <%
    }
    else
    {
    %>
        <br>Falta el parámetro tm
    <%
    }
}
catch(Exception e)
{
    AFUtils.log(e);
}
%>


<%!

class ResourceType
{
    private String name;
    private String type;   
    private String topicmap;
}
public static void Transform2To3(int id,String topicmapID,String title2,String description,Document workflow2,Document workflow3,Vector types,Connection destino)
{
       Element eWorkflows=workflow3.createElement("workflows");
       workflow3.appendChild(eWorkflows);
       Element eWorkflow=workflow3.createElement("workflow");
       eWorkflows.appendChild(eWorkflow);   
       
       Element eDescriptionW=workflow3.createElement("description");
       eDescriptionW.appendChild(workflow3.createTextNode(title2));
       eWorkflow.appendChild(eDescriptionW);   

       eWorkflow.setAttribute("name", title2);
       eWorkflow.setAttribute("canEdit", "true");
       eWorkflow.setAttribute("id", id+"_"+topicmapID);
       eWorkflow.setAttribute("version", "1.0");
            
       Element eDescription=workflow3.createElement("description");
       eDescription.appendChild(workflow3.createTextNode(description));
       
       Vector vnodes=new Vector();
       
       NodeList nodes=workflow2.getElementsByTagName("node");
       for(int i=0;i< nodes.getLength();i++)
       {
           Element node=(Element)nodes.item(i);           
           vnodes.add(node);           
       }
       
       // obtiene la primera actividad
       String nextActivity=getAcceptActivityName("0", vnodes);
       String nextActivityID=getAcceptActivityNameID("0", vnodes);
       if(nextActivity!=null)
       {
            Element activity=workflow3.createElement("activity");                  
            activity.setAttribute("days","0");
            activity.setAttribute("hours","0");
            activity.setAttribute("type","Activity");
            activity.setAttribute("name",nextActivity);
            eWorkflow.appendChild(activity);  
            
            Element eDescriptionActivity= workflow3.createElement("description");                  
            eDescriptionActivity.appendChild(workflow3.createTextNode(nextActivity));
            activity.appendChild(eDescriptionActivity);

            Element eActivity=getActivity(nextActivityID,vnodes);
            NodeList users=eActivity.getElementsByTagName("user");
            for(int iUser=0;iUser<users.getLength();iUser++)
            {
                try
                {
                    Element eUser=(Element)users.item(iUser);
                    String user_id=eUser.getFirstChild().getNodeValue();
                    PreparedStatement ps=destino.prepareStatement("select * from wbuser where usrId=? and usrRepository=?");
                    ps.setInt(1,Integer.parseInt(user_id));
                    ps.setString(2,"wb");
                    ResultSet rs=ps.executeQuery();
                    while(rs.next())
                    {
                        user_id+="_wb";
                        eUser=workflow3.createElement("user");
                        eUser.setAttribute("id",user_id);                
                        eUser.setAttribute("repository","wb");                
                        eUser.setAttribute("name",rs.getString("usrFirstName")+" " +rs.getString("usrLastName"));                
                        activity.appendChild(eUser);
                    }
                    rs.close();
                    ps.close();   
                }             
                catch(Exception e)
                {
                       AFUtils.log(e);
                }
            }            

            addNextActivities(nextActivityID,vnodes,eWorkflow,destino);
            try
            {
                destino.close();
            }   
            catch(Exception e)
            {
                AFUtils.log(e);
            }


       }             
       
       Element activity=workflow3.createElement("activity");                  
       activity.setAttribute("name","Content author");
       activity.setAttribute("type","AuthorActivity");
       eWorkflow.appendChild(activity);  
       
       activity=workflow3.createElement("activity");                  
       activity.setAttribute("name","Finish publish flow");
       activity.setAttribute("type","EndActivity");
       eWorkflow.appendChild(activity);  
       
       // area links
       
       // autorizaciones
             
       while(nextActivityID!=null && nextActivity!=null)
       {
            String from=nextActivity;
            nextActivity=getAcceptActivityName(nextActivityID,vnodes);
            nextActivityID=getAcceptActivityNameID(nextActivityID,vnodes);
            if(nextActivity!=null && nextActivityID!=null)
            {
                if(isEnd(nextActivityID))
                {
                    Element link=workflow3.createElement("link");                  
                    link.setAttribute("authorized","true");
                    link.setAttribute("publish","false");
                    link.setAttribute("from",from);
                    link.setAttribute("to","Finish publish flow");
                    link.setAttribute("type","authorized");
                    eWorkflow.appendChild(link);  

                    Element service=workflow3.createElement("service");
                    service.appendChild(workflow3.createTextNode("mail"));
                    link.appendChild(service);

                    service=workflow3.createElement("service");
                    service.appendChild(workflow3.createTextNode("authorize"));
                    link.appendChild(service);
                }
                else
                {
                    Element link=workflow3.createElement("link");                  
                    link.setAttribute("authorized","true");
                    link.setAttribute("publish","false");
                    link.setAttribute("from",from);
                    link.setAttribute("to",nextActivity);
                    link.setAttribute("type","authorized");
                    eWorkflow.appendChild(link);  

                    Element service=workflow3.createElement("service");
                    service.appendChild(workflow3.createTextNode("mail"));
                    link.appendChild(service);

                    /*service=workflow3.createElement("service");
                    service.appendChild(workflow3.createTextNode("authorize"));
                    link.appendChild(service);*/
                }
            }
       }
       
       // rechazos
       
       nextActivity=getAcceptActivityName("0", vnodes);
       nextActivityID=getAcceptActivityNameID("0", vnodes);
       
       for(int i=0;i<vnodes.size();i++)
       {
           Element node=(Element)vnodes.elementAt(i);
           String idnode=node.getAttribute("id");
           if(!(isEnd(idnode) || isFirst(idnode)))
           {
               if(node.getElementsByTagName("reject").getLength()>0)
               {
                   String idreject=((Element) node.getElementsByTagName("reject").item(0)).getFirstChild().getNodeValue();
                   if(isFirst(idreject))
                   {
                        Element link=workflow3.createElement("link");                  
                        link.setAttribute("authorized","false");
                        link.setAttribute("publish","false");
                        link.setAttribute("from",getActivityName(idnode,vnodes));
                        link.setAttribute("to","Content author");
                        link.setAttribute("type","unauthorized");
                        eWorkflow.appendChild(link);  

                        Element service=workflow3.createElement("service");
                        service.appendChild(workflow3.createTextNode("mail"));
                        link.appendChild(service);

                        service=workflow3.createElement("service");
                        service.appendChild(workflow3.createTextNode("noauthorize"));
                        link.appendChild(service);
                   }
                   else if(isEnd(idreject))
                   {
                        Element link=workflow3.createElement("link");                  
                        link.setAttribute("authorized","false");
                        link.setAttribute("publish","false");
                        link.setAttribute("from",getActivityName(idnode,vnodes));
                        link.setAttribute("to","Finish publish flow");
                        link.setAttribute("type","unauthorized");
                        eWorkflow.appendChild(link);  

                        Element service=workflow3.createElement("service");
                        service.appendChild(workflow3.createTextNode("mail"));
                        link.appendChild(service);

                        service=workflow3.createElement("service");
                        service.appendChild(workflow3.createTextNode("noauthorize"));
                        link.appendChild(service);
                   }
                   else
                   {
                       if(getAcceptActivityName(idnode,vnodes)!=null && getAcceptActivityName(idreject,vnodes)!=null)
                       {
                            Element link=workflow3.createElement("link");                  
                            link.setAttribute("authorized","false");
                            link.setAttribute("publish","false");
                            link.setAttribute("from",getActivityName(idnode,vnodes));
                            link.setAttribute("to",getActivityName(idreject,vnodes));
                            link.setAttribute("type","unauthorized");
                            eWorkflow.appendChild(link);  

                            Element service=workflow3.createElement("service");
                            service.appendChild(workflow3.createTextNode("mail"));
                            link.appendChild(service);

                            service=workflow3.createElement("service");
                            service.appendChild(workflow3.createTextNode("noauthorize"));
                            link.appendChild(service);
                       }
                   }
               }
           }
           
       }
       
       
       // tipo de recurso
        for(int i=0;i<types.size();i++)
        {
           ResourceType type=(ResourceType)types.elementAt(i);
           Element resourceType=workflow3.createElement("resourceType");                  
           resourceType.setAttribute("id",type.type);
           resourceType.setAttribute("name",type.name);
           resourceType.setAttribute("topicmap",type.topicmap);
           eWorkflow.appendChild(resourceType);  
        }
       
       //<resourceType id="9" name="LocalContent" topicmap="WBAGlobal"/>
       
   }  
   private static void addNextActivities(String idfirstId,Vector nodes,Element eWorkflow,Connection destino)
   {
       for(int i=0;i<nodes.size();i++)
       {
           Element node=(Element)nodes.elementAt(i);
           if(!(node.getAttribute("id").equals(idfirstId) || node.getAttribute("id").equals("0")))
           {
                String id=node.getAttribute("id");
                if(!isEnd(id))
                {
                    Element activity=eWorkflow.getOwnerDocument().createElement("activity");                  
                    String name=((Element)node.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
                    activity.setAttribute("days","0");
                    activity.setAttribute("hours","0");
                    activity.setAttribute("type","Activity");
                    activity.setAttribute("name",name);
                    
                    Element eDescriptionActivity= eWorkflow.getOwnerDocument().createElement("description");                  
                    eDescriptionActivity.appendChild(eWorkflow.getOwnerDocument().createTextNode(name));
                    activity.appendChild(eDescriptionActivity);
            
                    NodeList users=node.getElementsByTagName("user");
                    for(int iUser=0;iUser<users.getLength();iUser++)
                    {
                        try
                        {
                            Element eUser=(Element)users.item(iUser);
                            String user_id=eUser.getFirstChild().getNodeValue();
                            PreparedStatement ps=destino.prepareStatement("select * from wbuser where usrId=? and usrRepository=?");
                            ps.setInt(1,Integer.parseInt(user_id));
                            ps.setString(2,"wb");
                            ResultSet rs=ps.executeQuery();
                            while(rs.next())
                            {

                                user_id+="_wb";
                                eUser=eWorkflow.getOwnerDocument().createElement("user");
                                eUser.setAttribute("id",user_id);                
                                eUser.setAttribute("repository","wb");                
                                eUser.setAttribute("name",rs.getString("usrFirstName")+" "+rs.getString("usrLastName"));                
                                activity.appendChild(eUser);
                            }
                            rs.close();
                            ps.close();  
                        }
                        catch(Exception e)
                        {
                            AFUtils.log(e);
                        }

                        /*String user_id=eUser.getFirstChild().getNodeValue()+"_wb";
                        eUser=eWorkflow.getOwnerDocument().createElement("user");
                        eUser.setAttribute("id",user_id);
                        activity.appendChild(eUser);*/
                    }

                    eWorkflow.appendChild(activity);  
                }
           }
       }
   }
   private static String getAcceptActivityNameID(String id,Vector nodes)
   {
       for(int i=0;i<nodes.size();i++)
       {
           Element element=(Element)nodes.elementAt(i);           
           if(element.getAttribute("id").equals(id))
           {
               if(element.getElementsByTagName("accept").getLength()>0)
               {
                String idnext=((Element)element.getElementsByTagName("accept").item(0)).getFirstChild().getNodeValue();               
                if(idnext!=null)
                {
                    return idnext;
                }
               }
           }
       }
       return null;
   }
    private static Element getActivity(String id,Vector nodes)
   {
       for(int i=0;i<nodes.size();i++)
       {
           Element element=(Element)nodes.elementAt(i);           
           if(element.getAttribute("id").equals(id))
           {
               return element;
           }
       }
       return null;
   }
   private static String getAcceptActivityName(String id,Vector nodes)
   {
       for(int i=0;i<nodes.size();i++)
       {
           Element element=(Element)nodes.elementAt(i);           
           if(element.getAttribute("id").equals(id))
           {
               if(element.getElementsByTagName("accept").getLength()>0)
               {
                   String idnext=((Element)element.getElementsByTagName("accept").item(0)).getFirstChild().getNodeValue();
                   if(idnext!=null)
                   {
                        return getActivityName(idnext, nodes);
                   }
               }
           }
       }
       return null;
   }
   private static String getRejectActivityName(String id,Vector nodes)
   {
       for(int i=0;i<nodes.size();i++)
       {
           Element element=(Element)nodes.elementAt(i);
           if(element.getAttribute("id").equals(id))
           {
               if(element.getElementsByTagName("reject").getLength()>0)
               {
                String idnext=((Element)element.getElementsByTagName("reject").item(0)).getFirstChild().getNodeValue();
                if(idnext!=null)
                {
                    return getActivityName(idnext, nodes);
                }
               }
           }
       }
       return null;
   }
   private static boolean isEnd(String id)
   {
       if(id.equals("1"))
       {
           return true;
       }
       return false;
   }
   private static boolean isFirst(String id)
   {
       if(id.equals("0"))
       {
           return true;
       }
       return false;
   }
   private static String getActivityName(String id,Vector nodes)
   {
       for(int i=0;i<nodes.size();i++)
       {
           Element element=(Element)nodes.elementAt(i);
           if(element.getAttribute("id").equals(id))
           {
               if(element.getElementsByTagName("name").getLength()>0)
               {
                return ((Element)element.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
               }
           }
       }
       return null;
   }
%>

</body>
</html>
