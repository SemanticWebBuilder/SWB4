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
/*
 * ftp.java
 *
 * Created on 11 de noviembre de 2004, 10:00 AM
 */
package applets.ftp;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;
import java.awt.*;

import applets.commons.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;

/**
 * Formulario que presneta dirtectorios y archivos existentes en el servidor, así
 * da las opciones para enviar archivos, descargar archivos, renombrar, eliminar y
 * dar información de los archivos existentes.
 * @author Victor Lorenzana
 */
public class ftpPanel extends javax.swing.JPanel implements ListSelectionListener, FileListener, DropTargetListener
{

    private Object container;
    public static java.io.File pathDir;
    public String cgiPath = "/gtw.jsp";
    public String jsess = "";
    private URL url = null;
    public String[] choices = new String[4];
    public Locale locale;
    private URL urldownload;
    private URL urlupload;
    private String pathInit;
    private JApplet applet;
    private String ContextPath;
    private String ApplicationPath;

    public ftpPanel(String jsess, Locale locale, URL urlupload, URL urldownload, URL url, String pathInit, Object container, JApplet applet, String ContextPath, String ApplicationPath)
    {
        super();
        this.jsess = jsess;
        this.locale = locale;
        this.urlupload = urlupload;
        this.container = container;
        this.urldownload = urldownload;
        this.url = url;
        this.pathInit = pathInit;
        this.applet = applet;
        this.ContextPath = ContextPath;
        this.ApplicationPath = ApplicationPath;
        initComponents();

        choices[0] = java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("si");
        choices[1] = java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("si_todo");
        choices[2] = java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no");
        choices[3] = java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("cancel");


        try
        {
            jFileChooser1.setLocale(locale);
        }
        catch (Exception e)
        {
            //e.printStackTrace(System.out);
        }
        jTreeDirs.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("")));
        this.jTableFiles.setDefaultRenderer(JLabel.class, new TableFileRender());
        this.jTableFiles.getSelectionModel().addListSelectionListener(this);
        jTableFiles.setCellSelectionEnabled(false);
        jTableFiles.setRowSelectionAllowed(true);

        this.setJMenuBar(this.jMenuBar1);
        TableCellRenderHeader tableCellRenderHeader = new TableCellRenderHeader(jTableFiles);
        this.jTreeDirs.setCellRenderer(new DirectoryRenderer(this.jTableFiles));
        jTableFileModel filemodel = new jTableFileModel(this.jTableFiles, locale, tableCellRenderHeader);
        this.jTableFiles.setModel(filemodel);
        jTableFiles.setDragEnabled(false);
        JTableHeader header = jTableFiles.getTableHeader();
        header.setDefaultRenderer(tableCellRenderHeader);
        header.addMouseListener(new ColumnHeaderListener(jTableFiles));
        loadDirectories();

        java.awt.dnd.DropTarget dt = new DropTarget(jTreeDirs, this);
        java.awt.dnd.DropTarget dt2 = new DropTarget(jScrollPane2, this);
    }

    /** Initializes the applet ftp */
    /** Initializes the applet HtmlEditor */
