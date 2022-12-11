package com.maksimzotov.tof;

import com.maksimzotov.tof.controller.TofController;
import com.maksimzotov.tof.model.ErrorMessage;
import com.maksimzotov.tof.model.FindOccurrencesRequest;
import com.maksimzotov.tof.model.Pair;
import com.maksimzotov.tof.model.SuccessResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TofApplicationTests {

	@Autowired
	private TofController controller;

	@Test
	void testEmptyMainText() {
		FindOccurrencesRequest request = new FindOccurrencesRequest("", "test");
		String errorDescription = "Основной текст является пустым";
		ResponseEntity<ErrorMessage> response = ResponseEntity.badRequest().body(new ErrorMessage(errorDescription));
		assertThat(controller.find(request)).isEqualTo(response);
	}

	@Test
	void testNoOccurrences() {
		FindOccurrencesRequest request = new FindOccurrencesRequest("fffff", "a");
		String errorDescription = "Вхождений не найдено";
		ResponseEntity<ErrorMessage> response = ResponseEntity.ok().body(new ErrorMessage(errorDescription));
		assertThat(controller.find(request)).isEqualTo(response);
	}

	@Test
	void testDefaultUseCase1() {
		FindOccurrencesRequest request = new FindOccurrencesRequest("111122211112211", "22");
		ResponseEntity<SuccessResult> response = ResponseEntity.ok().body(
				new SuccessResult(
						List.of(
								new Pair<>(4, 5),
								new Pair<>(11, 12)
						),
						4f / 15
				)
		);
		assertThat(controller.find(request)).isEqualTo(response);
	}

	@Test
	void testDefaultUseCase2() {
		FindOccurrencesRequest request = new FindOccurrencesRequest("fffffff", "ff");
		ResponseEntity<SuccessResult> response = ResponseEntity.ok().body(
				new SuccessResult(
						List.of(
								new Pair<>(0, 1),
								new Pair<>(2, 3),
								new Pair<>(4, 5)
						),
						6f / 7
				)
		);
		assertThat(controller.find(request)).isEqualTo(response);
	}

}
