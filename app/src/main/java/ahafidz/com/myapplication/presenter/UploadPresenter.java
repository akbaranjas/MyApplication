package ahafidz.com.myapplication.presenter;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

public interface UploadPresenter{
    void getKategori();
    void doUpload(File file, HashMap<String, RequestBody> map);
}