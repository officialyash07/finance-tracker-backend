import appClient from "./client";
import type {
    LoginRequest,
    LoginResponse,
    RegisterRequest,
    UserResponse,
} from "../types/auth";

export async function register(
    request: RegisterRequest,
): Promise<UserResponse> {
    const response = await appClient.post<UserResponse>(
        "/auth/register",
        request,
    );

    return response.data;
}

export async function login(request: LoginRequest): Promise<LoginResponse> {
    const response = await appClient.post<LoginResponse>(
        "/auth/login",
        request,
    );

    return response.data;
}
