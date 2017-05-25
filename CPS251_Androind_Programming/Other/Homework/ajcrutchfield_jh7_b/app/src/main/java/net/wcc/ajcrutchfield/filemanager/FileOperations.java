package net.wcc.ajcrutchfield.filemanager;

/**
 * Created by AaronC on 10/19/2015.
 */
public interface FileOperations {
    // Commented out callbacks will be useful for JH4

    public void newFile(String filename);
    public void open(String filename);
    public void delete(String filename);
}