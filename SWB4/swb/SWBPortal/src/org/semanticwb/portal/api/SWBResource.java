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
package org.semanticwb.portal.api;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;


// TODO: Auto-generated Javadoc
/**
 * La interfaz WBResource es la que define los m�todos que una clase debe implementar
 * para poder ser considerado como un recurso que pueda ser administrado por WebBuilder.
 * 
 * @author Javier Solis Gonzalez
 * @version 2.0
 */
public interface SWBResource
{
    
    /**
     * Asigna el Objeto Resource con la informaci�n base del recurso.
     * (es llamando cada que el recurso es modificado desde la administraci�n de WB)
     * 
     * @param base Objeto Resource
     * @throws SWBResourceException the sWB resource exception
     */
    public void setResourceBase(Resource base) throws SWBResourceException;

    /**
     * Es llamado cuando es cargado el recurso en memoria
     * (solo es llamado una vez).
     * 
     * @throws SWBResourceException the sWB resource exception
     */
    public void init() throws SWBResourceException;
    
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
     * @param request el servlet request
     * @param actionResponse the action response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @exception  SWBResourceException
     * Si el recurso tiene alg�n problema con el procesamiento de la acci�n
     * @exception  IOException
     * Si hay algun problema de I/O con el procesamiento de streams.
     */
    public void processAction(HttpServletRequest request, SWBActionResponse actionResponse)
        throws SWBResourceException, java.io.IOException;
    
    /**
     * Este m�todo permite al recurso generar el contenido de la respuesta
     * basada en el estado actual.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @exception  SWBResourceException
     * Si el recurso tiene alg�n problema con la generaci�n de la respuesta.
     * @exception  IOException
     * Si hay alg�n problema de I/O con el procesamiento de streams.
     */
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
        throws SWBResourceException, java.io.IOException;
    
    /**
     * M�todo que es llamado al momento de instalar el recurso en webbuilder.
     * 
     * @param resourceType the resource type
     * @throws SWBResourceException the sWB resource exception
     */
    public void install(ResourceType resourceType) throws SWBResourceException;

    /**
     * M�todo que es llamado al momento de desinstalar el recurso en webbuilder.
     * 
     * @param resourceType the resource type
     * @throws SWBResourceException the sWB resource exception
     */
    public void uninstall(ResourceType resourceType) throws SWBResourceException;
    
    /**
     * Es llamado por el ResourceMgr para indicarle al recurso cuando 
     * va ha ser retirado del servicio.
    */
    public void destroy();    
}