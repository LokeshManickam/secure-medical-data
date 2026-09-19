import { useState } from "react";
import api from "./api/api";
import Dashboard from "./Dashboard";
import "./App.css";

function App() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const [isLoggedIn, setIsLoggedIn] = useState(
        Boolean(localStorage.getItem("token"))
    );

    const handleLogin = async (event) => {

        event.preventDefault();

        setMessage("");
        setIsLoading(true);

        try {

            const response = await api.post(
                "/auth/login",
                {
                    username,
                    password
                }
            );

            localStorage.setItem(
                "token",
                response.data.token
            );

            setMessage("Login successful!");

            setIsLoggedIn(true);

        } catch (error) {

            console.error(error);

            setMessage(
                "Invalid username or password"
            );

        } finally {

            setIsLoading(false);
        }
    };

    // Show dashboard after successful login
    if (isLoggedIn) {
        return <Dashboard />;
    }

    return (

        <div className="login-page">

            <div className="login-background">

                <div className="login-card">

                    {/* Brand */}
                    <div className="brand-section">

                        <div className="brand-icon">
                            🔐
                        </div>

                        <h1>
                            Secure Medical Data
                        </h1>

                        <p>
                            Healthcare Data Protection System
                        </p>

                    </div>

                    {/* Security information */}
                    <div className="security-badge">

                        <span>🛡️</span>

                        <span>
                            Secure Authentication
                        </span>

                    </div>

                    {/* Login form */}
                    <form
                        className="login-form"
                        onSubmit={handleLogin}
                    >

                        <div className="input-group">

                            <label htmlFor="username">
                                Username
                            </label>

                            <input
                                id="username"
                                type="text"
                                placeholder="Enter your username"
                                value={username}
                                onChange={(event) =>
                                    setUsername(
                                        event.target.value
                                    )
                                }
                                autoComplete="username"
                                required
                            />

                        </div>

                        <div className="input-group">

                            <label htmlFor="password">
                                Password
                            </label>

                            <input
                                id="password"
                                type="password"
                                placeholder="Enter your password"
                                value={password}
                                onChange={(event) =>
                                    setPassword(
                                        event.target.value
                                    )
                                }
                                autoComplete="current-password"
                                required
                            />

                        </div>

                        <button
                            className="login-button"
                            type="submit"
                            disabled={isLoading}
                        >

                            {isLoading
                                ? "Authenticating..."
                                : "Sign In"
                            }

                        </button>

                    </form>

                    {/* Login message */}
                    {message && (

                        <div
                            className={
                                message.includes("successful")
                                    ? "login-message success"
                                    : "login-message error"
                            }
                        >
                            {message}
                        </div>

                    )}

                    {/* Security footer */}
                    <div className="login-footer">

                        <span>🔒 JWT Authentication</span>

                        <span>•</span>

                        <span>🛡️ Protected System</span>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default App;