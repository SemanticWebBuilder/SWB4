/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.security.auth;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author victor.lorenzana
 */
public final class SWB4CallbackHandlerGateWayOffice implements CallbackHandler
{

    private String login,  password;

    public SWB4CallbackHandlerGateWayOffice(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
        for (int i = 0; i < callbacks.length; i++)
        {
            if (callbacks[i] instanceof NameCallback)
            {
                NameCallback nameCallback = (NameCallback) callbacks[i];
                nameCallback.setName(login);

            }
            else if (callbacks[i] instanceof PasswordCallback)
            {
                PasswordCallback passwordCallback = (PasswordCallback) callbacks[i];
                passwordCallback.setPassword(password.toCharArray());
            }
        }
    }
}
