package applets.edit;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.JTextComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public class XSLRootPane extends JRootPane implements HyperlinkListener, SyntaxConstants {

    private RTextScrollPane scrollPane;
    private RSyntaxTextArea textArea;
    private String filename;
    private JMenuItem localJMenuItem;
    private JTextComponent textComponentPrueba;

    public XSLRootPane() {
        textArea = createTextArea();
        //setContent("JavaExample.txt");
        //textArea.setText("Hola mundo");
        System.out.println("xslrootpane");
        textArea.setSyntaxEditingStyle("text/xml");
        this.scrollPane = new RTextScrollPane(textArea, true);
        Gutter localGutter = this.scrollPane.getGutter();
        localGutter.setBookmarkingEnabled(true);
        URL localURL = getClass().getClassLoader().getResource("bookmark.png");
        localGutter.setBookmarkIcon(new ImageIcon(localURL));
        getContentPane().add(this.scrollPane);
        setJMenuBar(createMenuBar());


    }

//  private void addItem(String paramString1, String paramString2, String paramString3, ButtonGroup paramButtonGroup, JMenu paramJMenu)
//  {
//    JRadioButtonMenuItem localJRadioButtonMenuItem = new JRadioButtonMenuItem(new ChangeSyntaxStyleAction(paramString1, paramString2, paramString3));
//
//    paramButtonGroup.add(localJRadioButtonMenuItem);
//    paramJMenu.add(localJRadioButtonMenuItem);
//  }
    private JMenuBar createMenuBar() {
        JMenuBar localJMenuBar = new JMenuBar();
        System.out.println("createmenubar");

        //    JMenu localJMenu = new JMenu("Language");
        //    ButtonGroup localButtonGroup = new ButtonGroup();
        //    addItem("C", "CExample.txt", "text/c", localButtonGroup, localJMenu);
        //    addItem("Java", "JavaExample.txt", "text/java", localButtonGroup, localJMenu);
        //    addItem("Perl", "PerlExample.txt", "text/perl", localButtonGroup, localJMenu);
        //    addItem("Ruby", "RubyExample.txt", "text/ruby", localButtonGroup, localJMenu);
        //    addItem("SQL", "SQLExample.txt", "text/sql", localButtonGroup, localJMenu);
        //    addItem("XML", "XMLExample.txt", "text/mxml", localButtonGroup, localJMenu);
        //    localJMenu.getItem(1).setSelected(true);
        //    localJMenuBar.add(localJMenu);
        JMenu localJMenu = new JMenu("Archivo");

        localJMenuItem = new JMenuItem(new SaveAction());



        localJMenu.add(localJMenuItem);
        localJMenuItem.setEnabled(false);

        localJMenuBar.add(localJMenu);

       /* JMenuItem localJMenuItemClose = new JMenuItem("Salir");
        localJMenu.add(localJMenuItemClose);
        localJMenuBar.add(localJMenu);
        localJMenuItemClose.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evento) {
                        System.exit(0);
                    }
                }
                );
*/


        localJMenu = new JMenu("Ver");
        JCheckBoxMenuItem localJCheckBoxMenuItem = new JCheckBoxMenuItem(new MonospacedFontAction());
        localJCheckBoxMenuItem.setSelected(true);
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new ViewLineHighlightAction());
        localJCheckBoxMenuItem.setSelected(true);
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new ViewLineNumbersAction());
        localJCheckBoxMenuItem.setSelected(true);
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new AnimateBracketMatchingAction());
        localJCheckBoxMenuItem.setSelected(true);
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new BookmarksAction());
        localJCheckBoxMenuItem.setSelected(true);
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new WordWrapAction());
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new ToggleAntiAliasingAction());
        localJMenu.add(localJCheckBoxMenuItem);
        localJCheckBoxMenuItem = new JCheckBoxMenuItem(new MarkOccurrencesAction());
        localJCheckBoxMenuItem.setSelected(true);
        localJMenu.add(localJCheckBoxMenuItem);

        localJMenuBar.add(localJMenu);

        localJMenu = new JMenu("Ayuda");
        JMenuItem localJMenuItemHelp = new JMenuItem(new AboutAction());
        localJMenu.add(localJMenuItemHelp);
        localJMenuBar.add(localJMenu);

        return localJMenuBar;
    }

    private RSyntaxTextArea createTextArea() {
        System.out.println("createarea");
        RSyntaxTextArea localRSyntaxTextArea = new RSyntaxTextArea(25, 70);
        localRSyntaxTextArea.setCaretPosition(0);
        localRSyntaxTextArea.addHyperlinkListener(this);
        localRSyntaxTextArea.requestFocusInWindow();
        localRSyntaxTextArea.setMarkOccurrences(true);
        localRSyntaxTextArea.setTextAntiAliasHint("VALUE_TEXT_ANTIALIAS_ON");
        return localRSyntaxTextArea;
    }

    void focusTextArea() {
        textArea.requestFocusInWindow();
        textArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                localJMenuItem.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                localJMenuItem.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                localJMenuItem.setEnabled(true);

            }
        });
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent paramHyperlinkEvent) {
        System.out.println("hyperlinkupdate");
        if (paramHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL localURL = paramHyperlinkEvent.getURL();
            if (localURL == null) {
                UIManager.getLookAndFeel().provideErrorFeedback(null);
            } else {
                JOptionPane.showMessageDialog(this, "URL clicked:\n" + localURL.toString());
            }
        }
    }

    public void setText(String text) {
        System.out.println("settext");
        textArea.setText(text);
    }

    public void setContent(String filename) {
        System.out.println("setcontent");
        //ClassLoader localClassLoader = getClass().getClassLoader();
        BufferedReader localBufferedReader = null;
        try {
            //localBufferedReader = new BufferedReader(new InputStreamReader(localClassLoader.getResourceAsStream(filename), "UTF-8"));
            System.out.println("XSLRootPane.......");
            System.out.println("filename=" + filename);
            localBufferedReader = new BufferedReader(new FileReader(filename));
            textArea.read(localBufferedReader, null);
            localBufferedReader.close();
            this.filename = filename;

            textArea.setCaretPosition(0);
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Exception localException) {
            textArea.setText("Type here to see syntax highlighting");
        }
    }

    private class WordWrapAction extends AbstractAction {

        public WordWrapAction() {
            putValue("Name", "Word Wrap");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            XSLRootPane.this.textArea.setLineWrap(!XSLRootPane.this.textArea.getLineWrap());
        }
    }

    private class ViewLineNumbersAction extends AbstractAction {

        public ViewLineNumbersAction() {
            putValue("Name", "Line Numbers");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            XSLRootPane.this.scrollPane.setLineNumbersEnabled(!XSLRootPane.this.scrollPane.getLineNumbersEnabled());
        }
    }

    private class ViewLineHighlightAction extends AbstractAction {

        public ViewLineHighlightAction() {
            putValue("Name", "Current Line Highlight");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            XSLRootPane.this.textArea.setHighlightCurrentLine(!XSLRootPane.this.textArea.getHighlightCurrentLine());
        }
    }

    private class ToggleAntiAliasingAction extends AbstractAction {

        private boolean selected;

        public ToggleAntiAliasingAction() {
            putValue("Name", "Anti-Aliasing");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            this.selected = (!this.selected);
            String str = this.selected ? "VALUE_TEXT_ANTIALIAS_ON" : null;
            XSLRootPane.this.textArea.setTextAntiAliasHint(str);
        }
    }

    private class MonospacedFontAction extends AbstractAction {

        private boolean selected;

        public MonospacedFontAction() {
            putValue("Name", "Monospaced Font");
            this.selected = true;
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            this.selected = (!this.selected);
            if (this.selected) {
                XSLRootPane.this.textArea.setFont(RSyntaxTextArea.getDefaultFont());
            } else {
                Font localFont = new Font("Dialog", 0, 13);
                XSLRootPane.this.textArea.setFont(localFont);
            }
        }
    }

    private class MarkOccurrencesAction extends AbstractAction {

        public MarkOccurrencesAction() {
            putValue("Name", "Mark Occurrences");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            XSLRootPane.this.textArea.setMarkOccurrences(!XSLRootPane.this.textArea.getMarkOccurrences());
        }
    }

    /*private class ChangeSyntaxStyleAction extends AbstractAction {
    private String res;
    private String style;

    public ChangeSyntaxStyleAction(String paramString1, String paramString2, String arg4) {
    putValue("Name", paramString1);
    this.res = paramString2;
    //Object localObject;
    //this.style = localObject;
    }

    @Override
    public void actionPerformed(ActionEvent paramActionEvent) {
    System.out.println("\n\n...................\nactionPerformed...");
    XSLRootPane.this.setContent(this.res);
    XSLRootPane.this.textArea.setCaretPosition(0);
    XSLRootPane.this.textArea.setSyntaxEditingStyle(this.style);
    }
    }*/
    private class BookmarksAction extends AbstractAction {

        public BookmarksAction() {
            putValue("Name", "Bookmarks");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            XSLRootPane.this.scrollPane.setIconRowHeaderEnabled(!XSLRootPane.this.scrollPane.isIconRowHeaderEnabled());
        }
    }

    private class AnimateBracketMatchingAction extends AbstractAction {

        public AnimateBracketMatchingAction() {
            putValue("Name", "Animate Bracket Matching");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            XSLRootPane.this.textArea.setAnimateBracketMatching(!XSLRootPane.this.textArea.getAnimateBracketMatching());
        }
    }

    private class AboutAction extends AbstractAction {

        public AboutAction() {
            putValue("Name", "CÃ³mo usar el editor...");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {

            JOptionPane.showMessageDialog(XSLRootPane.this, 
                      "El editor de texto es utilizado para"
                    + "\ncorregir o editar el cÃ³digo fuente"
                    + "\ndel archivo XSL de la plantilla actual.\n", "Ayuda", 1);
        }
    }

    private class SaveAction extends AbstractAction {

        public SaveAction() {
            putValue("Name", "Guardar");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            //JOptionPane.showMessageDialog(XSLRootPane.this, "<html><b>RSyntaxTextArea</b> - A Swing syntax highlighting text component<br>Version 1.5.0<br>Licensed under the LGPL", "About RSyntaxTextArea", 1);

            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(filename));
                textArea.write(bw);



            } catch (FileNotFoundException ex) {
                ex.printStackTrace(System.out);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            } finally {
                //Close the BufferedWriter
                try {
                    if (bw != null) {
                        bw.flush();
                        bw.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }
    }

    /* private class CloseAction extends AbstractAction {

    public CloseAction() {
    putValue("Name", "Salir");
    }

    @Override
    public void actionPerformed(ActionEvent paramActionEvent) {
    JOptionPane.showMessageDialog(XSLRootPane.this, "AdiÃ³s", "falta codigo", 1);
    }
    }*/
}
