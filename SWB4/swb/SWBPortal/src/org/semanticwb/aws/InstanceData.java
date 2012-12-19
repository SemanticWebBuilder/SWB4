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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.aws;

/**
 * POJO representing an Instance
 * @author serch
 */
public final class InstanceData implements Comparable<InstanceData> {

    private String id;
    private String type;
    private String ip;
    private String Status;
    private long startupTime;

    /**
     * get Instance ID
     * @return Instance ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set Instance ID
     * @param id instance ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get Private IP
     * @return Private IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set private IP
     * @param ip private ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * get status of the instance
     * @return status of the instance
     */
    public String getStatus() {
        return Status;
    }

    /**
     * set status of the instance
     * @param Status status of the instance
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * get Instance swb type
     * @return instance swb type
     */
    public String getType() {
        return type;
    }

    /**
     * set instance swb type
     * @param type instance swb type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get Startup Timestamp
     * @return startup timestamp
     */
    public long getStartupTime() {
        return startupTime;
    }

    /**
     * Set Startup timestamp
     * @param startupTime startup timestamp
     */
    public void setStartupTime(long startupTime) {
        this.startupTime = startupTime;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InstanceData other = (InstanceData) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(InstanceData o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (this.equals(o)) {
            return EQUAL;
        }
        if (this.getStartupTime() < o.getStartupTime()) {
            return BEFORE;
        }
        if (this.getStartupTime() > o.getStartupTime()) {
            return AFTER;
        } else {
            return this.getId().compareTo(o.getId());
        }
    }
}
