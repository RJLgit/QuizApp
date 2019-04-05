package com.example.android.myquizapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GlobalScoreFragment extends Fragment {
    public GlobalScoreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_global_scores, container, false);
        TextView value = fView.findViewById(R.id.globalScoreValue);
        TextView person = fView.findViewById(R.id.globalScorePerson);;
        TextView date = fView.findViewById(R.id.globalScoreDate);;

        return fView;
    }
}
