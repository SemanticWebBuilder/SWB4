
package org.semanticwb.base.util.imp;

import org.semanticwb.Logger;
import org.apache.log4j.Level;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class Logger4jImpl implements Logger
{
    //Definir objetos para ambos Loggers
    private org.apache.log4j.Logger log=null;
    //Definir objetos para ambos Loggers
    private Class cls=null;

    private static final String TRACE= "trace";
    private static final String DEBUG= "debug";
    private static final String INFO= "info";
    private static final String WARN= "warn";
    private static final String ERROR= "error";
    private static final String FATAL= "fatal";
    private static final String EVENT= "event";

    
    /**
     * 
     * @param logger
     */
    public Logger4jImpl(Class cls)
    {
        this.log=org.apache.log4j.Logger.getLogger(cls);
        this.cls=cls;
    }
    
    /*
     * Usado para debugueos mas profundos
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void trace(String txt, Throwable t)
    {
        log.trace(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, TRACE);
    }
    
    /**
     * 
     * @param txt
     */
    public void trace(String txt)
    {
        log.trace(txt);
    }
    
    /**
     * 
     * @param t
     */
    public void trace(Throwable t)
    {
        log.trace(null,t);
        SWBUtils.ERROR.addError(null, t, cls, TRACE);
    }
    
    /*
     * Usado para debugueos
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void debug(String txt, Throwable t)
    {
        log.debug(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, DEBUG);
    }
    
    /**
     * 
     * @param txt
     */
    public void debug(String txt)
    {
        log.debug(txt);
    }
    
    /**
     * 
     * @param t
     */
    public void debug(Throwable t)
    {
        log.debug(null,t);
        SWBUtils.ERROR.addError(null, t, cls, DEBUG);
    }
    
    /*
     * Usado para Informacion del Sistema 
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void info(String txt, Throwable t)
    {
        log.info(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, INFO);
    }
    
    /**
     * 
     * @param txt
     */
    public void info(String txt)
    {
        log.info(txt);
    }
    
    /**
     * 
     * @param t
     */
    public void info(Throwable t)
    {
        log.info(null,t);
        SWBUtils.ERROR.addError(null, t, cls, INFO);
    }
    
    /*
     * Usado para Warnings
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void warn(String txt, Throwable t)
    {
        log.warn(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, WARN);
    }
    
    /**
     * 
     * @param txt
     */
    public void warn(String txt)
    {
        log.warn(txt);
    }
    
    /**
     * 
     * @param t
     */
    public void warn(Throwable t)
    {
        log.warn(null,t);
        SWBUtils.ERROR.addError(null, t, cls, WARN);
    }
    
    /*
     * Usado para Errores
     */    
    /**
     * 
     * @param txt
     * @param t
     */
    public void error(String txt, Throwable t)
    {
        log.error(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, ERROR);
    }
    
    /**
     * 
     * @param txt
     */
    public void error(String txt)
    {
        log.error(txt);
    }
    
    /**
     * 
     * @param t
     */
    public void error(Throwable t)
    {
        log.error(null,t);
        SWBUtils.ERROR.addError(null, t, cls, ERROR);
    }    

    /*
     * Usado para Errores RunTime Exceptions
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void fatal(String txt, Throwable t)
    {
        log.fatal(txt,t);
        SWBUtils.ERROR.addError(txt, t, cls, FATAL);
    }
    
    /**
     * 
     * @param txt
     */
    public void fatal(String txt)
    {
        log.fatal(txt);
    }
    
    /**
     * 
     * @param t
     */
    public void fatal(Throwable t)
    {
        log.fatal(null,t);
        SWBUtils.ERROR.addError(null, t, cls, FATAL);
    }   
    
    /*
     * Usado para notificacion de eventos
     */
    /**
     * 
     * @param txt
     * @param t
     */
    public void event(String txt, Throwable t)
    {
        log.log(Level.OFF, txt, t);
        SWBUtils.ERROR.addError(txt, t, cls, EVENT);
    }
    
    /**
     * 
     * @param txt
     */
    public void event(String txt)
    {
        log.log(Level.OFF, txt);
    }
    
    /**
     * 
     * @param t
     */
    public void event(Throwable t)
    {
        log.log(Level.OFF, null, t);
        SWBUtils.ERROR.addError(null, t, cls, EVENT);
    }       
}
