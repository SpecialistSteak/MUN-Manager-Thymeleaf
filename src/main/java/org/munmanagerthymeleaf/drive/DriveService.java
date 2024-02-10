package org.munmanagerthymeleaf.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static org.munmanagerthymeleaf.drive.DocsService.APPLICATION_NAME;

/* Some of this code is taken from the Google Drive Documentation and is not original code. */
@UtilityClass
public class DriveService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = List.of(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Taken from Google's quickstart.
     *
     * @param HTTP_TRANSPORT --.
     * @return credentials
     */
    static Credential driveGetCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = DriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static Drive getDriveService(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, driveGetCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static String createNestedFolder(String parentFolderId, Drive driveService, String folderName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setParents(Collections.singletonList(parentFolderId));

        File file = driveService.files().create(fileMetadata)
                .setFields("id")
                .execute();
        System.out.println("Folder ID: " + file.getId());
        return file.getId();
    }

    public static List<File> getFilesInFolder(Drive driveService, String folderId) throws IOException {
        FileList result = driveService.files().list()
                .setQ("'" + folderId + "' in parents")
                .setPageSize(1000)
                .setFields("nextPageToken, files(name, parents, createdTime, mimeType, id, modifiedTime)")
                .execute();
        return result.getFiles();
    }

    public static boolean shareFile(Drive driveService, String fileId, String emailAddress) {
        try {
            Permission permission = new Permission()
                    .setType("user")
                    .setRole("writer")
                    .setEmailAddress(emailAddress);

            driveService.permissions().create(fileId, permission).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}