package com.backdoored.api;

import java.util.List;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.security.GeneralSecurityException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.auth.oauth2.Credential;

class GoogleCloudApi
{
    private static String SPREADSHEET_ID;
    
    GoogleCloudApi() {
        super();
    }
    
    private static Credential getCredential() throws IOException {
        final InputStream is = GoogleCloudApi.class.getResourceAsStream("/resources/backdoored-client-340b78ae95c4.json");
        final Credential credential = (Credential)GoogleCredential.fromStream(is).createScoped((Collection)Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));
        return credential;
    }
    
    private static Sheets getSheetsService() throws GeneralSecurityException, IOException {
        final Sheets sheets = new Sheets.Builder((HttpTransport)GoogleNetHttpTransport.newTrustedTransport(), (JsonFactory)JacksonFactory.getDefaultInstance(), (HttpRequestInitializer)getCredential()).setApplicationName("Backdoored License Handler").build();
        return sheets;
    }
    
    private static String getFirstdataFromRange(final String range) throws IOException, GeneralSecurityException {
        final ValueRange readFromDB = (ValueRange)getSheetsService().spreadsheets().values().get(GoogleCloudApi.SPREADSHEET_ID, range).execute();
        for (int n = 0; n < readFromDB.getValues().size(); ++n) {
            System.out.println(readFromDB.getValues().get(n));
        }
        final String result = (readFromDB.getValues() != null) ? readFromDB.getValues().get(0).toString() : "";
        return result;
    }
    
    private static List<List<Object>> getFullData(final String range) throws IOException, GeneralSecurityException {
        final ValueRange readFromDB = (ValueRange)getSheetsService().spreadsheets().values().get(GoogleCloudApi.SPREADSHEET_ID, range).execute();
        return (List<List<Object>>)readFromDB.getValues();
    }
    
    public static boolean foundToken(final String usertoken) throws IOException, GeneralSecurityException {
        final ValueRange readFromDB = (ValueRange)getSheetsService().spreadsheets().values().get(GoogleCloudApi.SPREADSHEET_ID, "A2:A").execute();
        for (int n = 0; n < readFromDB.getValues().size(); ++n) {
            if (readFromDB.getValues().get(n).toString().replace("[", "").replace("]", "").equals(usertoken)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        GoogleCloudApi.SPREADSHEET_ID = "1_kxn8nNafDEUPpKNZ6ozlUaASlODC_Sf9hIniJvH33E";
    }
}
