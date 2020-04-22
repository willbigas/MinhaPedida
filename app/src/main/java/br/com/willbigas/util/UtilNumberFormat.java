package br.com.willbigas.util;

import java.text.NumberFormat;

public class UtilNumberFormat {

    public static String deDecimalParaStringR$(String decimal) {
        return NumberFormat.getCurrencyInstance().format(decimal.toString());

    }

    public static String deDecimalParaStringR$(Double decimal) {
        return NumberFormat.getCurrencyInstance().format(decimal);
    }

    public static String deDecimalParaStringR$(Float decimal) {
        return NumberFormat.getCurrencyInstance().format(decimal);
    }


    public static String deDecimalParaString(String decimal) {
        return NumberFormat.getInstance().format(decimal.toString());

    }

    public static String deDecimalParaString(Double decimal) {
        return NumberFormat.getInstance().format(decimal);
    }

    public static String deDecimalParaString(Float decimal) {
        return NumberFormat.getInstance().format(decimal);
    }
}
