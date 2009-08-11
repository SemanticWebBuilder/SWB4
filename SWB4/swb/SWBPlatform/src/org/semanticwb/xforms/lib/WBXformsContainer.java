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
 * WBXformsContainer.java
 *
 * Created on 1 de julio de 2008, 05:45 PM
 */

package org.semanticwb.xforms.lib;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author  jorge.jimenez
 */

public abstract class WBXformsContainer extends XformsBaseImp
{
    protected ArrayList formelements;
    
    public WBXformsContainer() {
        formelements = new ArrayList();
    }
    
    public void add(Object obj) {
        formelements.add(obj);
    }
    
    public String getXform() {
        StringBuffer strb = new StringBuffer();
        Iterator iobj = formelements.iterator();
        while(iobj.hasNext())
        {
            if(!iobj.hasNext()){
                break;
            }
            Object obj = iobj.next();
            if(obj instanceof XformsBase) {
                XformsBase wbXformE = (XformsBase)obj;
                strb.append(wbXformE.getXml());
            }
        }
        return strb.toString();
    }
    
     public String getXformBinds() {
        StringBuffer strb = new StringBuffer();
        Iterator iobj = formelements.iterator();
        do {
            if(!iobj.hasNext()){
                break;
            }
            Object obj = iobj.next();
            if(obj instanceof XformsBase) {
                XformsBase wbXformE = (XformsBase)obj;
                String xmlBindL=wbXformE.getXmlBind();
                if(xmlBindL!=null && xmlBindL.trim().length()>0)
                {
                    strb.append(xmlBindL);
                }
            }
        } while(true);
        return strb.toString();
    }
        
    public String show() {
        return getXform();
    }
    
    public String showBinds() {
        return getXformBinds();
    }
    
}
