package exception;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class UndefinedDurationInDaysException extends RuntimeException {
    public UndefinedDurationInDaysException() {
    }

    public UndefinedDurationInDaysException(String message) {
        super(message);
    }
    public void traceMyException(){
        System.out.println("Invalid duration!");
    }
}
