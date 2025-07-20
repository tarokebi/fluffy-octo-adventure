// Spring Boot DTOに対応するTypeScript型定義

export interface User {
  id: number;
  name: string;
  email: string;
  createdAt: string;
  updatedAt: string;
}

export interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
  category: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface CreateUserRequest {
  name: string;
  email: string;
  password: string;
}

export interface UpdateUserRequest {
  name: string;
  email: string;
}

// バリデーション用のZodスキーマ
import { z } from 'zod';

export const LoginRequestSchema = z.object({
  username: z.string().min(1, 'Username is required'),
  password: z.string().min(1, 'Password is required'),
});

export const CreateUserSchema = z.object({
  name: z.string().min(1, 'Name is required').max(250, 'Name must be 1-250 characters'),
  email: z.string().email('Email address seems to be invalid'),
  password: z.string().min(6, 'Password must be at least 6 characters'),
});

export const UpdateUserSchema = z.object({
  name: z.string().min(1, 'Name is required').max(250, 'Name must be 1-250 characters'),
  email: z.string().email('Email address seems to be invalid'),
});

export const CreateProductSchema = z.object({
  name: z.string().min(1, 'Name is required'),
  price: z.number().min(0, 'Price must be positive'),
});
