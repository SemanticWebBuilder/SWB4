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
package org.semanticwb.portal.admin.admresources.lib;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.portal.admin.admresources.*;

// TODO: Auto-generated Javadoc
/**
 * Esta es una clase abstracta que implementa la interface WBAdmResource y que es utilizada para los objetos de tipo contenedor(ej. FormFE,SelectFE)
 * <p>
 * This is an abstract class that implements the  WBAdmResource interface and its used in the containers objects (ex. FormFE,SelectFE)
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public abstract class WBContainerFE extends WBAdmResourceAbs {
    
    /** The formelements. */
    protected ArrayList formelements;
    
    /** The ajsfe. */
    private ArrayList ajsfe;
    
    /**
     * Instantiates a new wB container fe.
     */
    public WBContainerFE() {
        formelements = new ArrayList();
        ajsfe = new ArrayList();
    }
    
    /**
     * Adds the.
     * 
     * @param obj the obj
     */
    public void add(Object obj) {
        formelements.add(obj);
    }
    
    /**
     * Gets the form e.
     * 
     * @return the form e
     */
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
    
    /**
     * Gets the form e.
     * 
     * @param formName the form name
     * @return the form e
     */
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
    
    /**
     * Gets the forms.
     * 
     * @return the forms
     */
    public Iterator getForms(){
        return formelements.iterator();
    }
    
    /**
     * Gets the form.
     * 
     * @param formName the form name
     * @return the form
     */
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
    
    /**
     * Show.
     * 
     * @return the string
     */
    public String show() {
        return getFormE();
    }
    
    /**
     * Show.
     * 
     * @param formName the form name
     * @return the string
     */
    public String show(String formName) {
        return getFormE(formName);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getHtml()
     */
    public String getHtml() {
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setAttributes()
     */
    public void setAttributes() {
    }

    /**
     * Gets the jscrips fe.
     * 
     * @return the jscrips fe
     */
    public Iterator getJscripsFE(){
        return ajsfe.iterator();
    }
    
}
