/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.monitor;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *
 * @author serch
 */
public class SWBThreadDumper
{

    private static ThreadMXBean thbean = java.lang.management.ManagementFactory.getThreadMXBean();

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

    public static String dumpThread()
    {

        StringBuilder sb = new StringBuilder("ThreadDump: " + "\n");
        long[] tiarr = thbean.getAllThreadIds();
        for (long ct : tiarr)
        {
            sb.append(formatThreadInfo(thbean.getThreadInfo(ct)));
        }
        return sb.toString();
    }

    public static String dumpThreadWithStackTrace()
    {

        StringBuilder sb = new StringBuilder("ThreadDump: " + "\n");
        long[] tiarr = thbean.getAllThreadIds();
        for (long ct : tiarr)
        {
            sb.append(formatThreadInfo(thbean.getThreadInfo(ct, Integer.MAX_VALUE)));
        }
        return sb.toString();
    }
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
