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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.lib;

import java.util.Map;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author javier.solis
 */
public class WBResourceURLImp implements WBResourceURL
{
    private SWBResourceURL url=null;

    public WBResourceURLImp(SWBResourceURL url)
    {
        this.url=url;
    }

    public WBResourceURL setWindowState(String state) {
        url.setWindowState(state);
        return this;
    }

    public String getWindowState() {
        return url.getWindowState();
    }

    public int getCallMethod() {
        return url.getCallMethod();
    }

    public WBResourceURL setCallMethod(int callMethod) {
        url.setCallMethod(callMethod);
        return this;
    }

    public WBResourceURL setMode(String mode) {
        url.setMode(mode);
        return this;
    }

    public String getMode() {
        return url.getMode();
    }

    public boolean isSecure() {
        return url.isSecure();
    }

    public WBResourceURL setSecure(boolean secure) {
        url.setSecure(secure);
        return this;
    }

    public String getAction() {
        return url.getAction();
    }

    public WBResourceURL setAction(String action) {
        url.setAction(action);
        return this;
    }

    public String toString(boolean encodeAmp) {
        return url.toString(encodeAmp);
    }

    public void setParameters(Map parameters) {
        url.setParameters(parameters);
    }

    public void setParameter(String key, String value) {
        url.setParameter(key,value);
    }

    public void setParameter(String key, String[] values) {
        url.setParameter(key,values);
    }

    public int getURLType() {
        return url.getURLType();
    }

    public boolean isOnlyContent() {
        return url.isOnlyContent();
    }

    public void setOnlyContent(boolean onlyContent)
    {
        url.setOnlyContent(onlyContent);
    }

}
