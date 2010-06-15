/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.security.limiter;

/**
 * Multiple Login Attempts controls
 * @author serch
 */
public class FailedAttempt {
    private String login;
    private boolean blocked=false;
    private int cont = 0;
    private long tsBlockedTime=0;

    public FailedAttempt(String login)
    {
        this.login = login;
    }

    public boolean isBlocked()
    {
        return blocked;
    }

    public int getCont()
    {
        return cont;
    }

    public void failedAttempt()
    {
        cont++;
        if (cont>4){
            block();
        }
    }

    public String getLogin()
    {
        return login;
    }


    public long getTsBlockedTime()
    {
        return tsBlockedTime;
    }

    public void block()
    {
        this.tsBlockedTime = System.currentTimeMillis();
        this.blocked = true;
    }


}
