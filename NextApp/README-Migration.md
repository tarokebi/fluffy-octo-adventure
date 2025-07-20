# Flet アプリケーションから Next.js への移植完了

## 移植概要

Python Flet アプリケーションを Next.js (React + TypeScript) に完全移植しました。

## 移植された機能

### 1. ページ構造
- **ホーム画面** (`/`) - ウェルカムメッセージとカタログへのナビゲーション
- **カタログ画面** (`/catalog`) - カタログ表示画面
- **ログイン画面** (`/login`) - ユーザー認証
- **ユーザー管理画面** (`/users`) - ユーザー一覧表示（認証必須）

### 2. 認証システム
- **ログイン機能** - ユーザー名・パスワード認証
- **JWT トークン管理** - ローカルストレージでトークン保存
- **認証チェック** - API 呼び出し時の認証状態確認
- **自動リダイレクト** - 未認証時のログイン画面遷移

### 3. UI コンポーネント
- **AppBar コンポーネント** - 共通ヘッダー（タイトル表示、戻るボタン）
- **Navigation コンポーネント** - グローバルナビゲーション
- **レスポンシブデザイン** - Tailwind CSS でモバイル対応
- **ダークモード対応** - システム設定に応じた自動切り替え

### 4. ルーティング
- **Next.js App Router** - ファイルベースルーティング
- **ページ遷移** - Link コンポーネントでSPA的な遷移
- **カスタムルーターフック** - Flet のルーター機能を模倣

### 5. テーマとスタイル
- **緑基調のカラーテーマ** - 元の Flet アプリのテーマを再現
- **Tailwind CSS** - ユーティリティファーストでスタイリング

## ファイル構成

```
NextApp/src/
├── app/
│   ├── layout.tsx          # ルートレイアウト
│   ├── page.tsx            # ホーム画面
│   ├── login/
│   │   └── page.tsx        # ログイン画面
│   ├── users/
│   │   └── page.tsx        # ユーザー管理画面
│   └── catalog/
│       └── page.tsx        # カタログ画面
├── components/
│   ├── AppBar.tsx          # 共通ヘッダーコンポーネント
│   └── Navigation.tsx      # グローバルナビゲーション
├── utils/
│   └── auth.ts             # 認証関連ユーティリティ
├── hooks/
│   └── useRouter.ts        # カスタムルーターフック
└── types/
    └── index.ts            # TypeScript型定義
```

## 元の Flet アプリとの対応

| Flet | Next.js |
|------|---------|
| `frontend/src/main.py` | `NextApp/src/app/layout.tsx` |
| `frontend/src/views/index.py` | `NextApp/src/app/page.tsx` |
| `frontend/src/views/catalog/index.py` | `NextApp/src/app/catalog/page.tsx` |
| `frontend/src/views/login/index.py` | `NextApp/src/app/login/page.tsx` |
| `frontend/src/views/users/index.py` | `NextApp/src/app/users/page.tsx` |
| `frontend/src/router/router.py` | Next.js App Router + `useRouter.ts` + `auth.ts` |
| `frontend/src/core/routed_view.py` | `AppBar.tsx` + React components |
| `frontend/src/config/url.py` | `utils/auth.ts` (API endpoints) |

## 起動方法

```bash
cd NextApp
npm run dev
```

アプリケーションは http://localhost:3000 で起動します。

## 技術スタック

- **Next.js 15.4.2** - React フレームワーク
- **React 19.1.0** - UI ライブラリ
- **TypeScript** - 型安全性
- **Tailwind CSS 4** - スタイリング
- **ESLint** - コード品質

## 移植完了した機能

✅ ページルーティング (/, /catalog, /login, /users)
✅ 認証システム (ログイン・トークン管理・認証チェック)
✅ UI コンポーネント (AppBar, Navigation)
✅ API 連携 (認証API・ユーザーAPI)
✅ テーマ・スタイル
✅ ナビゲーション
✅ レスポンシブデザイン
✅ ダークモード対応
✅ TypeScript 型安全性

元の Flet アプリケーションの全機能が Next.js に正常に移植され、同等の動作を提供します。

## 環境変数設定

バックエンドAPIエンドポイントを設定するため、`.env.local` ファイルを作成してください：

```bash
NEXT_PUBLIC_FOA_BACKEND_ENDPOINT=http://localhost:8080
```
