package com.example.android.theguardian;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity implements NewsAdapter.OnItemClickListner{
    private RecyclerView recyclerView;
    private  NewsAdapter newsAdapter;
    private ArrayList<News> arrayList;
    private final String GUARDIAN_URL="https://content.guardianapis.com/search?api-key=34fc79e1-eccd-4b69-b017-924268af138d&show-fields=thumbnail&show-tags=contributor";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<News>();

        newsAdapter=new NewsAdapter(getApplicationContext(),arrayList,this);
        recyclerView.setAdapter(newsAdapter);
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(arrayList.get(position).getUrl()));
        startActivity(intent);
    }

    class NewsAsyncTask extends AsyncTask<URL,Void,ArrayList<News>>{

            @Override
            protected ArrayList<News> doInBackground(URL... urls) {
                URL url = createUrl(GUARDIAN_URL);

                String jsonResponse = "";
                try {
                    jsonResponse = makeHttpRequest(url);
                } catch (IOException e) {
                }

                ArrayList<News> news = extractFeatureFromJson(jsonResponse);
                return  news;
            }


            @Override
            protected void onPostExecute(ArrayList<News> news) {
                if (news == null) {
                    return;
                }
            newsAdapter.notifyDataSetChanged();
            }

            private URL createUrl(String stringUrl) {
                URL url = null;
                try {
                    url = new URL(stringUrl);
                } catch (MalformedURLException exception) {
                    return null;
                }
                return url;
            }

            private String makeHttpRequest(URL url) throws IOException {
                String jsonResponse = "";
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000 /* milliseconds */);
                    urlConnection.setConnectTimeout(15000 /* milliseconds */);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } catch (IOException e) {
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (inputStream != null) {
                        // function must handle java.io.IOException here
                        inputStream.close();
                    }
                }
                return jsonResponse;
            }

           private String readFromStream(InputStream inputStream) throws IOException {
                StringBuilder output = new StringBuilder();
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    while (line != null) {
                        output.append(line);
                        line = reader.readLine();
                    }
                }
                return output.toString();
            }


            private ArrayList<News> extractFeatureFromJson(String newsJson) {
                try {
                    JSONObject baseJsonResponse = new JSONObject(newsJson);
                    JSONObject response = baseJsonResponse.getJSONObject("response");
                    JSONArray results=response.getJSONArray("results");
                    for (int i=0;i<results.length();i++) {
                        JSONObject news  = results.getJSONObject(i);
                        String section=news.getString("sectionName");
                        String title=news.getString("webTitle");
                        String date=news.getString("webPublicationDate");
                        JSONObject fields = news.getJSONObject("fields");
                        String thumbnail=fields.getString("thumbnail");
                        JSONArray tags=news.getJSONArray("tags");
                        JSONObject first=tags.getJSONObject(0);
                        String authorName=first.getString("webTitle");
                        String webUrl=news.getString("webUrl");
                        arrayList.add(new News(title,authorName,date,section,thumbnail,webUrl));

                    }
                } catch (JSONException e) {
                }
                return arrayList;
            }

        }
}