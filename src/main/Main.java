package main;

import util.XmlParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * @author FatemehSetareh
 * @version 2.3.0
 *          This program implement an application that simply calculate payedInterest of deposits.
 *          Our input file is an xml that contains deposit information.
 *          Deposit information contains depositNumber, depositBalance, depositType and durationInDays.
 *          DepositType has 3 subclasses contains Qarz, ShortTerm and LongTerm.
 *          This program calculates payedInterest of all deposits.
 *          Output file is an xml that contains "customerNumber#payedInterest" that sorted by payedInterest property.
 */
public class Main {
    public static void main(String[] args) {
        /**
         * With instantiate of SAXParser Class and sending address of input file to it,
         * parser starts parsing xml.
         */

        try {
            File inputFile = new File("deposit.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XmlParser xmlParser = new XmlParser();
            saxParser.parse(inputFile, xmlParser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
