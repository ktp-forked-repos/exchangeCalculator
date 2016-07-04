package pl.parser.nbp;


import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;


@SpringBootApplication
public class MainClass {

    private static boolean firstLine = true;
    private static String currentXML;
    private static String adressXML;
    private static boolean inRange = false;

    public static void main(String[] args) throws Exception {

        String currency = "EUR";
        String startDate = "2016-02-20";
        String endDate = "2016-02-29";

        String adress;


        Scanner consoleScanner = new Scanner(System.in);

        DOMParser parser = new DOMParser();
        parser.parse("http://www.nbp.pl/kursy/xml/c126z160701.xml");


        int startXML = mapIntoXMLDateFormat(startDate);
        int endXML = mapIntoXMLDateFormat(endDate);

        //int years = new Integer(endDate.substring(0, 4)) - new Integer(startDate.substring(0, 4));
        URL url = new URL("http://www.nbp.pl/kursy/xml/dir.txt");
        Scanner remoteScanner = new Scanner(url.openStream());



        while (remoteScanner.hasNext()) {


            currentXML = remoteScanner.nextLine();
            currentXML = firstLineBomRemove(currentXML);
            if (currentXML.charAt(0) == 'c') {
                if (!inRange) {
                    if (new Integer(currentXML) >= startXML) {
                        inRange = true;
                    }
                } else {
                    adressXML = "http://www.nbp.pl/kursy/xml/" + currentXML + ".xml";
                    System.out.println(getExchangeRate(adressXML));
                }
            }


        }

    }

    private static String firstLineBomRemove(String currentXML) {
        if(firstLine){
            currentXML = currentXML.substring(1);
            firstLine = false;
        }
        return currentXML;
    }

    private static Double getExchangeRate(String adressXML) throws SAXException, IOException {
        DOMParser parser = new DOMParser() ;
        parser.parse(adressXML);
        Document doc = parser.getDocument();
        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(7).getTextContent());
        return new Double(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(7).getTextContent());
    }


    public static Integer mapIntoXMLDateFormat(String date) {
        return new Integer( date.substring(2, 4) + date.substring(5, 7) + date.substring(8, 10));
    }
}
//
//   System.out.println(doc.getDocumentElement().getChildNodes().item(1));  //numer tabeli
//        System.out.println(doc.getDocumentElement().getChildNodes().item(1).getTextContent());
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(5));
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(5).getTextContent());
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(7));
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(7).getTextContent());