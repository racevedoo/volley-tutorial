package br.ufpe.cin.volleytutorial.network;

public interface NetworkRequestCallback<T> {

	public void onRequestResponse(T response);
	public void onRequestError(Exception error);
	
}

