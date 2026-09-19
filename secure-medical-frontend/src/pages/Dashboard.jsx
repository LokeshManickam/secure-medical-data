import { useEffect, useState } from "react";
import api from "../api/api";

function DashboardPage({ setActivePage }) {

    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const loadPatients = async () => {

            try {

                const response =
                    await api.get("/patients");

                setPatients(response.data);

            } catch (error) {

                console.error(
                    "Unable to load dashboard patients:",
                    error
                );

            } finally {

                setLoading(false);

            }
        };

        loadPatients();

    }, []);

    const recentPatients =
        patients.slice(-5).reverse();

    return (

        <div className="dashboard-content">

            {/* Welcome */}
            <section className="welcome-section">

                <div>

                    <p className="section-label">
                        OVERVIEW
                    </p>

                    <h2>
                        Welcome to Secure Medical Data
                    </h2>

                    <p>
                        Manage protected healthcare
                        information through a secure
                        authentication and encryption
                        environment.
                    </p>

                </div>

                <div className="welcome-icon">
                    🔐
                </div>

            </section>

            {/* Statistics */}
            <section className="stats-grid">

                <div className="stat-card">

                    <div className="stat-icon patients-icon">
                        👥
                    </div>

                    <div>
                        <span>
                            Total Patients
                        </span>

                        <strong>
                            {loading
                                ? "..."
                                : patients.length}
                        </strong>
                    </div>

                </div>

                <div className="stat-card">

                    <div className="stat-icon message-icon">
                        🔐
                    </div>

                    <div>
                        <span>
                            Secure Messaging
                        </span>

                        <strong>
                            Active
                        </strong>
                    </div>

                </div>

                <div className="stat-card">

                    <div className="stat-icon access-icon">
                        🏥
                    </div>

                    <div>
                        <span>
                            Patient Access
                        </span>

                        <strong>
                            Protected
                        </strong>
                    </div>

                </div>

                <div className="stat-card">

                    <div className="stat-icon security-icon-card">
                        🛡️
                    </div>

                    <div>
                        <span>
                            Security Status
                        </span>

                        <strong className="status-active">
                            Active
                        </strong>
                    </div>

                </div>

            </section>

            {/* Main dashboard grid */}
            <section className="dashboard-grid">

                {/* Recent Patients */}
                <div className="dashboard-card">

                    <div className="card-header">

                        <div>

                            <h3>
                                Recent Patient Records
                            </h3>

                            <p>
                                Latest protected patient data
                            </p>

                        </div>

                        <button
                            className="text-button"
                            onClick={() =>
                                setActivePage("patients")
                            }
                        >
                            View All
                        </button>

                    </div>

                    {recentPatients.length === 0 ? (

                        <div className="empty-state">

                            <span>👥</span>

                            <p>
                                No patient records yet.
                            </p>

                        </div>

                    ) : (

                        <div className="patient-preview">

                            {recentPatients.map(
                                (patient) => (

                                    <div
                                        className="patient-row"
                                        key={patient.id}
                                    >

                                        <div className="patient-avatar">
                                            {patient.name
                                                ?.charAt(0)
                                                .toUpperCase()}
                                        </div>

                                        <div className="patient-info">

                                            <strong>
                                                {patient.name}
                                            </strong>

                                            <span>
                                                {patient.patientCode}
                                            </span>

                                        </div>

                                        <span className="patient-status">
                                            Protected
                                        </span>

                                    </div>
                                )
                            )}

                        </div>

                    )}

                </div>

                {/* Security Overview */}
                <div className="dashboard-card">

                    <div className="card-header">

                        <div>

                            <h3>
                                Security Overview
                            </h3>

                            <p>
                                Active protection mechanisms
                            </p>

                        </div>

                        <span className="secure-badge">
                            SECURE
                        </span>

                    </div>

                    <div className="security-list">

                        <div className="security-row">

                            <span>JWT Authentication</span>

                            <strong>✓ Active</strong>

                        </div>

                        <div className="security-row">

                            <span>Password Protection</span>

                            <strong>✓ BCrypt</strong>

                        </div>

                        <div className="security-row">

                            <span>Data Encryption</span>

                            <strong>✓ AES-GCM</strong>

                        </div>

                        <div className="security-row">

                            <span>Text Concealment</span>

                            <strong>✓ Active</strong>

                        </div>

                        <div className="security-row">

                            <span>API Protection</span>

                            <strong>✓ Spring Security</strong>

                        </div>

                    </div>

                </div>

            </section>

            {/* Quick Actions */}
            <section className="quick-actions">

                <h3>
                    Quick Actions
                </h3>

                <div className="quick-action-grid">

                    <button
                        onClick={() =>
                            setActivePage("patients")
                        }
                    >

                        <span>👥</span>

                        <div>
                            <strong>
                                Manage Patients
                            </strong>

                            <small>
                                Add and manage records
                            </small>
                        </div>

                    </button>

                    <button
                        onClick={() =>
                            setActivePage("access")
                        }
                    >

                        <span>🏥</span>

                        <div>
                            <strong>
                                Patient Access
                            </strong>

                            <small>
                                View protected information
                            </small>
                        </div>

                    </button>

                    <button
                        onClick={() =>
                            setActivePage("messages")
                        }
                    >

                        <span>🔐</span>

                        <div>
                            <strong>
                                Secure Message
                            </strong>

                            <small>
                                Encrypt and conceal data
                            </small>
                        </div>

                    </button>

                    <button
                        onClick={() =>
                            setActivePage("security")
                        }
                    >

                        <span>🛡️</span>

                        <div>
                            <strong>
                                Security Center
                            </strong>

                            <small>
                                Review protection
                            </small>
                        </div>

                    </button>

                </div>

            </section>

        </div>
    );
}

export default DashboardPage;