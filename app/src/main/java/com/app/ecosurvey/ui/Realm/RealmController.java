package com.app.ecosurvey.ui.Realm;

import android.content.Context;
import android.util.Log;

import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;
import com.app.ecosurvey.ui.Model.Realm.Object.CachedCategory;
import com.app.ecosurvey.ui.Model.Realm.Object.CachedResult;
import com.app.ecosurvey.ui.Model.Realm.Object.ChecklistCached;
import com.app.ecosurvey.ui.Model.Realm.Object.Image;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ListSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ListSurveyRequest;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        try {
            final RealmResults<UserInfoCached> result = realm.where(UserInfoCached.class).findAll();
            realm.beginTransaction();
            result.clear();
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e("clearCached", e.getMessage());
        }

        realm.beginTransaction();
        UserInfoCached realmObject = realm.createObject(UserInfoCached.class);
        realmObject.setUserInfoString(userInfoReceive);
        realm.commitTransaction();
        realm.close();

    }

    public void saveChecklist(Context context, String checklistReceive) {

        Realm realm = getRealmInstanceContext(context);

        //clear user info in realm first.
        final RealmResults<ChecklistCached> result = realm.where(ChecklistCached.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        ChecklistCached realmObject = realm.createObject(ChecklistCached.class);
        realmObject.setCheckListString(checklistReceive);
        realm.commitTransaction();
        realm.close();

    }

    public void saveInitChecklist(Context context, String initchecklistReceive) {

        Realm realm = getRealmInstanceContext(context);

        //clear user info in realm first.
        final RealmResults<ChecklistCached> result = realm.where(ChecklistCached.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        ChecklistCached realmObject = realm.createObject(ChecklistCached.class);
        realmObject.setCheckListString(initchecklistReceive);
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

    public void surveyLocalStorageS4(Context context, String id, /*List<SelectedImagePath> imageList*/String listImage) {

        //move to realm list

        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();

        //realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setImagePath(listImage);

        realm.commitTransaction();
        realm.close();

    }

    public void surveyLocalStorageS5(Context context, String id, String time, String status, String lineStatus, String fromApiID) {

        Realm realm = getRealmInstanceContext(context);

        realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setSurveyLocalProgress(status);
        survey.setSurveyStatus(lineStatus);
        survey.setStatusUpdated(time);
        if (fromApiID != null) {
            survey.setLocalSurveyID(fromApiID);
        }
        realm.commitTransaction();
        realm.close();
    }

    public void surveyLocalStorageS6(Context context, String id, String listVideo) {

        //move to realm list

        Realm realm = getRealmInstanceContext(context);
        realm.beginTransaction();

        //realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setVideoPath(listVideo);

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

    public void updateLocalRealm(Context context, ListSurveyReceive listSurveyReceive) {

        Realm realm = getRealmInstanceContext(context);

        for (int x = 0; x < listSurveyReceive.getData().size(); x++) {

            String surveyID = listSurveyReceive.getData().get(x).getId();
            //listSurveyReceive.getData().get(x).getSurveyID();

            realm.beginTransaction();

            //need to change updated date here.
            //if local updated than api. skip. else update. vice versa

            //if id already exist in realm - UPDATE
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", surveyID).findFirst();
            if (survey != null) {

                Date date = null, date2 = null;
                try {
                    DateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
                    date = format.parse(survey.getStatusUpdated());
                    date2 = format.parse(listSurveyReceive.getData().get(x).getUpdated_at());

                } catch (Exception e) {

                }

                if (date != null && date2 != null) {
                    if (date2.after(date)) {

                        String categoryName, parlimenName;
                        if (listSurveyReceive.getData().get(x).getContent().get(0).getCategoryName() != null)
                            categoryName = listSurveyReceive.getData().get(x).getContent().get(0).getCategoryName();
                        else
                            categoryName = "-";

                        if (listSurveyReceive.getData().get(x).getLocationName() != null)
                            parlimenName = listSurveyReceive.getData().get(x).getLocationName();
                        else
                            parlimenName = "-";


                        survey.setSurveyIssue(listSurveyReceive.getData().get(x).getContent().get(0).getIssue());
                        survey.setSurveyWishlist(listSurveyReceive.getData().get(x).getContent().get(0).getWishlist());

                        survey.setSurveyCategory(categoryName + "/" + listSurveyReceive.getData().get(x).getContent().get(0).getCategoryid());
                        survey.setSurveyParliment(parlimenName + "/" + listSurveyReceive.getData().get(x).getLocationCode());
                        survey.setSurveyStatus("API-STATUS");
                        survey.setSurveyLocalProgress("Completed");

                        survey.setStatusCreated(listSurveyReceive.getData().get(x).getCreated_at());
                        survey.setStatusUpdated(listSurveyReceive.getData().get(x).getUpdated_at());

                    }
                }


            } else {

                LocalSurvey newSurvey = realm.createObject(LocalSurvey.class);
                newSurvey.setLocalSurveyID(surveyID);
                newSurvey.setSurveyLocalProgress("Completed");
                newSurvey.setSurveyStatus("API-STATUS");
                newSurvey.setSurveyIssue(listSurveyReceive.getData().get(x).getContent().get(0).getIssue());
                newSurvey.setSurveyWishlist(listSurveyReceive.getData().get(x).getContent().get(0).getWishlist());
                newSurvey.setSurveyCategory(listSurveyReceive.getData().get(x).getContent().get(0).getCategoryName() + "/" + listSurveyReceive.getData().get(x).getContent().get(0).getCategoryid());
                newSurvey.setSurveyParliment(listSurveyReceive.getData().get(x).getLocationName() + "/" + listSurveyReceive.getData().get(x).getLocationCode());

                newSurvey.setStatusCreated(listSurveyReceive.getData().get(x).getCreated_at());
                newSurvey.setStatusUpdated(listSurveyReceive.getData().get(x).getUpdated_at());

            }

            realm.commitTransaction();

        }


        realm.close();

    }


    public void surveyPhotoUpdate(Context context, String id, String time) {

        Realm realm = getRealmInstanceContext(context);

        realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setPhotoUpdateDate(time);
        realm.commitTransaction();
        realm.close();
    }

    public void surveyVideoUpdate(Context context, String id, String time) {

        Realm realm = getRealmInstanceContext(context);

        realm.beginTransaction();
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();
        survey.setVideoUpdateDate(time);
        realm.commitTransaction();
        realm.close();
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

    public void clearLocalSurvey(Context act) {

        Realm realm = getRealmInstanceContext(act);

        try {
            final RealmResults<LocalSurvey> result = realm.where(LocalSurvey.class).findAll();
            realm.beginTransaction();
            result.clear();
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            Log.e("clearCached", e.getMessage());
        }

    }
}
