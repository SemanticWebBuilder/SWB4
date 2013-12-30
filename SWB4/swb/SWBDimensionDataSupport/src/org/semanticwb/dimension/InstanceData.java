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
package org.semanticwb.dimension;

/**
 * POJO representing an Instance
 *
 * @author serch
 */
public final class InstanceData implements Comparable<InstanceData> {

    private String serverId;
    private String serverName;
    private String realName;
    private String realId;
    private String ip;
    private long startupTime;
    private String deployed;
    private String started;

    InstanceData(String name, String id, String deployed, String started) {
        this.setServerName(name);
        this.setServerId(id);
        this.setStarted(started);
        this.setDeployed(deployed);
    }
     // private String deployTime; 
    /**
     * @return the serverId
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * @param serverId the serverId to set
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return the realId
     */
    public String getRealId() {
        return realId;
    }

    /**
     * @param realId the realId to set
     */
    public void setRealId(String realId) {
        this.realId = realId;
    }

    /**
     * @return the id
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param id the id to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * get Startup Timestamp
     *
     * @return startup timestamp
     */
    public long getStartupTime() {
        return startupTime;
    }

    /**
     * Set Startup timestamp
     *
     * @param startupTime startup timestamp
     */
    public void setStartupTime(long startupTime) {
        this.startupTime = startupTime;
    }

//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InstanceData other = (InstanceData) obj;
        if ((this.realId == null) ? (other.realId != null) : !this.realId.equals(other.realId)) {
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
            return this.getRealId().compareTo(o.getRealId());
        }
    }

    /**
     * @return the deployed
     */
    public String getDeployed() {
        return deployed;
    }

    /**
     * @param deployed the deployed to set
     */
    public void setDeployed(String deployed) {
        this.deployed = deployed;
    }

    /**
     * @return the started
     */
    public String getStarted() {
        return started;
    }

    /**
     * @param started the started to set
     */
    public void setStarted(String started) {
        this.started = started;
    }
}
