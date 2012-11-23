/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
**/
 


package applets.wbabar;

/*
Esta clase esta dentro del archivo /wbadmin/lib/WBABar.jar y se usa para las graficas de los reportes
*/

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

class Bar{

    public int x=0;
    public int y=0;
    public int w=0;
    public int h=0;
    public String parent="";
    public String name="";
    public String value="";

    /** Creates new Bar */
    public Bar() {
    }

    public boolean inSide(int ax, int ay)
    {
        if(ax>=x && ax<=(x+w) && ay>=y && ay<=(y+h)) return true;
        else return false;
    }
}

public class WBABar4Report extends Applet
    implements Runnable
{

    public WBABar4Report()
    {
        kicker = null;
        percent = false;
        allpercent = false;
        brakelabels = true;
        graphtype = 0;
        dir = null;
        sx = 45;
        sy = 40;
        dr = 0;
        md = 0L;
        sd = 0L;
        mbs = 0L;
        mps = 0;
        enter = false;
        actbarname = null;
        actbarname_aux = null;  // nueva variable
        actsubbarname = null;
    }

    public void init()
    {
        initComponents();
        Width = size().width;
        Height = size().height;
        Maxx = size().width - 35;
        Maxy = size().height;
        pixels = new int[Width * Height];
        buffer = createImage(size().width, size().height);
        pad = buffer.getGraphics();
        pad.setColor(Color.white);
        pad.fillRect(0, 0, size().width, Maxy);
        pad.setColor(Color.black);
        Component object;
        for(object = this; !(object instanceof Frame); object = object.getParent());
        try
        {
            papa = (Frame)object;
        }
        catch(Throwable throwable) { }
        apc = getAppletContext();
        f1 = new Font("Arial", 0, 12);
        f2 = new Font("Arial", 0, 9);
        getParams();
        if(graphtype == 2)
            Grabber();
        render();
    }

    public void getParams()
    {
        GifUrl = "?Width=" + size().width + "&Height=" + size().height;
        int ndata = Integer.parseInt(getParameter("ndata"));
        GifUrl += "&ndata=" + ndata;
        int ncdata = Integer.parseInt(getParameter("ncdata"));
        GifUrl += "&ncdata=" + ncdata;
        if(getParameter("percent") != null && getParameter("percent").equals("true"))
            percent = true;
        if(getParameter("percent") != null)
            GifUrl += "&percent=" + getParameter("percent");
        if(getParameter("allpercent") != null && getParameter("allpercent").equals("true"))
            allpercent = true;
        if(getParameter("allpercent") != null)
            GifUrl += "&allpercent=" + getParameter("allpercent");
        if(getParameter("GraphType") != null && getParameter("GraphType").equals("Bars"))
            graphtype = 0;
        if(getParameter("GraphType") != null && getParameter("GraphType").equals("Lines"))
            graphtype = 1;
        if(getParameter("GraphType") != null && getParameter("GraphType").equals("Pie"))
            graphtype = 2;
        if(getParameter("GraphType") != null && getParameter("GraphType").equals("BarsPercent"))
            graphtype = 3;
        if(getParameter("GraphType") != null)
            GifUrl += "&GraphType=" + getParameter("GraphType");
        if(getParameter("BrakeLabels") != null && getParameter("BrakeLabels").equals("false"))
            brakelabels = false;
        if(getParameter("BrakeLabels") != null)
            GifUrl += "&BrakeLabels=" + getParameter("BrakeLabels");
        label = new String[ndata];
        data = new long[ndata][ncdata];
        barnames = new String[ncdata];
        bar = new Bar[ndata][ncdata];
        int ncolors = 0;
        if(graphtype == 2 || ncdata == 1)
            ncolors = ndata;
        else
            ncolors = ncdata;
        colors = new Color[ncolors];
        for(int x = 0; x < ndata; x++)
        {
            String aux = getParameter("label" + x);
            GifUrl += "&label" + x + "=" + aux;
            label[x] = aux;
            aux = getParameter("data" + x);
            GifUrl += "&data" + x + "=" + aux;
            StringTokenizer st = new StringTokenizer(aux, "|");
            for(int y = 0; y < ncdata; y++)
                if(st.hasMoreTokens())
                    data[x][y] = Long.parseLong(st.nextToken());
                else
                    data[x][y] = -1L;

        }

        for(int x = 0; x < ncolors; x++)
        {
            String aux = getParameter("color" + x);
            if(aux == null)
                aux = "" + (int)(Math.random() * 255D) + "," + (int)(Math.random() * 255D) + "," + (int)(Math.random() * 255D);
            GifUrl += "&color" + x + "=" + aux;
            StringTokenizer st = new StringTokenizer(aux, ",");
            colors[x] = new Color(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for(int x = 0; x < ncdata; x++)
        {
            barnames[x] = getParameter("barname" + x);
            GifUrl += "&barname" + x + "=" + barnames[x];
        }

        if(graphtype == 2)
        {
            int fsize = 0;
            if(getParameter("Gif") != null || getParameter("linkbk") != null)
                fsize = 35;
            for(int x = 0; x < ndata; x++)
            {
                int ax = pad.getFontMetrics(f2).stringWidth(label[x]);
                if(ax > fsize)
                    fsize = ax;
            }

            Maxx = size().width - fsize - 15;
        } else
        {
            int fsize = 0;
            if(getParameter("Gif") != null || getParameter("linkbk") != null)
                fsize = 35;
            for(int x = 0; x < ncdata; x++)
            {
                int ax = pad.getFontMetrics(f2).stringWidth(barnames[x]);
                if(ax > fsize)
                    fsize = ax;
            }

            Maxx = size().width - fsize;
        }
        Maxd = ndata;
        Maxc = ncdata;
        md = getMaxMulti(data);
        if(graphtype == 3)
            md = 90L;
        sd = getScale(md);
        mbs = getMaxBarScale(md, sd);
        sx = pad.getFontMetrics(f2).stringWidth(formatNum("" + mbs)) + 5;
        if(allpercent)
            sx += 10;
        if(percent)
            dr = (int)((double)(Maxx - sx - 40) / (double)Maxd);
        else
            dr = (int)((double)(Maxx - sx - 15) / (double)Maxd);
        if(percent)
            mps = getMaxPorcentScale();
        Title = getParameter("Title");
        if(getParameter("Title") != null)
            GifUrl += "&Title=" + getParameter("Title");
        SubTitle = getParameter("SubTitle");
        if(getParameter("SubTitle") != null)
            GifUrl += "&SubTitle=" + getParameter("SubTitle");
        DownTitle = getParameter("DownTitle");
        if(getParameter("DownTitle") != null)
            GifUrl += "&DownTitle=" + getParameter("DownTitle");
        else
            sy = 20;
    }

    public long getMax(long data[], int Maxd)
    {
        long mx = 0L;
        for(int x = 0; x < Maxd; x++)
            if(mx < data[x])
                mx = data[x];

        return mx;
    }

    public int getMaxPorcentScale()
    {
        long mx = 0L;
        int x;
        for(x = 0; x < Maxd; x++)
            if(mx < data[x][Maxc - 1])
                mx = data[x][Maxc - 1];

        for(x = 0; x < 100 && (long)x <= mx; x += 10);
        return x;
    }

    public long getMaxBarScale(long md, long sd)
    {
        long x;
        for(x = 0L; x < sd * 10L && x <= md; x += sd);
        return x;
    }

    public long getMaxMulti(long data[][])
    {
        long mx = 0L;
        for(int x = 0; x < data.length; x++)
        {
            for(int y = 0; y < data[x].length; y++)
                if(mx < data[x][y])
                    mx = data[x][y];

        }

        return mx;
    }

    public long getScale(long mx)
    {
        double n = 0.0D;
        long inc = 1L;
        int x = 0;
        do
        {
            n += inc;
            if(x == 9)
            {
                inc *= 10L;
                x = 0;
            }
            if(n > (double)mx)
                break;
            x++;
        } while(true);
        if(n < 10D)
            n = 10D;
        return (long)(n / 10D);
    }

    public void renderPie()
    {
        int x = 0;
        int y = 0;
        pad.setFont(f1);
        pad.setColor(Color.blue);
        if(Title != null)
            pad.drawString(Title, Maxx / 2 - pad.getFontMetrics().stringWidth(Title) / 2, 9);
        pad.setFont(f2);
        pad.setColor(Color.red);
        if(SubTitle != null)
            pad.drawString(SubTitle, Maxx / 2 - pad.getFontMetrics().stringWidth(SubTitle) / 2, 18);
        long tot = 0L;
        for(x = 0; x < Maxd; x++)
            tot += data[x][0];

        for(x = 0; x < Maxd; x++)
        {
            bar[x][0] = new Bar();
            if(label[x].startsWith("_"))
                bar[x][0].parent = label[x].substring(1);
            else
                bar[x][0].parent = label[x];
            bar[x][0].name = "" + (int)(((double)data[x][0] * 100D) / (double)tot + 0.5D);
            bar[x][0].name += "%";
            bar[x][0].value = formatNum("" + data[x][y]);
        }

        int cx = 30;
        int cy = 40;
        int rx = Maxx - 60;
        int ry = Maxy - 90;
        int sx;
        for(y = 0; y < cx; y++)
        {
            sx = 0;
            for(x = 0; x < Maxd; x++)
            {
                pad.setColor(colors[x].darker());
                int vx = (int)(((double)data[x][0] * 360D) / (double)tot + 0.5D);
                pad.drawArc(cx, (cy - y) + cx, rx, ry, sx, vx);
                if(y > cx - 3)
                {
                    pad.setColor(colors[x]);
                    pad.drawArc(cx, (cy - y) + cx, rx, ry, sx, vx);
                    pad.fillArc(cx, (cy - y) + cx, rx, ry, sx, vx);
                } else
                {
                    pad.drawArc(cx, (cy - y) + cx, rx, ry, sx, vx);
                }
                sx += vx;
            }

        }

        sx = 0;
        pad.setColor(Color.black);
        for(x = 0; x < Maxd; x++)
        {
            int vx = (int)(((double)data[x][0] * 360D) / (double)tot + 0.5D);
            int gx = (int)((double)(rx / 2) * Math.cos((3.1415926535897931D * (double)(sx + vx / 2)) / 180D));
            int gy = -(int)((double)(ry / 2) * Math.sin((3.1415926535897931D * (double)(sx + vx / 2)) / 180D));
            if(label[x].startsWith("_") && gx < 0)
                gx -= pad.getFontMetrics().stringWidth("%");
            else
            if(gx < 0)
                gx -= pad.getFontMetrics().stringWidth(label[x]);
            if(gx < 0)
                gx -= pad.getFontMetrics().stringWidth("%00");
            if(gy > 0)
                gy += cx + 8;
            if(!label[x].startsWith("_"))
                pad.drawString("" + label[x], cx + rx / 2 + gx, cy + ry / 2 + gy);
            if(gy > 0)
                gy += 10;
            else
                gy -= 10;
            pad.drawString("" + (int)(((double)data[x][0] * 100D) / (double)tot + 0.5D) + "%", cx + rx / 2 + gx, cy + ry / 2 + gy);
            sx += vx;
        }

        for(x = 0; x < Maxd; x++)
        {
            pad.setColor(colors[x]);
            pad.fillRect(Maxx, ((Maxy / 2 - (Maxd * 12) / 2) + (x + 1) * 12) - 9, 10, 10);
            pad.setColor(Color.black);
            pad.drawRect(Maxx, ((Maxy / 2 - (Maxd * 12) / 2) + (x + 1) * 12) - 9, 10, 10);
            if(label[x].startsWith("_"))
                pad.drawString(label[x].substring(1), Maxx + 15, (Maxy / 2 - (Maxd * 12) / 2) + (x + 1) * 12);
            else
                pad.drawString(label[x], Maxx + 15, (Maxy / 2 - (Maxd * 12) / 2) + (x + 1) * 12);
        }

        if(getParameter("linkbk") != null)
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
        if(getParameter("Gif") != null)
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
        pad.setColor(Color.white);
        pad.fillRect(0, 0, size().width, Maxy);
        long tot = 0L;
        for(int x = 0; x < Maxd; x++)
            tot += data[x][0];

        int cx = 30;
        int cy = 40;
        int rx = Maxx - 60;
        int ry = Maxy - 90;
        for(int y = 0; y < cx; y++)
        {
            int sx = 0;
            for(int x = 0; x < Maxd; x++)
            {
                pad.setColor(colors[x]);
                int vx = (int)(((double)data[x][0] * 360D) / (double)tot + 0.5D);
                pad.drawArc(cx, (cy - y) + cx, rx, ry, sx, vx);
                if(y > cx - 2)
                    pad.fillArc(cx, (cy - y) + cx, rx, ry, sx, vx);
                sx += vx;
            }

        }

        for(int x = 0; x < Maxd; x++)
        {
            pad.setColor(colors[x]);
            pad.fillRect(Maxx, ((Maxy / 2 - (Maxd * 12) / 2) + (x + 1) * 12) - 9, 10, 10);
        }

        PixelGrabber pg = new PixelGrabber(buffer, 0, 0, Width, Height, pixels, 0, Width);
        try
        {
            pg.grabPixels();
        }
        catch(InterruptedException e)
        {
            System.out.println("Error on class WBABar4Report method PixelGrabber:" + e);
        }
        rcolors = (Color[])colors.clone();
        for(int x = 0; x < Maxd; x++)
            rcolors[x] = new Color(pixels[(((Maxy / 2 - (Maxd * 12) / 2) + (x + 1) * 12) - 8) * Width + Maxx + 1]);

    }

    public void render()
    {
        if(graphtype == 2)
        {
            renderPie();
            return;
        }
        int x = 0;
        int y = 0;
        pad.setFont(f1);
        pad.setColor(Color.blue);
        if(Title != null)
            pad.drawString(Title, Maxx / 2 - pad.getFontMetrics().stringWidth(Title) / 2, 9);
        pad.setColor(Color.black);
        if(DownTitle != null)
            pad.drawString(DownTitle, Maxx / 2 - pad.getFontMetrics().stringWidth(DownTitle) / 2, Maxy - 10);
        pad.setFont(f2);
        pad.setColor(Color.red);
        if(SubTitle != null)
            pad.drawString(SubTitle, Maxx / 2 - pad.getFontMetrics().stringWidth(SubTitle) / 2, 18);
        pad.setColor(Color.black);
        pad.drawLine(sx - 2, Maxy - sy, Maxd * dr + sx + 2, Maxy - sy);
        for(x = 0; x < Maxd; x++)
        {
            if(!label[x].startsWith("_"))
            {
                String ax = null;
                String ax_aux = null;
                int i_filter = 0;
                ax_aux = label[x];
                i_filter = ax_aux.indexOf("-");
                if(i_filter != -1)
                    ax = ax_aux.substring(0, 1);
                else
                    ax = label[x];
                int l;
                if(brakelabels)
                {
                    ax_aux = label[x];
                    i_filter = ax_aux.indexOf("-");
                    if(i_filter != -1)
                        ax = ax_aux.substring(0, 1) + "-";
                    else
                        ax = label[x] + "-";
                    do
                    {
                        ax = ax.substring(0, ax.length() - 1);
                        l = pad.getFontMetrics().stringWidth(ax);
                    } while(l > dr);
                } else
                {
                    l = pad.getFontMetrics().stringWidth(ax);
                }
                pad.drawString(ax, (dr * x + dr / 2 + sx) - l / 2, (Maxy - sy) + 12);
            }
            pad.drawLine(dr * x + sx, Maxy - sy - 2, dr * x + sx, (Maxy - sy) + 2);
        }

        pad.drawLine(sx, sy, sx, (Maxy - sy) + 2);
        for(x = 0; (long)x <= mbs / sd; x++)
        {
            int h = (int)((long)(Maxy - sy) - ((long)(Maxy - 2 * sy) / (mbs / sd)) * (long)x);
            String sa = formatNum("" + (long)x * sd);
            if(allpercent)
                sa = sa + "%";
            pad.drawString(sa, sx - pad.getFontMetrics().stringWidth(sa) - 2, h + 4);
            pad.drawLine(sx - 2, h, sx + 2, h);
        }

        if(percent)
        {
            pad.drawLine(Maxd * dr + sx, sy, Maxd * dr + sx, (Maxy - sy) + 2);
            int es = 10;
            if(mps <= 50)
                es = 5;
            for(x = 0; x <= mps / es; x++)
            {
                int h = Maxy - sy - ((Maxy - 2 * sy) / (mps / es)) * x;
                String sa = "" + x * es + "%";
                pad.drawString(sa, sx + Maxd * dr + 3, h + 4);
                pad.drawLine((sx + Maxd * dr) - 2, h, sx + Maxd * dr + 2, h);
            }

        }
        int maxc = 0;
        if(percent)
            maxc = Maxc - 1;
        else
            maxc = Maxc;
        int sxd = (int)((double)dr * 0.10000000000000001D);
        int drd = (dr - 2 * sxd) / maxc;
        if(graphtype == 0)
        {
            for(x = 0; x < Maxd; x++)
                for(y = 0; y < maxc; y++)
                {
                    if(data[x][y] <= 0L)
                        continue;
                    bar[x][y] = new Bar();
                    bar[x][y].x = dr * x + sxd + drd * y + sx;
                    bar[x][y].y = (int)((long)(Maxy - sy) - (data[x][y] * (long)(Maxy - 2 * sy)) / (sd * (mbs / sd)));
                    bar[x][y].w = drd;
                    bar[x][y].h = (int)((data[x][y] * (long)(Maxy - 2 * sy)) / (sd * (mbs / sd)));
                    if(label[x].startsWith("_"))
                        bar[x][y].parent = label[x].substring(1);
                    else
                        bar[x][y].parent = label[x];
                    bar[x][y].name = barnames[y];
                    bar[x][y].value = formatNum("" + data[x][y]);
                    if(allpercent)
                        bar[x][y].value += "%";
                    if(maxc == 1)
                        pad.setColor(colors[x]);
                    else
                        pad.setColor(colors[y]);
                    pad.fill3DRect(bar[x][y].x, bar[x][y].y, bar[x][y].w, bar[x][y].h, true);
                }


            if(percent)
            {
                int oldx = (int)((double)dr * 0.5D + (double)sx);
                int oldy = (int)((long)(Maxy - sy) - (data[0][y] * (long)(Maxy - 2 * sy)) / (long)mps);
                for(x = 0; x < Maxd; x++)
                {
                    if(data[x][y] > 0L)
                    {
                        pad.setColor(colors[y]);
                        pad.drawLine(oldx, oldy, oldx = (int)((double)dr * ((double)x + 0.5D) + (double)sx), oldy = (int)((long)(Maxy - sy) - (data[x][y] * (long)(Maxy - 2 * sy)) / (long)mps));
                        pad.fillOval(oldx - 3, oldy - 3, 6, 6);
                        bar[x][y] = new Bar();
                        bar[x][y].x = oldx - 3;
                        bar[x][y].y = oldy - 3;
                        bar[x][y].w = 6;
                        bar[x][y].h = 6;
                        if(label[x].startsWith("_"))
                            bar[x][y].parent = label[x].substring(1);
                        else
                            bar[x][y].parent = label[x];
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = "" + data[x][y];
                        if(allpercent)
                            bar[x][y].value += "%";
                        continue;
                    }
                    if(x + 1 < Maxd)
                    {
                        oldx = (int)((double)dr * ((double)x + 1.5D) + (double)sx);
                        oldy = (int)((long)(Maxy - sy) - (data[x + 1][y] * (long)(Maxy - 2 * sy)) / (long)mps);
                    }
                }

            }
        }
        if(graphtype == 1)
        {
            for(y = 0; y < maxc; y++)
            {
                int oldx = (int)((double)dr * 0.5D + (double)sx);
                int oldy = (int)((long)(Maxy - sy) - (data[0][y] * (long)(Maxy - 2 * sy)) / mbs);
                for(x = 0; x < Maxd; x++)
                {
                    if(data[x][y] > 0L)
                    {
                        pad.setColor(colors[y]);
                        pad.drawLine(oldx, oldy, oldx = (int)((double)dr * ((double)x + 0.5D) + (double)sx), oldy = (int)((long)(Maxy - sy) - (data[x][y] * (long)(Maxy - 2 * sy)) / mbs));
                        pad.fillOval(oldx - 3, oldy - 3, 6, 6);
                        bar[x][y] = new Bar();
                        bar[x][y].x = oldx - 3;
                        bar[x][y].y = oldy - 3;
                        bar[x][y].w = 6;
                        bar[x][y].h = 6;
                        if(label[x].startsWith("_"))
                            bar[x][y].parent = label[x].substring(1);
                        else
                            bar[x][y].parent = label[x];
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = formatNum("" + data[x][y]);
                        if(allpercent)
                            bar[x][y].value += "%";
                        continue;
                    }
                    if(x + 1 < Maxd)
                    {
                        oldx = (int)((double)dr * ((double)x + 1.5D) + (double)sx);
                        oldy = (int)((long)(Maxy - sy) - (data[x + 1][y] * (long)(Maxy - 2 * sy)) / mbs);
                    }
                }

            }

            if(percent)
            {
                int oldx = (int)((double)dr * 0.5D + (double)sx);
                int oldy = (int)((long)(Maxy - sy) - (data[0][y] * (long)(Maxy - 2 * sy)) / (long)mps);
                for(x = 0; x < Maxd; x++)
                {
                    if(data[x][y] > 0L)
                    {
                        pad.setColor(colors[y]);
                        pad.drawLine(oldx, oldy, oldx = (int)((double)dr * ((double)x + 0.5D) + (double)sx), oldy = (int)((long)(Maxy - sy) - (data[x][y] * (long)(Maxy - 2 * sy)) / (long)mps));
                        pad.fillOval(oldx - 3, oldy - 3, 6, 6);
                        bar[x][y] = new Bar();
                        bar[x][y].x = oldx - 3;
                        bar[x][y].y = oldy - 3;
                        bar[x][y].w = 6;
                        bar[x][y].h = 6;
                        if(label[x].startsWith("_"))
                            bar[x][y].parent = label[x].substring(1);
                        else
                            bar[x][y].parent = label[x];
                        bar[x][y].name = barnames[y];
                        bar[x][y].value = "" + data[x][y];
                        if(allpercent)
                            bar[x][y].value += "%";
                        continue;
                    }
                    if(x + 1 < Maxd)
                    {
                        oldx = (int)((double)dr * ((double)x + 1.5D) + (double)sx);
                        oldy = (int)((long)(Maxy - sy) - (data[x + 1][y] * (long)(Maxy - 2 * sy)) / (long)mps);
                    }
                }

            }
        }
        if(graphtype == 3)
            for(x = 0; x < Maxd; x++)
            {
                int lasty = 0;
                for(y = 0; y < maxc; y++)
                {
                    if(data[x][y] <= 0L)
                        continue;
                    bar[x][y] = new Bar();
                    bar[x][y].x = dr * x + sxd + sx;
                    bar[x][y].y = (int)Math.round((double)Maxy - (double)sy - (double)((100 - lasty) * (Maxy - 2 * sy)) / (double)(sd * (mbs / sd)));
                    bar[x][y].w = drd * maxc;
                    bar[x][y].h = (int)Math.round((double)((data[x][y] - (long)lasty) * (long)(Maxy - 2 * sy)) / (double)(sd * (mbs / sd)));
                    if(label[x].startsWith("_"))
                        bar[x][y].parent = label[x].substring(1);
                    else
                        bar[x][y].parent = label[x];
                    bar[x][y].name = barnames[y];
                    bar[x][y].value = formatNum("" + (data[x][y] - (long)lasty));
                    if(allpercent)
                        bar[x][y].value += "%";
                    pad.setColor(colors[y]);
                    pad.fill3DRect(bar[x][y].x, bar[x][y].y, bar[x][y].w, bar[x][y].h, true);
                    lasty = (int)data[x][y];
                }

            }

        for(x = 0; x < Maxc; x++)
        {
            pad.setColor(colors[x]);
            pad.fillRect(Maxx - 15, ((Maxy / 2 - (Maxc * 20) / 2) + (x + 1) * 20) - 9, 10, 10);
            pad.setColor(Color.black);
            pad.drawRect(Maxx - 15, ((Maxy / 2 - (Maxc * 20) / 2) + (x + 1) * 20) - 9, 10, 10);
            pad.drawString(barnames[x], Maxx, (Maxy / 2 - (Maxc * 20) / 2) + (x + 1) * 20);
        }

        if(getParameter("linkbk") != null)
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
        if(getParameter("Gif") != null)
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
    private void initComponents() {//GEN-BEGIN:initComponents

            setLayout(new java.awt.BorderLayout());

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    mouseClick(evt);
                }
            });

            addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    formMouseMoved(evt);
                }
            });

        }//GEN-END:initComponents

    /*private void initComponents()
    {
        setLayout(new BorderLayout());
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt)
            {
                mouseClick(evt);
            }


            {
                super();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent evt)
            {
                formMouseMoved(evt);
            }


            {
                super();
            }
        });
    }*/

    private void formMouseMoved(MouseEvent evt)
    {
        boolean fl = false;
        boolean fl2 = false;
        boolean fl3 = false;
        int cx = 30;
        int cy = 40;
        int rx = Maxx - 60;
        int ry = Maxy - 90;
        long tot = 0L;
        for(int x = 0; x < Maxd; x++)
            tot += data[x][0];

        int sx = 0;
        for(int x = 0; x < Maxd; x++)
        {
            if(graphtype == 2)
            {
                int vx = (int)(((double)data[x][0] * 360D) / (double)tot + 0.5D);
                if(pixels[evt.getY() * Width + evt.getX()] == rcolors[x].getRGB())
                {
                    pad.setColor(Color.white);
                    pad.fillRect(0, 0, size().width, Maxy);
                    render();
                    pad.setColor(colors[x].darker());
                    for(int bx = -2; bx < 10; bx++)
                    {
                        pad.drawArc(cx, cy - bx, rx, ry, sx, vx);
                        pad.fillArc(cx, cy - bx, rx, ry, sx, vx);
                    }

                    pad.setColor(colors[x].brighter());
                    pad.fillArc(cx, cy - 10, rx, ry, sx, vx);
                    pad.setColor(Color.white);
                    int fh = pad.getFontMetrics().getHeight();
                    int mxf = Math.max(Math.max(pad.getFontMetrics().stringWidth(bar[x][0].value), pad.getFontMetrics().stringWidth(bar[x][0].name)), pad.getFontMetrics().stringWidth(bar[x][0].parent));
                    int dify = 0;
                    int difx = 0;
                    if(evt.getY() - fh * 3 - 5 < 0)
                    {
                        difx = -mxf - 10;
                        dify = -(evt.getY() - fh * 3 - 5);
                    }
                    if(evt.getX() + mxf + 10 > Width)
                        difx = -mxf - 10;
                    pad.fillRect(evt.getX() + difx, (evt.getY() - fh * 3 - 5) + dify, mxf + 10, fh * 3 + 5);
                    pad.setColor(Color.black);
                    pad.drawRect(evt.getX() + difx, (evt.getY() - fh * 3 - 5) + dify, mxf + 10, fh * 3 + 5);
                    pad.setColor(Color.blue);
                    pad.drawString(bar[x][0].value, evt.getX() + 5 + difx, (evt.getY() - 5) + dify);
                    pad.drawString(bar[x][0].name, evt.getX() + 5 + difx, (evt.getY() - fh - 5) + dify);
                    pad.drawString(bar[x][0].parent, evt.getX() + 5 + difx, (evt.getY() - fh * 2 - 5) + dify);
                    repaint();
                    fl = true;
                    if(actbarname == bar[x][0].parent)
                        enter = false;
                    String s_barfilter = bar[x][0].parent;
                    int i_filter = s_barfilter.indexOf("-");
                    if(i_filter != -1)
                    {
                        actbarname = s_barfilter.substring(0, i_filter);
                        actbarname_aux = s_barfilter.substring(i_filter + 1, s_barfilter.length());
                    } else
                    {
                        actbarname = s_barfilter;
                    }
                    actsubbarname = bar[x][0].name;
                }
                sx += vx;
                continue;
            }
            for(int y = 0; y < Maxc; y++)
            {
                if(bar[x][y] == null || !bar[x][y].inSide(evt.getX(), evt.getY()))
                    continue;
                String s_barfilter = bar[x][y].parent;
                int i_filter = s_barfilter.indexOf("-");
                if(i_filter != -1)
                {
                    actbarname = s_barfilter.substring(0, i_filter);
                    actbarname_aux = s_barfilter.substring(i_filter + 1, s_barfilter.length());
                } else
                {
                    actbarname = s_barfilter;
                }
                actsubbarname = bar[x][y].name;
                pad.setColor(Color.white);
                pad.fillRect(0, 0, size().width, Maxy);
                render();
                pad.setColor(Color.red);
                pad.drawRect(bar[x][y].x, bar[x][y].y, bar[x][y].w, bar[x][y].h);
                pad.setColor(Color.white);
                int fh = pad.getFontMetrics().getHeight();
                int mxf = Math.max(Math.max(pad.getFontMetrics().stringWidth(bar[x][y].value), pad.getFontMetrics().stringWidth(bar[x][y].name)), pad.getFontMetrics().stringWidth(bar[x][y].parent));
                int dify = 0;
                int difx = 0;
                if(evt.getY() - fh * 3 - 5 < 0)
                {
                    difx = -mxf - 10;
                    dify = -(evt.getY() - fh * 3 - 5);
                }
                pad.fillRect(evt.getX() + difx, (evt.getY() - fh * 3 - 5) + dify, mxf + 10, fh * 3 + 5);
                pad.setColor(Color.black);
                pad.drawRect(evt.getX() + difx, (evt.getY() - fh * 3 - 5) + dify, mxf + 10, fh * 3 + 5);
                pad.setColor(Color.blue);
                if(y == Maxc - 1 && percent)
                    pad.drawString(bar[x][y].value + "%", evt.getX() + 5 + difx, (evt.getY() - 5) + dify);
                else
                    pad.drawString(bar[x][y].value, evt.getX() + 5 + difx, (evt.getY() - 5) + dify);
                pad.drawString(bar[x][y].name, evt.getX() + 5 + difx, (evt.getY() - fh - 5) + dify);
                pad.drawString(bar[x][y].parent, evt.getX() + 5 + difx, (evt.getY() - fh * 2 - 5) + dify);
                repaint();
                fl = true;
            }

        }

        if(!enter && fl && !fl2 && !fl3)
        {
            enter = true;
            dir = null;
            if(getParameter("link") != null)
            {
                papa.setCursor(12);
                try
                {   // Este bloque se modifico para pasar el parametro a la grafica del detalle
                    String s_url = null;
                    if(actbarname_aux != null)
                        s_url = "&wbr_barname=" + actbarname + "&wbr_barnameaux=" + actbarname_aux;
                    else
                        s_url = "&wbr_barname=" + actbarname;
                    dir = new URL(getDocumentBase(), getParameter("link") + s_url);
                }
                catch(MalformedURLException e) { }
            }
        } else
        if(enter && !fl && !fl2 && !fl2)
        {
            enter = false;
            pad.setColor(Color.white);
            pad.fillRect(0, 0, Width, Maxy);
            papa.setCursor(0);
            render();
            repaint();
            dir = null;
        }
        if(but != null && but.inSide(evt.getX(), evt.getY()))
            fl2 = true;
        if(gif != null && gif.inSide(evt.getX(), evt.getY()))
            fl3 = true;
        if(!enter && fl2 && !fl && !fl3)
        {
            enter = true;
            dir = null;
            if(getParameter("linkbk") != null)
            {
                papa.setCursor(12);
                try
                {
                    dir = new URL(getDocumentBase(), getParameter("linkbk"));
                }
                catch(MalformedURLException e) { }
            }
        } else
        if(enter && !fl2 && !fl && !fl3)
        {
            enter = false;
            papa.setCursor(0);
            render();
            repaint();
            dir = null;
        }
        if(!enter && fl3 && !fl2 && !fl)
        {
            enter = true;
            dir = null;
            if(getParameter("Gif") != null)
            {
                papa.setCursor(12);
                try
                {
                    dir = new URL(getDocumentBase(), getParameter("Gif") + GifUrl);
                }
                catch(MalformedURLException e) { }
            }
        } else
        if(enter && !fl2 && !fl && !fl3)
        {
            enter = false;
            papa.setCursor(0);
            render();
            repaint();
            dir = null;
        }
    }

    private void mouseClick(MouseEvent evt)
    {
        if(enter && dir != null)
        {
            try
            {
                dir = new URL(dir.toString() + "&sub=" + actsubbarname);
            }
            catch(MalformedURLException e) { }
            if(target == null)
                apc.showDocument(dir);
            else
                apc.showDocument(dir, target);
        }
    }

    public void run()
    {
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
    }

    public void stop()
    {
        pad.dispose();
        buffer = null;
    }

    public String formatNum(String num)
    {
        String aux = "";
        int l = num.length();
        for(int x = 0; x < l; x++)
            if((l - x) % 3 == 0 && l - x != l)
                aux = aux + "," + num.substring(x, x + 1);
            else
                aux = aux + num.substring(x, x + 1);

        return aux;
    }

    Thread kicker;
    long nextTime;
    int Width;
    int Height;
    public Image buffer;
    int pixels[];
    Graphics pad;
    int Maxx;
    int Maxy;
    int Maxd;
    int Maxc;
    String Title;
    String SubTitle;
    String DownTitle;
    String label[];
    long data[][];
    Color colors[];
    Color rcolors[];
    String barnames[];
    boolean percent;
    boolean allpercent;
    boolean brakelabels;
    Font f1;
    Font f2;
    Bar bar[][];
    Bar but;
    Bar gif;
    String GifUrl;
    int graphtype;
    AppletContext apc;
    Frame papa;
    String target;
    URL dir;
    int sx;
    int sy;
    int dr;
    long md;
    long sd;
    long mbs;
    int mps;
    boolean enter;
    String actbarname;
    String actbarname_aux;
    String actsubbarname;


}
