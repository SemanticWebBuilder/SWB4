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

package org.semanticwb.portal.social.facebook;

/**
 *
 * @author Jorge Jiménez
 */
public class Photo {
    
    String pid=null;
    String aid=null;
    String owner=null;
    String src_small=null;
    String src_big=null;
    String src=null;
    String link=null;
    String caption=null;
    String created=null;
    String modified=null;

    public void setPid(String pid){
        this.pid=pid;
    }

    public void setAid(String aid){
        this.aid=aid;
    }

    public void setOwner(String owner){
        this.owner=owner;
    }

    public void setSrc_small(String src_small){
        this.src_small=src_small;
    }

    public void setSrc_big(String src_big){
        this.src_big=src_big;
    }

    public void setSrc(String src){
        this.src=src;
    }

    public void setLink(String link){
        this.link=link;
    }

    public void setCaption(String caption){
        this.caption=caption;
    }
    public void setCreated(String created){
        this.created=created;
    }
    public void setModified(String modified){
        this.modified=modified;
    }

    //Sets

    public String getPid(){
        return pid;
    }

    public String getAid(){
        return aid;
    }

    public String getOwner(){
        return owner;
    }

    public String getSrc_small(){
        return src_small;
    }

    public String getSrc_big(){
        return src_big;
    }

    public String getSrc(){
        return src;
    }

    public String getLink(){
        return link;
    }

    public String getCaption(){
        return caption;
    }

    public String getCreated(){
        return created;
    }

    public String getModified(){
        return modified;
    }

}
