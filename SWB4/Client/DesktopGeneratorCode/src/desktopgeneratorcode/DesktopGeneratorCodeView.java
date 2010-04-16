/*
 * DesktopGeneratorCodeView.java
 */
package desktopgeneratorcode;

import java.io.File;
import java.io.IOException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import org.semanticwb.SWBPlatform;
import org.semanticwb.codegen.CodeGenerator;

/**
 * The application's main frame.
 */
public class DesktopGeneratorCodeView extends FrameView
{

    private static final String PROYECT_EXTENSION = ".pcg";
    private HashSet<OWL> owls = new HashSet<OWL>();
    private JFileChooser filechooser = new JFileChooser();
    DialogConfiguration dc = new DialogConfiguration(this.getFrame(), filechooser);
    File project;

    class MyPrintStream extends PrintStream
    {

        public MyPrintStream()
        {
            super(new MyOut());
        }
    }

    class MyOut extends OutputStream
    {

        @Override
        public void write(int b) throws IOException
        {
            jTextArea1.append(new String(new char[]
                    {
                        (char) b
                    }));
        }
    }

    public DesktopGeneratorCodeView(SingleFrameApplication app)
    {
        super(app);

        initComponents();
        this.getFrame().setIconImage(ImageLoader.images.get("semius").getImage());
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++)
        {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {

            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName))
                {
                    if (!busyIconTimer.isRunning())
                    {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                }
                else if ("done".equals(propertyName))
                {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                }
                else if ("message".equals(propertyName))
                {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                }
                else if ("progress".equals(propertyName))
                {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox()
    {
        if (aboutBox == null)
        {
            JFrame mainFrame = DesktopGeneratorCodeApp.getApplication().getMainFrame();
            aboutBox = new DesktopGeneratorCodeAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopGeneratorCodeApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButtonSelectDir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAdd = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonGenerate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemopen = new javax.swing.JMenuItem();
        jMenuItemAdd = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenuTools = new javax.swing.JMenu();
        jMenuItemGenerateCode = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemConfiguration = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        mainPanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopgeneratorcode.DesktopGeneratorCodeApp.class).getContext().getResourceMap(DesktopGeneratorCodeView.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.border.border.title"))))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(437, 70));

        jTextField1.setEditable(false);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jButtonSelectDir.setText(resourceMap.getString("jButtonSelectDir.text")); // NOI18N
        jButtonSelectDir.setName("jButtonSelectDir"); // NOI18N
        jButtonSelectDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectDirActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .add(18, 18, 18)
                .add(jButtonSelectDir))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButtonSelectDir))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(459, 30));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setDoubleBuffered(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jButtonAdd.setIcon(resourceMap.getIcon("jButtonAdd.icon")); // NOI18N
        jButtonAdd.setText(resourceMap.getString("jButtonAdd.text")); // NOI18N
        jButtonAdd.setToolTipText(resourceMap.getString("jButtonAdd.toolTipText")); // NOI18N
        jButtonAdd.setFocusable(false);
        jButtonAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAdd.setName("jButtonAdd"); // NOI18N
        jButtonAdd.setNextFocusableComponent(jButtonDelete);
        jButtonAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAdd);

