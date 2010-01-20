/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * WBPriorityComaprator.java
 *
 * Created on 19 de julio de 2002, 12:39
 */

package com.infotec.topicmaps.util;

import java.util.*;

import com.infotec.topicmaps.*;

/** objeto: comparador de topicos, se utiliza para ordenar topicos
 * @author Javier Solis Gonzalez
 * @version
 */
public class TMComparator implements Comparator
{

    Topic scope = null;

    /** Creates new WBPriorityComaprator */
    public TMComparator()
    {
    }

    public TMComparator(Topic scope)
    {
        this.scope = scope;
    }

    public int compare(java.lang.Object obj, java.lang.Object obj1)
    {
        int ret;
        Topic tp = (Topic) obj;
        Topic tp1 = (Topic) obj1;
        ret = tp.getSortName(scope).compareToIgnoreCase(tp1.getSortName(scope));
        if (ret == 0 && !tp.getId().equals(tp1.getId())) ret = -1;
        return ret;
    }

}
