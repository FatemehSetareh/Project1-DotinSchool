package bean;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public abstract class DepositType {
    protected double interestRate;

    public DepositType() {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
