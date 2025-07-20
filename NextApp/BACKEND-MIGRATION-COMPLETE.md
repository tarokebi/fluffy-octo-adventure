# Spring Boot バックエンド移植完了

Spring Boot バックエンドの機能を Next.js API Route (TypeScript) に移植しました。

## 移植されたAPI一覧

### 認証API
- **POST** `/api/auth/login`
  - Spring Boot: `AuthController.createAuthenticationToken()`
  - 機能: ユーザー認証、JWTトークン生成
  - リクエスト: `{ username: string, password: string }`
  - レスポンス: `{ token: string }`

### ユーザー管理API
- **GET** `/api/users`
  - Spring Boot: `UserController.getAllUsers()`
  - 機能: 全ユーザー一覧取得（JWT認証必須）

- **POST** `/api/users`
  - Spring Boot: `UserController.createUser()`
  - 機能: 新規ユーザー作成（JWT認証必須）
  - リクエスト: `{ name: string, email: string, password: string }`

- **GET** `/api/users/[id]`
  - Spring Boot: `UserController.getUserById()`
  - 機能: ユーザー詳細取得（JWT認証必須）

- **PUT** `/api/users/[id]`
  - Spring Boot: `UserController.updateUser()`
  - 機能: ユーザー情報更新（JWT認証必須）
  - リクエスト: `{ name: string, email: string }`

- **DELETE** `/api/users/[id]`
  - Spring Boot: `UserController.deleteUser()`
  - 機能: ユーザー削除（JWT認証必須）

### 商品カタログAPI
- **GET** `/api/catalog`
  - Spring Boot: `CatalogController.getAllProducts()`
  - 機能: 全商品一覧取得（認証不要）

- **GET** `/api/catalog/[id]`
  - Spring Boot: `CatalogController.getProductById()`
  - 機能: 商品詳細取得（認証不要）

## 実装された機能

### 1. データモデル
- **User**: `id`, `name`, `email`, `createdAt`, `updatedAt`
- **Product**: `id`, `name`, `price`
- Spring Boot の UserDto, ProductDto に対応

### 2. JWT認証
- トークン生成・検証機能
- パスワードハッシュ化 (bcrypt)
- Authorization Bearer トークン認証
- Spring Boot の JwtTokenProvider 機能に対応

### 3. バリデーション
- Zod スキーマによるリクエストバリデーション
- Spring Boot の @Valid, @NotBlank, @Email 等に対応

### 4. データ永続化
- メモリベースのデータストレージ
- 初期データ付き（ユーザー2件、商品3件）
- Spring Boot のリポジトリ層に対応

## ファイル構成

```
NextApp/
├── src/
│   ├── app/api/           # API Routes
│   │   ├── auth/login/
│   │   ├── users/
│   │   │   └── [id]/
│   │   └── catalog/
│   │       └── [id]/
│   └── lib/               # 共通ライブラリ
│       ├── types/api.ts   # 型定義・バリデーション
│       ├── utils/jwt.ts   # JWT関連ユーティリティ
│       └── data/storage.ts # データストレージ
└── .env.local             # 環境変数
```

## API動作確認方法

### 1. サーバー起動
```bash
cd NextApp
npm run dev
```

### 2. ログイン（トークン取得）
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john@example.com","password":"password123"}'
```

### 3. ユーザー一覧取得（認証必須）
```bash
curl -X GET http://localhost:3000/api/users \
  -H "Authorization: Bearer <取得したトークン>"
```

### 4. 商品一覧取得（認証不要）
```bash
curl -X GET http://localhost:3000/api/catalog
```

## 初期ユーザーデータ

- **ユーザー1**: `john@example.com` / `password123`
- **ユーザー2**: `jane@example.com` / `password456`

## 初期商品データ

- **商品1**: Trading Card Pack A (¥500)
- **商品2**: Trading Card Pack B (¥750)
- **商品3**: Special Edition Set (¥2000)

## 移植完了状況

✅ Spring Boot バックエンドの全API機能を Next.js API Route に移植完了
✅ JWT認証・バリデーション・データモデル移植完了
✅ TypeScript による型安全性確保
✅ 動作確認済み

Spring Boot の `backend/` ディレクトリの機能は、Next.js の `NextApp/src/app/api/` および `NextApp/src/lib/` に完全移植されました。
