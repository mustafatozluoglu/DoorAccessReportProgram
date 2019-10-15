import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.FileNameMap;
import java.text.ParseException;
import java.util.ArrayList;
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
    private JComboBox timeComboBox;
    private JLabel insidePersonCount;

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
            if (j.getSelectedFile() != null)
                fileExtension = j.getSelectedFile().getAbsolutePath().substring(j.getSelectedFile().getAbsolutePath().lastIndexOf('.') + 1);

            if (fileExtension.equals("csv")) {
                if (r == JFileChooser.APPROVE_OPTION) {
                    selectedFilePath = j.getSelectedFile().getAbsolutePath();
                } else {
                    selectedFilePath = "";
                }
                textArea1.setText("");

                try {
                    report.readCSVFile(selectedFilePath);
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
                if (timeComboBox.getSelectedItem().equals("All Day")) {
                    try {
                        list = report.findPersonInside(report.getOneDayAllRecords());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("00:00-00:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("00:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("01:00-01:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("01:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("02:00-02:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("02:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("03:00-03:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("03:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("04:00-04:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("04:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("05:00-05:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("05:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("06:00-06:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("06:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("07:00-07:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("07:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("08:00-08:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("08:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("09:00-09:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("09:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("10:00-10:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("10:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("11:00-11:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("11:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("12:00-12:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("12:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("13:00-13:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("13:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("14:00-14:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("14:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("15:00-15:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("15:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("16:00-16:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("16:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("17:00-17:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("17:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("18:00-18:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("18:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("19:00-19:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("19:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("20:00-20:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("20:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("21:00-21:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("21:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("22:00-22:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("22:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (timeComboBox.getSelectedItem().equals("23:00-23:59")) {
                    try {
                        list = report.getInsidePersonGivenTime("23:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                textArea1.setText(report.getListWithName(list).toString());

            }

            if (claimComboBox.getSelectedItem().equals("Get All Records")) {
                list = report.getAllRecordList();
                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Day Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneDayAllRecords();
                } else {
                    list = report.getOneDayRecordGivenName(name);
                }

                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 10 Days Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getTenDaysAllRecords();
                } else {
                    list = report.getTenDaysRecordGivenName(name);
                }

                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Month Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneMonthAllRecords();
                } else {
                    list = report.getOneMonthRecordGivenName(name);
                }

                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 3 Months Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getThreeMonthsAllRecords();
                } else {
                    list = report.getThreeMonthsRecordGivenName(name);
                }

                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Year Record")) {
                if (name.equals("name") || name == null || name.equals("")) {
                    list = report.getOneYearAllRecords();
                } else {
                    list = report.getOneYearRecordGivenName(name);
                }

                textArea1.setText(list.toString());
            }

            if (claimComboBox.getSelectedItem().equals("Get 10 Days Shift")) {
                textArea1.setText("");
                String s = "";
                try {
                    s = (report.findDailyShift(report.getDailyList(report.getDepartureAndArrivalListGivenList(report.getTenDaysRecordGivenName(name))))).toString();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                textArea1.setText("IMPROVING...");
            }

            if (claimComboBox.getSelectedItem().equals("Get 3 Months Shift")) {
                textArea1.setText("IMPROVING...");

            }

            if (claimComboBox.getSelectedItem().equals("Get 1 Year Shift")) {
                textArea1.setText("IMPROVING...");

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
                timeComboBox.setEnabled(true);
            } else {
                timeComboBox.setEnabled(false);
            }
        });


    }


    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MainWindow");
            frame.setContentPane(new MainWindow().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(1000, 750);
            frame.setLocationRelativeTo(null);
        });
    }
}
