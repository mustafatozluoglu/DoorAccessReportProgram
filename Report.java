
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * created by mustafa.tozluoglu on 4.10.2019
 */
public class Report {

    private static final String path = "C:\\Users\\mustafa.tozluoglu\\Desktop\\small.csv";

    private static List<Record> allRecordList = new ArrayList<>();

    private static List<Record> onePersonListGivenName = new ArrayList<>();
    private static List<Record> onePersonListGivenDate = new ArrayList<>();

    private static List<Record> onePersonOneDayRecordList = new ArrayList<>();
    private static List<Record> onePersonTenDaysRecordList = new ArrayList<>();
    private static List<Record> onePersonThreeMonthsRecordList = new ArrayList<>();
    private static List<Record> onePersonOneYearRecordList = new ArrayList<>();
    private static List<Record> onePersonOneMonthRecordList = new ArrayList<>();

    private static List<Record> allRecordsOneDayList = new ArrayList<>();
    private static List<Record> allRecordsTenDaysList = new ArrayList<>();
    private static List<Record> allRecordsThreeMonthsList = new ArrayList<>();
    private static List<Record> allRecordsOneYearList = new ArrayList<>();
    private static List<Record> allRecordsOneMonthList = new ArrayList<>();

    private static List<Record> firmAllRecordsOneMonthList = new ArrayList<>();

    private static List<Record> departureAndArrivalList = new ArrayList<>();

    private static List<List<Record>> dailyList = new ArrayList<>();

    private static List<Record> insidePersonList = new ArrayList<>();

    private static List<String> listWithName = new ArrayList<>();


    private static Map<String, Long> dailyShiftHashMap = new LinkedHashMap<>();


    public static void main(String[] args) throws Exception {


        readCSVFile(path);
        //getInsidePersonGivenTime("14:00");


        //getInsidePersonGivenTime("00:00-00:59");

        findDailyShift(getDailyList(getDepartureAndArrivalListGivenList(getThreeMonthsRecordGivenName("YASIN TURGUT"))));

        //System.out.println(getDepartureAndArrivalListGivenList(getOneYearRecordGivenName("YASIN TURGUT")));
        //System.out.println(getDailyList(getDepartureAndArrivalListGivenList(getThreeMonthsRecordGivenName("Hakan Ozturk"))));
    }

