import bcrypt from 'bcryptjs';
import { User, Product } from '../types/api';

/**
 * Spring Bootのリポジトリ層に対応するメモリベースのデータストレージ
 */

// ユーザーデータ（パスワードハッシュ付き）
interface UserWithPassword extends User {
  passwordHash: string;
}

// 初期データ
let users: UserWithPassword[] = [
  {
    id: 1,
    name: 'John Doe',
    email: 'john@example.com',
    passwordHash: bcrypt.hashSync('password123', 10),
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
  {
    id: 2,
    name: 'Jane Smith',
    email: 'jane@example.com',
    passwordHash: bcrypt.hashSync('password456', 10),
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
];

let products: Product[] = [
  {
    id: 1,
    name: 'Trading Card Pack A',
    price: 500,
    description: 'ベーシックなトレーディングカードパック。初心者にもおすすめです。',
    category: 'スタンダード'
  },
  {
    id: 2,
    name: 'Trading Card Pack B',
    price: 750,
    description: 'より強力なカードが入ったプレミアムパック。上級者向け。',
    category: 'プレミアム'
  },
  {
    id: 3,
    name: 'Special Edition Set',
    price: 2000,
    description: '限定版のスペシャルセット。レアカードが必ず1枚入っています。',
    category: '限定版'
  },
];

let nextUserId = 3;
let nextProductId = 4;

/**
 * ユーザー関連の操作
 */
export class UserRepository {
  static findAll(): User[] {
    return users.map(user => ({
      id: user.id,
      name: user.name,
      email: user.email,
      createdAt: user.createdAt,
      updatedAt: user.updatedAt,
    }));
  }

  static findById(id: number): User | null {
    const user = users.find(u => u.id === id);
    if (!user) return null;

    return {
      id: user.id,
      name: user.name,
      email: user.email,
      createdAt: user.createdAt,
      updatedAt: user.updatedAt,
    };
  }

  static findByEmail(email: string): UserWithPassword | null {
    return users.find(u => u.email === email) || null;
  }

  static create(name: string, email: string, password: string): User {
    const passwordHash = bcrypt.hashSync(password, 10);
    const now = new Date().toISOString();

    const newUser: UserWithPassword = {
      id: nextUserId++,
      name,
      email,
      passwordHash,
      createdAt: now,
      updatedAt: now,
    };

    users.push(newUser);

    return {
      id: newUser.id,
      name: newUser.name,
      email: newUser.email,
      createdAt: newUser.createdAt,
      updatedAt: newUser.updatedAt,
    };
  }

  static update(id: number, name: string, email: string): User | null {
    const userIndex = users.findIndex(u => u.id === id);
    if (userIndex === -1) return null;

    users[userIndex] = {
      ...users[userIndex],
      name,
      email,
      updatedAt: new Date().toISOString(),
    };

    return {
      id: users[userIndex].id,
      name: users[userIndex].name,
      email: users[userIndex].email,
      createdAt: users[userIndex].createdAt,
      updatedAt: users[userIndex].updatedAt,
    };
  }

  static delete(id: number): boolean {
    const userIndex = users.findIndex(u => u.id === id);
    if (userIndex === -1) return false;

    users.splice(userIndex, 1);
    return true;
  }

  static validatePassword(email: string, password: string): boolean {
    const user = this.findByEmail(email);
    if (!user) return false;

    return bcrypt.compareSync(password, user.passwordHash);
  }

  static emailExists(email: string): boolean {
    return users.some(u => u.email === email);
  }
}

/**
 * 商品関連の操作
 */
export class ProductRepository {
  static findAll(): Product[] {
    return [...products];
  }

  static findById(id: number): Product | null {
    return products.find(p => p.id === id) || null;
  }

  static create(name: string, price: number, description: string = '', category: string = 'その他'): Product {
    const newProduct: Product = {
      id: nextProductId++,
      name,
      price,
      description,
      category,
    };

    products.push(newProduct);
    return newProduct;
  }

  static update(id: number, name: string, price: number, description: string = '', category: string = 'その他'): Product | null {
    const productIndex = products.findIndex(p => p.id === id);
    if (productIndex === -1) return null;

    products[productIndex] = { id, name, price, description, category };
    return products[productIndex];
  }

  static delete(id: number): boolean {
    const productIndex = products.findIndex(p => p.id === id);
    if (productIndex === -1) return false;

    products.splice(productIndex, 1);
    return true;
  }
}
