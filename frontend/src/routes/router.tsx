import { createBrowserRouter, Navigate } from "react-router-dom";

import PlaceholderPage from "../pages/PlaceholderPage";
import LoginPage from "../features/auth/LoginPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <Navigate to="/login" replace />,
    },
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        path: "/register",
        element: <PlaceholderPage title="Register" />,
    },
    {
        path: "/",
        children: [
            {
                path: "dashboard",
                element: <PlaceholderPage title="Dashboard" />,
            },
            {
                path: "transactions",
                element: <PlaceholderPage title="Transactions" />,
            },
            {
                path: "transactions/new",
                element: <PlaceholderPage title="New Transaction" />,
            },
            {
                path: "transactions/:id/edit",
                element: <PlaceholderPage title="Edit Transaction" />,
            },
            {
                path: "categories",
                element: <PlaceholderPage title="Categories" />,
            },
            {
                path: "profile",
                element: <PlaceholderPage title="Profile" />,
            },
        ],
    },
    {
        path: "*",
        element: <Navigate to="/login" replace />,
    },
]);
