// Deploy webpage
package com.nikhilbarthwal.WebPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class Deploy
{
    private static boolean validFile(String Filename)
    {
        return true; //TODO: Fix this!
    }
    private static void uploadDirectory(FTPClient ftpClient, String remoteDir, String localParentDir, String remoteParentDir) throws IOException
    {
        if (ftpClient.makeDirectory(remoteDir))
            System.out.println("CREATED the directory: " + remoteDir);
        else
        {
            System.out.println("COULD NOT create the directory: " + remoteDir);
            return;
        }

        System.out.println("LISTING directory: " + localParentDir);
        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();

        if (subFiles != null && subFiles.length > 0)
            for (File item : subFiles)
            {
                String remoteFilePath = remoteDir + "/" + remoteParentDir + "/" + item.getName();

                if (remoteParentDir.equals(""))
                    remoteFilePath = remoteDir + "/" + item.getName();

                if (item.isFile())
                {
                    // upload the file
                    String localFilePath = item.getAbsolutePath();
                    // TODO: Check if local File Path should be uploaded or not
                    System.out.println("About to upload the file: " + localFilePath);
                    if (uploadSingleFile(ftpClient, localFilePath, remoteFilePath))
                        System.out.println("UPLOADED a file to: " + remoteFilePath);
                    else
                        System.out.println("COULD NOT upload the file: " + localFilePath);

                }
                else
                {

                    // upload the sub directory
                    String parent = remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals(""))
                        parent = item.getName();

                    localParentDir = item.getAbsolutePath();
                    uploadDirectory(ftpClient, remoteDir, localParentDir, parent);
                }
            }
    }

    private static boolean uploadSingleFile(FTPClient ftpClient, String localFilePath, String remoteFilePath) throws IOException
    {
        File localFile = new File(localFilePath);

        InputStream inputStream = new FileInputStream(localFile);

        try
        {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(remoteFilePath, inputStream);
        }
        finally
        {
            inputStream.close();
        }
    }

    public static void main(String[] args) throws IOException
    {
        final int port = 21;
        final String localDirPath = args[0];
        final String server = (!args[1].equals("DEFAULT")) ? args[1] : System.getenv("SERVER");
        final String user   = (!args[2].equals("DEFAULT")) ? args[2] : System.getenv("USER");
        final String pass   = (!args[3].equals("DEFAULT")) ? args[3] : System.getenv("PASSWORD");

        FTPClient ftpClient = new FTPClient();

        // connect and login to the server
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);

        // use local passive mode to pass firewall
        ftpClient.enterLocalPassiveMode();

        System.out.println("Connected");
        final String remoteDirPath = "test1/";

        uploadDirectory(ftpClient, remoteDirPath, localDirPath, remoteDirPath);

        // log out and disconnect from the server
        ftpClient.logout();
        ftpClient.disconnect();
        System.out.println("Disconnected");
    }
}
