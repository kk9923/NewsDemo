package kx.newsdemo.module.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by 31716 on 2017/5/24.
 */

public class NewsFragment extends Fragment {
    String [] tiltes = {"1","1","1","1","243","1","1","5","1","1","1","1","1","2","9","1","1","1"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ListView textView = new ListView(getActivity());
        textView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,tiltes));
        return textView;
    }

}
