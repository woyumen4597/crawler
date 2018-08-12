package search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodeTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String string = URLEncoder.encode("水着", "UTF-8");
        System.out.println(string);
    }

}

