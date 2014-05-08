/**
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y
 * aplicaciones de integración, colaboración y conocimiento, que gracias al uso
 * de tecnología semántica puede generar contextos de información alrededor de
 * algún tema de interés o bien integrar información y aplicaciones de
 * diferentes fuentes, donde a la información se le asigna un significado, de
 * forma que pueda ser interpretada y procesada por personas y/o sistemas, es
 * una creación original del Fondo de Información y Documentación para la
 * Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 * 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de
 * su licenciamiento abierto al público (‘open source’), en virtud del cual,
 * usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y
 * puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a
 * su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA
 * ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización del
 * SemanticWebBuilder 4.0.
 * 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y
 * naturaleza, ni implícita ni explícita, siendo usted completamente responsable
 * de la utilización que le dé y asumiendo la totalidad de los riesgos que
 * puedan derivar de la misma.
 * 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC
 * pone a su disposición la siguiente dirección electrónica:
 * http://www.semanticwebbuilder.org
 *
 */
package applets.graph;

/*
 * JeiBar.java
 * Created on May 21, 2001, 1:07 PM
 *
 * WBABar.java
 * Created2 on Feb 07, 2006, 1:07 PM
 */
import java.net.*;
import java.io.*;
import java.awt.*;
import java.applet.*;
import java.lang.InterruptedException;
import java.util.*;
import java.awt.image.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Javier Solis Gonzalez
 * @version
 */
class Bar
{

    public int x = 0;
    public int y = 0;
    public int w = 0;
    public int h = 0;
    public String parent = "";
    public String name = "";
    public String value = "";
    public Polygon p = null;

    /**
     * Creates new Bar
     */
    public Bar()
    {
    }

    public boolean inSide(int ax, int ay)
    {
        //System.out.println("x:"+ax+" y:"+ay);
        if (p != null)
        {
            return p.contains(ax, ay);
        }
        if (ax >= x && ax <= (x + w) && ay >= y && ay <= (y + h))
        {
            return true;
        } else
        {
            return false;
        }
    }
}

/**
 *
 * @author Javier Solis
 * @version
 */
public class WBGraph extends java.applet.Applet implements Runnable
{

    public static final int BARS = 0;
    public static final int LINES = 1;
    public static final int PIE = 2;
    public static final int BARS_PERCENT = 3;
    public static final int AREA = 4;
    public static final int PERCENT = 5;

    private static int bufferSize = 8192;

    Thread kicker = null;
    long nextTime;

    int Width, Height;

    String cgi = null, JSESSION=null;
    Properties prop = null;

    //Image buffer;
    //Graphics pad;
    BufferedImage buffer;
    Graphics2D pad;
    private AlphaComposite composite = null;
    private Composite composite_orig = null;
    Graphics2D bpad;                        //back graphics
    BufferedImage backbuffer;               //back image

    int pixels[];
    int Maxx, Maxy;
    int Maxd, Maxc;                                  //Max data y cols

    String Title, SubTitle;

    String label[];
    double data[][];
    Color colors[];
    Color rcolors[];
    String barnames[];
    int graphtypes[];
    boolean percent = false;
    boolean allpercent = false;
    boolean brakelabels = true;

    Font f1, f2;

    Bar bar[][];
    Bar but;                                    //Boton up
    Bar gif;                                    //Boton Gif
    String GifUrl;
    boolean gifEnter = false;
    int graphtype = 0;                            //typo de grafica

    AppletContext apc;
    Frame papa;
    String target;
    URL dir = null;

    double ma = 0;                                 //area maxima
    int sx = 45;                                  //sangria x
    int sy = 25;                                  //sangria x
    double dr = 0;                                   //diretancia entre barra
    double md = 0;                                  //Get Max Value
    double mind = 0;                                //Get Min Value
    double mindif = 0;                              // mind % sd  //diferencia de sd
    double sd = 0;                                  //get scale
    double mbs = 0;                                 //max bar scale
    double minbs = 0;                                 //max bar scale
    int mps = 0;                                  //max percent scale
    double yrel = 1;                               //relacion y de cooredenada grafica y logica
    double xneg = 0;                                 //x negativa
    int ycero = 0;                                //y cero
    int ycerod = 0;                               //y para dibujar linea horizonatal
    boolean enter = false;                        //enter a bar;
    String actbarname = null;                     //actual bar name
    String actsubbarname = null;                  //actual SubBar name

    //******************** Zoom *************************************
    boolean zoom = false;                         //zoom disponible
    boolean vzoom = false;                        //vertical zoom disponible
    boolean hvzoom = false;                        //hard vertical zoom disponible
    boolean drag = false;                         //is graging
    boolean showgrid = false;                     //mostrar grid
    boolean showpoints = true;                    //muestra los puntos de la grafica de lineas
    boolean showseriesnames = true;               //muestra los nombres de las series
    boolean decimals = false;

    int drag_x1 = 0;
    int drag_x2 = 0;
    int offset = 0;

    DecimalFormat formatterInt = new DecimalFormat("0");
    DecimalFormat formatterFloat = new DecimalFormat("0");

    Timer timer = null;

    /**
     * Initializes the applet JeiBar
     */
    public void init()
    {
        //System.out.println("Enter Init.....");
        initComponents();

        sizeComponent();

        Component object;
        for (object = this; !(object instanceof Frame); object = object.getParent()) /* null body */ ;
        try
        {
            papa = (Frame) object;
        } catch (Throwable throwable)
        {
        }

        apc = getAppletContext();

        f1 = new Font("Arial", Font.PLAIN, 12);
        f2 = new Font("Arial", Font.PLAIN, 9);

        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);

