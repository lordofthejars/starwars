package org.starwars;

import javax.enterprise.inject.Produces;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AverageFormatterProducer {

    @Produces @AverageFormatter
    public DecimalFormat averageFormatter() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return decimalFormat;
    }

}
