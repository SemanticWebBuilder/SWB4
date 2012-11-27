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

// TODO: Auto-generated Javadoc
/**
 * Clase abstracta que implementa metodos de la interface WBJsValidationsFE
 * <p>
 * This is an abstract class that implements the  WBJsValidationsFE.
 * 
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public abstract class WBJsValidationsFEAbs implements WBJsValidationsFE
{
    
    /** The formfe name. */
    protected String formfeName;
    
    /** The field. */
    protected String field;
    
    /** The minsize. */
    protected int minsize;

    /**
     * Instantiates a new wB js validations fe abs.
     */
    public WBJsValidationsFEAbs()
    {
        formfeName = null;
        field = null;
        minsize = 0;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE#setFormFEName(java.lang.String)
     */
    public void setFormFEName(String formfeName)
    {
        this.formfeName = formfeName;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE#setField(java.lang.String)
     */
    public void setField(String field)
    {
        this.field = field;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE#setMinsize(int)
     */
    public void setMinsize(int minsize)
    {
        this.minsize = minsize;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE#getFormFEName()
     */
    public String getFormFEName()
    {
        return formfeName;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE#getField()
     */
    public String getField()
    {
        return field;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFE#getMinsize()
     */
    public int getMinsize()
    {
        return minsize;
    }

}
