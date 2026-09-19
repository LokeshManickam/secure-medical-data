function PatientAccess({ patient, setActivePage }) {

    if (!patient) {

        return (
            <div className="page-content">

                <div className="empty-access">

                    <span>🏥</span>

                    <p className="section-label">
                        AUTHORIZED ACCESS
                    </p>

                    <h2>
                        No Patient Selected
                    </h2>

                    <p>
                        Select a patient from Patient
                        Management to access protected
                        medical information.
                    </p>

                    <button
                        className="primary-button"
                        onClick={() =>
                            setActivePage("patients")
                        }
                    >
                        Open Patient Management
                    </button>

                </div>

            </div>
        );
    }

    return (
        <div className="page-content">

            <div className="page-heading">

                <div>

                    <p className="section-label">
                        AUTHORIZED ACCESS
                    </p>

                    <h2>
                        Patient Access
                    </h2>

                    <p>
                        Protected medical information
                        available to the authenticated user.
                    </p>

                </div>

                <span className="secure-badge">
                    🔐 AUTHORIZED
                </span>

            </div>

            <div className="access-card">

                <div className="access-header">

                    <div className="large-avatar">

                        {patient.name
                            ?.charAt(0)
                            .toUpperCase()}

                    </div>

                    <div>

                        <p className="section-label">
                            PATIENT RECORD
                        </p>

                        <h2>
                            {patient.name}
                        </h2>

                        <p>
                            Patient Code:{" "}
                            <strong>
                                {patient.patientCode}
                            </strong>
                        </p>

                    </div>

                </div>

                <div className="access-security">

                    <span>
                        🔐
                    </span>

                    <div>

                        <strong>
                            Authorized Access
                        </strong>

                        <p>
                            The current session is authenticated
                            using JWT-based security.
                        </p>

                    </div>

                </div>

                <div className="patient-details-grid">

                    <div>
                        <span>Patient Code</span>
                        <strong>
                            {patient.patientCode}
                        </strong>
                    </div>

                    <div>
                        <span>Patient Name</span>
                        <strong>
                            {patient.name}
                        </strong>
                    </div>

                    <div>
                        <span>Age</span>
                        <strong>
                            {patient.age}
                        </strong>
                    </div>

                    <div>
                        <span>Gender</span>
                        <strong>
                            {patient.gender}
                        </strong>
                    </div>

                    <div className="full-detail">
                        <span>Diagnosis</span>
                        <strong>
                            {patient.diagnosis ||
                                "Not provided"}
                        </strong>
                    </div>

                </div>

                <div className="access-actions">

                    <button
                        className="secondary-button"
                        onClick={() =>
                            setActivePage("patients")
                        }
                    >
                        ← Back to Patients
                    </button>

                </div>

            </div>

        </div>
    );
}

export default PatientAccess;