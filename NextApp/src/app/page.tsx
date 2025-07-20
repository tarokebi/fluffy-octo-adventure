'use client';

import Link from 'next/link';
import AppBar from '@/components/AppBar';

export default function Home() {
  return (
    <div className="min-h-screen bg-green-50 dark:bg-green-950">
      <AppBar title="Flet app" />

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex flex-col items-center justify-center space-y-8">
          <h2 className="text-2xl font-bold text-green-900 dark:text-green-100">
            Welcome to Fluffy Octo Adventure
          </h2>

          <Link href="/catalog">
            <button className="bg-green-600 hover:bg-green-700 text-white font-medium py-3 px-6 rounded-lg shadow-md transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2">
              Go to catalog
            </button>
          </Link>
        </div>
      </main>
    </div>
  );
}
