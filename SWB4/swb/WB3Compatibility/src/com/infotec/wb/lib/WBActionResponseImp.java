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
