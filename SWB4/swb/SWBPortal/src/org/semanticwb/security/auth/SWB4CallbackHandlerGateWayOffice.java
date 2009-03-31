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
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.semanticwb.model.SWBContext;

/**
 * CallbackHandler para el gateway de office
 * CallbackHandler fo Office gateway
 * @author victor.lorenzana
 */
public final class SWB4CallbackHandlerGateWayOffice implements CallbackHandler
{

    private String login,  password;

    /**
     * Constructor, requiere login y password del sujeto a autenticar
     * @param login
     * @param password
     */
    public SWB4CallbackHandlerGateWayOffice(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    /**
     * Manejo de los parámetros para enviarlos al JAAS
     * parameter management as required by JAAS
     * @param callbacks
     * @throws java.io.IOException
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     */
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
            else if (callbacks[i] instanceof TextInputCallback)
            {
                TextInputCallback textInputCallback = (TextInputCallback) callbacks[i];
                textInputCallback.setText(SWBContext.getAdminWebSite().getId());
            }
        }
    }
}
