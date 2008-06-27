
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
