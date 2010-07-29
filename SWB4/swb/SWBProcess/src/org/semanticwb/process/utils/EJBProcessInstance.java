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

package org.semanticwb.process.utils;

import org.semanticwb.process.model.ProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class EJBProcessInstance {

    private String instance;
    private String process;
    private String user;
    private String started;
    private String ended;
    private String status;

    public EJBProcessInstance() {
        
    }

    public EJBProcessInstance(ProcessInstance pinst) {
        instance = pinst.getId();
        process = pinst.getProcessType().getTitle();
        //user = pinst.getModifiedBy().getFullName();
        user = Ajax.getModifiedBy(pinst);
        started = pinst.getCreated().toString();
        ended = Ajax.notNull(pinst.getEnded());
        status = statusLabel(pinst.getStatus());
    }

    public String getInstance() {
        return instance;
    }

    public String getProcess() {
        return process;
    }

    public String getUser() {
        return user;
    }

    public String getStarted() {
        return started;
    }

    public String getEnded() {
        return ended;
    }

    public String getStatus() {
        return status;
    }

    public void setInstance(String instance) {
        this.instance=instance;
    }

    public void setProcess(String process) {
        this.process=process;
    }

    public void setUser(String user) {
        this.user=user;
    }

    public void setStarted(String started) {
        this.started=started;
    }

    public void setEnded(String ended) {
        this.ended=ended;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    private String statusLabel(int status) {
        String statusLabel = null;
        switch(status) {
            case 0: statusLabel = "Iniciada"; break;
            case 1: statusLabel = "En Proceso"; break;
            case 2: statusLabel = "Detenida"; break;
            case 3: statusLabel = "Cancelada"; break;
            case 4: statusLabel = "Concluida"; break;
            case 5: statusLabel = "Abierta"; break;
            default: statusLabel = "";
        }
        return statusLabel;
    }
}
