import { NextRequest, NextResponse } from 'next/server';
import { CreateUserSchema } from '@/lib/types/api';
import { UserRepository } from '@/lib/data/storage';
import { verifyToken, extractTokenFromHeader } from '@/lib/utils/jwt';

/**
 * GET /api/users
 * Spring BootのUserController.getAllUsers()に対応
 */
export async function GET(request: NextRequest) {
  try {
    // JWT認証チェック
    const authHeader = request.headers.get('authorization');
    const token = extractTokenFromHeader(authHeader);

    if (!token || !verifyToken(token)) {
      return NextResponse.json(
        { error: 'Unauthorized' },
        { status: 401 }
      );
    }

    // 全ユーザー取得
    const users = UserRepository.findAll();
    return NextResponse.json(users);

  } catch (error) {
    console.error('Get users error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}

/**
 * POST /api/users
 * Spring BootのUserController.createUser()に対応
 */
export async function POST(request: NextRequest) {
  try {
    // JWT認証チェック
    const authHeader = request.headers.get('authorization');
    const token = extractTokenFromHeader(authHeader);

    if (!token || !verifyToken(token)) {
      return NextResponse.json(
        { error: 'Unauthorized' },
        { status: 401 }
      );
    }

    const body = await request.json();

    // リクエストのバリデーション
    const validation = CreateUserSchema.safeParse(body);
    if (!validation.success) {
      return NextResponse.json(
        { error: 'Invalid request data', details: validation.error.issues },
        { status: 400 }
      );
    }

    const { name, email, password } = validation.data;

    // 既存ユーザーチェック
    if (UserRepository.emailExists(email)) {
      return NextResponse.json(
        { error: 'User with this email already exists' },
        { status: 409 }
      );
    }

    // ユーザー作成
    const newUser = UserRepository.create(name, email, password);

    return NextResponse.json(newUser, { status: 201 });

  } catch (error) {
    console.error('Create user error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
