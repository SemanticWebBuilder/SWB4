/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
        cont++;
        if (cont>4){
            block();
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
