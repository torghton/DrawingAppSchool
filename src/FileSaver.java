import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSaver {

    private FileSaver() {}

    public static File promptForSaveTextFile(JFrame jframe) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("please use .txt", "txt", "text");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showSaveDialog(jframe);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File newFile = fileChooser.getSelectedFile();

                if (newFile.createNewFile()) {
                    System.out.println("File created: " + newFile.getName() + " at " + newFile.getAbsolutePath());
                    return newFile;
                } else {
                    System.out.println("File already exists.");
                    return null;
                }
            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }

        return null;
    }

    public static File promptForLoadTextFile(JFrame jframe) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", "text");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(jframe);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File newFile = fileChooser.getSelectedFile();

            if (newFile != null) {
                System.out.println("File found: " + newFile.getName() + " at " + newFile.getAbsolutePath());
                return newFile;
            } else {
                System.out.println("File missing");
                return null;
            }
        }

        return null;
    }

    public static void saveObjectToFile(String fileName, Object object) {
        System.out.println(fileName);
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(object);

            out.close();
            file.close();

            System.out.println("Object has been serialized");


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadObjectFromFile(String fileName) {
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            Object object = in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been de-serialized");

            return object;
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
