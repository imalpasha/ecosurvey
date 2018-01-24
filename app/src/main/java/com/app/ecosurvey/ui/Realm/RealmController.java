package com.app.ecosurvey.ui.Realm;

import android.content.Context;
import android.util.Log;

import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;
import com.app.ecosurvey.ui.Model.Realm.Object.CachedCategory;
import com.app.ecosurvey.ui.Model.Realm.Object.CachedResult;
import com.app.ecosurvey.ui.Model.Realm.Object.Image;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.google.gson.Gson;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by Dell on 11/14/2017.
 */

public class RealmController {


    public static Realm getRealmInstanceContext(Context act) {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(act).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    public RealmController(Context context) {
        MainApplication.get(context).getNetComponent().inject(this);
    }

    //get default result cached (remove later)
    public RealmResults<CachedResult> getCachedResult(Context act) {

        Realm realm = getRealmInstanceContext(act);
        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();

        return result;
    }

    //cached default request
    public void cachedResult(Context act, String cachedResult, String cachedApi) {

        Realm realm = getRealmInstanceContext(act);

        try {
            final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
            realm.beginTransaction();
            result.clear();
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e("cachedResult1", e.getMessage());
        }

        try {
            realm.beginTransaction();
            CachedResult realmObject = realm.createObject(CachedResult.class);
            realmObject.setCachedResult(cachedResult);
            realmObject.setCachedAPI(cachedApi);
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            Log.e("cachedResul2", e.getMessage());
        }


    }

    //use gson first
    public void saveCategory(Context context, final CategoryReceive data) {

        Realm realm = getRealmInstanceContext(context);

        //clear user info in realm first.
        final RealmResults<CachedCategory> result = realm.where(CachedCategory.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        //convert to gsom
        Gson gson = new Gson();
        String list = gson.toJson(data);

        //add new user info in realm
        realm.beginTransaction();
        CachedCategory realmObject = realm.createObject(CachedCategory.class);
        realmObject.setCategoryList(list);
        realm.commitTransaction();
        realm.close();


    }

    //update-later
    /*public void saveCategory(Context context, final CategoryReceive data) {

        final CachedCategory product = new CachedCategory();
        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < data.getData().getItems().size(); i++) {
                    product.setId(data.getData().getItems().get(i).getId());
                    product.setTitle(data.getData().getItems().get(i).getTitle());
                    realm.i(product);
                }
            }
        });

        realm.commitTransaction();
        realm.close();


    }*/

    public void saveUserInfo(Context context, String userInfoReceive) {

        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();
        UserInfoCached realmObject = realm.createObject(UserInfoCached.class);
        realmObject.setUserInfoString(userInfoReceive);
        realm.commitTransaction();
        realm.close();

    }

    public void surveyLocalStorageS0(Context context, String surveyID, String progress, String date) {

        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();
        LocalSurvey realmObject = realm.createObject(LocalSurvey.class);
        realmObject.setLocalSurveyID(surveyID);
        realmObject.setSurveyLocalProgress(progress);
        realmObject.setSurveyStatus("Local");
        realmObject.setStatusCreated(date);
        realm.commitTransaction();
        realm.close();

    }

    public void surveyLocalStorageS1(Context context, String randomID, String category, String parliment, String progress) {

        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();

        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

        Log.e("SAVED", category + "_" + parliment);
        survey.setSurveyCategory(category);
        survey.setSurveyParliment(parliment);

        realm.commitTransaction();

        realm.close();
    }

    public void surveyLocalStorageS2(Context context, String id, String surveyIssue) {

        Realm realm = getRealmInstanceContext(context);

        realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setSurveyIssue(surveyIssue);
        realm.commitTransaction();

        realm.close();

    }

    public void surveyLocalStorageS3(Context context, String id, String surveyWishlist) {

        Realm realm = getRealmInstanceContext(context);

        realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setSurveyWishlist(surveyWishlist);
        realm.commitTransaction();

        realm.close();

    }

    public void surveyLocalStorageS4(Context context, String id, List<SelectedImagePath> imageList /*String image*/) {

        //move to realm list

        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();

        RealmList<Image> realmList = new RealmList<Image>();
        for (int y = 0; y < imageList.size(); y++) {
            Image image = new Image();
            image.setImagePath(imageList.get(y).getImagePath());
            realmList.add(image);
            Log.e("IMAGE_PATH", image.getImagePath());
        }


        //realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setImagePath(realmList);

        realm.commitTransaction();
        realm.close();

    }

    public void surveyLocalStorageS5(Context context, String id, String time){

        Realm realm = getRealmInstanceContext(context);


        realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setSurveyLocalProgress("Completed");
        survey.setStatusUpdated(time);
        realm.commitTransaction();
        realm.close();
    }

    public static <E extends RealmObject> void clearCachedList(Realm realm, Class<E> clazz) {

        try {
            final RealmResults<E> result = realm.where(clazz).findAll();
            realm.beginTransaction();
            result.clear();
            realm.commitTransaction();
        } catch (Exception e) {
        }

    }

    public void clearCachedResult(Context act) {

        Realm realm = getRealmInstanceContext(act);

        try {
            final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
            realm.beginTransaction();
            result.clear();
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            Log.e("clearCached", e.getMessage());
        }

    }
}
