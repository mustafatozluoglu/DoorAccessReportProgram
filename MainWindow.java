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

    private int dialogResult;

    private List<Record> list = new ArrayList<>();

    private String selectedFilePath;

    private MainWindow() {

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
        for (String claim : claims) {
            claimComboBox.addItem(claim);
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
        for (String s : tollgate) {
            departureTollgate.addItem(s);
            arrivalTollgate.addItem(s);
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
                    Report.readCSVFile(selectedFilePath);
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

            if (Objects.equals(claimComboBox.getSelectedItem(), "Get Inside Person")) {

                if ((startTimeFormattedTextField.getText().equals("HH:MM") || startTimeFormattedTextField.getText().equals("") || startTimeFormattedTextField.getText() == null) && (endTimeFormattedTextField.getText().equals("HH:MM") || endTimeFormattedTextField.getText().equals("") || endTimeFormattedTextField.getText() == null)) {
                    list = Report.findPersonInside(Report.getOneDayAllRecords());
                } else {
                    String start = startTimeFormattedTextField.getText();
                    String end = endTimeFormattedTextField.getText();
                    int startHour = 0;
                    int startMin = 0;
                    int endHour = 0;
                    int endMin = 0;
                    if (!start.contains(" ") && !end.contains(" ") && !start.matches(".*[a-zA-Z]+.*") && !end.matches(".*[a-zA-Z]+.*") && start.length() == 5 && end.length() == 5) {
                        startHour = Integer.parseInt(startTimeFormattedTextField.getText().substring(0, 2));
                        startMin = Integer.parseInt(startTimeFormattedTextField.getText().substring(3));
                        endHour = Integer.parseInt(endTimeFormattedTextField.getText().substring(0, 2));
                        endMin = Integer.parseInt(endTimeFormattedTextField.getText().substring(3));
                    }


                    if ((start.equals("") && !end.equals("")) || (!start.equals("") && end.equals("")) || start.contains(" ") || end.contains(" ") || startHour > 23 || startMin > 59 || endHour > 23 || endMin > 59 || start.length() != 5 || end.length() != 5 || start.charAt(2) != ':' || end.charAt(2) != ':' || start.matches(".*[a-zA-Z]+.*") || end.matches(".*[a-zA-Z]+.*")) {
                        JOptionPane.showMessageDialog(new Frame(), "Invalid time!");
                    } else {
                        LocalTime t1 = LocalTime.parse(start);
                        LocalTime t2 = LocalTime.parse(end);
                        if (t1.isAfter(t2)) {
                            JOptionPane.showMessageDialog(new Frame(), "Start Time cannot be after End Time!");
                        } else {
                            list = Report.getInsidePersonInTwoTime(start, end);
                        }
                    }
                }

                int count = list.size();

                countField.setText("Inside Person Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(Report.getListWithName(list).toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            if (Objects.equals(claimComboBox.getSelectedItem(), "Get All Records")) {

                list = Report.getAllRecordList();
                int count = list.size();
                countField.setText("All Records Count: " + count);

                if (count == 0) {
                    textArea1.setText("No Records!");
                } else {
                    textArea1.setText(list.toString().replace(",", "").replace("[", "").replace("]", ""));
                }
            }

            boolean bool = name.equals("name") || name == null || name.equals("");
            if (claimComboBox.getSelectedItem().equals("Get 1 Day Record")) {

                if (bool) {
                    list = Report.getOneDayAllRecords();
                } else {
                    list = Report.getOneDayRecordGivenName(name);
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

                if (bool) {
                    list = Report.getTenDaysAllRecords();
                } else {
                    list = Report.getTenDaysRecordGivenName(name);
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

                if (bool) {
                    list = Report.getOneMonthAllRecords();
                } else {
                    list = Report.getOneMonthRecordGivenName(name);
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

                if (bool) {
                    list = Report.getThreeMonthsAllRecords();
                } else {
                    list = Report.getThreeMonthsRecordGivenName(name);
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

                if (bool) {
                    list = Report.getOneYearAllRecords();
                } else {
                    list = Report.getOneYearRecordGivenName(name);
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
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map = new LinkedHashMap<>();

                    try {
                        map = (Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(Report.getTenDaysRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                        s.append(stringLongEntry).append(" m\n");
                    }

                    countField.setText("All Shift: " + Report.minToHour((int) Report.getAllShift()));

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 1 Month Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map = new LinkedHashMap<>();

                    try {
                        map = (Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(Report.getOneMonthRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                        s.append(stringLongEntry).append(" m\n");
                    }

                    countField.setText("All Shift: " + Report.minToHour((int) Report.getAllShift()) + "\n\nMonthly Shifts:\n" + Report.getMonthlyShift(map));

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 3 Months Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();

                    Map<String, Long> map = new LinkedHashMap<>();
                    try {
                        map = (Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(Report.getThreeMonthsRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                        s.append(stringLongEntry).append(" m\n");
                    }

                    countField.setText("All Shift: " + Report.minToHour((int) Report.getAllShift()) + "\n\nMonthly Shifts:\n" + Report.getMonthlyShift(map));

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Person 1 Year Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Person Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();

                    Map<String, Long> map = new LinkedHashMap<>();
                    try {
                        map = (Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(Report.getOneYearRecordGivenName(name), entrance, exit)), entrance, exit));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                        s.append(stringLongEntry).append(" m\n");
                    }

                    countField.setText("All Shift: " + Report.minToHour((int) Report.getAllShift()) + "\n\nMonthly Shifts:\n" + Report.getMonthlyShift(map));

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Shift All Records")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                int dialogButton = JOptionPane.YES_NO_OPTION;
                dialogResult = JOptionPane.showConfirmDialog(this, "This operation take 2 minute. Do you want to continue anyway?", "Confirmation", dialogButton);

                if (dialogResult == 0) {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map = new LinkedHashMap<>();

                    List<String> nameList = Report.getOneMonthNameList();

                    for (String s1 : nameList) {
                        try {
                            map = (Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(Report.getOneMonthRecordGivenName(s1), entrance, exit)), entrance, exit));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (map.size() > 0) {
                            for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                                s.append(stringLongEntry).append(" m\n");
                            }
                            s.append("-------------------------------------------------\n");
                        }
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Day Record")) {
                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<List<Record>> l = Report.getListDivideByName(Report.getOneDayRecordGivenName(name.toLowerCase()));
                    StringBuilder s = new StringBuilder();

                    for (List<Record> eachPersonList : l) {
                        for (Record record : eachPersonList) {
                            s.append(record);
                        }
                        s.append("---------------------------------------------------------------------------------------------------------\n");
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 10 Days Record")) {
                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<List<Record>> l = Report.getListDivideByName(Report.getTenDaysRecordGivenName(name.toLowerCase()));
                    StringBuilder s = new StringBuilder();

                    for (List<Record> eachPersonList : l) {
                        for (Record record : eachPersonList) {
                            s.append(record);
                        }
                        s.append("---------------------------------------------------------------------------------------------------------\n");
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Month Record")) {
                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<List<Record>> l = Report.getListDivideByName(Report.getOneMonthRecordGivenName(name.toLowerCase()));
                    StringBuilder s = new StringBuilder();

                    for (List<Record> eachPersonList : l) {
                        for (Record record : eachPersonList) {
                            s.append(record);
                        }
                        s.append("---------------------------------------------------------------------------------------------------------\n");
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 3 Months Record")) {
                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<List<Record>> l = Report.getListDivideByName(Report.getThreeMonthsRecordGivenName(name.toLowerCase()));
                    StringBuilder s = new StringBuilder();

                    for (List<Record> eachPersonList : l) {
                        for (Record record : eachPersonList) {
                            s.append(record);
                        }
                        s.append("---------------------------------------------------------------------------------------------------------\n");
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Year Record")) {
                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<List<Record>> l = Report.getListDivideByName(Report.getOneYearRecordGivenName(name.toLowerCase()));
                    StringBuilder s = new StringBuilder();

                    for (List<Record> eachPersonList : l) {
                        for (Record record : eachPersonList) {
                            s.append(record);
                        }
                        s.append("---------------------------------------------------------------------------------------------------------\n");
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (l.size() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 10 Days Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map;
                    List<List<Record>> l = Report.getListDivideByName(Report.getTenDaysRecordGivenName(name.toLowerCase()));

                    try {
                        for (List<Record> records : l) {
                            map = Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(records, entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                                    s.append(stringLongEntry).append(" m\n");
                                }
                                s.append("----------------------------------------------------------\n");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    countField.setText("All Shift: Not Calculated!");

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Month Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map;
                    List<List<Record>> l = Report.getListDivideByName(Report.getOneMonthRecordGivenName(name.toLowerCase()));

                    try {
                        for (List<Record> records : l) {
                            map = Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(records, entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                                    s.append(stringLongEntry).append(" m\n");
                                }
                                s.append("----------------------------------------------------------\n");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    countField.setText("All Shift: Not Calculated!");

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 3 Months Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map;
                    List<List<Record>> l = Report.getListDivideByName(Report.getThreeMonthsRecordGivenName(name.toLowerCase()));

                    try {
                        for (List<Record> records : l) {
                            map = Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(records, entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                                    s.append(stringLongEntry).append(" m\n");
                                }
                                s.append("----------------------------------------------------------\n");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    countField.setText("All Shift: Not Calculated!");
                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Year Shift")) {
                String entrance = Objects.requireNonNull(departureTollgate.getSelectedItem()).toString();
                String exit = Objects.requireNonNull(arrivalTollgate.getSelectedItem()).toString();

                if (bool) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!", "Information!", JOptionPane.ERROR_MESSAGE);
                } else {
                    textArea1.setText("");
                    StringBuilder s = new StringBuilder();
                    Map<String, Long> map;
                    List<List<Record>> l = Report.getListDivideByName(Report.getOneYearRecordGivenName(name.toLowerCase()));

                    try {
                        for (List<Record> records : l) {
                            map = Report.findDailyShift(Report.getDailyList(Report.getGivenTollgateListGivenList(records, entrance, exit)), entrance, exit);
                            if (map.size() > 0) {
                                for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
                                    s.append(stringLongEntry).append(" m\n");
                                }
                                s.append("----------------------------------------------------------\n");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    countField.setText("All Shift: Not Calculated!");

                    if (s.length() == 0) {
                        textArea1.setText("No Records!");
                    } else {
                        textArea1.setText(s.toString());
                    }
                }
            }

        });

        nameTextField.setEnabled(false);
        departureTollgate.setEnabled(false);
        arrivalTollgate.setEnabled(false);

        claimComboBox.addActionListener(actionEvent -> {
            if (Objects.equals(claimComboBox.getSelectedItem(), "Get Inside Person") || Objects.equals(claimComboBox.getSelectedItem(), "Get All Records") || claimComboBox.getSelectedItem().equals("Get 1 Month Shift All Records")) {
                nameTextField.setText("");
                nameTextField.setEnabled(false);
            } else {
                nameTextField.setEnabled(true);
            }

            if (claimComboBox.getSelectedItem().equals("Get Inside Person")) {
                startTimeFormattedTextField.setEnabled(true);
                endTimeFormattedTextField.setEnabled(true);
            } else {
                startTimeFormattedTextField.setText("HH:MM");
                endTimeFormattedTextField.setText("HH:MM");
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

        SeparatorRenderer(ListCellRenderer delegate, Integer... separatorIndexes) {
            this.delegate = delegate;
            this.separatorIndexes = new HashSet<>(Arrays.asList(separatorIndexes));
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
