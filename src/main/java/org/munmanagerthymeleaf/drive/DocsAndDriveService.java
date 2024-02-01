package org.munmanagerthymeleaf.drive;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.munmanagerthymeleaf.drive.DocsService.*;
import static org.munmanagerthymeleaf.drive.DriveService.driveGetCredentials;
import static org.munmanagerthymeleaf.drive.DriveService.getDriveService;

@UtilityClass
public class DocsAndDriveService {

    int getWordCountOfGetBodyGetContentToString(String contentString) {
        StringBuilder textContent = new StringBuilder();

        while (contentString.contains("content")) {
            int index = contentString.indexOf("\"content\":\"");
            contentString = contentString.substring(index + 11);
            int index2 = contentString.indexOf("\"");
            String text = contentString.substring(0, index2);
            textContent.append(text).append(" ");
        }

        String s = textContent.toString().replace("\\n", "")
                .replace("\\t", "")
                .replace("\\r", "")
                .replace("\\\"", "")
                .replace("\\", "");

        while (s.contains("  ")) {
            s = s.replace("  ", " ");
        }

        return s.isEmpty() ? 0 : s.split(" ").length;
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Docs docsService = getDocsService(HTTP_TRANSPORT);
        Drive driveService = getDriveService(HTTP_TRANSPORT);

        FileList result = driveService.files()
                .list()
                .setPageSize(10)
                .setFields("nextPageToken, files(mimeType, createdTime, id, name)").execute();

        List<File> files = result.getFiles();
        for (File file : files) {
            if (file.getMimeType().equals("application/vnd.google-apps.document")) {
                System.out.print(file.getName() + " Created at: " + file.getCreatedTime() + " ID: " + file.getId() + " " + "Word count: ");
                System.out.println(getWordCountOfGetBodyGetContentToString(docsService.documents()
                        .get(file.getId()).execute().getBody().getContent().toString()));
            }
        }
    }
}