package pl.parser.nbp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Patryk on 04.07.2016.
 */
class CurrencyMap {

    private Map<String, Integer> currencyMap;

    public CurrencyMap() {
        currencyMap = new HashMap<>();

        currencyMap.put("USD", 0);
        currencyMap.put("AUD", 1);
        currencyMap.put("CAD", 2);
        currencyMap.put("EUR", 3);
        currencyMap.put("HUF", 4);
        currencyMap.put("CHF", 5);
        currencyMap.put("GBP", 6);
        currencyMap.put("JPY", 7);
        currencyMap.put("CZK", 8);
        currencyMap.put("DKK", 9);
        currencyMap.put("EEK", 10);
        currencyMap.put("NOK", 11);
        currencyMap.put("SEK", 12);
        currencyMap.put("XDR", 13);
    }

    @Override
    public String toString() {
        return "CurrencyMap{" +
                "currencyMap=" + currencyMap +
                '}';
    }

    public Map<String, Integer> getCurrencyMap() {
        return currencyMap;
    }
}
