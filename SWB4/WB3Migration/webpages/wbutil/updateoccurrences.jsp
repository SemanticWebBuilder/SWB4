<%@page contentType="text/html;charset=windows-1252" import="java.util.*,org.w3c.dom.*,com.infotec.topicmaps.*, com.infotec.wb.core.db.*,com.infotec.appfw.util.*,java.sql.*,com.infotec.topicmaps.bean.*,com.infotec.wb.core.*,com.infotec.wb.services.*"%>
<html>
<head><title>Update Occurrences - Actualizaci&oacute;n de ocurrencias - WB3</title></head>
<body>
<font size=3 face=verdana>
Actualizaci&oacute;n de ocurrencias - WB3
<br>
<br>
<%

/*
*  Utilería que actualiza todas las ocurrencias que se encuentran en flujo de publicación
*/

if(request.getParameter("tm")!=null&&request.getParameter("conexion1")!=null&&request.getParameter("conexion2")!=null)
{
try
{
    
    int occurrences=0;
    if(request.getParameter("tm")!=null)
    {
                String topicmap=request.getParameter("tm");

                // modifica occurrencias en flujo

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
                    con=AFUtils.getInstance().getDBConnection(origen);
                    if(origen!=null)
                    {
                        PreparedStatement ps=con.prepareStatement("select * from wboccurrence where idtm=?");
                        ps.setString(1,topicmap);
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
                                    PreparedStatement psupdate=con2.prepareStatement("update wboccurrence set step=?, status=?,flow=? where id=? and idtm=?");
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
                                PreparedStatement psupdate=con2.prepareStatement("update wboccurrence set step=?, status=?,flow=? where id=? and idtm=?");
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
                                
                                PFlowSrv pflowsvr=new PFlowSrv();
                                com.infotec.topicmaps.TopicMap map=TopicMgr.getInstance().getTopicMap(topicmap);                                
                                
                                Topic topic=map.getTopic(topicid);
                                
                                Occurrence occ=topic.getOccurrence(occurrenceid);
                                
                                Iterator it=pflowsvr.getFlowsToSendContent(occ);
                                if(it.hasNext())
                                {                                    
                                    PFlow pflow=(PFlow)it.next();
                                    String idflow=""+pflow.getId();                                      
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
                                        PreparedStatement psupdate=con2.prepareStatement("update wboccurrence set step=?, status=?,flow=? where id=? and idtm=?");
                                        psupdate.setString(1,activityName);
                                        psupdate.setInt(2,Newstatus); 
                                        psupdate.setString(3,newFlowId);
                                        psupdate.setString(4,occurrenceid);
                                        psupdate.setString(5,topicmap);
                                        int res=psupdate.executeUpdate();                                        
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
                    AFUtils.log(e);
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
}
else
{
%>
<br>
Para poder ejecutarse faltan parámetros:
<br>
<br>
<li><b>tm</b>:        identificador del topicmap al cual se le van actualizar las ocurrencias.</li>
<li><b>conexion1</b>: nombre de la conexión de la base de datos origen  (wb2).</li>
<li><b>conexion2</b>: nombre de la conexión de la base de datos destino (wb3).</li>
<br>
<br>
Ejecutarlo de la forma: <font size='4'><b>updateoccurrences.jsp?tm=IDTopiMap&conexion1=origen&conexion2=destino</b></font>
<%
}
%>
</font>
<%!

   
   
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


</body>
</html>
