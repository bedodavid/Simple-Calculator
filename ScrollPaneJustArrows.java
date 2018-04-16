package simplecalculator;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class ScrollPaneJustArrows extends JPanel {
    private String content;
    private JTextPane textArea;
    final Color lightGreyColor = new Color( 230, 230, 230 );
    final Color bitDarkerGreyColor = new Color( 220, 220, 220 );
    BasicArrowButton west,east;

    public ScrollPaneJustArrows(String textAreaContent) {
        this.content=textAreaContent;
        setLayout( new BorderLayout() );
        textArea = new JTextPane( ){
            public boolean getScrollableTracksViewportWidth() {
                Component parent = getParent();
                ComponentUI ui = getUI();
                return parent != null ? (ui.getPreferredSize(this).width <= parent
                        .getSize().width) : true;
            }
        };

        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        textArea.setFont( new Font( "Monospaced", Font.BOLD, 15 ) );
        textArea.setAlignmentX( RIGHT_ALIGNMENT );
        textArea.setBackground( bitDarkerGreyColor );
        textArea.setText( content );
        textArea.setEnabled( false);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setBorder( null );
        JScrollPane scrollPane = new JScrollPane( textArea );
        scrollPane.setBackground( lightGreyColor );
        scrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER );
        scrollPane.setBorder( null );
        add( scrollPane );

        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();


        west = new BasicArrowButton( BasicArrowButton.WEST );
        west.setAction( new ActionMapAction( "", horizontal, "negativeUnitIncrement" ) );
        west.setBorder( null );
        west.setBackground( bitDarkerGreyColor );
        add( west, BorderLayout.WEST );

        east = new BasicArrowButton( BasicArrowButton.EAST );
        east.setAction( new ActionMapAction( "", horizontal, "positiveUnitIncrement" ) );
        east.setBorder( null );
        east.setBackground( bitDarkerGreyColor );
        add( east, BorderLayout.EAST );
    }


    public void setContent(String showContent) {
       //this.content=showContent;
       textArea.setText( showContent );

    }

    public String getContent() {
        return textArea.getText();
    }

    public void arrowButtonVisible(boolean needVisible) {
         east.setVisible( needVisible );
         west.setVisible( needVisible );
        }
    }


