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

/** La interfaz WBAdmResource es la que define los m�todos que una clase debe implementar 
 * para poder ser considerado como un elemento de una forma en la api de administraci�n de recursos
 * <p>
 * This object is the interfaz that defines the methods that a class must implement to be considered as a form element in the
 * administration api
 * <p>
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

import org.semanticwb.portal.admin.admresources.*;
import org.semanticwb.portal.admin.admresources.db.*;

public interface WBAdmResource {
    
    //sets
    
    /**  insert the name of the form element**/
    public void setName(String name);
    
    public void setLabel(String label);
    
    /** insert extra attributes to one form element**/
    public void setMoreAttr(String moreattr); 
    
    public void setAdmDBConnMgr(AdmDBConnMgr dbconnmgr);
    
    public void setStyle(String style);
    
    public void setStyleClass(String styleclass);
    
    //gets
    /**
     * regresa el nombre del elemento de la forma
     * Returs the form element name
     * @return The form element name
     */
    public String getName(); 
    
    public String getLabel();
    
    /** obtine atributos insertados adicionalmente a un elemento de forma **/
    public String getMoreAttr(); 
    
    public AdmDBConnMgr getAdmDBConnMgr();
    
    public String getStyle();
    
    public String getStyleClass();
    
       
    /**
     * regresa el html del elemento de la forma
     * Returs the html of the form element
     */
    public String getHtml();
    
    /**
    * Set attributes to class according with the xml tag element
    */ 
    public void setAttributes();

}
