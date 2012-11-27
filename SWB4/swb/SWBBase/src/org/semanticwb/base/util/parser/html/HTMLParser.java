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
package org.semanticwb.base.util.parser.html;

import java.io.*;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class HTMLParser.
 */
public class HTMLParser implements HTMLParserConstants {

    /** The SUMMAR y_ length. */
    public static int SUMMARY_LENGTH = 200;
    
    /** The title. */
    StringBuffer title = new StringBuffer(SUMMARY_LENGTH);
    
    /** The summary. */
    StringBuffer summary = new StringBuffer(SUMMARY_LENGTH * 2);
    
    /** The length. */
    int length = 0;
    
    /** The title complete. */
    boolean titleComplete = false;
    
    /** The in title. */
    boolean inTitle = false;
    
    /** The in script. */
    boolean inScript = false;
    
    /** The after tag. */
    boolean afterTag = false;
    
    /** The after space. */
    boolean afterSpace = false;
    
    /** The eol. */
    String eol = System.getProperty("line.separator");
    
    /** The pipe in. */
    PipedReader pipeIn = null;
    
    /** The pipe out. */
    PipedWriter pipeOut;

    /**
     * Instantiates a new hTML parser.
     * 
     * @param file the file
     * @throws FileNotFoundException the file not found exception
     */
    public HTMLParser(File file) throws FileNotFoundException
    {
        this(new FileInputStream(file));
    }

    /**
     * Gets the title.
     * 
     * @return the title
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public String getTitle() throws IOException, InterruptedException
    {
        if (pipeIn == null)
        {
            getReader();                                // spawn parsing thread
        }
        while (true)
        {
            synchronized (this)
            {
                if (titleComplete || (length > SUMMARY_LENGTH))
                {
                    break;
                }
                wait(10);
            }
        }
        return title.toString().trim();
    }

    /**
     * Gets the summary.
     * 
     * @return the summary
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public String getSummary() throws IOException, InterruptedException
    {
        if (pipeIn == null)
        {
            getReader();                                // spawn parsing thread
        }
        while (true)
        {
            synchronized (this)
            {
                if (summary.length() >= SUMMARY_LENGTH)
                {
                    break;
                }
                wait(10);
            }
        }
        if (summary.length() > SUMMARY_LENGTH)
        {
            summary.setLength(SUMMARY_LENGTH);
        }

        String sum = summary.toString().trim();
        String tit = getTitle();
        if (sum.startsWith(tit))
        {
            return sum.substring(tit.length());
        } else
        {
            return sum;
        }
    }

    /**
     * Gets the text.
     * 
     * @return the text
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public String getText() throws IOException, InterruptedException
    {
        String ret = null;
        ret = SWBUtils.IO.readReader(getReader());
        return ret;
    }

    /**
     * Gets the reader.
     * 
     * @return the reader
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Reader getReader() throws IOException
    {
        if (pipeIn == null)
        {
            pipeIn = new PipedReader();
            pipeOut = new PipedWriter(pipeIn);

            Thread thread = new ParserThread(this);
            thread.start();                             // start parsing
        }

        return pipeIn;
    }

    /**
     * Adds the to summary.
     * 
     * @param text the text
     */
    void addToSummary(String text)
    {
        if (summary.length() < SUMMARY_LENGTH)
        {
            summary.append(text);
            if (summary.length() >= SUMMARY_LENGTH)
            {
                synchronized (this)
                {
                    notifyAll();
                }
            }
        }
    }

