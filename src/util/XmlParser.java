package util;

import bean.Deposit;
import bean.DepositType;
import exception.NegativeDepositBalanceException;
import exception.UndefinedDurationInDaysException;
import exception.UndefinedTypeException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class XmlParser extends DefaultHandler {

    boolean bdepositType = false;
    boolean bdepositBalance = false;
    boolean bdurationDay = false;

    protected String customerNumber;
    protected String depositType;
    protected Integer durationDay;
    protected BigDecimal depositBalance;
    protected String tmpValue = null;
    protected Double interestRate;
    public BigDecimal payedInterest;

    ArrayList<Deposit> preSortArray = new ArrayList<Deposit>();

    public XmlParser() {
    }

    @Override
    public void startElement(String uri, String localname, String qname, Attributes attributes)
            throws SAXException {

        if (qname.equalsIgnoreCase("deposit")) {
            customerNumber = attributes.getValue("customerNumber");
        } else if (qname.equalsIgnoreCase("depositType")) {
            bdepositType = true;
        } else if (qname.equalsIgnoreCase("depositBalance")) {
            bdepositBalance = true;
        } else if (qname.equalsIgnoreCase("durationDay")) {
            bdurationDay = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("deposit")) {
            try {
                if(depositType != null){
                DepositType depositTypeClass = (DepositType) Class.forName("bean." + depositType).newInstance();
                Deposit deposit = new Deposit(customerNumber, depositBalance, durationDay, depositTypeClass);
                deposit.calculatePayedInterest();
                //System.out.println(deposit.getDepositBalance());
                preSortArray.add(deposit);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        String tmpValue = new String(ch, start, length);

        if (bdepositType) {
            depositType = tmpValue;
            try {
                if (!(depositType.equals("Qarz") || depositType.equals("ShortTerm") || depositType.equals("LongTerm"))) {
                    throw new UndefinedTypeException();
                }
            }catch (UndefinedTypeException undefinedTypeException){
                undefinedTypeException.traceMyException();
                depositType = null;
            }
            bdepositType = false;

        } else if (bdepositBalance) {
            depositBalance = new BigDecimal(tmpValue);
            try {
                if (depositBalance.toString().charAt(0) == '-' || depositBalance.equals(new BigDecimal(0))) {
                    throw new NegativeDepositBalanceException();
                }
            }catch (NegativeDepositBalanceException negativeDepositBalanceException){
                negativeDepositBalanceException.traceMyException();
                depositBalance = new BigDecimal(0);
            }
            bdepositBalance = false;

        } else if (bdurationDay) {
            durationDay = Integer.parseInt(tmpValue);
            if (durationDay.toString().charAt(0) == '-') {
                try {
                    throw new UndefinedDurationInDaysException();
                }catch (UndefinedDurationInDaysException undefinedDurationInDaysException){
                    undefinedDurationInDaysException.traceMyException();
                    durationDay = 0;
                }
            }
            bdurationDay = false;

        }


    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

        for (int i = 0; i < preSortArray.size(); i++) {
            for (int j = 0; j < preSortArray.size(); j++) {
                Collections.sort(preSortArray);

//                if (preSortArray.get(i).compareTo(preSortArray.get(j)) == 1) {
//                    Deposit deposit = preSortArray.get(i);
//                    preSortArray.get(i) = preSortArray.get(j);
//                    preSortArray.get(j) = deposit;
//                }
            }
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (Deposit deposit : preSortArray) {
            stringBuilder.append(deposit.getCustomerNumber());
            stringBuilder.append("#");
            stringBuilder.append((deposit.getPayedInterest()).toString());
            stringBuilder.append("\n");
        }

        RandomAccessFile output = null;
        try {
            output = new RandomAccessFile("output.xml", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            output.writeBytes(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        {
        }
    }
}

