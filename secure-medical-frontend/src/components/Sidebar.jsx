function Sidebar({ activePage, setActivePage }) {

    const menuItems = [
        {
            id: "dashboard",
            icon: "📊",
            label: "Dashboard"
        },
        {
            id: "patients",
            icon: "👥",
            label: "Patients"
        },
        {
            id: "access",
            icon: "🏥",
            label: "Patient Access"
        },
        {
            id: "messages",
            icon: "🔐",
            label: "Secure Messages"
        },
        {
            id: "security",
            icon: "🛡️",
            label: "Security Center"
        },
        {
            id: "activity",
            icon: "📋",
            label: "Activity"
        }
    ];

    return (

        <aside className="sidebar">

            <div className="sidebar-brand">

                <div className="sidebar-logo">
                    🔐
                </div>

                <div>
                    <h2>Secure Medical</h2>
                    <span>Data Protection</span>
                </div>

            </div>

            <nav className="sidebar-nav">

                <p className="nav-title">
                    MAIN MENU
                </p>

                {menuItems.map((item) => (

                    <button
                        key={item.id}
                        className={
                            activePage === item.id
                                ? "nav-item active"
                                : "nav-item"
                        }
                        onClick={() =>
                            setActivePage(item.id)
                        }
                    >

                        <span className="nav-icon">
                            {item.icon}
                        </span>

                        <span>
                            {item.label}
                        </span>

                    </button>

                ))}

            </nav>

            <div className="sidebar-security">

                <div className="security-icon">
                    🛡️
                </div>

                <div>
                    <strong>System Protected</strong>
                    <span>JWT Security Active</span>
                </div>

            </div>

        </aside>
    );
}

export default Sidebar;