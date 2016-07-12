package pl.parser.nbp;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class MainClass {

    private static boolean firstLine = true;
    private static String currentXML;
    private static String adressXML;

    private static String currency;
    private static String startDate;
    private static String endDate;
    private static double sum;
    private static int divider = 0;


    public static void main(String[] args) throws Exception {
        //////// INPUT /////////////
        currency = args[0];
        startDate = args[1];
        endDate = args[2];
        //////// INPUT /////////////

        ArrayList<Double> currencyValues = new ArrayList<>();
        CurrencyMap currencyMap = new CurrencyMap();
        DirParser dirParser = new DirParser(startDate, endDate);
        DOMParser parser = new DOMParser();
        Scanner remoteScanner;

        for (Object dirName : dirParser.getDirNames()) {
            URL url = new URL(dirName.toString());
            remoteScanner = new Scanner(url.openStream());
            while (remoteScanner.hasNext()) {
                currentXML = remoteScanner.nextLine();
                currentXML = firstLineBomRemove(currentXML);
                if (currentXML.charAt(0) == 'c') {
                    if (rangeHandler(startDate, endDate, currentXML)) {
                        adressXML = "http://www.nbp.pl/kursy/xml/" + currentXML + ".xml";
                        currencyValues.add(getExchangeRate(adressXML, currencyMap.getCurrencyMap().get(currency), parser));
                        sum += currencyValues.get(divider);
                        divider += 1;
                    }
                }
            }
        }
        double average = new Double(String.valueOf(new Double(sum / divider)).substring(0, 6));
        System.out.println(average);
        System.out.print(String.valueOf(standardDevation(currencyValues, average, sum, divider)).substring(0, 6));
    }

    private static double standardDevation(List<Double> t, double avr, double sum, int divider) {
        double sD = 0;
        int l = t.size();
        for (double y : t) {
            double r = (y - (avr));
            sD += (r * r);
        }
        sD /= l;
        sD = Math.sqrt(sD);
        return sD;
    }

    private static double getExchangeRate(String adressXML, int currency, DOMParser parser) throws SAXException, IOException {
        parser.parse(adressXML);
        Document doc = parser.getDocument();
        String value = doc.getDocumentElement().getElementsByTagName("pozycja").item(currency).getChildNodes().item(7).getTextContent().replace(",", ".");

        return Double.parseDouble(value);
    }

    private static boolean rangeHandler(String startDateXML, String endDateXML, String currentXML) {

        int currentXMLNumber = Integer.parseInt(currentXML.substring(5));
        int startDateXMLNumber = Integer.parseInt(mapIntoXMLDateFormat(startDate));
        int endDateXMLNumber = Integer.parseInt(mapIntoXMLDateFormat(endDate));
        if (currentXMLNumber >= startDateXMLNumber
                && currentXMLNumber <= endDateXMLNumber) {
            return true;
        }
        if (currentXMLNumber > endDateXMLNumber) {
            return false;
        }
        return false;
    }


    private static String firstLineBomRemove(String currentXML) {
        if (firstLine) {
            currentXML = currentXML.substring(1);
            firstLine = false;
        }
        return currentXML;
    }

    private static String mapIntoXMLDateFormat(String date) {
        return date.substring(2, 4) + date.substring(5, 7) + date.substring(8, 10);
    }

}


//
//   System.out.println(doc.getDocumentElement().getChildNodes().item(1));  //numer tabeli
//        System.out.println(doc.getDocumentElement().getChildNodes().item(1).getTextContent());
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(5));
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(5).getTextContent());
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(7));
//        System.out.println(doc.getDocumentElement().getElementsByTagName("pozycja").item(3).getChildNodes().item(7).getTextContent());