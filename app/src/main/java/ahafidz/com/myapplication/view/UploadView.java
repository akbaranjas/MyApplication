package ahafidz.com.myapplication.view;

import java.util.List;

import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.bean.UploadResponse;
import ahafidz.com.myapplication.bean.Uploaded;

public interface UploadView {
    void showLoading();
    void hideLoading();
    void showList(List<Record> records);
    void showError(String message);
    void onSuccess(Uploaded result);
    void upload(UploadResponse response);
}
