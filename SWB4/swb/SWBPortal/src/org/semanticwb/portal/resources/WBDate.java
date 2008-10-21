/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.resources;

import java.util.HashMap;
import java.util.Date;
import org.semanticwb.SWBUtils;


/** Esta clase se encarga de desplegar el formato largo de la fecha en espa�ol.
 * Viene de la versi�n 2 de WebBuilder.
 *
 * This class displays long format date in Spanish. It comes from WebBuilder 2.
 * @author : Javier Solis
 * @modifier: Jorge Jiménez
 * @since June 20th 2002, 16:38
 */

public class WBDate
{
    /**
     * Crea un nuevo objeto WBDate.
     */
    public WBDate() {
    }

    /**
     * @return
     */    
    public String getDateEs()
    {
        return SWBUtils.TEXT.getStrDate(new Date(), "es", null);
    }

    /**
     * @return
     */    
    public String getDate()
    {
        return getDateEs();
    }

    /**
     * @param args
     * @return
     */    
    public String getDate(HashMap args)
    {
        String lang = (String) args.get("language");
        String format = (String) args.get("format");
        if (lang == null) {
            lang = "es";
        }
        return SWBUtils.TEXT.getStrDate(new Date(), lang, format);
    }
}