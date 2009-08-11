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
 * WBIndexObjList.java
 *
 * Created on 11 de julio de 2006, 02:55 PM
 */

package org.semanticwb.portal.indexer;

import java.util.AbstractList;
import java.util.ArrayList;
import org.semanticwb.model.User;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBIndexObjList extends AbstractList
{
    private ArrayList objs=new ArrayList(400);
    //protected int size=0;
    protected User user;
    protected int page;
    protected int pindex;
    private int hits=0;
    private int pageLength=10;
    
    /** Creates a new instance of WBIndexObjList */
    public SWBIndexObjList(User user, int page, int pindex)
    {
        this.user=user;
        this.page=page;
        this.pindex=pindex;
    }
    
    public Object get(int index)
    {
        return objs.get(index);
    }
    
    public int size()
    {
        return objs.size();
    }
    
    public void add(int index, Object element) 
    {
        SWBIndexObj obj=(SWBIndexObj)element;
        if(SWBIndexer.validate(obj, user))
        {
            objs.add(index,obj);
            //size++;
        }
    }    
    
    /**
     * Getter for property hits.
     * @return Value of property hits.
     */
    public int getHits()
    {
        return hits;
    }
    
    /**
     * Setter for property hits.
     * @param hits New value of property hits.
     */
    protected void setHits(int hits)
    {
        this.hits = hits;
    }
    
    /**
     * Getter for property pageLength.
     * @return Value of property pageLength.
     */
    public int getPageLength()
    {
        return pageLength;
    }
    
    /**
     * Setter for property pageLength.
     * @param pageLength New value of property pageLength.
     */
    public void setPageLength(int pageLength)
    {
        this.pageLength = pageLength;
    }
    
}
