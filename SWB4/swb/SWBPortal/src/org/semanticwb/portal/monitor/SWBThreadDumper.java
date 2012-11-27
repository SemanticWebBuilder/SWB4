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

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBThreadDumper.
 * 
 * @author serch
 */
public class SWBThreadDumper
{

    /** The thbean. */
    private static ThreadMXBean thbean = java.lang.management.ManagementFactory.getThreadMXBean();

    /**
     * Format thread info.
     * 
     * @param ti the ti
     * @return the string
     */
    private static String formatThreadInfo(ThreadInfo ti)
    {
        StringBuilder sb = new StringBuilder("\"" + ti.getThreadName() + "\""
                + " Id=" + ti.getThreadId()
                + " in " + ti.getThreadState());
        if (ti.getLockName() != null)
        {
            sb.append(" on lock=" + ti.getLockName());
        }
        if (ti.isSuspended())
        {
            sb.append(" (suspended)");
        }
        if (ti.isInNative())
        {
            sb.append(" (running in native)");
        }
        sb.append("\n");
        if (ti.getLockOwnerName() != null)
        {
            sb.append("     owned by " + ti.getLockOwnerName()
                    + " Id=" + ti.getLockOwnerId() + "\n");
        }
        for (StackTraceElement ste : ti.getStackTrace())
        {
            sb.append("    at " + ste.toString() + "\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Dump death lock.
     * 
     * @return the string
     */
    public static String dumpDeathLock()
    {
        String ret = "No DeathLock found!\n";
        if (thbean.isThreadContentionMonitoringSupported())
        {
            thbean.setThreadContentionMonitoringEnabled(true);
            long[] tiarr = thbean.findMonitorDeadlockedThreads();
            if (null != tiarr)
            {
                StringBuilder sb = new StringBuilder("DeathLock: " + "\n");

                for (long ct : tiarr)
                {
                    sb.append(formatThreadInfo(thbean.getThreadInfo(ct, Integer.MAX_VALUE)));
                }
                ret = sb.toString();
            }
        }
        return ret;
    }

    /**
     * Dump thread.
     * 
     * @return the string
     */
    public static String dumpThread()
    {

        StringBuilder sb = new StringBuilder("ThreadDump: " + "\n");
        long[] tiarr = thbean.getAllThreadIds();
        for (long ct : tiarr)
        {
            sb.append("ThreadCpuTime: "+SWBFormatUtils.formatTime(thbean.getThreadCpuTime(ct)/1000000)+"\n");
            sb.append(formatThreadInfo(thbean.getThreadInfo(ct)));
        }
        return sb.toString();
    }

    /**
     * Dump thread with stack trace.
     * 
     * @return the string
     */
    public static String dumpThreadWithStackTrace()
    {

        StringBuilder sb = new StringBuilder("ThreadDump: " + "\n");
        long[] tiarr = thbean.getAllThreadIds();
        for (long ct : tiarr)
        {
            sb.append("ThreadCpuTime: "+SWBFormatUtils.formatTime(thbean.getThreadCpuTime(ct)/1000000)+"\n");
            sb.append(formatThreadInfo(thbean.getThreadInfo(ct, Integer.MAX_VALUE)));
        }
        return sb.toString();
    }
    
    /**
     * Dump blocked thread.
     * 
     * @return the string
     */
    public static String dumpBLOCKEDThread()
    {

        StringBuilder sb = new StringBuilder("ThreadDump: " + "\n");
        long[] tiarr = thbean.getAllThreadIds();
        for (long ct : tiarr)
        {
            ThreadInfo ti = thbean.getThreadInfo(ct);
            if (ti.getThreadState().equals(Thread.State.BLOCKED))
            sb.append(formatThreadInfo(thbean.getThreadInfo(ct, Integer.MAX_VALUE)));
        }
        return sb.toString();
    }

    /**
     * Dump blocked thread with stack trace.
     * 
     * @return the string
     */
    public static String dumpBLOCKEDThreadWithStackTrace()
    {

        StringBuilder sb = new StringBuilder("ThreadDump: " + "\n");
        long[] tiarr = thbean.getAllThreadIds();
        for (long ct : tiarr)
        {
            ThreadInfo ti = thbean.getThreadInfo(ct);
            if (ti.getThreadState().equals(Thread.State.BLOCKED))
            sb.append(formatThreadInfo(thbean.getThreadInfo(ct, Integer.MAX_VALUE)));
        }
        return sb.toString();
    }

}