        //antes del getParams y cgi
        
        
         try
        {
            JSESSION = getParameter("jsess");
            System.out.println("JSESSION: " + JSESSION);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        String sreload = getParameter("reload");
        cgi = getParameter("cgi");
        
       
        getParams();
        if (graphtype == PIE)
        {
            Grabber();
        }
        render();

        //*********** reload ********************************
        if (sreload != null && sreload.trim().length() > 0)
        {
            try
            {
                int reload = Integer.parseInt(sreload);

                TimerTask t = new TimerTask()
                {
                    public void run()
                    {
                        reload();
                    }
                };
                timer = new Timer();
                timer.schedule(t, reload * 1000, reload * 1000);
                //System.out.println("timer:"+reload);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void reload()
    {
        System.out.println("reload");
        if (!drag && drag_x1 == 0 && drag_x2 == 0)
        {
            getParams();
            if (graphtype == PIE)
            {
                Grabber();
            }
            pad.setColor(Color.white);
            pad.fillRect(0, 0, size().width, Maxy);
            render();
            repaint();
        }
    }

    public Graphics2D createGraphics2D(BufferedImage buf)
    {
        Graphics2D g2 = null;
        //buf = (BufferedImage) createImage(w, h);
        g2 = buf.createGraphics();
        //g2.setBackground(getBackground());
        //g2.clearRect(0, 0, w, h);

        //normStroke=new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        //lineStroke=new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        //dashStroke=new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new double[]{3.1f}, 0.0f);
        //g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        //g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new double[]{0,6,0,6}, 0));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        //g2.setComposite(AlphaComposite.SrcOver);
        //g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return g2;
    }

    public void setZoomData(int x1, int x2)
    {
        if (x1 == 0 && x2 == 0)
        {
            getParams(0, -1);
        } else
        {
            if (x1 > x2)
            {
                int aux = x1;
                x1 = x2;
                x2 = aux;
            }
            if (x1 < sx)
            {
                x1 = sx;
            }
            if (x2 > (int) (Maxd * dr + sx - 1))
            {
                x2 = (int) (Maxd * dr + sx - 1);
            }

            //System.out.println("c1:"+((x1-sx)/dr));
            //System.out.println("c2:"+((x2-sx)/dr));
            int c1 = (int) ((x1 - sx) / dr) + offset;
            int c2 = (int) ((x2 - sx) / dr) + offset;;
            getParams(c1, c2);
            offset = c1;
        }
//        Maxd=Maxd=c2-c1+1;
//        if(c1>0)
//        {
//            for(int x=0;x<Maxd;x++)
//            {
//                label[c1+x-1]=label[c1+x];
//                for(int y=0;y<Maxc;y++)
//                {
//                    data[c1+x-1][y]=data[c1+x][y];
//                    bar[c1+x-1][y]=bar[c1+x][y];
//                }
//            }
//        }
    }

    public void getParams()
    {
        if (cgi != null)
        {
            try
            {
                System.out.print("Entrando a getPararams");
                prop = new Properties();
                URL urldownload = new URL(getDocumentBase(), cgi);
                URLConnection con = urldownload.openConnection();
                con.setUseCaches(false);
                if (JSESSION != null)
                {
                    con.setRequestProperty("Cookie", "JSESSIONID=" + JSESSION);
                }
                con.setDoInput(true);
                StringBuilder sb = new StringBuilder();
                InputStream in = con.getInputStream();
                byte[] bcont = new byte[8192];
                int ret = in.read(bcont);
                while (ret != -1)
                {
                    sb.append(new String(bcont, 0, ret));
                    ret = in.read(bcont);
                }
                in.close();
                System.out.println(sb.toString());
                StringReader st = new StringReader(sb.toString());
                prop.load(st);
                //prop.load(new URL(getDocumentBase(), cgi).openStream());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        setZoomData(drag_x1, drag_x2);
    }

    public String getParameter(String name)
    {
        if (cgi == null)
        {
            return super.getParameter(name);
        }
        return prop.getProperty(name, null);
    }

    private int getGraphType(String type)
    {
        int _graphtype = graphtype;
        if (type != null)
        {
            if (type.equalsIgnoreCase("Bars"))
            {
                _graphtype = BARS;
            }
            if (type.equalsIgnoreCase("Lines"))
            {
                _graphtype = LINES;
            }
            if (type.equalsIgnoreCase("Pie"))
            {
                _graphtype = PIE;
            }
            if (type.equalsIgnoreCase("BarsPercent"))
            {
                _graphtype = BARS_PERCENT;
            }
            if (type.equalsIgnoreCase("Area"))
            {
                _graphtype = AREA;
            }
        }
        return _graphtype;
    }

    public void getParams(int x1, int x2)
    {
        //System.out.println("Enter getParams.....");
        GifUrl = "Width=" + size().width + "&Height=" + size().height;
        int ndata = Integer.parseInt(getParameter("ndata"));

        if (x2 > -1)
        {
            ndata = x2 - x1 + 1;                                  //zoom
        }
        GifUrl += "&ndata=" + ndata;
        int ncdata = Integer.parseInt(getParameter("ncdata"));
        GifUrl += "&ncdata=" + ncdata;
        if (getParameter("percent") != null && getParameter("percent").equals("true"))
        {
            percent = true;
        }
        if (getParameter("percent") != null)
        {
            GifUrl += "&percent=" + getParameter("percent");
        }
        if (getParameter("allpercent") != null && getParameter("allpercent").equals("true"))
        {
            allpercent = true;
        }
        if (getParameter("allpercent") != null)
        {
            GifUrl += "&allpercent=" + getParameter("allpercent");
        }
        graphtype = getGraphType(getParameter("GraphType"));
        if (getParameter("GraphType") != null)
        {
            GifUrl += "&GraphType=" + getParameter("GraphType");
        }
        if (getParameter("BrakeLabels") != null && getParameter("BrakeLabels").equals("false"))
        {
            brakelabels = false;
        }
        if (getParameter("BrakeLabels") != null)
        {
            GifUrl += "&BrakeLabels=" + getParameter("BrakeLabels");
        }
        if (getParameter("vzoom") != null)
        {
            vzoom = Boolean.valueOf(getParameter("vzoom")).booleanValue();
        }
        if (getParameter("vzoom") != null)
        {
            GifUrl += "&vzoom=" + getParameter("vzoom");
        }
        if (getParameter("hvzoom") != null)
        {
            hvzoom = Boolean.valueOf(getParameter("hvzoom")).booleanValue();
        }
        if (getParameter("hvzoom") != null)
        {
            GifUrl += "&hvzoom=" + getParameter("hvzoom");
        }
        if (getParameter("showgrid") != null)
        {
            showgrid = Boolean.valueOf(getParameter("showgrid")).booleanValue();
        }
        if (getParameter("showgrid") != null)
        {
            GifUrl += "&showgrid=" + getParameter("showgrid");
        }
        if (getParameter("showpoints") != null)
        {
            showpoints = Boolean.valueOf(getParameter("showpoints")).booleanValue();
        }
        if (getParameter("showpoints") != null)
        {
            GifUrl += "&showpoints=" + getParameter("showpoints");
        }
        if (getParameter("showseriesnames") != null)
        {
            showseriesnames = Boolean.valueOf(getParameter("showseriesnames")).booleanValue();
        }
        if (getParameter("showseriesnames") != null)
        {
            GifUrl += "&showseriesnames=" + getParameter("showseriesnames");
        }
        if (getParameter("decimals") != null)
        {
            decimals = Boolean.valueOf(getParameter("decimals")).booleanValue();
        }
        if (getParameter("decimals") != null)
        {
            GifUrl += "&decimals=" + getParameter("decimals");
        }

        if (decimals)
        {
            formatterFloat = new DecimalFormat("0.00");
        }

        label = new String[ndata];
        data = new double[ndata][ncdata];
        barnames = new String[ncdata];
        graphtypes = new int[ncdata];
        bar = new Bar[ndata][ncdata];

        int ncolors = 0;
        if (graphtype == PIE)
        {
            ncolors = ndata;
        } else
        {
            ncolors = ncdata;
        }
        colors = new Color[ncolors];

        String aux;
        int x;
        for (x = 0; x < ndata; x++)
        {
            aux = getParameter("label" + (x + x1));
            GifUrl += "&label" + x + "=" + aux;
            label[x] = aux;
            aux = getParameter("data" + (x + x1));
            GifUrl += "&data" + x + "=" + aux;
            StringTokenizer st = new StringTokenizer(aux, "|");
            for (int y = 0; y < ncdata; y++)
            {
                if (st.hasMoreTokens())
                {
                    data[x][y] = Double.parseDouble(st.nextToken());
                } else
                {
                    data[x][y] = Double.NaN;
                }
                //System.out.println("x:"+x+" y:"+y+" data:"+data[x][y]);
            }
        }

        for (x = 0; x < ncolors; x++)
        {
            aux = getParameter("color" + (x));
            if (aux == null)
            {
                aux = "" + (int) (Math.random() * 255) + "," + (int) (Math.random() * 255) + "," + (int) (Math.random() * 255);
            }
            //System.out.println("color"+x+":"+aux);
            GifUrl += "&color" + x + "=" + aux;
            StringTokenizer st = new StringTokenizer(aux, ",");
            colors[x] = new Color(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (x = 0; x < ncdata; x++)
        {
            barnames[x] = getParameter("barname" + x);
            GifUrl += "&barname" + x + "=" + barnames[x];

            String type = getParameter("graphtype" + x);
            if (type != null)
            {
                GifUrl += "&graphtype" + x + "=" + type;
                graphtypes[x] = getGraphType(type);
                //System.out.println("graphtype"+x+"="+type+"->"+graphtypes[x]);
            } else
            {
                graphtypes[x] = graphtype;
            }
        }

        if (graphtype == PIE)
        {
            int fsize = 0;
            if (getParameter("Gif") != null || getParameter("linkbk") != null)
            {
                fsize = 35;
            }
            if (showseriesnames)
            {
                for (x = 0; x < ndata; x++)
                {
                    int ax = pad.getFontMetrics(f2).stringWidth(label[x]);
                    if (ax > fsize)
                    {
                        fsize = ax;
                    }
                }
            }
            Maxx = size().width - fsize - 15;
        } else
        {
            int fsize = 0;
            if (getParameter("Gif") != null || getParameter("linkbk") != null)
            {
                fsize = 35;
            }
            if (showseriesnames)
            {
                if (getParameter("zoom") != null)
                {
                    fsize = 35;
                }
                for (x = 0; x < ncdata; x++)
                {
                    int ax = pad.getFontMetrics(f2).stringWidth(barnames[x]);
                    if (ax > fsize)
                    {
                        fsize = ax;
                    }
                }
            }
            Maxx = size().width - fsize - 5;
        }

        Maxd = ndata;
        Maxc = ncdata;

        md = getMaxMulti(data);                     //Get Max Value
        mind = getMinMulti(data, md);                //Get Min Value
        if (graphtype == BARS_PERCENT)
        {
            md = 90;         //Porcentage 100%
        }
        if (hvzoom)
        {
            sd = (md - mind) / 10;                      //get scale
            if (sd == 0)
            {
                sd = 0.1F;
                md = md + .5F;
                mind = mind - .5F;
            }
            mbs = md;                               //max bar scale
            minbs = mind;                           //min bar scale
            xneg = (minbs / sd);
            mindif = minbs % sd;
        } else if (mind < 0)
        {
            sd = getScale(md - mind);                 //get scale
            mbs = getMaxBarScale(md, 0, sd);          //max bar scale
            minbs = -getMaxBarScale(-mind, 0, sd);    //min bar scale
            xneg = (int) (minbs / sd);
        } else
        {
            if (vzoom)
            {
                sd = getScale((md - mind) * 1.05);              //get scale
                mbs = getMaxBarScale(md, mind, sd);    //max bar scale
                minbs = mbs - sd * 10;  //min bar scale
                if (minbs < 0)
                {
                    minbs = 0;
                }
                if (minbs > mind)
                {
                    minbs = (int) mind;
                }
                xneg = (int) (minbs / sd);
            } else
            {
                sd = getScale(md);                   //get scale
                mbs = getMaxBarScale(md, 0, sd);         //max bar scale
                minbs = 0;                           //min bar scale
                xneg = 0;
            }
        }
        //System.out.println("*********************************************");
        //System.out.println("md:"+md);
        //System.out.println("mind:"+mind);
        //System.out.println("sd:"+sd);
        //System.out.println("mbs:"+mbs);
        //System.out.println("minbs:"+minbs);
        //System.out.println("mindif:"+mindif);
        //System.out.println("xneg:"+xneg);

        ma = (Maxy - 2 * sy);
        yrel = ma / (mbs - minbs);

        //System.out.println("ma:"+ma);
        ycero = (int) (Maxy - sy - (yrel * sd) * (-xneg));

        ycerod = ycero;
        if (ycerod > Maxy - sy)
        {
            ycerod = Maxy - sy;
        }

        //System.out.println("ycero:"+ycero);
        //System.out.println("ycerod:"+ycerod);
        sx = pad.getFontMetrics(f2).stringWidth(formatNum(mbs)) + 7;
        if (allpercent)
        {
            sx += 10;
        }
        if (percent)
        {
            dr = ((double) (Maxx - sx - 40) / (double) Maxd); //diretancia entre barra
        } else
        {
            dr = ((double) (Maxx - sx - 20) / (double) Maxd);       //diretancia entre barra
        }
        if (percent)
        {
            mps = getMaxPorcentScale();  //max percent scale
        }
        Title = getParameter("Title");
        target = getParameter("Target");
        if (getParameter("Title") != null)
        {
            GifUrl += "&Title=" + getParameter("Title");
        }
        SubTitle = getParameter("SubTitle");
        if (getParameter("SubTitle") != null)
        {
            GifUrl += "&SubTitle=" + getParameter("SubTitle");
        }
        if (getParameter("zoom") != null)
        {
            zoom = Boolean.valueOf(getParameter("zoom")).booleanValue();
        }
    }

    public long getMax(long data[], int Maxd)
    {
        long mx = 0;
        for (int x = 0; x < Maxd; x++)
        {
            if (mx < data[x])
            {
                mx = data[x];
            }
        }
        return mx;
    }

    public int getMaxPorcentScale()
    {
        double mx = 0;
        int x;
        for (x = 0; x < Maxd; x++)
        {
            if (mx < data[x][Maxc - 1])
            {
                mx = data[x][Maxc - 1];
            }
        }

        for (x = 0; x < 100; x += 10)
        {
            if (x > mx)
            {
                break;
            }
        }
        return x;
    }

    public double getMaxBarScale(double md, double mind, double sd)
    {
        double x = 0;
        for (x = 0; x < sd * 10 + mind; x += sd)
        {
            if (x > md)
            {
                break;
            }
        }
        if (x == 0)
        {
            x = 1;
        }
        return x;
    }

    public double getMaxMulti(double data[][])
    {
        double mx = 0;
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[x].length; y++)
            {
                if (mx < data[x][y])
                {
                    mx = data[x][y];
                }
            }
        }
        return mx;
    }

    public double getMinMulti(double data[][], double ini)
    {
        double mx = ini;
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[x].length; y++)
            {
                if (!Double.isNaN(data[x][y]))
                {
                    if (mx > data[x][y])
                    {
                        mx = data[x][y];
                    }
                }
            }
        }
        return mx;
    }

    public long getScale(double mx)
    {
        double n = 0;
        int x;
        long inc = 1;

        for (x = 0;; x++)
        {
            n = n + inc;
            if (x == 9)
            {
                inc = inc * 10;
                x = 0;
            }
            if (n > mx)
            {
                break;
            }
        }
        long r = (long) (n / 10);
        if (r == 0)
        {
            r = 1;
        }
        return r;
    }

    public void renderPie()
    {
        int x = 0;
        int y = 0;

        pad.setFont(f1);
        pad.setColor(Color.blue);
        if (Title != null)
        {
            pad.drawString(Title, Maxx / 2 - pad.getFontMetrics().stringWidth(Title) / 2, 9);
        }
        pad.setFont(f2);
        pad.setColor(Color.red);
        if (SubTitle != null)
        {
            pad.drawString(SubTitle, Maxx / 2 - pad.getFontMetrics().stringWidth(SubTitle) / 2, 18);
        }

        double tot = 0;
        for (x = 0; x < Maxd; x++)
        {
            tot = tot + data[x][0];
        }
        for (x = 0; x < Maxd; x++)
        {
            bar[x][0] = new Bar();
            if (label[x].startsWith("_"))
            {
                bar[x][0].parent = label[x].substring(1);
            } else
            {
                bar[x][0].parent = label[x];
            }
            bar[x][0].name = "" + (int) (((double) data[x][0] * 100 / tot) + 0.5);
            //System.out.println((double)data[x][0]*100/tot);
            bar[x][0].name += "%";
            bar[x][0].value = formatNum(data[x][y]);
        }

        //int cx=(int)(Maxx*.1),cy=(int)(Maxy*.2);
        //int rx=(int)(Maxx*.8),ry=(int)(Maxy*.5);
        int cx = (int) 30, cy = (int) 40;
        int rx = (int) (Maxx - 60), ry = (int) (Maxy - 90);
        pad.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        for (y = 0; y < cx; y++)
        {
            int sx = 0;
            for (x = 0; x < Maxd; x++)
            {
                pad.setColor(colors[x].darker());
                int vx = (int) ((double) data[x][0] * 360 / tot + 0.5);
                //pad.drawArc(cx,cy-y+cx,rx,ry,sx,vx);
                if (y > cx - 3)
                {
                    pad.setColor(colors[x]);
                    pad.drawArc(cx, cy - y + cx, rx, ry, sx, vx);
                    pad.fillArc(cx, cy - y + cx, rx, ry, sx, vx);
                } else
                {
                    pad.drawArc(cx, cy - y + cx, rx, ry, sx, vx);
                }
                sx += vx;
            }
        }
        pad.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int sx = 0;
        pad.setColor(Color.black);
        for (x = 0; x < Maxd; x++)
        {
            int vx = (int) ((double) data[x][0] * 360 / tot + 0.5);
            if (vx > 0)
            {
                double p = ((double) data[x][0] * 100 / tot + .5);
                if (p > 1.5)
                {
                    int lt = pad.getFontMetrics().stringWidth(label[x]);
                    int gx = (int) (rx / 2 * Math.cos(Math.PI * (sx + vx / 2) / 180));
                    int gy = -(int) (ry / 2 * Math.sin(Math.PI * (sx + vx / 2) / 180));
                    if (gx < 0)
                    {
                        gx -= lt;
                    }
                    if (gy > 0)
                    {
                        gy += cx + 8;
                    }
                    if (p > 4 && (cx + rx / 2 + gx + lt) < Maxx)
                    {
                        pad.drawString("" + label[x], cx + rx / 2 + gx, cy + ry / 2 + gy);
                        if (gy > 0)
                        {
                            gy += 10;
                        } else
                        {
                            gy += -10;
                        }
                    }
                    pad.drawString("" + (int) p + "%", cx + rx / 2 + gx, cy + ry / 2 + gy);
                    sx += vx;
                }
            }
        }

        for (x = 0; x < Maxd; x++)
        {
            pad.setColor(colors[x]);
            pad.fillRect(Maxx, Maxy / 2 - Maxd * 12 / 2 + (x + 1) * 12 - 9, 10, 10);
            pad.setColor(Color.black);
            pad.drawRect(Maxx, Maxy / 2 - Maxd * 12 / 2 + (x + 1) * 12 - 9, 10, 10);
            pad.drawString(label[x], Maxx + 15, Maxy / 2 - Maxd * 12 / 2 + (x + 1) * 12);
        }

        if (getParameter("linkbk") != null)
        {
            pad.setColor(Color.lightGray);
            pad.fill3DRect(Maxx - 15, Maxy - 35, 50, 20, true);
            pad.setColor(Color.black);
            pad.drawString("Up", Maxx + 5, Maxy - 22);
            but = new Bar();
            but.x = Maxx - 15;
            but.y = Maxy - 35;
            but.w = 50;
            but.h = 20;
        }
        //System.out.println("Gif:"+getParameter("Gif"));
        if (getParameter("Gif") != null)
        {
            pad.setColor(Color.lightGray);
            pad.fill3DRect(Maxx - 15, 15, 50, 20, true);
            pad.setColor(Color.black);
            pad.drawString("Get Image", Maxx - 12, 28);
            gif = new Bar();
            gif.x = Maxx - 15;
            gif.y = 15;
            gif.w = 50;
            gif.h = 20;
        }
    }

    void Grabber()
    {
        //System.out.println("Enter Grabber.....");
        pad.setColor(Color.white);
        pad.fillRect(0, 0, size().width, Maxy);
        int x, y;
        double tot = 0;
        for (x = 0; x < Maxd; x++)
        {
            tot = tot + data[x][0];
        }

        int cx = (int) 30, cy = (int) 40;
        int rx = (int) (Maxx - 60), ry = (int) (Maxy - 90);
        pad.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        for (y = 0; y < cx; y++)
        {
            int sx = 0;
            for (x = 0; x < Maxd; x++)
            {
                pad.setColor(colors[x]);
                int vx = (int) ((double) data[x][0] * 360 / tot + 0.5);
                pad.drawArc(cx, cy - y + cx, rx, ry, sx, vx);
                if (y > cx - 2)
                {
                    pad.fillArc(cx, cy - y + cx, rx, ry, sx, vx);
                }
                sx += vx;
            }
        }
        pad.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (x = 0; x < Maxd; x++)
        {
            pad.setColor(colors[x]);
            pad.fillRect(Maxx, Maxy / 2 - Maxd * 12 / 2 + (x + 1) * 12 - 9, 10, 10);
        }

        PixelGrabber pg = new PixelGrabber(buffer, 0, 0, Width, Height, pixels, 0, Width);
        try
        {
            pg.grabPixels();
        } catch (InterruptedException e)
        {
            System.out.println("Error en PixelGrabber:" + e);
        }

        rcolors = (Color[]) colors.clone();
        for (x = 0; x < Maxd; x++)
        {
            rcolors[x] = new Color(pixels[(Maxy / 2 - Maxd * 12 / 2 + (x + 1) * 12 - 8) * Width + Maxx + 1]);
        }
    }

    private int getLabelWidth(int x)
    {
        String ax = label[x];
        if (ax.startsWith("_"))
        {
            ax = ax.substring(1);
        }
        return pad.getFontMetrics().stringWidth(ax);
    }

    private int leftLabel(int x)
    {
        if (x == 0)
        {
            return 0;
        }
        if (label[x - 1].startsWith("_"))
        {
            int r = leftLabel(x - 1);
            int l = getLabelWidth(x - 1);
            int a = (int) ((dr * (x - 1) + dr / 2 + sx) - l / 2);
            if (r < a)
            {
                return (int) ((dr * (x - 1) + dr / 2 + sx) + l / 2);
            } else
            {
                return r;
            }
        } else
        {
            int l = getLabelWidth(x - 1);
            return (int) ((dr * (x - 1) + dr / 2 + sx) + l / 2);
        }
    }

    private int rigthLabel(int x)
    {
        if (x == (Maxd - 1))
        {
            return Maxx;
        }
        if (label[x + 1].startsWith("_"))
        {
            return rigthLabel(x + 1);
        } else
        {
            int l = getLabelWidth(x + 1);
            return (int) ((dr * (x + 1) + dr / 2 + sx) - l / 2);
        }
    }

    private boolean canWriteLabel(int x)
    {
        if (!label[x].startsWith("_"))
        {
            return true;
        }
        int l = getLabelWidth(x);
        int le = (int) ((dr * x + dr / 2 + sx) - l / 2);
        int ri = (int) ((dr * x + dr / 2 + sx) + l / 2);
        //System.out.println(x+"->"+le+">"+leftLabel(x)+" "+ri+"<"+rigthLabel(x));

        if (le > leftLabel(x) && ri < rigthLabel(x))
        {
            return true;
        }
        return false;
    }

    public void render()
    {
        if (graphtype == PIE)
        {
            renderPie();
            return;
        }
        int x = 0;
        int y = 0;

        pad.setFont(f1);
        pad.setColor(Color.blue);
        if (Title != null)
        {
            pad.drawString(Title, Maxx / 2 - pad.getFontMetrics().stringWidth(Title) / 2, 12);
        }
        pad.setFont(f2);
        pad.setColor(Color.red);
        if (SubTitle != null)
        {
            pad.drawString(SubTitle, Maxx / 2 - pad.getFontMetrics().stringWidth(SubTitle) / 2, 23);
        }
        pad.setColor(Color.black);
        pad.drawLine(sx - 2, ycerod, (int) (Maxd * dr + sx + 2), ycerod);

        if (brakelabels)
        {
            for (x = 0; x < Maxd; x++)
            {
                boolean swb = label[x].startsWith("_");
                String ax = label[x];
                if (swb)
                {
                    ax = ax.substring(1);
                }
                int l;
                ax = ax + "-";
                do
                {
                    ax = ax.substring(0, ax.length() - 1);
                    l = pad.getFontMetrics().stringWidth(ax);
                } while (l > dr);
                int fx = (int) ((dr * x + dr / 2 + sx) - l / 2);
                int fy = Maxy - sy + 12;
                pad.drawString(ax, fx, fy);
            }
        } else
        {
            for (x = 0; x < Maxd; x++)
            {
                if (canWriteLabel(x))
                {
                    boolean swb = label[x].startsWith("_");
                    String ax = label[x];
                    if (swb)
                    {
                        ax = ax.substring(1);
                    }
                    int l = pad.getFontMetrics().stringWidth(ax);
                    int fx = (int) ((dr * x + dr / 2 + sx) - l / 2);
                    int fy = Maxy - sy + 12;
                    //System.out.println(ax+" "+fx+" "+(fx+l));
                    pad.drawString(ax, fx, fy);
                }
            }
        }

        for (x = 0; x < Maxd; x++)
        {
            pad.drawLine((int) (dr * x + sx), ycerod - 2, (int) (dr * x + sx), ycerod + 2);
        }

        if (showgrid)
        {
            pad.setColor(Color.lightGray);
            int gridx = (int) (Maxd / 10);
            if (gridx == 0)
            {
                gridx = 1;
            }
            for (x = 0; x < Maxd; x += gridx)
            {
                pad.drawLine((int) (dr * x + sx), Maxy - sy, (int) (dr * x + sx), sy);
            }
            pad.setColor(Color.black);
        }

        pad.drawLine(sx, sy, sx, Maxy - sy + 2);
        for (x = (int) xneg; x <= Math.round((mbs / sd)); x++)
        {
            int h = (int) (Maxy - sy - (yrel * (sd * (x - xneg) + mindif)));
            if (h >= sy - 2)
            {
                //System.out.println("x:"+x+" h:"+h);
                double la = (x * sd) + mindif;
                String sa = "";
                if (hvzoom)
                {
                    sa = formatNum(la);
                } else
                {
                    sa = formatNum((long) la);
                }
                //System.out.println(sa);
                if (allpercent)
                {
                    sa = sa + "%";
                }
                pad.drawString(sa, sx - pad.getFontMetrics().stringWidth(sa) - 2, h + 4);
                pad.drawLine(sx - 2, h, sx + 2, h);
                if (showgrid && h != ycerod)
                {
                    //System.out.println("x:"+x);
                    pad.setColor(Color.lightGray);
                    pad.drawLine(sx + 3, h, (int) (Maxd * dr + sx + 2), h);
                    pad.setColor(Color.black);
                }
            }
        }

        if (percent)
        {
            pad.drawLine((int) (Maxd * dr + sx), sy, (int) (Maxd * dr + sx), Maxy - sy + 2);
            int es = 10;
            if (mps <= 50)
            {
                es = 5;
            }
            for (x = 0; x <= mps / es; x++)
            {
                int h = (int) (Maxy - sy - (ma / (mps / es)) * x);
                String sa = "" + (x * es) + "%";
                pad.drawString(sa, (int) (sx + Maxd * dr + 3), h + 4);
                pad.drawLine((int) (sx + Maxd * dr - 2), h, (int) (sx + Maxd * dr + 2), h);
            }
        }

        int maxc = Maxc;
//        if(percent)maxc=Maxc-1;
//          else maxc=Maxc;
        double sxd = (double) (dr * 0.1);

        //barras division por columna
        int ibars = 0;
        for (y = 0; y < maxc; y++)
        {
            if (graphtypes[y] == BARS || graphtypes[y] == BARS_PERCENT)
            {
                ibars++;
            }
        }
        if (ibars == 0)
        {
            ibars = 1;
        }
        double drd = ((dr - 2 * sxd) / ibars);

        for (y = 0; y < maxc; y++)
        {
            graphtype = graphtypes[y];
            if (percent && y == (maxc - 1))
            {
                graphtype = PERCENT;
            }

            //System.out.println(y+" graphtype:"+graphtype);
            if (graphtype == BARS)
            {
                for (x = 0; x < Maxd; x++)
                {
//                    for(y=0;y<maxc;y++)
//                    {
                    if (data[x][y] != 0 && !Double.isNaN(data[x][y]))
                    {
                        bar[x][y] = new Bar();
                        bar[x][y].x = (int) (dr * x + sxd + drd * y + sx);
                        bar[x][y].w = (int) drd;
                        bar[x][y].h = (int) (data[x][y] * yrel) - (ycero - ycerod);
                        bar[x][y].y = (int) (ycerod - bar[x][y].h);
                        if (data[x][y] < 0)
                        {
                            bar[x][y].h = -bar[x][y].h;
                            bar[x][y].y = ycero;
                        }
                        if (label[x].startsWith("_"))
                        {
                            bar[x][y].parent = label[x].substring(1);
                        } else
                        {
                            bar[x][y].parent = label[x];
                        }
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = formatNum(data[x][y]);
                        if (allpercent)
                        {
                            bar[x][y].value += "%";
                        }
                        pad.setColor(colors[y]);
                        pad.fill3DRect(bar[x][y].x, bar[x][y].y, bar[x][y].w, bar[x][y].h, true);
                    }
//                    }
                }

//                if(percent)
//                {
//                    int oldx=(int)((dr*.5)+sx);
//                    int oldy=(int)(Maxy-sy-((data[0][y]*ma)/mps));
//                    for(x=0;x<Maxd;x++)
//                    {
//                        if(data[x][y]>0)
//                        {
//                            pad.setColor(colors[y]);
//                            pad.drawLine(oldx,oldy,oldx=(int)(dr*(x+.5)+sx),oldy=(int)(Maxy-sy-((data[x][y]*ma)/mps)));
//                            if(showpoints)pad.fillOval(oldx-3,oldy-3,6,6);
//                            bar[x][y]=new Bar();
//                            bar[x][y].x=oldx-3;
//                            bar[x][y].y=oldy-3;
//                            bar[x][y].w=6;
//                            bar[x][y].h=6;
//                            if(label[x].startsWith("_"))bar[x][y].parent=label[x].substring(1);
//                            else bar[x][y].parent=label[x];
//                            bar[x][y].name=barnames[y];
//                            bar[x][y].value=""+data[x][y];
//                            if(allpercent)bar[x][y].value+="%";
//                        }
//                        else if(x+1<Maxd)
//                        {
//                            oldx=(int)(dr*(x+1.5)+sx);
//                            oldy=(int)(Maxy-sy-((data[x+1][y]*ma)/mps));
//                        }
//                    }
//                }
            }

            if (graphtype == PERCENT)
            {
                int oldx = (int) ((dr * .5) + sx);
                int oldy = (int) (Maxy - sy - ((data[0][y] * ma) / mps));
                for (x = 0; x < Maxd; x++)
                {
                    if (data[x][y] > 0)
                    {
                        pad.setColor(colors[y]);
                        pad.drawLine(oldx, oldy, oldx = (int) (dr * (x + .5) + sx), oldy = (int) (Maxy - sy - ((data[x][y] * ma) / mps)));
                        if (showpoints)
                        {
                            pad.fillOval(oldx - 3, oldy - 3, 6, 6);
                        }
                        bar[x][y] = new Bar();
                        bar[x][y].x = oldx - 3;
                        bar[x][y].y = oldy - 3;
                        bar[x][y].w = 6;
                        bar[x][y].h = 6;
                        if (label[x].startsWith("_"))
                        {
                            bar[x][y].parent = label[x].substring(1);
                        } else
                        {
                            bar[x][y].parent = label[x];
                        }
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = "" + data[x][y];
                        if (allpercent)
                        {
                            bar[x][y].value += "%";
                        }
                    } else if (x + 1 < Maxd)
                    {
                        oldx = (int) (dr * (x + 1.5) + sx);
                        oldy = (int) (Maxy - sy - ((data[x + 1][y] * ma) / mps));
                    }
                }
            }

            if (graphtype == LINES)
            {
//                for(y=0;y<maxc;y++)
//                {
                int oldx = (int) ((dr * .5) + sx);
                int oldy = (int) (ycero - (data[0][y] * yrel));
                for (x = 0; x < Maxd; x++)
                {
                    if (!Double.isNaN(data[x][y]))
                    {
                        pad.setColor(colors[y]);
                        pad.drawLine(oldx, oldy, oldx = (int) (dr * (x + .5) + sx), oldy = (int) (ycero - (data[x][y] * yrel)));
                        if (showpoints)
                        {
                            pad.fillOval(oldx - 3, oldy - 3, 6, 6);
                        }
                        bar[x][y] = new Bar();
                        bar[x][y].x = oldx - 3;
                        bar[x][y].y = oldy - 3;
                        bar[x][y].w = 6;
                        bar[x][y].h = 6;
                        if (label[x].startsWith("_"))
                        {
                            bar[x][y].parent = label[x].substring(1);
                        } else
                        {
                            bar[x][y].parent = label[x];
                        }
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = formatNum(data[x][y]);
                        if (allpercent)
                        {
                            bar[x][y].value += "%";
                        }
                    }
                    //                    else if(x+1<Maxd)
                    //                    {
                    //                        oldx=(int)(dr*(x+1.5)+sx);
                    //                        oldy=(int)(ycero-((data[x+1][y]*ma)/mbs));
                    //                    }
                }
//                }

//                if(percent)
//                {
//                    int oldx=(int)((dr*.5)+sx);
//                    int oldy=(int)(Maxy-sy-((data[0][y]*ma)/mps));
//                    for(x=0;x<Maxd;x++)
//                    {
//                        if(data[x][y]>0)
//                        {
//                            pad.setColor(colors[y]);
//                            pad.drawLine(oldx,oldy,oldx=(int)(dr*(x+.5)+sx),oldy=(int)(Maxy-sy-((data[x][y]*ma)/mps)));
//                            if(showpoints)pad.fillOval(oldx-3,oldy-3,6,6);
//                            bar[x][y]=new Bar();
//                            bar[x][y].x=oldx-3;
//                            bar[x][y].y=oldy-3;
//                            bar[x][y].w=6;
//                            bar[x][y].h=6;
//                            if(label[x].startsWith("_"))bar[x][y].parent=label[x].substring(1);
//                            else bar[x][y].parent=label[x];
//                            bar[x][y].name=barnames[y];
//                            bar[x][y].value=""+data[x][y];
//                            if(allpercent)bar[x][y].value+="%";
//                        }
//                        else if(x+1<Maxd)
//                        {
//                            oldx=(int)(dr*(x+1.5)+sx);
//                            oldy=(int)(Maxy-sy-((data[x+1][y]*ma)/mps));
//                        }
//                    }
//                }
            }
            if (graphtype == BARS_PERCENT)
            {
                for (x = 0; x < Maxd; x++)
                {
                    int lasty = 0;
//                    for(y=0;y<maxc;y++)
//                    {
                    if (data[x][y] > 0 && !Double.isNaN(data[x][y]))
                    {
                        bar[x][y] = new Bar();
                        bar[x][y].x = (int) (dr * x + sxd + sx);
                        bar[x][y].y = (int) Math.round((double) Maxy - (double) sy - (double) ((double) ((100 - lasty) * ma) / (double) (sd * (mbs / sd))));
                        bar[x][y].w = (int) drd * maxc;
                        bar[x][y].h = (int) Math.round((double) ((data[x][y] - lasty) * ma) / (double) (sd * (mbs / sd)));
                        if (label[x].startsWith("_"))
                        {
                            bar[x][y].parent = label[x].substring(1);
                        } else
                        {
                            bar[x][y].parent = label[x];
                        }
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = formatNum(data[x][y] - lasty);
                        if (allpercent)
                        {
                            bar[x][y].value += "%";
                        }
                        pad.setColor(colors[y]);
                        pad.fill3DRect(bar[x][y].x, bar[x][y].y, bar[x][y].w, bar[x][y].h, true);
                        lasty = (int) data[x][y];
                    }
//                    }
                }
            }

            if (graphtype == AREA)
            {
                for (x = 0; x < Maxd; x++)
                {
//                    for(y=0;y<maxc;y++)
//                    {
                    //if(data[x][y]!=0)
                    {
                        bar[x][y] = new Bar();
                        bar[x][y].x = (int) (dr * x + sx);
                        bar[x][y].w = (int) (dr);
                        bar[x][y].h = (int) (data[x][y] * yrel) - (ycero - ycerod);
                        bar[x][y].y = (int) (ycerod - bar[x][y].h);
                        if (data[x][y] < 0)
                        {
                            bar[x][y].h = -bar[x][y].h;
                            bar[x][y].y = ycero;
                        }
                        if (label[x].startsWith("_"))
                        {
                            bar[x][y].parent = label[x].substring(1);
                        } else
                        {
                            bar[x][y].parent = label[x];
                        }
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = formatNum(data[x][y]);
                        if (allpercent)
                        {
                            bar[x][y].value += "%";
                        }
                    }
//                    }
                }

                for (x = 0; x < Maxd; x++)
                {
//                    for(y=0;y<maxc;y++)
//                    {
                    if (!Double.isNaN(data[x][y]))
                    {

                        pad.setColor(colors[y]);
                        //pad.fill3DRect(bar[x][y].x,bar[x][y].y,bar[x][y].w,bar[x][y].h,true);
                        Polygon p = new Polygon();
                        p.addPoint(bar[x][y].x, bar[x][y].y + bar[x][y].h);
                        if (x == 0)
                        {
                            p.addPoint(bar[x][y].x, bar[x][y].y);
                        } else
                        {
                            int y1 = bar[x - 1][y].y;
                            int y2 = bar[x][y].y;
                            p.addPoint(bar[x][y].x, y1 + (y2 - y1) / 2);
                        }
                        p.addPoint(bar[x][y].x + bar[x][y].w / 2, bar[x][y].y);
                        if (x == Maxd - 1 || Double.isNaN(data[x + 1][y]))
                        {
                            p.addPoint(bar[x][y].x + bar[x][y].w, bar[x][y].y);
                            p.addPoint(bar[x][y].x + bar[x][y].w, bar[x][y].y + bar[x][y].h);
                        } else
                        {
                            //int y2=(int)(ycero-((data[x+1][y]*ma)/(mbs-minbs)));
                            int y2 = bar[x + 1][y].y;
                            int y1 = bar[x][y].y;
                            p.addPoint(bar[x + 1][y].x, y1 + (y2 - y1) / 2);
                            p.addPoint(bar[x + 1][y].x, bar[x][y].y + bar[x][y].h);
                        }

                        bar[x][y].p = p;
                        Rectangle rec = p.getBounds();
                        if (rec.getWidth() == 0)                   //Si las width==0
                        {
                            pad.fillRect(rec.x, rec.y, 1, rec.height);
                        } else
                        {
                            pad.fillPolygon(p);
                        }

                    }
//                    }
                }

//                if(percent)
//                {
//                    int oldx=(int)((dr*.5)+sx);
//                    int oldy=(int)(Maxy-sy-((data[0][y]*ma)/mps));
//                    for(x=0;x<Maxd;x++)
//                    {
//                        if(data[x][y]>0)
//                        {
//                            pad.setColor(colors[y]);
//                            pad.drawLine(oldx,oldy,oldx=(int)(dr*(x+.5)+sx),oldy=(int)(Maxy-sy-((data[x][y]*ma)/mps)));
//                            if(showpoints)pad.fillOval(oldx-3,oldy-3,6,6);
//                            bar[x][y]=new Bar();
//                            bar[x][y].x=oldx-3;
//                            bar[x][y].y=oldy-3;
//                            bar[x][y].w=6;
//                            bar[x][y].h=6;
//                            if(label[x].startsWith("_"))bar[x][y].parent=label[x].substring(1);
//                            else bar[x][y].parent=label[x];
//                            bar[x][y].name=barnames[y];
//                            bar[x][y].value=""+data[x][y];
//                            if(allpercent)bar[x][y].value+="%";
//                        }
//                        else if(x+1<Maxd)
//                        {
//                            oldx=(int)(dr*(x+1.5)+sx);
//                            oldy=(int)(Maxy-sy-((data[x+1][y]*ma)/mps));
//                        }
//                    }
//                }
            }
        }

        if (showseriesnames)
        {
            for (x = 0; x < Maxc; x++)
            {
                pad.setColor(colors[x]);
                pad.fillRect(Maxx - 15, Maxy / 2 - Maxc * 20 / 2 + (x + 1) * 20 - 9, 10, 10);
                pad.setColor(Color.black);
                pad.drawRect(Maxx - 15, Maxy / 2 - Maxc * 20 / 2 + (x + 1) * 20 - 9, 10, 10);
                pad.drawString(barnames[x], Maxx, Maxy / 2 - Maxc * 20 / 2 + (x + 1) * 20);
            }
        }

        if (showseriesnames && Integer.parseInt(getParameter("ndata")) != Maxd)           //esta con zoom
        {
            pad.setColor(Color.lightGray);
            pad.fill3DRect(Maxx - 15, Maxy - 35, 50, 20, true);
            pad.setColor(Color.black);
            pad.drawString("Zoom Out", Maxx - 10, Maxy - 22);
            but = new Bar();
            but.x = Maxx - 15;
            but.y = Maxy - 35;
            but.w = 50;
            but.h = 20;
        } else if (getParameter("linkbk") != null)
        {
            pad.setColor(Color.lightGray);
            pad.fill3DRect(Maxx - 15, Maxy - 35, 50, 20, true);
            pad.setColor(Color.black);
            pad.drawString("Up", Maxx + 5, Maxy - 22);
            but = new Bar();
            but.x = Maxx - 15;
            but.y = Maxy - 35;
            but.w = 50;
            but.h = 20;
        }

        if (getParameter("Gif") != null)
        {
            pad.setColor(Color.lightGray);
            pad.fill3DRect(Maxx - 15, 15, 50, 20, true);
            pad.setColor(Color.black);
            pad.drawString("Get Image", Maxx - 12, 28);
            gif = new Bar();
            gif.x = Maxx - 15;
            gif.y = 15;
            gif.w = 50;
            gif.h = 20;
        }
        //return;
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {

        setLayout(new java.awt.BorderLayout());

        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                mouseClick(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                formMouseMoved(evt);
            }
        });

    }//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        // TODO add your handling code here:
        sizeComponent();

        setZoomData(0, 0);
        render();
    }//GEN-LAST:event_formComponentResized

    private void sizeComponent()
    {
        Width = size().width;
        Height = size().height;
        Maxx = size().width - 35;
        Maxy = size().height;
        pixels = new int[Width * Height];
        buffer = (BufferedImage) createImage(size().width, size().height);
        //pad=buffer.getGraphics();
        pad = createGraphics2D(buffer);

        backbuffer = (BufferedImage) createImage(size().width, size().height);
        bpad = createGraphics2D(backbuffer);

        pad.setColor(Color.white);
        pad.fillRect(0, 0, size().width, Maxy);
        pad.setColor(Color.black);

        composite_orig = pad.getComposite();
    }

    private void formMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseReleased
    {//GEN-HEADEREND:event_formMouseReleased
        // TODO add your handling code here:
        drag_x2 = evt.getX();
        if (drag)
        {
            //System.out.println("End");
            drag = false;
            setZoomData(drag_x1, drag_x2);
            pad.setColor(Color.white);
            pad.fillRect(0, 0, size().width, Maxy);
            render();
            //pad.drawImage(backbuffer,0,0,this);
            repaint();
        }
    }//GEN-LAST:event_formMouseReleased

    private void formMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMousePressed
    {//GEN-HEADEREND:event_formMousePressed
        // TODO add your handling code here:
        drag_x1 = evt.getX();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseDragged
    {//GEN-HEADEREND:event_formMouseDragged
        // TODO add your handling code here:
        //System.out.println("Drag:"+evt);
        drag_x2 = evt.getX();

        if (zoom)
        {
            int x1 = drag_x1;
            int x2 = drag_x2;

            if (x1 > x2)
            {
                x1 = drag_x2;
                x2 = drag_x1;
            }

            if (x1 < sx)
            {
                x1 = sx;
            }
            if (x2 > (Maxd * dr + sx - 1))
            {
                x2 = (int) (Maxd * dr + sx - 1);
            }

            if (!drag)
            {
                //System.out.println("Start");
                bpad.drawImage(buffer, 0, 0, this);
                drag = true;
            } else
            {
                pad.drawImage(backbuffer, 0, 0, this);
                pad.setComposite(composite);
                pad.fillRect(x1, sy, x2 - x1, Maxy - sy - sy);
                pad.setComposite(composite_orig);
            }
            repaint();
        }
    }//GEN-LAST:event_formMouseDragged

  private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved

      //System.out.println("Move:"+evt);
      boolean fl = false;
      boolean fl2 = false;
      boolean fl3 = false;

      int cx = (int) 30, cy = (int) 40;                          //
      int rx = (int) (Maxx - 60), ry = (int) (Maxy - 90);            // PIEChart
      double tot = 0;
      for (int x = 0; x < Maxd; x++)
      {
          tot = tot + data[x][0];   //
      }
      int sx = 0;                                           //

      for (int x = 0; x < Maxd; x++)
      {
          if (graphtype == PIE)
          {
              int vx = (int) ((double) data[x][0] * 360 / tot + 0.5);
              if (pixels[evt.getY() * Width + evt.getX()] == rcolors[x].getRGB())
              {
                  pad.setColor(Color.white);
                  pad.fillRect(0, 0, size().width, Maxy);
                  render();
                  pad.setColor(colors[x].darker());
//                  pad.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                  for (int bx = -3; bx < 7; bx++)
                  {
                      //pad.drawArc(cx,cy-bx,rx,ry,sx,vx);
                      pad.fillArc(cx, cy - bx, rx, ry, sx, vx);
                  }
//                  pad.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  pad.setColor(colors[x].brighter());
                  pad.fillArc(cx, cy - 7, rx, ry, sx, vx);
                  pad.setColor(Color.white);
                  int fh = pad.getFontMetrics().getHeight();
                  int mxf = java.lang.Math.max(java.lang.Math.max(pad.getFontMetrics().stringWidth(bar[x][0].value), pad.getFontMetrics().stringWidth(bar[x][0].name)), pad.getFontMetrics().stringWidth(bar[x][0].parent));
                  int dify = 0;
                  int difx = 0;
//                  if((evt.getY()-fh*3-5)<0)
//                  {
//                      difx=-mxf-10;
//                      dify=-(evt.getY()-fh*3-5);
//                  }
//                  if(evt.getX()+mxf+10>Width)
//                  {
//                      difx=-mxf-10;
//                      //dify=-(evt.getY()-fh*3-5);
//                  }
                  if ((evt.getY() - fh * 3 - 5) < 0)
                  {
                      dify = -(evt.getY() - fh * 3 - 5);
                  }
                  if ((evt.getX() + mxf + 10) > getSize().getWidth())
                  {
                      difx = -mxf - 10;
                  }
                  if ((evt.getX() + difx) < 0)
                  {
                      difx = difx - (evt.getX() + difx);
                  }

                  pad.fillRect(evt.getX() + difx, evt.getY() - fh * 3 - 5 + dify, mxf + 10, fh * 3 + 5);
                  pad.setColor(Color.black);
                  pad.drawRect(evt.getX() + difx, evt.getY() - fh * 3 - 5 + dify, mxf + 10, fh * 3 + 5);
                  pad.setColor(Color.blue);
                  pad.drawString(bar[x][0].value, evt.getX() + 5 + difx, evt.getY() - 5 + dify);
                  pad.drawString(bar[x][0].name, evt.getX() + 5 + difx, evt.getY() - fh - 5 + dify);
                  pad.drawString(bar[x][0].parent, evt.getX() + 5 + difx, evt.getY() - fh * 2 - 5 + dify);
                  repaint();
                  fl = true;
                  if (actbarname == bar[x][0].parent)
                  {
                      enter = false;
                  }
                  actbarname = bar[x][0].parent;
                  actsubbarname = bar[x][0].name;
              }
              sx += vx;
              /*
               pad.setColor(Color.white);
               pad.fillRect(0,0,200,30);
               pad.setColor(Color.black);
               //pad.drawString(new Color(pixels[evt.getY()*Width+evt.getX()]).toString(),10,10);
               pad.drawString(actbarname,10,10);
               repaint();
               */

          } else
          {
              for (int y = 0; y < Maxc; y++)
              {
                  if (bar[x][y] != null)
                  {
                      if (bar[x][y].inSide(evt.getX(), evt.getY()))
                      {
                          actbarname = bar[x][y].parent;
                          actsubbarname = bar[x][y].name;
                          //System.out.println(actsubbarname);
                          pad.setColor(Color.white);
                          pad.fillRect(0, 0, size().width, Maxy);
                          render();
                          //pad.setColor(colors[y]);
                          //pad.fill3DRect(bar[x][y].x,bar[x][y].y,bar[x][y].w,bar[x][y].h,false);
                          pad.setColor(Color.red);
                          if (bar[x][y].p == null)
                          {
                              pad.drawRect(bar[x][y].x, bar[x][y].y, bar[x][y].w, bar[x][y].h);
                          } else
                          {
                              pad.drawPolygon(bar[x][y].p);
                          }
                          pad.setColor(Color.white);
                          int fh = pad.getFontMetrics().getHeight();
                          int mxf = java.lang.Math.max(java.lang.Math.max(pad.getFontMetrics().stringWidth(bar[x][y].value), pad.getFontMetrics().stringWidth(bar[x][y].name)), pad.getFontMetrics().stringWidth(bar[x][y].parent));
                          int dify = 0;
                          int difx = 0;
                          if ((evt.getY() - fh * 3 - 5) < 0)
                          {
                              dify = -(evt.getY() - fh * 3 - 5);
                              difx = -mxf - 10;
                          }
                          if ((evt.getX() + mxf + 10) > getSize().getWidth())
                          {
                              difx = -mxf - 10;
                          }
                          if ((evt.getX() + difx) < 0)
                          {
                              difx = difx - (evt.getX() + difx);
                          }

                          pad.fillRect(evt.getX() + difx, evt.getY() - fh * 3 - 5 + dify, mxf + 10, fh * 3 + 5);
                          pad.setColor(Color.black);
                          pad.drawRect(evt.getX() + difx, evt.getY() - fh * 3 - 5 + dify, mxf + 10, fh * 3 + 5);
                          pad.setColor(Color.blue);
                          if (y == Maxc - 1 && percent)
                          {
                              pad.drawString(bar[x][y].value + "%", evt.getX() + 5 + difx, evt.getY() - 5 + dify);
                          } else
                          {
                              pad.drawString(bar[x][y].value, evt.getX() + 5 + difx, evt.getY() - 5 + dify);
                          }
                          pad.drawString(bar[x][y].name, evt.getX() + 5 + difx, evt.getY() - fh - 5 + dify);
                          pad.drawString(bar[x][y].parent, evt.getX() + 5 + difx, evt.getY() - fh * 2 - 5 + dify);
                          repaint();
                          fl = true;
                      }
                  }
              }
          }
      }

      if (!enter && fl && !fl2 && !fl3)
      {
          enter = true;
          dir = null;
          if (getParameter("link") != null)
          {
              papa.setCursor(12);
              try
              {
                  dir = new URL(getDocumentBase(), getParameter("link") + actbarname);
              } catch (MalformedURLException e)
              {
              }
          }
      } else if (enter && !fl && !fl2 && !fl2)
      {
          enter = false;
          pad.setColor(Color.white);
          pad.fillRect(0, 0, Width, Maxy);
          papa.setCursor(0);
          render();
          repaint();
          dir = null;
      }

      if (but != null && but.inSide(evt.getX(), evt.getY()))
      {
          fl2 = true;
      }
      if (gif != null && gif.inSide(evt.getX(), evt.getY()))
      {
          fl3 = true;
      }

      if (!enter && fl2 && !fl && !fl3)
      {
          enter = true;
          dir = null;
          if (Integer.parseInt(getParameter("ndata")) != Maxd)
          {
              papa.setCursor(12);
          } else if (getParameter("linkbk") != null)
          {
              papa.setCursor(12);
              try
              {
                  dir = new URL(getDocumentBase(), getParameter("linkbk"));
              } catch (MalformedURLException e)
              {
              }
          }
      } else if (enter && !fl2 && !fl && !fl3)
      {
          enter = false;
          papa.setCursor(0);
          render();
          repaint();
          dir = null;
      }

      if (!enter && fl3 && !fl2 && !fl)
      {
          enter = true;
          dir = null;
          if (getParameter("Gif") != null)
          {
              papa.setCursor(12);
              try
              {
                  dir = new URL(getDocumentBase(), getParameter("Gif"));
                  gifEnter = true;
              } catch (MalformedURLException e)
              {
                  e.printStackTrace();
              }
          }
      } else if (enter && !fl2 && !fl && !fl3)
      {
          gifEnter = false;;
          enter = false;
          papa.setCursor(0);
          render();
          repaint();
          dir = null;
      }

  }//GEN-LAST:event_formMouseMoved

  private void mouseClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClick
      // Add your handling code here:
      //System.out.println("Click:"+evt);

      if (Integer.parseInt(getParameter("ndata")) != Maxd && ((but != null && but.inSide(evt.getX(), evt.getY())) || evt.getButton() == evt.BUTTON3))
      {
          offset = 0;
          drag_x1 = 0;
          drag_x2 = 0;
          setZoomData(drag_x1, drag_x2);
          pad.setColor(Color.white);
          pad.fillRect(0, 0, size().width, Maxy);
          render();
          repaint();
      } else
      {
          if (enter && dir != null)
          {
              if (gifEnter)
              {
                  String ret = sendPost(GifUrl);
                  try
                  {
                      dir = new URL(dir.toString());
                  } catch (MalformedURLException e)
                  {
                  }
              } else
              {
                  try
                  {
                      dir = new URL(dir.toString() + "&sub=" + actsubbarname);
                  } catch (MalformedURLException e)
                  {
                  }
              }
              //try {dir = new URL(getDocumentBase(),getParameter("link")+actbarname+"&sub="+actsubbarname);}catch (MalformedURLException e){}
              if (target == null)
              {
                  apc.showDocument(dir);
              } else
              {
                  apc.showDocument(dir, target);
              }
          }
      }
  }//GEN-LAST:event_mouseClick
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private String sendPost(String data)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String ret = "";
        try
        {
            URLConnection urlconn = dir.openConnection();
            urlconn.setUseCaches(false);
            //if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            //urlconn.setRequestProperty("Accept-Encoding", "gzip");
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(data);
            pout.close();

            //String accept=urlconn.getHeaderField("Content-Encoding");
            InputStream in;
            //if (accept != null && accept.toLowerCase().indexOf("gzip") != -1)
            //{
            //    in=new GZIPInputStream(urlconn.getInputStream(),bufferSize);
            //}else
            //{
            in = urlconn.getInputStream();
            //}

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                out.write(bfile, 0, x);
            }
            in.close();
            out.flush();
            out.close();
            ret = new String(out.toByteArray());
        } catch (Exception e)
        {
            System.out.println("Error to open service..." + e);
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        return ret;
    }

    public void run()
    {
        /*
         while(kicker != null)
         {
         try
         {
         kicker.sleep(Math.max(20,nextTime-System.currentTimeMillis()));
         } catch (InterruptedException e) {}
         nextTime=System.currentTimeMillis()+77;
         //repaint();
         }
         */
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        g.drawImage(buffer, 0, 0, this);
    }

    public void start()
    {
        /*
         if (kicker == null)
         {
         kicker = new Thread(this);
         kicker.start();
         nextTime=System.currentTimeMillis()+75;
         }
         */
    }

    public void stop()
    {
        /*
         if (kicker != null)
         {
         kicker.stop();
         kicker=null;
         }
         **/
        pad.dispose();
        buffer = null;
    }

    public String formatNum(double num)
    {
        return formatNum(formatterFloat.format(num));
        //return NumberFormat.getInstance().format(num);
        //return formatNum(""+num);
    }

    public String formatNum(int num)
    {
        return formatNum(formatterInt.format(num));
        //return NumberFormat.getInstance().format(num);
        //return formatNum(""+num);
    }

    public String formatNum(String num)
    {
        String ax = "";
        if (num.startsWith("-"))
        {
            num = num.substring(1);
            ax = "-";
        }
        String dec = "";
        int idn = num.indexOf('.');
        if (idn > -1)
        {
            dec = num.substring(idn);
            num = num.substring(0, idn);
            if (dec.length() > 3)
            {
                dec = dec.substring(0, 3);
            } else if (dec.length() == 2)
            {
                dec = dec + "0";
            }
        }
        String aux = "";
        int l = num.length();
        for (int x = 0; x < l; x++)
        {
            if ((l - x) % 3 == 0 && (l - x) != l)
            {
                aux = aux + "," + num.substring(x, x + 1);
            } else
            {
                aux = aux + num.substring(x, x + 1);
            }
        }
        aux = ax + aux + dec;
        return aux;
    }

}
