/**
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
**/

/*
 * WBResRequest.java
 *
 * Created on 1 de junio de 2004, 01:39 PM
 */
package com.infotec.wb.lib;

import com.infotec.appfw.exception.AFException;
import com.infotec.topicmaps.*;
import com.infotec.wb.core.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBException;
import org.semanticwb.portal.api.SWBParamRequestImp;

/**
 * Clase que implementa WBParamRequest.
 * Ccontiene los objetos que son pasados al recurso como parametros.
 * Ejemplo: Topic, WBUser..
 * @author Javier Solis Gonzalez
 */
public class WBParamRequestImp implements WBParamRequest
{
    private SWBParamRequestImp url=null;
    
    public WBParamRequestImp(SWBParamRequestImp url)
    {
        this.url=url;
    }

    /** Creates a new instance of WBResRequest */
    public WBParamRequestImp(HttpServletRequest request, Resource resource, Topic topic, WBUser user)
    {
        url=new SWBParamRequestImp(request, resource.getNative(), topic.getNative(), user.getNative());
    }

    public Map getArguments() {
        return url.getArguments();
    }

    public String getArgument(String key) {
        return url.getArgument(key);
    }

    public String getArgument(String key, String defvalue) {
        return url.getArgument(key, defvalue);
    }

    public WBResourceURL getActionUrl() {
        return new WBResourceURLImp(url.getActionUrl());
    }

    public WBResourceURL getRenderUrl() {
        return new WBResourceURLImp(url.getRenderUrl());
    }

    public String getLocaleString(String key) throws AFException {
        try
        {
            return url.getLocaleString(key);
        }catch(SWBException e){throw new AFException(e);}
    }

    public String getLocaleLogString(String key) throws AFException {
        try
        {
            return url.getLocaleLogString(key);
        }catch(SWBException e){throw new AFException(e);}
    }

    public String getWindowTitle() {
        return url.getWindowTitle();
    }

    public void setWindowTitle(String windowTitle) {
        url.setWindowTitle(windowTitle);
    }

    public void setTemplateHead(String templateHead) {
        url.setTemplateHead(templateHead);
    }

    public Topic getTopic() {
        return new Topic(url.getWebPage());
    }

    public Topic getAdminTopic() {
        return new Topic(url.getAdminTopic());
    }

    public WBUser getUser() {
        return new WBUser(url.getUser());
    }

    public int getUserLevel() {
        return url.getUserLevel();
    }

    public Resource getResourceBase() {
        return new Resource(url.getResourceBase());
    }

    public String getWindowState() {
        return url.getWindowState();
    }

    public int getCallMethod() {
        return url.getCallMethod();
    }

    public String getMode() {
        return url.getMode();
    }

    public boolean isSecure() {
        return url.isSecure();
    }

    public String getAction() {
        return url.getAction();
    }

    public boolean haveVirtualTopic() {
        return url.haveVirtualWebPage();
    }
    
}
