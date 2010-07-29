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

package org.semanticwb.process.kpi;

import java.util.ArrayList;
import org.semanticwb.process.utils.Restriction;

/**
 *
 * @author Sergio Téllez
 */
public class ProcessCaseCount {

    CaseCountSys casesys=null;
    String processURI = null;

    public ProcessCaseCount(String processURI) {
        this.processURI = processURI;
        casesys = new CaseCountSys();
        casesys.addRestriction(new Restriction(CaseCountSys.PROCESS,processURI,null));
    }

    public int totalProcessInstance() {
        return casesys.totalProcessInstance();
    }

    public ArrayList listProcessInstance() {
        return casesys.listProcessInstance();
    }

    public String getprocessURI() {
        return this.processURI;
    }

    public void addRestriction(Restriction restriction) {
        casesys.addRestriction(restriction);
    }

    public void addRestrictions(ArrayList restrictions) {
        casesys.addRestrictions(restrictions);
    }

    public void removeRestriction(Restriction restriction) {
        casesys.removeRestriction(restriction);
    }

    public void clear() {
        casesys.clear();
    }
}