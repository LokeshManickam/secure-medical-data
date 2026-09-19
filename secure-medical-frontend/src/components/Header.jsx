function Header({ activePage, username, role, onLogout }) {

    const pageTitles = {
        dashboard: "Dashboard",
        patients: "Patient Management",
        access: "Patient Access",
        messages: "Secure Message Center",
        security: "Security Center",
        activity: "Activity"
    };

    return (

        <header className="app-header">

            <div>

                <p className="header-label">
                    SECURE MEDICAL DATA
                </p>

                <h1>
                    {pageTitles[activePage]}
                </h1>

            </div>

            <div className="header-user">

                <div className="user-avatar">
                    {role === "ADMIN" ? "A" : "D"}
                </div>

                <div className="user-details">

                    <strong>
                        {username}
                    </strong>

                    <span>
                        {role}
                    </span>

                </div>

                <button
                    className="logout-button"
                    onClick={onLogout}
                >
                    Logout
                </button>

            </div>

        </header>
    );
}

export default Header;