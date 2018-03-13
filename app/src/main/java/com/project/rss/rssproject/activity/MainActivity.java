package com.project.rss.rssproject.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.rss.rssproject.R;
import com.project.rss.rssproject.adapter.MainRecyclerViewAdapter;
import com.project.rss.rssproject.model.Deal;
import com.project.rss.rssproject.tool.RssDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;


public class MainActivity extends AppCompatActivity implements MainRecyclerViewAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    static final String url = "https://www.dealabs.com/rss/hot";

    ArrayList deals;
    SwipeRefreshLayout swipeRefreshLayout;

    MainRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        deals = new ArrayList();
        getRSSDatas();
        adapter = new MainRecyclerViewAdapter(deals, this);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    /**
     * get data from RSS feed
     */
    private void getRSSDatas() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //converting xml into json
                        XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
                        JSONObject jsonObject = xmlToJson.toJson();


                        // clear the table
                        FlowManager.getDatabase(RssDatabase.class).reset();

                        try {

                            //extract information array from jSon
                            JSONArray jsonArray = jsonObject.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");

                            deals.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                //Extract deals informations
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String title = jsonObject1.getString("title");
                                String category = jsonObject1.getString("category");
                                String imageUrl = jsonObject1.getJSONObject("media:content").getString("url");
                                String description = jsonObject1.getString("description");

                                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.UK);

                                long timeStamp = formatter.parse(jsonObject1.getString("pubDate")).getTime();


                                Deal d = new Deal(title, category, imageUrl, description, timeStamp);
                                deals.add(d);
                                //save deal into database
                                d.save();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //notify the adapter
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //if problems happen with the network request
                        //we get information from the database
                        deals.clear();
                        deals.addAll(SQLite.select()
                                .from(Deal.class)
                                .queryList());
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
        queue.add(strRequest);
    }

    /**
     * item from the list is clicked => call the DetailActivity
     * @param deal
     */
    @Override
    public void onClick(Deal deal) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("deal", deal);
        startActivity(i);
    }


    /**
     * called when user swipe down to refresh
     */
    @Override
    public void onRefresh() {
        getRSSDatas();
    }






}
