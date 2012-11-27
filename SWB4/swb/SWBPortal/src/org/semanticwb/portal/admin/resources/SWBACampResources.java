/*
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
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.Camp;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * Shows a list of Resources asociated to a specific Campain.
 * 
 * @author juan.fernandez
 */
public class SWBACampResources extends GenericResource {

    /** The page_elements. */
    private static int page_elements = 20;

    /**
     * Creates a new instance of SWBACampResources.
     */
    public SWBACampResources() {
    }

    /**
     * User view for the SWBACampResources resource, show a list of resources asociated to a specific campain.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String tmid = request.getParameter("tm");
        String idcamp = request.getParameter("id");
        String idrrtype = "-1";
        String idres = "-1";
        User user = paramRequest.getUser();
        int numpage=1;
        if(request.getParameter("idrrtype")!=null) idrrtype = request.getParameter("idrrtype");
        if(request.getParameter("idres")!=null) idres = request.getParameter("idres");
        if(request.getParameter("numpage")!=null) numpage = Integer.parseInt(request.getParameter("numpage"));
        
        String rowColor="";
        boolean cambiaColor = true;
        
        String act =request.getParameter("act");
        if(act==null) act="";
        
        Camp rCamp = SWBContext.getWebSite(tmid).getCamp(idcamp);
        //boolean mostrarPaginacion=false;
        
        
        WebSite tmADMIN = SWBContext.getAdminWebSite();
        WebPage tpRESINFO = tmADMIN.getWebPage("WBAd_sysi_ResourcesInfo");
        
        String idc = "0";
        if(idcamp!=null) idc=idcamp;
        Iterator<ResourceType> iteTypes = SWBContext.getWebSite(tmid).listResourceTypes();
        
        HashMap hmTypes = new HashMap();
        // contando elementos a mostrar en total
        int totalElementos=0;
        while(iteTypes.hasNext()){
            ResourceType rrtype = (ResourceType) iteTypes.next();
            
            if(rrtype.getResourceMode()!=2)continue;
            //TODO: Revisar getResourcesBaseOfType() con el cambio hecho
            //HashMap hmRec = ResourceMgr.getInstance().getResourcesBaseOfType(tmid,rrtype.getId(),rrtype.getTopicMapId());
            // Comienza cambio >>>>>>>>>>>>>>>>>>>
            HashMap hmRec = new HashMap();
            Iterator ite_ro = rrtype.listRelatedObjects();
            while(ite_ro.hasNext())
            {
                Object obj = ite_ro.next();
                if(obj instanceof Resource)
                {
                    Resource por = (Resource)obj;
                    hmRec.put(por.getId(), por);
                }
            }
            // Termina cambio <<<<<<<<<<<<<<<<<<<<<
            Iterator iteRec = hmRec.keySet().iterator();
            int cuenta1=0;
            boolean entro = false;
            while(iteRec.hasNext()){
                String idhm = (String) iteRec.next();
                Resource rRB = (Resource) hmRec.get(idhm);
                if(idc.equals(rRB.getCamp().getId())){
                    cuenta1++;
                    entro=true;
                }
            }
            if(entro){
                hmTypes.put(rrtype.getId(), rrtype);
            }
            if(cuenta1>0){
                totalElementos+=cuenta1;
            }
        }
        
        if(act.equals("")){
            if(hmTypes.size()>0){
                out.println("<p class=box>");
                out.println("<table cellpadding=10 cellspacing=0 border=0 width=\"100%\">");
                out.println("<tr>");
                out.println("<td class=tabla>");
                out.println(paramRequest.getLocaleString("msgExistingRes"));
                out.println("</td>");
                out.println("</tr>");
                rowColor="";
                cambiaColor = true;
                Iterator iteRTypes = hmTypes.values().iterator();
                while(iteRTypes.hasNext()){
                    ResourceType rrtype = (ResourceType) iteRTypes.next();
                    SWBResourceURL url=paramRequest.getRenderUrl();
                    url.setParameter("idrrtype", rrtype.getId() );
                    url.setParameter("tm",tmid);
                    url.setParameter("id",idcamp);
                    url.setParameter("act","view");
                    rowColor="#EFEDEC";
                    if(!cambiaColor) rowColor="#FFFFFF";
                    cambiaColor = !(cambiaColor);
                    out.println("<tr bgcolor=\""+rowColor+"\">");
                    out.println("<td class=valores>");
                    out.println("<a href=\""+url.toString()+"\" class=link>"+rrtype.getDisplayTitle(user.getLanguage())+"</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</p>");
            }
            else{
                out.println("<p class=box align=left>");
                out.println("No hay recursos asociados a esta campaña.");
                out.println("</p>");
                
            }
        }
        else{
            if(act.equals("view")){
                //SWBContext.getWebSite("").
                ResourceType restype = (ResourceType) hmTypes.get(idrrtype);
                
                String nombretipo = restype.getDisplayTitle(user.getLanguage());
                //HashMap hmRec = ResourceMgr.getInstance().getResourcesBaseOfType(tmid,restype.getId(),restype.getTopicMapId());
                //TODO: Revisar getResourcesBaseOfType() con el cambio hecho
                // Comienza cambio >>>>>>>>>>>>>>>>>>>
                HashMap hmRec = new HashMap();
                Iterator ite_ro = restype.listRelatedObjects();
                while(ite_ro.hasNext())
                {
                    Object obj = ite_ro.next();
                    if(obj instanceof Resource)
                    {
                        Resource por = (Resource)obj;
                        hmRec.put(por.getId(), por);
                    }
                }
                // Termina cambio <<<<<<<<<<<<<<<<<<<<<
                
                StringBuffer header = new StringBuffer("");
                
                out.println("<p class=box>");
                out.println("<table cellpadding=10 cellspacing=0 border=0 width=\"100%\">");
                out.println("<tr>");
                out.println("<td colspan=6 class=tabla>");
                out.println(paramRequest.getLocaleString("msgExistingRes"));
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td colspan=6 class=tabla>");
                out.println(nombretipo);
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td class=tabla>");
                out.println(paramRequest.getLocaleString("msgIdentifier"));
                out.println("</td>");
                out.println("<td class=tabla>");
                out.println(paramRequest.getLocaleString("msgTitle"));
                out.println("</td>");
                out.println("<td class=tabla>");
                out.println(paramRequest.getLocaleString("msgDescription"));
                out.println("</td>");
                out.println("<td class=tabla>");
                out.println(paramRequest.getLocaleString("msgCreationDate"));
                out.println("</td>");
                out.println("<td class=tabla>");
                out.println(paramRequest.getLocaleString("msgLastUpdate"));
                out.println("</td>");
                out.println("</tr>");
                Iterator iteRec = hmRec.keySet().iterator();
                
                int cuenta=0;
                int inicio=1;
                int fin=page_elements+1;
                
                if(numpage>1){
                    inicio = ((numpage-1)*page_elements)+1;
                    fin = numpage*page_elements+1;
                }
                
                int todos=0;
                rowColor="";
                cambiaColor = true;
                StringBuffer existentes = new StringBuffer("");
                while(iteRec.hasNext()){
                    String idhm = (String) iteRec.next();
                    Resource rRB = (Resource) hmRec.get(idhm);
                    if(idc.equals(rRB.getCamp().getId())){
                        cuenta++;
                        if(cuenta>=inicio && cuenta<fin) {
                            rowColor="#EFEDEC";
                            if(!cambiaColor) rowColor="#FFFFFF";
                            cambiaColor = !(cambiaColor);
                            existentes.append("\n<tr bgcolor=\""+rowColor+"\">");
                            existentes.append("\n<td class=valores>");
                            existentes.append("\n<a class=link href=\""+tpRESINFO.getUrl()+"?id="+rRB.getId()+"&tm="+tmid+"\" title=\""+paramRequest.getLocaleString("msgTitleResDetail")+"\">");
                            existentes.append(rRB.getId());
                            existentes.append("\n</a>");
                            existentes.append("\n</td>");
                            existentes.append("\n<td class=valores>");
                            existentes.append(rRB.getDisplayTitle(user.getLanguage()));
                            existentes.append("\n</td>");
                            existentes.append("\n<td class=valores>");
                            existentes.append(rRB.getDescription());
                            existentes.append("\n</td>");
                            existentes.append("\n<td class=valores>");
                            existentes.append(rRB.getCreated());
                            existentes.append("\n</td>");
                            existentes.append("\n<td class=valores>");
                            existentes.append(rRB.getUpdated().toString());
                            existentes.append("\n</td>");
                            existentes.append("\n</tr>");
                            
                        }// fin
                        todos++;
                    }
                }
                if(cuenta>0){
                    out.println(header.toString());
                    out.println(existentes.toString());
                }
                int totalpaginas = Math.round(todos/page_elements);
                if((todos%page_elements)>0) totalpaginas++;
                
                if(todos>page_elements) {
                    out.println("<tr>");
                    out.println("<td colspan=6 class=valores align=right><HR size=1 noshade>");
                    WebPage tp = paramRequest.getWebPage();
                    if(numpage>1){
                        out.println("<a class=link href=\""+tp.getUrl()+"?idrrtype="+idrrtype+"&numpage="+(numpage-1)+"&tm="+tmid+"&id="+idcamp+"&act=view\">"+paramRequest.getLocaleString("msgPreviousPage")+"</a>&nbsp;");
                    }
                    for(int i=1;i<=totalpaginas;i++){
                        if(i!=numpage){
                            out.println("<a class=link href=\""+tp.getUrl()+"?idrrtype="+idrrtype+"&numpage="+i+"&tm="+tmid+"&id="+idcamp+"&act=view\">"+i+"</a>");
                        }
                        else{
                            out.println(i);
                        }
                        out.println("&nbsp;");
                    }
                    if((numpage+1)<=totalpaginas) {
                        out.println("<a class=link href=\""+tp.getUrl()+"?idrrtype="+idrrtype+"&numpage="+(numpage+1)+"&tm="+tmid+"&id="+idcamp+"&act=view\">"+paramRequest.getLocaleString("msgNextPage")+"</a>&nbsp;");
                    }
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</p>");
            }
        }
    }
    
}
