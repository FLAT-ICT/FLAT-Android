package com.websarva.wings.android.flat.api

class Params {
    companion object {
        // mockサーバーの場合
//        const val BASE_URL = "http://34.68.157.198:8080"
        // dockerで立てたローカルサーバーにエミュレーターで接続する場合
        const val BASE_URL = "http://10.0.2.2:3000"
        // dockerで立てたローカルサーバーに実機で接続する場合
        // chrome://inspect/#devices にPCでアクセスし、 Discover USB devices と Discover network targets にチェックをいれる
        // Port forwarding settings をクリックして、Portを3000, IP address and port をlocalhost:3000とし、Doneをクリック
//        const val BASE_URL = "http://127.0.0.1:3000"
    }
}