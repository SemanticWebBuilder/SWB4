package org.semanticwb.process.model;

import java.util.Date;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ProcessPeriod extends org.semanticwb.process.model.base.ProcessPeriodBase 
{
    /** The log. */
    private static Logger log    = SWBUtils.getLogger(ProcessPeriod.class);

    /** The m_dom. */
    private Document      m_dom  = null;

    /** The m_node. */
    private NodeList      m_node = null;

    public ProcessPeriod(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the dom.
     *
     * @return the dom
     */
    public Document getDom() {
        return getSemanticObject().getDomProperty(swb_xml);
    }

    /**
     * Gets the node list.
     *
     * @return the node list
     */
    private NodeList getNodeList() {
        Document aux = getDom();

        if (aux != m_dom) {
            m_dom = aux;

            NodeList nl = aux.getElementsByTagName("interval");
            int n = nl.getLength();

            if (n > 0) {
                m_node = nl;
            } else {
                m_node = null;
            }
        }

        // System.out.println("getFilterNode:"+getURI()+" "+m_filternode);
        return m_node;
    }

    /**
     * Checks if is on schedule.
     *
     * @return true, if is on schedule
     */
    public boolean isOnSchedule() {
        boolean  ret   = false;
        Date     today = new Date();
        NodeList nl    = getNodeList();

        if (nl != null)
        {
            for (int x = 0; x < nl.getLength(); x++)
            {
                Node interval = nl.item(x);
                try {
                    ret = eval(today, interval);
                    if (ret) {
                        ret = true;
                        break;
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
        return ret;
    }

    /**
     * Eval.
     *
     * @param today the today
     * @param interval the interval
     * @return true, if successful
     * @throws Exception the exception
     */
    private boolean eval(Date today, Node interval) throws Exception {
        boolean ret = true;
        Date inidate = today;
        Date exdate = null;
        Date cdate=new Date(today.getYear(),today.getMonth(),today.getDate(),today.getHours(),today.getMinutes());

        if (interval != null) {
            NodeList nl = interval.getChildNodes();

            for (int x = 0; x < nl.getLength(); x++) {
                if (nl.item(x) != null) {
                    String name = nl.item(x).getNodeName();

                    // System.out.println("node:"+name);
                    if (name.equals("inidate")) {
                        inidate = new Date(nl.item(x).getFirstChild().getNodeValue());

                        System.out.println("inidate:"+inidate+" "+today);
                        if (inidate.after(today)) {
                            ret = false;
                            break;
                        }
                    } else if (name.equals("enddate")) {
                        Date enddate = new Date(nl.item(x).getFirstChild().getNodeValue());

                        enddate.setHours(23);
                        enddate.setMinutes(59);
                        enddate.setSeconds(59);

                        System.out.println("enddate:"+enddate+" "+today);
                        if (enddate.before(today)) {
                            ret = false;

                            break;
                        }
                    } else if (name.equals("exechour")) {
                        String time = nl.item(x).getFirstChild().getNodeValue();
                        StringTokenizer st   = new StringTokenizer(time, ":");
                        int h    = 0,
                            m    = 0,
                            s    = 0;

                        try {
                            h = Integer.parseInt(st.nextToken());

                            if (st.hasMoreTokens()) {
                                m = Integer.parseInt(st.nextToken());
                            }

                            if (st.hasMoreTokens()) {
                                s = Integer.parseInt(st.nextToken());
                            }
                        } catch (Exception noe) {
                            // No error
                        }

                        exdate = new Date(today.getYear(),today.getMonth(),today.getDate());
                        exdate.setHours(h);
                        exdate.setMinutes(m);
                        exdate.setSeconds(s);

                        System.out.println("exdate:"+exdate+" "+cdate);
                        if (exdate.compareTo(cdate)!=0){
                            ret = false;
                            break;
                        }
                    } else if (name.equals("inter")) {
                        String time = nl.item(x).getFirstChild().getNodeValue();
                        long inter=Long.parseLong(time)*1000*60;
                        long act=cdate.getTime()-exdate.getTime();

                        if(act%inter!=0){
                            ret = false;
                            break;
                        }
                    } else if (name.equals("endhour")) {
                        String time = nl.item(x).getFirstChild().getNodeValue();
                        StringTokenizer st = new StringTokenizer(time, ":");
                        int h    = 0,
                            m    = 0,
                            s    = 0;
                        try {
                            h = Integer.parseInt(st.nextToken());

                            if (st.hasMoreTokens()) {
                                m = Integer.parseInt(st.nextToken());
                            }

                            if (st.hasMoreTokens()) {
                                s = Integer.parseInt(st.nextToken());
                            }
                        } catch (Exception noe) {
                            // No error
                        }

                        Date enddate = new Date(today.getTime());

                        enddate.setHours(h);
                        enddate.setMinutes(m);
                        enddate.setSeconds(s);

                        if (enddate.before(cdate)){
                            ret = false;
                            break;
                        }
                    } else if (name.equals("iterations") && !evalIteration(inidate, today, nl.item(x)))
                    {
                        ret = false;
                        break;
                    }
                }
            }
        }
        // System.out.println("ret:"+ret);
        return ret;
    }

    /**
     * Eval iteration.
     *
     * @param inidate the inidate
     * @param today the today
     * @param iteration the iteration
     * @return true, if successful
     * @throws Exception the exception
     * @return
     */
    private boolean evalIteration(Date inidate, Date today, Node iteration) throws Exception {
        boolean ret = true;

        try {
            NodeList nl = iteration.getChildNodes();

            for (int x = 0; x < nl.getLength(); x++) {
                String name = nl.item(x).getNodeName();

                if (name.equals("weekly") && !evalElement(inidate, today, nl.item(x))) {
                    ret = false;
                    break;
                } else if (name.equals("monthly") && !evalElement(inidate, today, nl.item(x))) {
                    ret = false;
                    break;
                } else if (name.equals("yearly") && !evalElement(inidate, today, nl.item(x))) {
                    ret = false;
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("error Calendar Iteration 1", e);
        }

        return ret;
    }

    /**
     * Eval element.
     *
     * @param inidate the inidate
     * @param today the today
     * @param element the element
     * @return true, if successful
     * @throws Exception the exception
     * @return
     */
    private boolean evalElement(Date inidate, Date today, Node element) throws Exception {
        boolean ret = true;

        try {
            NodeList nl = element.getChildNodes();

            for (int x = 0; x < nl.getLength(); x++) {
                String name  = nl.item(x).getNodeName();
                String value = nl.item(x).getFirstChild().getNodeValue();
                int    val   = Integer.parseInt(value);

                if (name.equals("wdays")) {
                    if (((1 << today.getDay()) & val) == 0) {
                        ret = false;

                        break;
                    }
                } else if (name.equals("day")) {
                    if (today.getDate() != val) {
                        ret = false;

                        break;
                    }
                } else if (name.equals("months")) {
                    int inim = inidate.getMonth();                     // mes inicial
                    int actm = today.getMonth();                       // mes actual
                    int dify = today.getYear() - inidate.getYear();    // diferencia de años
                    int difm = actm + dify * 12 - inim;                // diferencia de meses realses contando años
                    int modm = difm % val;                             // modulo de los mese con respecto al valor

                    // System.out.println("difm="+difm);
                    // System.out.println("modm="+modm);
                    if (modm != 0) {
                        ret = false;

                        break;
                    }
                } else if (name.equals("week")) {
                    int week = (int) ((today.getDate() + inidate.getDay() - 1) / 7) + 1;

                    if ((val != week) && (val == 5) && (week != 4)) {
                        ret = false;
                        break;
                    }
                } else if (name.equals("month")) {
                    if ((today.getMonth() + 1) != val) {
                        ret = false;

                        break;
                    }
                } else if (name.equals("years")) {
                    int dify = today.getYear() - inidate.getYear();    // diferencia de años
                    int mody = dify % val;                             // modulo de los años con respecto al valor

                    if (mody != 0) {
                        ret = false;

                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error Calendar Iteration 2", e);
        }
        return ret;
    }

}
