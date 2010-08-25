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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static java.lang.management.ManagementFactory.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.management.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.monitor.SWBGCDump;
import org.semanticwb.portal.monitor.SWBMonitorBeans;
import org.semanticwb.portal.monitor.SWBMonitorData;
import org.semanticwb.portal.monitor.SWBSummary;
import org.semanticwb.portal.monitor.SWBThreadDumper;

// TODO: Auto-generated Javadoc
/**
 * The Class Monitor.
 * 
 * @author serch
 */
public class Monitor implements InternalServlet
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Monitor.class);
    
    /** The rmbean. */
    private static RuntimeMXBean rmbean;
    
    /** The mmbean. */
    private static MemoryMXBean mmbean;
    
    /** The pools. */
    private static List<MemoryPoolMXBean> pools;
    
    /** The gcmbeans. */
    private static List<GarbageCollectorMXBean> gcmbeans;
//    private static MBeanServer mbs;
    /** The buffer. */
private Vector<SWBMonitorData> buffer;
    
    /** The timer. */
    private Timer timer;
    
    /** The max. */
    private int max = 2500;
//    private int maxgc = 50;
    
    /** The delays. */
    private int delays = 1000;
    
    /** The t. */
    private TimerTask t = null;
    
    /** The summary. */
    private SWBSummary summary = null;
    
    /** The monitorbeans. */
    private SWBMonitorBeans monitorbeans = null;
    
    /** The secret key. */
    private SecretKey secretKey = null;
    
    /** The timetaken last. */
    public static long timetakenLast = 0;
//    private Cipher cipher = null;
//    //Java 6.0
//    private ConcurrentHashMap<String, BasureroCtl> basureros;
//    private Vector<CompositeData> basureroBuff;
//    private SWBGCDump dumper;

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing InternalServlet Monitor...");
        monitorbeans = new SWBMonitorBeans();
        buffer = new Vector<SWBMonitorData>(max);
        try
        {
            SemanticProperty sp = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getSemanticProperty(SWBPlatform.getSemanticMgr().SWBAdmin + "/PrivateKey");
            //System.out.println("sp:"+sp);
            String priv = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getModelObject().getProperty(sp);
            //System.out.println("priv:"+priv);
            if (null==priv)
            {
                org.semanticwb.SWBPlatform.getSemanticMgr().createKeyPair();
                priv = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getModelObject().getProperty(sp);
            }
            if (priv != null)
            {
                priv = priv.substring(priv.indexOf("|") + 1);
                byte[] PKey = SFBase64.decode(priv);
                byte[] pKey = SFBase64.decode(SWBPlatform.getEnv("swbMonitor/PublicKey", 
                        "MIHfMIGXBgkqhkiG9w0BAwEwgYkCQQCLxCFm00uKxKmedeD9XqiJ1SZ/DoXRtdibiTIv" +
                        "Ciz2MfNzu+TnGkrgsBhTpfZN00nLopd80oPFvpBZTGIUTX2FAkBxDxzqmO0rG7TMQf4b" +
                        "q5o7lIlf0DM1qcaVvFGjCt6t/NcFcko2S//V/58sqrzcyfBQKqZr0yTyqD6J4gCL4EN/" +
                        "AgIB/wNDAAJAAwR0XE5XXl4xiTpZaF2jlLvp9YRSskMWOWPa/h3Bn+ovSpEuuMwnJ8yg" +
                        "aj5/fcFNFLj5TaIRNDqTPQgbkMUI3A=="));
                if (null != pKey)
                {
                    java.security.spec.X509EncodedKeySpec pK = new java.security.spec.X509EncodedKeySpec(pKey);
                    
                    java.security.spec.PKCS8EncodedKeySpec PK = new java.security.spec.PKCS8EncodedKeySpec(PKey);
                    KeyFactory keyFact = KeyFactory.getInstance("DiffieHellman");
                    KeyAgreement ka = KeyAgreement.getInstance("DiffieHellman");
                    PublicKey publicKey = keyFact.generatePublic(pK);
                    PrivateKey privateKey = keyFact.generatePrivate(PK);
                    ka.init(privateKey);
                    ka.doPhase(publicKey, true);
                    secretKey = new SecretKeySpec(ka.generateSecret(), 0, 16, "AES");
                    //SecretKey secretKey = ka.generateSecret("AES");
                }
            }
        //} catch (java.security.GeneralSecurityException gse)
        } catch (Exception gse)
        {
            log.error(gse);
            // assert (false);
        }
