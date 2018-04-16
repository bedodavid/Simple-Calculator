package simplecalculator;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import static java.awt.event.MouseWheelEvent.WHEEL_UNIT_SCROLL;

public class Calculator implements ActionListener, MouseListener, MouseWheelListener {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;        //  public static final int LAYOUT_LEFT_TO_RIGHT=true;
    private  JTextField textField;
    public static String showResult="";
    private static ArrayList<String> textStoredOperation=new ArrayList<>();
    private static ArrayList<String> textStoredOperationValue=new ArrayList<>();

    private static boolean operation=false;
    private JTextArea textAreaResults;
    JScrollPane jLabelScroll;
    public static final int FONT_SIZE = 12;
    final Color lightGreyColor=new Color(230, 230, 230);
    final Color greenColor=new Color(199,215,178);
    final Color whiteColor= new Color( 250,250,250 );
    final Color blueColor= new Color(204,238,255);
    final Color bitDarkerGreyColor = new Color(220,220,220);
    final Color lightRedColor= new Color (227,209,188);
    final Color lightYellowColor =new Color(225,227,188 );
    final int gridRowStart=0;
    final int gridColumnStart=0;
    private ScrollPaneJustArrows jPaneShowOperation;
    private JLabel jLabelShowMemorizedData;


    public void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        pane.setLayout(new GridBagLayout());
        pane.setBackground( bitDarkerGreyColor);
        GridBagConstraints c = new GridBagConstraints();

        if (shouldFill) {
            c.fill = GridBagConstraints.BOTH;
        }

