/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * TreeRep.java
 *
 * Created on 20 de abril de 2004, 12:25 PM
 */

package com.infotec.wb.resources.repository;


import javax.servlet.http.*;
//import com.infotec.wb.core.*;
//import com.infotec.wb.core.db.*;
//import com.infotec.wb.util.*;
//import com.infotec.appfw.exception.*;
//import com.infotec.appfw.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
//import com.infotec.topicmaps.*;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;


/**
 *
 * @author  Jorge Alberto Jim�ne
 */
public class TreeRep {

     Resource base=null;

    String webpath= SWBPlatform.getContextPath();

    Templates plt;
    Transformer trans;
    DocumentBuilderFactory dbf=null;
    DocumentBuilder db=null;
    String strRes="";
    String strWorkPath="";
    Vector vTopic= new Vector();
    int intMaxLevel=1;

    /** Creates a new instance of TreeRep */
    public TreeRep() {

    }


   /**
     * Asigna la informaci�n de la base de datos al recurso.
     *
     * @param     base  La informaci�n del recurso en memoria.
     */
    public void setResourceBase(Resource base)
    {
        this.base=base;
        Document dom1=base.getDom();
        strRes=SWBPlatform.getEnv("wb/admresource");
        String rutawork=SWBPortal.getWorkPath();
        strWorkPath=SWBPortal.getWorkPath();
        if(base.getXml()==null)return;
        try
        {
            TransformerFactory transFact = TransformerFactory.newInstance();
            NodeList template=null;
            if(dom1!=null)
            {
                template=dom1.getElementsByTagName("template");
                //RecResourceType recobj = DBResourceType.getInstance().getResourceType(base.getTopicMapId(), base.getType());
                ResourceType recobj = base.getResourceType();
                if(template.getLength()>0)
                {
                    plt = transFact.newTemplates(new StreamSource(SWBUtils.IO.getFileFromPath("/resources/"+recobj.getTitle()+"/"+base.getId()+"/"+template.item(0).getChildNodes().item(0).getNodeValue())));
                    trans = plt.newTransformer();
                }
                else
                {
                    Repository.log.error("Error en la definici�n del XML (Tag Template) en el recurso MapaSitio: "+base.getId());
                }
            }
            else
            {
                Repository.log.error("Error en la definici�n del XML en el recurso MapaSitio: "+base.getId());
            }
        }
        catch(Exception e)
        {
            Repository.log.error("Error:setResourceBase:toXml() recurso de tipo MapaSitio: "+base.getId(),e);
        }
        try
        {
            dbf=DocumentBuilderFactory.newInstance();
            db=dbf.newDocumentBuilder();
        }
        catch(Exception e)
        {
            Repository.log.error("Error al interpretar xml del recurso tipo MapaSitio.",e);
        }
    }



     public String getHtml(HttpServletRequest request, HttpServletResponse response, User user, WebPage topicrec, HashMap arguments,WebPage topic) throws SWBException {
        Document dcmDom=base.getDom();
        if(dcmDom==null)throw new SWBException("Dom nulo - getHtml()");
        StringBuffer sbfRet=new StringBuffer();

        String strResmaptopic=null;
            strResmaptopic=topicrec.getId();
        String strUrl = topic.getWebSite().getHomePage().getUrl();

        try
        {
                WebSite tm   =topic.getWebSite();

                Document dom=null;
                WebPage tpid=null;
                Vector vctPath=new Vector();
                int intLevel=1, intWidth=10;
                String idhome=null;
                String dir=topic.getId();

                if(db!=null)
                {
                    try
                    {
                        if(request.getParameter("reptp")!=null && !request.getParameter("reptp").trim().equals(""))
                        {
                            tpid=tm.getWebPage(request.getParameter("reptp"));
                            vctPath=getMapPath(tpid);
                        }

                        //DE AQUI

                        if(dir!=null){
                                idhome = dir;
                        }

                        WebPage tpsite=null;
                        if(idhome!=null)
                        {
                            tpsite=tm.getWebPage(idhome);
                            if(tpsite==null) tpsite=tm.getHomePage();
                        }
                        else tpsite=tm.getHomePage();

                        ///hASTA AQUI


                        dom=db.newDocument();
                        Element el=dom.createElement("mapa");
                        el.setAttribute("title", "Repositorio de contenidos");
                        el.setAttribute("path", SWBPortal.getWorkPath()+base.getWorkPath()+"/");
                        dom.appendChild(el);

                            el=dom.createElement("topicm");
                            el.setAttribute("urlmapa", strUrl + strResmaptopic + "/?reptp=" + topic.getId());
                            el.setAttribute("url", strUrl + strResmaptopic + "/?reptp=" + topic.getId());
                            el.setAttribute("nombre", topic.getDisplayName());
                            if(topic.listChilds().hasNext()) el.setAttribute("mas", "1");
                            else el.setAttribute("mas", "0");
                            Iterator<WebPage> it=tpsite.listChilds();
                            while(it.hasNext())
                            {
                                intLevel=1;
                                WebPage tp=it.next();
                                if(user.haveAccess(tp) && tp.getId()!=null)
                                {
                                        Element padre=dom.createElement("padre");
                                        padre.setAttribute("urlmapa", strUrl + strResmaptopic + "/?reptp=" + tp.getId());
                                        padre.setAttribute("url", strUrl + strResmaptopic + "/?reptp=" + tp.getId());
                                        padre.setAttribute("nombre", tp.getDisplayName());
                                        if(tp.listChilds().hasNext()) padre.setAttribute("mas", "1");
                                        else padre.setAttribute("mas", "0");
                                        el.appendChild(padre);
                                        if((intLevel < intMaxLevel || (tpid!=null && tp.getId().equals(tpid.getId())) ||
                                        vctPath.contains(tp.getId())) && tp.listChilds().hasNext())
                                            sbfRet.append(getChilds(tpid, tp, user, dom, padre, vctPath, intLevel+1, intWidth,strResmaptopic));
                                }
                            }
                            dom.getChildNodes().item(0).appendChild(el);
                    }
                    catch(Exception e)
                    {
                        Repository.log.error("Error al agregar elementos al recurso de tipo MapaSitio:",e);
                    }
                }
                sbfRet.append(SWBUtils.XML.transformDom(plt, dom));
        }
        catch(Exception e)
        {
            Repository.log.error("Error en recurso MapaSitio al traer el Html",e);
        }
        return sbfRet.toString();
    }


