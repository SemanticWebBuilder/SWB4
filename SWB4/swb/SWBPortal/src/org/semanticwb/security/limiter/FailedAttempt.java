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
package org.semanticwb.security.limiter;

// TODO: Auto-generated Javadoc
/**
 * Multiple Login Attempts controls.
 * 
 * @author serch
 */
public class FailedAttempt {
    
    /** The login. */
    private String login;
    
    /** The blocked. */
    private boolean blocked=false;
    
    /** The cont. */
    private int cont = 0;
    
    /** The ts blocked time. */
    private long tsBlockedTime=0;

    /**
     * Instantiates a new failed attempt.
     * 
     * @param login the login
     */
    public FailedAttempt(String login)
    {
        this.login = login;
    }

    /**
     * Checks if is blocked.
     * 
     * @return true, if is blocked
     */
    public boolean isBlocked()
    {
        return blocked;
    }

    /**
     * Gets the cont.
     * 
     * @return the cont
     */
    public int getCont()
    {
        return cont;
    }

    /**
     * Failed attempt.
     */
    public void failedAttempt()
    {
        if (!blocked){
            cont++;
            if (cont>4){
                block();
            }
        }
    }

    /**
     * Gets the login.
     * 
     * @return the login
     */
    public String getLogin()
    {
        return login;
    }


    /**
     * Gets the ts blocked time.
     * 
     * @return the ts blocked time
     */
    public long getTsBlockedTime()
    {
        return tsBlockedTime;
    }

    /**
     * Block.
     */
    public void block()
    {
        this.tsBlockedTime = System.currentTimeMillis();
        this.blocked = true;
    }


}
