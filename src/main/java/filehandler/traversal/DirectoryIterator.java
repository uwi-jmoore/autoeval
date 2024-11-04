package filehandler.traversal;

import java.io.File;

public interface DirectoryIterator {
    public File next();
    public boolean hasNext();
    public int getLength();
}
