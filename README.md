# FLAT-Android

### 接続先
接続先の候補は3つあります。アプリを起動する前に必ず`app/src/main/java/com/websarva/wings/android/flat/api/Params.kt`を開き、指定するBASE_URL以外をすべてコメントアウトしてください。

1. mockサーバーに接続する場合(実機、エミュレーター問わない)
```
const val BASE_URL = "http://34.68.157.198:8080"
```
2. エミュレーターでdockerで立てたローカルサーバーに接続する場合
```
const val BASE_URL = "http://10.0.2.2:3000"
```
3. 実機でdockerで立てたローカルサーバーに接続する場合
```
const val BASE_URL = "http://127.0.0.1:3000"
```

また、3. 実機でdockerで立てたローカルサーバーに接続する場合 では、上記の他にも幾つかの手順を踏む必要があります。

1. chrome://inspect/#devices にPCでアクセスする
1. Discover USB devices と Discover network targets にチェックをいれる
1. Port forwarding settings をクリックして、Portを3000, IP address and port をlocalhost:3000とし、Doneをクリックする
1. Android端末をPCにUSB接続し、ページ上にデバイス名が表示されることを確認する
![実機接続設定](https://user-images.githubusercontent.com/52447646/142002474-3b3d6b6a-a828-4b0d-9c82-01eaedc4a79d.png)

画像のように、端末名の横にポート番号とともに緑色の丸がついていればOKです。
