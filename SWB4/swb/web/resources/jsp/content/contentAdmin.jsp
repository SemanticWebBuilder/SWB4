<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.Portlet"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
     Portlet base=paramRequest.getResourceBase();
     SWBResourceURL url=paramRequest.getRenderUrl();
     url.setCallMethod(url.Call_DIRECT);
     url.setMode("upload");
     String action=paramRequest.getAction();
     System.out.println("action:"+action);
     if(action.equals(url.Action_ADD)){
%>
<form  method="POST" enctype="multipart/form-data" action="<%=url.toString()%>">
<table width="50%">
    <tr>
        <td>
            Archivo a subir(html)
        </td>
        <td>
            <input type="file" name="filecontent"/>
        </td>        
    </tr>
    <tr>
        <td>
            <input type="submit" value="enviar"/>
        </td>
    </tr>
</table>
</form>
<%
}else if(action.equals(url.Action_EDIT)){
    System.out.println("entra editJ1:"+base);
    try{
        //System.out.println("entra editJ1:"+base.getLastVersion().getVersionNumber());
    }catch(Exception e){
        e.printStackTrace();
    }
    String path=base.getWorkPath() + "/1/index.htm";
    System.out.println("rutaJ:" + path);
    String content="";
    try{
        content=SWBUtils.IO.readInputStream(SWBPlatform.getFileFromWorkPath(path));     
    }catch(Exception e){
        e.printStackTrace();
    }
%>     
    <script type="text/javascript">
       dojo.require("dojo.parser");
       dojo.require("dijit.Editor");
     </script>
    <body class="tundra">
    <form method="post">
    <textarea name="field" width="200px" dojoType="dijit.Editor">
            <%=content%>
    </textarea>
    <input type="submit" value="Save" />
    </form>
    </body>
<%
}
%>


