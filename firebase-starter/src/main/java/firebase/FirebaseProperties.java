package firebase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Quangnv 2018/03/23
 */
@ConfigurationProperties(prefix = "firebase")
@Data
@SuppressWarnings("WeakerAccess")
public class FirebaseProperties {


    private String filePath ="E:/WEBRTC/firebase-starter/demoUsingFirStarter/src/test/resources/org.google/firebase.json";


    private String databaseUrl = "https://local-dr.firebaseio.com";


    private String bucket = "local-dr.appspot.com";
}
