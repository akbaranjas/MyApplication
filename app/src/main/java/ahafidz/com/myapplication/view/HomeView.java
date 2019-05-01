package ahafidz.com.myapplication.view;

import java.util.List;

import ahafidz.com.myapplication.bean.Record;

public interface HomeView {
    void showLoading();
    void hideLoading();
    void showList(List<Record> records);
    void showError(String message);
}
