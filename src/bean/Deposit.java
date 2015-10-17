package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class Deposit implements Comparable<Deposit> {
    private String customerNumber;
    private BigDecimal depositBalance;
    private Integer durationInDays;
    private BigDecimal payedInterest;
    private DepositType depositType;

    public Deposit(String customerNumber, BigDecimal depositBalance, Integer durationInDays, DepositType depositType) {
        this.customerNumber = customerNumber;
        this.depositBalance = depositBalance;
        this.durationInDays = durationInDays;
        this.depositType = depositType;
    }

    public BigDecimal calculatePayedInterest() {
        payedInterest = depositBalance.multiply(new BigDecimal(depositType.getInterestRate() * durationInDays))
                .divide(new BigDecimal(36500), 2, RoundingMode.HALF_UP);
        System.out.println("your balance is: ("
                + depositBalance
                + ") Rial, and your duration day is: ("
                + durationInDays
                + "), therefore your payed "
                + "interest is: ("
                + payedInterest
                + ")Rial! ");
        return payedInterest;
    }

    @Override
    public int compareTo(Deposit deposit) {
        return this.getPayedInterest().compareTo(deposit.getPayedInterest());
    }


    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public BigDecimal getPayedInterest() {
        return payedInterest;
    }

    public void setPayedInterest(BigDecimal payedInterest) {
        this.payedInterest = payedInterest;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }
}