package ahafidz.com.myapplication.presenter;

import ahafidz.com.myapplication.bean.CreateDataRequest;

public interface HomePresenter {
    void getData();
    void logout();
    void insertData(CreateDataRequest createDataRequest);
    void updateData(CreateDataRequest createDataRequest);
    void deleteData(CreateDataRequest createDataRequest);
}
