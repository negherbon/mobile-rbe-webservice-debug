package br.com.pontosistemas.webservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;


public class RBEWebservice extends CordovaPlugin {
	CordovaInterface activity;
	
	// Construtor
	public RBEWebservice(){
		
	}
	
	public String getDataFromDirectory(String filename) throws IOException{
		 FileInputStream fin = this.activity.getActivity().openFileInput(filename);
		  int c;
		  String temp="";
		  while( (c = fin.read()) != -1){
		     temp = temp + Character.toString((char)c);
		  }
		  //string temp contains all the data of the file.
		  fin.close();
		  return temp;
	}
	
	public String getDataFromWeb() throws IOException{
		URL url = new URL("http://www.rbenergia.com.br/ws/wsrbe.php");
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		try {
		   String str = readStream(in);
		   return str;
		}finally {
		   in.close();
		}
	}
	
	
	private String readStream(InputStream is) {
	    try {
	      ByteArrayOutputStream bo = new ByteArrayOutputStream();
	      int i = is.read();
	      while(i != -1) {
	        bo.write(i);
	        i = is.read();
	      }
	      return bo.toString();
	    } catch (IOException e) {
	      return "";
	    }
	}
	
	// Joga dados dentro de um arquivo
	public void printDataOnFile(String filename, String content) throws IOException{
		FileOutputStream outputStream;
		outputStream = this.activity.getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
		outputStream.write(content.getBytes());
		outputStream.close();
	}
	
	public void createNewFile(String filename){
		// Cria um novo arquivo
		File file = new File(activity.getActivity().getFilesDir(), filename);
	}
	
	 public boolean fileExists(String filename){
		 File file = this.activity.getActivity().getFileStreamPath(filename);
		 return file.exists();
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
					writeFileInternalStorage(jsonData.toString(), this.activity.getActivity().getApplicationContext(), "rbe.json");
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
		String filename = "data.json";
		
		
		if (action.equals("webservice")){
		
			try {
				String dados = getDataFromWeb();
				// Se o arquivo existe
				if (fileExists(filename)){
					Log.d("Arquivo ", "O ARQUIVO EXISTE");
					// Atualiza o conteÃƒÆ’Ã‚Âºdo dele
					printDataOnFile(filename, dados);
				}else{
					createNewFile(filename);
					printDataOnFile(filename, dados);
				}
			  
			} catch (Exception e) {
			  e.printStackTrace();
			}
			
			Log.d("Retorno ", "Vai retornar");
			JSONObject r = new JSONObject();
			r.put("retorno1", "retorno2");
			callbackContext.success(r);
            return true;
		}
		
		// Se for sÃƒÂ³ pra pegar os dados
		if (action.equals("getData")){
			Log.d("GETDATA", "Getdata");
			try {
				JSONObject r = new JSONObject();
				r.put("data", getDataFromDirectory(filename));
				callbackContext.success(r);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	
}
