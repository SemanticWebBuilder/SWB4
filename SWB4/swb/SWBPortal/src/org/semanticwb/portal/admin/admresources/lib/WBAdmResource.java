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

/** La interfaz WBAdmResource es la que define los m?todos que una clase debe implementar
 * para poder ser considerado como un elemento de una forma en la api de administraci?n de recursos
 * <p>
 * This object is the interfaz that defines the methods that a class must implement to be considered as a form element in the
 * administration api
 * <p>
 * @author  Jorge Alberto Jim?nez
 * @version 1.0
 */

import org.semanticwb.portal.admin.admresources.*;
import org.semanticwb.portal.admin.admresources.db.*;

// TODO: Auto-generated Javadoc
/**
 * The Interface WBAdmResource.
 */
public interface WBAdmResource {

    //sets

    /**
     * insert the name of the form element*.
     * 
     * @param name the new name
     */
    public void setName(String name);

    /**
     * Sets the label.
     * 
     * @param label the new label
     */
    public void setLabel(String label);

    /**
     * insert extra attributes to one form element*.
     * 
     * @param moreattr the new more attr
     */
    public void setMoreAttr(String moreattr);

    /**
     * Sets the adm db conn mgr.
     * 
     * @param dbconnmgr the new adm db conn mgr
     */
    public void setAdmDBConnMgr(AdmDBConnMgr dbconnmgr);

    /**
     * Sets the style.
     * 
     * @param style the new style
     */
    public void setStyle(String style);

    /**
     * Sets the style class.
     * 
     * @param styleclass the new style class
     */
    public void setStyleClass(String styleclass);

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id);

    /**
     * Sets the form fe.
     * 
     * @param formFe the new form fe
     */
    public void setFormFE(FormFE formFe);


    //gets
    /**
     * regresa el nombre del elemento de la forma
     * Returs the form element name.
     * 
     * @return The form element name
     */
    public String getName();

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel();

    /**
     * obtine atributos insertados adicionalmente a un elemento de forma *.
     * 
     * @return the more attr
     */
    public String getMoreAttr();

    /**
     * Gets the adm db conn mgr.
     * 
     * @return the adm db conn mgr
     */
    public AdmDBConnMgr getAdmDBConnMgr();

    /**
     * Gets the style.
     * 
     * @return the style
     */
    public String getStyle();

    /**
     * Gets the style class.
     * 
     * @return the style class
     */
    public String getStyleClass();

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId();

    /**
     * Gets the form fe.
     * 
     * @return the form fe
     */
    public FormFE getFormFE();

    /**
     * regresa el html del elemento de la forma
     * Returs the html of the form element.
     * 
     * @return the html
     */
    public String getHtml();

    /**
     * Set attributes to class according with the xml tag element.
     */
    public void setAttributes();



}
