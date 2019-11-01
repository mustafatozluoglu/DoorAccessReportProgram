import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

/**
 * created by mustafa.tozluoglu on 11.10.2019
 */
public class MainWindow extends JFrame {
    private JButton chooseFileButton;
    private JPanel panel1;
    private JTextArea textArea1;
    private JComboBox claimComboBox;
    private JButton findButton;
    private JTextField nameTextField;
    private JTextArea countField;
    private JFormattedTextField startTimeFormattedTextField;
    private JFormattedTextField endTimeFormattedTextField;
    private JComboBox departureTollgate;
    private JComboBox arrivalTollgate;
    private JProgressBar progressBar1;

    private int dialogResult;

    private List<Record> list = new ArrayList<>();

    private Report report = new Report();

    private String selectedFilePath;

    public MainWindow() {

        String[] claims = new String[]{"Get Inside Person",
                "Get All Records",
                "Get 1 Day Record",
                "Get 10 Days Record",
                "Get 1 Month Record",
                "Get 3 Months Record",
                "Get 1 Year Record",
                "Get Person 10 Days Shift",
                "Get Person 1 Month Shift",
                "Get Person 3 Months Shift",
                "Get Person 1 Year Shift",
                "Get 1 Month Shift All Records",
                "Get Firm 1 Day Record",
                "Get Firm 10 Days Record",
                "Get Firm 1 Month Record",
                "Get Firm 3 Months Record",
                "Get Firm 1 Year Record",
                "Get Firm 10 Days Shift",
                "Get Firm 1 Month Shift",
                "Get Firm 3 Months Shift",
                "Get Firm 1 Year Shift"
        };
        for (int i = 0; i < claims.length; i++) {
            claimComboBox.addItem(claims[i]);
        }
        claimComboBox.setRenderer(new SeparatorRenderer(claimComboBox.getRenderer(), 0, 1, 6, 10, 11, 16));
        claimComboBox.setMaximumRowCount(20);


        String[] tollgate = new String[]{"Turnike Giris",
                "Turnike Cikis",
                "PERSONEL GIRIS",
                "PERSONEL CIKIS",
                "SAHA_Turnike Giris",
                "SAHA_Turnike Cikis",
                "Reader 1",
                "Reader 2",
                "Reader 3",
                "Reader 4",
                "WORKSHOP ANA GIRIS",
                "WORKSHOP ARKA KAPI",
                "Reader 1-ECB ANA GIRIS",
                "Reader 2-ENGINEERING ROOM",
                "Reader 3-CONTROL ROOM",
                "Reader 4-CONTROL ROOM",
                "WAREHOUSE ANA GIRIS",
                "SECURITY ROOM",
                "TOPLANMA ALANI"
        };
        for (int i = 0; i < tollgate.length; i++) {
            departureTollgate.addItem(tollgate[i]);
            arrivalTollgate.addItem(tollgate[i]);
        }
        departureTollgate.setMaximumRowCount(15);
        arrivalTollgate.setMaximumRowCount(15);
        departureTollgate.setRenderer(new SeparatorRenderer(departureTollgate.getRenderer(), 1, 3, 5, 9, 11, 15));
        arrivalTollgate.setRenderer(new SeparatorRenderer(arrivalTollgate.getRenderer(), 1, 3, 5, 9, 11, 15));


        ImageIcon searchIcon = new ImageIcon("C:\\Users\\mustafa.tozluoglu\\IdeaProjects\\GAMA\\searchIcon.png");
        ImageIcon fileIcon = new ImageIcon("C:\\Users\\mustafa.tozluoglu\\IdeaProjects\\GAMA\\fileIcon.png");
        Icon loadingGif = new ImageIcon("loadingGif.gif");

        chooseFileButton.setIcon(fileIcon);
        findButton.setIcon(searchIcon);

        initialize();

    }