//    @Override
//    public void init()
//    {
//        Thread t=new Thread()
//        {
//            @Override
//            public void run()
//            {
//                init2();
//            }
//        };
//        t.start();
//    }
    public void loadDirectories(Directory odir)
    {
        odir.removeAllChildren();
        String path = odir.getDirectory();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd><path>" + path + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        try
        {
            ArrayList<Directory> directories = new ArrayList<Directory>();
            if (enode != null && enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
            {
                WBTreeNode dir = enode.getFirstNode().getFirstNode();
                if (dir.getName().equals("dir"))
                {
                    Iterator it = dir.getNodes().iterator();
                    while (it.hasNext())
                    {
                        dir = (WBTreeNode) it.next();
                        if (dir != null && dir.getName().equals("dir"))
                        {
                            Directory child = new Directory(dir.getAttribute("name"), dir.getAttribute("path"), dir.getAttribute("hasChild"));
                            directories.add(child);
                        }
                    }
                    NameDirectoryCompartor c = new NameDirectoryCompartor();
                    Collections.sort(directories, c);
                    for (Directory child : directories)
                    {
                        odir.add(child);
                        if (child.haschilds.equals("true"))
                        {
                            child.add(new DefaultMutableTreeNode(""));
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadDirectories(WBTreeNode dir, Directory root)
    {
        ArrayList<Directory> directories = new ArrayList<Directory>();
        Iterator it = dir.getNodes().iterator();
        while (it.hasNext())
        {
            WBTreeNode enode = (WBTreeNode) it.next();
            if (enode != null && enode.getName().equals("dir"))
            {
                Directory child = new Directory(enode.getAttribute("name"), enode.getAttribute("path"), enode.getAttribute("hasChild"));
                directories.add(child);
            }
        }
        Collections.sort(directories, new NameDirectoryCompartor());
        for (Directory child : directories)
        {
            root.add(child);
            if (child.haschilds.equals("true"))
            {
                child.add(new DefaultMutableTreeNode(""));
            }
        }


    }

    public boolean hasPermission(String path)
    {
        boolean hasPermission = false;

        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>hasPermissionFile</cmd><path>" + path + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        try
        {
            if (enode != null && enode.getFirstNode() != null)
            {
                WBTreeNode node = enode.getFirstNode().getNodebyName("dir");
                if (node != null && node.getAttribute("permission") != null)
                {
                    hasPermission = Boolean.valueOf(node.getAttribute("permission"));
                    return hasPermission;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return hasPermission;
    }

    public boolean hasPermission(File file)
    {
        return hasPermission(file.getPath());
    }

    public void loadFiles(Directory dir)
    {
        String path = dir.getDirectory();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>ftp.getFiles</cmd><path>" + path + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null)
        {
            TableCellRenderHeader tableCellRenderHeader = new TableCellRenderHeader(jTableFiles);
            jTableFileModel model = new jTableFileModel(this.jTableFiles, locale, tableCellRenderHeader);
            jTableFiles.getTableHeader().setDefaultRenderer(tableCellRenderHeader);
            jTableFiles.getTableHeader().addMouseListener(new ColumnHeaderListener(jTableFiles));
            model.addFileListener(this);
            this.jTableFiles.setModel(model);
            Iterator files = enode.getFirstNode().getNodes().iterator();
            ArrayList<applets.ftp.File> lfiles = new ArrayList<applets.ftp.File>();
            while (files.hasNext())
            {
                WBTreeNode file = (WBTreeNode) files.next();
                applets.ftp.File ofile = new applets.ftp.File(dir, file.getAttribute("name"), file.getAttribute("path"), file.getAttribute("size"), file.getAttribute("lastupdate"));
                lfiles.add(ofile);
            }
            FileNameCompartor fc = new FileNameCompartor();
            fc.toogle();
            Collections.sort(lfiles, fc);
            tableCellRenderHeader.onNewOrder(0, true);
            for (applets.ftp.File ofile : lfiles)
            {
                model.addFile(ofile);
            }
            this.jTableFiles.updateUI();
        }
    }

    private void loadDirectories()
    {
        String xml = null;
        if (pathInit == null)
        {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd></req>";
        }
        else
        {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd><path>" + pathInit + "</path></req>";
        }
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        try
        {
            WBTreeNode enode = parser.parse(respxml);
            if (enode != null && enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
            {
                WBTreeNode dir = enode.getFirstNode().getFirstNode();
                if (dir.getName().equals("dir"))
                {
                    Directory root = new Directory(dir.getAttribute("name"), dir.getAttribute("path"), "false");
                    jTreeDirs.setModel(new DefaultTreeModel(root));
                    loadDirectories(dir, root);
                    loadFiles(root);
                    this.jTreeDirs.expandRow(0);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getData(String xml)
    {

        StringBuilder ret = new StringBuilder();
        try
        {

            URLConnection con = url.openConnection();
            con.setUseCaches(false);
            if (jsess != null)
            {
                con.setRequestProperty("Cookie", "JSESSIONID=" + jsess);
            }
            con.setRequestProperty("Content-Type", "application/xml");
            con.addRequestProperty("FTP", "true");
            con.setDoOutput(true);
            PrintWriter pout = new PrintWriter(con.getOutputStream());
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
            }
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuDir = new javax.swing.JPopupMenu();
        jMenuItemNewFolder = new javax.swing.JMenuItem();
        jMenuDirAdd = new javax.swing.JMenu();
        jMenuItemDirAddFile = new javax.swing.JMenuItem();
        jMenuItemDirAddFolder = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItemDownloadDir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemDirRename = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemDirDelete = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemCrearDirectorio = new javax.swing.JMenuItem();
        jMenuAdd = new javax.swing.JMenu();
        jMenuItemAddFile = new javax.swing.JMenuItem();
        jMenuItemAddFolder = new javax.swing.JMenuItem();
        jMenuBorrar = new javax.swing.JMenu();
        jMenuItemBorrarDir = new javax.swing.JMenuItem();
        jMenuItemBorrarFile = new javax.swing.JMenuItem();
        jMenuTools = new javax.swing.JMenu();
        jMenuPreview = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItemDownload = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuDownloadDir = new javax.swing.JMenuItem();
        jMenuRename = new javax.swing.JMenu();
        jMenuItemRenameFolder = new javax.swing.JMenuItem();
        jMenuItemRenameFile = new javax.swing.JMenuItem();
        jPopupMenuFile = new javax.swing.JPopupMenu();
        jMenuItemPreview = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuFileAdd = new javax.swing.JMenu();
        jMenuItemFileAddFile = new javax.swing.JMenuItem();
        jMenuItemFileAddDir = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItemFileDownload = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        jMenuItemFileRename = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItemFileDelete = new javax.swing.JMenuItem();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanelToolbar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddFile = new javax.swing.JButton();
        jButtonaddFolder = new javax.swing.JButton();
        jButtonNewFolder = new javax.swing.JButton();
        jButtonDownload = new javax.swing.JButton();
        jPanelProgressBar = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanelContent = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeDirs = new JDropTree(this);
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new ScroollPanel(this);
        jTableFiles = new javax.swing.JTable();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale); // NOI18N
        jMenuItemNewFolder.setText(bundle.getString("new_folder")); // NOI18N
        jMenuItemNewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewFolderActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemNewFolder);

        jMenuDirAdd.setText(bundle.getString("add")); // NOI18N

        jMenuItemDirAddFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemDirAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirAddFileActionPerformed(evt);
            }
        });
        jMenuDirAdd.add(jMenuItemDirAddFile);

        jMenuItemDirAddFolder.setText(bundle.getString("folder")); // NOI18N
        jMenuItemDirAddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirAddFolderActionPerformed(evt);
            }
        });
        jMenuDirAdd.add(jMenuItemDirAddFolder);

        jPopupMenuDir.add(jMenuDirAdd);
        jPopupMenuDir.add(jSeparator6);

        jMenuItemDownloadDir.setText(bundle.getString("downloaddir"));
        jMenuItemDownloadDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownloadDirActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemDownloadDir);
        jPopupMenuDir.add(jSeparator1);

        jMenuItemDirRename.setText(bundle.getString("rename")); // NOI18N
        jMenuItemDirRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirRenameActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemDirRename);
        jPopupMenuDir.add(jSeparator2);

        jMenuItemDirDelete.setText(bundle.getString("del")); // NOI18N
        jMenuItemDirDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirDeleteActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemDirDelete);

        jMenuBar1.setBackground(new java.awt.Color(213, 227, 248));

        jMenuFile.setBackground(new java.awt.Color(213, 227, 248));
        jMenuFile.setText(bundle.getString("file")); // NOI18N

        jMenuItemCrearDirectorio.setText(bundle.getString("new_folder")); // NOI18N
        jMenuItemCrearDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCrearDirectorioActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemCrearDirectorio);

        jMenuAdd.setText(bundle.getString("add")); // NOI18N

        jMenuItemAddFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddFileActionPerformed(evt);
            }
        });
        jMenuAdd.add(jMenuItemAddFile);

        jMenuItemAddFolder.setText(bundle.getString("folder")); // NOI18N
        jMenuItemAddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddFolderActionPerformed(evt);
            }
        });
        jMenuAdd.add(jMenuItemAddFolder);

        jMenuFile.add(jMenuAdd);

        jMenuBorrar.setText(bundle.getString("del")); // NOI18N

        jMenuItemBorrarDir.setText(bundle.getString("folder")); // NOI18N
        jMenuItemBorrarDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarDirActionPerformed(evt);
            }
        });
        jMenuBorrar.add(jMenuItemBorrarDir);

        jMenuItemBorrarFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemBorrarFile.setEnabled(false);
        jMenuItemBorrarFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarFileActionPerformed(evt);
            }
        });
        jMenuBorrar.add(jMenuItemBorrarFile);

        jMenuFile.add(jMenuBorrar);

        jMenuBar1.add(jMenuFile);

        jMenuTools.setBackground(new java.awt.Color(213, 227, 248));
        jMenuTools.setText(bundle.getString("tools")); // NOI18N

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("applets/ftp/ftp"); // NOI18N
        jMenuPreview.setText(bundle1.getString("preview")); // NOI18N
        jMenuPreview.setEnabled(false);
        jMenuPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPreviewActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuPreview);
        jMenuTools.add(jSeparator8);

        jMenuItemDownload.setText(bundle.getString("download")); // NOI18N
        jMenuItemDownload.setEnabled(false);
        jMenuItemDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownloadActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemDownload);
        jMenuTools.add(jSeparator7);

        jMenuDownloadDir.setText(bundle1.getString("downloaddir")); // NOI18N
        jMenuDownloadDir.setEnabled(false);
        jMenuDownloadDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDownloadDirActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuDownloadDir);

        jMenuRename.setText(bundle.getString("rename")); // NOI18N

        jMenuItemRenameFolder.setText(bundle.getString("folder")); // NOI18N
        jMenuItemRenameFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRenameFolderActionPerformed(evt);
            }
        });
        jMenuRename.add(jMenuItemRenameFolder);

        jMenuItemRenameFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemRenameFile.setEnabled(false);
        jMenuItemRenameFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRenameFileActionPerformed(evt);
            }
        });
        jMenuRename.add(jMenuItemRenameFile);

        jMenuTools.add(jMenuRename);

        jMenuBar1.add(jMenuTools);

        jMenuItemPreview.setText(bundle1.getString("preview")); // NOI18N
        jMenuItemPreview.setEnabled(false);
        jMenuItemPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreviewActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemPreview);
        jPopupMenuFile.add(jSeparator9);

        jMenuFileAdd.setText(bundle.getString("add")); // NOI18N

        jMenuItemFileAddFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemFileAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileAddFileActionPerformed(evt);
            }
        });
        jMenuFileAdd.add(jMenuItemFileAddFile);

        jMenuItemFileAddDir.setText(bundle.getString("folder")); // NOI18N
        jMenuItemFileAddDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileAddDirActionPerformed(evt);
            }
        });
        jMenuFileAdd.add(jMenuItemFileAddDir);

        jPopupMenuFile.add(jMenuFileAdd);
        jPopupMenuFile.add(jSeparator3);

        jMenuItemFileDownload.setText(bundle.getString("download")); // NOI18N
        jMenuItemFileDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileDownloadActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemFileDownload);
        jPopupMenuFile.add(jSeparator5);

        jMenuItemFileRename.setText(bundle.getString("rename")); // NOI18N
        jMenuItemFileRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileRenameActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemFileRename);
        jPopupMenuFile.add(jSeparator4);

        jMenuItemFileDelete.setText(bundle.getString("del")); // NOI18N
        jMenuItemFileDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileDeleteActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemFileDelete);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanelToolbar.setBackground(new java.awt.Color(225, 235, 251));
        jPanelToolbar.setPreferredSize(new java.awt.Dimension(10, 30));
        jPanelToolbar.setLayout(new java.awt.BorderLayout());

        jToolBar1.setBackground(new java.awt.Color(225, 235, 251));
        jToolBar1.setFloatable(false);

        jButtonAddFile.setBackground(new java.awt.Color(213, 227, 255));
        jButtonAddFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/archivonuevo2.gif"))); // NOI18N
        jButtonAddFile.setToolTipText(bundle.getString("add_files")); // NOI18N
        jButtonAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddFileActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAddFile);

        jButtonaddFolder.setBackground(new java.awt.Color(213, 227, 255));
        jButtonaddFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/ftpupfolder.gif"))); // NOI18N
        jButtonaddFolder.setToolTipText(bundle.getString("menu_add_folder")); // NOI18N
        jButtonaddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonaddFolderActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonaddFolder);

        jButtonNewFolder.setBackground(new java.awt.Color(213, 227, 255));
        jButtonNewFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/nuevofolder2.gif"))); // NOI18N
        jButtonNewFolder.setToolTipText(bundle.getString("new_folder")); // NOI18N
        jButtonNewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewFolderActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonNewFolder);

        jButtonDownload.setBackground(new java.awt.Color(213, 227, 255));
        jButtonDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/archivodown2.gif"))); // NOI18N
        jButtonDownload.setToolTipText(bundle.getString("download")); // NOI18N
        jButtonDownload.setEnabled(false);
        jButtonDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownloadActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonDownload);

        jPanelToolbar.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanelToolbar, java.awt.BorderLayout.NORTH);

        jPanelProgressBar.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanelProgressBar.setLayout(new java.awt.BorderLayout());
        jPanelProgressBar.add(jProgressBar1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanelProgressBar, java.awt.BorderLayout.SOUTH);

        jPanelContent.setBackground(new java.awt.Color(225, 235, 251));
        jPanelContent.setLayout(new java.awt.GridLayout(0, 2));

        jPanel3.setLayout(new java.awt.BorderLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeDirs.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeDirs.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeDirsTreeWillExpand(evt);
            }
        });
        jTreeDirs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTreeDirsMousePressed(evt);
            }
        });
        jTreeDirs.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeDirsValueChanged(evt);
            }
        });
        jTreeDirs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTreeDirsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeDirs);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelContent.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(225, 235, 251));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(225, 235, 251));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(225, 235, 251));
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane2MousePressed(evt);
            }
        });
        jScrollPane2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseDragged(evt);
            }
        });

        jTableFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableFilesMousePressed(evt);
            }
        });
        jTableFiles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableFilesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableFiles);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanelContent.add(jPanel4);

        jPanel1.add(jPanelContent, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane2MousePressed(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_jScrollPane2MousePressed
        this.jMenuItemFileDownload.setEnabled(false);
        this.jMenuItemFileRename.setEnabled(false);
        this.jMenuItemFileDelete.setEnabled(false);
        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1)
        {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try
            {
                if (this.jTableFiles.getSelectedRowCount() > 0 && this.jTableFiles.getModel().getRowCount() > 0)
                {
                    jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
                    File file = model.getFile(jTableFiles.getSelectedRow());
                    if (hasPermission(file))
                    {
                        this.jMenuItemFileDownload.setEnabled(true);
                        this.jMenuItemFileRename.setEnabled(true);
                        this.jMenuItemFileDelete.setEnabled(true);


                    }
                }
                this.jPopupMenuFile.show(this.jScrollPane2, evt.getX(), evt.getY());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_jScrollPane2MousePressed

    private void jButtonDownloadActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jButtonDownloadActionPerformed
        downloadFile();
    }//GEN-LAST:event_jButtonDownloadActionPerformed

    private void jButtonNewFolderActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jButtonNewFolderActionPerformed
        this.createDir();
    }//GEN-LAST:event_jButtonNewFolderActionPerformed

    private void jButtonaddFolderActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jButtonaddFolderActionPerformed
        this.addDir();
    }//GEN-LAST:event_jButtonaddFolderActionPerformed

    private void jButtonAddFileActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jButtonAddFileActionPerformed
        this.addFile();
    }//GEN-LAST:event_jButtonAddFileActionPerformed

    private void jMenuItemAddFileActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemAddFileActionPerformed
        this.addFile();
    }//GEN-LAST:event_jMenuItemAddFileActionPerformed

    private void jMenuItemDownloadActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemDownloadActionPerformed
        jMenuItemFileDownloadActionPerformed(null);
    }//GEN-LAST:event_jMenuItemDownloadActionPerformed

    private void jMenuItemNewFolderActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemNewFolderActionPerformed
        this.createDir();
    }//GEN-LAST:event_jMenuItemNewFolderActionPerformed

    private void jMenuItemCrearDirectorioActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemCrearDirectorioActionPerformed
        this.createDir();
    }//GEN-LAST:event_jMenuItemCrearDirectorioActionPerformed

    private void downloadFile()
    {
        if (this.jTableFiles.getSelectedRowCount() > 0)
        {
            jTableFileModel filemodel = (jTableFileModel) this.jTableFiles.getModel();
            this.jFileChooser1 = new JFileChooser();
            if (pathDir != null)
            {
                this.jFileChooser1.setCurrentDirectory(pathDir);
            }
            this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("download"));
            this.jFileChooser1.setMultiSelectionEnabled(false);
            this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = this.jFileChooser1.showSaveDialog(this);
            ftpPanel.pathDir = this.jFileChooser1.getCurrentDirectory();
            if (ret == JFileChooser.APPROVE_OPTION)
            {
                try
                {

                    java.io.File dir = this.jFileChooser1.getSelectedFile();
                    int[] irows = this.jTableFiles.getSelectedRows();

                    for (int i = 0; i < irows.length; i++)
                    {
                        applets.ftp.File file = filemodel.getFile(irows[i]);
                        String path = dir.getAbsolutePath() + "/" + file.getName();
                        java.io.File flocal = new java.io.File(path);
                        if (flocal.exists())
                        {
                            int status = JOptionPane.showConfirmDialog(
                                    this, // The parent frame
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_file_download1")
                                    + " " + flocal.getName()
                                    + " " + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_file_download2")
                                    + "\r\n" + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("sobreEscribir"),
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (status == JOptionPane.NO_OPTION)
                            {
                                continue;
                            }
                        }
                        //FileDownload fdown=new FileDownload(file.getPath(), this.jProgressBar1, flocal, this.jsess, urldownload);
                        Dialog parent = null;
                        if (container instanceof Dialog)
                        {
                            parent = (Dialog) container;
                        }
                        FDownload fdown = new FDownload(parent, false, file.getPath(), flocal, jsess, urldownload, locale);
                        fdown.show();
                        fdown.setLocation(400, 300);
                        fdown.setSize(200, 50);
                        fdown.getFile();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void jMenuItemFileDownloadActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemFileDownloadActionPerformed
        downloadFile();
    }//GEN-LAST:event_jMenuItemFileDownloadActionPerformed

    private void jMenuItemDirAddFileActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemDirAddFileActionPerformed
        addFile();
    }//GEN-LAST:event_jMenuItemDirAddFileActionPerformed

    private void jMenuItemFileAddFileActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemFileAddFileActionPerformed

        this.addFile();

    }//GEN-LAST:event_jMenuItemFileAddFileActionPerformed

    private void jMenuItemRenameFileActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemRenameFileActionPerformed
        this.renameFile();
    }//GEN-LAST:event_jMenuItemRenameFileActionPerformed

    private void jMenuItemRenameFolderActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemRenameFolderActionPerformed
        this.renameDir();
    }//GEN-LAST:event_jMenuItemRenameFolderActionPerformed

    private void jTreeDirsKeyReleased(java.awt.event.KeyEvent evt)
    {//GEN-FIRST:event_jTreeDirsKeyReleased
        try
        {
            if (evt != null && evt.getKeyCode() == KeyEvent.VK_DELETE)
            {
                this.deleteDir();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTreeDirsKeyReleased

    private void jTableFilesKeyReleased(java.awt.event.KeyEvent evt)
    {//GEN-FIRST:event_jTableFilesKeyReleased
        if (evt != null && evt.getKeyCode() == KeyEvent.VK_DELETE)
        {
            this.deleteFile();
        }
    }//GEN-LAST:event_jTableFilesKeyReleased

    private void jMenuItemBorrarFileActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemBorrarFileActionPerformed
        this.deleteFile();
    }//GEN-LAST:event_jMenuItemBorrarFileActionPerformed

    private void jMenuItemBorrarDirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemBorrarDirActionPerformed
        this.deleteDir();
    }//GEN-LAST:event_jMenuItemBorrarDirActionPerformed

    private void jMenuItemFileRenameActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemFileRenameActionPerformed
        this.renameFile();
    }//GEN-LAST:event_jMenuItemFileRenameActionPerformed

    private void jMenuItemDirRenameActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemDirRenameActionPerformed
        this.renameDir();
    }//GEN-LAST:event_jMenuItemDirRenameActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt)
    {//GEN-FIRST:event_formComponentShown
        try
        {
            this.jTreeDirs.setSelectionRow(0);
        }
        catch (NullPointerException npe)
        {
        }
    }//GEN-LAST:event_formComponentShown

    private void jMenuItemDirDeleteActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemDirDeleteActionPerformed
        this.deleteDir();
    }//GEN-LAST:event_jMenuItemDirDeleteActionPerformed

    private void jMenuItemFileDeleteActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemFileDeleteActionPerformed
        deleteFile();
    }//GEN-LAST:event_jMenuItemFileDeleteActionPerformed

    private void jMenuItemFileAddDirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemFileAddDirActionPerformed
        this.addDir();
    }//GEN-LAST:event_jMenuItemFileAddDirActionPerformed

    private void jMenuItemDirAddFolderActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemDirAddFolderActionPerformed
        this.addDir();
    }//GEN-LAST:event_jMenuItemDirAddFolderActionPerformed

    private void jTreeDirsMousePressed(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_jTreeDirsMousePressed
        try
        {
            if (evt != null && evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1 && this.jTreeDirs != null && this.jTreeDirs.getSelectionPath() != null)
            {
                jMenuItemNewFolder.setEnabled(false);
                jMenuDirAdd.setEnabled(false);


                jMenuItemDirRename.setEnabled(false);
                jMenuItemDirDelete.setEnabled(false);
                Object obj = this.jTreeDirs.getSelectionPath().getLastPathComponent();
                if (obj != null && obj instanceof Directory)
                {
                    Directory directory = (Directory) obj;
                    if (hasPermission(directory.getDirectory()))
                    {
                        jMenuItemNewFolder.setEnabled(true);
                        jMenuDirAdd.setEnabled(true);
                        jMenuItemDirRename.setEnabled(true);
                        jMenuItemDirDelete.setEnabled(true);
                    }
                }
                this.jPopupMenuDir.show(this.jTreeDirs, evt.getX(), evt.getY());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTreeDirsMousePressed

    private void jMenuItemAddFolderActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jMenuItemAddFolderActionPerformed
        addDir();
    }//GEN-LAST:event_jMenuItemAddFolderActionPerformed

    private void jTableFilesMousePressed(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_jTableFilesMousePressed
        this.jMenuItemFileDownload.setEnabled(false);
        this.jMenuDownloadDir.setEnabled(false);
        this.jMenuItemFileRename.setEnabled(false);
        this.jMenuItemFileDelete.setEnabled(false);

        this.jMenuItemPreview.setEnabled(false);
        this.jMenuPreview.setEnabled(false);

        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1)
        {
            if (this.jTableFiles.getSelectedRow() != -1)
            {
                jTableFileModel model = (jTableFileModel) jTableFiles.getModel();
                File file = model.getFile(this.jTableFiles.getSelectedRow());
                if (hasPermission(file))
                {
                    this.jMenuItemFileDownload.setEnabled(true);
                    this.jMenuItemFileRename.setEnabled(true);
                    this.jMenuItemFileDelete.setEnabled(true);
                    if (JPreview.isImage(file.path) || JPreview.isText(file.path))
                    {
                        this.jMenuItemPreview.setEnabled(true);
                        this.jMenuPreview.setEnabled(true);
                    }
                }
            }
            this.jPopupMenuFile.show(this.jTableFiles, evt.getX(), evt.getY());

        }
    }//GEN-LAST:event_jTableFilesMousePressed

    private void jTreeDirsTreeWillExpand(javax.swing.event.TreeExpansionEvent evt) throws javax.swing.tree.ExpandVetoException
    {//GEN-FIRST:event_jTreeDirsTreeWillExpand
        if (evt.getPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) evt.getPath().getLastPathComponent();
            if (dir.getChildCount() == 1 && !(dir.getChildAt(0) instanceof Directory))
            {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                dir.remove(0);
                loadDirectories(dir);
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_jTreeDirsTreeWillExpand

    private void jTreeDirsValueChanged(javax.swing.event.TreeSelectionEvent evt)
    {//GEN-FIRST:event_jTreeDirsValueChanged

        this.jButtonAddFile.setEnabled(false);
        this.jButtonDownload.setEnabled(false);
        this.jButtonNewFolder.setEnabled(false);
        this.jButtonaddFolder.setEnabled(false);
        this.jMenuBorrar.setEnabled(false);
        this.jMenuDirAdd.setEnabled(false);
        this.jMenuDownloadDir.setEnabled(false);
        this.jMenuAdd.setEnabled(false);
        this.jMenuItemCrearDirectorio.setEnabled(false);
        this.jMenuRename.setEnabled(false);
        this.jMenuFileAdd.setEnabled(false);

        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            if (this.hasPermission(dir.getDirectory()))
            {
                this.jMenuFileAdd.setEnabled(true);
                this.jMenuAdd.setEnabled(true);
                this.jMenuRename.setEnabled(true);
                this.jMenuItemCrearDirectorio.setEnabled(true);
                this.jMenuDownloadDir.setEnabled(true);
                this.jMenuDirAdd.setEnabled(true);
                this.jMenuBorrar.setEnabled(true);
                this.jButtonAddFile.setEnabled(true);
                this.jButtonDownload.setEnabled(true);
                this.jButtonNewFolder.setEnabled(true);
                this.jButtonaddFolder.setEnabled(true);
            }
            loadFiles(dir);
        }
        this.jTreeDirs.updateUI();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_jTreeDirsValueChanged

    private void jScrollPane2MouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jScrollPane2MouseDragged
    {//GEN-HEADEREND:event_jScrollPane2MouseDragged
    }//GEN-LAST:event_jScrollPane2MouseDragged

    private void jMenuItemDownloadDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDownloadDirActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDownloadDirActionPerformed
        downloadDir();
    }//GEN-LAST:event_jMenuItemDownloadDirActionPerformed

    private void jMenuDownloadDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuDownloadDirActionPerformed
    {//GEN-HEADEREND:event_jMenuDownloadDirActionPerformed
        jMenuItemDownloadDirActionPerformed(null);
    }//GEN-LAST:event_jMenuDownloadDirActionPerformed

    private void jMenuItemPreviewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemPreviewActionPerformed
    {//GEN-HEADEREND:event_jMenuItemPreviewActionPerformed

        if (this.jTableFiles.getSelectedRowCount() > 0 && this.container instanceof JDialog)
        {
            jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
            File file = model.getFile(this.jTableFiles.getSelectedRow());
            if (JPreview.isText(file.path) || JPreview.isImage(file.path))
            {

                JPreview preview = new JPreview((JDialog) this.container, locale, urldownload, jsess, file.path, applet, ContextPath, ApplicationPath);
                preview.showPreview();
            }
        }
        else if (this.jTableFiles.getSelectedRowCount() > 0 && !(this.container instanceof JDialog))
        {
            jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
            File file = model.getFile(this.jTableFiles.getSelectedRow());
            if (JPreview.isText(file.path) || JPreview.isImage(file.path))
            {
                JPreview preview = new JPreview(locale, jsess, urldownload, file.path, applet, ContextPath, ApplicationPath);
                preview.showPreview();
            }
        }

    }//GEN-LAST:event_jMenuItemPreviewActionPerformed

    private void jMenuPreviewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuPreviewActionPerformed
    {//GEN-HEADEREND:event_jMenuPreviewActionPerformed
        this.jMenuItemPreviewActionPerformed(evt);
    }//GEN-LAST:event_jMenuPreviewActionPerformed

    public void downloadDir()
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {


            try
            {
                Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
                this.jFileChooser1 = new JFileChooser();
                if (pathDir != null)
                {
                    this.jFileChooser1.setCurrentDirectory(pathDir);
                }
                this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("downloaddir"));
                this.jFileChooser1.setMultiSelectionEnabled(false);
                this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = this.jFileChooser1.showOpenDialog(this);
                pathDir = this.jFileChooser1.getCurrentDirectory();
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    java.io.File dirlocal = this.jFileChooser1.getSelectedFile();
                    Dialog parent = null;
                    if (container instanceof Dialog)
                    {
                        parent = (Dialog) container;
                    }
                    DialogAddFile addFile = new DialogAddFile(parent, dirlocal, this, dir, urldownload);
                    try
                    {
                        addFile.downloadDir();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    } finally
                    {
                        addFile.setVisible(false);
                        addFile.dispose();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean createFile(java.io.File filelocal, String path, boolean siAll, Component dialog) throws Exception
    {

        if (exists(path))
        {
            if (!siAll)
            {
                Component parent = this;
                if (dialog != null)
                {
                    parent = dialog;
                }
                int selection = JOptionPane.showOptionDialog(
                        parent,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_file_overwrite1")
                        + " '" + filelocal.getParentFile().getName() + "' "
                        + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_file_overwrite2")
                        + " '" + filelocal.getName() + "'.\r\n"
                        + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_file_overwrite3"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                switch (selection)
                {
                    case 0: //Si
                        siAll = false;
                        break;
                    case 1: //Si All
                        siAll = true;
                        break;
                    case 2:
                        return siAll;
                    case 3:
                        throw new Exception(this.choices[selection]);

                }
            }
        }
        try
        {

            FileUpload fup = new FileUpload(jsess, urlupload, locale);
            fup.sendFile(path, filelocal);
        }
        catch (Exception e)
        {
        }
        return siAll;
    }

    public boolean exists(String path)
    {
        String newpath = path;
        try
        {
            newpath = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>exists</cmd><path>" + newpath + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("exists"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public boolean createDir(String path, boolean siAll, Component component) throws Exception
    {
        String newpath = path;
        try
        {
            newpath = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        boolean exists = exists(path);
        if (exists)
        {
            if (!siAll)
            {
                java.io.File carpeta = new java.io.File(path);
                Component parent = this;
                if (component != null)
                {
                    parent = component;
                }
                int selection = JOptionPane.showOptionDialog(
                        parent,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_carpeta_existe")
                        + " '" + carpeta.getName()
                        + "'.\r\n"
                        + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_carpeta_sobreescribe"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                switch (selection)
                {
                    case 0: //Si
                        siAll = false;
                        break;
                    case 1: //Si All
                        siAll = true;
                        break;
                    case 2:
                        return siAll;
                    case 3:
                        throw new Exception(this.choices[selection]);

                }
            }
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>createDir</cmd><path>" + newpath + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("create"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return siAll;
    }

    private void addDir()
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            try
            {

                //URL urlupload=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),this.uploadpath);
                Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
                this.jFileChooser1 = new JFileChooser();
                if (pathDir != null)
                {
                    this.jFileChooser1.setCurrentDirectory(pathDir);
                }
                this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("add_dir"));
                this.jFileChooser1.setMultiSelectionEnabled(false);
                this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = this.jFileChooser1.showOpenDialog(this);
                pathDir = this.jFileChooser1.getCurrentDirectory();
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    java.io.File dirlocal = this.jFileChooser1.getSelectedFile();
                    DialogAddFile addFile = new DialogAddFile(null, dirlocal, this, dir, url);
                    try
                    {
                        addFile.addDir();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    } finally
                    {
                        addFile.setVisible(false);
                        addFile.dispose();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void SendFiles(ArrayList<java.io.File> files, Directory dir, URL urlupload)
    {
        jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
        Iterator it = files.iterator();
        while (it.hasNext())
        {
            java.io.File f = (java.io.File) it.next();
            String path = dir.getDirectory() + "/" + f.getName();
            Dialog parent = null;
            if (container instanceof Dialog)
            {
                parent = (Dialog) container;
            }
            FUpload fup = new FUpload(parent, false, jsess, urlupload, locale);
            ftpPanel.Worker wr = new ftpPanel.Worker(f, path, fup, model, dir);
            SwingUtilities.invokeLater(wr);
        }
    }

    private void addFile(java.io.File file)
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {

            try
            {


                Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();

                applets.ftp.File fileexists = null;
                ArrayList<java.io.File> files = new ArrayList<java.io.File>();
                files.add(file);
                ArrayList<java.io.File> vfiles = new ArrayList<java.io.File>();
                boolean siAll = false;
                for (java.io.File f : files)
                {

                    jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
                    boolean existe = false;
                    for (int j = 0; j < model.getRowCount(); j++)
                    {
                        applets.ftp.File filecat = model.getFile(j);
                        if (filecat.getName().equalsIgnoreCase(f.getName()))
                        {
                            existe = true;
                            fileexists = filecat;
                        }
                    }
                    if (existe)
                    {
                        if (siAll)
                        {
                            if (fileexists != null)
                            {
                                model.removeFile(fileexists);
                            }
                        }
                        else
                        {
                            int selection = JOptionPane.showOptionDialog(
                                    this,
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombrefile") + " '" + fileexists.getName() + "' " + "\r\n" + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("sobreEscribir"),
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    choices,
                                    choices[0]);
                            switch (selection)
                            {
                                case 0: //Si
                                    siAll = false;
                                    if (fileexists != null)
                                    {
                                        model.removeFile(fileexists);
                                    }
                                    break;
                                case 1: //Si All
                                    siAll = true;
                                    if (fileexists != null)
                                    {
                                        model.removeFile(fileexists);
                                    }
                                    break;
                                case 2: // No
                                    continue;
                                case 3:  //Cancel
                                    return;
                            }
                        }



                    }
                    vfiles.add(f);
                }
                final ArrayList<java.io.File> osendfiles = vfiles;
                final Directory odir = dir;
                final URL urlup = urlupload;

                Runnable doWorkRunnable = new Runnable()
                {

                    @Override
                    public void run()
                    {
                        SendFiles(osendfiles, odir, urlup);
                    }
                };
                SwingUtilities.invokeLater(doWorkRunnable);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addFile()
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {

            try
            {


                Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
                this.jFileChooser1 = new JFileChooser();
                if (pathDir != null)
                {
                    this.jFileChooser1.setCurrentDirectory(pathDir);
                }
                this.jFileChooser1.setMultiSelectionEnabled(true);
                this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("add_files"));
                this.jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int ret = this.jFileChooser1.showOpenDialog(this);
                pathDir = this.jFileChooser1.getCurrentDirectory();
                if (ret == JFileChooser.APPROVE_OPTION)
                {



                    ArrayList<java.io.File> vfiles = new ArrayList<java.io.File>();
                    applets.ftp.File fileexists = null;
                    java.io.File[] files = this.jFileChooser1.getSelectedFiles();


                    boolean siAll = false;
                    for (int i = 0; i < files.length; i++)
                    {
                        java.io.File f = files[i];
                        jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
                        boolean existe = false;
                        for (int j = 0; j < model.getRowCount(); j++)
                        {
                            applets.ftp.File filecat = model.getFile(j);
                            if (filecat.getName().equalsIgnoreCase(f.getName()))
                            {
                                existe = true;
                                fileexists = filecat;
                            }
                        }
                        if (existe)
                        {
                            if (siAll)
                            {
                                if (fileexists != null)
                                {
                                    model.removeFile(fileexists);
                                }
                            }
                            else
                            {
                                int selection = JOptionPane.showOptionDialog(
                                        this,
                                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombrefile") + " '" + fileexists.getName() + "' " + "\r\n" + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("sobreEscribir"),
                                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        choices,
                                        choices[0]);
                                switch (selection)
                                {
                                    case 0: //Si
                                        siAll = false;
                                        if (fileexists != null)
                                        {
                                            model.removeFile(fileexists);
                                        }
                                        break;
                                    case 1: //Si All
                                        siAll = true;
                                        if (fileexists != null)
                                        {
                                            model.removeFile(fileexists);
                                        }
                                        break;
                                    case 2: // No
                                        continue;
                                    case 3:  //Cancel                      
                                        return;
                                }
                            }



                        }
                        vfiles.add(f);
                    }
                    this.jFileChooser1.updateUI();
                    final ArrayList<java.io.File> osendfiles = vfiles;
                    final Directory odir = dir;
                    final URL urlup = urlupload;

                    Runnable doWorkRunnable = new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            SendFiles(osendfiles, odir, urlup);
                        }
                    };
                    SwingUtilities.invokeLater(doWorkRunnable);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void renameFile()
    {
        if (this.jTableFiles.getSelectedRowCount() > 0)
        {
            jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
            File file = model.getFile(this.jTableFiles.getSelectedRow());
            String name = JOptionPane.showInputDialog(
                    this, // The parent frame
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("nombre_file"), file.getName());
            name = name.trim();
            if (name.trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_filename"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < model.getRowCount(); i++)
            {
                File f = model.getFile(i);
                if (f.getName().equalsIgnoreCase(name))
                {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombrefile"),
                            java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            String newpath = file.getDirectory().getDirectory() + "/" + name;
            if (renameFile(file, newpath))
            {

                file.setName(name);
                file.setPath(newpath);
                this.jTableFiles.updateUI();
            }
        }
    }

    private boolean renameFile(File f, String newpath)
    {
        String path = f.getPath();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
            newpath = WBXMLParser.encode(newpath, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>rename</cmd><path>" + path + "</path><newpath>" + newpath + "</newpath></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("rename"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;
    }

    private void renameDir()
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            if (dir.getParent() == null)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_renombrar"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Directory parent = (Directory) dir.getParent();
            String name = JOptionPane.showInputDialog(
                    this, // The parent frame
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("nombre_dir"), dir.getName());
            name = name.trim();
            if (name.trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_nombre"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                Directory child = (Directory) parent.getChildAt(i);
                if (child.getName().equalsIgnoreCase(name))
                {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombre"),
                            java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String path = parent.getDirectory() + "/" + name;
            if (renameDir(dir, path))
            {
                dir.setName(name);
                dir.setDirectory(path);
                this.jTreeDirs.updateUI();

            }
        }
    }

    private boolean renameDir(Directory dir, String newpath)
    {
        String path = dir.getDirectory();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
            newpath = WBXMLParser.encode(newpath, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>rename</cmd><path>" + path + "</path><newpath>" + newpath + "</newpath></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("rename"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;

    }

    private void deleteDir()
    {
        if (this.jTreeDirs != null && this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            loadDirectories(dir);
            jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
            if (model.getRowCount() > 0 || dir.getChildCount() > 0)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_empty"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dir.getParent() == null)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_borrar"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(
                    this, // The parent frame
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("askdir") + " " + dir.getName() + "?",
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                    JOptionPane.YES_NO_OPTION, // The option type
                    JOptionPane.QUESTION_MESSAGE))
            {
                return;
            }




            if (dir.getParent() instanceof Directory)
            {
                Directory parent = (Directory) dir.getParent();
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                if (deleteDir(dir))
                {
                    this.jTreeDirs.setSelectionPath(this.jTreeDirs.getSelectionPath().getParentPath());
                    parent.remove(dir);
                    this.jTreeDirs.updateUI();
                }
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }


    }

    private boolean deleteDir(Directory dir)
    {
        String path = dir.getDirectory();
        try
        {
            path = WBXMLParser.encode(dir.getDirectory(), "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>delete</cmd><path>" + path + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("delete"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;
    }

    private void deleteFile()
    {

        jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
        int[] isel = this.jTableFiles.getSelectedRows();
        ArrayList<File> files = new ArrayList<File>();
        for (int i = 0; i < isel.length; i++)
        {
            int index = isel[i];
            files.add(model.getFile(index));
        }
        boolean siAll = false;
        for (int i = 0; i < files.size(); i++)
        {
            File f = (File) files.get(i);
            if (!siAll)
            {
                int selection = JOptionPane.showOptionDialog(
                        this,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("ask1") + " " + f.getName() + "?",
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                switch (selection)
                {
                    case 0: //Si
                        siAll = false;
                        break;
                    case 1: //Si All
                        siAll = true;
                        break;
                    case 2: // No
                        continue;
                    case 3:  //Cancel                      
                        return;
                }
            }
            if (deleteFile(f))
            {
                model.removeFile(f);
                this.jTableFiles.updateUI();
                this.Modificated();
            }
        }
    }

    private boolean deleteFile(File f)
    {

        String path = f.getPath();
        try
        {
            path = WBXMLParser.encode(f.getPath(), "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>delete</cmd><path>" + path + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("delete"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;
    }

    private void createDir()
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            createDir(dir);
        }
    }

    public void createDir(Directory dir)
    {
        this.jTreeDirs.expandPath(this.jTreeDirs.getSelectionPath());
        String name = JOptionPane.showInputDialog(
                this, // The parent frame
                java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("nombre_dir"));
        name = name.trim();
        if (name.trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_nombre"),
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < dir.getChildCount(); i++)
        {
            Directory child = (Directory) dir.getChildAt(i);
            if (child.getName().equalsIgnoreCase(name))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombre"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        String path = dir.getDirectory() + "/" + name;
        String pathdir = path;
        try
        {
            pathdir = WBXMLParser.encode(pathdir, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>createDir</cmd><path>" + pathdir + "</path></req>";
        String respxml = getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("create"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    Directory child = new Directory(name, path, "false");
                    dir.add(child);
                    TreePath newpath = this.jTreeDirs.getSelectionPath().pathByAddingChild(child);
                    this.jTreeDirs.setSelectionPath(newpath);
                    this.jTreeDirs.updateUI();
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }


    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        Modificated();
    }

    @Override
    public void Modificated()
    {
        this.jTableFiles.updateUI();
        if (this.jTableFiles.getSelectedRowCount() == 0 || this.jTableFiles.getRowCount() == 0)
        {
            this.jMenuItemBorrarFile.setEnabled(false);
            this.jMenuItemRenameFile.setEnabled(false);
            this.jMenuItemDownload.setEnabled(false);
            this.jButtonDownload.setEnabled(false);
        }
        else
        {
            this.jMenuItemBorrarFile.setEnabled(true);
            this.jMenuItemRenameFile.setEnabled(true);
            this.jMenuItemDownload.setEnabled(true);
            this.jButtonDownload.setEnabled(true);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddFile;
    private javax.swing.JButton jButtonDownload;
    private javax.swing.JButton jButtonNewFolder;
    private javax.swing.JButton jButtonaddFolder;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenuAdd;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuBorrar;
    private javax.swing.JMenu jMenuDirAdd;
    private javax.swing.JMenuItem jMenuDownloadDir;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuFileAdd;
    private javax.swing.JMenuItem jMenuItemAddFile;
    private javax.swing.JMenuItem jMenuItemAddFolder;
    private javax.swing.JMenuItem jMenuItemBorrarDir;
    private javax.swing.JMenuItem jMenuItemBorrarFile;
    private javax.swing.JMenuItem jMenuItemCrearDirectorio;
    private javax.swing.JMenuItem jMenuItemDirAddFile;
    private javax.swing.JMenuItem jMenuItemDirAddFolder;
    private javax.swing.JMenuItem jMenuItemDirDelete;
    private javax.swing.JMenuItem jMenuItemDirRename;
    private javax.swing.JMenuItem jMenuItemDownload;
    private javax.swing.JMenuItem jMenuItemDownloadDir;
    private javax.swing.JMenuItem jMenuItemFileAddDir;
    private javax.swing.JMenuItem jMenuItemFileAddFile;
    private javax.swing.JMenuItem jMenuItemFileDelete;
    private javax.swing.JMenuItem jMenuItemFileDownload;
    private javax.swing.JMenuItem jMenuItemFileRename;
    private javax.swing.JMenuItem jMenuItemNewFolder;
    private javax.swing.JMenuItem jMenuItemPreview;
    private javax.swing.JMenuItem jMenuItemRenameFile;
    private javax.swing.JMenuItem jMenuItemRenameFolder;
    private javax.swing.JMenuItem jMenuPreview;
    private javax.swing.JMenu jMenuRename;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelProgressBar;
    private javax.swing.JPanel jPanelToolbar;
    private javax.swing.JPopupMenu jPopupMenuDir;
    private javax.swing.JPopupMenu jPopupMenuFile;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTable jTableFiles;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTreeDirs;
    // End of variables declaration//GEN-END:variables

    @Override
    public void dragEnter(DropTargetDragEvent dtde)
    {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde)
    {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde)
    {
    }

    @Override
    public void dragExit(DropTargetEvent dte)
    {
    }

    @Override
    public void drop(DropTargetDropEvent dtde)
    {
        if (this.jTreeDirs.getSelectionPath() != null && this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();



            try
            {
                Transferable tr = dtde.getTransferable();
                DataFlavor[] flavors = tr.getTransferDataFlavors();
                for (int i = 0; i < flavors.length; i++)
                {
                    if (flavors[i].isFlavorJavaFileListType())
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);
                        java.util.List<java.io.File> _list = (java.util.List<java.io.File>) tr.getTransferData(flavors[i]);
                        java.util.ArrayList<java.io.File> files = new ArrayList<java.io.File>();

                        for (java.io.File file : _list)
                        {
                            if (file.isDirectory())
                            {
                                //this.addDir(file, dir.getDirectory(), false, this);
                                DialogAddFile dialog = new DialogAddFile(null, file, this, dir, urldownload);
                                dialog.addDir();
                            }
                        }
                        for (java.io.File file : _list)
                        {
                            if (!file.isDirectory())
                            {
                                files.add(file);
                            }
                        }
                        if (files.size() > 0)
                        {
                            for (java.io.File file : files)
                            {
                                this.addFile(file);
                            }
                        }

                        dtde.dropComplete(true);
                        return;
                    }
                }
                System.out.println("Drop failed: " + dtde);
                dtde.rejectDrop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                dtde.rejectDrop();
            }
        }
    }

    public JPanel getContentPane()
    {
        return this;
    }

    private void setJMenuBar(JMenuBar jMenuBar1)
    {
        if (container instanceof JApplet)
        {
            ((JApplet) container).setJMenuBar(jMenuBar1);
        }
        else if (container instanceof JDialog)
        {
            ((JDialog) container).setJMenuBar(jMenuBar1);
        }
    }

    // End of variables declaration
    private class Worker implements Runnable
    {

        FUpload fup;
        String path;
        java.io.File f;
        jTableFileModel model;
        Directory dir;

        public Worker(java.io.File f, String path, FUpload fup, jTableFileModel model, Directory dir)
        {
            this.fup = fup;
            this.path = path;
            this.f = f;
            this.model = model;
            this.dir = dir;
        }

        public void run()
        {
            fup.setLocation(400, 300);
            fup.setSize(200, 50);
            fup.sendFile(path, f, model, dir);
            jTableFiles.updateUI();
        }
    }

    public void updateTreeUI()
    {
        jTreeDirs.updateUI();
    }
}
