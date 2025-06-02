package tqs.ChargeUnity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ChargeUnityApplicationTests {
	@Autowired
	private ApplicationContext context;

  @Test
  void contextLoads() {
	// This test will simply check if the application context loads successfully.
	// If there are any issues with the configuration or beans, this test will fail.
	// No additional assertions are needed here.
	assertNotNull(context);
  }
}
