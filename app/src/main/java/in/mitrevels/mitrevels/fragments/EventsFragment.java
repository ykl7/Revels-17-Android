package in.mitrevels.mitrevels.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.mitrevels.mitrevels.R;
import in.mitrevels.mitrevels.adapters.DayPagerAdapter;

/**
 * Created by anurag on 6/12/16.
 */
public class EventsFragment extends Fragment {

    private static final int UPDATE_EVENTS = 1;

    public EventsFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.revels));
        setHasOptionsMenu(true);

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().findViewById(R.id.toolbar).setElevation(0);
                AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.main_app_bar_layout);
                appBarLayout.setElevation(0);
                appBarLayout.setTargetElevation(0);
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        final TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.events_tab_layout);
        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.events_view_pager);

        DayPagerAdapter pagerAdapter = new DayPagerAdapter(getChildFragmentManager());
        DayFragment[] fragments = new DayFragment[4];
        for (int i=0; i<4; i++){
            fragments[i] = new DayFragment();
        }
        pagerAdapter.add(fragments[0], "Day 1");
        pagerAdapter.add(fragments[1], "Day 2");
        pagerAdapter.add(fragments[2], "Day 3");
        pagerAdapter.add(fragments[3], "Day 4");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        switch(formattedDate){

            case "08-03-2017":{
                viewPager.setCurrentItem(0);
                break;
            }
            case "09-03-2017":{
                viewPager.setCurrentItem(1);
                break;
            }
            case "10-03-2017":{
                viewPager.setCurrentItem(2);
                break;
            }
            case "11-03-2017":{
                viewPager.setCurrentItem(3);
                break;
            }
            default: viewPager.setCurrentItem(0);

        }

        if (!getActivity().getIntent().getBooleanExtra("dataLoaded", false)){
            try{
                fragments[0].prepareData(UPDATE_EVENTS);
                fragments[1].displayData();
                fragments[2].displayData();
                fragments[3].displayData();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setHasOptionsMenu(false);
        setMenuVisibility(false);
    }
}