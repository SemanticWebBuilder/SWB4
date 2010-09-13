
/**
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
package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class Dns.
 */
public class Dns extends DnsBase {
    
    /** The names. */
    private static HashMap<String, Dns> names = null;

    /**
     * Instantiates a new dns.
     * 
     * @param base the base
     */
    public Dns(SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DnsBase#setDefault(boolean)
     */
    @Override
    public void setDefault(boolean dnsDefault) {
        super.setDefault(dnsDefault);

        Iterator<Dns> it = getWebSite().listDnses();

        while (it.hasNext()) {
            Dns d = it.next();

            if (!d.equals(this) && d.isDefault()) {
                d.setDefault(false);
            }
        }
    }

    /**
     * Refresh.
     */
    synchronized public static void refresh() {
        names = new HashMap();

        Iterator<Dns> it = ClassMgr.listDnses();

        while (it.hasNext()) {
            Dns dns = it.next();

            names.put(dns.getDns(), dns);
        }
    }

    /**
     * Gets the dns.
     * 
     * @param serverName the server name
     * @return the dns
     */
    public static Dns getDns(String serverName) {
        if (names == null) {
            refresh();
        }

        return names.get(serverName);
    }

    /**
     * Cache dns.
     * 
     * @param serverName the server name
     * @param dns the dns
     */
    public static void cacheDns(String serverName, Dns dns)
    {
        names.put(serverName, dns);
    }

    /**
     * Contains dns.
     * 
     * @param serverName the server name
     * @return true, if successful
     */
    public static boolean containsDns(String serverName)
    {
        return names.containsKey(serverName);
    }
}
