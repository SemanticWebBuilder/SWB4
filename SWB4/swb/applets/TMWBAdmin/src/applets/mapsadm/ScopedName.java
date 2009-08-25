/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package applets.mapsadm;

/*
 * ScopedName.java
 *
 * Created on 5 de septiembre de 2002, 18:30
 */

/**
 *
 * @author  Administrador
 * @version 
 */
public class ScopedName {
    private String name;
    private String scope;
    private String language;

    /** Creates new ScopedName */
    public ScopedName() {
    }

    /** Creates new ScopedName */
    public ScopedName(String name, String scope, String language) {
        this.name=name;
        this.scope=scope;
        this.language=language;
    }

    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /** Getter for property scope.
     * @return Value of property scope.
     */
    public java.lang.String getScope() {
        return scope;
    }
    
    /** Setter for property scope.
     * @param scope New value of property scope.
     */
    public void setScope(java.lang.String scope) {
        this.scope = scope;
    }
    
    public String toString()
    {
        String ret=name;
        //if(scope!=null)
            //ret+=" "+scope;
        return ret;
    }
    
    /** Getter for property language.
     * @return Value of property language.
     */
    public java.lang.String getLanguage() {
        return language;
    }
    
    /** Setter for property language.
     * @param language New value of property language.
     */
    public void setLanguage(java.lang.String language) {
        this.language = language;
    }
    
}
