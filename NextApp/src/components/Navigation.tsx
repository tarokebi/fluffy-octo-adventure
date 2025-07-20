'use client';

import Link from 'next/link';
import { useRouter, usePathname } from 'next/navigation';
import { logout, getToken } from '@/utils/auth';
import { useState, useEffect } from 'react';

export default function Navigation() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const router = useRouter();
  const pathname = usePathname();

  useEffect(() => {
    const token = getToken();
    setIsAuthenticated(!!token);
  }, [pathname]);

  const handleLogout = () => {
    logout();
    setIsAuthenticated(false);
    router.push('/');
  };

  return (
    <nav className="bg-white dark:bg-gray-800 shadow-sm border-b border-gray-200 dark:border-gray-700">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-12">
          <div className="flex space-x-4">
            <Link
              href="/"
              className={`px-3 py-2 text-sm font-medium transition-colors duration-200 ${
                pathname === '/'
                  ? 'text-green-600 dark:text-green-400'
                  : 'text-gray-600 dark:text-gray-300 hover:text-green-600 dark:hover:text-green-400'
              }`}
            >
              Home
            </Link>
            <Link
              href="/catalog"
              className={`px-3 py-2 text-sm font-medium transition-colors duration-200 ${
                pathname === '/catalog'
                  ? 'text-green-600 dark:text-green-400'
                  : 'text-gray-600 dark:text-gray-300 hover:text-green-600 dark:hover:text-green-400'
              }`}
            >
              Catalog
            </Link>
            {isAuthenticated && (
              <Link
                href="/users"
                className={`px-3 py-2 text-sm font-medium transition-colors duration-200 ${
                  pathname === '/users'
                    ? 'text-green-600 dark:text-green-400'
                    : 'text-gray-600 dark:text-gray-300 hover:text-green-600 dark:hover:text-green-400'
                }`}
              >
                Users
              </Link>
            )}
          </div>

          <div className="flex items-center space-x-2">
            {isAuthenticated ? (
              <button
                onClick={handleLogout}
                className="px-3 py-1 text-sm font-medium text-red-600 dark:text-red-400 hover:text-red-700 dark:hover:text-red-300 transition-colors duration-200"
              >
                Logout
              </button>
            ) : (
              <Link
                href="/login"
                className="px-3 py-1 text-sm font-medium text-green-600 dark:text-green-400 hover:text-green-700 dark:hover:text-green-300 transition-colors duration-200"
              >
                Login
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
