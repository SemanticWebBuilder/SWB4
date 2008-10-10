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


package org.semanticwb.portal.admin.admresources.lib;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.portal.admin.admresources.*;

/**
 * Esta es una clase abstracta que implementa la interface WBAdmResource y que es utilizada para los objetos de tipo contenedor(ej. FormFE,SelectFE)
 * <p>
 * This is an abstract class that implements the  WBAdmResource interface and its used in the containers objects (ex. FormFE,SelectFE)
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public abstract class WBContainerFE extends WBAdmResourceAbs {
    
    protected ArrayList formelements;
    public WBContainerFE() {
        formelements = new ArrayList();
    }
    
    public void add(Object obj) {
        formelements.add(obj);
    }
    
    public String getFormE() {
        StringBuffer strb = new StringBuffer();
        Iterator iobj = formelements.iterator();
        do {
            if(!iobj.hasNext()){
                break;
            }
            Object obj = iobj.next();
            if(obj instanceof WBAdmResource) {
                WBAdmResource wbadmres = (WBAdmResource)obj;
                strb.append(wbadmres.getHtml());
            }
        } while(true);
        return strb.toString();
    }
    
    public String getFormE(String formName) {
        StringBuffer strb = new StringBuffer();
        Iterator iobj = formelements.iterator();
        do {
            if(!iobj.hasNext()){
                break;
            }
            Object obj = iobj.next();
            if(obj instanceof WBAdmResource) {
                WBAdmResource wbadmres = (WBAdmResource)obj;
                if(wbadmres.getName().equals(formName)){
                    strb.append(wbadmres.getHtml());
                    break;
                }
            }
        } while(true);
        return strb.toString();
    }
    
    public Iterator getForms(){
        return formelements.iterator();
    }
    
    public FormFE getForm(String formName){
        Iterator iforms=formelements.iterator();
        while(iforms.hasNext()){
            FormFE formfe=(FormFE)iforms.next();
            if(formfe!=null && formfe.getName().equals(formName)){
                return formfe;
            }
        }
        return null;
    }
    
    public String show() {
        return getFormE();
    }
    
    public String show(String formName) {
        return getFormE(formName);
    }
    
    public String getHtml() {
        return null;
    }
    
    public void setAttributes() {
    }
    
}