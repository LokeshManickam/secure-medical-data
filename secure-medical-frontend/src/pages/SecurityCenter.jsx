function SecurityCenter() {
    const securityModules = [
        {
            icon: "🔑",
            title: "JWT Authentication",
            status: "ACTIVE",
            description:
                "Authenticated users receive a JSON Web Token that is used to access protected REST APIs.",
            details: [
                "Token-based authentication",
                "Stateless session management",
                "Protected API requests"
            ]
        },
        {
            icon: "🔐",
            title: "BCrypt Password Hashing",
            status: "ACTIVE",
            description:
                "User passwords are stored as BCrypt hashes instead of plain-text passwords.",
            details: [
                "One-way password hashing",
                "Plain-text passwords are not stored",
                "Password verification during login"
            ]
        },
        {
            icon: "🛡️",
            title: "Spring Security",
            status: "ACTIVE",
            description:
                "Spring Security protects application endpoints and works with the JWT authentication filter.",
            details: [
                "Protected REST endpoints",
                "Authentication filter",
                "Role information available"
            ]
        },
        {
            icon: "🔒",
            title: "AES-GCM Encryption",
            status: "ACTIVE",
            description:
                "Sensitive medical data is encrypted using AES-GCM before the steganography stage.",
            details: [
                "AES encryption",
                "GCM authentication mode",
                "Encrypted payload before concealment"
            ]
        },
        {
            icon: "📝",
            title: "Text Steganography",
            status: "ACTIVE",
            description:
                "The encrypted payload is concealed inside carrier text using the project's zero-width Unicode implementation.",
            details: [
                "Encrypted data is hidden",
                "Carrier text is used",
                "Hidden payload can be extracted"
            ]
        },
        {
            icon: "🌐",
            title: "Protected REST APIs",
            status: "ACTIVE",
            description:
                "Application APIs are protected through Spring Security and JWT authentication.",
            details: [
                "Authenticated API access",
                "Authorization header",
                "Unauthorized requests are rejected"
            ]
        }
    ];

    return (
        <div className="page-content">

            <div className="page-heading">
                <div>
                    <p className="section-label">SECURITY</p>

                    <h2>Security Center</h2>

                    <p>
                        Overview of the security mechanisms implemented
                        in the Secure Medical Data system.
                    </p>
                </div>

                <span className="secure-badge">
                    🛡️ SYSTEM PROTECTED
                </span>
            </div>

            <div className="security-summary">

                <div className="security-summary-icon">
                    🛡️
                </div>

                <div>
                    <h3>Multi-Layer Data Protection</h3>

                    <p>
                        The application combines authentication,
                        password protection, encryption and text
                        steganography to protect sensitive medical data.
                    </p>
                </div>

                <span className="security-status">
                    ● ACTIVE
                </span>

            </div>

            <div className="security-grid">

                {securityModules.map((module) => (

                    <section
                        className="security-card"
                        key={module.title}
                    >

                        <div className="security-card-top">

                            <div className="security-module-icon">
                                {module.icon}
                            </div>

                            <span className="security-active">
                                ✓ {module.status}
                            </span>

                        </div>

                        <h3>{module.title}</h3>

                        <p className="security-description">
                            {module.description}
                        </p>

                        <div className="security-details">

                            {module.details.map((detail) => (
                                <div
                                    className="security-detail"
                                    key={detail}
                                >
                                    <span>✓</span>
                                    <span>{detail}</span>
                                </div>
                            ))}

                        </div>

                    </section>

                ))}

            </div>

            <div className="security-workflow">

                <div className="security-workflow-heading">
                    <div>
                        <p className="section-label">
                            PROTECTION WORKFLOW
                        </p>

                        <h3>
                            Medical Data Security Flow
                        </h3>
                    </div>
                </div>

                <div className="security-flow">

                    <div className="flow-item">
                        <span>1</span>
                        <strong>Medical Data</strong>
                        <small>
                            Sensitive information
                        </small>
                    </div>

                    <div className="flow-arrow">
                        →
                    </div>

                    <div className="flow-item">
                        <span>2</span>
                        <strong>AES-GCM</strong>
                        <small>
                            Data encryption
                        </small>
                    </div>

                    <div className="flow-arrow">
                        →
                    </div>

                    <div className="flow-item">
                        <span>3</span>
                        <strong>Steganography</strong>
                        <small>
                            Payload concealment
                        </small>
                    </div>

                    <div className="flow-arrow">
                        →
                    </div>

                    <div className="flow-item">
                        <span>4</span>
                        <strong>Authorized User</strong>
                        <small>
                            Secure extraction
                        </small>
                    </div>

                </div>

            </div>

            <div className="security-note">

                <span>ℹ️</span>

                <p>
                    <strong>Implementation note:</strong>{" "}
                    Text steganography is implemented using zero-width
                    Unicode characters. This is a technical implementation
                    decision for this project; the original project concept
                    requires text steganography but does not prescribe this
                    specific technique.
                </p>

            </div>

        </div>
    );
}

export default SecurityCenter;