'use client';

import { useState, useEffect } from 'react';
import AppBar from '@/components/AppBar';
import { authApi } from '@/utils/auth';

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  category: string;
}

export default function Catalog() {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchCatalog = async () => {
    try {
      const data = await authApi.getCatalog();
      setProducts(data);
      setError(null);
    } catch (err: any) {
      console.error('Fetch catalog error:', err);
      setError(err.message || 'Network error. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchCatalog();
  }, []);

  if (isLoading) {
    return (
      <div className="min-h-screen bg-green-50 dark:bg-green-950">
        <AppBar title="カタログ" showBackButton={true} />
        <div className="container mx-auto px-4 py-8">
          <div className="flex justify-center">
            <div className="text-green-600 dark:text-green-300">Loading...</div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-green-50 dark:bg-green-950">
      <AppBar title="カタログ" showBackButton={true} />

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="space-y-6">
          <h2 className="text-3xl font-bold text-green-900 dark:text-green-100 text-center">
            商品カタログ
          </h2>

          {error && (
            <div className="mb-4 p-4 bg-red-100 dark:bg-red-900 border border-red-400 dark:border-red-600 text-red-700 dark:text-red-300 rounded">
              {error}
            </div>
          )}

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {products.length === 0 ? (
              <div className="col-span-full text-center text-green-700 dark:text-green-300">
                <p>商品が見つかりませんでした。</p>
              </div>
            ) : (
              products.map((product) => (
                <div key={product.id} className="bg-white dark:bg-green-800 rounded-lg shadow-md overflow-hidden">
                  <div className="p-6">
                    <h3 className="text-xl font-semibold text-green-900 dark:text-green-100 mb-2">
                      {product.name}
                    </h3>
                    <p className="text-green-700 dark:text-green-300 mb-4">
                      {product.description}
                    </p>
                    <div className="flex justify-between items-center">
                      <span className="text-sm text-green-600 dark:text-green-400 bg-green-100 dark:bg-green-700 px-2 py-1 rounded">
                        {product.category}
                      </span>
                      <span className="text-lg font-bold text-green-900 dark:text-green-100">
                        ¥{product.price.toLocaleString()}
                      </span>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </main>
    </div>
  );
}
