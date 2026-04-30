# Spring Boot with Kotlin

## Setup

### データベース設定（MySQL）

`resources/application.properties` でDB名、DBユーザ名、DBパスワードを変更

```
spring.datasource.url=jdbc:mysql://localhost:3306/dbname?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=12345
```

`resources/data/products.sql` をインポートしてデータ挿入

## Usage

### ハローワールド

```shell
curl --location --request GET 'http://localhost:8080/'
```

### 全件取得

```shell
curl --location --request GET 'http://localhost:8080/api/products'
```

### IDで1件取得

```shell
curl --location --request GET 'http://localhost:8080/api/products/P001'
curl --location --request GET 'http://localhost:8080/api/products/P002'
```

### カテゴリで絞り込んで取得

```shell
curl --location --request GET 'http://localhost:8080/api/products/category/electronics'
curl --location --request GET 'http://localhost:8080/api/products/category/furniture'
curl --location --request GET 'http://localhost:8080/api/products/category/sports'
curl --location --request GET 'http://localhost:8080/api/products/category/daily_goods'
```

### リソースの切替

商品情報をJSONからではなくデータベース（MySQL）から取得する

- `service/JsonProductService.kt` の `@Primary` を消去
- `service/MySqlProductService.kt` に `@Primary` を追加

## Note

### SprintBoot

- SpringBootは、容易にWeb開発が行えるように、SpringFrameworkから必要な機能をまとめたもの

### `by lazy`

- Kotlinの遅延初期化の仕組み。プロパティへの初回アクセス時に一度だけ初期化処理を実行し、その結果をキャッシュする
- `by lazy` はキャッシュするため、JSONファイルを書き換えてもアプリを再起動しないと反映されない。今回のような静的な商品マスタデータには適しているが、頻繁に更新されるデータには向いていない

```kt
private val products: List<Product> by lazy {
    // アプリ起動時ではなく、初めてproductsが参照されたときに実行される
    val resource = ClassPathResource("data/products.json")
    val wrapper = objectMapper.readValue(resource.inputStream, ProductWrapper::class.java)
    wrapper.products
}
```

### `ObjectMapper.readValue()`

- JacksonライブラリのAPIで、JSONを指定したKotlin/Javaのクラスに変換（デシリアライズ）するメソッド（ここでは `ProductWrapper::class.java` に変換した）

```
【JSONファイルの中身】                【Kotlinクラスに変換】

{                                   ProductWrapper(
  "products": [                         products = listOf(
    {                                       Product(
      "id": "P001",                             id = "P001",
      "name": "ワイヤレスイヤホン",               name = "ワイヤレスイヤホン",
      "price": 12800                            price = 12800
    },                  ────────▶           ),
    ...                                     ...
  ]                                   )
}                                   )
```

- `ProductWrapper::class.java` はkotlinでJavaのClassオブジェクトを取得するための書き方
- `ObjectMapper.readValue()` はJacksonのメソッドで、Javaライブラリとして作られているため、JavaのClassオブジェクトしか受け付けないため、この書き方で実装している

### データクラス

- データ保持に特化したクラス
- 通常のクラスと異なり、以下のメソッドが自動生成される
  - `equals()` : プロパティの値で等値比較
  - `hashCode()` : プロパティをもとにハッシュ値を生成
  - `toString()` : プロパティを含む文字列を返す
  - `copy()` : 一部のプロパティだけ変えたコピー生成
  - `componentN()` : 分解宣言（destructuring）に使用

```kt
data class Product(val id: String, val name: String, val price: Int)

val p1 = Product("P001", "イヤホン", 12800)
val p2 = Product("P001", "イヤホン", 12800)
val p3 = Product("P002", "キーボード", 9800)

// equals() 値が同じなら true
println(p1 == p2)  // true
println(p1 == p3)  // false

// toString() 中身が見やすい
println(p1)  // Product(id=P001, name=イヤホン, price=12800)

// copy() 一部だけ変えたコピーを作成
val sale = p1.copy(price = 9800)
println(sale)  // Product(id=P001, name=イヤホン, price=9800)

// componentN() 分解宣言
val (id, name, price) = p1
println(id)    // P001
println(name)  // イヤホン
println(price) // 12800
```

- Javaだと `record` がもっとも近い
- ただし、Javaの `record` は `copy()` が自動生成されない

## Log

- IntelliJ IDEAのインストール
- JDK25インストール
- JDK25環境変数（PATH）の設定
- [spring initializr](https://start.spring.io/)でひな形をダウンロード
- IntelliJ IDEAでダウンロードしたひな形を開く
- `build.gradle.kts` に `implementation("org.springframework.boot:spring-boot-starter-web")` を追記
- `HelloWolrdContller.kt` を追加
- `SpringbootSampleApplication.kt` でファイルを実行
- `curl` でリクエスト送り `Hello World` が返ってくれば成功
- 
## Reference

- [ハンズオンで学ぶサーバーサイド Kotlin（Spring Boot&Arrow）v2.2.1](https://zenn.dev/msksgm/books/implementing-server-side-kotlin-development/viewer/chapter-02-02-spring-intro-hello-world)
 