package com.example.acer.pricelist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Acer on 1/11/2016.
 */
public class MyFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "FRAGMENT";

    ArrayList<DatModle> listData = new  ArrayList<>();


    private int mPage;
    protected RecyclerView mRecyclerView;
    private static final int SPAN_COUNT = 2;
     PriceAdapter mAdapter;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView.LayoutManager mLayoutManager;
     JSONArray obj;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    public static MyFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "create");

        mPage = getArguments().getInt(ARG_PAGE);
        String tag_json_arry = "json_array_req";

        String url = "http://fabfresh.elasticbeanstalk.com:80/cloth/type/";

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString()+"");
                        if(response != null && response.length() > 0)
                        {

                            obj = new JSONArray();
                            obj=response;
                            getData();

                        } else {
                            Log.e(TAG, "obj is null");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Log.e(TAG, "Error: " + error.getMessage() + "");
                //pDialog.hide();
            }
        }){ @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer hari");

            return headers;
        }};

        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG, "On create");
        View view = inflater.inflate(R.layout.myfragment_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
           // mRecyclerView.setAdapter(mAdapter);
        //TextView textView = (TextView) view.findViewById(R.id.textView);

        switch(mPage){
            case 1: mAdapter = new PriceAdapter(listData, 1);
                mRecyclerView.setAdapter(mAdapter);

                break;
            case 2:
                mAdapter = new PriceAdapter(listData, 2);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);

                break;
            case 3:
                mAdapter = new PriceAdapter(listData, 3);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);

        }

        return view;
    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }




    private void getData() {
        Log.e(TAG, "get data");
        for (int i = 0; i < obj.length(); i++) {

            DatModle datModle = new DatModle();

            String t, wi, w, iP;

            try {
                JSONObject jsonobject = obj.getJSONObject(i);

                t = jsonobject.getString("type_name");

                wi= jsonobject.getString("type_price_wash_and_iron");
                w = jsonobject.getString("type_price_wash");
                iP = jsonobject.getString("type_price_iron");


                datModle.setiPrice(iP);
                datModle.setType(t);
                datModle.setwPrice(w);
                datModle.setWiPrice(wi);

                listData.add(datModle);
                //Log.e(TAG, datModle.getType()+"");

                mAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
  }

}