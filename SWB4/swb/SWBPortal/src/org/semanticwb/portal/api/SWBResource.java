
/*
 * SWBResource2.java
 *
 * Created on 12 de mayo de 2004, 11:40 AM
 */

package org.semanticwb.portal.api;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletType;


/** La interfaz WBResource es la que define los m�todos que una clase debe implementar
 * para poder ser considerado como un recurso que pueda ser administrado por WebBuilder
 *
 * @author Javier Solis Gonzalez
 * @version 2.0
 */
public interface SWBResource
{
    /** Asigna el Objeto Portlet con la informaci�n base del recurso.
     * (es llamando cada que el recurso es modificado desde la administraci�n de WB)
     * @param base Objeto Portlet
     */
    public void setResourceBase(Portlet base) throws SWBResourceException;

    /** Es llamado cuando es cargado el recurso en memoria
     * (solo es llamado una vez)
     *
     */
    public void init() throws SWBResourceException;
    
    /** Regresa el Objeto Portlet con la informaci�n base del recurso.
     * @return  Objeto Portlet
     */
    public Portlet getResourceBase();
    
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
     * @exception  SWBResourceException
     *           Si el recurso tiene alg�n problema con el procesamiento de la acci�n
     * @exception  IOException
     *           Si hay algun problema de I/O con el procesamiento de streams.
     */
    public void processAction(HttpServletRequest request, SWBActionResponse actionResponse)
        throws SWBResourceException, java.io.IOException;
    
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
     * @exception  SWBResourceException
     *             Si el recurso tiene alg�n problema con la generaci�n de la respuesta.
     * @exception  IOException
     *             Si hay alg�n problema de I/O con el procesamiento de streams.
     */
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
        throws SWBResourceException, java.io.IOException;
    
    /**
     * M�todo que es llamado al momento de instalar el recurso en webbuilder
     * @param recobj informaci�n de base de datos de la definici�n del Recurso
     */
    public void install(PortletType portletType) throws SWBResourceException;

    /**
     * M�todo que es llamado al momento de desinstalar el recurso en webbuilder
     * @param recobj informaci�n de base de datos de la definici�n del Recurso
     */
    public void uninstall(PortletType portletType) throws SWBResourceException;
    
    /**
     * Es llamado por el ResourceMgr para indicarle al recurso cuando 
     * va ha ser retirado del servicio.
    */
    public void destroy();    
}