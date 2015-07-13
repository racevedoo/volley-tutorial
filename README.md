# Tutorial [Volley](https://developer.android.com/training/volley/index.html)
Tutorial desenvolvido para a disciplina IF1001(Programação 3) do Centro de Informática da UFPE

## Conteúdo
* O que é volley?
* Vantagens
* Implementação

## O que é volley?

O volley é uma biblioteca para Android, desenvolvida pelo Google, que é utilizada para manter as conexões HTTP mais simples e rápidas, provendo grande flexibilidade para chamadas que retornam vários tipos de dados, desde JSON até imagens

## Vantagens
* Fornece uma API de alto nível
* Possui uma `RequestQueue` robusta e capaz de tratar diversas requisições simultaneamente
* Cache bem tratado
* Tratamento de erros simples, mas eficaz

## Implementação
Para melhor organização e entendimento do código, foram criadas a classe `NetworkQueue` (responsável por integragir com a API do volley) e a interface `NetworkRequestCallback`(que funciona como callback para chamadas na UI) no pacote `br.ufpe.cin.volleytutorial.network`

### NetworkQueue
Antes de podermos utilizar a classe `NetworkQueue`, devemos instanciá-la e, de alguma forma, "guardar a instância" para que esta possa ser acessada de qualquer lugar do código que deseje realizar requisições HTTP. A forma escolhida consiste em instanciar a classe no método onCreate da classe que estende `android.app.Application` no seu projeto(no nosso caso, `GoogleVolleyApplication`), como mostrado abaixo:
```
  @Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
	}
```

Para "guardar a instância", utilizamos um [Singleton](https://en.wikipedia.org/wiki/Singleton_pattern), garantindo que só vai haver uma instância de `NetworkQueue` na aplicação.

Na classe `NetworkQueue`, 5 métodos públicos são utilizados para manipulação de requisições, são eles:

#### doGet
Este método recebe uma url(url destino da requisição), um objeto para servir como tag e um `NetworkRequestCallback` que tem seus métodos chamados quando a requisição termina, com sucesso ou com erro. Este método adiciona à fila do volley uma requisição HTTP do tipo GET  que espera uma resposta no formato de um objeto JSON.

Um exemplo de uso deste método é o seguinte trecho de código da classe `MainActivity`
```
mGetItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doGet(
		        	"" + mIdEditText.getText().toString(),
		        	TAG,
		        	new NetworkRequestCallback<JSONObject>() {
		        		@Override
						public void onRequestResponse(JSONObject jsonObject) {
							Log.d(TAG, "GET onRequestResponse!" + "\n" + jsonObject.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "GET onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
    }
```
Este trecho de código diz que quando o botão `mGetItButton` for clicado, uma requisição do tipo GET é disparada, através da instância `mNetworkQueue` da classe `NetworkQueue`.

#### doGetArray
Similar ao doGet, porém espera um array JSON.

#### doPost
Similar ao doGet, porém a requisição disparada é do tipo POST.

#### cancelAllRequests
Cancela todas as requisições na fila do volley, através do método cancelAll de `RequestQueue` do volley.

#### cancelRequestsByTag
Cancela as requisições na fila do volley que tiverem a tag especificada, através do método cancelAll de `RequestQueue` do volley.

É importante notar que o método buildJSONRequest é utilizado em todos os métodos que disparam requisições, pois ele constrói, a partir dos seus parâmetros, um `JsonObjectRequest`, que é passado para o volley.
