<%-- 
    Document   : socialTopicMsgOut
    Created on : 23-Abril-2013, 16:54:38
    Author     : Jorge.Jiménez
--%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="static org.semanticwb.social.admin.resources.SocialTopicMsgOut.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
System.out.println("Entra a SocialOut-1");
if(request.getParameter("showSource")!=null)
{
    SemanticObject sObjPostOut=(SemanticObject)request.getAttribute("sObjPostOut");
    System.out.println("Entra a SocialOut-2:"+sObjPostOut);
    if(sObjPostOut.createGenericInstance() instanceof org.semanticwb.social.MessageIn)   
    {
        MessageIn messageIn=(MessageIn)sObjPostOut.createGenericInstance();
        out.println(showData(messageIn, "msg", paramRequest));
    }else if(sObjPostOut.createGenericInstance()  instanceof Photo)
    {
        PhotoIn photoIn=(PhotoIn)sObjPostOut.createGenericInstance();
         out.println(showData(photoIn, "photo", paramRequest));
        //System.out.println("Es una foto");
    }else if(sObjPostOut.createGenericInstance()  instanceof Video)
    {
        VideoIn videoIn=(VideoIn)sObjPostOut.createGenericInstance();
        System.out.println("video:"+videoIn); 
         out.println(showData(videoIn, "video", paramRequest));
        //System.out.println("Es un Video");
    }                    
}else
{
%>
<style type="text/css">
            @import "/swbadmin/js/dojo/dojo/resources/dojo.css";
            @import "/swbadmin/js/dojo/dijit/themes/soria/soria.css";
            @import "/swbadmin/css/swb.css";
            @import "/swbadmin/js/dojo/dojox/grid/resources/soriaGrid.css";
            @import "/swbadmin/js/dojo/dojox/grid/resources/Grid.css";
            html, body, #main{
                overflow: auto;
            }
</style>    
<link rel="stylesheet" type="text/css" media="all" href="/swbadmin/js/dojo/dijit/themes/soria/soria.css" />
<link rel="stylesheet" type="text/css" media="all" href="/swbadmin/css/swb_portal.css" />
<script type="text/javascript" >
    var djConfig = {
        parseOnLoad: true,
        isDebug: false
    };
</script>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" ></script>
<script type="text/javascript" src="/swbadmin/js/swb.js" ></script>    

<%
    String objUri = request.getParameter("suri");
    SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance();    
    String wsiteId = socialTopic.getSemanticObject().getModel().getName();
    //System.out.println("wsiteId:" + wsiteId);
    //WebSite wsite=WebSite.ClassMgr.getWebSite(wsiteId);
    //System.out.println("wsite-J1:"+wsite.getId()); 
    
//////////////////////
int ipage;
try {
    ipage = Integer.parseInt(request.getParameter("ipage"));
}catch (NumberFormatException nfe) {
    ipage = 1;
}
Iterator<PostOut> itposts = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic);  
long el = SWBUtils.Collections.sizeOf(itposts);
//System.out.println("el:"+el);
long paginas = el / PAGE_SIZE;
if(el % PAGE_SIZE != 0) {
    paginas++;
}
long inicio = 0;
long fin = PAGE_SIZE;

inicio = (ipage * PAGE_SIZE) - PAGE_SIZE;
fin = (ipage * PAGE_SIZE);

if(ipage < 1 || ipage > paginas) {
    ipage = 1;
}
if(inicio < 0) {
    inicio = 0;
}
if(fin < 0) {
    fin = PAGE_SIZE;
}
if(fin > el) {
    fin = el;
}
if(inicio > fin) {
    inicio = 0;
    fin = PAGE_SIZE;
}
if(fin - inicio > PAGE_SIZE) {
    inicio = 0;
    fin = PAGE_SIZE;
}
inicio++;
//System.out.println("wsite-J2:"+wsiteId);
SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
//System.out.println("wsiteId en Jsp de PostSummary:"+wsiteId);
url.setParameter("wsite", wsiteId);
url.setParameter("sTopic", socialTopic.getId());
url.setParameter("inicio", inicio+"");
url.setParameter("fin", fin+"");       
url.setParameter("ipage", ipage+"");
out.println("<script type=\"text/javascript\">");        

out.println(" dojo.require('dojox.grid.DataGrid');");
out.println(" dojo.require('dojo.data.ItemFileReadStore');");

out.println("dojox.grid.cells.dijit");

out.println(" function fillGrid(grid, uri) {");               
out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri});");
out.println("   grid._refresh();");
out.println(" }");
        
out.println("function showPopUp(value,pageURL,title, w, h) {");
//out.println("alert('value en showPopUp:'+value)");
out.println(" var left = (screen.width/2)-(w/2);");
out.println(" var top = (screen.height/2)-(h/2);");
out.println(" var targetWin = window.open (pageURL+'?postUri='+value, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);");
out.println(" return false;");
out.println("}");
out.println("function postInSource(value) {");
//out.println("alert('valueJ0:'+value)");
out.println("  return value;");
out.println("}");

out.println("sourcePostUri = function(value) {");
out.println(" return value;");
out.println("}");
        
out.println(" var layout= null;");
out.println(" var jStrMaster = null;");
out.println(" var gridMaster = null;");
out.println(" var gridResources = null;");

