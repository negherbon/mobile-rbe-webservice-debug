package br.com.pontosistemas.webservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.squareup.okhttp.internal.Util;


public class RBEWebservice extends CordovaPlugin {
	
	public static final String NATIVE_ACTION_STRING = "webservice";
	public static final String SUCCESS_PARAMETER="success";
	private CordovaInterface activity;
	
	// Construtor
	public RBEWebservice(){
		
	}
	
	
	/*
	 * @param int action 
	 * action 1 = Write data on file
	 * action 2 = update data on file
	 * */
	/*public void getDataSaveFile(int action) {
		new AsyncTask<String, Void, JSONObject>() {
			JSONObject jsonItem;

			@Override
			protected void onPreExecute() {

			}

			protected JSONObject doInBackground(String... urls) {
				try {

					jsonItem = Json.getJson("http://www.rbenergia.com.br/ws/wsrbe.php");

				} catch (Exception e) {
					Log.d("ERROR", "Erro ao buscar os dados");
				}
				return jsonItem;
			}

			@Override
			protected void onPostExecute(JSONObject jsonData) {
				super.onPostExecute(jsonData);
				try {
					
					switch(action){
						case 1:
							writeFileInternalStorage(jsonData.toString(), this.activity.getActivity().getApplicationContext(), "rbe.json");
							break;
						case 2:
							updateFileInternalStorage(jsonData.toString(), this.activity.getActivity().getApplicationContext(), "rbe.json");
							break;
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.execute();
	}
	
	public boolean isSdReadable() {

		boolean mExternalStorageAvailable = false;
		try {
			String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				mExternalStorageAvailable = true;
				Log.i("isSdReadable", "External storage card is readable.");
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				Log.i("isSdReadable", "External storage card is readable.");
				mExternalStorageAvailable = true;
			} else {
				mExternalStorageAvailable = false;
			}
		} catch (Exception ex) {

		}
		return mExternalStorageAvailable;
	}
	
	public void writeFileInternalStorage(String strWrite,
			Context context, String fileName) {
		try {
			// Check if Storage is Readable
			if (isSdReadable()) // isSdReadable()e method is define at bottom of
								// the post
			{
				String smsfilename = fileName;
				FileOutputStream fos = context.openFileOutput(smsfilename,
						Context.MODE_PRIVATE);
				fos.write(strWrite.getBytes());
				fos.flush();
				fos.close();

			}
		} catch (Exception e) {
			// Your Code
		}
	}

	public String readFileInternalStorage() {
		
		String stringToReturn = " ";
		try {
			if (isSdReadable()) // isSdReadable()e method is define at bottom of
								// the post
			{
				// String sfilename = fileName;
				InputStream inputStream = this.activity.getActivity().getApplicationContext().openFileInput("rbe");

				if (inputStream != null) {
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader bufferedReader = new BufferedReader(
							inputStreamReader);
					String receiveString = "";
					StringBuilder stringBuilder = new StringBuilder();

					while ((receiveString = bufferedReader.readLine()) != null) {
						stringBuilder.append(receiveString);
					}
					inputStream.close();
					stringToReturn = stringBuilder.toString();
				}
			}
		} catch (FileNotFoundException e) {
			Log.e("TAG", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("TAG", "Can not read file: " + e.toString());
		}
	
		return stringToReturn;
	}*/
	
	 /**
	     * Sets the context of the Command. This can then be used to do things like
	     * get file paths associated with the Activity.
	     *
	     * @param cordova The context of the main Activity.
	     * @param webView The CordovaWebView Cordova is running in.
	     */
	    
	 public void initialize(CordovaInterface cordova, CordovaWebView webView) {
	      Log.d("Inicializo", "Deu uma inicializada no bixo");
	      this.activity = cordova;
	      super.initialize(cordova, webView);
	  }
	 
	 
	 
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		Log.d("Hello Plugin", "Hello, this is a native function called on javascript function");
		
		if (action.equals("webservice")){
			File path = Environment.getDataDirectory();
			Log.d("Path", path);
			Log.d("Absolutepath", path.getAbsolutePath());
			
			/*File file = new File();
			
			if (!file){
				// se o arquivo nÃ£o existir
				this.getDataSaveFile(1); // pega os dados do webservice e grava nele
			}else{
				// se o arquivo existir dÃ¡ um update
				this.getDataSaveFile(2);
			}*/
			
			JSONObject r = new JSONObject();
			r.put("retorno", "retorno");
			callbackContext.success(r);
			
            return true;
		}
		
		/*if (action.equals("getData")){
			String data = this.readFileInternalStorage();
			r.put("data", data);
		}*/
		
		
		return false;
	}

	
}
