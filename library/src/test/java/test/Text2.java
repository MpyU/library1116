package test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Text2 {
    @Test
public void testMD5()
{
    System.out.println("==============MD5================");
    //2020-11-18 19:12
    String result = DigestUtils.md5Hex("123"+"2020-11-18 19:15");
//

    System.out.println(result);

    LocalDateTime localDate=LocalDateTime.now();
    System.out.println("localDate:"+localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));


}
}
