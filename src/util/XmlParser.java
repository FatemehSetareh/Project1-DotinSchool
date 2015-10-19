package util;

import bean.Deposit;
import bean.DepositType;
import exception.NegativeDepositBalanceException;
import exception.UndefinedDepositTypeException;
import exception.UndefinedDurationInDaysException;
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
 * The XmlParser implements that simply pars xml files and giving theirs properties to process them.
 */
public class XmlParser extends DefaultHandler {

    boolean depositTypeFlag = false;
    boolean depositBalanceFlag = false;
    boolean durationDayFlag = false;

    protected String customerNumber;
    protected String depositType;
    protected Integer durationDay;
    protected BigDecimal depositBalance;
    protected String tmpValue = null;

    ArrayList<Deposit> preSortArray = new ArrayList<Deposit>();

    public XmlParser() {
    }

    /**
     * This method used to take specific actions at the start of
     * each element.
     *
     * @param uri        The Namespace URI, or the empty string if the
     *                   element has no Namespace URI or if Namespace
     *                   processing is not being performed.
     * @param localname  The local name, or the
     *                   empty string if Namespace processing is not being
     *                   performed.
     * @param qname      The qualified name, or the
     *                   empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *                   there are no attributes, it shall be an empty
     *                   Attributes object.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     */
    @Override
    public void startElement(String uri, String localname, String qname, Attributes attributes)
            throws SAXException {

        if (qname.equalsIgnoreCase("deposit")) {
            customerNumber = attributes.getValue("customerNumber");
        } else if (qname.equalsIgnoreCase("depositType")) {
            depositTypeFlag = true;
        } else if (qname.equalsIgnoreCase("depositBalance")) {
            depositBalanceFlag = true;
        } else if (qname.equalsIgnoreCase("durationDay")) {
            durationDayFlag = true;
        }
    }

    /**
     * This method called at the end of each deposit tag (each element).
     * At this time we have all information about a deposit and we can calculate this deposits
     * interest.
     * In this step we have depositType and by using Reflection we can create object from related class.
     *
     * @param uri       The Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed.
     * @param localName The local name, or the
     *                  empty string if Namespace processing is not being
     *                  performed.
     * @param qName     The qualified name, or the
     *                  empty string if qualified names are not available.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     */

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("deposit")) {
            try {
                if (depositType != null) {
                    DepositType depositTypeClass = (DepositType) Class.forName("bean." + depositType).newInstance();
                    Deposit deposit = new Deposit(customerNumber, depositBalance, durationDay, depositTypeClass);
                    deposit.calculatePayedInterest();
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

    /**
     * This method used to get XML properties as a string and initialize our variable depositType,
     * depositBalance, durationDay and check that this input information are defined in our boundary
     * by exceptions.
     *
     * @param ch     The characters.
     * @param start  The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     */
    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        String tmpValue = new String(ch, start, length);

        if (depositTypeFlag) {
            depositType = tmpValue;
            try {
                if (!(depositType.equals("Qarz") || depositType.equals("ShortTerm") || depositType.equals("LongTerm"))) {
                    throw new UndefinedDepositTypeException();
                }
            } catch (UndefinedDepositTypeException undefinedDepositTypeException) {
                undefinedDepositTypeException.traceMyException();
                depositType = null;
            }
            depositTypeFlag = false;

        } else if (depositBalanceFlag) {
            depositBalance = new BigDecimal(tmpValue);
            try {
                if (depositBalance.toString().charAt(0) == '-' || depositBalance.equals(new BigDecimal(0))) {
                    throw new NegativeDepositBalanceException();
                }
            } catch (NegativeDepositBalanceException negativeDepositBalanceException) {
                negativeDepositBalanceException.traceMyException();
                depositBalance = new BigDecimal(0);
            }
            depositBalanceFlag = false;

        } else if (durationDayFlag) {
            durationDay = Integer.parseInt(tmpValue);
            if (durationDay.toString().charAt(0) == '-') {
                try {
                    throw new UndefinedDurationInDaysException();
                } catch (UndefinedDurationInDaysException undefinedDurationInDaysException) {
                    undefinedDurationInDaysException.traceMyException();
                    durationDay = 0;
                }
            }
            durationDayFlag = false;

        }


    }

    /**
     * This method called at the end of document, when all the XML file was parsed by this class.
     * At this time we have an arrayList of deposit objects from the input XML.
     * Now we have to sort this arrayList and write "customerNumber#payedInterest" in output file
     * using by RandomAccessFile.
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

        for (int i = 0; i < preSortArray.size(); i++) {
            for (int j = 0; j < preSortArray.size(); j++) {
                Collections.sort(preSortArray);
            }
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (Deposit deposit : preSortArray) {
            stringBuilder.append(deposit.getCustomerNumber()
                    + "#"
                    + (deposit.getPayedInterest()).toString()
                    + "\n");
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

