import java.math.RoundingMode;
import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LunarPhaseController {

    @FXML private TextField dayTextField;
    @FXML private TextField monthTextField;
    @FXML private TextField yearTextField;
    @FXML private ImageView lunarPhaseImage;
    @FXML private TextField lunarPhaseName;
    
    public void initialize() {
    	dayTextField.textProperty().addListener(new dateBoxListener());
    	monthTextField.textProperty().addListener(new dateBoxListener());
    	yearTextField.textProperty().addListener(new dateBoxListener());
    }
    
    public class dateBoxListener implements ChangeListener<String> {
    	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	
    		if (!dayTextField.getText().isEmpty() && !monthTextField.getText().isEmpty() && !yearTextField.getText().isEmpty()) {
	    		Double daysSinceNewMoon = convertJulianDate() - 2451549.5; // Current date - Julian Day of 1/6/2000
	        	Double cycles = daysSinceNewMoon / 29.53;
	
	        	Double daysIntoCycle = cycles % 1;
	        	DecimalFormat df = new DecimalFormat();
	        	df.setRoundingMode(RoundingMode.CEILING);
	        	
	        	daysIntoCycle = Double.valueOf(df.format(daysIntoCycle));
	        	daysIntoCycle = daysIntoCycle * 29.53;
	        	
	        	Image newMoonImage = new Image("file:///Users/carly/eclipse-workspace/ExCredit_LunarPhase/src/newMoon.png");
	        	Image firstQuarterMoon = new Image("file:///Users/carly/eclipse-workspace/ExCredit_LunarPhase/src/firstQuarterMoon.jpg");
	        	Image fullMoonImage = new Image("file:///Users/carly/eclipse-workspace/ExCredit_LunarPhase/src/fullMoon.jpg");
	        	Image thirdQuarterMoon = new Image("file:///Users/carly/eclipse-workspace/ExCredit_LunarPhase/src/thirdQuarterMoon.jpg");
	        	
	        	if (daysIntoCycle >= 0 && daysIntoCycle <= 7.38) {
	        		lunarPhaseImage.setImage(newMoonImage);
	        		lunarPhaseName.setText("New Moon");
	        	} else if (daysIntoCycle >= 7.39 && daysIntoCycle <= 14.77) {
	        		lunarPhaseImage.setImage(firstQuarterMoon);
	        		lunarPhaseName.setText("First Quarter Moon");
	        	} else if (daysIntoCycle >=14.78 && daysIntoCycle <= 22.15) {
	        		lunarPhaseImage.setImage(fullMoonImage);
	        		lunarPhaseName.setText("Full Moon");
	        	} else {
	        		lunarPhaseImage.setImage(thirdQuarterMoon);
	        		lunarPhaseName.setText("Third Quarter Moon");
	        	}
	    	}
		}
    }

    private Double convertJulianDate() {
    	Double julianDate = 0.0;
    	Double y = new Double(yearTextField.getText());
    	Double m = new Double(monthTextField.getText());
    	Double d = new Double(dayTextField.getText());
    	Double a, b, c, e, f;
    	
    	if (m == 1 || m == 2) {
    		y = y - 1;
    		m = m + 12;
    	}
    	
    	a = y / 100;
    	b = a / 4;
    	c = 2 - a + b;
    	e = 365.25 * (y + 4716);
    	f = 30.6001 * (m + 1);
    	julianDate = c + d + e + f - 1524.5;
    
		return julianDate;	
    }
}
