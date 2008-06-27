
package org.semanticwb.base.util.imp;

import org.semanticwb.Logger;
import org.apache.log4j.Level;

/**
 *
 * @author Jei
 */
public class Logger4jImpl implements Logger
{
    //Definir objetos para ambos Loggers
    private org.apache.log4j.Logger log=null;
    
    /**
     * 
     * @param logger
     */
    public Logger4jImpl(org.apache.log4j.Logger logger)
    {
        log=logger;
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
    }       
}