out.println(" dojo.addOnLoad(function() {");
out.println("   layout= [");
out.println("      { field:'fl',  width:'2%', name:'Num', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'type', width:'6%', name:'Tipo', styles:'font-size:11px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'element',width:'6%',name:'Elemento', styles:'font-size:11px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'nets', width:'43%',name:'Redes de envio', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'isShared', width:'43%',name:'Difundido', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'postIn',width:'5%', name:'Origen', formatter:function(value){var artf=postInSource(value);if(artf!='---'){return '<a href=\"javascript:void(0)\" onclick=\"showPopUp(\\''+escape(artf)+'\\',\\'"+paramRequest.getRenderUrl().setMode(Mode_SOURCE).setCallMethod(SWBResourceURL.Call_DIRECT) +"\\',\\'Origen\\',500,250)\" >Origen</a>';}else {return '---';};}, styles:'text-align:center;font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },"); 
out.println("      { field:'crated', width:'43%',name:'Fecha', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'crator', width:'43%',name:'Usuario', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'pflow', width:'43%',name:'Flujo', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' },");
out.println("      { field:'step', width:'43%',name:'Paso', styles:'font-size:10px;', headerStyles:'font-size:11px;text-align:center;' }");
out.println("   ];");

out.println("   gridMaster = new dojox.grid.DataGrid({");
//out.println("      escapeHTMLInData: 'true',");     
//out.println("      preload: 'true',");s
out.println("      id: 'gridMaster',");
out.println("      structure: layout,");
//out.println("      rowSelector: '10px',");
out.println("      rowsPerPage: '25'"); 
out.println("   }, 'gridMaster');");
out.println("   gridMaster.startup();");

out.println("   fillGrid(gridMaster, '"+url.setMode(Mode_JSON)+"');");
out.println(" });");
out.println("</script>");
out.println("<p style=\"font-size:12px; font-weight:bold; text-align:center\">Total: "+el+"</p>");
out.println("<div id=\"ctnergrid\" style=\"height:580px; width:98%; border: 1px solid #DAE1FE; text-align:center;\">");
out.println("  <div id=\"gridMaster\"></div>");        
out.println("</div>");
    
// paginación
if(paginas > 1) {
    SWBResourceURL pagURL = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW);
    StringBuilder html = new StringBuilder();
    html.append("<div class=\"\"  style=\"text-align:center\">");
    String nextURL = "#";
    String previusURL = "#";
    if (ipage < paginas) {
        nextURL = paramRequest.getRenderUrl().setParameter("ipage", Integer.toString(ipage+1)).setParameter("wsite", wsiteId).setParameter("doView", "doView").setParameter("suri", objUri).toString();        
    }
    if (ipage > 1) {
        previusURL = paramRequest.getRenderUrl().setParameter("ipage", Integer.toString(ipage-1)).setParameter("wsite", wsiteId).setParameter("doView", "doView").setParameter("suri", objUri).toString();
    }
    if (ipage > 1) {
        html.append("<a class=\"amfr-pagi\" href=\""+previusURL+"\">Anterior</a>&nbsp;");
    }
    for (int i=ipage-2<=0?1:ipage-2; i<=ipage+2; i++)
    {
        if(i <= paginas){
            if(i==ipage)
                html.append("<strong>");
            else
                html.append("<a class=\"amfr-pagi\" href=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("ipage", Integer.toString(i)).setParameter("wsite", wsiteId).setParameter("doView", "doView").setParameter("suri", objUri) +"\">");
            html.append(i);
            if(i==ipage)
                html.append("</strong>&nbsp;");
            else
                html.append("</a>&nbsp;");
        }
    }
    if (ipage != paginas)
    {
        html.append("<a class=\"amfr-pagi\" href=\""+nextURL+"\">Siguiente</a>");
    }
    html.append("</div>");
    out.println(html);
}
}
%>

<%!
private String showData(PostIn postIn, String postOutType, SWBParamRequest paramRequest)
{
    if(postIn==null) return null;
    StringBuilder strb=new StringBuilder();  
    try
    {
        strb.append("<table border=\"0\" cellspacing=\"5\">");
        strb.append("<tr>");
        strb.append("<td>");
        strb.append(postOutType.equals("msg")?paramRequest.getLocaleString("message"):postOutType.equals("photo")?paramRequest.getLocaleString("photo"):postOutType.equals("video")?paramRequest.getLocaleString("video"):"---");
        strb.append("</td>");
        strb.append("</tr>");
        strb.append("<tr>");
        strb.append("<td>");
        if(postIn instanceof  MessageIn)
        {
            MessageIn msgIn=(MessageIn)postIn;
            strb.append(msgIn.getMsg_Text()!=null?msgIn.getMsg_Text():"---");
        }else if(postIn instanceof  PhotoIn)
        {
            PhotoIn photoIn=(PhotoIn)postIn;
            strb.append(photoIn.getPhoto());
            strb.append("<br>"+photoIn.getMsg_Text()!=null?photoIn.getMsg_Text():"---");
        }else if(postIn instanceof  VideoIn)
        {
            VideoIn videoIn=(VideoIn)postIn;
            strb.append(videoIn.getVideo());
            strb.append("<br>"+videoIn.getMsg_Text()!=null?videoIn.getMsg_Text():"---");
        }
        strb.append("</td>");
        strb.append("</tr>");
      strb.append("</table>");  
        
    }catch(Exception e)
    {
        log.error(e); 
    }
    
    return strb.toString();
}

%>