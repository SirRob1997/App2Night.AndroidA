package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.backendTasks.party.GetPartyList;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.exceptions.GPSUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.GetPartyListSave;
import de.dhbw.utils.Gps;


/**
 * Created by Flo on 27.10.2016.
 */

public class HomeFragment extends Fragment implements GetPartyList {
    private List<Party> partyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PartiesAdapter pAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        pAdapter = new PartiesAdapter(partyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        adaptParties(GetPartyListSave.getInstance().getList());
        try {
            double[] gpsResult = Gps.getInstance().getGPSCoordinates();
            new GetPartyListTask(this,gpsResult[0],gpsResult[1],100);
        } catch (GPSUnavailableException e) {
            //TODO: Behandlung im Fall, dass GPS nicht verfügbar ist
            e.printStackTrace();
        }






        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private synchronized void adaptParties(Party[] parties){
        for (Party party:parties) {
            partyList.add(party);
        }
        pAdapter.notifyDataSetChanged();


    }


    @Override
    public void onSuccessGetPartyList(Party[] parties) {
       adaptParties(parties);
    }

    @Override
    public void onFailGetPartyList(Party[] parties) {

    }
}
