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
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.DocsScopes;
import com.google.api.services.docs.v1.model.StructuralElement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;


/* Some of this code is taken from the Google Drive Documentation and is not original code. */
public class DocsService {
    static final String APPLICATION_NAME = "mun-management-application";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES =
            Collections.singletonList(DocsScopes.DOCUMENTS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    protected static Credential docsGetCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = DocsService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("admin");
    }

    public static Docs getDocsService(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        return new Docs.Builder(HTTP_TRANSPORT, JSON_FACTORY, docsGetCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static List<StructuralElement> getDocumentBodyContent(String fileId, Docs docsService) throws IOException {
        return docsService.documents().get(fileId).execute().getBody().getContent();
    }

    public static int getWordCountOfGetBodyGetContentToString(String contentString) {
        contentString = getContentBodyOfContentString(contentString);

        return contentString.isEmpty() ? 0 : contentString.split(" ").length;
    }

    /**
     * This method goes through the whole json file, and gets all the text from each content body.
     * @param contentString The string that is gotten from the get content body method.
     * @return The clean text from the content body.
     */
    public static String getContentBodyOfContentString(String contentString) {
        StringBuilder textContent = new StringBuilder();

        while (contentString.contains("\"content\":\"")) {
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
        return s;
    }
}