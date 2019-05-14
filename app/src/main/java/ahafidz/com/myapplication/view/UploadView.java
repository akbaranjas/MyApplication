package ahafidz.com.myapplication.view;

import java.util.List;

import ahafidz.com.myapplication.bean.Record;
import ahafidz.com.myapplication.bean.UploadResponse;

public interface UploadView {
    void showLoading();
    void hideLoading();
    void showList(List<Record> records);
    void showError(String message);
    void onSuccess(String message);
    void upload(UploadResponse response);
}
