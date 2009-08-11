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
 


package org.semanticwb.portal.admin.admresources.lib;

import org.semanticwb.portal.admin.admresources.FormFE;
import org.semanticwb.portal.admin.admresources.db.*;

/** Esta es una clase abstracta que implementa los metodos genericos de la interface WBAdmResource.
 * <p>
 * This is an abstract class that implements the generic methods in the WBAdmResource interface
 * @author  Jorge Alberto Jim?nez
 * @version 1.0
 */

public abstract class WBAdmResourceAbs implements WBAdmResource {


    protected String name=null;
    protected String moreattr=null;
    protected String style=null;
    protected String styleclass=null;
    protected AdmDBConnMgr dbconnmgr=null;
    protected String label=null;
    protected String id=null;
    protected FormFE formFe=null;

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

         /** inserta m?s atributos a un elemento de forma **/
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

    public void setId(String id){
        this.id=id;
    }

    public void setFormFE(FormFE formFe){
        this.formFe=formFe;
        dbconnmgr=this.formFe.getAdmDBConnMgr();
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

    public String getId(){
        return id;
    }

    public FormFE getFormFE(){
        return formFe;
    }

}
