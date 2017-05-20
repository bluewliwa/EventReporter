package com.laioffer.eventreporter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laioffer.eventreporter.artifacts.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowEventFragment extends Fragment {

    ListView listView;
    DatabaseReference database;
    EventAdapter eventAdapter;
    List<Event> events;

    public ShowEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_show_event, container, false);
        listView = (ListView) view.findViewById(R.id.event_listview);
        final String username = ((EventActivity)getActivity()).getUsername();
        database = FirebaseDatabase.getInstance().getReference();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        events = new ArrayList<Event>();
        database.child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Event event = noteDataSnapshot.getValue(Event.class);
                    events.add(event);
                }
                Event[] eventArr = toArray(events);
                Arrays.sort(eventArr, new Comparator<Event>() {
                    public int compare(Event e1, Event e2) {
                        if (e1.getTime() == e2.getTime()) {
                            return 0;
                        } else {
                            return e1.getTime() > e2.getTime() ? -1 : 1;
                        }
                    }
                });

                events = toList(eventArr);
                eventAdapter = new EventAdapter(getContext(), events);
                eventAdapter.setUserName(((EventActivity)getActivity()).getUsername());
                listView.setAdapter(eventAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: do something
            }
        });
    }

    private List<Event> toList(Event[] array){
        List<Event> events = new ArrayList<>();
        for(Event event : array) {
            events.add(event);
        }
        return events;
    }
    private Event[] toArray(List<Event> events) {
        Event[] array = new Event[events.size()];
        for (int i = 0; i < events.size(); i++) {
            array[i] = events.get(i);
        }
        return array;
    }

}
