package net.wcc.ajcrutchfield.filemanager;

/**
 * Created by AaronC on 10/15/2015.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyAlertDialogHandler implements DialogInterface.OnClickListener
{
    Context context;
    String[] fileList;
    DIALOG_TYPE dialog_type;
    FileManager fileManager;
    FileOperations fileOperations;

    //had to make this public for some reason
    public MyAlertDialogHandler(Context context, FileManager fileManager, String title,
                                FileOperations fileOperations, DIALOG_TYPE dialog_type)
    {
        this.context = context;
        this.dialog_type = dialog_type;
        this.fileManager = fileManager;
        this.fileOperations = fileOperations;

        fileList = fileManager.getFileList();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setItems(fileList, this);
        alertDialog.show();
    }

    public void onClick(DialogInterface dialoginterface, int index)
    {
        String fileName = fileList[index];
        switch(dialog_type)
        {
            case Delete:
                fileOperations.delete(fileName);
                break;
            case Open:
                fileOperations.open(fileName);
                break;
        }
    }

}
