export interface RouteProps {
  path: string;
  component: React.ComponentType<any>;
  title: string;
}

export interface ViewStackItem {
  path: string;
  title: string;
  component: React.ComponentType<any>;
  parent?: ViewStackItem;
}

export interface User {
  id: number;
  name: string;
  email: string;
}

export interface AuthResponse {
  access_token: string;
}
