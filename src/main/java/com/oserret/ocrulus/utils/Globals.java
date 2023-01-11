package com.oserret.ocrulus.utils;

import okhttp3.MediaType;

/**
 *
 * @author oserret
 */
public class Globals {

    public static final String USER_ALREADY_PRESENT = "The user you are trying to add is already present into the database";
    public static final String USER_DOES_NOT_EXIST = " - This user id does not exist";
    public static final String USER_DISABLED = "The user is disabled";
    public static final String USER_INVALID_CREDENTIALS = "The user or the password introduced are incorrect";
    public static final String REQUEST_INSERT_ERROR = "The request can't be inserted into the database";
    public static final String FILE_UPLOADED_SUCCESSFULLY ="You successfully uploaded the following file: ";
    public static final String VOID_STRING = "";
    public static final String STATUS_OK = "200";
    public static final String STATUS_KO = "500";
    public static final String STATUS_KO_BAD_REQUEST = "400";
    public static final String PROCESS_STATUS_NOT_PROCESSED = "NOT PROCESSED";
    public static final String PROCESS_STATUS_PROCESSED = "PROCESSED";
    public static final String DATABASE_LOGIN = "1";
    public static final String LDAP_LOGIN = "2";
    public static final String CARRY_RETURN = "\n";
    public static final String TRUE_STRING = "1";
    public static final String FALSE_STRING = "0";
    public static final String LOCALHOST = "localhost";
    public static final int ZERO = 0;
    public static final double ZERO_ZERO = 0.0;
    public static final MediaType CONTENT_JSON = MediaType.get("application/json");
    public static final MediaType CONTENT_XML = MediaType.get("application/xml");
    public static final String PDF_OUTPUT_EXTENSION = ".pdf";
    public static final String DOCX_OUTPUT_EXTENSION = ".docx";
    public static final String PDF_OUTPUT = "pdf";
    public static final String DOCX_OUTPUT = "docx";
    public static final String NOT_DEFINED = "N/A";
    public static final String ZIP_OUTPUT_EXTENSION = ".zip";
    public static final String WHITE_SPACE = " ";
    public static final String HYPHEN = "-";
    public static final String SLASH = "/";
    public static final String UNDERSCORE = "_";

    public static final String MAIL_SUBJECT_MESSAGE = "Your user for Expert.ai Internal Converted has been created";
    public static final String MAIL_BODY_MESSAGE = "Hi,<br>" +
            "<br>" +
            "Your user for the Expert.ai Internal Converter has been created.<br>" +
            "<br>" +
            "Your credentials are written down below this email<br><br>" +
            "#<br>*<br><br>" +
            "<b>URL:</b> $ <br><br>" +
            "Regards,";



    public static final String CNV_STATUS_PROCESSED = "PROCESSED";
    public static final String CNV_STATUS_NOT_PROCESSED = "NOT PROCESSED";

    public static Integer toPersistenceBoolean(Boolean value)
    {
        if (value == null)
            return null;

        if (value == true)
            return 1;

        return 0;
    }






}