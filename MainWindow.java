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

    private List<Record> list = new ArrayList<>();

    private Report report = new Report();

    private String selectedFilePath;

    public MainWindow() {

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

                if (startTimeFormattedTextField.getText().equals("Start Time (HH:MM)") || startTimeFormattedTextField.getText().equals("") || startTimeFormattedTextField.getText() == null) {
                    list = report.findPersonInside(report.getOneDayAllRecords());
                } else {
                    String start = startTimeFormattedTextField.getText();
                    String end = endTimeFormattedTextField.getText();
                    if (start.length() != 5 || end.length() != 5 || start.charAt(2) != ':' || end.charAt(2) != ':' || start.matches(".*[a-zA-Z]+.*") || end.matches(".*[a-zA-Z]+.*")) {
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
                textArea1.setText(report.getListWithName(list).toString());

            }

            if (claimComboBox.getSelectedItem().equals("Get All Records")) {

                list = report.getAllRecordList();
                int count = list.size();
                countField.setText("All Records Count: " + count);
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Day Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneDayAllRecords();
                } else {
                    list = report.getOneDayRecordGivenName(name);
                }

                int count = list.size();
                countField.setText("1 Day Record Count: " + count);
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 10 Days Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getTenDaysAllRecords();
                } else {
                    list = report.getTenDaysRecordGivenName(name);
                }
                int count = list.size();
                countField.setText("10 Day Record Count: " + count);
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneMonthAllRecords();
                } else {
                    list = report.getOneMonthRecordGivenName(name);
                }
                int count = list.size();
                countField.setText("1 Mounth Record Count: " + count);
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 3 Months Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getThreeMonthsAllRecords();
                } else {
                    list = report.getThreeMonthsRecordGivenName(name);
                }

                int count = list.size();
                countField.setText("3 Months Record Count: " + count);
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Year Record")) {

                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneYearAllRecords();
                } else {
                    list = report.getOneYearRecordGivenName(name);
                }

                int count = list.size();
                countField.setText("1 Year Record Count: " + count);
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 10 Days Shift")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Name!");
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map = new LinkedHashMap<>();

                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getDepartureAndArrivalListGivenList(report.getTenDaysRecordGivenName(name)))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()));
                    textArea1.setText(s);
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Shift")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Name!");
                } else {
                    textArea1.setText("");
                    String s = "";
                    Map<String, Long> map = new LinkedHashMap<>();

                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getDepartureAndArrivalListGivenList(report.getOneMonthRecordGivenName(name)))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()) + "\n\nMonthly Shifts:\n" + report.getMonthlyShift(map));
                    textArea1.setText(s);
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 3 Months Shift")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Name!");
                } else {
                    textArea1.setText("");
                    String s = "";

                    Map<String, Long> map = new LinkedHashMap<>();
                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getDepartureAndArrivalListGivenList(report.getThreeMonthsRecordGivenName(name)))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()) + "\n\nMonthly Shifts:\n" + report.getMonthlyShift(map));
                    textArea1.setText(s);
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Year Shift")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Name!");
                } else {
                    textArea1.setText("");
                    String s = "";

                    Map<String, Long> map = new LinkedHashMap<>();
                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getDepartureAndArrivalListGivenList(report.getOneYearRecordGivenName(name)))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        s += iter.next() + " m\n";
                    }

                    countField.setText("All Shift: " + report.minToHour((int) report.getAllShift()) + "\n\nMonthly Shifts:\n" + report.getMonthlyShift(map));
                    textArea1.setText(s);
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Shift All Records")) {
                textArea1.setText("");
                String s = "";
                Map<String, Long> map = new LinkedHashMap<>();

                List<String> nameList = report.getOneMonthNameList();

                for (String s1 : nameList) {
                    try {
                        map = (report.findDailyShift(report.getDailyList(report.getDepartureAndArrivalListGivenList(report.getOneMonthRecordGivenName(s1)))));
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
                textArea1.setText(s);
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Day Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!");
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

                    textArea1.setText(s);
                    countField.setText("All Shift: Not Calculated!");
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 10 Days Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!");
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

                    textArea1.setText(s);
                    countField.setText("All Shift: Not Calculated!");
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Month Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!");
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

                    textArea1.setText(s);
                    countField.setText("All Shift: Not Calculated!");
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 3 Months Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!");
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

                    textArea1.setText(s);
                    countField.setText("All Shift: Not Calculated!");
                }
            }

            if (claimComboBox.getSelectedItem().equals("Get Firm 1 Year Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(new Frame(), "Please Enter Firm Name!");
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

                    textArea1.setText(s);
                    countField.setText("All Shift: Not Calculated!");
                }
            }

        });

        nameTextField.setEnabled(false);

        claimComboBox.addActionListener(actionEvent -> {
            if (claimComboBox.getSelectedItem().equals("Get Inside Person") || claimComboBox.getSelectedItem().equals("Get All Records")) {
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

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Shift All Records")) {
                nameTextField.setEnabled(false);
            } else {
                nameTextField.setEnabled(true);
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
}
