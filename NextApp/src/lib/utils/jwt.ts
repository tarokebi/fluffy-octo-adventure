import jwt from 'jsonwebtoken';

// JWT設定（Spring Bootのapplication.propertiesに対応）
const JWT_SECRET = process.env.JWT_SECRET || '123456';
const JWT_EXPIRATION = process.env.JWT_EXPIRATION || '3600'; // 1時間

export interface JwtPayload {
  username: string;
  sub: string;
  iat: number;
  exp: number;
}

/**
 * JWTトークンを生成する
 * Spring BootのJwtTokenUtil.generateToken()に対応
 */
export function generateToken(username: string): string {
  const payload = {
    username,
    sub: username,
  };

  return jwt.sign(payload, JWT_SECRET, {
    expiresIn: parseInt(JWT_EXPIRATION),
  });
}

/**
 * JWTトークンを検証し、ペイロードを取得する
 * Spring BootのJwtTokenUtil.validateToken()に対応
 */
export function verifyToken(token: string): JwtPayload | null {
  try {
    const decoded = jwt.verify(token, JWT_SECRET) as JwtPayload;
    return decoded;
  } catch (error) {
    console.error('JWT verification failed:', error);
    return null;
  }
}

/**
 * Authorizationヘッダーからトークンを抽出する
 */
export function extractTokenFromHeader(authHeader: string | null): string | null {
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return null;
  }
  return authHeader.substring(7);
}

/**
 * トークンからユーザー名を取得する
 */
export function getUsernameFromToken(token: string): string | null {
  const payload = verifyToken(token);
  return payload ? payload.username : null;
}