    /**
     * Adds the text.
     * 
     * @param text the text
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void addText(String text) throws IOException
    {
        if (inScript)
        {
            return;
        }
        if (inTitle)
        {
            title.append(text);
        } else
        {
            addToSummary(text);
            if (!titleComplete && !title.equals(""))
            {  // finished title
                synchronized (this)
                {
                    titleComplete = true;                   // tell waiting threads
                    notifyAll();
                }
            }
        }

        length += text.length();
        pipeOut.write(text);

        afterSpace = false;
    }

    /**
     * Adds the space.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void addSpace() throws IOException
    {
        if (inScript)
        {
            return;
        }
        if (!afterSpace)
        {
            if (inTitle)
            {
                title.append(" ");
            } else
            {
                addToSummary(" ");
            }

            String space = afterTag ? eol : " ";
            length += space.length();
            pipeOut.write(space);
            afterSpace = true;
        }
    }

    /**
     * HTML document.
     * 
     * @throws ParseException the parse exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    final public void HTMLDocument() throws ParseException, IOException
    {
        Token t;
        label_1:
        while (true)
        {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
                case TagName:
                case DeclName:
                case Comment1:
                case Comment2:
                case Word:
                case Entity:
                case Space:
                case Punct:
                    break;
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
                case TagName:
                    Tag();
                    afterTag = true;
                    break;
                case DeclName:
                    t = Decl();
                    afterTag = true;
                    break;
                case Comment1:
                case Comment2:
                    CommentTag();
                    afterTag = true;
                    break;
                case Word:
                    t = jj_consume_token(Word);
                    addText(t.image);
                    afterTag = false;
                    break;
                case Entity:
                    t = jj_consume_token(Entity);
                    addText(Entities.decode(t.image));
                    afterTag = false;
                    break;
                case Punct:
                    t = jj_consume_token(Punct);
                    addText(t.image);
                    afterTag = false;
                    break;
                case Space:
                    jj_consume_token(Space);
                    addSpace();
                    afterTag = false;
                    break;
                default:
                    jj_la1[1] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(0);
    }

    /**
     * Tag.
     * 
     * @throws ParseException the parse exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    final public void Tag() throws ParseException, IOException
    {
        Token t1, t2;
        boolean inImg = false;
        t1 = jj_consume_token(TagName);
        inTitle = t1.image.equalsIgnoreCase("<title"); // keep track if in <TITLE>
        inImg = t1.image.equalsIgnoreCase("<img");    // keep track if in <IMG>
        if (inScript)
        {                               // keep track if in <SCRIPT>
            inScript = !t1.image.equalsIgnoreCase("</script");
        } else
        {
            inScript = t1.image.equalsIgnoreCase("<script");
        }
        label_2:
        while (true)
        {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
                case ArgName:
                    break;
                default:
                    jj_la1[2] = jj_gen;
                    break label_2;
            }
            t1 = jj_consume_token(ArgName);
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
                case ArgEquals:
                    jj_consume_token(ArgEquals);
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
                    {
                        case ArgValue:
                        case ArgQuote1:
                        case ArgQuote2:
                            t2 = ArgValue();
                            if (inImg && t1.image.equalsIgnoreCase("alt") && t2 != null)
                            {
                                addText("[" + t2.image + "]");
                            }
                            break;
                        default:
                            jj_la1[3] = jj_gen;

                    }
                    break;
                default:
                    jj_la1[4] = jj_gen;

            }
        }
        jj_consume_token(TagEnd);
    }

    /**
     * Arg value.
     * 
     * @return the token
     * @throws ParseException the parse exception
     */
    final public Token ArgValue() throws ParseException
    {
        Token t = null;
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
        {
            case ArgValue:
                t = jj_consume_token(ArgValue);
            {
                return t;
            }
//                break;
            default:
                jj_la1[5] = jj_gen;
                if (jj_2_1(2))
                {
                    jj_consume_token(ArgQuote1);
                    jj_consume_token(CloseQuote1);
                    {
                        return t;
                    }
                } else
                {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
                    {
                        case ArgQuote1:
                            jj_consume_token(ArgQuote1);
                            t = jj_consume_token(Quote1Text);
                            jj_consume_token(CloseQuote1);
                        {
                            return t;
                        }
//                            break;
                        default:
                            jj_la1[6] = jj_gen;
                            if (jj_2_2(2))
                            {
                                jj_consume_token(ArgQuote2);
                                jj_consume_token(CloseQuote2);
                                {
                                    return t;
                                }
                            } else
                            {
                                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
                                {
                                    case ArgQuote2:
                                        jj_consume_token(ArgQuote2);
                                        t = jj_consume_token(Quote2Text);
                                        jj_consume_token(CloseQuote2);
                                    {
                                        return t;
                                    }
//                                        break;
                                    default:
                                        jj_la1[7] = jj_gen;
                                        jj_consume_token(-1);
                                        throw new ParseException();
                                }
                            }
                    }
                }
        }
//        throw new Error("Missing return statement in function");
    }

