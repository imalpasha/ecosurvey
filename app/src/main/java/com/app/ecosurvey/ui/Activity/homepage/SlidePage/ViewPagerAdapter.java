package com.app.ecosurvey.ui.Activity.homepage.SlidePage;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.ecosurvey.ui.Activity.homepage.MySurveyFragment;
import com.app.ecosurvey.ui.Activity.homepage.MyWishlistFragment;
import com.app.ecosurvey.ui.Activity.homepage.ProfileFragment;

/**
 * Created by hp1 on 21-01-2015.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    String Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    String status;
    Context context;
    Activity act;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, String myImageList[], int mNumbOfTabsumb, Context context, Activity act) {
        super(fm);

        this.Titles = myImageList;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.status = status;
        this.context = context;
        this.act = act;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            ProfileFragment tab1 = new ProfileFragment();
            return tab1;

        } else if (position == 1) {
            MySurveyFragment tab2 = new MySurveyFragment();
            return tab2;

        } else {
            MyWishlistFragment tab3 = new MyWishlistFragment();
            return tab3;
        }

    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        //Drawable image = ContextCompat.getDrawable(context, Titles[position]);
        //image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        //image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());

        //SpannableString sb = new SpannableString(" ");
        //ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        //sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}