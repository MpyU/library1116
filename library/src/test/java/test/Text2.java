package test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Text2 {
	@org.junit.Test
	public void testMD5() {
		System.out.println("==============MD5================");
		// 2020-11-18 19:12
		// String result = DigestUtils.md5Hex("123"+"2020-11-18 19:15");
		//

		// System.out.println(result);

		// LocalDateTime localDate = LocalDateTime.now();
		// System.out.println("localDate:" +
		// localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));

		String pdate = "2020-11-21";
		LocalDateTime date = LocalDateTime.parse(pdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	}

	public static void main(String args[]) {
		// String pdate = "2020-11-21 00:00:00";
		// LocalDate localDateTime1 = LocalDate.parse("2019-09-26",
		// DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		// System.out.println(localDateTime1);

		// System.out.println(new DateTimeFormatter("yyyy-MM-dd
		// hh:mm:ss").format(Duration.of(20, TimeUnit.HOURS)));
		// DurationFormatUtils.formatDuration(intervalTime, "dd天");
		System.out.println(Duration.ofHours(1));
	}
}
