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

import org.semanticwb.portal.admin.admresources.FormFE;
import org.semanticwb.portal.admin.admresources.db.*;

// TODO: Auto-generated Javadoc
/** Esta es una clase abstracta que implementa los metodos genericos de la interface WBAdmResource.
 * <p>
 * This is an abstract class that implements the generic methods in the WBAdmResource interface
 * @author  Jorge Alberto Jim?nez
 * @version 1.0
 */

public abstract class WBAdmResourceAbs implements WBAdmResource {


    /** The name. */
    protected String name=null;

    /** The moreattr. */
    protected String moreattr=null;

    /** The style. */
    protected String style=null;

    /** The styleclass. */
    protected String styleclass=null;

    /** The dbconnmgr. */
    protected AdmDBConnMgr dbconnmgr=null;

    /** The label. */
    protected String label=null;

    /** The id. */
    protected String id=null;

    /** The form fe. */
    protected FormFE formFe=null;

    /**
     * Creates a new instance of WBAdmResourceAbs.
     */
    public WBAdmResourceAbs() {
    }

    //sets

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setName(java.lang.String)
     */
    public void setName(String name){
        this.name=name;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setLabel(java.lang.String)
     */
    public void setLabel(String label) {
        this.label=label;
    }

         /**
          * inserta m?s atributos a un elemento de forma *.
          *
          * @param moreattr the new more attr
          */
    public void setMoreAttr(String moreattr) {
        this.moreattr=moreattr;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setAdmDBConnMgr(org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr)
     */
    public void setAdmDBConnMgr(AdmDBConnMgr dbconnmgr){
       this.dbconnmgr=dbconnmgr;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setStyle(java.lang.String)
     */
    public void setStyle(String style) {
        this.style=style;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setStyleClass(java.lang.String)
     */
    public void setStyleClass(String styleclass) {
        this.styleclass=styleclass;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setId(java.lang.String)
     */
    public void setId(String id){
        this.id=id;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#setFormFE(org.semanticwb.portal.admin.admresources.FormFE)
     */
    public void setFormFE(FormFE formFe){
        this.formFe=formFe;
        dbconnmgr=this.formFe.getAdmDBConnMgr();
    }

    //gets

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getName()
     */
    public String getName(){
        return name;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getLabel()
     */
    public String getLabel() {
        return label;
    }

     /**
      * obtine atributos insertados adicionalmente a un elemento de forma *.
      *
      * @return the more attr
      */
    public String getMoreAttr(){
        return moreattr;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getAdmDBConnMgr()
     */
    public AdmDBConnMgr getAdmDBConnMgr(){
       return dbconnmgr;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getStyle()
     */
    public String getStyle() {
        return style;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getStyleClass()
     */
    public String getStyleClass() {
         return styleclass;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getId()
     */
    public String getId(){
        return id;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResource#getFormFE()
     */
    public FormFE getFormFE(){
        return formFe;
    }
}
