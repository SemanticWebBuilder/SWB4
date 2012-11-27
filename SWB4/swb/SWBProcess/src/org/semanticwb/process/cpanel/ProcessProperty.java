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
package org.semanticwb.process.cpanel;

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author haydee.vertti
 */
public class ProcessProperty
{
    private org.semanticwb.platform.SemanticProperty property;
    private String URI;
    private String processURI;
    private String artifactName;
    private boolean displayOnTask;
    private boolean [] applyOnTask;
    private int orderOnTask;
    private static Logger log = SWBUtils.getLogger(ProcessProperty.class);

        public ProcessProperty(){
        }

        public ProcessProperty(org.semanticwb.platform.SemanticProperty
                property)
        {
            this.property = property;
            this.URI = "";
            this.artifactName = "";
            this.displayOnTask = false;
            boolean [] bApplyOn = {false,false,false};
            this.applyOnTask = bApplyOn;
            this.orderOnTask = 0;
        }

        public ProcessProperty(org.semanticwb.platform.SemanticProperty
                property, String URI, String artifactName)
        {
            this.URI = URI;
            this.property = property;
            this.artifactName = artifactName;
            this.displayOnTask = false;
            boolean [] bApplyOn = {false,false,false};
            this.applyOnTask = bApplyOn;
            //this.applyOnTask = 0;
            this.orderOnTask = 0;
        }

        public ProcessProperty(org.semanticwb.platform.SemanticProperty
                property, String URI, String artifactName,
                boolean displayOnTask, int applyOnTask, int orderOnTask)
        {
            this.URI = URI;
            this.property = property;
            this.artifactName = artifactName;
            this.displayOnTask = displayOnTask;
            boolean [] bApplyOn = {false,false,false};
            this.applyOnTask = bApplyOn;
            //this.applyOnTask = applyOnTask;
            this.orderOnTask = orderOnTask;
        }

        public org.semanticwb.platform.SemanticProperty getProperty()
        {
            return this.property;
        }

        public String getArtifactName(){
            return this.artifactName;
        }

        public String getURI(){
            return this.URI;
        }

        public String getProcessURI(){
            return this.processURI;
        }

        public boolean getDisplayOnTask(){
            return this.displayOnTask;
        }

        /*
        public int getApplyOnTask(){
            return this.applyOnTask;
        }
         */
        public boolean[]getApplyOnTask(){
            return this.applyOnTask;
        }

        public boolean isAppliedOnTaskLink()
        {
            return this.applyOnTask[0];
        }

        public boolean isAppliedOnTaskLegend()
        {
            return this.applyOnTask[1];
        }

        public boolean isAppliedOnTaskColumn()
        {
            return this.applyOnTask[2];
        }

        public int getOrderOnTask(){
            return this.orderOnTask;
        }

        public void setProperty(org.semanticwb.platform.SemanticProperty
                property)
        {
            this.property = property;
        }

        public void setDisplayOnTask(boolean displayOnTask){
            this.displayOnTask = displayOnTask;
        }

        public void setArtifactName(String artifactName){
            this.artifactName = artifactName;
        }

        public void setURI(String URI){
            this.URI = URI;
        }

        public void setProcessURI(String URI){
            this.processURI = URI;
        }

        /*
        public void setApplyOnTask(int applyOnTask){
            this.applyOnTask = applyOnTask;
        }
        */
        public void setApplyOnTask(boolean [] applyOnTask){
            this.applyOnTask = applyOnTask;
        }

        public void setAppliedOnTaskLink(boolean b)
        {
            this.applyOnTask[0] = b;
        }

        public void setAppliedOnTaskLegend(boolean b)
        {
            this.applyOnTask[1] = b;
        }

        public void setAppliedOnTaskColumn(boolean b)
        {
            this.applyOnTask[2] = b;
        }

        public void setOrderOnTask(int orderOnTask){
            this.orderOnTask = orderOnTask;
        }

        /**
        * Ordena un vector de acuerdo al valor de la propiedad  orderOnTask
        * proporcionado por el usuario
        *
        * @param            Vector de objetos tipo ProcessProperty
        * @see
        */
        public static void sortProcessProperty(Vector v){
            try {
                ProcessPropertyOrderComparator comparator =
                        new ProcessPropertyOrderComparator();
                Collections.sort(v,comparator);
            } catch(Exception e){
                //System.out.println("Error en ProcessProperty.sortProcessProperty:" + e.getMessage());
                log.error("Error en ProcessProperty.sortProcessProperty", e);
            }
        }

        /**
        * Implementación de Comparator para objetos de tipo ProcessProperty
        */
        static class ProcessPropertyOrderComparator implements Comparator{
            public int compare(Object obj1, Object obj2)
            {
                int result = 0;
                ProcessProperty pProp1 = (ProcessProperty)obj1;
                ProcessProperty pProp2 = (ProcessProperty)obj2;
                Integer order1 = pProp1.getOrderOnTask();
                Integer order2 = pProp2.getOrderOnTask();
                String strName1 = pProp1.getProperty().getDisplayName();
                String strName2 = pProp2.getProperty().getDisplayName();
                result = order1.compareTo(order2);
                if(result == 0){
                    result = strName1.compareTo(strName2);
                }
                return result;
            }
        }

}
