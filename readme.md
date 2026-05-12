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

## Reference

- [ハンズオンで学ぶサーバーサイド Kotlin（Spring Boot&Arrow）v2.2.1](https://zenn.dev/msksgm/books/implementing-server-side-kotlin-development/viewer/chapter-02-02-spring-intro-hello-world)
 
