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
 * Interface que determina los metodos a implementar por los objetos de tipo validaci�n de JavaScript
 * <p>
 * This is an abstract class that implements the  WBAdmResource interface and its used in the containers objects (ex. FormFE,SelectFE)
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public interface WBJsValidationsFE
{

    /**
     * Sets the form fe name.
     * 
     * @param s the new form fe name
     */
    public abstract void setFormFEName(String s);

    /**
     * Sets the field.
     * 
     * @param s the new field
     */
    public abstract void setField(String s);

    /**
     * Sets the minsize.
     * 
     * @param i the new minsize
     */
    public abstract void setMinsize(int i);

    /**
     * Gets the form fe name.
     * 
     * @return the form fe name
     */
    public abstract String getFormFEName();

    /**
     * Gets the field.
     * 
     * @return the field
     */
    public abstract String getField();

    /**
     * Gets the minsize.
     * 
     * @return the minsize
     */
    public abstract int getMinsize();

    /**
     * Gets the html.
     * 
     * @param locale the locale
     * @return the html
     */
    public abstract String getHtml(java.util.Locale locale);
}