    /**
     * Decl.
     * 
     * @return the token
     * @throws ParseException the parse exception
     */
    final public Token Decl() throws ParseException
    {
        Token t;
        t = jj_consume_token(DeclName);
        label_3:
        while (true)
        {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
                case ArgName:
                case ArgEquals:
                case ArgValue:
                case ArgQuote1:
                case ArgQuote2:

                    break;
                default:
                    jj_la1[8] = jj_gen;
                    break label_3;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
                case ArgName:
                    jj_consume_token(ArgName);
                    break;
                case ArgValue:
                case ArgQuote1:
                case ArgQuote2:
                    ArgValue();
                    break;
                case ArgEquals:
                    jj_consume_token(ArgEquals);
                    break;
                default:
                    jj_la1[9] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(TagEnd);
        {
            return t;
        }
        // throw new Error("Missing return statement in function");
    }

    /**
     * Comment tag.
     * 
     * @throws ParseException the parse exception
     */
    final public void CommentTag() throws ParseException
    {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
        {
            case Comment1:
                jj_consume_token(Comment1);
                label_4:
                while (true)
                {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
                    {
                        case CommentText1:

                            break;
                        default:
                            jj_la1[10] = jj_gen;
                            break label_4;
                    }
                    jj_consume_token(CommentText1);
                }
                jj_consume_token(CommentEnd1);
                break;
            case Comment2:
                jj_consume_token(Comment2);
                label_5:
                while (true)
                {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
                    {
                        case CommentText2:

                            break;
                        default:
                            jj_la1[11] = jj_gen;
                            break label_5;
                    }
                    jj_consume_token(CommentText2);
                }
                jj_consume_token(CommentEnd2);
                break;
            default:
                jj_la1[12] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    /**
     * Jj_2_1.
     * 
     * @param xla the xla
     * @return true, if successful
     */
    final private boolean jj_2_1(int xla)
    {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_1();
        jj_save(0, xla);
        return retval;
    }

    /**
     * Jj_2_2.
     * 
     * @param xla the xla
     * @return true, if successful
     */
    final private boolean jj_2_2(int xla)
    {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_2();
        jj_save(1, xla);
        return retval;
    }

    /**
     * Jj_3_1.
     * 
     * @return true, if successful
     */
    final private boolean jj_3_1()
    {
        if (jj_scan_token(ArgQuote1))
        {
            return true;
        }
        if (jj_la == 0 && jj_scanpos == jj_lastpos)
        {
            return false;
        }
        if (jj_scan_token(CloseQuote1))
        {
            return true;
        }
        if (jj_la == 0 && jj_scanpos == jj_lastpos)
        {
            return false;
        }
        return false;
    }

    /**
     * Jj_3_2.
     * 
     * @return true, if successful
     */
    final private boolean jj_3_2()
    {
        if (jj_scan_token(ArgQuote2))
        {
            return true;
        }
        if (jj_la == 0 && jj_scanpos == jj_lastpos)
        {
            return false;
        }
        if (jj_scan_token(CloseQuote2))
        {
            return true;
        }
        if (jj_la == 0 && jj_scanpos == jj_lastpos)
        {
            return false;
        }
        return false;
    }
    
    /** The token_source. */
    public HTMLParserTokenManager token_source;
    
    /** The jj_input_stream. */
    SimpleCharStream jj_input_stream;
    
    /** The jj_nt. */
    public Token token, jj_nt;
    
    /** The jj_ntk. */
    private int jj_ntk;
    
    /** The jj_lastpos. */
    private Token jj_scanpos, jj_lastpos;
    
    /** The jj_la. */
    private int jj_la;
    
    /** The looking ahead. */
    public boolean lookingAhead = false;
    //private boolean jj_semLA;
    /** The jj_gen. */
    private int jj_gen;
    
    /** The jj_la1. */
    final private int[] jj_la1 = new int[13];
    
    /** The jj_la1_0. */
    static private int[] jj_la1_0;

    static
    {
        jj_la1_0();
    }

    /**
     * Jj_la1_0.
     */
    private static void jj_la1_0()
    {
        jj_la1_0 = new int[]
                {
                    0xb3e, 0xb3e, 0x1000, 0x38000, 0x2000, 0x8000, 0x10000, 0x20000, 0x3b000, 0x3b000, 0x800000, 0x2000000, 0x18,
                };
    }
    
    /** The jj_2_rtns. */
    final private JJCalls[] jj_2_rtns = new JJCalls[2];
    
    /** The jj_rescan. */
    private boolean jj_rescan = false;
    
    /** The jj_gc. */
    private int jj_gc = 0;

    /**
     * Instantiates a new hTML parser.
     * 
     * @param stream the stream
     */
    public HTMLParser(java.io.InputStream stream)
    {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new HTMLParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 13; i++)
        {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /**
     * Re init.
     * 
     * @param stream the stream
     */
    public void ReInit(java.io.InputStream stream)
    {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 13; i++)
        {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /**
     * Instantiates a new hTML parser.
     * 
     * @param stream the stream
     */
    public HTMLParser(java.io.Reader stream)
    {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new HTMLParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 13; i++)
        {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /**
     * Re init.
     * 
     * @param stream the stream
     */
    public void ReInit(java.io.Reader stream)
    {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 13; i++)
        {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /**
     * Instantiates a new hTML parser.
     * 
     * @param tm the tm
     */
    public HTMLParser(HTMLParserTokenManager tm)
    {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 13; i++)
        {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /**
     * Re init.
     * 
     * @param tm the tm
     */
    public void ReInit(HTMLParserTokenManager tm)
    {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 13; i++)
        {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /**
     * Jj_consume_token.
     * 
     * @param kind the kind
     * @return the token
     * @throws ParseException the parse exception
     */
    final private Token jj_consume_token(int kind) throws ParseException
    {
        Token oldToken;
        if ((oldToken = token).next != null)
        {
            token = token.next;
        } else
        {
            token = token.next = token_source.getNextToken();
        }
        jj_ntk = -1;
        if (token.kind == kind)
        {
            jj_gen++;
            if (++jj_gc > 100)
            {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++)
                {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null)
                    {
                        if (c.gen < jj_gen)
                        {
                            c.first = null;
                        }
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    /**
     * Jj_scan_token.
     * 
     * @param kind the kind
     * @return true, if successful
     */
    final private boolean jj_scan_token(int kind)
    {
        if (jj_scanpos == jj_lastpos)
        {
            jj_la--;
            if (jj_scanpos.next == null)
            {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else
            {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else
        {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan)
        {
            int i = 0;
            Token tok = token;
            while (tok != null && tok != jj_scanpos)
            {
                i++;
                tok = tok.next;
            }
            if (tok != null)
            {
                jj_add_error_token(kind, i);
            }
        }
        return (jj_scanpos.kind != kind);
    }

    /**
     * Gets the next token.
     * 
     * @return the next token
     */
    final public Token getNextToken()
    {
        if (token.next != null)
        {
            token = token.next;
        } else
        {
            token = token.next = token_source.getNextToken();
        }
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /**
     * Gets the token.
     * 
     * @param index the index
     * @return the token
     */
    final public Token getToken(int index)
    {
        Token t = lookingAhead ? jj_scanpos : token;
        for (int i = 0; i < index; i++)
        {
            if (t.next != null)
            {
                t = t.next;
            } else
            {
                t = t.next = token_source.getNextToken();
            }
        }
        return t;
    }

    /**
     * Jj_ntk.
     * 
     * @return the int
     */
    final private int jj_ntk()
    {
        if ((jj_nt = token.next) == null)
        {
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        } else
        {
            return (jj_ntk = jj_nt.kind);
        }
    }
    
    /** The jj_expentries. */
    private java.util.Vector jj_expentries = new java.util.Vector();
    
    /** The jj_expentry. */
    private int[] jj_expentry;
    
    /** The jj_kind. */
    private int jj_kind = -1;
    
    /** The jj_lasttokens. */
    private int[] jj_lasttokens = new int[100];
    
    /** The jj_endpos. */
    private int jj_endpos;

    /**
     * Jj_add_error_token.
     * 
     * @param kind the kind
     * @param pos the pos
     */
    private void jj_add_error_token(int kind, int pos)
    {
        if (pos >= 100)
        {
            return;
        }
        if (pos == jj_endpos + 1)
        {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0)
        {
            jj_expentry = new int[jj_endpos];
            for (int i = 0; i < jj_endpos; i++)
            {
                jj_expentry[i] = jj_lasttokens[i];
            }
            boolean exists = false;
            for (java.util.Enumeration enumn = jj_expentries.elements(); enumn.hasMoreElements();)
            {
                int[] oldentry = (int[]) enumn.nextElement();
                if (oldentry.length == jj_expentry.length)
                {
                    exists = true;
                    for (int i = 0; i < jj_expentry.length; i++)
                    {
                        if (oldentry[i] != jj_expentry[i])
                        {
                            exists = false;
                            break;
                        }
                    }
                    if (exists)
                    {
                        break;
                    }
                }
            }
            if (!exists)
            {
                jj_expentries.addElement(jj_expentry);
            }
            if (pos != 0)
            {
                jj_lasttokens[(jj_endpos = pos) - 1] = kind;
            }
        }
    }

    /**
     * Generate parse exception.
     * 
     * @return the parses the exception
     */
    public ParseException generateParseException()
    {
        jj_expentries.removeAllElements();
        boolean[] la1tokens = new boolean[27];
        for (int i = 0; i < 27; i++)
        {
            la1tokens[i] = false;
        }
        if (jj_kind >= 0)
        {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 13; i++)
        {
            if (jj_la1[i] == jj_gen)
            {
                for (int j = 0; j < 32; j++)
                {
                    if ((jj_la1_0[i] & (1 << j)) != 0)
                    {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 27; i++)
        {
            if (la1tokens[i])
            {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.addElement(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++)
        {
            exptokseq[i] = (int[]) jj_expentries.elementAt(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    /**
     * Enable_tracing.
     */
    final public void enable_tracing()
    {
    }

    /**
     * Disable_tracing.
     */
    final public void disable_tracing()
    {
    }

    /**
     * Jj_rescan_token.
     */
    final private void jj_rescan_token()
    {
        jj_rescan = true;
        for (int i = 0; i < 2; i++)
        {
            JJCalls p = jj_2_rtns[i];
            do
            {
                if (p.gen > jj_gen)
                {
                    jj_la = p.arg;
                    jj_lastpos = jj_scanpos = p.first;
                    switch (i)
                    {
                        case 0:
                            jj_3_1();
                            break;
                        case 1:
                            jj_3_2();
                            break;
                    }
                }
                p = p.next;
            } while (p != null);
        }
        jj_rescan = false;
    }

    /**
     * Jj_save.
     * 
     * @param index the index
     * @param xla the xla
     */
    final private void jj_save(int index, int xla)
    {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen)
        {
            if (p.next == null)
            {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = jj_gen + xla - jj_la;
        p.first = token;
        p.arg = xla;
    }

    /**
     * The Class JJCalls.
     */
    static final class JJCalls {

        /** The gen. */
        int gen;
        
        /** The first. */
        Token first;
        
        /** The arg. */
        int arg;
        
        /** The next. */
        JJCalls next;
    }
//    void handleException(Exception e) {
//      System.out.println(e.toString());  // print the error message
//      System.out.println("Skipping...");
//      Token t;
//      do {
//        t = getNextToken();
//      } while (t.kind != TagEnd);
//    }
}
