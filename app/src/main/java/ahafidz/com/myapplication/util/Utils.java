package ahafidz.com.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import ahafidz.com.myapplication.R;
import ahafidz.com.myapplication.listener.ConfirmDialogListener;

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

    public static void showAlertDialog(AlertDialog mAlertDialog) {

        if(mAlertDialog != null && !mAlertDialog.isShowing())
            mAlertDialog.show();
    }

    public static void hideAlertDialog(AlertDialog mAlertDialog) {

        if(mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();
    }

    public static void showConfirmDialog(Context context, final ConfirmDialogListener menuClickListener, final String title){
        AlertDialog alertDialog= null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(context.getString(R.string.text_are_you_sure));
        builder.setPositiveButton(context.getString(R.string.text_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alertdialog, int which) {
                alertdialog.cancel();
                menuClickListener.confirmListener(title);
            }
        });
        builder.setNegativeButton(context.getString(R.string.text_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alertdialog, int which) {

                alertdialog.cancel();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
