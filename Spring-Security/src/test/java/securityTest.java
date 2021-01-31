import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
//盐值加密算法

public class securityTest {


    private String proEncoder(String input){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(input);
        return encode;
    }

    private String isSimple(String input){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String s = proEncoder(input);
        String s= "$2a$10$8NqfLE4OcOA9LwCwJUxA2eVTMT9Srio13Y4hpmKY9ogZUHndXsWAO";
        return encoder.matches("123456",s)?"一致":"不一致";
    }



    public static void main(String[] args) {
        securityTest securityTest = new securityTest();
        String simple = securityTest.isSimple("123456");
        System.out.println(simple);
    }
}
