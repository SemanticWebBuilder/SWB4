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
package org.semanticwb.portal.monitor;

import java.io.Serializable;
import static org.semanticwb.portal.monitor.SWBFormatUtils.*;
// TODO: Auto-generated Javadoc

/**
 * The Class SWBSummaryData.
 * 
 * @author serch
 */
public class SWBSummaryData implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4012L;
       
       /** The start time. */
       public long upTime, startTime = -1L;
//       public double systemLoadAverage;
	/** The process cpu time. */
public long processCpuTime = -1L;
	
	/** The time stamp. */
	public long timeStamp;
	
	/** The n cp us. */
	public int nCPUs;
        
        /** The vm boot class path. */
        public String vmName, vmVendor, vmInstanceName, jitCompiler, osName, vmArch, vmArgs, vmClassPath, vmLibraryPath, vmBootClassPath;//, internalName;
        
        /** The deamon th. */
        public int liveTh, peakTh, deamonTh =-1;
        
        /** The current class. */
        public int  currentClass = -1;
        
        /** The total class. */
        public long currentHeap, maxHeap, currentCommited, objectsPending, startedTh, unloadedClass, totalClass = -1L;
        
        /** The gc details. */
        public String[] gcDetails;
        
        /** The free swap mem. */
        public long commitedVirtualMem, totalPhysicalMem, freePhysicalMem, totalSwapMem, freeSwapMem = -1L;
        
        /** The instant cpu. */
        public float instantCPU = Float.MIN_VALUE;

    /**
     * Instantiates a new sWB summary data.
     */
    public SWBSummaryData()
    {
        timeStamp = System.currentTimeMillis();
    }


        /**
         * Gets the sumary html.
         * 
         * @return the string
         */
        public String GetSumaryHTML(){
            StringBuilder ret = new StringBuilder();
            ret.append("<div id=\"SWBSummary\">\n");
            ret.append("<div id=\"Status\">\n");
            ret.append("<ul>\n<li>M&aacute;quina Virtual: "+vmName+"</li>\n");
            ret.append("<li>Fabricante: "+vmVendor+"</li>\n");
            ret.append("<li>Instancia: "+vmInstanceName+"</li>\n");
            //ret.append("<li>Nombre Interno: "+internalName+"</li>\n");
            ret.append("<li>Compilador: "+jitCompiler+"</li>\n");
//            if (systemLoadAverage>0d)
//            ret.append("<li>Carga promedio: "+String.format("%1$3.4f", systemLoadAverage)+"</li>\n");
            ret.append("<li>CPU Instantaneo: "+String.format("%1$3.4f", instantCPU)+"</li>\n");
            ret.append("<li>Hora de Inicio: "+formatDate(startTime)+"</li>\n");
            ret.append("<li>Tiempo de Uso: "+formatTime(upTime)+"</li>\n");
            ret.append("<li>Tiempo de Procesamiento: "+formatTime(processCpuTime/1000000)+"</li>\n</ul>\n</div>\n");
            ret.append("<div id=\"SO\">");
            ret.append("<ul>\n<li>Sistema Operatvo: "+osName+"</li>\n");
            ret.append("<li>Arquitectura: "+vmArch+"</li>\n");
            ret.append("<li>Procesadores: "+nCPUs+"</li>\n");
            ret.append("<li>Memoria Virutal Accesible: "+formatLong(commitedVirtualMem/1024)+" kbytes</li>\n");
            ret.append("<li>Total Memoria F&iacute;sica: "+formatLong(totalPhysicalMem/1024)+" kbytes</li>\n");
            ret.append("<li>Memoria f&iacute;sica Libre: "+formatLong(freePhysicalMem/1024)+" kbytes</li>\n");
            ret.append("<li>Total Memoria SWAP: "+formatLong(totalSwapMem/1024)+" kbytes</li>\n");
            ret.append("<li>Memoria SWAP Libre: "+formatLong(freeSwapMem/1024)+" kbytes</li>\n</ul>\n</div>\n");
            ret.append("<div id=\"Threads\">");
            ret.append("<ul>\n<li>Threads Vivos: "+liveTh+"</li>\n");
            ret.append("<li>Pico de Threads: "+peakTh+"</li>\n");
            ret.append("<li>Deamons Threads: "+deamonTh+"</li>\n");
            ret.append("<li>Total Threads iniciadas: "+startedTh+"</li>\n");
            ret.append("<li>Classes Cargadas: "+currentClass+"</li>\n");
            ret.append("<li>Total Clases: "+totalClass+"</li>\n");
            ret.append("<li>Classes Descargadas: "+unloadedClass+"</li>\n</ul>\n</div>\n");
            ret.append("<div id=\"Memory\">");
            ret.append("<ul>\n<li>Memoria Heap en uso: "+formatLong(currentHeap/1024)+" kbytes</li>\n");
            ret.append("<li>Heap M&aacute;ximo: "+formatLong(maxHeap/1024)+" kbytes</li>\n");
            ret.append("<li>Tama&ntilde;o asignado de Heap: "+formatLong(currentCommited/1024)+" kbytes</li>\n");
            ret.append("<li>Objetos por finalizar: "+objectsPending+"</li>\n");
            for (String curr: gcDetails){
                ret.append("<li>GC: "+curr+"</li>\n");
            }
            ret.append("</ul>\n</div>\n");
            ret.append("<div id=\"VMArgs\">");
            ret.append("<ul>\n<li>Argumentos: "+vmArgs+"</li>\n");
            ret.append("<li>ClassPath: "+vmClassPath+"</li>\n");
            ret.append("<li>LibraryPath: "+vmLibraryPath+"</li>\n");
            ret.append("<li>BootClassPath: "+vmBootClassPath+"</li>\n</ul>\n</div>\n");
            ret.append("</div>");
            return ret.toString();
        }
}
