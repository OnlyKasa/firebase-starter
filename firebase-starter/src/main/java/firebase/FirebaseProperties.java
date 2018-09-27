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


    private String filePath;


    private String databaseUrl = "";


    private String bucket = "";
}