        JLabel jMemoryTitle= new JLabel("Memory: ");
        if (shouldWeightX) {
            c.weightx = 0.5;
            c.weighty=0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = gridColumnStart+5;
        c.gridy = gridRowStart+0;
        c.gridheight =1;
        c.gridwidth=2;
        c.insets = new Insets(20, 10, 0, 0);
        jMemoryTitle.setFont(new Font("Monospaced", Font.BOLD,FONT_SIZE +4));
        jMemoryTitle.setBackground( bitDarkerGreyColor );
        pane.add(jMemoryTitle,c);

        JLabel copyRight= new JLabel("Created by David Bedo. All rights reserved"+"\u00A9");
        if (shouldWeightX) {
            c.weightx = 0.5;
            c.weighty=0.5;
        }
        c.fill = GridBagConstraints.CENTER;
        c.anchor=GridBagConstraints.LAST_LINE_END;
        c.gridx = gridColumnStart+7;
        c.gridy = gridRowStart+8;
        c.gridheight =1;
        c.gridwidth=2;
        c.insets = new Insets(0, 10, 0, 0);
        copyRight.setFont(new Font("Monospaced", Font.PLAIN,9 ));
        copyRight.setBackground( bitDarkerGreyColor );
        pane.add(copyRight,c);

        jLabelShowMemorizedData=new JLabel("0");
        if (shouldWeightX) {
            c.weightx = 0.5;
            c.weighty=0.5;
        }
        c.fill = GridBagConstraints.CENTER;
        c.anchor=GridBagConstraints.LINE_END;
        c.gridx = gridColumnStart+6;
        c.gridy = gridRowStart+0;
        c.gridheight =1;
        c.gridwidth=3;
        jLabelShowMemorizedData.setHorizontalAlignment( SwingConstants.LEFT );
        c.insets = new Insets(20, 5, 0, 10);
        jLabelShowMemorizedData.setFont(new Font("Monospaced", Font.BOLD,FONT_SIZE +5));
        jLabelShowMemorizedData.setBackground( bitDarkerGreyColor );
        pane.add(jLabelShowMemorizedData,c);



        textAreaResults= new JTextArea(12, 28);
        if (shouldWeightX) {
            c.weightx = 0.5;
            c.weighty=0;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.PAGE_START;
        c.gridheight =7;
        c.gridwidth=5;
        c.gridx = gridColumnStart+5;
        c.gridy =gridRowStart+ 1;
        textAreaResults.setEditable(false); // set textArea non-editable
        textAreaResults.setBackground( bitDarkerGreyColor );
        c.insets = new Insets(5, 5, 0, 5);
        textAreaResults.setFont(new Font("Monospaced", Font.BOLD,FONT_SIZE +5));
        JScrollPane textAreaScroll = new JScrollPane(textAreaResults);
        textAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textAreaScroll.setBorder( null );
        pane.add(textAreaScroll,c);



        jPaneShowOperation=new ScrollPaneJustArrows( "" );
        if (shouldWeightX) {
            c.weightx = 0.5;
            c.weighty=0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight=1;
        c.gridwidth = 5;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+0;
        c.insets = new Insets(20, 10, 0, 10);
        jLabelScroll = new JScrollPane(jPaneShowOperation);
        jLabelScroll.setBackground( bitDarkerGreyColor );
        jLabelScroll.setBorder( null );
        jLabelScroll.getVerticalScrollBar().setPreferredSize(new Dimension(300,20));
        pane.add(jLabelScroll,c);
        jLabelScroll.addMouseWheelListener( this );


        textField = new JTextField(15);
        if (shouldWeightX) {
            c.weightx = 0.5;
            c.weighty=0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        c.insets = new Insets(0, 10, 0, 5);
        c.gridwidth = 5;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+1;
        textField.setBorder( null );
        textField.setPreferredSize(new Dimension(40, 40));
        textField.setFont(new Font("Monospaced", Font.BOLD,FONT_SIZE +15));
        textField.setText("0");
        pane.add(textField, c);


        JButton button1 = new JButton("MC");
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridwidth = 1;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+2;
        button1.setBackground(lightGreyColor);
        button1.setBorderPainted(false);
        pane.add(button1, c);
        button1.addActionListener(this);
        button1.setActionCommand("MC");
        button1.addMouseListener(this);


        JButton button2 = new JButton("MR");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+1;
        c.gridy = gridRowStart+2;
        button2.setBackground(lightGreyColor);
        button2.setBorderPainted(false);
        pane.add(button2, c);
        button2.addActionListener(this);
        button2.setActionCommand("MR");
        button2.addMouseListener(this);


        JButton button3 = new JButton("MS");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+2;
        c.gridy = gridRowStart+2;
        button3.setBackground(lightGreyColor);
        button3.setBorderPainted(false);
        pane.add(button3, c);
        button3.addActionListener(this);
        button3.setActionCommand("MS");
        button3.addMouseListener(this);


        JButton button4 = new JButton("M+");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+3;
        c.gridy = gridRowStart+2;
        button4.setBackground(lightGreyColor);
        button4.setBorderPainted(false);
        pane.add(button4, c);
        button4.addActionListener(this);
        button4.setActionCommand("M+");
        button4.addMouseListener(this);


        JButton button5 = new JButton("M-");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+4;
        c.gridy = gridRowStart+2;
        button5.setBackground(lightGreyColor);
        button5.setBorderPainted(false);
        pane.add(button5, c);
        button5.addActionListener(this);
        button5.setActionCommand("M-");
        button5.addMouseListener(this);


        JButton button6 = new JButton("%");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+3;
        button6.setBackground(lightGreyColor);
        button6.setBorderPainted(false);
        pane.add(button6, c);
        button6.addActionListener(this);
        button6.setActionCommand("%");
        button6.addMouseListener(this);


        JButton button7 = new JButton("\u221A");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+1;
        c.gridy = gridRowStart+3;
        button7.setBackground(lightGreyColor);
        button7.setBorderPainted(false);
        pane.add(button7, c);
        button7.addActionListener(this);
        button7.setActionCommand("sqrt");
        button7.addMouseListener(this);


        JButton button8 = new JButton("x" + "\u00B2");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+2;
        c.gridy = gridRowStart+3;
        button8.setBackground(lightGreyColor);
        button8.setBorderPainted(false);
        pane.add(button8, c);
        button8.addActionListener(this);
        button8.setActionCommand("x2");
        button8.addMouseListener(this);


        JButton button9 = new JButton("1/x");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+3;
        c.gridy = gridRowStart+3;
        button9.setBackground(lightGreyColor);
        button9.setBorderPainted(false);
        pane.add(button9, c);
        button9.addActionListener(this);
        button9.setActionCommand("1/x");
        button9.addMouseListener(this);


        JButton button10 = new JButton("\u232B");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+4;
        c.gridy = gridRowStart+3;
        button10.setBackground(lightYellowColor);
        button10.setBorderPainted(false);
        pane.add(button10, c);
        button10.addActionListener(this);
        button10.setActionCommand("delete");
        button10.addMouseListener(this);



        JButton button11 = new JButton("7");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+4;
        button11.setBackground(Color.white);
        button11.setBorderPainted(false);
        pane.add(button11, c);
        button11.addActionListener(this);
        button11.setActionCommand("7");
        button11.addMouseListener(this);


        JButton button12 = new JButton("8");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+1;
        c.gridy = gridRowStart+4;
        button12.setBackground(Color.white);
        button12.setBorderPainted(false);
        pane.add(button12, c);
        button12.addActionListener(this);
        button12.setActionCommand("8");
        button12.addMouseListener(this);


        JButton button13 = new JButton("9");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+2;
        c.gridy = gridRowStart+4;
        button13.setBackground(Color.white);
        button13.setBorderPainted(false);
        pane.add(button13, c);
        button13.addActionListener(this);
        button13.setActionCommand("9");
        button13.addMouseListener(this);


        JButton button14 = new JButton("CE");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+3;
        c.gridy = gridRowStart+4;
        button14.setBackground(lightRedColor);
        button14.setBorderPainted(false);
        pane.add(button14, c);
        button14.addActionListener(this);
        button14.setActionCommand("CE");
        button14.addMouseListener(this);

        JButton button15 = new JButton("C");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+4;
        c.gridy = gridRowStart+4;
        button15.setBackground(lightRedColor);
        button15.setBorderPainted(false);
        pane.add(button15, c);
        button15.addActionListener(this);
        button15.setActionCommand("C");
        button15.addMouseListener(this);


        JButton button16 = new JButton("4");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+5;
        button16.setBackground(Color.white);
        button16.setBorderPainted(false);
        pane.add(button16, c);
        button16.addActionListener(this);
        button16.setActionCommand("4");
        button16.addMouseListener(this);


        JButton button17 = new JButton("5");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+1;
        c.gridy = gridRowStart+5;
        button17.setBackground(Color.white);
        button17.setBorderPainted(false);
        pane.add(button17, c);
        button17.addActionListener(this);
        button17.setActionCommand("5");
        button17.addMouseListener(this);

        JButton button18 = new JButton("6");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+2;
        c.gridy = gridRowStart+5;
        button18.setBackground(Color.white);
        button18.setBorderPainted(false);
        pane.add(button18, c);
        button18.addActionListener(this);
        button18.setActionCommand("6");
        button18.addMouseListener(this);


        JButton button19 = new JButton("X");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+3;
        c.gridy = gridRowStart+5;
        button19.setBackground(blueColor);
        button19.setBorderPainted(false);
        pane.add(button19, c);
        button19.addActionListener(this);
        button19.setActionCommand("*");
        button19.addMouseListener(this);


        JButton button20 = new JButton("\u00F7");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+4;
        c.gridy = gridRowStart+5;
        button20.setBackground(blueColor);
        button20.setBorderPainted(false);
        pane.add(button20, c);
        button20.addActionListener(this);
        button20.setActionCommand("/");
        button20.addMouseListener(this);



        JButton button21 = new JButton("1");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+0;
        c.gridy = gridRowStart+6;
        button21.setBackground(Color.white);
        button21.setBorderPainted(false);
        pane.add(button21, c);
        button21.addActionListener(this);
        button21.setActionCommand("1");
        button21.addMouseListener(this);


        JButton button22 = new JButton("2");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+1;
        c.gridy = gridRowStart+6;
        button22.setBackground(Color.white);
        button22.setBorderPainted(false);
        pane.add(button22, c);
        button22.addActionListener(this);
        button22.setActionCommand("2");
        button22.addMouseListener(this);

        JButton button23 = new JButton("3");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+2;
        c.gridy = gridRowStart+6;
        button23.setBackground(Color.white);
        button23.setBorderPainted(false);
        pane.add(button23, c);
        button23.addActionListener(this);
        button23.setActionCommand("3");
        button23.addMouseListener(this);

        JButton button24 = new JButton("+");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+3;
        c.gridy = gridRowStart+6;
        button24.setBackground(blueColor);
        button24.setBorderPainted(false);
        pane.add(button24, c);
        button24.addActionListener(this);
        button24.setActionCommand("+");
        button24.addMouseListener(this);


        JButton button25 = new JButton("-");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+4;
        c.gridy = gridRowStart+6;
        button25.setBackground(blueColor);
        button25.setBorderPainted(false);
        pane.add(button25, c);
        button25.addActionListener(this);
        button25.setActionCommand("-");
        button25.addMouseListener(this);

        JButton button26 = new JButton("\u00B1");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+0;
        c.gridy =gridRowStart+ 7;
        button26.setBackground(lightGreyColor);
        button26.setBorderPainted(false);
        pane.add(button26, c);
        button26.addActionListener(this);
        button26.setActionCommand("+-");
        button26.addMouseListener(this);

        JButton button27 = new JButton("0");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+1;
        c.gridy = gridRowStart+7;
        button27.setBackground(Color.white);
        button27.setBorderPainted(false);
        pane.add(button27, c);
        button27.addActionListener(this);
        button27.setActionCommand("0");
        button27.addMouseListener(this);

        JButton button28 = new JButton(".");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridColumnStart+2;
        c.gridy = gridRowStart+7;
        button28.setBackground(lightGreyColor);
        button28.setBorderPainted(false);
        pane.add(button28, c);
        button28.addActionListener(this);
        button28.setActionCommand(".");
        button28.addMouseListener(this);

        JButton button29 = new JButton("=");
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth=2;
        c.gridx = gridColumnStart+3;
        c.gridy = gridRowStart+7;
        button29.setBackground(greenColor);
        button29.setBorderPainted(false);
        pane.add(button29, c);
        button29.addActionListener(this);
        button29.setActionCommand("=");
        button29.addMouseListener(this);



        JButton button30 = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("blackRubbisBin.png"));
            button30.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        c.fill = GridBagConstraints.CENTER;
        c.anchor=GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(1, 0, 1, 2);
        c.gridwidth = 1;
        c.gridheight=1;
        c.gridx = gridColumnStart+8;
        c.gridy = gridRowStart+7;
        button30.setBackground(lightGreyColor);
        button30.setBorderPainted(false);
        pane.add(button30, c);
        button30.addActionListener(this);
        button30.setActionCommand("clearTextArea");
        button30.addMouseListener(this);



    }




    public String addNumber(String baseString, String numberToTheString){
        int fieldLength=baseString.length();
        String returnString=baseString;
        int numberLength=returnString.length();
        int countDigits=0;
        String dealWithBaseString=baseString;

        for (int i=0;i<numberLength;i++){
            if (Character.isDigit( returnString.charAt( i ))){
                countDigits++;
            }
        }

        if (!operation|| baseString.equals( "infinity" )){
            dealWithBaseString="0";
            fieldLength=1;
            countDigits=1;
        }
        if (countDigits<15){
        if (fieldLength==1&&dealWithBaseString.charAt(0)=='0'){
            returnString=numberToTheString;
            // System.out.println(returnString);
        }
        else {
            if (fieldLength==2&&dealWithBaseString.charAt(0)=='-'&&dealWithBaseString.charAt(1)=='0'){
                returnString="-"+numberToTheString;
                //     System.out.println(returnString);
            }
            else{
                returnString=returnString+numberToTheString;
                //     System.out.println(returnString);
            }
        }
        }
        operation=true;
        return returnString;
    }

    private BigDecimal arithmeticOperations(BigDecimal base, BigDecimal addendum, int listSize){
        String operation= textStoredOperationValue.get(listSize-1);
        BigDecimal result = null;
        switch (operation){
            case "+": {
                result=base.add(addendum);
                break;
            }
            case "-": {
                result = base.subtract( addendum );
                break;
            }
            case "*":{
                result=base.multiply( addendum );
                break;
            }
            case "/":{
                result=base.divide( addendum );
            }

        }

        return result;
    }

  /*  public String changeDoubleToRequiredString(BigDecimal number){
        String returnString="";
        BigDecimal doubleResult=number;
        if (doubleResult == Math.rint(doubleResult)){
            if (Math.abs(doubleResult)<Long.MAX_VALUE){
                returnString=String.valueOf(((long) doubleResult ));
            }else{
                returnString=String.valueOf(Math.rint(doubleResult));}

        }else{
            returnString=String.valueOf(doubleResult);
        }
        return returnString;
    }
*/
    public String doOperation(int listSize){
        String returnString="";
        BigDecimal base= new BigDecimal( textStoredOperationValue.get(0));
        BigDecimal doubleResult=null;
        for (int i=0;i<listSize-2;i++){
            BigDecimal addendum=new BigDecimal(textStoredOperationValue.get(i+2));
            doubleResult= arithmeticOperations(base,addendum,i+2);
            base=doubleResult;
            i++;
        }
        returnString=doubleResult.toString();
       // returnString= changeDoubleToRequiredString( doubleResult );
        return returnString;
    }


    public String equalEndOperator(){
        String returnString="";
        int listSize=textStoredOperationValue.size();
        BigDecimal base= new BigDecimal(showResult);
        BigDecimal doubleResult= arithmeticOperations(base,base,listSize);
        returnString= doubleResult.toString();
        return returnString;
    }



    public void setOperator(String baseString,String operatorString) {
        int fieldLength = baseString.length();
        int contentLength= textStoredOperationValue.size();


        if (operation) {
            if (/*(!(fieldLength == 1 && baseString.charAt(0) == '0')) && */(!(fieldLength == 2 && baseString.charAt(0) == '-'&&baseString.charAt(1)=='0'))&& (!(fieldLength == 3 && baseString.charAt(0) == '-'&&baseString.charAt(1)=='0'&&baseString.charAt(2)=='.'))) {
                if (operatorString.equals("=")||operatorString.equals("+")||operatorString.equals("-")||operatorString.equals("/")||operatorString.equals("*")) {
                    if (contentLength>0&&(!textStoredOperation.get(contentLength-1).equals(textStoredOperationValue.get(contentLength-1)))){
                        textStoredOperation.add(operatorString);
                        textStoredOperationValue.add(operatorString);
                    }
                    else{
                        textStoredOperation.add(baseString);
                        textStoredOperation.add(operatorString);
                        textStoredOperationValue.add(baseString);
                        textStoredOperationValue.add(operatorString);

                    }

                }
                if (textStoredOperationValue.size()<3){
                    showResult=baseString;
                }

                if (textStoredOperationValue.size()>2){
                    int listSize=textStoredOperationValue.size();
                    showResult=doOperation(listSize);
                }
            }
            textField.setText(showResult.toLowerCase());
            operation=false;
        }
        else{
            if (operatorString.equals("=")){
                showResult=equalEndOperator();

            }
            else{
                textStoredOperationValue.set(textStoredOperationValue.size()-1,operatorString);
                textStoredOperation.set(textStoredOperation.size()-1,operatorString);
            }
        }
    }

     public boolean setArrowsVisibel(String showString){
        if (showString.length()>35){
            return true;
        }else
            {return false;}
     }

    public String arrayListToString(){
        String equation=" ";
        for (int i=0; i<textStoredOperation.size();i++){
            equation=equation+textStoredOperation.get(i)+" ";
        }

        return equation;
    }

    public void setOperatorComplex(String baseString,String operatorString){
        String result="";
        double base= Double.parseDouble(baseString);
        switch (operatorString){
            case "sqrt":{
                textStoredOperation.add("\u221A"+"("+baseString+")");
                result=String.valueOf(Math.sqrt(base));
                textStoredOperationValue.add(result);
                break;

            }
            case "x2":{
                textStoredOperation.add("sqr("+baseString+")");
                result=String.valueOf(base*base);
                textStoredOperationValue.add(result);
                break;

            }

            case "1/x":{
                textStoredOperation.add("1/("+baseString+")");
                result=String.valueOf(1/base);
                textStoredOperationValue.add(result);
                break;
            }

            case "%":{
                String stringAddendum="";
                if (textStoredOperationValue.size()>2){
                    if (operation){
                        int listSize=textStoredOperationValue.size();
                        stringAddendum=doOperation(listSize-2);
                        double addendum=Double.parseDouble(stringAddendum);
                        result=String.valueOf(addendum*base/100);
                        System.out.println(result);
                        textStoredOperation.add("("+baseString+"% of "+stringAddendum+")");
                        textStoredOperationValue.add(result);
                    }else{
                        result=String.valueOf(base*base/100);
                        textStoredOperation.add("("+baseString+"% of "+baseString+")");
                        textStoredOperationValue.add(result);
                    }
                }
                else{
                    if (textStoredOperationValue.size()==2){
                        if (baseString.equals(textStoredOperationValue.get(0))) {
                            result = String.valueOf(base * base / 100);
                            textStoredOperation.add("(" + baseString + "% of " + baseString + ")");
                            textStoredOperationValue.add(result);
                        }
                        else{
                            String firstItem =textStoredOperationValue.get(0);
                            double addendum=Double.parseDouble(firstItem);
                            result = String.valueOf(addendum * base / 100);
                            textStoredOperation.add("(" + firstItem + "% of " + baseString + ")");
                            textStoredOperationValue.add(result);
                        }

                    }
                    else{
                        textStoredOperation.add(" 0");
                        textStoredOperationValue.add("0");
                        result="0";
                    }

                }
                break;
            }
        }

        showResult=result;
        textField.setText(showResult.toLowerCase());
        operation=true;
    }



    public String arrayListToStringExceptLast(){
        String equation=" ";
        for (int i=0; i<textStoredOperation.size()-1;i++){
            equation=equation+textStoredOperation.get(i)+" ";
        }

        return equation;
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Standard Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        frame.setMinimumSize(new Dimension(720, 450));
        frame.setResizable(false);
        System.out.println(frame.getSize());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }




    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

    public void mouseEntered(MouseEvent e){
        Object event = e.getComponent();
        Color buttonColor= ((JButton) event).getBackground();
        if (event instanceof  JButton){
           // ((JButton) event).setBorderPainted(true);
            ((JButton) event).setBackground(buttonColor.darker());
        }
    }

    public void mouseExited(MouseEvent e){
        Object event = e.getComponent();
        Color buttonColor= ((JButton) event).getBackground();
        if (event instanceof  JButton){
          // ((JButton) event).setBorderPainted(false);
           ((JButton) event).setBackground(buttonColor.brighter());
        }
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        String textFieldContent= textField.getText();

        switch(action){
            case "1": {
                textFieldContent=addNumber(textFieldContent,"1");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "2":{
                textFieldContent=addNumber(textFieldContent,"2");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "3":{
                textFieldContent=addNumber(textFieldContent,"3");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "4":{
                textFieldContent=addNumber(textFieldContent,"4");
                textField.setText(textFieldContent.toLowerCase());
                System.out.println(operation);
                break;
            }

            case "5":{
                textFieldContent=addNumber(textFieldContent,"5");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "6":{
                textFieldContent=addNumber(textFieldContent,"6");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "7":{
                textFieldContent=addNumber(textFieldContent,"7");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "8":{
                textFieldContent=addNumber(textFieldContent,"8");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "9":{
                textFieldContent=addNumber(textFieldContent,"9");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case "0":{
                textFieldContent=addNumber(textFieldContent,"0");
                textField.setText(textFieldContent.toLowerCase());
                break;
            }

            case ".": {
                int i = 0;
                boolean alreadyIsDot = false;
                while (!alreadyIsDot && i < textFieldContent.length()) {
                    if (textFieldContent.charAt(i) == '.') {
                        alreadyIsDot = true;
                    }
                    i++;
                }
                if (!alreadyIsDot) {
                    textFieldContent = textFieldContent + ".";
                }

                textField.setText(textFieldContent.toLowerCase());
                break;
            }
            case "+-": {
                if (textFieldContent.charAt(0) != '-') {
                    textFieldContent = "-" + textFieldContent;
                } else {
                    textFieldContent = textFieldContent.substring(1);
                }

                textField.setText(textFieldContent.toLowerCase());
                System.out.println(textFieldContent);
                break;
            }
            case "delete":{
                if ((textFieldContent.length() == 1)||(textFieldContent.length() == 2&&textFieldContent.charAt(0)=='-')){
                    textFieldContent = "0";
                }
                else{
                    textFieldContent=textFieldContent.substring(0,textFieldContent.length()-1);
                }
                textField.setText(textFieldContent.toLowerCase());
                break;
            }
            case "+": {
                setOperator(textFieldContent, "+");
                jPaneShowOperation.setContent(arrayListToString());
                if (arrayListToString().length()>35){
                   jPaneShowOperation.arrowButtonVisible( true );
                }
                break;

            }

            case "-":{
                setOperator(textFieldContent,"-");
                String showResult=arrayListToString();
                jPaneShowOperation.setContent(showResult);
              //  System.out.println(arrayListToString());
                break;
            }

            case "*":{
                setOperator(textFieldContent,"*");
                jPaneShowOperation.setContent(arrayListToString());
                break;
            }

            case "/":{
                setOperator(textFieldContent,"/");
                jPaneShowOperation.setContent(arrayListToString());
                break;
            }

            case "1/x":{
                setOperatorComplex(textFieldContent,"1/x");
                jPaneShowOperation.setContent(arrayListToString());
                break;
            }

            case "x2":{
                setOperatorComplex(textFieldContent,"x2");
                jPaneShowOperation.setContent(arrayListToString());
                break;
            }

            case "%":{
                setOperatorComplex(textFieldContent,"%");
                jPaneShowOperation.setContent(arrayListToString());

                break;
            }

            case "sqrt":{
                setOperatorComplex(textFieldContent,"sqrt");
                jPaneShowOperation.setContent(arrayListToString());

                break;
            }

            case "CE":{
                textField.setText("0");
                break;
            }

            case "C":{
                textStoredOperation.clear();
                textStoredOperationValue.clear();
                jPaneShowOperation.setContent(" ");
                textField.setText("0");
                break;
            }

            case "=":{
                setOperator(textFieldContent,"=");
                String textArea;
                if (textStoredOperationValue.get(textStoredOperationValue.size()-1).equals("=")){
                    textArea= arrayListToString()+String.valueOf(showResult)+System.lineSeparator();
                }
                else{
                    textArea=arrayListToString()+"("+arrayListToStringExceptLast()+") ="+String.valueOf(showResult)+System.lineSeparator();
                }
                textAreaResults.append(textArea);
                textStoredOperation.clear();
                textStoredOperationValue.clear();
                jPaneShowOperation.setContent(" ");
                textField.setText(showResult.toLowerCase());
                operation=true;
                break;
            }

            case "MC":{
                jLabelShowMemorizedData.setText( "0" );
                break;
            }

            case "MS":{
                String storeData=textField.getText();
                jLabelShowMemorizedData.setText( storeData );
                System.out.println( storeData );
                break;
            }

            case "MR":{
                String getData=jLabelShowMemorizedData.getText();
                textField.setText( getData.toLowerCase() );
                break;
            }

            case "M+":{
                String getData=jLabelShowMemorizedData.getText();
                String storeData=textField.getText();
                BigDecimal addmemory=new BigDecimal( getData ).add(new BigDecimal( storeData )) ;
                String resultInString= addmemory.toString();
                textField.setText( resultInString.toLowerCase() );
                break;
            }

            case "M-":{
                String getData=jLabelShowMemorizedData.getText();
                String storeData=textField.getText();
                BigDecimal addmemory=new BigDecimal( storeData ).subtract(new BigDecimal(getData));
                String resultInString= addmemory.toString();
                textField.setText( resultInString.toLowerCase() );
                break;

            }
            case "clearTextArea": {
                textAreaResults.setText( "" );
                break;
            }

        }
    }

    @Override
    public  void mouseWheelMoved(MouseWheelEvent e) {
           if (e.getScrollType() == WHEEL_UNIT_SCROLL) {
            int totalScrollAmount = e.getUnitsToScroll();

        }

    }

    }


