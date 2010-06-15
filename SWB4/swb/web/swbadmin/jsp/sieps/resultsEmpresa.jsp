
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.scian.Clase"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.sieps.search.SearchResource"%>

<%@page import="org.semanticwb.sieps.EmpresaInteres"%>
<%@page import="org.semanticwb.platform.SemanticObject"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.sieps.Empresa"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.List"%>
<%@page import="com.hp.hpl.jena.rdf.model.*"%>

<%!
    public String getLocaleProperty(org.semanticwb.platform.SemanticObject obj,org.semanticwb.platform.SemanticProperty prop, String lang)
    {
        String ret = null;
        if (lang == null)
        {
            ret = obj.getProperty(prop);
        }else
        {
            ret= obj.getProperty(prop, null, lang);
            if(ret==null)
            {
                ret=obj.getProperty(prop);
            }
        }
        return ret;
    }
%>
<%!
    public boolean isEmpresasInteres(User user, SWBModel model, String uriEmpresa) {
    	 boolean isEmprInteres = false;

         try
         {        	 
             Iterator<EmpresaInteres> interes = EmpresaInteres.ClassMgr.listEmpresaIntereses();

             if (interes != null)
             {
                 while (interes.hasNext())
                 {
                     EmpresaInteres empresaInteres = interes.next();
                     if (empresaInteres != null) {
                         User userInteres = empresaInteres.getUsuario();
                         Empresa e = empresaInteres.getEmpresa();
                         if (userInteres.equals(user)) {                        	 
	                         if (e.getId().equals(uriEmpresa)) {
	                        	 isEmprInteres = true;
	                         }
                         }
                     }
                 }
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         return isEmprInteres;
    }
%>
<script type="text/javascript" src="/swbadmin/jsp/sieps/sieps.js"></script>
<style>
	div#qTip {
	 padding: 3px;
	 border: 1px solid #666;
	 border-right-width: 2px;
	 border-bottom-width: 2px;
	 display: none;
	 background: #999;
	 color: #FFF;
	 font: bold 9px Verdana, Arial, sans-serif;
	 text-align: left;
	 position: absolute;
 	 z-index: 1000;
	 
	}
</style>

<script type="text/javascript" src="/swbadmin/jsp/sieps/sieps.js"></script>
<script type="text/javascript" src="/swbadmin/jsp/sieps/qtips.js"></script>

<%
	String query 				= 	request.getParameter("query");
	String queryAttr 			= 	(String)request.getAttribute("query");
	SWBResourceURL urlDetail 	= 	paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	
	SWBResourceURL 	urlGuardaBusqueda 	= 	paramRequest.getActionUrl().setAction("guardaConsulta"),
					urlGuardaEmpresas 	= 	paramRequest.getActionUrl().setAction("guardaEmpresas");
	
	List<Empresa> empresas 		= 	(List<Empresa>)request.getAttribute("results");
	Boolean isAllEmpInt 		= 	(Boolean)request.getAttribute("isAllEmpInt");
	
	boolean isResultados   	= 	(empresas != null &&  !empresas.isEmpty());
	String mensaje		   	= 	request.getParameter("mensaje")!= null ? request.getParameter("mensaje") : "";
	
	/** XXX:Implementación para fines del demo. Replantear funcionalidad para un ambiente productivo...**/
		
	User user				= 	paramRequest.getUser();
	SWBModel webSite		=	paramRequest.getWebPage().getWebSite();
	
	String urlImages		=	"/swbadmin/jsp/"+paramRequest.getWebPage().getWebSiteId()+"/images/";
	
	boolean isUser				=	(user != null && user.isSigned());
	boolean isBusquedaCarpeta 	= 	SearchResource.isQueryInCarpeta(queryAttr, user, webSite);	
			mensaje 			= 	isBusquedaCarpeta ? mensaje : "";		
%>
<div id="resultadosBusqueda">
<% if (queryAttr != null && queryAttr.length() > 0) { %>
	<p id="resultadosBusquedaTop"><strong>Usted buscó:</strong><%=queryAttr%></p>
<% } %>
<% if (isResultados) {%>
      <h2 class="tableH2">Resultados de búsqueda</h2>
      <form id="formTableRes" method="post" action="">
      	<input type="hidden" id="currentQuery" name="currentQuery" value="<%=queryAttr%>"/>
        <p>
          <input type="checkbox" name="checkAllDescrip" id="checkAllDescrip"  onclick="javascript:desplieguaTodasDescripcion(this);"/>
          <label for="check1">Vista breve de todos los resultados</label>
		  <% if (isUser && !isAllEmpInt) { %>	
	          <input type="checkbox" name="checkAllEmpresas" id="checkAllEmpresas"  onclick="javascript:desplieguaTodasEmpresas(this);"/>
	          <label for="check4">Selecciona la empresa</label>
          <% } %>
        
        </p>
      
	  <table id="tablaResultados">
          <tr>
            <th>Código</th>
            <th>Subrama</th>
            <th>Actividad</th>
            <th>Empresa</th>
            <th colspan="2">Ubicación</th>
          </tr>
          <%          	
          	for (int i = 0; i< empresas.size(); i++) {
        	  Empresa e = empresas.get(i);
        	  String estiloRow = (i % 2 == 0) ? "odd" : "even";     
        	  String subcategoria="Sin subcategoria";
        	  String categoria="Sin categoria";
        	  
        	  Clase clase=e.getScian();
        	  if(clase!=null)
        	  {
                    subcategoria=clase.getSemanticObject().getLabel(paramRequest.getUser().getLanguage());
                    Resource res=clase.getSemanticObject().getRDFResource();

                    /*Iterator<Statement> it=res.getModel().listStatements(res,null,(String)null);
                    while (it.hasNext())
                    {
                        Statement s = it.next();
                        out.println("Stmt:"+s);
                    }*/

                   
                    org.semanticwb.platform.SemanticProperty prop=paramRequest.getWebPage().getSemanticObject().getModel().getSemanticProperty(org.semanticwb.platform.SemanticVocabulary.RDFS_SUBCLASSOF);
                    Iterator<org.semanticwb.platform.SemanticObject> parents=clase.getSemanticObject().listObjectProperties(prop);
                    org.semanticwb.platform.SemanticObject parent=null;
                    while(parents.hasNext())
                    {
                        org.semanticwb.platform.SemanticObject temp=parents.next();
                        if(org.semanticwb.scian.SubRama.sclass.equals(temp.getSemanticClass()))
                        {                            
                            parent=temp;
                            break;
                        }
                    }

                    if(parent!=null)
                    {
                        categoria=parent.getLabel(paramRequest.getUser().getLanguage());
                    }
                    if(subcategoria==null)
                    {
                            subcategoria="Sin subcategoria";
                    }
              }
          %>  
          	<tr>          		
	            <td onclick='javascript: document.location ="<%=urlDetail.setParameter("uri", e.getEncodedURI())%>"' style="cursor: hand;" class="<%=estiloRow%> bold"><%=e.getScian().getCode()%></td>
	            <td class="<%=estiloRow%> bold"><%=categoria%></td>
	            <td class="<%=estiloRow%> bold"><%=subcategoria%></td>            
	            <td onclick='javascript: document.location ="<%=urlDetail.setParameter("uri", e.getEncodedURI())%>"' style="cursor: hand;" class="<%=estiloRow%> bold"><%=e.getName()%></td>            
	            <td class="<%=estiloRow%> bold"><%=e.getEstado()%></td>
	            <td class="<%=estiloRow%>">            
            	<% if (isUser && !isEmpresasInteres(user, webSite, e.getURI())) {%>
              		<input type="checkbox" name="chkEmpresas" id="chkEmpresas" value="<%=e.getURI()%>" onclick="javascript:cambiaEstadoSelectAllEmpresas(this);"/>
            	<% } else if (isUser){%>
            		<img src="<%=urlImages%>favorites.png" width="16" height="16" alt="" />
            	<% } else { %>
            		&nbsp;
            	<% } %>
	           </td> 	
	            <!--  <td class="">&nbsp;</td> -->	          
           </tr>
           <tr id="rowBullets<%=i%>">
	            <td id="cellBulletDescrip<%=i%>" class="<%=estiloRow%>"><a id="anchor<%=i%>" href="javascript:desplieguaDescripcion('anchor<%=i%>', 'cellDescrip<%=i%>');");"><img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" />Abrir detalle</a></td>
	            <td class="<%=estiloRow%>"><a href="#" title='  <iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=es&amp;geocode=&amp;q=Toriello+Guerra,+Tlalpan,+M%C3%A9xico&amp;sll=37.0625,-95.677068&amp;sspn=47.885545,79.013672&amp;ie=UTF8&amp;hq=&amp;z=14&amp;ll=19.29476,-99.166373&amp;output=embed"></iframe>'><img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" /> Ver mapa</a>
	            </td>
	            <td colspan="5" class="<%=estiloRow%>">&nbsp;</td>
          </tr>
          <tr id="rowDescrip<%=i%>">
            <td id="cellDescrip<%=i%>" class="<%=estiloRow%> tableHide" colspan="6"><%=e.getDescripcion()!=null ? e.getDescripcion() : "No disponible"%></td>
          </tr>
		  <%}%>		 
	</table>
	<p class="centrar">
	<% if (isUser && !isAllEmpInt){ %>
    	<input type="button" name="btnSendEmpresa" id="btnSendEmpresa" value="Enviar Empresa(s) a mi Carpeta" class="btn-bigger" onclick="javascript:enviarEmpresasInteres('<%=urlGuardaEmpresas%>', this); "/>
    <% } %>	
	<% if (isUser && !isBusquedaCarpeta){ %>    	
    	<input type="button" name="btnSendConsulta" id="btnSendConsulta" value="Enviar Consulta a mi Carpeta" class="btn-bigger" onclick="javascript:enviarBusquedas('<%=urlGuardaBusqueda%>', this); "/>
    <% } %>	
	</p>  	        	 
	</form>
<%} else {%>
	<h2 class="tableH2">No se encontraron coincidencias</h2>
<%}%>
<script type="text/javascript">muestraMensaje('<%=mensaje%>');</script>
</div>