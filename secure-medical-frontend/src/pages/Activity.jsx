function Activity() {
    const activities = [
        {
            icon: "🔐",
            title: "Authentication System",
            description: "JWT-based authentication protects application access.",
            status: "ACTIVE",
            time: "System"
        },
        {
            icon: "👥",
            title: "Patient Management",
            description: "Authorized users can create, view, update and delete patient records.",
            status: "ACTIVE",
            time: "System"
        },
        {
            icon: "🔒",
            title: "Medical Data Encryption",
            description: "Sensitive medical information is protected using AES-GCM encryption.",
            status: "ACTIVE",
            time: "System"
        },
        {
            icon: "📝",
            title: "Text Steganography",
            description: "Encrypted payloads can be concealed inside carrier text.",
            status: "ACTIVE",
            time: "System"
        },
        {
            icon: "🛡️",
            title: "Protected REST APIs",
            description: "Protected endpoints require authenticated access.",
            status: "ACTIVE",
            time: "System"
        },
        {
            icon: "🏥",
            title: "Patient Access",
            description: "Selected patient information is presented through the protected application interface.",
            status: "ACTIVE",
            time: "System"
        }
    ];

    return (
        <div className="page-content">

            <div className="page-heading">
                <div>
                    <p className="section-label">SYSTEM MONITORING</p>

                    <h2>Activity</h2>

                    <p>
                        Overview of the major security and application
                        activities currently available in the system.
                    </p>
                </div>

                <span className="secure-badge">
                    🛡️ SYSTEM STATUS
                </span>
            </div>

            <div className="activity-summary">

                <div className="activity-summary-card">
                    <span className="activity-summary-icon">
                        ⚡
                    </span>

                    <div>
                        <strong>System Status</strong>
                        <span>Operational</span>
                    </div>
                </div>

                <div className="activity-summary-card">
                    <span className="activity-summary-icon">
                        🔐
                    </span>

                    <div>
                        <strong>Security</strong>
                        <span>Protected</span>
                    </div>
                </div>

                <div className="activity-summary-card">
                    <span className="activity-summary-icon">
                        👥
                    </span>

                    <div>
                        <strong>Access</strong>
                        <span>Authenticated</span>
                    </div>
                </div>

            </div>

            <section className="activity-card">

                <div className="activity-card-heading">
                    <div>
                        <p className="section-label">
                            APPLICATION MODULES
                        </p>

                        <h3>System Activity Overview</h3>
                    </div>

                    <span className="activity-count">
                        {activities.length} Modules
                    </span>
                </div>

                <div className="activity-list">

                    {activities.map((activity) => (

                        <div
                            className="activity-item"
                            key={activity.title}
                        >

                            <div className="activity-icon">
                                {activity.icon}
                            </div>

                            <div className="activity-information">

                                <div className="activity-title-row">

                                    <h4>
                                        {activity.title}
                                    </h4>

                                    <span className="activity-status">
                                        ✓ {activity.status}
                                    </span>

                                </div>

                                <p>
                                    {activity.description}
                                </p>

                            </div>

                            <span className="activity-time">
                                {activity.time}
                            </span>

                        </div>

                    ))}

                </div>

            </section>

            <div className="activity-note">

                <span>ℹ️</span>

                <p>
                    <strong>Note:</strong>{" "}
                    This page provides a system activity overview for the
                    project interface. It is not a persistent audit-log
                    database and does not represent historical user activity.
                </p>

            </div>

        </div>
    );
}

export default Activity;