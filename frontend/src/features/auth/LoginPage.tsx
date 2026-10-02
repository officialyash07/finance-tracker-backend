import { useState } from "react";

import { login } from "../../api/authApi";
import apiClient from "../../api/client";

const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (event: React.SubmitEvent<HTMLFormElement>) => {
        event.preventDefault();

        try {
            const response = await login({
                email,
                password,
            });

            console.log("Login successful:", response);

            const userResponse = await apiClient.get("/users/me");

            console.log("Current user:", userResponse.data);
        } catch (error) {
            console.error("Login failed:", error);
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-canvas">
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-md space-y-4 rounded-lg bg-surface p-6 shadow-md"
            >
                <h1 className="text-2xl font-semibold text-ink-900">Login</h1>

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                    className="w-full rounded-md border border-border px-3 py-2"
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    className="w-full rounded-md border border-border px-3 py-2"
                />

                <button
                    type="submit"
                    className="w-full rounded-md bg-brand-600 px-4 py-2 text-white hover:bg-brand-700"
                >
                    Login
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
