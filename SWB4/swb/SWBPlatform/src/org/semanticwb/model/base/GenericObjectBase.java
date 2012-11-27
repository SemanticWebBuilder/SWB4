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
package org.semanticwb.model.base;

import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericObjectBase.
 * 
 * @author Jei
 */
public class GenericObjectBase implements GenericObject
{
    
    /** The m_obj. */
    protected SemanticObject m_obj;
    
    /**
     * Instantiates a new generic object base.
     * 
     * @param obj the obj
     */
    public GenericObjectBase(SemanticObject obj)
    {
        this.m_obj=obj;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.GenericObject#getURI()
     */
    public String getURI()
    {
        return m_obj.getURI();
    }

    public static String shortToFullURI(String shorturi)
    {
        return SemanticObject.shortToFullURI(shorturi);
    }

    public String getShortURI()
    {
        return m_obj.getShortURI();
    }
    /**
     * Regresa URI codificado para utilizar en ligas de html.
     * 
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }     
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.GenericObject#getId()
     */
    public String getId()
    {
        return m_obj.getId();
    }

//    public String getSId()
//    {
//        String id=getSemanticObject().getRDFName();//getId();
//        //SemanticClass cls=getSemanticObject().getSemanticClass();
//        //if(cls!=swb_WebPage)
//        //{
//        //    id=cls.getClassId()+":"+id;
//        //}
//        return id;
//    }
    
    /* (non-Javadoc)
 * @see org.semanticwb.model.GenericObject#getSemanticObject()
 */
public SemanticObject getSemanticObject()
    {
        return m_obj;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     * 
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public GenericObject setProperty(String prop, String value)
    {
        m_obj.setProperty(_getProperty(prop), value);
        return this;
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.GenericObject#removeProperty(java.lang.String)
     */
    public GenericObject removeProperty(String prop)
    {
        m_obj.removeProperty(_getProperty(prop));
        return this;
    }      

    /**
     * Gets the property.
     * 
     * @param prop the prop
     * @return the property
     */
    public String getProperty(String prop)
    {
        return  getProperty(prop, null);
    }
    
    /**
     * Gets the property.
     * 
     * @param prop the prop
     * @param defValue the def value
     * @return the property
     */
    public String getProperty(String prop, String defValue)
    {
        return getSemanticObject().getProperty(_getProperty(prop), defValue);
    }
    
    /**
     * _get property.
     * 
     * @param prop the prop
     * @return the semantic property
     */
    private SemanticProperty _getProperty(String prop)
    {
        return new SemanticProperty(m_obj.getModel().getRDFModel().createProperty(m_obj.getModel().getNameSpace()+"prop_"+prop));
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return m_obj.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return m_obj.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }
    
    /**
     * Regresa ruta de trabajo del objeto relativa al directorio work
     * ejemplo: /sep/Template/1
     * /dominio/Objeto/id.
     * 
     * @return String con la ruta relativa al directorio work
     */
    public String getWorkPath()
    {
        return m_obj.getWorkPath();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.GenericObject#dispose()
     */
    public void dispose() {
        
    }

}
