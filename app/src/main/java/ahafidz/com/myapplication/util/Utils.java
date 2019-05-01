package ahafidz.com.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Utils {

    public static void showToast(Context context, String message){
        Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(ProgressDialog mProgressDialog) {

        if(mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public static void hideDialog(ProgressDialog mProgressDialog) {

        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public static void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar progressBar) {

        progressBar.setVisibility(View.GONE);
    }
}
