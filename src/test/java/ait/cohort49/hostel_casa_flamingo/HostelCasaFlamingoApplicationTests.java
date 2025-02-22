package ait.cohort49.hostel_casa_flamingo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"mail.username=fake",
		"mail.password=fake"
})
class HostelCasaFlamingoApplicationTests {

	@Test
	void contextLoads() {
	}

}
