package firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Quangnv 2018/03/23
 */
@Configuration
@ConditionalOnClass({FirebaseAuth.class, FirebaseDatabase.class, StorageClient.class})
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseAutoConfiguration implements DisposableBean {

    // Instance variables
    // ------------------------------------------------------------------------
    // Firebase properties
    private final FirebaseProperties properties;
    // Firebase Application
    private FirebaseApp firebaseApp;

    // Constructor
    // ------------------------------------------------------------------------
    @Autowired public FirebaseAutoConfiguration(FirebaseProperties properties) {
        this.properties = properties;
    }

    // Bean
    // ------------------------------------------------------------------------
    /**
     * FirebaseAuthクライアント.
     */
    @ConditionalOnMissingBean
    @Bean
    public FirebaseAuth firebaseAuth() {
        initialize();
        return FirebaseAuth.getInstance();
    }

    /**
     * FirebaseDatabaseクライアント.
     */
    @ConditionalOnMissingBean
    @Bean
    public FirebaseDatabase database() {
        initialize();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(false);
        return database;
    }

    /**
     * FirebaseStorageクライアント.
     */
    @ConditionalOnMissingBean
    @Bean
    public StorageClient storage() {
        initialize();
        return StorageClient.getInstance();
    }

    // Private methods
    // ------------------------------------------------------------------------
    private synchronized void initialize() {
        if (firebaseApp == null) {
            try {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(reader()))
                        .setDatabaseUrl(properties.getDatabaseUrl())
                        .setStorageBucket(properties.getBucket())
                        .build();
                firebaseApp = FirebaseApp.initializeApp(options);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // DisposableBean methods
    // ------------------------------------------------------------------------
    public void destroy() {
        if (firebaseApp != null) {
            firebaseApp.delete();
        }
    }


    // Private methods
    // ------------------------------------------------------------------------
    private InputStream reader() throws IOException {
        try {
            return new FileInputStream(properties.getFilePath());
        } catch (IOException e) {
            return new ClassPathResource(properties.getFilePath()).getInputStream();
        }
    }
}