    private void initialize() {

        chooseFileButton.addActionListener(actionEvent -> {

            list = new ArrayList<>();
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int r = j.showSaveDialog(null);
            String fileExtension = "";
            if (j.getSelectedFile() != null) {
                fileExtension = j.getSelectedFile().getAbsolutePath().substring(j.getSelectedFile().getAbsolutePath().lastIndexOf('.') + 1);
            }
            if (fileExtension.equals("csv")) {
                if (r == JFileChooser.APPROVE_OPTION) {
                    selectedFilePath = j.getSelectedFile().getAbsolutePath();
                    countField.setText("");
                } else {
                    selectedFilePath = "";
                }
                textArea1.setText("");

                try {
                    report.readCSVFile(selectedFilePath);
                    JOptionPane.showMessageDialog(new Frame(), "File Uploading is Successful!");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!fileExtension.equals("csv") && j.getSelectedFile() != null) {
                JOptionPane.showMessageDialog(new Frame(), "Invalid file extension!");
            }
        });

        findButton.addActionListener(actionEvent -> {
            list = new ArrayList<>();
            textArea1.setText("");
            String name = nameTextField.getText().toLowerCase();

            if (claimComboBox.getSelectedItem().equals("Get Inside Person")) {

                if (startTimeFormattedTextField.getText().equals("HH:MM") || startTimeFormattedTextField.getText().equals("") || startTimeFormattedTextField.getText() == null) {
                    list = report.findPersonInside(report.getOneDayAllRecords());
                } else {
                    String start = startTimeFormattedTextField.getText();
                    String end = endTimeFormattedTextField.getText();
                    int startHour = 0;
                    int startMin = 0;
                    int endHour = 0;
                    int endMin = 0;
                    if (!start.contains(" ") && !end.contains(" ")) {
                        startHour = Integer.parseInt(startTimeFormattedTextField.getText().substring(0, 2));
                        startMin = Integer.parseInt(startTimeFormattedTextField.getText().substring(3));
                        endHour = Integer.parseInt(endTimeFormattedTextField.getText().substring(0, 2));
                        endMin = Integer.parseInt(endTimeFormattedTextField.getText().substring(3));
                    }


                    if (start.contains(" ") || end.contains(" ") || startHour > 23 || startMin > 59 || endHour > 23 || endMin > 59 || start.length() != 5 || end.length() != 5 || start.charAt(2) != ':' || end.charAt(2) != ':' || start.matches(".*[a-zA-Z]+.*") || end.matches(".*[a-zA-Z]+.*")) {
                        JOptionPane.showMessageDialog(new Frame(), "Invalid time!");
                    } else {
                        LocalTime t1 = LocalTime.parse(start);
                        LocalTime t2 = LocalTime.parse(end);
                        if (t1.isAfter(t2)) {
                            JOptionPane.showMessageDialog(new Frame(), "Start Time cannot be after End Time!");
                        } else {
                            list = report.getInsidePersonInTwoTime(start, end);
                        }
                    }
                }

                int count = list.size();

                countField.setText("Inside Person Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(report.getListWithName(list).toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get All Records")) {

                list = report.getAllRecordList();
                int count = list.size();
                countField.setText("All Records Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Day Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneDayAllRecords();
                } else {
                    list = report.getOneDayRecordGivenName(name);
                }

                int count = list.size();
                countField.setText("1 Day Record Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 10 Days Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getTenDaysAllRecords();
                } else {
                    list = report.getTenDaysRecordGivenName(name);
                }
                int count = list.size();
                countField.setText("10 Day Record Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneMonthAllRecords();
                } else {
                    list = report.getOneMonthRecordGivenName(name);
                }
                int count = list.size();
                countField.setText("1 Mounth Record Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 3 Months Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getThreeMonthsAllRecords();
                } else {
                    list = report.getThreeMonthsRecordGivenName(name);
                }

                int count = list.size();
                countField.setText("3 Months Record Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Year Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneYearAllRecords();
                } else {
                    list = report.getOneYearRecordGivenName(name);
                }

                int count = list.size();
                countField.setText("1 Year Record Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 10 Days Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map = new LinkedHashMap<>();

                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(report.getTenDaysRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()));

                    if (map.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 1 Month Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map = new LinkedHashMap<>();

                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(report.getOneMonthRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()) + "\n\nMonthly Shifts:\n" + report.getMonthlyShift(map));

                    if (map.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 3 Months Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";

                    Map<String, Long> map = new LinkedHashMap<>();
                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(report.getThreeMonthsRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()) + "\n\nMonthly Shifts:\n" + report.getMonthlyShift(map));

                    if (map.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 1 Year Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";

                    Map<String, Long> map = new LinkedHashMap<>();
                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(report.getOneYearRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()) + "\n\nMonthly Shifts:\n" + report.getMonthlyShift(map));

                    if (map.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Shift All Records")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                int dialogButton = JOptionPane.YES_NO_OPTION;
                dialogResult = JOptionPane.showConfirmDialog(this, "This operation take 2 minute. Do you want to continue anyway?", "Confirmation", dialogButton);

                if (dialogResult == 0) {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map = new LinkedHashMap<>();

                    List<String> nameList = report.getOneMonthNameList();

                    for (String s1 : nameList) {
                        try {
                            map = (report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(report.getOneMonthRecordGivenName(name), entrance, exit)), entrance, exit));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                        while (iter.hasNext()) {
                            s += iter.next() + " m\n";
                        }
                        s += "-------------------------------------------------\n";
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (map.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Day Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    List<List<Record>> l = report.getListDivideByName(report.getOneDayRecordGivenName(name.toLowerCase()));
                    String s = "";

                    for (int i = 0; i < l.size(); i++) {
                        List<Record> eachPersonList = l.get(i);

                        for (int j = 0; j < eachPersonList.size(); j++) {
                            s += eachPersonList.get(j);
                        }
                        s += "---------------------------------------------------------------------------------------------------------\n";
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 10 Days Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    List<List<Record>> l = report.getListDivideByName(report.getTenDaysRecordGivenName(name.toLowerCase()));
                    String s = "";

                    for (int i = 0; i < l.size(); i++) {
                        List<Record> eachPersonList = l.get(i);

                        for (int j = 0; j < eachPersonList.size(); j++) {
                            s += eachPersonList.get(j);
                        }
                        s += "---------------------------------------------------------------------------------------------------------\n";
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Month Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    List<List<Record>> l = report.getListDivideByName(report.getOneMonthRecordGivenName(name.toLowerCase()));
                    String s = "";

                    for (int i = 0; i < l.size(); i++) {
                        List<Record> eachPersonList = l.get(i);

                        for (int j = 0; j < eachPersonList.size(); j++) {
                            s += eachPersonList.get(j);
                        }
                        s += "---------------------------------------------------------------------------------------------------------\n";
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 3 Months Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    List<List<Record>> l = report.getListDivideByName(report.getThreeMonthsRecordGivenName(name.toLowerCase()));
                    String s = "";

                    for (int i = 0; i < l.size(); i++) {
                        List<Record> eachPersonList = l.get(i);

                        for (int j = 0; j < eachPersonList.size(); j++) {
                            s += eachPersonList.get(j);
                        }
                        s += "---------------------------------------------------------------------------------------------------------\n";
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Year Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    List<List<Record>> l = report.getListDivideByName(report.getOneYearRecordGivenName(name.toLowerCase()));
                    String s = "";

                    for (int i = 0; i < l.size(); i++) {
                        List<Record> eachPersonList = l.get(i);

                        for (int j = 0; j < eachPersonList.size(); j++) {
                            s += eachPersonList.get(j);
                        }
                        s += "---------------------------------------------------------------------------------------------------------\n";
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 10 Days Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map;
                    List<List<Record>> l = report.getListDivideByName(report.getTenDaysRecordGivenName(name.toLowerCase()));

                    try {
                        map = new LinkedHashMap<>();
                        for (int i = 0; i < l.size(); i++) {
                            map = report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(l.get(i), entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                                while (iter.hasNext()) {
                                    s += iter.next() + " m\n";
                                }
                                s += "----------------------------------------------------------\n";
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Month Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map;
                    List<List<Record>> l = report.getListDivideByName(report.getOneMonthRecordGivenName(name.toLowerCase()));

                    try {
                        map = new LinkedHashMap<>();
                        for (int i = 0; i < l.size(); i++) {
                            map = report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(l.get(i), entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                                while (iter.hasNext()) {
                                    s += iter.next() + " m\n";
                                }
                                s += "----------------------------------------------------------\n";
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 3 Months Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map;
                    List<List<Record>> l = report.getListDivideByName(report.getThreeMonthsRecordGivenName(name.toLowerCase()));

                    try {
                        map = new LinkedHashMap<>();
                        for (int i = 0; i < l.size(); i++) {
                            map = report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(l.get(i), entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                                while (iter.hasNext()) {
                                    s += iter.next() + " m\n";
                                }
                                s += "----------------------------------------------------------\n";
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    countField.setText("All Shift: Not Calculated!");
                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Year Shift")) {
                String entrance = departureTollgate.getSelectedItem().toString();
                String exit = arrivalTollgate.getSelectedItem().toString();

                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", 0);
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map;
                    List<List<Record>> l = report.getListDivideByName(report.getOneYearRecordGivenName(name.toLowerCase()));

                    try {
                        map = new LinkedHashMap<>();
                        for (int i = 0; i < l.size(); i++) {
                            map = report.findDailyShift(report.getDailyList(report.getGivenTollgateListGivenList(l.get(i), entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                                while (iter.hasNext()) {
                                    s += iter.next() + " m\n";
                                }
                                s += "----------------------------------------------------------\n";
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s);
                    }
                }
            }

        });

        nameTextField.setEnabled(false);
        departureTollgate.setEnabled(false);
        arrivalTollgate.setEnabled(false);

        claimComboBox.addActionListener(actionEvent -> {
            if (claimComboBox.getSelectedItem().equals("Get Inside Person") || claimComboBox.getSelectedItem().equals("Get All Records") || claimComboBox.getSelectedItem().equals("Get 1 Month Shift All Records")) {
                nameTextField.setEnabled(false);
            } else {
                nameTextField.setEnabled(true);
            }

            if (claimComboBox.getSelectedItem().equals("Get Inside Person")) {
                startTimeFormattedTextField.setEnabled(true);
                endTimeFormattedTextField.setEnabled(true);
            } else {
                startTimeFormattedTextField.setEnabled(false);
                endTimeFormattedTextField.setEnabled(false);
            }

            if (claimComboBox.getSelectedItem().toString().contains("Shift")) {
                departureTollgate.setEnabled(true);
                arrivalTollgate.setEnabled(true);
            } else {
                departureTollgate.setEnabled(false);
                arrivalTollgate.setEnabled(false);
            }

        });


    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reporting Program");
            frame.setContentPane(new MainWindow().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(1500, 750);
            frame.setLocationRelativeTo(null);
        });
    }

    private static class SeparatorRenderer implements ListCellRenderer {
        private ListCellRenderer delegate;
        private JPanel container;
        private JLabel label;
        private HashSet<Integer> separatorIndexes;
        private boolean cellRendererSelectionBackgroundEnabled;
        private boolean isSelected;

        public SeparatorRenderer(ListCellRenderer delegate, Integer... separatorIndexes) {
            this.delegate = delegate;
            this.separatorIndexes = new HashSet<Integer>(Arrays.asList(separatorIndexes));
            container = new JPanel();
            label = new JLabel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                }
            };
            container.setLayout(new BorderLayout());
            container.setOpaque(false);
            container.add(label);
            container.add(new JSeparator(), BorderLayout.SOUTH);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel c = (JLabel) delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (index >= 0 && separatorIndexes.contains(index)) {
                this.isSelected = isSelected;
                label.setText(c.getText());
                label.setForeground(c.getForeground());
                label.setBackground(c.getBackground());
                label.setBorder(c.getBorder());
                label.setFont(c.getFont());
                label.setOpaque(isSelected && !cellRendererSelectionBackgroundEnabled);
                return container;
            }
            return c;
        }
    }
}
