# ocrulus-converter

This project uses Springboot as a main framework to execute the application to manage the conversions requests made by users connecting to the APP

## Main class

* **OcrulusApplication:** This class is the main class of the SpringBoot ap to execute the whole application it extends `SpringBootServletInitializer`, this will allow to deploy the application inside a Tomcat.
    
    ``` java
    @SpringBootApplication
    public class OcrulusApplication extends SpringBootServletInitializer {
    ```

## Resources

* **Postman collection:** src/main/resources/postman This path contains the JSON file with the collection of services for the app 
    > *OCRulus.postman_collection.json*
* **Properties:** src/main/resources This path contains the properties to set the global properties for the application
  > **application.properties** <br>
  >* *spring.datasource.url:* URL to the database with the content of the app <br>
  >* *spring.datasource.username:* username owner of the schema ocrulus<br>
  >* *spring.datasource.password:* password to access to the schema ocrulus<br>
  >* *spring.datasource.driver-class-name:* `com.mysql.cj.jdbc.Driver` default driver set to MySQL<br>
  >* *spring.jpa.generate-ddl:* set by default to true for not having ddl files <br>
  >* *file.upload.rootLocation:* location on the server where the files will be uploaded <br>
  >* *file.upload.outputLocation:* location on the server where the output files will be located <br>
  >* *user.login.type:* type of the login, can be: 1 (database) or 2 (LDAP)<br>
  >* *jwt.secret:* word to encrypt the user token<br>
  >* *jwt.get.token.uri:* url where is located the service to generate the jwt token for the user<br>

* **Logging:** src/main/resources This path contains the xml to configure the logging for the app, [here](https://www.baeldung.com/logback) is a guide to configure the output
    > *logback-spring.xml*


## Services

| Service       | Type | URL                                                                                   | Parameters                                                                                                                                                                                          |
|---------------|------|---------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| upload        | POST | http://**host:port**/documents/upload                                                 | **file:** The file to be uploaded                                                                                                                                                                   |
| parseDocument | POST | http://**host:port**/documents/parseDocument/{document_name}?week=[int]&format=[type] | **week:** Number of the week to be analyzed <br> **format:** Type of the output files of the process, accepted values [docx,pdf]                                                                    |
| sendEmail     | POST | http://**host:port**/documents/sendEmail?week=[int]&year=[int]                        | **week:** Number of the week to be analyzed <br> **year:** Current year                                                                                                                             |
| doAll         | POST | http://**host:port**/documents/doAll?week=[int]&year=[int]&format=[type]              | **week:** Number of the week to be analyzed <br> **year:** Current year <br> **file:** The file to be uploaded <br> **format:** Type of the output files of the process, accepted values [docx,pdf] |



