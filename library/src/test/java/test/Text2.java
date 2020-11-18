package test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class Text2 {
    @Test
public void testMD5()
{
    System.out.println("==============MD5================");
    //2020-11-18 19:12
    String result = DigestUtils.md5Hex("123"+"2020-11-18 19:15");
//

    System.out.println(result);


}
}