        jButtonDelete.setIcon(resourceMap.getIcon("jButtonDelete.icon")); // NOI18N
        jButtonDelete.setText(resourceMap.getString("jButtonDelete.text")); // NOI18N
        jButtonDelete.setToolTipText(resourceMap.getString("jButtonDelete.toolTipText")); // NOI18N
        jButtonDelete.setFocusable(false);
        jButtonDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDelete.setName("jButtonDelete"); // NOI18N
        jButtonDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonDelete);

        jButtonGenerate.setIcon(resourceMap.getIcon("jButtonGenerate.icon")); // NOI18N
        jButtonGenerate.setText(resourceMap.getString("jButtonGenerate.text")); // NOI18N
        jButtonGenerate.setToolTipText(resourceMap.getString("jButtonGenerate.toolTipText")); // NOI18N
        jButtonGenerate.setFocusable(false);
        jButtonGenerate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonGenerate.setName("jButtonGenerate"); // NOI18N
        jButtonGenerate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonGenerate);

        jPanel3.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 100));
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OWL", "prefijo", "Ruta", "Generar código"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel5);

        jPanel4.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        mainPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItemNew.setText(resourceMap.getString("jMenuItemNew.text")); // NOI18N
        jMenuItemNew.setName("jMenuItemNew"); // NOI18N
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemNew);

        jMenuItemopen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemopen.setText(resourceMap.getString("jMenuItemopen.text")); // NOI18N
        jMenuItemopen.setName("jMenuItemopen"); // NOI18N
        jMenuItemopen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemopenActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemopen);

        jMenuItemAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemAdd.setText(resourceMap.getString("jMenuItemAdd.text")); // NOI18N
        jMenuItemAdd.setName("jMenuItemAdd"); // NOI18N
        jMenuItemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemAdd);

        jSeparator3.setName("jSeparator3"); // NOI18N
        fileMenu.add(jSeparator3);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText(resourceMap.getString("jMenuItemSave.text")); // NOI18N
        jMenuItemSave.setName("jMenuItemSave"); // NOI18N
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemSave);

        jMenuItemSaveAs.setText(resourceMap.getString("jMenuItemSaveAs.text")); // NOI18N
        jMenuItemSaveAs.setName("jMenuItemSaveAs"); // NOI18N
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveAsActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemSaveAs);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopgeneratorcode.DesktopGeneratorCodeApp.class).getContext().getActionMap(DesktopGeneratorCodeView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenuTools.setText(resourceMap.getString("jMenuTools.text")); // NOI18N
        jMenuTools.setName("jMenuTools"); // NOI18N
        jMenuTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuToolsActionPerformed(evt);
            }
        });

        jMenuItemGenerateCode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItemGenerateCode.setText(resourceMap.getString("jMenuItemGenerateCode.text")); // NOI18N
        jMenuItemGenerateCode.setName("jMenuItemGenerateCode"); // NOI18N
        jMenuItemGenerateCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGenerateCodeActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemGenerateCode);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenuTools.add(jSeparator1);

        jMenuItemConfiguration.setText(resourceMap.getString("jMenuItemConfiguration.text")); // NOI18N
        jMenuItemConfiguration.setName("jMenuItemConfiguration"); // NOI18N
        jMenuItemConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConfigurationActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemConfiguration);

        menuBar.add(jMenuTools);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 335, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exitMenuItemActionPerformed
    {//GEN-HEADEREND:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jMenuItemAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemAddActionPerformed
    {//GEN-HEADEREND:event_jMenuItemAddActionPerformed

        filechooser.addChoosableFileFilter(new FileFilter()
        {

            @Override
            public boolean accept(File f)
            {
                return f.getName().endsWith(".owl") || f.isDirectory();
            }

            @Override
            public String getDescription()
            {
                return "Archivo de Ontologia (*.owl)";
            }
        });
        filechooser.setDialogTitle("Agregar archivo de ontologia");
        filechooser.setMultiSelectionEnabled(true);
        int res = filechooser.showDialog(this.mainPanel, "Agregar");
        if (res == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
        BaseReferencesConfiguration bf = new BaseReferencesConfiguration();
        for (File file : filechooser.getSelectedFiles())
        {
            if (bf.exists(file.getAbsolutePath()))
            {
                JOptionPane.showMessageDialog(this.getFrame(), "El owl " + file.getAbsolutePath() + " que se deseagregar ya existe en las referencias existentes", "Agregar owl", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return;
            }
            try
            {
                OWL owl = new OWL(file);
                if (!owls.contains(owl))
                {
                    this.owls.add(owl);
                }
            }
            catch (GenerationException ge)
            {
                JOptionPane.showMessageDialog(this.getFrame(), ge.getMessage(), "Error", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            }
        }
        loadOWL();
    }//GEN-LAST:event_jMenuItemAddActionPerformed
    private void loadOWL()
    {
        this.jTextArea1.setText("");
        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            model.removeRow(0);
        }
        for (OWL owl : owls)
        {
            if (owl.prefixes != null)
            {
                for (String prefix : owl.prefixes)
                {
                    boolean isbase = false;
                    if (owl.getRequiredPrefixes() != null)
                    {
                        for (String requiredprefix : owl.getRequiredPrefixes())
                        {
                            if (requiredprefix.equals(prefix))
                            {
                                isbase = true;
                            }
                        }
                    }
                    // puede ser uno commun
                    for (OWL owlcommon : this.dc.getOWLBaseCommons())
                    {
                        if (owlcommon.getNamespace() != null)
                        {
                            String prefixowl = owl.getPrefix(owlcommon.getNamespace());
                            if (prefixowl != null && prefixowl.equals(prefix))
                            {
                                isbase = true;
                            }
                        }
                    }

                    if (!isbase)
                    {
                        Object[] data =
                        {
                            owl, prefix, owl.getLocation(), true
                        };
                        model.addRow(data);
                    }
                }
            }

            for (String namespace : owl.getRequiredNamespaces())
            {
                boolean foundRequiredNamespace = false;
                for (OWL owlbase : dc.getOWLBaseCommons())
                {
                    if (owlbase.getNamespace() != null && owlbase.getNamespace().equals(namespace))
                    {
                        foundRequiredNamespace = true;
                        break;
                    }
                }
                for (OWL owlbase : dc.getOWLBaseProyect())
                {
                    if (owlbase.getNamespace() != null && owlbase.getNamespace().equals(namespace))
                    {
                        foundRequiredNamespace = true;
                        break;
                    }
                }
                if (!foundRequiredNamespace)
                {
                    this.jTextArea1.append(owl.getName() + ": No se encontro el archio owl para el espacio de nombres " + namespace + "\r\n");
                }
            }
        }

    }
    private void jMenuItemConfigurationActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemConfigurationActionPerformed
    {//GEN-HEADEREND:event_jMenuItemConfigurationActionPerformed

        dc.setVisible(true);
    }//GEN-LAST:event_jMenuItemConfigurationActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddActionPerformed
    {//GEN-HEADEREND:event_jButtonAddActionPerformed
        jMenuItemAddActionPerformed(null);
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonSelectDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectDirActionPerformed
    {//GEN-HEADEREND:event_jButtonSelectDirActionPerformed
        JFileChooser file = new JFileChooser();
        file.setMultiSelectionEnabled(false);
        file.setDialogTitle("Selecionar directorio");
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = file.showDialog(this.getFrame(), "Seleccionar directorio");
        if (res == JFileChooser.APPROVE_OPTION && file.getSelectedFile() != null)
        {
            this.jTextField1.setText(file.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jButtonSelectDirActionPerformed
    private void generateCode()
    {
        if (this.jTextField1.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this.getFrame(), "!Debe indicar una ubicación para generar proyecto!", "Guardar proyecto", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return;
        }
        /*if (!this.jTextArea1.getText().isEmpty())
        {
        JOptionPane.showMessageDialog(this.getFrame(), "!Debe corregir primero los errores de la consola de salida!", "Guardar proyecto", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        return;
        }*/
        jTextArea1.setText("Generando código\r\n");

        System.setOut(new MyPrintStream());
        System.setErr(new MyPrintStream());

        SWBPlatform.createInstance();


        File directory = new File(this.jTextField1.getText());
        if (!directory.exists())
        {
            directory.mkdirs();
        }
        for (OWL owl : this.dc.getOWLBaseCommons())
        {
            File file = new File(owl.getLocation());
            if (!file.exists())
            {
                System.out.println("The file " + file.getAbsolutePath() + " was not found");
                return;
            }
        }
        for (OWL owl : this.dc.getOWLBaseProyect())
        {
            File file = new File(owl.getLocation());
            if (!file.exists())
            {
                System.out.println("The file " + file.getAbsolutePath() + " was not found");
                return;
            }
        }
        for (OWL owl : owls)
        {
            File file = new File(owl.getLocation());
            if (!file.exists())
            {
                System.out.println("The file " + file.getAbsolutePath() + " was not found");
                return;
            }
        }

        for (OWL owl : this.dc.getOWLBaseCommons())
        {
            SWBPlatform.getSemanticMgr().addBaseOntology(owl.getLocation());
        }
        for (OWL owl : this.dc.getOWLBaseProyect())
        {
            SWBPlatform.getSemanticMgr().addBaseOntology(owl.getLocation());
        }
        for (OWL owl : owls)
        {
            SWBPlatform.getSemanticMgr().addBaseOntology(owl.getLocation());
        }


        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();


        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        int rows = model.getRowCount();

        this.progressBar.setVisible(true);
        this.progressBar.setMaximum(rows);
        this.progressBar.setMinimum(0);
        this.progressBar.setValue(0);
        for (int i = 0; i < rows; i++)
        {
            Boolean generate = (Boolean) model.getValueAt(i, 3);
            String prefix = model.getValueAt(i, 1).toString();
            String path = model.getValueAt(i, 2).toString();
            CodeGenerator codeGeneration = new CodeGenerator();
            File owl = new File(path);
            if (!owl.exists())
            {
                jTextArea1.append("The file " + owl.getAbsolutePath() + " was not found");
            }
            if (generate.booleanValue())
            {
                try
                {
                    codeGeneration.generateCode(prefix, false, directory);
                }
                catch (Exception e)
                {
                    jTextArea1.append(e.getMessage());
                    e.printStackTrace();
                }
            }
            this.progressBar.setValue(this.progressBar.getValue() + 1);
        }
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String date=df.format(Calendar.getInstance().getTime());
        jTextArea1.append("\r\nCódigo generado "+date+"\r\n");
        this.progressBar.setVisible(false);
    }
    private void jButtonGenerateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonGenerateActionPerformed
    {//GEN-HEADEREND:event_jButtonGenerateActionPerformed
        generateCode();
    }//GEN-LAST:event_jButtonGenerateActionPerformed
    private void readBaseReferencesToProjectOWLS(byte[] cont, File basepath) throws Exception
    {
        HashSet<OWL> owlstoread = new HashSet<OWL>();
        Properties props = new Properties();
        props.loadFromXML(new ByteArrayInputStream(cont));
        for (Object key : props.keySet())
        {
            String value = props.getProperty(key.toString());
            File path = new File(basepath, value);
            if (!path.exists())
            {
                path = new File(key.toString());
            }
            OWL owl = new OWL(path);
            owlstoread.add(owl);
        }
        this.dc.setOWLBaseProyect(owlstoread.toArray(new OWL[owlstoread.size()]));
    }

    private void readProjectOWLS(byte[] cont, File basepath) throws Exception
    {
        owls = new HashSet<OWL>();
        Properties props = new Properties();
        props.loadFromXML(new ByteArrayInputStream(cont));
        for (Object key : props.keySet())
        {
            String value = props.getProperty(key.toString());
            File path = new File(basepath, value);
            if (!path.exists())
            {
                path = new File(key.toString());
            }
            OWL owl = new OWL(path);
            owls.add(owl);
        }
        loadOWL();
    }

    private byte[] createBaseReferencesToProjectOWLS(File basepath) throws Exception
    {
        Properties properties = new Properties();
        for (OWL owl : this.dc.getOWLBaseProyect())
        {
            String key = owl.getLocation();
            String value = owl.getLocation();
            File owllocation = new File(value);
            value = RelativePath.getRelativePath(basepath, owllocation);
            properties.setProperty(key, value);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        properties.storeToXML(out, "Archivos base del proyecto");
        return out.toByteArray();
    }

    private byte[] createProjectOWLS(File basepath) throws Exception
    {
        Properties properties = new Properties();
        for (OWL owl : owls)
        {
            String key = owl.getLocation();
            String value = owl.getLocation();
            File owllocation = new File(value);
            value = RelativePath.getRelativePath(basepath, owllocation);
            properties.setProperty(key, value);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        properties.storeToXML(out, "Archivos owl del proyecto");
        return out.toByteArray();
    }

    private byte[] createNamespaces() throws Exception
    {
        Properties properties = new Properties();
        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            Boolean generate = (Boolean) model.getValueAt(i, 3);
            String prefix = model.getValueAt(i, 1).toString();
            String path = model.getValueAt(i, 2).toString();
            properties.setProperty(prefix + ":" + path, generate.toString());
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        properties.storeToXML(out, "Namespaces to generate");
        return out.toByteArray();
    }

    private byte[] writeConfig() throws Exception
    {
        Properties properties = new Properties();
        properties.setProperty("dir", this.jTextField1.getText());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        properties.storeToXML(out, "Directory to create");
        return out.toByteArray();
    }

    private void readNamespaces(byte[] cont) throws Exception
    {
        Properties props = new Properties();
        props.loadFromXML(new ByteArrayInputStream(cont));
        for (Object key : props.keySet())
        {
            String value = props.getProperty(key.toString());
            int pos = key.toString().indexOf(":");
            if (pos != -1)
            {
                String owlprefix = key.toString().substring(0, pos);
                String owlpath = key.toString().substring(pos + 1);
                boolean cresate = Boolean.parseBoolean(value);
                DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
                int rows = model.getRowCount();
                for (int i = 0; i < rows; i++)
                {
                    String prefix = model.getValueAt(i, 1).toString();
                    String path = model.getValueAt(i, 2).toString();
                    if (prefix.equals(owlprefix) && owlpath.equals(path))
                    {
                        model.setValueAt(cresate, i, 3);
                    }
                }

            }
        }
    }

    private void readConfig(byte[] cont) throws Exception
    {
        Properties props = new Properties();
        props.loadFromXML(new ByteArrayInputStream(cont));
        if (props.get("dir") != null)
        {
            this.jTextField1.setText(props.get("dir").toString());
        }
    }

    private void createProyect(boolean saveas)
    {
        if (this.jTextField1.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this.getFrame(), "!Debe indicar una ubicación para generar proyecto!", "Guardar proyecto", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return;
        }
//        if (!this.jTextArea1.getText().isEmpty())
//        {
//            JOptionPane.showMessageDialog(this.getFrame(), "!Debe corregir primero los errores de la consola de salida!", "Guardar proyecto", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        File file = null;
        if (project == null && !saveas)
        {
            JFileChooser select = new JFileChooser();
            if (filechooser.getCurrentDirectory() != null)
            {
                select.setCurrentDirectory(filechooser.getCurrentDirectory());
            }
            select.setFileFilter(new FileFilter()
            {

                @Override
                public boolean accept(File f)
                {
                    return f.isDirectory() || f.getName().endsWith(PROYECT_EXTENSION);
                }

                @Override
                public String getDescription()
                {
                    return "Archivos de proyectos de generación de código (" + PROYECT_EXTENSION + ")";
                }
            });
            select.setMultiSelectionEnabled(false);
            select.setDialogTitle("Guardar proyecto de generación de código");

            int res = select.showSaveDialog(this.getFrame());
            if (res == JFileChooser.CANCEL_OPTION)
            {
                return;
            }
            else
            {
                file = select.getSelectedFile();
                if (!file.getName().endsWith(PROYECT_EXTENSION))
                {
                    file = new File(file.getAbsoluteFile() + PROYECT_EXTENSION);
                }
            }
        }
        else
        {
            file = project;
        }
        String path = file.getAbsolutePath();
        String temppath = file.getParentFile().getAbsolutePath() + File.pathSeparatorChar + file.getName() + ".bk";
        if (file.exists())
        {
            file.renameTo(new File(temppath));
        }
        try
        {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
            out.putNextEntry(new ZipEntry("base.properties"));
            byte[] cont = this.createBaseReferencesToProjectOWLS(file);
            out.write(cont, 0, cont.length);
            out.closeEntry();

            out.putNextEntry(new ZipEntry("owls.properties"));
            cont = this.createProjectOWLS(file);
            out.write(cont, 0, cont.length);


            out.putNextEntry(new ZipEntry("namespaces.properties"));
            cont = this.createNamespaces();
            out.write(cont, 0, cont.length);


            out.putNextEntry(new ZipEntry("config.properties"));
            cont = this.writeConfig();
            out.write(cont, 0, cont.length);

            out.close();

            new File(temppath).delete();
            project = file;
            this.getFrame().setTitle(project.getAbsolutePath());
            JOptionPane.showMessageDialog(this.getFrame(), "Se ha guardado el proyecto " + project.getName() + "", "Guardar", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);

        }
        catch (Exception e)
        {
            file = new File(temppath);
            file.renameTo(new File(path));
            JOptionPane.showMessageDialog(this.getFrame(), "Error al crear proyecto\r\n" + e.getMessage(), "Error al crear proyecto", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        }

    }
    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemSaveActionPerformed
    {//GEN-HEADEREND:event_jMenuItemSaveActionPerformed
        createProyect(false);
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemopenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemopenActionPerformed
    {//GEN-HEADEREND:event_jMenuItemopenActionPerformed
        openProyect();
    }//GEN-LAST:event_jMenuItemopenActionPerformed

    private void jMenuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemSaveAsActionPerformed
    {//GEN-HEADEREND:event_jMenuItemSaveAsActionPerformed
        createProyect(true);
    }//GEN-LAST:event_jMenuItemSaveAsActionPerformed

    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemNewActionPerformed
    {//GEN-HEADEREND:event_jMenuItemNewActionPerformed

        ResourceMap resourceMap = getResourceMap();
        this.getFrame().setTitle(resourceMap.getString("Application.title"));
        this.owls = new HashSet<OWL>();
        this.dc.setOWLBaseProyect(new OWL[0]);
        this.jTextField1.setText("");
        this.project = null;
        loadOWL();

    }//GEN-LAST:event_jMenuItemNewActionPerformed

    private void jMenuItemGenerateCodeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemGenerateCodeActionPerformed
    {//GEN-HEADEREND:event_jMenuItemGenerateCodeActionPerformed
        generateCode();
    }//GEN-LAST:event_jMenuItemGenerateCodeActionPerformed

    private void jMenuToolsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuToolsActionPerformed
    {//GEN-HEADEREND:event_jMenuToolsActionPerformed
    }//GEN-LAST:event_jMenuToolsActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDeleteActionPerformed
    {//GEN-HEADEREND:event_jButtonDeleteActionPerformed
        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        if (this.jTable1.getSelectedRow() != -1)
        {
            OWL owl = (OWL) model.getValueAt(this.jTable1.getSelectedRow(), 0);
            int res = JOptionPane.showConfirmDialog(null,"¿Desea eliminar el owl " + owl + "?", "Eliminar OWL", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION)
            {
                owls.remove(owl);
                loadOWL();
            }
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed
    private void openProyect()
    {
        JFileChooser select = new JFileChooser();
        if (filechooser.getCurrentDirectory() != null)
        {
            select.setCurrentDirectory(filechooser.getCurrentDirectory());
        }
        select.setFileFilter(new FileFilter()
        {

            @Override
            public boolean accept(File f)
            {
                return f.isDirectory() || f.getName().endsWith(PROYECT_EXTENSION);
            }

            @Override
            public String getDescription()
            {
                return "Archivos de proyectos de generación de código (" + PROYECT_EXTENSION + ")";
            }
        });
        select.setMultiSelectionEnabled(false);
        select.setDialogTitle("Abrir proyecto de generación de código");

        int res = select.showOpenDialog(this.getFrame());
        if (res == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
        else
        {
            File file = select.getSelectedFile();
            if (file.equals(project))
            {
                return;
            }
            try
            {
                ZipFile zip = new ZipFile(file);
                Enumeration entries = zip.entries();
                while (entries.hasMoreElements())
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.getName().equals("base.properties"))
                    {
                        InputStream in = zip.getInputStream(entry);
                        byte[] buf = new byte[1024];
                        int len;
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        while ((len = in.read(buf)) > 0)
                        {
                            out.write(buf, 0, len);
                        }
                        readBaseReferencesToProjectOWLS(out.toByteArray(), file.getParentFile());

                    }
                    if (entry.getName().equals("owls.properties"))
                    {
                        InputStream in = zip.getInputStream(entry);
                        byte[] buf = new byte[1024];
                        int len;
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        while ((len = in.read(buf)) > 0)
                        {
                            out.write(buf, 0, len);
                        }
                        readProjectOWLS(out.toByteArray(), file.getParentFile());

                    }
                    if (entry.getName().equals("namespaces.properties"))
                    {
                        InputStream in = zip.getInputStream(entry);
                        byte[] buf = new byte[1024];
                        int len;
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        while ((len = in.read(buf)) > 0)
                        {
                            out.write(buf, 0, len);
                        }
                        readNamespaces(out.toByteArray());
                    }
                    if (entry.getName().equals("config.properties"))
                    {
                        InputStream in = zip.getInputStream(entry);
                        byte[] buf = new byte[1024];
                        int len;
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        while ((len = in.read(buf)) > 0)
                        {
                            out.write(buf, 0, len);
                        }
                        readConfig(out.toByteArray());
                    }
                }
                this.project = file;
                this.getFrame().setTitle(project.getAbsolutePath());
            }
            catch (Exception ze)
            {
                JOptionPane.showMessageDialog(this.getFrame(), "No se puede abrir el archivo de pryecto\r\n" + ze.getMessage(), "Error al abrir proyecto", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);

            }


        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JButton jButtonSelectDir;
    private javax.swing.JMenuItem jMenuItemAdd;
    private javax.swing.JMenuItem jMenuItemConfiguration;
    private javax.swing.JMenuItem jMenuItemGenerateCode;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenuItem jMenuItemopen;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