     /**
     * Obtiene los t�picos hijo relacionados al t�pico solicitado bajo una estructura jer�rquica.
     *
     * @param     tpid      El t�pico que solicita el recurso.
     * @param     tpc       El t�pico hijo de acuerdo al nivel de recursividad presente en la generaci�n
     *                      de la estructura jer�rquica.
     * @param     user      El usuario concurrente que solicita el t�pico.
     * @param     dom       El DOM requerido para crear el recurso conforme el template establecido.
     * @param     el        El elemento del DOM al cual se agregar�n los t�picos hijo subyacentes.
     * @param     vctPath   El vector que almacena los t�picos padre del t�pico requerido.
     * @param     intLevel  El nivel de recursividad presente.
     * @param     intWidth  El espacio en pixeles para definir la separaci�n entre los difentes niveles
     *                      de la estructura jer�rquica con respecto al margen izquierdo.
     * @return    Regresa un nuevo String que contiene.
     * @see       infotec.topicmaps
     */
    public String getChilds(WebPage tpid, WebPage tpc, User user, Document dom, Element el, Vector vctPath, int intLevel, int intWidth,String topicrec)
    {
        Document dcmDom=base.getDom();
        String strResmaptopic=null;
              strResmaptopic=topicrec;
        String strUrl =tpc.getWebSite().getHomePage().getUrl();

        StringBuffer sbfRet=new StringBuffer();
        Iterator<WebPage> it=tpc.listChilds();

        while(it.hasNext())
        {
            WebPage tpsub=it.next();
            if(tpsub.getId()!=null &&  user.haveAccess(tpsub))
            {
                if(vTopic.contains(tpsub)) break;
                vTopic.addElement(tpsub);

                Element hijo=dom.createElement("hijo");
                for(int i=0; i < intLevel-1; i++)
                {
                    Element hijospc=dom.createElement("hijospc");
                    hijospc.setAttribute("width", String.valueOf(intWidth));
                    hijo.appendChild(hijospc);
                }
                hijo.setAttribute("urlmapa", strUrl + strResmaptopic + "/?reptp=" + tpsub.getId());
                hijo.setAttribute("url", strUrl + strResmaptopic + "/?reptp=" + tpsub.getId());
                hijo.setAttribute("nombre", tpsub.getDisplayName());
                if(isMapParent(tpid, tpsub, vctPath)) hijo.setAttribute("mas", "1");
                else hijo.setAttribute("mas", "0");
                el.appendChild(hijo);

                if((intLevel < intMaxLevel ||  (tpid!=null && tpsub.getId().equals(tpid.getId())) ||
                vctPath.contains(tpsub.getId())) && tpsub.listChilds().hasNext())
                    sbfRet.append(getChilds(tpid, tpsub, user, dom, el, vctPath, intLevel+1, intWidth,strResmaptopic));
                vTopic.removeElement(tpsub);
            }
        }
        return sbfRet.toString();
    }

    /**
     * Calcula los t�picos padre de un t�pico.
     *
     * @param     tpid      El t�pico que solicita el recurso.
     * @return    Regresa un objeto Vector que contiene los t�picos padre del t�pico requerido.
     * @see       infotec.topicmaps.Topic
     */
    public Vector getMapPath(WebPage tpid)
    {
        Vector vctPath=new Vector();
        if(tpid.getWebSite().getHomePage()!=tpid)
        {
            Iterator<WebPage> aux=tpid.listVirtualParents();
            while(aux.hasNext())
            {
                WebPage tp=aux.next();
                vctPath.addElement(tp.getId());
                aux=tp.listVirtualParents();
                if(tpid.getWebSite().getHomePage()==tp)break;
            }
        }
        return vctPath;
   }

    /**
     * Calcula si un t�pico tiene t�picos hijo sin referencias c�clicas.
     *
     * @param     tpid      El t�pico que solicita el recurso.
     * @param     tpsub     El t�pico hijo de acuerdo al nivel de recursividad presente en la generaci�n
     *                      de la estructura jer�rquica.
     * @param     vctPath   El vector que almacena los t�picos padre del t�pico requerido.
     * @return    Regresa si el t�pico solicitado contiene o no t�picos hijo con referencias c�clicas.
     * @see       infotec.topicmaps.Topic
     */
   public boolean isMapParent(WebPage tpid, WebPage tpsub, Vector vctPath)
   {
        boolean bParent=false;
        Iterator<WebPage> hit=tpsub.listChilds();
        if(hit.hasNext())
        {
            do
            {
                WebPage htp=hit.next();
                if(tpid!=null)
                {
                    if(htp.getId()!=null && !tpid.getId().equals(htp.getId()) && !vctPath.contains(htp.getId()))
                    {
                        bParent=true;
                        break;
                    }
                }
                else
                {
                    if(htp.getId()!=null && !vctPath.contains(htp.getId()))
                    {
                        bParent=true;
                        break;
                    }
                }
            } while(hit.hasNext());
        }
        else bParent=false;

        return bParent;
    }

}
