package exception;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class UndefinedDepositTypeException extends RuntimeException {
    public UndefinedDepositTypeException() {
    }

    public void traceMyException(){
        System.out.println("Invalid Deposit Type! Sorry we could not calculate interest of this account");
    }
}
