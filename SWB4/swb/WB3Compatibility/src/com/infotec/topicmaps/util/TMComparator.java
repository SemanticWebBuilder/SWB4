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
**/


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
