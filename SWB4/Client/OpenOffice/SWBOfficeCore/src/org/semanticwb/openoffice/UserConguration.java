/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.net.URI;

/**
 *
 * @author victor.lorenzana
 */
public class UserConguration
{

    private URI uri;
    private String login;

    public UserConguration(URI uri, String login)
    {
        this.uri = uri;
        this.login = login;
    }
    public URI getURI()
    {
        return uri;
    }
    public String getLogin()
    {
        return login;
    }
}
