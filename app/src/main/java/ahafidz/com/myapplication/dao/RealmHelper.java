package ahafidz.com.myapplication.dao;


import android.util.Log;

import ahafidz.com.myapplication.bean.ValidateData;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

//    Realm realm;
//
//    public  RealmHelper(Realm realm){
//        this.realm = realm;
//    }
    private static final String TAG = RealmHelper.class.getSimpleName();


    public RealmResults<User> getUser() {
        Realm realm = null;
        RealmResults<User> users = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<User> resUser = realm.where(User.class).findAll();
            if (resUser.size() > 0) {
                users = resUser;
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {
            if(realm != null){
                realm.close();
            }
        }

        return users;
    }

    public void insertUser(ValidateData data, String jwt){
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            User user = realm.createObject(User.class);
            user.setId(data.getId());
            user.setEmail(data.getEmail());
            user.setJwt(jwt);
            user.setFirstName(data.getFirstName());
            user.setLastName(data.getLastName());

            realm.commitTransaction();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
    }

    public void deleteUser(){
        Realm realm = null;
        realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {
            if(realm != null){
                realm.close();
            }
        }
    }
}
