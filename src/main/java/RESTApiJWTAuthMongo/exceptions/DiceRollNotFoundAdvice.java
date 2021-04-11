package RESTApiJWTAuthMongo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DiceRollNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(DiceRollNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String diceRollNotFoundHandler(DiceRollNotFoundException ex) {
		return ex.getMessage();
	}
}