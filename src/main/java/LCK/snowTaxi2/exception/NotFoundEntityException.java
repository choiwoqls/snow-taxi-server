package LCK.snowTaxi2.exception;

import lombok.Getter;

@Getter
public class NotFoundEntityException extends RuntimeException{

    // Not Found : memberId=abc123
    public NotFoundEntityException(String field, String value) {
        super("Not Found : ".concat(field).concat("=").concat(value));
    }

}
