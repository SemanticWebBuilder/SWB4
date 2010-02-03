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


package org.semanticwb.servlet.internal;

import com.sun.management.GcInfo;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static java.lang.management.ManagementFactory.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.management.*;
import java.util.*;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.monitor.SWBGCDump;
import org.semanticwb.portal.monitor.SWBMonitorBeans;
import org.semanticwb.portal.monitor.SWBMonitorData;
import org.semanticwb.portal.monitor.SWBSummary;
import org.semanticwb.portal.monitor.SWBThreadDumper;

/**
 *
 * @author serch
 */
public class Monitor implements InternalServlet
{

    private static Logger log = SWBUtils.getLogger(Monitor.class);
    private static RuntimeMXBean rmbean;
    private static MemoryMXBean mmbean;
    private static List<MemoryPoolMXBean> pools;
    private static List<GarbageCollectorMXBean> gcmbeans;
    private static MBeanServer mbs;
    private Vector<SWBMonitorData> buffer;
    private Timer timer;
    private int max = 2500;
    private int delays = 250;
    private TimerTask t = null;
    private SWBSummary summary = null;
    private SWBMonitorBeans monitorbeans = null;
//    private static MonitoredHost mh=null;
//    private static MonitoredVm mvm = null;

    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing InternalServlet Monitor...");
        monitorbeans = new SWBMonitorBeans();
        buffer = new Vector<SWBMonitorData>(max);
        t = new TimerTask()
        {

            public void run()
            {

                _run();

            }
        };
        timer = new Timer("Monitoring Facility", true);
        timer.schedule(t, delays, delays);

        log.event("Initializing Timer Monitor(" + max + "," + delays + "ms)...");
//        try
//        {
//            mh = MonitoredHost.getMonitoredHost("localhost");
//            VmIdentifier vmid = new VmIdentifier(java.lang.management.ManagementFactory.getRuntimeMXBean().getName());
//            mvm= mh.getMonitoredVm(vmid);
////            mvm.addVmListener(new SWBJvmEventListener());
////            sun.jvmstat.monitor.Monitor m = mvm.findByName("sun.gc.lastCause");
////            System.out.println("Monitor:"+m.getName());
////                //System.out.println("Monitor:"+m.);
////                System.out.println("Units:"+m.getUnits());
////                System.out.println("Value:"+m.getValue());
//
//        } catch (Exception ex)
//        {
//            log.error(ex);
//        }

