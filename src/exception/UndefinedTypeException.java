package exception;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class UndefinedTypeException extends RuntimeException {
    public UndefinedTypeException() {
    }

    public void traceMyException(){
        System.out.println("Invalid Deposit Type! Sorry we could not calculate interest of this account");
    }
}
