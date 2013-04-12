package applets.edit;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public class XSLRootPane extends JRootPane implements HyperlinkListener, SyntaxConstants {

    private RTextScrollPane scrollPane;
    private RSyntaxTextArea textArea;
    private String filename;
    private boolean isDefaultTemplate;
    private JMenuItem localJMenuItem;
    private JMenuItem localJMenuItemSaveAs;
    private Clipboard portapapeles;
    private String textoCopiado;

    public XSLRootPane() {
        this(true);
    }

    public XSLRootPane(boolean isDefaultTemplate) {
        this.isDefaultTemplate = isDefaultTemplate;
        
        textArea = createTextArea();        
        textArea.setSyntaxEditingStyle("text/xml");
        scrollPane = new RTextScrollPane(textArea, true);
        Gutter localGutter = scrollPane.getGutter();
        localGutter.setBookmarkingEnabled(true);
        URL localURL = getClass().getClassLoader().getResource("bookmark.png");
        localGutter.setBookmarkIcon(new ImageIcon(localURL));
        getContentPane().add(scrollPane);
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
        if (!isDefaultTemplate) {            
            localJMenu.add(localJMenuItem);
            //El boton Guardar esta desactivado
            localJMenuItem.setEnabled(false);
            localJMenuBar.add(localJMenu);
            localJMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localJMenuItem.setEnabled(false);
            }
        });
        }   
        //BOTON GUARDAR COMO
        localJMenuItemSaveAs = new JMenuItem(new SaveAsAction());
        localJMenu.add(localJMenuItemSaveAs);
        localJMenuBar.add(localJMenu);
        
        
        if (!isDefaultTemplate) {
            localJMenu = new JMenu("Editar");
            JMenuItem localJMenuItemasteUndo = new JMenuItem("Deshacer");
            localJMenu.add(localJMenuItemasteUndo);
            localJMenuItemasteUndo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.undoLastAction();
                }
            });

            JMenuItem localJMenuItemasteRedo = new JMenuItem("Rehacer");
            localJMenu.add(localJMenuItemasteRedo);
            localJMenuItemasteRedo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.redoLastAction();
                }
            });

            localJMenu.add(new JSeparator());

            JMenuItem localJMenuItemasteCut = new JMenuItem("Cortar");
            localJMenu.add(localJMenuItemasteCut);
            localJMenuItemasteCut.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    int inicio = textArea.getSelectionStart();
                    int fin = textArea.getSelectionEnd();
                    String s = textArea.getText();
                    textoCopiado = s.substring(inicio, fin);
                    portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection texto = new StringSelection("" + textoCopiado);
                    portapapeles.setContents(texto, texto);
                    textArea.cut();
                }
            });

            JMenuItem localJMenuItemCopy = new JMenuItem("Copiar");
            localJMenu.add(localJMenuItemCopy);
            localJMenuItemCopy.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.copy();
                    int inicio = textArea.getSelectionStart();
                    int fin = textArea.getSelectionEnd();
                    String s = textArea.getText();
                    textoCopiado = s.substring(inicio, fin);
                    portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection texto = new StringSelection("" + textoCopiado);
                    portapapeles.setContents(texto, texto);
                }
            });

            JMenuItem localJMenuItemPaste = new JMenuItem("Pegar");
            localJMenu.add(localJMenuItemPaste);
            localJMenuItemPaste.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.paste();
                }
            });

            JMenuItem localJMenuItemDelete = new JMenuItem("Eliminar");
            localJMenu.add(localJMenuItemDelete);
            localJMenuItemDelete.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                textArea.cut();
                }
            });

            localJMenu.add(new JSeparator());

            JMenuItem localJMenuItemSelectAll = new JMenuItem("Seleccionar todo");
            localJMenu.add(localJMenuItemSelectAll);
            localJMenuItemSelectAll.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.selectAll();
                }
            });


            localJMenuBar.add(localJMenu);
        }
        
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

        JMenuItem localJMenuItemExit = new JMenuItem(new AboutActionExit());
        localJMenu.add(localJMenuItemExit);
        localJMenuBar.add(localJMenu);

        return localJMenuBar;
    }

    private RSyntaxTextArea createTextArea() {
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
        if (paramHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL localURL = paramHyperlinkEvent.getURL();
            if (localURL == null) {
                UIManager.getLookAndFeel().provideErrorFeedback(null);
            }else {
                JOptionPane.showMessageDialog(this, "URL clicked:\n" + localURL.toString());
            }
        }
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void setContent(String filename) {
        //ClassLoader localClassLoader = getClass().getClassLoader();
        BufferedReader localBufferedReader = null;
        try {
            localBufferedReader = new BufferedReader(new FileReader(filename));
            textArea.read(localBufferedReader, null);
            localBufferedReader.close();
            this.filename = filename;
            textArea.setCaretPosition(0);
            //archivo default, no editable
            if(isDefaultTemplate) {
                textArea.setEditable(false);
            }
        }catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        }catch (Exception localException) {
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
            putValue("Name", "Cómo usar este editor...");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            if( !isDefaultTemplate )
            {
                JOptionPane.showMessageDialog(XSLRootPane.this,
                        "El editor de texto es utilizado para corregir o editar el "
                        + "\narchivo XSL de la plantilla actual.", "Ayuda", 1);
            }
            else
            {
                JOptionPane.showMessageDialog(XSLRootPane.this,
                        "Puede guardar una copia del archivo para modificarla y reemplazar la plantilla actual.", "Ayuda", 1);
            }
        }
    }

    private class AboutActionExit extends AbstractAction {

        public AboutActionExit() {
            putValue("Name", "Cómo salir del editor...");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            JOptionPane.showMessageDialog(XSLRootPane.this,
                    "Para salir del editor seleccione la pestaña Administrar.", "Ayuda", 1);
        }
    }

    private class SaveAction extends AbstractAction {
        
        public SaveAction() {
            if( !isDefaultTemplate ) {
                putValue("Name", "Guardar");
            }else {
                putValue("Name", "Guardar como...");
            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isDefaultTemplate) {
                String texto = textArea.getText();
                JFileChooser chooser = new JFileChooser();

                int retval = chooser.showSaveDialog(null);
                if (retval == JFileChooser.APPROVE_OPTION) {
                    PrintWriter printwriter = null;
                    try {
                        File JFC = chooser.getSelectedFile();
                        String PATH = JFC.getAbsolutePath();
                        printwriter = new PrintWriter(JFC);
                        printwriter.print(texto);
                        printwriter.close();

                        if (!(PATH.endsWith(".xsl"))) {
                            File temp = new File(PATH + ".xsl");
                            JFC.renameTo(temp);//renombramos el archivo
                        }
                        return;
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        printwriter.close();
                    }


                }
            }else {
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
    }
    
    private class SaveAsAction extends AbstractAction
    {
        public SaveAsAction() {
            putValue("Name", "Guardar como");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String texto = textArea.getText();
            JFileChooser chooser = new JFileChooser();

            int retval = chooser.showSaveDialog(null);
            if (retval == JFileChooser.APPROVE_OPTION) {
                PrintWriter printwriter = null;
                try {
                    File JFC = chooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();
                    printwriter = new PrintWriter(JFC);
                    printwriter.print(texto);
                    printwriter.close();

                    if (!(PATH.endsWith(".xsl"))) {
                        File temp = new File(PATH + ".xsl");
                        JFC.renameTo(temp);//renombramos el archivo
                    }
                    return;
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    printwriter.close();
                }
            }
        }
    }
}
