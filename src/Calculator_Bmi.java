import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Objects;

public class Calculator_Bmi extends JFrame {

        private JPanel screen;

        public static final int WIDTH = 700;

        public static final int HEIGHT = 400;

        public static final int X = 0;

        public static final int Y = 0;

        public static final int HEIGHT_TITLE = 40;

        private int checkWeigh;
        private final String[] gender = {"All", "Male", "Female"};
        private final String[] bodyFrame = {"All", "Small", "Medium", "Large"};

        private double bmi;

        private static JFrame calcWindow;

        private JTextField nameTF;
        private JTextField fnameTF;

        private double idealWeight;

        private JTextField age;

        private JTextField calculateBmi;
        private double slimness;
        private JComboBox genderChoices = null;
        private JComboBox bodyFrameStart = null;
        private JSlider heightSlider;
        private JTextField weight;


        public Calculator_Bmi() {


            screen = new JPanel();
            screen.add(personalData());
            screen.add(gender());
            screen.add(slider());
            screen.add(weight());
            screen.add(bodyFrame());

        }


        public String weightStatus() {
            String result = "";
            if (bmi < 15) {
                result = "Anorexic";
            }
            if (bmi >= 15 && bmi <= 18.5) {
                result = "Underweight";
            }
            if (bmi > 18.5 && bmi <= 24.9) {
                result = "Normal";
            }
            if (bmi >= 25 && bmi <= 29.9) {
                result = "Overweight";
            }
            if (bmi == 35) {
                result = "Obese";
            }
            if (bmi > 35) {
                result = "Extreme Obese";
            }
            return result;
        }

        public String idealWeight() {
            String selectedBodyFrame = (String) bodyFrameStart.getSelectedItem();

            if (selectedBodyFrame.equals("Small")) {
                slimness = 0.9;
            }
            if (selectedBodyFrame.equals("Medium")) {
                slimness = 1;
            }
            if (selectedBodyFrame.equals("Large")) {
                slimness = 1.1;
            }
            double height = heightSlider.getValue();
            double newAge = Double.parseDouble(age.getText());
            idealWeight = (height - 100 + (newAge / 10)) * 0.9 * slimness;
            String newIdealWeight= String.valueOf(idealWeight).substring(0,5);

            return newIdealWeight;
        }


        private JPanel slider() {

            JPanel panel = new JPanel();
            JLabel weight = new JLabel("165");
            heightSlider = new JSlider(JSlider.VERTICAL, 140, 190, 160);

            heightSlider.setMinorTickSpacing(1);
            heightSlider.setMajorTickSpacing(5);
            heightSlider.setPaintTicks(true);

            Hashtable<Integer, JLabel> labels = new Hashtable<>();
            labels.put(140, new JLabel("140"));
            labels.put(150, new JLabel("150"));
            labels.put(160, new JLabel("160"));
            labels.put(170, new JLabel("170"));
            labels.put(180, new JLabel("180"));
            labels.put(190, new JLabel("190"));

            heightSlider.setLabelTable(labels);
            heightSlider.setPaintLabels(true);

            panel.add(weight);
            panel.add(heightSlider);
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Height"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            heightSlider.addMouseMotionListener(new MouseMotionListener() {

                @Override
                public void mouseMoved(MouseEvent e) {


                }

                @Override
                public void mouseDragged(MouseEvent e) {

                    weight.setText("" + heightSlider.getValue());
                }
            });
            return panel;
        }

        private JPanel gender() {

            JPanel panel = new JPanel();
            genderChoices = new JComboBox(gender);
            panel.add(genderChoices);
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Gender"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            return panel;
        }

        private JPanel weight() {

            JPanel panel = new JPanel();
            weight = new JTextField();
            weight.setPreferredSize(new Dimension(100, 20));
            panel.add(weight);
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Weight"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));


            return panel;
        }

        private JPanel bodyFrame() {
            JPanel panel = new JPanel();
            bodyFrameStart = new JComboBox(bodyFrame);
            panel.add(bodyFrameStart);
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Body Frame:"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 4)));
            return panel;
        }

        private JPanel personalData() {


            JPanel panel = new JPanel();
            JLabel name = new JLabel("First name:");
            JLabel fname = new JLabel("Last name:");
            JLabel newAge = new JLabel("Age:");

            nameTF = new JTextField();
            fnameTF = new JTextField();
            age = new JTextField();
            JButton print = new JButton("Print");
            panel.setLayout(new GridLayout(4, 2, 10, 10));
            panel.add(name);
            panel.add(nameTF);
            panel.add(fname);
            panel.add(fnameTF);
            panel.add(newAge);
            panel.add(age);
            panel.add(print);
            panel.add(new JLabel());
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Personal Data"),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));


            print.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        boolean flag = true;

                        if (weight.getText().length() == 0) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please fill Weight field");
                        }

                        if (age.getText().length() == 0) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please fill Age field");
                        }
                        if (weight.getText().matches("[a-zA-Z]+")) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please insert only numbers to Weight field! ");
                        }

                        if (age.getText().matches("[a-zA-Z]+")) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please insert only numbers to Age field! ");
                        }


                        double checkAge = Double.parseDouble(age.getText());
                        if (checkAge <= 0 || checkAge > 120) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please insert valid value in age field(1-120");
                        }
                        if (bodyFrameStart.getSelectedIndex() == 0) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please choose valid value in combo box body frame");
                        }
                        checkWeigh = Integer.parseInt(weight.getText());
                        if (checkWeigh < 2 || checkWeigh > 400) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Please insert to weight field correct value(1-400) and only numbers!");
                        }


                        if (flag == true) {
                            double newWeight = Integer.parseInt(weight.getText());
                            double height = heightSlider.getValue() * 0.01;
                            bmi = newWeight / (height * height);
                            String lengthBmi = String.valueOf(bmi);
                            String weightStatus = weightStatus();

                            System.out.println("Your ideal weight is:" + idealWeight() + "\n" + "Your real weight is:" + weight.getText());
                            JOptionPane.showMessageDialog(null, " Your BMI is:" + " " + lengthBmi.substring(0, 5) + "\n" + ", Your weight status is: " + " " + weightStatus);

                        }

                    }catch (Exception exception) {
                    }
                }
            });
            return panel;

        }


        public static void main(String[] args) {

           Calculator_Bmi calculator_bmi=new Calculator_Bmi();
            calcWindow = new JFrame("WEIGHT CALCULATOR");
            calcWindow.setSize(WIDTH,HEIGHT);
            calcWindow.setContentPane(calculator_bmi.screen);
            calcWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            calcWindow.getContentPane().setBackground(Color.cyan);
            calcWindow.setLocationRelativeTo(null);
            calcWindow.setResizable(false);
            calcWindow.pack();
            calcWindow.setVisible(true);
            calcWindow.setLayout(null);

            JLabel title = new JLabel("Please enter your details in the correct fields:");
            title.setBounds(X, Y, WIDTH, HEIGHT_TITLE);
            calcWindow.add(title);



        }

    }


