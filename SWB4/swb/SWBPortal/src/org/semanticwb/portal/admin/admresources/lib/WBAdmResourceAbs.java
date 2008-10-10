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

import org.semanticwb.model.Portlet;
import org.semanticwb.portal.admin.admresources.db.*;

/** Esta es una clase abstracta que implementa los metodos genericos de la interface WBAdmResource.
 * <p>
 * This is an abstract class that implements the generic methods in the WBAdmResource interface
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public abstract class WBAdmResourceAbs implements WBAdmResource {
    
    
    protected String name=null;
    protected String moreattr=null;
    protected String style=null;
    protected String styleclass=null;
    protected AdmDBConnMgr dbconnmgr=null;
    protected String label=null;
    protected Portlet base=null;
    
    /** Creates a new instance of WBAdmResourceAbs */
    public WBAdmResourceAbs() {
    }
    
    //sets
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setLabel(String label) {
        this.label=label;
    }
    
         /** inserta m�s atributos a un elemento de forma **/
    public void setMoreAttr(String moreattr) {
        this.moreattr=moreattr;
    } 
    
    public void setAdmDBConnMgr(AdmDBConnMgr dbconnmgr){
       this.dbconnmgr=dbconnmgr;
    }

    public void setStyle(String style) {
        this.style=style;
    }
    
    public void setStyleClass(String styleclass) {
        this.styleclass=styleclass;
    }
    
    //gets
    
    public String getName(){
        return name;
    }
    
    public String getLabel() {
        return label;
    }
    
     /** obtine atributos insertados adicionalmente a un elemento de forma **/
    public String getMoreAttr(){
        return moreattr;
    }
    
    public AdmDBConnMgr getAdmDBConnMgr(){
       return dbconnmgr;
    }
    
    public String getStyle() {
        return style;
    }
    
    public String getStyleClass() {
         return styleclass;
    }
    
    
}
