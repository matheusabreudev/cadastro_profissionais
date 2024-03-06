package com.cadastroprofissional.simples;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContatoProfissionalSimplesDentalApplicationTests {

	@Test
	@DisplayName("Testa o método main")
	void testMain() {
		ConfigurableApplicationContext context = SpringApplication.run(CadastroProfissionalSimplesDentalApplication.class);

		assertNotNull(context);

		context.close();
	}

}
