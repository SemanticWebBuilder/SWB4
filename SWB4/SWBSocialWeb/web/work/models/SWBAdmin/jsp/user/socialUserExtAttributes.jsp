<%-- 
    Document   : socialUserExtAttributes
    Created on : 19-ago-2013, 16:53:27
    Author     : jorge.jimenez
--%>

<%-- 
    Document   : showPostIn
    Created on : 03-jun-2013, 13:01:48
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.SWBFormMgr" %>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<%
    String userAttrStr=paramRequest.getLocaleString("userAttr");
    if(request.getParameter("suri")==null) return; 
    SemanticObject sObj=SemanticObject.createSemanticObject(request.getParameter("suri")); 
    if(sObj==null) return;
    User user=(User)sObj.createGenericInstance();    
    //Tomando en cuenta que los usuarios siempre seran del sitio de Admin..., es decir, que ningún socialSite tendra usuarios
    //WebSite wsite=SWBContext.getAdminWebSite();
    //SocialUserExtAttributes socialExtAtt = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), wsite);
    //if (socialExtAtt == null) {
    //    socialExtAtt = SocialUserExtAttributes.ClassMgr.createSocialUserExtAttributes(user.getId(), wsite);  
    //}
    HashMap<String, SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
    Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes").listProperties();
    while (list.hasNext()) {
        SemanticProperty sp = list.next();
        mapa.put(sp.getName(),sp);
    }
    boolean usrCanRetopicMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanReTopicMsg"))).booleanValue();
    boolean userCanReValueMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanReValueMsg"))).booleanValue();
    boolean userCanRemoveMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRemoveMsg"))).booleanValue();
    boolean userCanRespondMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRespondMsg"))).booleanValue();
  
    /*
    System.out.println("En Jsp/user:"+user+",socialExtAtt:"+socialExtAtt);
    System.out.println("1:"+socialExtAtt.isUserCanReTopicMsg());
    System.out.println("2:"+socialExtAtt.isUserCanReValueMsg());
    System.out.println("3:"+socialExtAtt.isUserCanRemoveMsg());
    System.out.println("4:"+socialExtAtt.isUserCanRespondMsg());*/
    //boolean isAble2RemoveMsg = false, isAble2RespondMsg = false, isAble2ReValueMsg = false, isAble2ReTopicMsg=false;
    SWBResourceURL acc_url = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_EDIT);    
%>
    Holass--<%=userAttrStr%>:<%=user.getFullName()%>
    <br/><br/><br/><br/>
    <form id="form_<%=user.getId()%>" dojoType="dijit.form.Form" class="swbform" action="<%=acc_url%>" method="post" onsubmit="submitForm('form_<%=user.getId()%>'); return false;"> 
    <input type="hidden" name="scls" value="http://www.semanticwebbuilder.org/swb4/ontology#User"/>
    <input type="hidden" name="smode" value="edit"/>
    <input type="hidden" name="suri" value="<%=user.getURI()%>"/>
    <div id="registro-data1">
        <label for="userCanRemoveMsg"><span>&nbsp;</span>Permiso para Eliminar Mensajes:</label>
        <input type="checkbox" name="userCanRemoveMsg" <%=userCanRemoveMsg?"checked":""%>/>
        <br/> 
        <label for="userCanRespondMsg"><span>&nbsp;</span>Permiso para Responder/Publicar Mensajes:</label>
        <input type="checkbox" name="userCanRespondMsg" <%=userCanRespondMsg?"checked":""%>/> 
        <br/> 
        <label for="userCanReValueMsg"><span>&nbsp;</span>Permiso para Re-Evaluar Mensajes:</label>
        <input type="checkbox" name="userCanReValueMsg" <%=userCanReValueMsg?"checked":""%>/> 
        <br/>  
        <label for="userCanReTopicMsg"><span>&nbsp;</span>Permiso para Cambiar de Tema Mensajes:</label>
        <input type="checkbox" name="userCanReTopicMsg" <%=usrCanRetopicMsg?"checked":""%>/> 
        <br/> 
        
        <br/>
        <div class="clear">&nbsp;</div>
    </div>
    <div class="clear">&nbsp;</div>


   <input type="submit" value="Guardar" class="btn-registro">&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="reset" value="Cancelar" class="btn-registro"/>
  </form>