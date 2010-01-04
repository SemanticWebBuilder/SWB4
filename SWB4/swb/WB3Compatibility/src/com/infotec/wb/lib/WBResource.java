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


/*
 * WBResource2.java
 *
 * Created on 12 de mayo de 2004, 11:40 AM
 */

package com.infotec.wb.lib;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infotec.wb.core.*;
import com.infotec.appfw.exception.*;

import com.infotec.wb.core.db.RecResourceType;

/** La interfaz WBResource es la que define los m�todos que una clase debe implementar
 * para poder ser considerado como un recurso que pueda ser administrado por WebBuilder
 *
 * @author Javier Solis Gonzalez
 * @version 2.0
 */
public interface WBResource
{
    /** Asigna el Objeto Resource con la informaci�n base del recurso.
     * (es llamando cada que el recurso es modificado desde la administraci�n de WB)
     * @param base Objeto Resource
     */
    public void setResourceBase(Resource base) throws AFException;

    /** Es llamado cuando es cargado el recurso en memoria
     * (solo es llamado una vez)
     *
     */
    public void init() throws AFException;
    
    /** Regresa el Objeto Resource con la informaci�n base del recurso.
     * @return  Objeto Resource
     */
    public Resource getResourceBase();
    
    /**
     * Este m�todo permite al recurso procesar una acci�n. Este m�todo 
     * es llamado si la respuesta del cliente fue originada por un 
     * URL creado (por el recurso) con el m�todo 
     * <code>WBParamRequest.createActionURL()</code>.
     * <p>
     * T�picamente en la respuesta a una solicitud de una acci�n 
     * el recurso actualiza su estado basado en la informaci�n enviada en 
     * el par�metro actionResponse. 
     * En esta acci�n el recurso puede:
     * <ul>
     * <li>enviar un redireccionamiento
     * <li>cambiar su estado de la ventana
     * <li>cambiar el modo del recurso
     * <li>asignar par�metros al render
     * </ul>
     * <p>
     * Una solicitud del cliente hacia un actionURL es traslada a un processAction 
     * y muchos renders, uno por cada recurso dentro de la secci�n.
     * El procesamiento de la acci�n es finalizado antes que el request sea invocado.
     *
     * @param request
     *                   el servlet request
     * @param   actionResponse
     *           WBActionResponse con los par�metros de WebBuilder(User, Topic, Action).
     * @exception  AFException
     *           Si el recurso tiene alg�n problema con el procesamiento de la acci�n
     * @exception  IOException
     *           Si hay algun problema de I/O con el procesamiento de streams.
     */
    public void processAction(HttpServletRequest request, WBActionResponse actionResponse)
        throws AFException, java.io.IOException;
    
    /**
     * Este m�todo permite al recurso generar el contenido de la respuesta 
     * basada en el estado actual.
     *
     * @param   request
     *          el servlet request
     * @param   response
     *          el servlet response
     * @param   paramRequest
     *          WBParamRequest con los par�metros de WebBuilder (User, Topic, Action).
     *
     * @exception  AFException
     *             Si el recurso tiene alg�n problema con la generaci�n de la respuesta.
     * @exception  IOException
     *             Si hay alg�n problema de I/O con el procesamiento de streams.
     */
    public void render(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramRequest)
        throws AFException, java.io.IOException;
    
    /**
     * M�todo que es llamado al momento de instalar el recurso en webbuilder
     * @param recobj informaci�n de base de datos de la definici�n del Recurso
     */
    public void install(RecResourceType recobj) throws AFException;

    /**
     * M�todo que es llamado al momento de desinstalar el recurso en webbuilder
     * @param recobj informaci�n de base de datos de la definici�n del Recurso
     */
    public void uninstall(RecResourceType recobj) throws AFException;
    
    /**
     * Es llamado por el ResourceMgr para indicarle al recurso cuando 
     * va ha ser retirado del servicio.
    */
    public void destroy();    
}