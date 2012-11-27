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
package org.semanticwb.security.auth;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.semanticwb.model.SWBContext;

// TODO: Auto-generated Javadoc
/**
 * CallbackHandler para el gateway de office
 * CallbackHandler fo Office gateway.
 * 
 * @author victor.lorenzana
 */
public final class SWB4CallbackHandlerGateWayOffice implements CallbackHandler
{

    /** The password. */
    private String login,  password;

    /**
     * Constructor, requiere login y password del sujeto a autenticar.
     * 
     * @param login the login
     * @param password the password
     */
    public SWB4CallbackHandlerGateWayOffice(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    /**
     * Manejo de los parámetros para enviarlos al JAAS
     * parameter management as required by JAAS.
     * 
     * @param callbacks the callbacks
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws UnsupportedCallbackException the unsupported callback exception
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
                //textInputCallback.setText(SWBContext.getAdminWebSite().getId());
                textInputCallback.setText(SWBContext.WEBSITE_ADMIN);
            }
        }
    }
}
