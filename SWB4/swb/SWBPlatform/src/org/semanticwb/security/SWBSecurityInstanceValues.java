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
package org.semanticwb.security;

import java.util.Properties;

/**
 * Class to contain instance security configuration values, to avoid been converting every request
 * @author serch
 */
public class SWBSecurityInstanceValues {
    private int minlength=0;
    private boolean differFromLogin=false;
    private int complexity=0;
    private boolean forceChage=false;
    private int expires=0;
    private int inactive=0;
    private boolean sendMail=false;
    private boolean restrict=false;
    private boolean multiple=false;
    private boolean encrypt=false;

    public SWBSecurityInstanceValues(Properties props)
    {
        if (null!=props){
            try { minlength = Integer.parseInt(props.getProperty("password/minlength", "0")); } catch (Exception noe) {} //if fails go for default value
            try { differFromLogin = Boolean.parseBoolean(props.getProperty("password/differFromLogin", "false")); } catch (Exception noe) {} //if fails go for default value
            if ("simple".equalsIgnoreCase(props.getProperty("password/complexity", "none"))){complexity=1;}
            if ("complex".equalsIgnoreCase(props.getProperty("password/complexity", "none"))){complexity=2;}
            try { forceChage = Boolean.parseBoolean(props.getProperty("password/forceChangeOnFirstLogon", "false")); } catch (Exception noe) {} //if fails go for default value
            try { expires = Integer.parseInt(props.getProperty("password/expiresInDays", "0")); } catch (Exception noe) {} //if fails go for default value
            try { inactive = Integer.parseInt(props.getProperty("account/inactiveInDays", "0")); } catch (Exception noe) {} //if fails go for default value
            try { sendMail = Boolean.parseBoolean(props.getProperty("account/sendMailOnLogon", "false")); } catch (Exception noe) {} //if fails go for default value
            try { restrict = Boolean.parseBoolean(props.getProperty("session/restrictToSingleIP", "false")); } catch (Exception noe) {} //if fails go for default value
            try { multiple = Boolean.parseBoolean(props.getProperty("session/restrictMultipleLogon", "false")); } catch (Exception noe) {} //if fails go for default value
            try { encrypt = Boolean.parseBoolean(props.getProperty("login/encryptData", "false")); } catch (Exception noe) {} //if fails go for default value
        }
    }

    public int getComplexity()
    {
        return complexity;
    }

    public boolean isDifferFromLogin()
    {
        return differFromLogin;
    }

    public boolean isEncrypt()
    {
        return encrypt;
    }

    public int getExpires()
    {
        return expires;
    }

    public boolean isForceChage()
    {
        return forceChage;
    }

    public int getInactive()
    {
        return inactive;
    }

    public int getMinlength()
    {
        return minlength;
    }

    public boolean isMultiple()
    {
        return multiple;
    }

    public boolean isRestrict()
    {
        return restrict;
    }

    public boolean isSendMail()
    {
        return sendMail;
    }


}
