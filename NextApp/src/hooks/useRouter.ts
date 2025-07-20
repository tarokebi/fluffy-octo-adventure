'use client';

import { useRouter as useNextRouter, usePathname } from 'next/navigation';

export interface RouterHook {
  push: (path: string) => void;
  back: () => void;
  currentPath: string;
  go: (path: string) => void;
}

export function useRouter(): RouterHook {
  const router = useNextRouter();
  const pathname = usePathname();

  return {
    push: (path: string) => router.push(path),
    back: () => router.back(),
    currentPath: pathname,
    go: (path: string) => router.push(path),
  };
}
