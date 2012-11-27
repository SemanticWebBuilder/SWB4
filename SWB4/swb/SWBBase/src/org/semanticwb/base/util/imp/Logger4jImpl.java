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
package org.semanticwb.base.util.imp;


import org.semanticwb.Logger;
import org.apache.log4j.Level;
import org.semanticwb.SWBUtils;


// TODO: Auto-generated Javadoc
/* Se eliminan los comentarios de documentaci&oacute;n de esta clase, para que
 * se hereden los de la clase padre. */
/**
 * The Class Logger4jImpl.
 * 
 * @author Jei
 */
public class Logger4jImpl implements Logger
{


    //Definir objetos para ambos Loggers
    /**
     * Instance of a Logger.
     * <p>Instancia de un logger.</p>
     */
    private org.apache.log4j.Logger log = null;

    //Definir objetos para ambos Loggers
    /**
     * Reference to the object class from which the logger will be used.
     * <p>Referencia a la clase (tipo) del objeto desde la que se usar&aacute; el
     * logger.</p>
     */
    private Class cls = null;

    /**
     * Identifier for the <q>TRACE</q> kind of message in the log. This kind of
     * message must provide the information needed to trace an error or exceptional condition.
     * <p>Identificador para el tipo de mensaje <q>TRACE</q> en la bit&aacute;cora.
     * Este tipo de mensaje debe proveer la informaci&oacute;n necesaria para rastrear
     * un error o condici&oacute;n excepcional.</p>
     */
    private static final String TRACE = "trace";

    /**
     * Identifier for the <q>DEBUG</q> kind of message in the log. This kind of
     * message must provide information for bug identification and resolution.
     * <p>Identificador para el tipo de mensaje <q>DEBUG</q> en la bit&aacute;cora.
     * Este tipo de mensaje debe mostrar informaci&oacute;n para identificaci&oacute;n
     * y resoluci&oacute;n de bugs.</p>
     */
    private static final String DEBUG = "debug";

    /**
     * Identifier for the <q>INFO</q> kind of message in the log. This kind of
     * message provides information in a general manner.
     * <p>Identificador para el tipo de mensaje <q>INFO</q> en la bit&aacute;cora.
     * Este tipo de mensaje muestra informaci&oacute;n de manera general.</p>
     */
    private static final String INFO = "info";

    /**
     * Identifier for the <q>WARN</q> kind of message in the log. This kind of
     * message provides a warning about a certain situation.
     * <p>Identificador para el tipo de mensaje <q>WARN</q> en la bit&aacute;cora.
     * Este tipo de mensaje provee una advertencia sobre cierta situaci&oacute.</p>
     */
    private static final String WARN = "warn";

    /**
     * Identifier for the <q>ERROR</q> kind of message in the log. This kind of
     * message provides notice of an error and information related to that error.
     * <p>Identificador para el tipo de mensaje <q>ERROR</q> en la bit&aacute;cora.
     * Este tipo de mensaje provee notificaci&oacute;n sobre un error e informaci&oacute;n
     * relacionada a ese error.</p>
     */
    private static final String ERROR = "error";

    /**
     * Identifier for the <q>FATAL</q> kind of message in the log. This kind of
     * message provides notice of a situation from which the application cannot recover.
     * <p>Identificador para el tipo de mensaje <q>FATAL</q> en la bit&aacute;cora.
     * Este tipo de mensaje provee notificaci&oacute;n de una situaci&oacute;n de
     * la que la aplicaci&oacute;n no se puede recuperar.</p>
     */
    private static final String FATAL = "fatal";

    /**
     * Identifier for the <q>EVENT</q> kind of message in the log. This kind of
     * message provides notice of the happening of a specific event.
     * <p>Identificador para el tipo de mensaje <q>EVENT</q> en la bit&aacute;cora.
     * Este tipo de mensaje provee notificaci&oacute;n de la ocurrencia de un evento
     * espec&iacute;fico.</p>
     */
    private static final String EVENT = "event";

    
    /**
     * Creates an instance of this logger associating the class that will use it.
     * <p>Crea una instancia de este logger asociando la clase que lo usar&aacute;.</p>
     * @param cls the object class from which the logger will be used
     */
    public Logger4jImpl(Class cls)
    {
        this.log = org.apache.log4j.Logger.getLogger(cls);
        this.cls = cls;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#trace(java.lang.String, java.lang.Throwable)
     */
    public void trace(String txt, Throwable t)
    {
        log.trace(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.TRACE);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#trace(java.lang.String)
     */
    public void trace(String txt)
    {
        log.trace(txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#trace(java.lang.Throwable)
     */
    public void trace(Throwable t)
    {
        log.trace(null,t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.TRACE);
    }
    
    /*
     * Usado para debugueos
     */
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#debug(java.lang.String, java.lang.Throwable)
     */
    public void debug(String txt, Throwable t)
    {
        log.debug(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.DEBUG);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#debug(java.lang.String)
     */
    public void debug(String txt)
    {
        log.debug(txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#debug(java.lang.Throwable)
     */
    public void debug(Throwable t)
    {
        log.debug(null,t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.DEBUG);
    }
    
    /*
     * Usado para Informacion del Sistema 
     */
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#info(java.lang.String, java.lang.Throwable)
     */
    public void info(String txt, Throwable t)
    {
        log.info(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.INFO);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#info(java.lang.String)
     */
    public void info(String txt)
    {
        log.info(txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#info(java.lang.Throwable)
     */
    public void info(Throwable t)
    {
        log.info(null,t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.INFO);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#warn(java.lang.String, java.lang.Throwable)
     */
    public void warn(String txt, Throwable t)
    {
        log.warn(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.WARN);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#warn(java.lang.String)
     */
    public void warn(String txt)
    {
        log.warn(txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#warn(java.lang.Throwable)
     */
    public void warn(Throwable t)
    {
        log.warn(null,t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.WARN);
    }
    
    /*
     * Usado para Errores
     */    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#error(java.lang.String, java.lang.Throwable)
     */
    public void error(String txt, Throwable t)
    {
        log.error(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.ERROR);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#error(java.lang.String)
     */
    public void error(String txt)
    {
        log.error(txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#error(java.lang.Throwable)
     */
    public void error(Throwable t)
    {
        log.error(null,t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.ERROR);
    }    

    /*
     * Usado para Errores RunTime Exceptions
     */
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#fatal(java.lang.String, java.lang.Throwable)
     */
    public void fatal(String txt, Throwable t)
    {
        log.fatal(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.FATAL);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#fatal(java.lang.String)
     */
    public void fatal(String txt)
    {
        log.fatal(txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#fatal(java.lang.Throwable)
     */
    public void fatal(Throwable t)
    {
        log.fatal(null,t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.FATAL);
    }   
    
    /*
     * Usado para notificacion de eventos
     */
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#event(java.lang.String, java.lang.Throwable)
     */
    public void event(String txt, Throwable t)
    {
        log.log(Level.OFF, txt, t);
        SWBUtils.ERROR.addError(txt, t, cls, Logger4jImpl.EVENT);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#event(java.lang.String)
     */
    public void event(String txt)
    {
        log.log(Level.OFF, txt);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.Logger#event(java.lang.Throwable)
     */
    public void event(Throwable t)
    {
        log.log(Level.OFF, null, t);
        SWBUtils.ERROR.addError(null, t, cls, Logger4jImpl.EVENT);
    }       
}
