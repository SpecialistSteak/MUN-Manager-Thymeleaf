package org.munmanagerthymeleaf.drive;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.munmanagerthymeleaf.drive.DocsService.getDocsService;
import static org.munmanagerthymeleaf.drive.DocsService.getWordCountOfGetBodyGetContentToString;
import static org.munmanagerthymeleaf.drive.DriveService.*;

@UtilityClass
public class DocsAndDriveService {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Docs docsService = getDocsService(HTTP_TRANSPORT);
        Drive driveService = getDriveService(HTTP_TRANSPORT);

        shareFile(driveService,"1l2zs2UGvnGKUkuuUglfdSENeBTRhGYl1", "msmun@aischennai.org");

//        FileList result = driveService.files()
//                .list()
//                .setPageSize(1000)
//                .setFields("nextPageToken, files(mimeType, createdTime, id, name)").execute();
//
//        List<File> files = result.getFiles();
//        for (File file : files) {
//            if (file.getMimeType().equals("application/vnd.google-apps.document")) {
//                System.out.print(file.getName() + " Created at: " + file.getCreatedTime() +
//                        " ID: " + file.getId() + " " + "Word count: ");
//                System.out.println(getWordCountOfGetBodyGetContentToString(docsService.documents()
//                        .get(file.getId()).execute().getBody().getContent().toString()));
//            } else {
//                System.out.println(
//                        file.getName() + " Created at: " + file.getCreatedTime() + " ID: " + file.getId() + " File Type: " + file.getMimeType());
//            }
//        }

//        createNestedFolder("1iXrtaYFPvstjLWbeCzKCa4Lo8sNfabdC", driveService, "NESTED");
    }
}