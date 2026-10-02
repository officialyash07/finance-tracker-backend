export interface RegisterRequest {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    preferredCurrency: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    accessToken: string;
    tokenType: string;
    expiresIn: number;
}

export interface UserResponse {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
    preferredCurrency: string;
    createdAt: string;
}
