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
 * WBResponse.java
 *
 * Created on 2 de septiembre de 2003, 11:35
 */

package com.infotec.wb.lib;

import com.infotec.appfw.exception.AFException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.infotec.wb.core.*;
import com.infotec.topicmaps.*;

import com.infotec.appfw.util.AFUtils;
import org.semanticwb.SWBException;
import org.semanticwb.portal.api.SWBActionResponseImp;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 * Clase que implementa WBActionResponse.
 * Esta clase da acceso al Response del recurso cuando es llamado por el ActionResponse.
 * @author Javier Solis Gonzalez
 */
public class WBActionResponseImp implements WBActionResponse
{
    private SWBActionResponseImp url=null;

    public WBActionResponseImp(SWBActionResponseImp url)
    {
        this.url=url;
    }

    public WBActionResponseImp(HttpServletResponse response)
    {
        url=new SWBActionResponseImp(response);
    }

    public void sendRedirect(String location) {
        url.sendRedirect(location);
    }

    public String getLocation() {
        return url.getLocation();
    }

    public void setWindowState(String state) {
        url.setWindowState(state);
    }

    public void setRenderParameters(Map parameters) {
        url.setRenderParameters(parameters);
    }

    public void setRenderParameter(String key, String value) {
        url.setRenderParameter(key,value);
    }

    public void setRenderParameter(String key, String[] values) {
        url.setRenderParameter(key,values);
    }

    public void setCallMethod(int callMethod) {
        url.setCallMethod(callMethod);
    }

    public void setAction(String action) {
        url.setAction(action);
    }

    public void setMode(String mode) {
        url.setMode(mode);
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
