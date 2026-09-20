function Sidebar({ activePage, setActivePage }) {
    const menuItems = [
        { id: "dashboard", label: "Dashboard" },
        { id: "patients", label: "Patients" },
        { id: "access", label: "Patient Access" },
        { id: "messages", label: "Secure Messages" },
        { id: "security", label: "Security" },
        { id: "activity", label: "Activity" }
    ];

    return (
        <aside className="sidebar">

            <div className="sidebar-brand">
                <div className="sidebar-logo">
                    SMD
                </div>

                <div>
                    <h2>Secure Medical</h2>
                    <span>Data Protection</span>
                </div>
            </div>

            <nav className="sidebar-nav">

                <p className="nav-title">
                    APPLICATION
                </p>

                {menuItems.map((item) => (
                    <button
                        key={item.id}
                        className={
                            activePage === item.id
                                ? "nav-item active"
                                : "nav-item"
                        }
                        onClick={() => setActivePage(item.id)}
                    >
                        <span className="nav-indicator"></span>
                        <span>{item.label}</span>
                    </button>
                ))}

            </nav>

            <div className="sidebar-footer">

                <div className="sidebar-footer-title">
                    Security Status
                </div>

                <div className="sidebar-status">
                    <span className="status-dot"></span>

                    <div>
                        <strong>Protected</strong>
                        <small>JWT authentication active</small>
                    </div>
                </div>

            </div>

        </aside>
    );
}

export default Sidebar;