//        dumper = new SWBGCDump();
//        //Java 6.0
//        basureros = new ConcurrentHashMap<String, BasureroCtl>();
//        basureroBuff=new Vector<CompositeData>(maxgc);
//        for ( GarbageCollectorMXBean gc :dumper.getCollectors()){
//            basureros.put(gc.getName(), new BasureroCtl());
//            BasureroCtl actual =basureros.get(gc.getName());
//
//        }
        t = new TimerTask()
        {

            public void run()
            {
                long current = System.nanoTime();//System.currentTimeMillis();
                _run();
                timetakenLast = System.nanoTime() - current;//System.currentTimeMillis()-current;
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
//        mbs = sun.management.ManagementFactory.createPlatformMBeanServer();
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

    /**
     * _run.
     */
    private void _run()
    {
        if (buffer.size() == max)
        {
            buffer.remove(0);
        }
        buffer.add(new SWBMonitorData(monitorbeans));
//        //Java 6.0
//        for (com.sun.management.GarbageCollectorMXBean gc :dumper.getCollectors()){
//            BasureroCtl basurero = basureros.get(gc.getName());
//            GcInfo gcinfo = gc.getLastGcInfo();
//            if (basurero.idx<gcinfo.getId()){
//                basurero.idx=gcinfo.getId();
//                if (basureroBuff.size()==maxgc){
//                    basureroBuff.remove(0);
//                }
//                basureroBuff.add(gcinfo.toCompositeData(gcinfo.getCompositeType()));
//            }
//
//        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {

        if (null == secretKey)
        {
            response.setContentType("text/plain");
            response.getWriter().println("Not Initializad...");
            return;
        }
        try
        {
            if (null != request.getParameter("cmd"))
            {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                response.setContentType("application/octet-stream");
                CipherOutputStream out = new CipherOutputStream(response.getOutputStream(), cipher);
                ObjectOutputStream data = new ObjectOutputStream(out);
                if ("summary".equals(request.getParameter("cmd")))
                {
                    data.writeObject(summary.getSample());

                }
                if ("datapkg".equals(request.getParameter("cmd")))
                {
                    data.writeObject(buffer);

                }
                if ("deathlock".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBThreadDumper.dumpDeathLock());

                }
                if ("blocked".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBThreadDumper.dumpBLOCKEDThread());

                }
                if ("blockedst".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBThreadDumper.dumpBLOCKEDThreadWithStackTrace());

                }
                if ("threads".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBThreadDumper.dumpThread());

                }
                if ("threadsst".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBThreadDumper.dumpThreadWithStackTrace());

                }
                if ("lastGC".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBGCDump.getGc());

                }
                if ("lastGCVerbose".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBGCDump.getVerboseGc());

                }
                if ("swbmonitor".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBPortal.getMonitor().getMonitorRecords());

                }
                if ("swbavgmonitor".equals(request.getParameter("cmd")))
                {
                    data.writeObject(SWBPortal.getMonitor().getAverageMonitorRecords(5));

                }
//            if ("".equals(request.getParameter("cmd")))
//            {
//                data.writeObject("");
//                
//            }
//            if ("".equals(request.getParameter("cmd")))
//            {
//                data.writeObject("");
//                
//            }
//            if ("".equals(request.getParameter("cmd")))
//            {
//                data.writeObject("");
//                
//            }
//            if ("".equals(request.getParameter("cmd")))
//            {
//                data.writeObject("");
//
//            }
                data.flush();
//            String datos = new String(cipher.doFinal(summary.getSample().GetSumaryHTML().getBytes()));
//            System.out.println(datos);
//            datos = SFBase64.encodeBytes(datos.getBytes(), false);
//            System.out.println(datos);
//            PrintWriter out = response.getWriter();
//
//            out.println(datos);
                data.close();
                out.close();
            } else
            {
                response.setContentType("text/plain");
                response.getWriter().println("Nothing to do...");
            }
        } catch (Exception ex)
        {
            log.error(ex);
        }


    }

    /**
     * _do process.
     * 
     * @param request the request
     * @param response the response
     * @param dparams the dparams
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    public void _doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
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
            out.println(((Vector<SWBMonitorData>) is.readObject()).size());
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
//        buf = new ByteArrayOutputStream();
//        os = new ObjectOutputStream(buf);
//       os.writeObject(basureros);
//       os.close();
//       out.println(buf.toString().length());
//        is = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()));
//        try
//        {
//            Vector<CompositeData> bas = (Vector<CompositeData>)is.readObject();
//            out.println(bas.size());
//
//        } catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
    }

/*
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
 
 *
 */
    /** The INDENT. */
private static String INDENT = "    ";

    /**
     * Prints the thread info.
     * 
     * @param ti the ti
     */
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

    /**
     * Format millis.
     * 
     * @param ms the ms
     * @return the string
     */
    private static String formatMillis(long ms)
    {
        return String.format("%.4fsec", ms / (double) 1000);
    }

    /**
     * Format bytes.
     * 
     * @param bytes the bytes
     * @return the string
     */
    private static String formatBytes(long bytes)
    {
        long kb = bytes;
        if (bytes > 0)
        {
            kb = bytes / 1024;
        }
        return kb + "K";
    }

    /**
     * Prints the verbose gc.
     */
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

    /**
     * Prints the gc info.
     * 
     * @param gci the gci
     * @return true, if successful
     */
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

    /**
     * Byte array to hex string.
     * 
     * @param in the in
     * @return the string
     */
    public static String byteArrayToHexString(byte in[]) {

	    byte ch = 0x00;
	    int i = 0;

	    if (in == null || in.length <= 0)
	        return null;

	    String pseudo[] = {"0", "1", "2","3", "4", "5", "6", "7", "8","9", "A", "B", "C", "D", "E","F"};
	    StringBuffer out = new StringBuffer(in.length * 2);

	    while (i < in.length) {

	        ch = (byte) (in[i] & 0xF0); // Strip off high nibble
	        ch = (byte) (ch >>> 4);	     // shift the bits down
	        ch = (byte) (ch & 0x0F);    //	must do this is high order bit is on!

	        out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	        ch = (byte) (in[i] & 0x0F); // Strip off low nibble
	        out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	        i++;
	    }

	    String rslt = new String(out);
	    return rslt;
	}

}

class BasureroCtl implements Serializable
{

    private static final long serialVersionUID = 33233L;
    long idx = 0;
    // Vector<CompositeData> basureroBuff;
}
