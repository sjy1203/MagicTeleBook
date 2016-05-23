package com.daydayup.magictelebook.util;

import android.content.Context;
import android.os.AsyncTask;

import com.daydayup.magictelebook.weather.HttpUtils;
import com.daydayup.magictelebook.weather.WeatherInfo;
import com.daydayup.magictelebook.weather.XML;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jay on 16/5/23.
 */
public class WeatherParseUtil {
    public static final String BASE_URL = "http://wthrcdn.etouch.cn/weather_mini?citykey=";

    public static void parseWeather(Context context,String country,OnWeatherInfoListener onWeatherInfoListener){

        int weatherCode = XML.parse(context,country);
        if (weatherCode == -1){
            onWeatherInfoListener.onFail("城市不存在");
            return;
        }
        new WeatherAsyncTask(onWeatherInfoListener).execute(BASE_URL+weatherCode);
    }
    public static class WeatherAsyncTask extends AsyncTask<String,Object,WeatherInfo>{
        OnWeatherInfoListener onWeatherInfoListener;
        public WeatherAsyncTask(OnWeatherInfoListener onWeatherInfoListener){
            this.onWeatherInfoListener = onWeatherInfoListener;
        }
        @Override
        protected WeatherInfo doInBackground(String... params) {
            WeatherInfo weatherInfo = new WeatherInfo();
            try {
                JSONObject jsonObject = new JSONObject(HttpUtils.getJsonContent(params[0]));
                JSONObject data = jsonObject.getJSONObject("data");
                weatherInfo.setTemperature(data.getString("wendu"));
                weatherInfo.setGanmao(data.getString("ganmao"));

                JSONArray resultJsonArray = data.getJSONArray("forecast");
                //只获取当天的
                JSONObject resJson = resultJsonArray.getJSONObject(0);
                weatherInfo.setDate(resJson.getString("date"));
                weatherInfo.setTempHighest(resJson.getString("high"));
                weatherInfo.setTempLowest(resJson.getString("low"));
                weatherInfo.setWeatherStatus(resJson.getString("type"));
                weatherInfo.setWindDirection(resJson.getString("fengxiang"));
                weatherInfo.setWindPower(resJson.getString("fengli"));

            } catch (JSONException e) {
                e.printStackTrace();
                onWeatherInfoListener.onFail(e.getMessage().toString());
            }
            return weatherInfo;
        }

        @Override
        protected void onPostExecute(WeatherInfo weatherInfo) {
            onWeatherInfoListener.onSuccess(weatherInfo);
        }
    }
    public interface OnWeatherInfoListener{
        void onSuccess(WeatherInfo weatherInfo);
        void onFail(String msg);
    }

    //        WeatherParseUtil.parseWeather(getApplicationContext(), "襄阳", new WeatherParseUtil.OnWeatherInfoListener() {
//            @Override
//            public void onSuccess(WeatherInfo weatherInfo) {
//                T.showShort(getApplicationContext(),"success");
//                L.e(weatherInfo.toString());
//            }
//
//            @Override
//            public void onFail(String msg) {
//                T.showShort(getApplicationContext(),msg);
//            }
//        });
}
