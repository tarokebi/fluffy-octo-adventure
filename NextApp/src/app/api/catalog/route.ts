import { NextRequest, NextResponse } from 'next/server';
import { ProductRepository } from '@/lib/data/storage';

/**
 * GET /api/catalog
 * Spring BootのCatalogController.getAllProducts()に対応
 */
export async function GET(request: NextRequest) {
  try {
    // 全商品取得（認証不要）
    const products = ProductRepository.findAll();
    return NextResponse.json(products);

  } catch (error) {
    console.error('Get products error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
