package ahafidz.com.myapplication.util;

import android.widget.EditText;

public class TextUtils {

    public static boolean isValidEmail(String input){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    public static void setError(EditText editText, String errorString) {

        editText.setError(errorString);

    }

    public static void clearError(EditText editText) {

        editText.setError(null);

    }

}
