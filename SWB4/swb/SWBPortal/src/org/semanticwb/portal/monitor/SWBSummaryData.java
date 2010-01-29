/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.monitor;

import java.io.Serializable;
import static org.semanticwb.portal.monitor.SWBFormatUtils.*;
/**
 *
 * @author serch
 */
public class SWBSummaryData implements Serializable {
    private static final long serialVersionUID = 4012L;
       public long upTime, startTime = -1L;
    //   public double systemLoadAverage = Double.NEGATIVE_INFINITY;
	public long processCpuTime = -1L;
	public long timeStamp;
	public int nCPUs;
        public String vmName, vmVendor, vmInstanceName, jitCompiler, osName, vmArch, vmArgs, vmClassPath, vmLibraryPath, vmBootClassPath;//, internalName;
        public int liveTh, peakTh, deamonTh =-1;
        public int  currentClass = -1;
        public long currentHeap, maxHeap, currentCommited, objectsPending, startedTh, unloadedClass, totalClass = -1L;
        public String[] gcDetails;
        public long commitedVirtualMem, totalPhysicalMem, freePhysicalMem, totalSwapMem, FreeSwapMem = -1L;
        public float instantCPU = Float.MIN_VALUE;

    public SWBSummaryData()
    {
        timeStamp = System.currentTimeMillis();
    }


        public String GetSumaryHTML(){
            StringBuilder ret = new StringBuilder();
            ret.append("<div id=\"SWBSummary\">\n");
            ret.append("<div id=\"Status\">\n");
            ret.append("<ul>\n<li>M&aacute;quina Virtual: "+vmName+"</li>\n");
            ret.append("<li>Fabricante: "+vmVendor+"</li>\n");
            ret.append("<li>Instancia: "+vmInstanceName+"</li>\n");
            //ret.append("<li>Nombre Interno: "+internalName+"</li>\n");
            ret.append("<li>Compilador: "+jitCompiler+"</li>\n");
          //  ret.append("<li>Carga promedio: "+String.format("%1$3.4f", systemLoadAverage)+"</li>\n");
            ret.append("<li>CPU Instantaneo: "+String.format("%1$3.4f", instantCPU)+"</li>\n");
            ret.append("<li>Hora de Inicio: "+formatDate(startTime)+"</li>\n");
            ret.append("<li>Tiempo de Uso: "+formatTime(upTime)+"</li>\n");
            ret.append("<li>Tiempo de Procesamiento: "+formatTime(processCpuTime/1000000)+"</li>\n</ul>\n</div>\n");
            ret.append("<div id\"SO\">");
            ret.append("<ul>\n<li>Sistema Operatvo: "+osName+"</li>\n");
            ret.append("<li>Arquitectura: "+vmArch+"</li>\n");
            ret.append("<li>Procesadores: "+nCPUs+"</li>\n");
            ret.append("<li>Memoria Virutal en Uso: "+formatLong(commitedVirtualMem/1024)+" kbytes</li>\n");
            ret.append("<li>Total Memoria F&iacute;sica: "+formatLong(totalPhysicalMem/1024)+" kbytes</li>\n");
            ret.append("<li>Memoria f&iacute;sica Libre: "+formatLong(freePhysicalMem/1024)+" kbytes</li>\n");
            ret.append("<li>Total Memoria SWAP: "+formatLong(totalSwapMem/1024)+" kbytes</li>\n");
            ret.append("<li>Memoria SWAP Libre: "+formatLong(FreeSwapMem/1024)+" kbytes</li>\n</ul>\n</div>\n");
            ret.append("<div id\"Threads\">");
            ret.append("<ul>\n<li>Threads Vivos: "+liveTh+"</li>\n");
            ret.append("<li>Pico de Threads: "+peakTh+"</li>\n");
            ret.append("<li>Deamons Threads: "+deamonTh+"</li>\n");
            ret.append("<li>Total Threads iniciadas: "+startedTh+"</li>\n");
            ret.append("<li>Classes Cargadas: "+currentClass+"</li>\n");
            ret.append("<li>Total Clases: "+totalClass+"</li>\n");
            ret.append("<li>Classes Descargadas: "+unloadedClass+"</li>\n</ul>\n</div>\n");
            ret.append("<div id\"Memory\">");
            ret.append("<ul>\n<li>Heap Actual: "+formatLong(currentHeap/1024)+" kbytes</li>\n");
            ret.append("<li>Heap M&aacute;ximo: "+formatLong(maxHeap/1024)+" kbytes</li>\n");
            ret.append("<li>Memoria asignada: "+formatLong(currentCommited/1024)+" kbytes</li>\n");
            ret.append("<li>Objetos por finalizar: "+objectsPending+"</li>\n");
            for (String curr: gcDetails){
                ret.append("<li>GC: "+curr+"</li>\n");
            }
            ret.append("</ul>\n</div>\n");
            ret.append("<div id\"VMArgs\">");
            ret.append("<ul>\n<li>Argumentos: "+vmArgs+"</li>\n");
            ret.append("<li>ClassPath: "+vmClassPath+"</li>\n");
            ret.append("<li>LibraryPath: "+vmLibraryPath+"</li>\n");
            ret.append("<li>BootClassPath: "+vmBootClassPath+"</li>\n</ul>\n</div>\n");
            ret.append("</div>");
            return ret.toString();
        }
}
