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
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
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
    private JMenuItem localJMenuItem;
  

    public XSLRootPane() {
        textArea = createTextArea();
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
    
    private JMenuBar createMenuBar() {
        JMenuBar localJMenuBar = new JMenuBar();
        JMenu localJMenu = new JMenu("Archivo");

        localJMenuItem = new JMenuItem(new SaveAction());



        localJMenu.add(localJMenuItem);
        localJMenuItem.setEnabled(false);
        localJMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              localJMenuItem.setEnabled(false);
            }
        });

        localJMenuBar.add(localJMenu);
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
        textArea.setText(text);
    }

    public void setContent(String filename) {
        BufferedReader localBufferedReader = null;
        try {
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
            putValue("Name", "Cómo usar el editor...");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {

            JOptionPane.showMessageDialog(XSLRootPane.this,
                    "Este editor de texto es utilizado para"
                    + "\neditar el archivo XSLT"
                    + "\ndel componente actual.\n", "Ayuda", 1);
        }
    }

    private class SaveAction extends AbstractAction {
        public SaveAction() {
            putValue("Name", "Guardar");
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(filename));
                textArea.write(bw);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace(System.out);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            } finally {
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
