package info.b3.q1.medictime.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import info.b3.q1.medictime.R;

public class PriseListFragment extends androidx.fragment.app.Fragment{
    private RecyclerView mCrimeRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prises_list_fragment, container,
                false);
        mCrimeRecyclerView = (RecyclerView)
                view.findViewById(R.id.prises_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity()));
        return view;
    }
}