        rmbean = getRuntimeMXBean();
        mmbean = getMemoryMXBean();
        pools = getMemoryPoolMXBeans();
        gcmbeans = getGarbageCollectorMXBeans();
        mbs = sun.management.ManagementFactory.createPlatformMBeanServer();
        if (null == summary)
        {
            summary = new SWBSummary();//mvm);
        }//        Set<ObjectName> names = mbs.queryNames(null, null);
//        Iterator<ObjectName> it = names.iterator();
//        System.out.println("Setting.......");
//        while (it.hasNext()){
//        try {
//
//            ObjectName on = it.next();
//           // System.out.println("....... "+on.getCanonicalName());
//            mbs.addNotificationListener(on, new SWBNotificationListener(), null, null);
//            System.out.println("....... "+on.getCanonicalKeyPropertyListString());
//        //mbs.addNotificationListener(new ObjectName("java.lang:name=ConcurrentMarkSweep,type=GarbageCollector"),new SWBNotificationListener(),null, null);
//      //  mbs.addNotificationListener(new ObjectName("java.lang:name=ParNew,type=GarbageCollector"),new SWBNotificationListener(),null, null);
//
//        } catch (Exception e) {
//       // e.printStackTrace();
//        }
//        }
//        System.out.println("Ok.......");
    }

    private void _run()
    {
        if(buffer.size()==max)
        {
            buffer.remove(0);
        }
        buffer.add(new SWBMonitorData(monitorbeans));
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();

        out.println("<html><body>Processing...");
//        try{
//            if (null!=mvm){
        if (null == summary)
        {
            summary = new SWBSummary();//mvm);
        }
        out.println(summary.getSample().GetSumaryHTML());
//        List list = mvm.findByPattern("");
//            Iterator itl = list.iterator();
//            while(itl.hasNext()){
//                sun.jvmstat.monitor.Monitor m = (sun.jvmstat.monitor.Monitor)itl.next();
//
//                System.out.println("Monitor Name:"+m.getName());
//                System.out.println("Monitor BaseName:"+m.getBaseName());
//                //System.out.println("Monitor:"+m.);
//                System.out.println("Units:"+m.getUnits());
//                System.out.println("Value:"+m.getValue());
//            }
//        }
//        } catch (Exception e){e.printStackTrace();}
        out.print("<div id=\"DeathLock\"><pre>" + SWBThreadDumper.dumpDeathLock() + "<pre></div>");
        out.print("<div id=\"ThreadDump\"><pre>" + SWBThreadDumper.dumpBLOCKEDThreadWithStackTrace() + "<pre></div>");
        out.print("<div id=\"GC\"><pre>" + SWBGCDump.getVerboseGc() + "<pre></div>");
//        doMonitor();
//        ThreadMXBean thbean = java.lang.management.ManagementFactory.getThreadMXBean();
//        if (thbean.isThreadContentionMonitoringSupported())
//        {
//            thbean.setThreadContentionMonitoringEnabled(true);
//            long[] tiarr = thbean.findMonitorDeadlockedThreads();
//            if (null != tiarr)
//            {
//                System.out.println("DeathLock!");
//                for (long ct : tiarr)
//                {
//                    printThreadInfo(thbean.getThreadInfo(ct));
//                }
//            }
//            System.out.println("ThreadDump");
//            tiarr = thbean.getAllThreadIds();
//            for (long ct : tiarr)
//            {
//                printThreadInfo(thbean.getThreadInfo(ct));
//            }
//
//        }
        out.print("</body></html>");
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
       ObjectOutputStream os = new ObjectOutputStream(buf);
       os.writeObject(buffer);
       os.close();
       out.println(buf.toString().length());
       ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()));
        try
        {
            out.println(((Vector<SWBMonitorData>)is.readObject()).size());
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } 
    }

    private void doMonitor()
    {
        printVerboseGc();
        Set<ObjectName> names = mbs.queryNames(null, null);
        Iterator<ObjectName> it = names.iterator();
        while (it.hasNext())
        {
            ObjectName on = it.next();
            System.out.println(on.getCanonicalName());
            System.out.println(on.getKeyPropertyListString());
            try
            {
                System.out.println(mbs.getObjectInstance(on).getClassName());
//                if (mbs.getObjectInstance(on).getClassName().equals("sun.management.HotSpotDiagnostic") ) {
//                    mbs.invoke(on, INDENT, os, strings)
//                }
            } catch (InstanceNotFoundException ex)
            {
            }
        }
    }
    private static String INDENT = "    ";

    private static void printThreadInfo(ThreadInfo ti)
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
        System.out.println(sb.toString());
        if (ti.getLockOwnerName() != null)
        {
            System.out.println(INDENT + " owned by " + ti.getLockOwnerName()
                    + " Id=" + ti.getLockOwnerId());
        }
        for (StackTraceElement ste : ti.getStackTrace())
        {
            System.out.println(INDENT + "at " + ste.toString());
        }
        System.out.println();
    }

    private static String formatMillis(long ms)
    {
        return String.format("%.4fsec", ms / (double) 1000);
    }

    private static String formatBytes(long bytes)
    {
        long kb = bytes;
        if (bytes > 0)
        {
            kb = bytes / 1024;
        }
        return kb + "K";
    }

    public static void printVerboseGc()
    {

//        LocalVmManager lvm = new LocalVmManager();
//        Iterator it = lvm.activeVms().iterator();
//        //sun.jvmstat.perfdata.monitor
//        while (it.hasNext()){
//            Object obj = it.next();
//            System.out.println("objCls:"+obj.getClass().getCanonicalName());
//        }

        System.out.println("Uptime: " + formatMillis(rmbean.getUptime()));

        System.out.println(mmbean.getHeapMemoryUsage());
        for (GarbageCollectorMXBean gc : gcmbeans)
        {
            System.out.print(" [" + gc.getName() + ": ");
            System.out.print("Count=" + gc.getCollectionCount());
            System.out.print(" GCTime=" + formatMillis(gc.getCollectionTime()));
            System.out.println("]");
//            System.out.println(gc.getClass().getCanonicalName());
            com.sun.management.GarbageCollectorMXBean gci = (com.sun.management.GarbageCollectorMXBean) gc;
            GcInfo info = gci.getLastGcInfo();
            if (null != info)
            {
                printGCInfo(info);
            }
            //System.out.println(info);
        }
        System.out.println();
        for (MemoryPoolMXBean p : pools)
        {
            System.out.print("  [" + p.getName() + ":");
            MemoryUsage u = p.getUsage();

            System.out.print(" Used=" + formatBytes(u.getUsed()));
            System.out.print(" Committed=" + formatBytes(u.getCommitted()));
            System.out.println("]");
        }
    }

    static boolean printGCInfo(GcInfo gci)
    {
        // initialize GC MBean

        try
        {
            //= gcMBean.getLastGcInfo();
            long id = gci.getId();
            long startTime = gci.getStartTime();
            long endTime = gci.getEndTime();
            long duration = gci.getDuration();



            if (startTime == endTime)
            {
                return false;   // no gc
            }
            System.out.println("GC ID: " + id);
            System.out.println("Start Time: " + startTime);
            System.out.println("End Time: " + endTime);
            System.out.println("Duration: " + duration);
            Map mapBefore = gci.getMemoryUsageBeforeGc();
            Map mapAfter = gci.getMemoryUsageAfterGc();


            System.out.println("Before GC Memory Usage Details....");
            Set memType = mapBefore.keySet();
            Iterator it = memType.iterator();
            while (it.hasNext())
            {
                String type = (String) it.next();
                System.out.println(type);
                MemoryUsage mu1 = (MemoryUsage) mapBefore.get(type);
                System.out.print("Initial Size: " + mu1.getInit());
                System.out.print(" Used: " + mu1.getUsed());
                System.out.print(" Max: " + mu1.getMax());
                System.out.print(" Committed: " + mu1.getCommitted());
                System.out.println(" ");
            }

            System.out.println("After GC Memory Usage Details....");
            memType = mapAfter.keySet();
            it = memType.iterator();
            while (it.hasNext())
            {
                String type = (String) it.next();
                System.out.println(type);
                MemoryUsage mu2 = (MemoryUsage) mapAfter.get(type);
                System.out.print("Initial Size: " + mu2.getInit());
                System.out.print(" Used: " + mu2.getUsed());
                System.out.print(" Max: " + mu2.getMax());
                System.out.print(" Committed: " + mu2.getCommitted());
                System.out.println(" ");
            }
        } catch (RuntimeException re)
        {
            throw re;
        } catch (Exception exp)
        {
            throw new RuntimeException(exp);
        }
        return true;
    }
}
