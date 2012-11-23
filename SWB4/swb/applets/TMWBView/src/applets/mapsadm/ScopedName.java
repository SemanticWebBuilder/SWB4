/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/


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
