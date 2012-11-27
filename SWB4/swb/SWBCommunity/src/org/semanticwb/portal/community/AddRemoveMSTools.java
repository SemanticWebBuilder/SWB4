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
package org.semanticwb.portal.community;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class AddRemoveMSTools extends GenericResource {

    private static Logger log=SWBUtils.getLogger(AddRemoveMSTools.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        String act=request.getParameter("act");
        if(act==null)act="view";
        String path="/swbadmin/jsp/microsite/AddRemoveMSTools/view.jsp";
        if(act.equals("add"))path="/swbadmin/jsp/microsite/AddRemoveMSTools/add.jsp";
        if(act.equals("edit"))path="/swbadmin/jsp/microsite/AddRemoveMSTools/edit.jsp";
        if(act.equals("detail"))path="/swbadmin/jsp/microsite/AddRemoveMSTools/detail.jsp";

        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}

//        PrintWriter out = response.getWriter();
//        User user = paramRequest.getUser();
//        WebPage wp = paramRequest.getWebPage();
//        WebSite model = wp.getWebSite();
//        String act = request.getParameter("act");
//        if (null == act) {
//            act = "";
//        }
//
//        boolean isMicrosite = false;
//
//        if (wp.getSemanticObject().getGenericInstance() instanceof MicroSite) {
//            isMicrosite = true;
//        }
//
//        if (isMicrosite) {
//            if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
//                SWBResourceURL url = paramRequest.getRenderUrl();
//                url.setParameter("act", "edit");
//                url.setCallMethod(SWBResourceURL.Call_CONTENT);
//                url.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
//                out.println("<ul id=\"menuRight\">");
//                out.println("<li><a href=\"" + url + "\">Administrar Herramientas</a></li>");
//                out.println("</ul>");
//            } else {
//                if (act.equals("edit")) {
//
//                    HashMap hmwp = new HashMap();
//                    Iterator<WebPage> itwp = wp.listChilds(user.getLanguage(), true, false, false, false);
//                    while(itwp.hasNext())
//                    {
//                        WebPage wpc = itwp.next();
//                        hmwp.put(wpc.getId(), wp);
//                    }
//
//                    out.println("<div>");
//                    out.println("<fieldset>");
//                    out.println("<legend>Agregar o quitar herramientas de la comunidad</legend>");
//                    SWBResourceURL urla = paramRequest.getActionUrl();
//                    urla.setAction("update");
//                    out.println("<form action=\""+urla+"\" method=\"post\" name=\"frm_adminTools\">");
//                    Iterator<MicroSiteUtil> itmsu = ((MicrositeContainer) model).listMicroSiteUtils();
//
//                    while (itmsu.hasNext()) {
//                        MicroSiteUtil msu = itmsu.next();
//
//                        String toolsel = "";
//                        if(hmwp.get(wp.getId()+"_"+msu.getId())!=null) toolsel = "checked";
//                        out.println("<label for=\"" + msu.getId() + "\">"+msu.getDisplayTitle(user.getLanguage())+"</label><input type=\"checkbox\" id=\"" + msu.getId() + "\" name=\"micrositeutil\" "+toolsel+"><br/>");
//                    }
//
//                    out.println("</fieldset>");
//                    out.println("<fieldset>");
//                    out.println("<button type=\"submit\" name=\"btn_save\">Actualizar</button>");
//                    out.println("</fieldset>");
//                    out.println("</form>");
//                    out.println("</div>");
//
//                }
//            }
//
//
//        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        User user = response.getUser();
        WebPage wp = response.getWebPage();
        WebSite model = wp.getWebSite();
        
        String action=request.getParameter("act");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        
        String[] utils = request.getParameterValues("micrositeutil");
        
        boolean isMicrosite = false;

        if (wp.getSemanticObject().getGenericInstance() instanceof MicroSite) {
            isMicrosite = true;
        }
        
        if(isMicrosite)
        {
            if("update".equals(action))
            {
                //Lista de utilerias activas actualmente
                HashMap hmwp = new HashMap();
                HashMap hmndel = new HashMap();

                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String tags = request.getParameter("tags");

                if(title!=null&&title.trim().length()>0) wp.setTitle(SWBUtils.XML.replaceXMLChars(title));
                if(description!=null&&description.trim().length()>0) wp.setDescription(SWBUtils.XML.replaceXMLChars(description));
                if(tags!=null&&tags.trim().length()>0) wp.setTags(SWBUtils.XML.replaceXMLChars(tags));

                Iterator<WebPage> itwp = wp.listChilds(user.getLanguage(), true, false, false, false);
                while(itwp.hasNext())
                {
                    WebPage wpc = itwp.next();
                    hmwp.put(wpc.getId(), wpc);
                    //System.out.println("wp tool: "+wpc.getId());
                }


                MicroSite ms = (MicroSite)wp;

                if(null!=utils&&utils.length>0)
                {

                    for(int i=0;i<utils.length;i++)
                    {

                        GenericObject sowpu = ont.getGenericObject(utils[i]);

                        //System.out.println("checked tool: "+sowpu.getURI());
                        if(hmwp.get(ms.getId()+"_"+sowpu.getId())!=null)
                        {
                            //System.out.println("exist tool: "+sowpu.getURI());
                            //hmndel.put(ms.getId()+"_"+sowpu.getId(),sowpu);
                            hmwp.remove(ms.getId()+"_"+sowpu.getId());
                        }
                        else 
                        {
                            //System.out.println("new checked tool: "+sowpu.getURI());
                            if(sowpu!=null && sowpu instanceof MicroSiteUtil )
                            {
                                MicroSiteUtil msu = (MicroSiteUtil)sowpu;
                                MicroSiteWebPageUtil mswpu = MicroSiteWebPageUtil.ClassMgr.createMicroSiteWebPageUtil(ms.getId()+"_"+msu.getId(), model);

                                mswpu.setTitle(msu.getTitle());

                                mswpu.setMicroSite(ms);
                                mswpu.setMicroSiteUtil(msu);

                                mswpu.setParent(ms);
                                mswpu.setActive(Boolean.TRUE);

                            }
                        }
                    }
                }

                //Eliminando utilerias de-seleccionadas
                Iterator<WebPage> itwpu = hmwp.values().iterator();
                while(itwpu.hasNext())
                {
                    WebPage wprem = itwpu.next();
                    //System.out.println("wp tool to delete: "+wprem.getURI());
                    wprem.remove();
                }

                //Revisando miembros de la comunidad

                String[] msmla = request.getParameterValues("memberlevelaccess");
                if(msmla!=null)
                {
                    for(int j=0;j<msmla.length;j++)
                    {
                        StringTokenizer stoken = new StringTokenizer(msmla[j],"|");
                        String usr_id = stoken.nextToken();
                        int level = Integer.parseInt(stoken.nextToken());
                        //System.out.println("User: "+usr_id+", access: "+level);

                        User umember = user.getUserRepository().getUser(usr_id);

                        Member member = Member.getMember(umember, wp);

                        if(member.getAccessLevel()!=level)
                        {
                            //System.out.println("User: "+umember.getFullName()+" nivel anterior: "+member.getAccessLevel()+" a nivel: "+level+", modificado.");
                            if(level==Member.LEVEL_EDIT)
                            {
                                member.setAccessLevel(Member.LEVEL_EDIT);
                            }
                            else if(level==Member.LEVEL_ADMIN)
                            {
                                member.setAccessLevel(Member.LEVEL_ADMIN);
                            }
                            else if(level==0)
                            {
                                member.remove();
                            }
                        }

                    }

                }
                response.setRenderParameter("act", "view");
                response.setCallMethod(SWBActionResponse.Call_STRATEGY);
                response.setMode(SWBActionResponse.Mode_VIEW);
            } else if("remove".equals(action))
            {
                String red = wp.getWebSite().getHomePage().getUrl();
                MicroSite ms = (MicroSite)wp;
                ms.remove();
                response.sendRedirect(red);
            }

        }
    }

    private Member getMember(User user, MicroSite site)
    {
        //System.out.println("getMember:"+user+" "+site);
        if(site!=null)
        {
            Iterator<Member> it=Member.ClassMgr.listMemberByUser(user,site.getWebSite());
            while(it.hasNext())
            {
                Member mem=it.next();
                //System.out.println("mem:"+mem+" "+mem.getMicroSite());me
                if(mem.getMicroSite().equals(site))
                {
                   return mem;
                }
            }
        }
        return null;
    }
}
