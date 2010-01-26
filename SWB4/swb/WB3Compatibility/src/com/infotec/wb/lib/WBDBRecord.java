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
 * WBDBRecord.java
 *
 * Created on 27 de junio de 2002, 13:00
 */

package com.infotec.wb.lib;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.AFObserver;

/** Interfaz: Es utilizada por los objetos de cache de los registros de Base de Datos.
 * @author Javier Solis Gonzalez
 * @version
 */
public interface WBDBRecord
{
     
    /** Elimina el registro de la base de datos asi como todos los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws AFException;

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void update() throws AFException;

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void load() throws AFException;

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws AFException;

    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
    public void registerObserver(AFObserver obs);
}

