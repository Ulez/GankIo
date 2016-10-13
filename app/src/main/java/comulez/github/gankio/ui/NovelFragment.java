package comulez.github.gankio.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import comulez.github.gankio.R;
import comulez.github.gankio.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NovelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout mTablayout;
    private ViewPager mViewPager;


    public NovelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NovelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NovelFragment newInstance(String param1, String param2) {
        NovelFragment fragment = new NovelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
        mTablayout.addTab(mTablayout.newTab().setText(R.string.xuanhuan));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.wuxia));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.yanqing));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.dushi));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.xuanyi));
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }
    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NovelListFragment.newInstance(Constant.NOVEL_TYPE_XUANHUAN), getString(R.string.xuanhuan));
        adapter.addFragment(NovelListFragment.newInstance(Constant.NOVEL_TYPE_WUXIA), getString(R.string.wuxia));
        adapter.addFragment(NovelListFragment.newInstance(Constant.NOVEL_TYPE_YANQING), getString(R.string.yanqing));
        adapter.addFragment(NovelListFragment.newInstance(Constant.NOVEL_TYPE_DUSHI), getString(R.string.dushi));
        adapter.addFragment(NovelListFragment.newInstance(Constant.NOVEL_TYPE_XUANYI), getString(R.string.xuanyi));
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
