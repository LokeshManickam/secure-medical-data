import { useEffect, useMemo, useState } from "react";
import api from "../api/api";

function Patients({ setSelectedPatient, setActivePage }) {
    const [patients, setPatients] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");

    const [isLoading, setIsLoading] = useState(true);
    const [isSaving, setIsSaving] = useState(false);

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("");

    const [editingPatient, setEditingPatient] = useState(null);

    const [formData, setFormData] = useState({
        patientCode: "",
        name: "",
        age: "",
        gender: "",
        diagnosis: ""
    });

    const loadPatients = async () => {
        setIsLoading(true);

        try {
            const response = await api.get("/patients");
            setPatients(response.data);
        } catch (error) {
            console.error("LOAD PATIENTS ERROR:", error);
            showMessage("Failed to load patients.", "error");
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        loadPatients();
    }, []);

    const showMessage = (text, type) => {
        setMessage(text);
        setMessageType(type);

        setTimeout(() => {
            setMessage("");
            setMessageType("");
        }, 3000);
    };

    const handleChange = (event) => {
        const { name, value } = event.target;

        setFormData((previous) => ({
            ...previous,
            [name]: value
        }));
    };

    const resetForm = () => {
        setFormData({
            patientCode: "",
            name: "",
            age: "",
            gender: "",
            diagnosis: ""
        });

        setEditingPatient(null);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        setIsSaving(true);

        try {
            if (editingPatient) {
                await api.put(`/patients/${editingPatient.id}`, {
                    ...formData,
                    age: Number(formData.age)
                });

                showMessage("Patient updated successfully.", "success");
            } else {
                await api.post("/patients", {
                    ...formData,
                    age: Number(formData.age)
                });

                showMessage("Patient added successfully.", "success");
            }

            resetForm();
            await loadPatients();

        } catch (error) {
            console.error("SAVE PATIENT ERROR:", error);

            const backendMessage =
                error.response?.data?.message ||
                error.response?.data ||
                "Failed to save patient.";

            showMessage(
                typeof backendMessage === "string"
                    ? backendMessage
                    : "Failed to save patient.",
                "error"
            );
        } finally {
            setIsSaving(false);
        }
    };

    const handleEdit = (patient) => {
        setEditingPatient(patient);

        setFormData({
            patientCode: patient.patientCode,
            name: patient.name,
            age: patient.age,
            gender: patient.gender,
            diagnosis: patient.diagnosis || ""
        });

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    };

    const handleDelete = async (id) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this patient?"
        );

        if (!confirmed) {
            return;
        }

        try {
            await api.delete(`/patients/${id}`);

            showMessage("Patient deleted successfully.", "success");

            await loadPatients();

        } catch (error) {
            console.error("DELETE PATIENT ERROR:", error);
            showMessage("Failed to delete patient.", "error");
        }
    };

    const handleView = (patient) => {
        setSelectedPatient(patient);
        setActivePage("access");
    };

    const filteredPatients = useMemo(() => {
        const search = searchTerm.toLowerCase().trim();

        if (!search) {
            return patients;
        }

        return patients.filter((patient) =>
            patient.patientCode.toLowerCase().includes(search) ||
            patient.name.toLowerCase().includes(search)
        );
    }, [patients, searchTerm]);

    return (
        <div className="page-content">

            <div className="page-heading">
                <div>
                    <p className="section-label">PATIENT MANAGEMENT</p>

                    <h2>
                        {editingPatient
                            ? "Edit Patient"
                            : "Patient Records"}
                    </h2>

                    <p>
                        Manage authorized patient records securely through
                        the protected medical data system.
                    </p>
                </div>

                <div className="patient-count-badge">
                    👥 {patients.length} Patients
                </div>
            </div>

            {message && (
                <div className={`page-message ${messageType}`}>
                    <span>
                        {messageType === "success" ? "✓" : "⚠"}
                    </span>

                    {message}
                </div>
            )}

            <div className="patient-management-grid">

                {/* PATIENT FORM */}

                <section className="content-card patient-form-card">

                    <div className="card-heading">
                        <div>
                            <span className="card-icon">
                                {editingPatient ? "✏️" : "➕"}
                            </span>

                            <div>
                                <h3>
                                    {editingPatient
                                        ? "Update Patient"
                                        : "Add Patient"}
                                </h3>

                                <p>
                                    {editingPatient
                                        ? "Modify the selected patient record."
                                        : "Create a new patient record."}
                                </p>
                            </div>
                        </div>
                    </div>

                    <form onSubmit={handleSubmit} className="patient-form">

                        <div className="form-row">

                            <div className="input-group">
                                <label htmlFor="patientCode">
                                    Patient Code
                                </label>

                                <input
                                    id="patientCode"
                                    name="patientCode"
                                    type="text"
                                    placeholder="Example: PT-001"
                                    value={formData.patientCode}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="input-group">
                                <label htmlFor="name">
                                    Full Name
                                </label>

                                <input
                                    id="name"
                                    name="name"
                                    type="text"
                                    placeholder="Enter patient name"
                                    value={formData.name}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                        </div>

                        <div className="form-row">

                            <div className="input-group">
                                <label htmlFor="age">
                                    Age
                                </label>

                                <input
                                    id="age"
                                    name="age"
                                    type="number"
                                    min="1"
                                    max="120"
                                    placeholder="Age"
                                    value={formData.age}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="input-group">
                                <label htmlFor="gender">
                                    Gender
                                </label>

                                <select
                                    id="gender"
                                    name="gender"
                                    value={formData.gender}
                                    onChange={handleChange}
                                    required
                                >
                                    <option value="">
                                        Select gender
                                    </option>

                                    <option value="Male">
                                        Male
                                    </option>

                                    <option value="Female">
                                        Female
                                    </option>

                                    <option value="Other">
                                        Other
                                    </option>
                                </select>
                            </div>

                        </div>

                        <div className="input-group">
                            <label htmlFor="diagnosis">
                                Diagnosis
                            </label>

                            <textarea
                                id="diagnosis"
                                name="diagnosis"
                                placeholder="Enter diagnosis information"
                                value={formData.diagnosis}
                                onChange={handleChange}
                                rows="4"
                            />
                        </div>

                        <div className="form-actions">

                            <button
                                type="submit"
                                className="primary-button"
                                disabled={isSaving}
                            >
                                {isSaving
                                    ? "Saving..."
                                    : editingPatient
                                        ? "Update Patient"
                                        : "Add Patient"}
                            </button>

                            {editingPatient && (
                                <button
                                    type="button"
                                    className="secondary-button"
                                    onClick={resetForm}
                                >
                                    Cancel Edit
                                </button>
                            )}

                        </div>

                    </form>

                </section>

                {/* PATIENT LIST */}

                <section className="content-card patient-list-card">

                    <div className="card-heading patient-list-heading">

                        <div>
                            <span className="card-icon">
                                📋
                            </span>

                            <div>
                                <h3>Patient Records</h3>

                                <p>
                                    Authorized medical records
                                </p>
                            </div>
                        </div>

                        <div className="patient-search">

                            <span>🔍</span>

                            <input
                                type="text"
                                placeholder="Search by code or name..."
                                value={searchTerm}
                                onChange={(event) =>
                                    setSearchTerm(event.target.value)
                                }
                            />

                        </div>

                    </div>

                    {isLoading ? (

                        <div className="empty-state">
                            <div className="loading-spinner"></div>

                            <h3>Loading patients...</h3>

                            <p>
                                Retrieving protected patient records.
                            </p>
                        </div>

                    ) : filteredPatients.length === 0 ? (

                        <div className="empty-state">

                            <div className="empty-icon">
                                {searchTerm ? "🔎" : "👥"}
                            </div>

                            <h3>
                                {searchTerm
                                    ? "No patients found"
                                    : "No patient records"}
                            </h3>

                            <p>
                                {searchTerm
                                    ? "Try a different patient code or name."
                                    : "Add your first patient record using the form."}
                            </p>

                        </div>

                    ) : (

                        <div className="patient-table-wrapper">

                            <table className="patient-table">

                                <thead>
                                    <tr>
                                        <th>Code</th>
                                        <th>Patient</th>
                                        <th>Age</th>
                                        <th>Gender</th>
                                        <th>Diagnosis</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>

                                <tbody>

                                    {filteredPatients.map((patient) => (

                                        <tr key={patient.id}>

                                            <td>
                                                <span className="patient-code">
                                                    {patient.patientCode}
                                                </span>
                                            </td>

                                            <td>
                                                <strong>
                                                    {patient.name}
                                                </strong>
                                            </td>

                                            <td>
                                                {patient.age}
                                            </td>

                                            <td>
                                                {patient.gender}
                                            </td>

                                            <td>
                                                <span className="diagnosis-text">
                                                    {patient.diagnosis ||
                                                        "Not provided"}
                                                </span>
                                            </td>

                                            <td>

                                                <div className="table-actions">

                                                    <button
                                                        className="action-button view"
                                                        onClick={() =>
                                                            handleView(patient)
                                                        }
                                                        title="View patient"
                                                    >
                                                        👁
                                                    </button>

                                                    <button
                                                        className="action-button edit"
                                                        onClick={() =>
                                                            handleEdit(patient)
                                                        }
                                                        title="Edit patient"
                                                    >
                                                        ✏️
                                                    </button>

                                                    <button
                                                        className="action-button delete"
                                                        onClick={() =>
                                                            handleDelete(patient.id)
                                                        }
                                                        title="Delete patient"
                                                    >
                                                        🗑️
                                                    </button>

                                                </div>

                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

                        </div>

                    )}

                </section>

            </div>

        </div>
    );
}

export default Patients;