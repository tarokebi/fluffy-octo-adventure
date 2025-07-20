'use client';

import Link from 'next/link';

interface AppBarProps {
  title: string;
  showBackButton?: boolean;
  backHref?: string;
}

export default function AppBar({ title, showBackButton = false, backHref = '/' }: AppBarProps) {
  return (
    <header className="bg-green-100 dark:bg-green-900 shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center py-4">
          <h1 className="text-xl font-semibold text-green-900 dark:text-green-100">
            {title}
          </h1>
          {showBackButton && (
            <Link href={backHref}>
              <button className="text-green-700 hover:text-green-900 dark:text-green-300 dark:hover:text-green-100 font-medium">
                ← Back to Home
              </button>
            </Link>
          )}
        </div>
      </div>
    </header>
  );
}
