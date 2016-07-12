package pl.parser.nbp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liki on 2016-07-04.
 */
public class DirParser {
    List dirNames = new ArrayList();
    private String startDate;
    private String endDate;
    private int startYear;
    private int endYear;

    public DirParser(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        startYear = Integer.parseInt(startDate.substring(2, 4));
        endYear = Integer.parseInt(endDate.substring(2, 4));
        for (int i = startYear; i < endYear; i++) {
            dirNames.add("http://www.nbp.pl/kursy/xml/dir20" + i + ".txt");
        }
        if (endYear == 16) dirNames.add("http://www.nbp.pl/kursy/xml/dir.txt");
        else dirNames.add("http://www.nbp.pl/kursy/xml/dir20" + endYear + ".txt");

    }

    @Override
    public String toString() {
        return "DirParser{" +
                "dirNames=" + dirNames +
                '}';
    }

    public List getDirNames() {
        return dirNames;
    }
}
