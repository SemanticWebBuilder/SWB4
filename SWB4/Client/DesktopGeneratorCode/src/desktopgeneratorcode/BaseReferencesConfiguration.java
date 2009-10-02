/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopgeneratorcode;

import java.io.File;

/**
 *
 * @author victor.lorenzana
 */
public final class BaseReferencesConfiguration  extends Configuration {

    public static final String CONFIGURATION_PROPERTY_NAME = "org.semanticwb.openoffice.configuration.Configuration";
    public BaseReferencesConfiguration()
    {
        super(new File(System.getProperty(CONFIGURATION_PROPERTY_NAME, "codegeneratorconfig.xml")));
    }

}
