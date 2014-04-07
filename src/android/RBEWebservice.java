package br.com.pontosistemas.webservice;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import com.the9tcat.hadi.DefaultDAO;

public class RBEWebservice extends CordovaPlugin {
	private CordovaInterface activity;
	
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
	
	public String getDataFromDB(){
		Context ctx = activity.getActivity().getApplicationContext();
		DefaultDAO dao = new DefaultDAO(ctx); // Dao
		// Verifica se ja tem o registro no banco
		List<Data> dataDB = (List<Data>)dao.select(Data.class, false, null, null, null, null, null, null); 
		
		return dataDB.get(0).data;
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
	

	 /**
	     * Sets the context of the Command. This can then be used to do things like
	     * get file paths associated with the Activity.
	     *
	     * @param cordova The context of the main Activity.
	     * @param webView The CordovaWebView Cordova is running in.
	     */
	    
	 public void initialize(CordovaInterface cordova, CordovaWebView webView) {
	      this.activity = cordova;
	      super.initialize(cordova, webView);
	 }
	
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		//String filename = "data.json";
		Context ctx = activity.getActivity().getApplicationContext();
		
		if (action.equals("webservice")){
		
			try {
				// Insere os dados no banco de dados
				String dados = getDataFromWeb();
				DefaultDAO dao = new DefaultDAO(ctx); // Dao
				Data dataJson = new Data(); // Model
				dataJson.data = dados;
				
				// Verifica se ja tem o registro no banco
				List<Data> dataDB = (List<Data>)dao.select(Data.class, false, null, null, null, null, null, null); 
				
				if (dataDB.size()==0){
					dao.insert(dataJson);
				}else{
					dao.update(dataJson, null, null, null);
				}
				
			} catch (Exception e) {
			  e.printStackTrace();
			}

			callbackContext.success("success");
            return true;
		}
		
		// Se for para pegar os dados do device
		if (action.equals("getData")){
			callbackContext.success(getDataFromDB());
			return true;
		}

		return false;
	}

	
}
