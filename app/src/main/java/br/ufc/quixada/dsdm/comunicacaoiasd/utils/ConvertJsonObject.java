package br.ufc.quixada.dsdm.comunicacaoiasd.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.dsdm.comunicacaoiasd.model.SearchResult;
import br.ufc.quixada.dsdm.comunicacaoiasd.model.YouTubeVideo;

/**
 * Created by Márcio Sn on 24/12/2015.
 */
public class ConvertJsonObject {

    private static final String TAG = ConvertJsonObject.class.getSimpleName();
    private List<YouTubeVideo> youTubeVideos;
    private SearchResult result;

    public ConvertJsonObject() {
        this.youTubeVideos = new ArrayList<>();
    }

    public SearchResult convert(JSONObject response){

        try {
            JSONArray items = response.getJSONArray("items");
            JSONObject item = null;
            for(int i = 0 ; i < items.length(); i++){
                //get json objects

                item = items.getJSONObject(i);
                JSONObject id = item.getJSONObject("id");
                String type = id.getString("kind");
                if(!type.contains("youtube#video"))
                    continue;
                JSONObject snippet = item.getJSONObject("snippet");
                JSONObject thumb = snippet.getJSONObject("thumbnails");
                JSONObject default_thumb = thumb.getJSONObject("default");
                JSONObject medium_thumb = thumb.getJSONObject("medium");
                JSONObject high_thumb = thumb.getJSONObject("high");

                //get string values from json objects

                String videoId = id.getString("videoId");
                String title = snippet.getString("title");
                String thumb_default = default_thumb.getString("url");
                String thumb_medium = medium_thumb.getString("url");
                String thumb_high = high_thumb.getString("url");
                String description = snippet.getString("description");
                String publishedAt = convertToDate( snippet.getString("publishedAt") );
                String channelTitle = snippet.getString("channelTitle");

                YouTubeVideo youTubeVideo = new YouTubeVideo();

                youTubeVideo.setId(String.valueOf(i));
                youTubeVideo.setVideoId(videoId);
                youTubeVideo.setTitle(title);
                youTubeVideo.setThumbnailDefault(thumb_default);
                youTubeVideo.setThumbnailMedium(thumb_medium);
                youTubeVideo.setThumbnailHigh(thumb_high);
                youTubeVideo.setDescription(description);
                youTubeVideo.setPublishedAt(publishedAt);
                youTubeVideo.setChannelTitle(channelTitle);

                youTubeVideos.add(youTubeVideo);
            }

            //get result search full object

            result = new SearchResult();
            JSONObject pageInfo =  response.getJSONObject("pageInfo");

            String kind = response.getString("kind");
            String etag = response.getString("etag");
            String nextPageToken = response.getString("nextPageToken");

            int totalResults = pageInfo.getInt("totalResults");
            int resultsPerPage = pageInfo.getInt("resultsPerPage");

            result.setKind(kind);
            result.setEtag(etag);
            result.setNextPageToken(nextPageToken);
            result.setTotalResults(totalResults);
            result.setResultsPerPage(resultsPerPage);
            result.setYouTubeVideos(youTubeVideos);
            Log.d(TAG, result.toString());

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.d(TAG, result.toString());
        return result;
    }


    public static String convertToDate(String publishedAt){
        long epoch = 0L;
        String date = "";
        try {
            epoch = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse( publishedAt ).getTime() / 1000;
            date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format( new java.util.Date ( epoch * 1000 ) );
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        return date;
    }
}
