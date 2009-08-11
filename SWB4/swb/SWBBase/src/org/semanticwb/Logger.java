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
 

package org.semanticwb;

/**
 *
 * @author Javier Solis 
 */
public interface Logger 
{
    /*
     * Usado para debugueos mas profundos
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void trace(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void trace(String txt);
    /**
     * 
     * @param t
     */
    public void trace(Throwable t);

    /*
     * Usado para debugueos
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void debug(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void debug(String txt);
    /**
     * 
     * @param t
     */
    public void debug(Throwable t);

    /*
     * Usado para Informacion del Sistema 
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void info(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void info(String txt);
    /**
     * 
     * @param t
     */
    public void info(Throwable t);

    /*
     * Usado para Warnings
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void warn(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void warn(String txt);
    /**
     * 
     * @param t
     */
    public void warn(Throwable t);

    /*
     * Usado para Errores
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void error(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void error(String txt);
    /**
     * 
     * @param t
     */
    public void error(Throwable t);

    /*
     * Usado para Errores RunTime Exceptions
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void fatal(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void fatal(String txt);
    /**
     * 
     * @param t
     */
    public void fatal(Throwable t);
    
    /*
     * Usado para notificar eventos
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void event(String txt, Throwable t);
    /**
     * 
     * @param txt
     */
    public void event(String txt);
    /**
     * 
     * @param t
     */
    public void event(Throwable t);
    
}
