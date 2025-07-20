export interface AuthResponse {
  token: string;
}

export const authApi = {
  /**
   * ログイン処理
   */
  async login(username: string, password: string): Promise<AuthResponse> {
    const response = await fetch('/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      throw new Error('Authentication failed');
    }

    return response.json();
  },

  /**
   * トークン検証
   */
  async validateToken(token: string): Promise<boolean> {
    // JWT verification is handled locally in the client
    // For a more secure implementation, you could create an API route for validation
    try {
      if (!token) return false;
      // Simple token format check (JWT has 3 parts separated by dots)
      const parts = token.split('.');
      return parts.length === 3;
    } catch {
      return false;
    }
  },

  /**
   * ユーザー一覧取得
   */
  async getUsers(token: string): Promise<any[]> {
    const response = await fetch('/api/users', {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`API failed. (${response.status})`);
    }

    return response.json();
  },

  /**
   * カタログ一覧取得
   */
  async getCatalog(): Promise<any[]> {
    const response = await fetch('/api/catalog');

    if (!response.ok) {
      throw new Error(`API failed. (${response.status})`);
    }

    return response.json();
  },
};

/**
 * 現在の認証状態をチェック
 */
export const isAuthenticated = async (): Promise<boolean> => {
  if (typeof window === 'undefined') return false;

  const token = localStorage.getItem('token');
  if (!token) return false;

  return authApi.validateToken(token);
};

/**
 * ログアウト処理
 */
export const logout = (): void => {
  if (typeof window !== 'undefined') {
    localStorage.removeItem('token');
    sessionStorage.removeItem('redirect_to');
  }
};

/**
 * トークンを取得
 */
export const getToken = (): string | null => {
  if (typeof window === 'undefined') return null;
  return localStorage.getItem('token');
};

/**
 * トークンを保存
 */
export const setToken = (token: string): void => {
  if (typeof window !== 'undefined') {
    localStorage.setItem('token', token);
  }
};
