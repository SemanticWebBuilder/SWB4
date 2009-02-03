<jsp:useBean id="actionResponse" scope="request" type="org.semanticwb.portal.api.SWBActionResponse"/>
<%
   String tpname=request.getParameter("tpname");
   if(tpname==null)return;
   System.out.println(tpname);
//TODO
/*
   com.infotec.topicmaps.util.IDGenerator gen=new com.infotec.topicmaps.util.IDGenerator();
   String id=gen.getID(tpname,null,false);
   String userid=actionResponse.getUser().getId();
   com.infotec.topicmaps.Topic tp=srv.createTopic(actionResponse.getTopic(),id,null,tpname,userid);
   if(tp==null)
   {
       actionResponse.setRenderParameter("tpname",tpname);
       actionResponse.setRenderParameter("atpe","true");
   }else
   {
        srv.setActive(tp,1,userid);
   }
*/
%>
