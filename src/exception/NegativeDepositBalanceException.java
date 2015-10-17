package exception;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class NegativeDepositBalanceException extends RuntimeException {
    public NegativeDepositBalanceException() {
    }

    public void traceMyException(){
        System.out.println("Invalid Balance!");
    }
}
