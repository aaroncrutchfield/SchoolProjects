package net.wcc.ajcrutchfield.jh4_ajcrutchfield;

/**
 * Created by AaronC on 10/19/2015.
 */
public interface FileOperations {
    // Commented out callbacks will be useful for JH4

    public void newFile(String category);
    public void open(String category);
    public void delete(String category);

    public void replaceWord(int index, String value);
}