import { NextRequest, NextResponse } from 'next/server';
import { ProductRepository } from '@/lib/data/storage';

/**
 * GET /api/catalog/[id]
 * Spring BootのCatalogController.getProductById()に対応
 */
export async function GET(
  request: NextRequest,
  { params }: { params: { id: string } }
) {
  try {
    const productId = parseInt(params.id);
    if (isNaN(productId)) {
      return NextResponse.json(
        { error: 'Invalid product ID' },
        { status: 400 }
      );
    }

    // 商品取得（認証不要）
    const product = ProductRepository.findById(productId);
    if (!product) {
      return NextResponse.json(
        { error: 'Product not found' },
        { status: 404 }
      );
    }

    return NextResponse.json(product);

  } catch (error) {
    console.error('Get product error:', error);
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    );
  }
}
