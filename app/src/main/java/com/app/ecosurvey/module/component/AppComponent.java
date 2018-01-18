package com.app.ecosurvey.module.component;

import com.app.ecosurvey.api.ApiRequestHandler;
import com.app.ecosurvey.api.ApiService;
import com.app.ecosurvey.module.AppModule;
import com.app.ecosurvey.module.NetModule;
import com.app.ecosurvey.module.PresenterModule;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.homepage.MySurveyFragment;
import com.app.ecosurvey.ui.Activity.homepage.MyWishlistFragment;
import com.app.ecosurvey.ui.Activity.homepage.ProfileFragment;
import com.app.ecosurvey.ui.Activity.login.LoginFragment;
import com.app.ecosurvey.ui.Activity.splash.SplashFragment;
import com.app.ecosurvey.ui.Activity.survey.CategoryParlimenFragment;
import com.app.ecosurvey.ui.Activity.survey.SurveyIssueFragment;
import com.app.ecosurvey.ui.Activity.survey.SurveyPhotoFragment;
import com.app.ecosurvey.ui.Activity.survey.SurveyReviewFragment;
import com.app.ecosurvey.ui.Activity.survey.SurveyVideoFragment;
import com.app.ecosurvey.ui.Activity.survey.SurveyWishlistFragment;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

//import com.app.tbd.ui.Activity.MultiCampaign.MultiCampaignFragment;


@Singleton
@Component(modules = {AppModule.class, NetModule.class, PresenterModule.class})
public interface AppComponent {

    Bus bus();
    ApiService apiService();

    void inject(ApiRequestHandler frag);
    void inject(MainPresenter homePresenter);
    void inject(LoginFragment loginFragment);

    void inject(MyWishlistFragment myWishlistFragment);
    void inject(MySurveyFragment mySurveyFragment);
    void inject(ProfileFragment profileFragment);

    void inject(CategoryParlimenFragment categoryParlimentFragment);
    void inject(SurveyIssueFragment surveyIssueFragment);
    void inject(SurveyWishlistFragment surveyWishlishFragment);
    void inject(SurveyPhotoFragment surveyPhotoFragment);
    void inject(SurveyVideoFragment surveyVideoFragment);
    void inject(SurveyReviewFragment surveyReviewFragment);
    void inject(SplashFragment splashFragment);

    void inject(RealmController realmController);

}




