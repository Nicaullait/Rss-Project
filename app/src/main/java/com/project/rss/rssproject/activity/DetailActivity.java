package com.project.rss.rssproject.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.rss.rssproject.R;
import com.project.rss.rssproject.model.Deal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    Deal deal;
    TextView title;
    TextView category;
    TextView dateTV;
    TextView description;
    ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deal = (Deal) getIntent().getExtras().getSerializable("deal");

        //extract views
        title = (TextView) findViewById(R.id.title);
        category = (TextView) findViewById(R.id.category);
        dateTV = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);
        photo = (ImageView) findViewById(R.id.image_details);

        //load photo from url
        Glide.with(this)
                .load(deal.getImageUrl())
                .into(photo).onLoadFailed(new Exception(), getResources().getDrawable(R.color.colorPrimaryDark, null));

        title.setText(deal.getTitle());
        category.setText(deal.getCategorie());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(deal.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(deal.getDescription()));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy à HH:mm:ss ", Locale.FRANCE);
        dateTV.setText(formatter.format(new Date(deal.getTimeStamp())));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //back arrow is pressed
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
