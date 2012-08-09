package signatureapplet.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author serch
 */
public class ExtensionFileFilter extends FileFilter {

    private String description;
    private String extension;

    public ExtensionFileFilter(String description, String extension) {
        this.description = description;
        this.extension = extension.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                return true;
            } else {
                return false;
            }
        }
    }
}
