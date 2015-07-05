package br.ufpe.cin.volleytutorial.android;

import br.ufpe.cin.volleytutorial.network.NetworkQueue;
import android.app.Application;

public class GoogleVolleyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
	}
	
}