    public static void readCSVFile(String path) throws Exception { // read csv file and parse and store allRecordList
        allRecordList = new ArrayList<>();
        listWithName = new ArrayList<>();
        allRecordsOneDayList = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader("Gen Time", "Seq ID", "Type", "Status", "P", "Reader/Point/Data", "Site", "Card Number", "Account", "Name", "Operator", "Message")
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord csvRecord : csvParser) {
            String genTime = csvRecord.get("Gen Time");
            String seqId = csvRecord.get("Seq ID");
            String type = csvRecord.get("Type");
            String status = csvRecord.get("Status");
            String p = csvRecord.get("P");
            String readerPointData = csvRecord.get("Reader/Point/Data");
            String site = csvRecord.get("Site");
            String cardNumber = csvRecord.get("Card Number");
            String account = csvRecord.get("Account");
            String name = csvRecord.get("Name");
            String operator = csvRecord.get("Operator");
            String message = csvRecord.get("Message");

            Record record = new Record(genTime, seqId, type, status, p, readerPointData, site, cardNumber, account, name, operator, message);
            allRecordList.add(record);
        }
        allRecordList.remove(0); // remove header which is "Gen Time", "Seq ID", "Type"...etc.
    }

    public static List<Record> getOnePersonListGivenName(String name) {
        onePersonListGivenName = new ArrayList<>();

        for (Record record : allRecordList) {
            if (record.getName().toLowerCase().contains(name.toLowerCase())) {
                onePersonListGivenName.add(record);
            }
        }
        return onePersonListGivenName;
    }

    public static List<Record> getOnePersonListGivenDate(List<Record> onePersonList, String date) { // date format is DD.MM.YYYY
        onePersonListGivenDate = new ArrayList<>();

        for (Record record : onePersonList) {
            if (record.getGenTime().contains(date)) {
                onePersonListGivenDate.add(record);
            }
        }
        if (onePersonListGivenDate.size() == 0) {
            System.out.println("No Records!");
        }
        return onePersonListGivenDate;
    }

    public static String getDateFarFromDay(String stringDate, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = sdf.parse(stringDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, day);

        String formatted = sdf.format(cal.getTime());

        return formatted;
    }

    public static List<Record> getOneDayRecordGivenName(String name) {
        onePersonOneDayRecordList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 1; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : getOnePersonListGivenName(name.toLowerCase())) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    onePersonOneDayRecordList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (onePersonOneDayRecordList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : onePersonOneDayRecordList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getTenDaysRecordGivenName(String name) {
        onePersonTenDaysRecordList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 10; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : getOnePersonListGivenName(name.toLowerCase())) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    onePersonTenDaysRecordList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (onePersonTenDaysRecordList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : onePersonTenDaysRecordList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getThreeMonthsRecordGivenName(String name) {
        onePersonThreeMonthsRecordList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 90; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : getOnePersonListGivenName(name.toLowerCase())) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.equals(formattedDate)) {
                    onePersonThreeMonthsRecordList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (onePersonThreeMonthsRecordList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : onePersonThreeMonthsRecordList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getOneYearRecordGivenName(String name) {
        onePersonOneYearRecordList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 365; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : getOnePersonListGivenName(name.toLowerCase())) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    onePersonOneYearRecordList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (onePersonOneYearRecordList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : onePersonOneYearRecordList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getOneMonthRecordGivenName(String name) {
        onePersonOneMonthRecordList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 30; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : getOnePersonListGivenName(name.toLowerCase())) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    onePersonOneMonthRecordList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (onePersonOneMonthRecordList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : onePersonOneMonthRecordList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getAllRecordList() {
        return allRecordList;
    }

    public static List<Record> getTenDaysAllRecords() {
        allRecordsTenDaysList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 10; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : allRecordList) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    allRecordsTenDaysList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (allRecordsTenDaysList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : allRecordsTenDaysList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getOneDayAllRecords() {
        allRecordsOneDayList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 1; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : allRecordList) {
                String recordFormattedDate = "";
                if (record.getGenTime().length() > 10)
                    recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    allRecordsOneDayList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (allRecordsOneDayList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : allRecordsOneDayList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getThreeMonthsAllRecords() {
        allRecordsThreeMonthsList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 90; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : allRecordList) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.equals(formattedDate)) {
                    allRecordsThreeMonthsList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (allRecordsThreeMonthsList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : allRecordsThreeMonthsList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getOneYearAllRecords() {
        allRecordsOneYearList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 365; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : allRecordList) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    allRecordsOneYearList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (allRecordsOneYearList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : allRecordsOneYearList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getOneMonthAllRecords() {
        allRecordsOneMonthList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        for (int i = 0; i < 30; i++) {
            String formatted = sdf.format(cal.getTime());

            for (Record record : allRecordList) {
                String recordFormattedDate = record.getGenTime().substring(0, 10);
                String formattedDate = formatted.substring(0, 10);

                if (recordFormattedDate.contains(formattedDate)) {
                    allRecordsOneMonthList.add(record);
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        if (allRecordsOneMonthList.size() == 0) {
            System.out.println("No Records!");
        }

        List<Record> newList = new ArrayList<>();
        for (Record record : allRecordsOneMonthList) {
            if (!newList.contains(record)) {
                newList.add(record);
            }
        }

        return newList;
    }

    public static List<Record> getFirmAllRecord(String firm) {
        firmAllRecordsOneMonthList = new ArrayList<>();

        for (Record record : allRecordList) {
            if (record.getName().contains(firm)) {
                firmAllRecordsOneMonthList.add(record);
            }
        }

        return firmAllRecordsOneMonthList;
    }

    public static void printListAllRecords(List<Record> list) {
        System.out.println("GEN TIME             READER/POINT/DATA  CARD ID  NAME");
        System.out.println("--------------------||---------------||--------||--------");

        for (Record record : list) {
            String name = record.getName();
            String arrivalDeparture = record.getReaderPointData().substring(record.getReaderPointData().indexOf("-") + 2);
            System.out.println(record.getGenTime() + " || " + arrivalDeparture + " || " + record.getCardNumber() + " || " + name);
        }
    }

    public static void printFormatted(List<Record> list) {
        for (Record record : list)
            System.out.println(record.getGenTime() + " || " + record.getReaderPointData().substring(record.getReaderPointData().indexOf("-") + 2) + " || " + record.getCardNumber() + " || " + record.getName());
    }

    public static List<Record> getDepartureAndArrivalListGivenList(List<Record> list) {
        departureAndArrivalList = new ArrayList<>();

        for (Record record : list) {
            String point = record.getReaderPointData().toLowerCase();
            if (point.contains("personel giris") || point.contains("personel cikis") || point.contains("turnike giris") || point.contains("turnike cikis")) {
                departureAndArrivalList.add(record);
            }
        }

        return departureAndArrivalList;
    }

    public static List<List<Record>> getDailyList(List<Record> list) {

        List<Record> eachDayList = new ArrayList<>();

        for (int i = 0; i < list.size() - 1; i++) { // get all element except last element
            Record record1 = list.get(i);
            Record record2 = list.get(i + 1);

            String recordDate1 = record1.getGenTime();
            String recordDate2 = record2.getGenTime();

            if (recordDate1.substring(0, 10).equals(recordDate2.substring(0, 10))) {
                eachDayList.add(record1);
            } else {
                eachDayList.add(record1);
                dailyList.add(eachDayList);
                eachDayList = new ArrayList<>();
            }
        }

        eachDayList = new ArrayList<>();
        for (int i = list.size() - 1; i > 0; i--) { // get last element
            Record record1 = list.get(i);
            Record record2 = list.get(i - 1);

            String recordDate1 = record1.getGenTime();
            String recordDate2 = record2.getGenTime();

            if (recordDate1.substring(0, 10).equals(recordDate2.substring(0, 10))) {
                eachDayList.add(0, record1);
            } else {
                eachDayList.add(0, record1);
                dailyList.add(eachDayList);
                break;
            }
        }

        for (int i = 0; i < dailyList.size(); i++) { // cikisla baslayan gunlerde bir onceki gundeki son girisi al.
            if (dailyList.get(i).size() != 0) {
                String point = dailyList.get(i).get(0).getReaderPointData().toLowerCase();
                if (point.contains("cikis") && i != dailyList.size() - 1) {
                    if (dailyList.get(i + 1).get(0).getReaderPointData().toLowerCase().contains("giris")) {
                        Record r = dailyList.get(i + 1).remove(dailyList.get(i + 1).size() - 1);
                        dailyList.get(i).add(0, r);
                    }
                }
                if (point.contains("cikis") && i == dailyList.size() - 1) {
                    dailyList.remove(i);
                }
            }
        }

        return dailyList;
    }

    public static Map<String, Long> findDailyShift(List<List<Record>> list) throws ParseException {
        dailyShiftHashMap = new LinkedHashMap<>();

        long allShift = 0;

        long dailyShift = 0;
        long shift = 0;
        String start = null;
        String end = null;
        String name = null;

        String date = null;

        for (List<Record> dailyList : list) {
            for (int i = 0; i < dailyList.size() - 1; i++) {
                Record r1 = dailyList.get(i);
                Record r2 = dailyList.get(i + 1);

                name = r1.getName();
                date = r1.getGenTime().substring(0, 10); // dd.mm.yyyy

                String point1 = r1.getReaderPointData().toLowerCase(); // giris or cikis
                String point2 = r2.getReaderPointData().toLowerCase(); // giris or cikis

                if (point1.contains("giris") && point2.contains("cikis")) {
                    start = r1.getGenTime();
                    end = r2.getGenTime();

                    shift = findShiftMinInTwoTime(start, end);
                    dailyShift += shift;

                    start = null;
                    end = null;
                    shift = 0;
                } /*else if (point1.contains("giris") && point2.contains("giris")) {
                    System.out.println("Cikis kaydi bulunamadi!");
                    start = null;
                    end = null;
                } else if (point1.contains("cikis") && point2.contains("cikis")) {
                    System.out.println("Giris kaydi bulunamadi!");
                    start = null;
                    end = null;
                }*/
            }
            dailyShift = findSecondToMinute(dailyShift);
            if (date != null && name != null)
                dailyShiftHashMap.put(date + " || " + name, dailyShift);

            name = null;
            date = null;
            shift = 0;
            allShift += dailyShift;
            dailyShift = 0;
        }


        Iterator<Map.Entry<String, Long>> iter = dailyShiftHashMap.entrySet().iterator();
        Map.Entry<String, Long> prev = null;
        while (iter.hasNext()) { // mesaisi 15dk'dan az olanlar bir onceki gune eklendi.
            Map.Entry<String, Long> next = iter.next();
            if (next.getValue() < 15 && prev != null) {
                prev.setValue(prev.getValue() + next.getValue());
                iter.remove();
            }
            prev = next;
        }


        for (Map.Entry e : dailyShiftHashMap.entrySet()) { // print daily shift
            System.out.println(e.getKey() + " => " + e.getValue() + " m");
        }
        System.out.println("All Shift: " + allShift + " m");
        allShift = 0;
        return dailyShiftHashMap;

    }

    public static long findShiftMinInTwoTime(String start, String end) throws ParseException {

        long min = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(sdf.parse(start));
        Date startDate = calendar.getTime();
        calendar.setTime(sdf.parse(end));
        Date endDate = calendar.getTime();

        min = TimeUnit.MILLISECONDS.toSeconds(endDate.getTime() - startDate.getTime());

        return min;
    }

    public static long findSecondToMinute(long second) {
        return TimeUnit.SECONDS.toMinutes(second);
    }

    public static List<Record> findPersonInside(List<Record> list) {
        insidePersonList = new ArrayList<>();
        List<Record> allList = new ArrayList<>();
        List<Record> toplanmaAlaniList = new ArrayList<>();
        List<Record> insideList = new ArrayList<>();

        for (Record record : list) {
            String point = record.getReaderPointData().toLowerCase();
            if (point.contains("personel giris") || point.contains("personel cikis") || point.contains("turnike giris") || point.contains("turnike cikis") || point.contains("toplanma alani")) {
                allList.add(record);
            }
        }

        boolean inside = false; // person is inside
        boolean outside = false; // person is outside

        for (Record record : allList) {
            String point = record.getReaderPointData().toLowerCase();

            if (point.contains("toplanma alani")) {
                toplanmaAlaniList.add(record);
            }
        }

        for (Record record : allList) {
            String name = record.getName();
            String point = record.getReaderPointData().toLowerCase();

            if (point.contains("giris")) {
                inside = true;
                outside = false;
            } else if (record.getName().contains(name) && point.contains("cikis")) {
                inside = false;
                outside = true;
            }

            if (inside && !outside) {
                if (!isContainsListGivenName(insideList, name)) {
                    insideList.add(record);
                }
                inside = false;
                outside = false;
                name = null;
            } else if (outside && !inside) {
                if (isContainsListGivenName(insideList, name)) {
                    removeRecordGivenNameInList(insideList, name);
                }
            }
        }

        for (int i = 0; i < insideList.size(); i++) { // insideList'i insidePersonList'e ekler
            insidePersonList.add(insideList.get(i));
        }

        for (int i = 0; i < toplanmaAlaniList.size(); i++) { // toplanma alanindakileri insidePersonList'e ekler
            insidePersonList.add(toplanmaAlaniList.get(i));
        }


        for (int i = 0; i < insidePersonList.size(); i++) { // remove duplicate records
            for (int j = i + 1; j < insidePersonList.size(); j++) {
                if (insidePersonList.get(i).getName().equals(insidePersonList.get(j).getName())) {
                    insidePersonList.remove(j);
                }
            }
        }

        return insidePersonList;
    }

    public static boolean isContainsListGivenName(List<Record> list, String searchName) {
        boolean b = false;

        for (Record record : list) {
            String recordName = record.getName();

            if (recordName.contains(searchName)) {
                b = true;
            }
        }

        return b;
    }

    public static boolean isContainsListGivenSeqID(List<Record> list, String searchSeqID) {
        boolean b = false;

        for (Record record : list) {
            String seqID = record.getSeqId();

            if (seqID.equals(searchSeqID)) {
                b = true;
            }
        }

        return b;
    }

    public static void removeRecordGivenNameInList(List<Record> list, String deleteName) {
        for (int i = 0; i < list.size(); i++) {
            String recordName = list.get(i).getName();

            if (recordName.contains(deleteName)) {
                list.remove(i);
            }
        }
    }

    public static List<String> getListWithName(List<Record> list) {
        listWithName = new ArrayList<>();
        int count = 1;
        for (Record record : list) {
            listWithName.add("" + count + ") " + record.getName() + "\n");
            count++;
        }

        return listWithName;
    }

    public static List<Record> getInsidePersonGivenTime(String time) throws ParseException {
        List<Record> l = getOneDayAllRecords();
        List<Record> allList = new ArrayList<>();
        for (Record record : l) {
            String point = record.getReaderPointData().toLowerCase();
            if (point.contains("personel giris") || point.contains("personel cikis") || point.contains("turnike giris") || point.contains("turnike cikis") || point.contains("toplanma alani")) {
                allList.add(record);
            }
        }

        List<Record> newList = new ArrayList<>();

        LocalTime start = LocalTime.parse(time);

        for (int i = 0; i < allList.size(); i++) {
            Record r = allList.get(i);
            LocalTime stop = LocalTime.parse(r.getGenTime().substring(11, 16));
            Duration duration = Duration.between(start, stop);
            if (duration.toMinutes() < 0) {
                newList.add(r);
            }
        }

        boolean inside = false; // person is inside
        boolean outside = false; // person is outside
        List<Record> ins = new ArrayList<>();

        for (Record record : newList) {
            String name = record.getName();
            String point = record.getReaderPointData().toLowerCase();

            if (point.contains("giris")) {
                inside = true;
                outside = false;
            } else if (record.getName().contains(name) && point.contains("cikis")) {
                inside = false;
                outside = true;
            }

            if (inside && !outside) {
                if (!isContainsListGivenName(ins, name)) {
                    ins.add(record);
                }
                inside = false;
                outside = false;
                name = null;
            } else if (outside && !inside) {
                if (isContainsListGivenName(ins, name)) {
                    removeRecordGivenNameInList(ins, name);
                }
            }
        }

        for (int i = 0; i < ins.size(); i++) { // remove duplicate records
            for (int j = i + 1; j < ins.size(); j++) {
                if (ins.get(i).getName().equals(ins.get(j).getName())) {
                    ins.remove(j);
                }
            }
        }

        return ins;

    }

}
