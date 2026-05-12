# プロジェクトで使用されているアノテーション

本プロジェクトのソースコードおよびテストコードにおいて、多様なSpring Boot、Jakarta Persistence、およびJUnitアノテーションが使用されている。

## 1. Spring Boot コア・構成関連

### @SpringBootApplication

- **効果**: `@Configuration`、`@EnableAutoConfiguration`、`@ComponentScan` の3つのアノテーションを組み合わせたアノテーション。Spring Bootアプリケーションの設定、自動構成、およびコンポーネントスキャンを有効にする
- **追加理由**: `SpringbootSampleApplication.kt` に付与されており、このクラスがアプリケーションの起動エントリーポイントであることを定義するため

### @Service

- **効果**: クラスがビジネスロジックを保持する「サービス」であると示すアノテーション。Springのコンポーネントスキャンの対象となり、Beanとして登録される
- **追加理由**: `JsonProductService` および `MySqlProductService` に付与されており、これらをサービスコンポーネントとしてSpringコンテナに管理させるため

### @Primary

- **効果**: 同一のインターフェースを実装した複数のBeanが存在する場合、注入対象として優先的に選択されることを指定する
- **追加理由**: `JsonProductService` に付与。`ProductService` の実装が複数ある中で、デフォルトでJSONベースのサービスを使用するように指定するため

### @Repository

- **効果**: クラスまたはインターフェースがデータアクセス層（リポジトリ）であることを示す。データアクセスに関する例外をSpringのデータアクセス例外に変換する機能も持つ。
- **追加理由**: `ProductRepository` に付与されており、Spring Data JPAのリポジトリとして認識させるため

## 2. Spring Web (REST API) 関連

### @RestController

- **効果**: `@Controller` と `@ResponseBody` を組み合わせたアノテーション。そのクラスのメソッドの戻り値が、ビューではなくHTTPレスポンスボディとして直接書き出される（通常はJSON形式）
- **追加理由**: `HelloWorldController` や `ProductController` に付与。これらがRESTful Webサービスのコントローラであることを定義するため

### @RequestMapping

- **効果**: 特定のURLパスやHTTPメソッドをコントローラクラスまたはメソッドにマッピング
- **追加理由**: `ProductController` のクラスレベルに付与されており、このコントローラ内の全エンドポイントのベースパスを `/api/products` に統一するため

### @GetMapping

- **効果**: HTTP GETリクエストを特定のハンドラメソッドにマッピングする `@RequestMapping(method = RequestMethod.GET)` のショートカット
- **追加理由**: 各コントローラのメソッドに付与されており、特定のリソース取得リクエスト（全件取得、ID指定取得など）に対応させるため

### @PathVariable

- **効果**: URIテンプレート変数（URLパス内の動的な部分）をメソッドの引数にバインドする
- **追加理由**: `ProductController` の `getById` や `getByCategory` メソッドで使用されており、URLパスに含まれるIDやカテゴリ名を引数として受け取るため

## 3. 永続化 (Jakarta Persistence / JPA) 関連

### @Entity

- **効果**: クラスがJPAのエンティティであることを示し、データベースのテーブルとマッピングされる対象であることを定義
- **追加理由**: `ProductEntity` に付与されており、データベース内の商品データを表すオブジェクトとして定義するため

### @Table

- **効果**: エンティティに対応するデータベースのテーブル名を明示的に指定する
- **追加理由**: `ProductEntity` を `products` テーブルにマッピングするために使用

### @Id

- **効果**: エンティティの主キー（プライマリキー）を指定する
- **追加理由**: `ProductEntity` の `id` フィールドを主キーとして定義するため

### @Column

- **効果**: フィールドを特定のデータベースカラムにマッピングする。属性によりカラム名や制約などを指定できる
- **追加理由**: `imageUrl` フィールドをデータベース上の `image_url` カラムに対応付けるために使用されている

### @Convert / @Converter

- **効果**: データベースのデータ型とエンティティのフィールド型の間の変換ロジックを適用する。`@Converter` は変換ロジックを持つクラスに、`@Convert` はその変換を利用するフィールドに付与する
- **追加理由**: `ProductEntity` の `tags`（List<String>）をデータベース保存時に文字列としてシリアライズし、取得時にデシリアライズするために `TagsConverter` を適用している

### @Transactional

- **効果**: メソッドまたはクラス全体をトランザクションの境界として定義する。`readOnly = true` を指定すると、読み取り専用トランザクションとして最適化される
- **追加理由**: `MySqlProductService` の各メソッドに付与されており、データベース操作の一貫性を保証し、読み取り専用操作のパフォーマンスを向上させるため

## 4. テスト関連

### @SpringBootTest

- **効果**: Spring Bootのアプリケーションコンテキスト全体をロードしてテストを実行するためのアノテーション
- **追加理由**: `SpringbootSampleApplicationTests` に付与されており、アプリケーションが正常に起動することを確認する結合テストを行うため

### @WebMvcTest

- **効果**: Webレイヤー（コントローラ）のみに焦点を当てたテストを行うためのアノテーションである。関連するMVC構成のみがロードされ、フルコンテキストの起動よりも高速になる
- **追加理由**: `HelloWorldControllerTest` や `ProductControllerTest` で使用されており、HTTPリクエストのハンドリングロジックを単体テストするため

### @Autowired

- **効果**: Springコンテナが管理するBeanを自動的に注入する
- **追加理由**: テストクラス内で `MockMvc` などの必要なコンポーネントを注入するために使用されている

### @MockitoBean

- **効果**: Springのアプリケーションコンテキスト内の既存のBeanをMockitoのモックで置き換える
- **追加理由**: `ProductControllerTest` において、本物の `ProductService` の代わりにモックを注入し、サービスの振る舞いを制御しながらコントローラをテストするため

### @Test

- **効果**: メソッドがJUnitのテストケースであることを示す
- **追加理由**: 各テストクラス内のテスト実行メソッドを定義するために使用

## 5. その他

### @Suppress

- **効果**: コンパイラの警告を抑制する
- **追加理由**: `TagsConverter` において、ジェネリクスの型キャストに伴う警告（UNCHECKED_CAST）を抑制し、コードの意図を明確にするために使用
