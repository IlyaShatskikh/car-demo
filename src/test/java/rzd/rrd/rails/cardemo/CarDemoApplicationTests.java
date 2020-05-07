package rzd.rrd.rails.cardemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootTest(classes = CarDemoApplicationTests.class)
class CarDemoApplicationTests {

	@Test
	@Disabled
	void contextLoads() throws IOException {
		FileSystemUtils.deleteRecursively(Paths.get("wallet"));
	}

	@Value("https://${ca.host}:${ca.port}")
	private String caUrl;

	@Test
	void testValue() {
		org.junit.jupiter.api.Assertions.assertEquals("https://localhost:7054", caUrl);
	}

}
