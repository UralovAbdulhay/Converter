package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class Controller implements Initializable {


    @FXML
    private Label _1_amount_course_1;

    @FXML
    private Label _1_amount_course_2;

    @FXML
    private Label couse1_lab;

    @FXML
    private Label couse2_lab;

    @FXML
    private TextField course_1;

    @FXML
    private TextField course_2;

    @FXML
    private RadioButton rb_g1;

    @FXML
    private ToggleGroup group_1;


    @FXML
    private TextField course_g1;

    @FXML
    private TextField course_g2;

    @FXML
    private RadioButton rb_g2;


    @FXML
    private TextField input;

    @FXML
    private Button switchBt;

    @FXML
    private TextField output;

    private boolean isCourse_1;
    private String course2Str = "RMB";
    private String course1Str = "USD";


    @FXML
    private void convert() {

        if (rb_g1.isSelected()) {
            try {
                conclude(1, Double.parseDouble(course_g1.getText()), Double.parseDouble(input.getText()));
            } catch (NumberFormatException e) {
                conclude(1, 0, 0);
            }
        } else {
            try {
                conclude(Double.parseDouble(course_g2.getText()), 1, Double.parseDouble(input.getText()));

            } catch (NumberFormatException e) {
                conclude(0, 1, 0);

            }
        }


    }

    private void conclude(double course_1, double course_2, double input) {
        double d1 = 0;
        System.out.println("input = " + input);
        System.out.println("1  = " + course_1);
        System.out.println("2  = " + course_2);
        if (!isCourse_1) {

            try {
                if (course_1 == 0) throw new ArithmeticException();
                d1 = course_2 / course_1;
            } catch (ArithmeticException | NumberFormatException e) {
                d1 = 0;
            }

        } else {
            try {
                if (course_2 == 0) throw new ArithmeticException();
                d1 = course_1 / course_2;
            } catch (ArithmeticException | NumberFormatException e) {
                d1 = 0;
            }
        }
        double d2 = input * d1;
        System.out.println("d2 = " + d2);

        d2 = Math.round(d2*1_000_000_00)/1_000_000_00.0;
        output.setText(d2 + "");
    }


    @FXML
    private void onSwitch() {

        if (isCourse_1) {
            isCourse_1 = false;
            input.setPromptText(course1Str);
            output.setPromptText(course2Str);
        } else {
            isCourse_1 = true;
            input.setPromptText(course2Str);
            output.setPromptText(course1Str);
        }

        convert();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        radioSwitch();

        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,8})?");
        Pattern decimalPatternSale = Pattern.compile("\\d{0,3}(\\.\\d{0,8})?");


        UnaryOperator<TextFormatter.Change> filterI = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };


        course_g2.setTextFormatter(new TextFormatter<Integer>(filterI));
        course_g1.setTextFormatter(new TextFormatter<Integer>(filterI));

        input.setTextFormatter(new TextFormatter<Integer>(filterI));


    }


    public void radioSwitch() {

        course_g2.setDisable(rb_g1.isSelected());

        course_g1.setDisable(rb_g2.isSelected());
        convert();

    }


    @FXML
    private void editValyuta_1(MouseEvent mouseEvent) {

        if (mouseEvent.getClickCount() == 2) {
            course_1.setVisible(true);
            course_1.setText(course1Str);
            course_1.requestFocus();
        }

    }


    @FXML
    private void editValyuta_2(MouseEvent mouseEvent) {

        if (mouseEvent.getClickCount() == 2) {
            course_2.setVisible(true);
            course_2.setText(course2Str);
            course_2.requestFocus();
        }


    }


    @FXML
    private void edit_confirm_1() {
        String course1 = course_1.getText().trim();
        if (!course1.isEmpty() && !course1.equals(course2Str)) {
            course1Str = course1;
            refreshGUI();
        }
        System.out.println(course1Str);

        course_1.setVisible(false);
    }


    @FXML
    private void edit_confirm_2() {
        String course2 = course_2.getText().trim();
        if (!course2.isEmpty() && !course2.equals(course1Str)) {
            course2Str = course2;
            refreshGUI();
        }
        System.out.println(course2Str);
        course_2.setVisible(false);

    }


    private void refreshGUI() {


        couse1_lab.setText(course1Str);
        couse2_lab.setText(course2Str);
        rb_g1.setText(course1Str + " -> " + course2Str);
        rb_g2.setText(course2Str + " -> " + course1Str);
        onSwitch();
        _1_amount_course_1.setText("1 " + course1Str);
        _1_amount_course_2.setText("1 " + course2Str);
        course_g1.setPromptText(course2Str);
        course_g2.setPromptText(course1Str);


    }

}
