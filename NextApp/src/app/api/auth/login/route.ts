import { NextRequest, NextResponse } from 'next/server';
import { LoginRequestSchema } from '@/lib/types/api';
import { UserRepository } from '@/lib/data/storage';
import { generateToken } from '@/lib/utils/jwt';

/**
 * POST /api/auth/login
 * Spring BootのAuthController.createAuthenticationToken()に対応
 */
export async function POST(request: NextRequest) {
  try {
    const body = await request.json();

    // リクエストのバリデーション
    const validation = LoginRequestSchema.safeParse(body);
    if (!validation.success) {
      return NextResponse.json(
        { error: 'Invalid request data', details: validation.error.issues },
        { status: 400 }
      );
    }

    const { username, password } = validation.data;

    // ユーザー認証（emailをusernameとして使用）
    const isValid = UserRepository.validatePassword(username, password);
    if (!isValid) {
      return NextResponse.json(
        { error: 'Invalid credentials' },
        { status: 401 }
      );
    }

    // JWTトークンの生成
    const token = generateToken(username);

    // トークンをレスポンス
    return NextResponse.json({ token });

  } catch (error) {
    console.error('Login error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
