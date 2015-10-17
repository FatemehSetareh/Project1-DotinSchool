package main;

import util.XmlParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * Created by ${Dotin} on ${4/25/2015}.
 */
public class Main {
    public static void main(String[] args) {


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
